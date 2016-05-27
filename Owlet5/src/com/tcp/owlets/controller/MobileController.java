package com.tcp.owlets.controller;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tcp.logger.LoggerConfig;
import com.tcp.owlets.bean.AdderInfo;
import com.tcp.owlets.bean.AppInfo;
import com.tcp.owlets.bean.AppSettingInfo;
import com.tcp.owlets.bean.CalendarInfo;
import com.tcp.owlets.bean.CouponBean;
import com.tcp.owlets.bean.MobileBean;
import com.tcp.owlets.bean.EventNoticeBean;
import com.tcp.owlets.bean.StoreInfo;
import com.tcp.owlets.handler.AdderHandler;
import com.tcp.owlets.handler.BasicHandler;
import com.tcp.owlets.handler.EventHandler;
import com.tcp.owlets.handler.MobileHandler;
import com.tcp.util.AppManager;
import com.tcp.util.SessionManager;
import com.tcp.util.TaskManager;

/**
 * 웹앱에서 요청하는 Request URL 에 대해
 * 처리하는 컨트롤러
 * 
 * @author blessldk
 *
 */
@Controller
@RequestMapping("/mobile")
public class MobileController {
	@Autowired
	MobileHandler service;

	@Autowired
	AdderHandler adderSerivce;

	@Autowired
	EventHandler eventService;
	
	@Autowired
	BasicHandler basicService;

	/**
	 * 숫자쿠폰 사용내역 Update
	 * @param number
	 * @return
	 */
	@RequestMapping(value="/useNumberCoupon")
	public String updateNumberCouponStatus(@RequestParam(value="num") String number, 
			@RequestParam(value="couponIndex") int couponIndex){
		/*
		 * 숫자쿠폰 같은 경우 쿠폰 발급 번호만 알면되지 않나....
		 * 굳이 StoreCode나 이런거 확인할 필요는 없겠지..??
		 */
		service.updateNumberCoupon(number, couponIndex);

		return "";
	}

	/**
	 * Image 쿠폰 사용내역 Update
	 * @param couponIndex
	 * @return
	 */
	@RequestMapping(value="/useImageCoupon")
	public String updateImageCouponStatus(@RequestParam(value="couponIndex") int couponIndex){
		//하..이보다 좋은 방법이 있을것 같은데...

		/**
		 * Image 쿠폰 일일 사용량 증가 시키기 유후...
		 */
		TaskManager.getInstance().setIncrement(couponIndex);


		service.updateImageCoupon(couponIndex);

		return "";
	}

	@RequestMapping(value="/getFrames" , produces="application/json")
	public @ResponseBody AdderInfo getFrameInfo(@RequestParam(value="num") int frameIndex
			, @RequestParam(value="code") int storeCode){
		service.updatePageReport(frameIndex);

		AdderInfo adderInfo= adderSerivce.getFrame(frameIndex);
		
		//getFrames로 들어온 Data를 전달
		
		String key=TaskManager.getInstance().createKey(storeCode);
		
		if(!TaskManager.getInstance().isExist(key)){
			
		}
		
		TaskManager.getInstance().setVisitData(frameIndex, storeCode, false);
		return adderInfo;
	}

	@RequestMapping(value="/getCouponList", produces="application/json")
	public @ResponseBody ArrayList<CouponBean> getCouponLIst(@RequestParam(value="num") int frameIndex
			, @RequestParam(value="code") int storeCode){
		service.updatePageReport(frameIndex);

		ArrayList<CouponBean> list = eventService.getMonthCouponList(storeCode); 

		return list;
	}

	@RequestMapping(value="/getImgCoupon" , produces="application/json")
	public @ResponseBody CouponBean getImgCoupon(@RequestParam(value="num") int frameIndex
			, @RequestParam(value="code") int storeCode){
		CouponBean bean = eventService.getImageCoupon(storeCode, frameIndex);

		return bean;
	}

	@RequestMapping(value="/getNumCoupon" , produces="application/json")
	public @ResponseBody CouponBean getNumCoupon(@RequestParam(value="num") int frameIndex
			, @RequestParam(value="code") int storeCode){
		CouponBean bean = eventService.getNumberCoupon(storeCode, frameIndex);

		return bean;
	}

	@RequestMapping(value="/imgCouponCount")
	public @ResponseBody int increaseImgCouponCount(@RequestParam(value="num") int frameIndex){
		//		System.out.println(frameIndex);

		service.updateIncImgCouponCount(frameIndex);

		return 1;
	}

	@RequestMapping(value="/numCouponCount")
	public @ResponseBody int increaseNumCouponCount(@RequestParam(value="num") int frameIndex){
		service.updateIncNumCouponEanble(frameIndex);

		return 1;
	}

	@RequestMapping(value="/getNoticeList" , produces="application/json")
	public @ResponseBody ArrayList<CalendarInfo> getNoticeList(@RequestParam(value="num") int frameIndex
			, @RequestParam(value="code") int storeCode){

		ArrayList<CalendarInfo> list = service.getNoticeList(storeCode);

		return list;
	}

	@RequestMapping(value="/getAlarmDataInfo" , produces="application/json")
	public @ResponseBody EventNoticeBean getNotice(@RequestParam(value="num") int frameIndex
			, @RequestParam(value="code") int storeCode , @RequestParam(value="type") int type){
		EventNoticeBean info = service.getNotice(frameIndex, type);
		
		TaskManager.getInstance().setVisitData(frameIndex, storeCode, false);

		return info;
	}

	@RequestMapping(value="/ConfirmAppMake")
	public @ResponseBody int getConfirmAppMake(){
		try{
			
			int code=SessionManager.getInstance().getSessionCode();
			
			int appIndex=adderSerivce.isMobileAppFrameExist();
			
			AppInfo info=adderSerivce.getAppInfo(code);
			
			
			StoreInfo store=basicService.getStoreInfo(code);
			
			if(appIndex <= 0){
				AdderInfo bean=new AdderInfo();
				bean.setTitle(info.getName());
				bean.setContent("");
				
				adderSerivce.insertFrame(bean, 6);
				
				adderSerivce.updateAppConfirm(info);
			}else{
				AdderInfo bean=new AdderInfo();
				bean.setnIndex(appIndex);
				bean.setTitle(store.getStoreName());
				bean.setContent("");
				
				info.setName(store.getStoreName());
				
//				System.out.println(info.getName());
				
				adderSerivce.updateFrame(bean);
				
				adderSerivce.updateAppConfirm(info);
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			
			LoggerConfig.getWebLogger().debug(e.getMessage());
			
			return 0;
		}
	}
	
	@RequestMapping(value="/getAppInfo" , produces="application/json")
	public @ResponseBody MobileBean getAppInfo(@RequestParam(value="code") int code){
		MobileBean bean = service.getAppInfo(code);
		
		return bean;
	}
	
	@RequestMapping(value="/getMenuList" , produces="application/json")
	public @ResponseBody ArrayList<AdderInfo> getMenuList(@RequestParam(value="code") int code){
		ArrayList<AdderInfo> list= service.getMenuList(code);
	
		return list;
	}
	
	@RequestMapping(value="/ConfirmInfo" , produces="application/json")
	public @ResponseBody AppInfo getConfirmInfo(){
		
		int code=SessionManager.getInstance().getSessionCode();
		
		int appIndex=AppManager.getInstance().findAppIndex(code);
		
		AppInfo info=new AppInfo();
		
		info.setAppIndex(appIndex);
		info.setStoreCode(code);
		
		return info;
	}
	
	@RequestMapping(value="/groupInfo" , produces="application/json")
	public @ResponseBody ArrayList<AdderInfo> selectGroup(@RequestParam(value="group") int groupIndex){

		ArrayList<AdderInfo> info=service.getGroupInfo(groupIndex);
		
		return info;
	}
	
	@RequestMapping(value="/getImgFrames" , produces="application/json")
	public @ResponseBody AdderInfo getImgFrameInfo(@RequestParam(value="num") int frameIndex
			, @RequestParam(value="code") int storeCode , @RequestParam(value="group") int groupIndex){
		AdderInfo adderInfo= adderSerivce.getFrame(frameIndex);
		
		//getFrames로 들어온 Data를 전달
		TaskManager.getInstance().setVisitData(frameIndex, storeCode, false);
		
		return adderInfo;
	}
}


