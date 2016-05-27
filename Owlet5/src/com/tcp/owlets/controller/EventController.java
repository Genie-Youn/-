package com.tcp.owlets.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tcp.logger.LoggerConfig;
import com.tcp.owlets.bean.AdderInfo;
import com.tcp.owlets.bean.CalendarInfo;
import com.tcp.owlets.bean.CouponBean;
import com.tcp.owlets.bean.EventNoticeBean;
import com.tcp.owlets.handler.AdderHandler;
import com.tcp.owlets.handler.EventHandler;
import com.tcp.util.FileManager;
import com.tcp.util.NumberCouponFactory;
import com.tcp.util.SessionManager;

/**
 * 쿠폰, Event 관련 사항은 이쪽 Controller 에서 관여한다.
 * @author blessldk
 *
 */

@Controller
@RequestMapping("/event")
public class EventController {
	@Autowired
	EventHandler eventService;
	
	@Autowired
	AdderHandler adderService;
	
	/**
	 * @param bean
	 * @param calInfo
	 * @param file 
	 * @param type (0: Image 1: Num)
	 * @return
	 */
	@RequestMapping(value="/insertCoupons" , method=RequestMethod.POST)
	public String insertCoupon(@ModelAttribute(value="couponInfo") CouponBean bean ,
			@ModelAttribute(value="calInfo") CalendarInfo calInfo , 
			@RequestParam(value="file" , required=false) MultipartFile file,
			@RequestParam(value="group" , required=false) int type){

		calInfo.setType(2); //나는 쿠폰인 것을 알려준다는 그 타입인것이여...
		bean.setType(type); //얘는 그냥 쿠폰이 이미지인지 숫자인지 알려주는 것이고
		
		if(type==0){
			boolean isInsert = FileManager.getInstance().saveServerFile(file, 1);
			if(isInsert){
				eventService.insertCoupon(bean, calInfo);
				eventService.insertUseImageCoupon();
			}else{
				
			}
		}else{
			//숫자쿠폰 처리
			long startTime = System.currentTimeMillis();
			//동기화 처리가 필요합니다... 꼭 해놓읍시다..언젠가는~~
			NumberCouponFactory.getInstance().setCouponGenerator(bean.getCount());
			eventService.insertCoupon(bean, calInfo);
			eventService.insertActualCoupon();
			
			long endTime = System.currentTimeMillis();
			
			LoggerConfig.getWebLogger().debug("Used Time  : " + (endTime- startTime) +" ms");
		}
		
		//쿠폰 list URL 을 만든다
		if(!adderService.isCouponListExist()){
			AdderInfo info=new AdderInfo();
			info.setTitle("쿠폰목록");
			info.setContent("");

			adderService.insertFrame(info, 4);
		}
		
		return "redirect:/event/couponList.do";
	}

	@RequestMapping("/updateCoupons")
	public String updateCoupon(@ModelAttribute(value="couponInfo") CouponBean bean
			,@ModelAttribute(value="calInfo") CalendarInfo calInfo ,
			@RequestParam(value="file" , required=false) MultipartFile file,
			@RequestParam(value="group" , required=false) int type,
			@RequestParam(value="index", required=false) int index){
		
		calInfo.setType(2); //나는 쿠폰인 것을 알려준다는 그 타입인것이여...
		bean.setType(type); //얘는 그냥 쿠폰이 이미지인지 숫자인지 알려주는 것이고
		bean.setGenIndex(index);
		calInfo.setTitle(bean.getName());
		
		int storeCode= SessionManager.getInstance().getSessionCode();
		
		if(type==0){
			boolean isInsert = FileManager.getInstance().saveServerFile(file, 1);
			if(isInsert){
				eventService.updateCoupon(bean , calInfo , index , storeCode);
			}
		}else{
			eventService.updateCoupon(bean , calInfo , index , storeCode);
		}
		return "redirect:/event/couponList.do";
	}

	@RequestMapping("/coupon")
	public String couponEdit(){
		return "coupon";
	}
	
	/**
	 * 쿠폰 등록정보 출력 List Json
	 * @return
	 */
	@RequestMapping(value="/couponListToJson" , produces="application/json")
	public @ResponseBody ArrayList<CouponBean> getFrameListToJson() {  
		ArrayList<CouponBean> list = eventService.getCouponList();
		
		return list;
	}
	
	/**
	 * 쿠폰 등록정보 출력 List
	 * @return
	 */
	@RequestMapping("/couponList")
	public ModelAndView getFrameLists() {  
		ArrayList<CouponBean> list = eventService.getCouponList();
		
		return new ModelAndView("couponList", "couponList", list);  
	} 
	
	@RequestMapping(value="/getSelectCoupon" , method=RequestMethod.GET)
	public @ResponseBody CouponBean getSelectCoupon(@RequestParam(value="index") int index){
		CouponBean bean = eventService.getCoupon(index);
		
		return bean;
	}
	
	@RequestMapping("/eventList")
	public ModelAndView getEventList(){
		ArrayList<EventNoticeBean> list = eventService.getEventNoticeList();
		
		return new ModelAndView("eventList" , "eventList" , list);
	}
}
