package com.tcp.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DayDV;
import com.sun.xml.internal.bind.v2.util.CollisionCheckStack;
import com.tcp.logger.LoggerConfig;
import com.tcp.owlets.bean.CouponBean;
import com.tcp.owlets.bean.PageInfoBean;
import com.tcp.owlets.bean.ReportBean;

/**
 * Report 정보 추가 및
 * 
 * Owlet5에서 앱만들기나 메뉴 구성을 통해 만들어지는 Frame과
 * page접근시 발생하는 count 를 관리한다.
 *
 *
 * @author blessldk
 *
 */
public class TaskManager {

	private HashMap<Integer, ReportBean> dayReportMap = new HashMap<Integer, ReportBean>();
	private HashMap<Integer, PageInfoBean> daypageReportMap = new HashMap<Integer, PageInfoBean>();
	private HashMap<Integer, CouponBean> dayCouponMap = new HashMap<Integer, CouponBean>();

	/**사용자 접근 여부를 판단하는 자료구조, 동기화가 반드시 필요하여 HashTable로 처리 **/
	private Hashtable<String , Integer> connTable=new Hashtable<String, Integer>();
	
	private static TaskManager instance=null;

	public static TaskManager getInstance(){
		if(instance==null){
			instance= new TaskManager();
		}
		return instance;
	}

	/**
	 * 초기 Service 구동 시 또는 App 
	 * @param beanList
	 */
	public void setAppInfoMap(ArrayList<ReportBean> beanList){
		dayReportMap.clear();

		for(ReportBean bean : beanList){
			dayReportMap.put(bean.getStoreCode(), bean);
		}
	}

	public void setPageInfoMap(ArrayList<PageInfoBean> beanList){
		daypageReportMap.clear();

		for(PageInfoBean bean : beanList){
			daypageReportMap.put(bean.getPageIndex(), bean);	
		}
	}

	public void setCouponsMap(ArrayList<CouponBean> beanList){
		dayCouponMap.clear();

		for(CouponBean bean : beanList){
			dayCouponMap.put(bean.getGenIndex(), bean);
		}
	}

	public HashMap<Integer, ReportBean> getAppInfoMap(){
		return this.dayReportMap;
	}

	public HashMap<Integer, PageInfoBean> getPageInfoMap(){
		return this.daypageReportMap;
	}

	public HashMap<Integer , CouponBean> getCouponMap(){
		return this.dayCouponMap;
	}
	
	public void addCoupon(CouponBean bean){
		dayCouponMap.put(bean.getGenIndex(), bean);
	}
	
	public void addPageInfo(PageInfoBean bean){
		daypageReportMap.put(bean.getPageIndex(), bean);
	}
	
	public void addAppMapInfo(ReportBean bean){
		dayReportMap.put(bean.getnIndex(), bean);
	}
	
	
	/**
	 * 이미지쿠폰의 사용 횟수를 증가 시킨다.
	 * @param genIndex
	 */
	public void setIncrement(int genIndex){
		//속도에 따라 동기화가 필요할지도...
		synchronized (dayCouponMap) {
			if(dayCouponMap.containsKey(genIndex)){
				int count = dayCouponMap.get(genIndex).getCount()+1;

				dayCouponMap.get(genIndex).setCount(count);
			}
		}
	}

	/**
	 * 해당 쿠폰의 금일 현재 Count
	 * @param genIndex
	 * @return
	 */
	public int getCount(int genIndex){
		int count=0;
		if(dayCouponMap.containsKey(genIndex)){
			count = dayCouponMap.get(genIndex).getCount();
		}
		return count; 
	}

	/**
	 * DayReport에 입력될 Data들을 쌓아둔다.
	 * 
	 * 인덱스 페이지로 접근 시 다운카운트를 늘린다.
	 * 
	 * @param storeCode
	 * @param dataType 0:VisitCount , 1:VisitorCount 2:Download
	 */
	public void setIncReportCount(int pageIndex, int storeCode, int dataType, boolean isIndexPage){
		int count=0;

		if(isIndexPage){
			if(dayReportMap.containsKey(storeCode)){
				int cnt=0;
				cnt=dayReportMap.get(storeCode).getDownCount()+1;
				dayReportMap.get(storeCode).setDownCount(cnt);
			}
		}

		if(dayReportMap.containsKey(storeCode)){
			switch(dataType){
			case 0:
				count=dayReportMap.get(storeCode).getVisitCount()+1;
				dayReportMap.get(storeCode).setVisitCount(count);
				break;
			case 1:
				count=dayReportMap.get(storeCode).getVisitorCount()+1;
				dayReportMap.get(storeCode).setVisitorCount(count);
				break;
			case 2:
				count=dayReportMap.get(storeCode).getDownCount()+1;
				dayReportMap.get(storeCode).setDownCount(count);
				break;
			default:
				break;
			}
		}

		if(daypageReportMap.containsKey(pageIndex)){
			int cnt=0;
			cnt=daypageReportMap.get(pageIndex).getVisitCount()+1;

			daypageReportMap.get(pageIndex).setVisitCount(cnt);
		}
	}

	public ReportBean getRealTimeReportData(int storeCode){
		ReportBean bean=new ReportBean();
		if(dayReportMap.containsKey(storeCode)){
			bean=dayReportMap.get(storeCode);
		}
		return bean;
	}
 
	/**
	 * 페이지 조횟수 증가를 실시간과 결합
	 * @param list
	 * @param storeCode
	 * @return
	 */
	public ArrayList<PageInfoBean> getPageCountList(ArrayList<PageInfoBean> list , int storeCode){
		
		if(list.size()==0){
			for(Integer code : daypageReportMap.keySet()){
				
				PageInfoBean data = daypageReportMap.get(code);

				if(data.getStoreCode()==storeCode){
					list.add(daypageReportMap.get(code));
				}
			}
		}else{
			for(PageInfoBean data : list){
				if(daypageReportMap.containsKey(data.getPageIndex())){
					
					int visitCount=data.getVisitCount()+daypageReportMap.get(data.getPageIndex()).getVisitCount();

					daypageReportMap.get(data).setVisitCount(visitCount);
				}
			}
		}
		Collections.sort(list, new CollectorSort());

		return list;
	}

	/**
	 * 
	 * @param list
	 * @param storeCode
	 * @return
	 */
	public ArrayList<ReportBean> getVisitCountList(ArrayList<ReportBean> list , int storeCode){
		if(dayReportMap.containsKey(storeCode)){
			if(!isExistList(list)){
				String date=CalendarFactory.getInstance().setCurrentDateTime(3);	// 0이었는데 (yy/mm/dd) 이전 날짜 포맷과 안맞아 3으로 변경 janpro

				ReportBean data=new ReportBean();

				int visitorCount=data.getVisitorCount()+dayReportMap.get(storeCode).getVisitorCount();
				int visitCount=data.getVisitCount()+dayReportMap.get(storeCode).getVisitCount();
				int downCount=data.getDownCount()+dayReportMap.get(storeCode).getDownCount();

				dayReportMap.get(storeCode).setVisitCount(visitCount);
				dayReportMap.get(storeCode).setVisitorCount(visitorCount);
				dayReportMap.get(storeCode).setDownCount(downCount);

				data.setVisitorCount(visitorCount);
				data.setVisitCount(visitCount);
				data.setDownCount(downCount);
				data.setDate(date);

				list.add(data);
			}
		}
		return list;
	}
	
	private boolean isExistList(ArrayList<ReportBean> list){
		String date=CalendarFactory.getInstance().setCurrentDateTime(3);
		
		boolean result=false;
		
		for(ReportBean bean : list){
			LoggerConfig.getEngineLogger().debug(bean.getDate() +" : " + date);
			
			if(bean.getDate().equals(date)){
				result=true;
			}
		}
		return result;
	}
	
	/**
	 * 접속에 대하여 Visit/Visitor/Download에 대해 처리한다.
	 * 
	 * 동일한 IP+동일 storeCode로 접근 시, Visit은 늘리지 않는다.
	 * 
	 * Visitor는 Index Page접근 DownLoad 역시 Index Page 접근시에만 늘리며
	 * 
	 * 마찬가지로 동일한 IP+동일한 storeCode로 두번 접근시에는 허용하지 않는다.
	 * 
	 * @param code
	 */
	public void setVisitData(int pageIndex , int code, boolean isIndexPage ){
		String key=createKey(code);
		
		if(!isExist(key)){
			addConnTable(key, code);
			
			setIncReportCount(pageIndex, code, AppManager.REPORT_VISITCOUNT, false); //IndexPage를 두번 걸지 않기 위해
			setIncReportCount(pageIndex, code, AppManager.REPORT_VISITORCOUNT, isIndexPage);
		}else{
			//인덱스 페이지로 접근했다면 , 다운로드 수는 함께 늘려준다.
			setIncReportCount(pageIndex, code,AppManager.REPORT_VISITCOUNT, isIndexPage);
		}
	}
	
	/**
	 * 접속하는 Client 의 IP를 확인한다.
	 * 
	 * 혹, 프록시나 Local Balancer 같은 것을 거쳐 오게 되는 경우 
	 * 
	 * getRemoteAddr()로는 확인할 수가 없으니, 메소드로 구현한다.
	 * 
	 * @return
	 */
	public String getClientIP(){
		ServletRequestAttributes attr = (ServletRequestAttributes)
				RequestContextHolder.currentRequestAttributes();

		HttpServletRequest req =attr.getRequest();
		
		String ip=req.getHeader("X-FORWARDED-FOR");
		
		if(ip==null || ip.length()==0){
			ip=req.getHeader("Proxy-Client-IP");
		}
		
		if(ip==null || ip.length()==0){
			ip=req.getHeader("WL-Proxy-Client-IP"); //웹로직
		}
				
		if(ip==null || ip.length()==0){
			ip=req.getRemoteAddr();
		}
		
		return ip;
	}
	
	public boolean isExist(String code){
		return connTable.containsKey(code);
	}
	
	public void addConnTable(String key , int code){
		connTable.put(key, code);
	}
	
	public String createKey(int code){
		String ip=getClientIP();
		
		String key=ip+String.valueOf(code);
		
		return key;
	}
}
