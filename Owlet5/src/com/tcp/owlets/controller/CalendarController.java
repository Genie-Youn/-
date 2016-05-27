package com.tcp.owlets.controller;

import java.util.ArrayList;

import javax.xml.ws.RequestWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tcp.logger.LoggerConfig;
import com.tcp.owlets.bean.AdderInfo;
import com.tcp.owlets.bean.CalendarInfo;
import com.tcp.owlets.bean.EventNoticeBean;
import com.tcp.owlets.handler.AdderHandler;
import com.tcp.owlets.handler.CalendarHandler;
import com.tcp.util.SessionManager;

@Controller
@RequestMapping("/calendar")
public class CalendarController {

	@Autowired
	CalendarHandler calendarService;

	@Autowired
	AdderHandler adderService;

	@RequestMapping("/calendar")
	public String getCalendarPage(){
		return "calendar";
	}

	@RequestMapping("/getInsertPage")
	public String getInsertPage(){
		return "/calendar/edit";
	}

	/**
	 * @param storeCode
	 * @param calID
	 * @return CalendarInfo
	 * @using 데이터를 인서트 한 뒤, calID를 받고 그 calID를 통해 다시 정보를 요청하여 달력을 갱신한다.
	 */
	@RequestMapping(value="/getCalendarInfo", produces="application/json")
	public @ResponseBody CalendarInfo getCalendarInfo(@RequestParam(value="tcp") String tcp
			, @RequestParam int calID){

		int storeCode = SessionManager.getInstance().getSessionCode(tcp);


		CalendarInfo info = calendarService.getCalendarInfo(storeCode, calID);

		LoggerConfig.getWebLogger().debug(info.getContents());

		return info;
	}

	/**
	 * 
	 * @param info
	 * @param tcp
	 * @return CalendarInfo
	 */
	@RequestMapping(value="/calendarInsert", method=RequestMethod.POST , produces="application/json")
	public String insertCalendarInfo(@ModelAttribute(value="info") CalendarInfo info ,
			@RequestParam(value="tcp") String tcp){
		//		CalendarInfo bean = new CalendarInfo(); //향후 소스 수정에서..중간에 혹시모를  Set을 위해..


		LoggerConfig.getDBLogger().debug(info.getContents());
		int storeCode = SessionManager.getInstance().getSessionCode(tcp);
		calendarService.insertCalendarInfo(info,storeCode);

		//공지사항 list URL 을 만든다
		if(!adderService.isNoticeListExist()){
			AdderInfo bean=new AdderInfo();
			bean.setTitle("공지&이벤트");
			bean.setContent("");

			adderService.insertFrame(bean, 1);
		}

		String url="";

		switch(info.getType()){
		case 0:
			url="redirect:/notice/noticeList.do?tcp=";
			break;
		case 1:
			url="redirect:/event/eventList.do";
			break;
		case 2:
			url="redirect:/event/couponList.do";
		}
		return url;
	}

	@RequestMapping("/list")
	public String calendarList(){
		return "calendar";
	}
	/**
	 * 
	 * 목록보기 또는 초기에 Data 가져오는 작업 등 달력의 내용들을 가져올 때 쓴다.
	 * @param storeCode
	 * @return 사용자 달력 데이터를 모두 가져온다. 
	 */ 
	@RequestMapping(value="/allCalendarInfo", produces="application/json")
	public @ResponseBody ArrayList<CalendarInfo> getAllCalendarInfoList(@RequestParam(value="tcp" , required=false) String tcp){
		int storeCode = SessionManager.getInstance().getSessionCode(tcp);

		return calendarService.getAllCalendarInfo(storeCode);
	}
	/**
	 * 
	 * 달력 내용 삭제
	 * @Table  calendarinfos, calendarkinds 
	 * @param storeCode
	 * @param calID
	 */
	@RequestMapping(value="/deleteCalendarInfo",method=RequestMethod.GET)
	public String deleteCalendarInfo(@RequestParam(value="calID") int calNode){

		int storeCode = SessionManager.getInstance().getSessionCode();

		calendarService.deleteCalendarInfo(storeCode , calNode);

		return "redirect:/event/couponList.do";
	}

	/**
	 * 달력 세부항목 수정
	 * @Table calendarinfos, calendarkinds 
	 * @param storeCode
	 * @param calID
	 * @param bean
	 */

	@RequestMapping(value="/updateCalendarInfo",method=RequestMethod.POST)
	public  String updateCalendarInfo(@RequestParam(value="calID" , required=false)int calID,@RequestParam(value="tcp") String tcp
			,@ModelAttribute CalendarInfo bean){

		int storeCode = SessionManager.getInstance().getSessionCode(tcp);
		calendarService.updateCalendarInfo(storeCode, calID, bean);

		CalendarInfo info = new CalendarInfo();

		info = bean; //귀찮다.. 뭐 나중에 updateCalendarInfo에서 뭐 해가꼬 갖고오든가..말든가...

		String url="";

		switch(info.getType()){
		case 0:
			url="redirect:/notice/noticeList.do";
			break;
		case 1:
			url="redirect:/event/eventList.do";
			break;
		}
		return url;
	}


	@RequestMapping(value="/bulkDelete", method=RequestMethod.POST)
	public String bulkDeltete(@RequestParam(value="del_check") int[] calID, 
			@RequestParam(value="tcp") String tcp){

		int storeCode = SessionManager.getInstance().getSessionCode(tcp);

		for(int i=0,len=calID.length; i<len; i++){
			calendarService.deleteCalendarInfo(storeCode, calID[i]);
		}
		return "calendar";
	}

	@RequestMapping(value="/deletePage")
	public ModelAndView getCalendarList(@RequestParam(value="tcp") String tcp){

		int code = SessionManager.getInstance().getSessionCode(tcp);

		if(code==0)
			return new ModelAndView("/login");
		else{
			ArrayList<CalendarInfo> list = calendarService.getAllCalendarInfo(code);
			return new ModelAndView("calDelPage","calendarInfoList",list);
		}
	}
	@RequestMapping(value="/getEventInfo", produces = "application/json")
	public @ResponseBody EventNoticeBean getEventType(@RequestParam(value="tcp") String tcp
			, @RequestParam int calID){
		int storeCode = SessionManager.getInstance().getSessionCode(tcp);


		EventNoticeBean bean = calendarService.getEventInfo(storeCode, calID);




		return bean;
	}


}

