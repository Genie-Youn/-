package com.tcp.owlets.controller;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.eclipse.jdt.internal.compiler.lookup.MemberTypeBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcp.owlets.bean.MemberBean;
import com.tcp.owlets.bean.MemberStoreMapBean;
import com.tcp.owlets.bean.ReportBean;
import com.tcp.owlets.bean.ReportListInfo;
import com.tcp.owlets.bean.StoreInfo;
import com.tcp.owlets.handler.BasicHandler;
import com.tcp.owlets.handler.ReportHandler;
import com.tcp.util.CalendarFactory;
import com.tcp.util.DataParser;
import com.tcp.util.SessionManager;

/**
 * 통계와 메인보드 관련 사항은 
 * 여기에다가 RequestMapping으로 작업해주면 된다.
 * 
 * 뭐 다 똑같으니까 무지막지하게 쉽다..ㅋㅋ
 * 
 * @author blessldk
 *
 */
@Controller
@RequestMapping("/analysis")
public class AnalysisController {
	@Autowired
	ReportHandler reportService;
	
	@Autowired
	BasicHandler basicService;
	
	/**
	 * 통계 리스트 가져오는 메소드
	 * @return 
	 */
	@RequestMapping("/list")
	public String getAnalysisList(){
		return "Statistic";
	}
	
	@RequestMapping("/main")
	public String getDashBoard(){
		return "main";
	}
	
	/**
	 * 이쁘게 가져오는데 성공 !!
	 * Json 처리도 Period까지 모두 완료함.. 꽤 괜찮게 작업 함.
	 * 
	 * @author blessldk
	 * 
	 * @param codeIndex
	 * @param date ex) 20140910
	 * @param period (0: 일 1: 주 2: 월)
	 * @return
	 */
	@RequestMapping(value="/reportList", produces="application/json" , method=RequestMethod.POST)
	public @ResponseBody ReportListInfo getDayReportList(@RequestParam(value="date" , required=false) String date, 
								@RequestParam(value="period" , required=false) int period){
		int code = SessionManager.getInstance().getSessionCode();
		
		return reportService.getDayReportInfo(code, date, period,false);
	}
	
	/**
	 * 
	 * @param codeIndex
	 * @param date
	 * @param period
	 * @return
	 */
	@RequestMapping(value="/randomReportList", produces="application/json" , method=RequestMethod.POST)
	public @ResponseBody ReportListInfo getDayReportList(@RequestParam(value="startDate" , required=false) String startDate, 
								@RequestParam(value="endDate" , required=false) String endDate ){
		int code = SessionManager.getInstance().getSessionCode();
		
		return reportService.getDayReportInfoRandom(code, startDate, endDate);
		
	}
	
	/**
	 * DashBoard Chart에 뿌릴 정보와 기본정보를 함께 넘긴다.
	 * @return
	 */
	@RequestMapping(value="/dashboard" , produces="application/json")
	public @ResponseBody ReportListInfo getSummaryDataList(){
		MemberStoreMapBean session= SessionManager.getInstance().getSession();
		
		MemberBean bean=basicService.getMember(session.getnIndex());
		StoreInfo info=basicService.getStoreInfo(session.getStoreCode());
		
		String date = CalendarFactory.getInstance().setCurrentDateTime(2);
		
		ReportListInfo list = reportService.getDayReportInfo(session.getStoreCode(), date, 1,true);
		
		DataParser.getInstance().setAddressName(info.getAddress());
		
		list.setMemName(bean.getName());
		list.setStoreName(info.getStoreName());
		list.setAddress(DataParser.getInstance().getAddressName());
		list.setPhoneNum(info.getPhone());
		list.setHomepage(info.getUrl());
		list.setImage(info.getImage());
		
		return list;
	}
	
	/**
	 * 1분 단위로 요청이 들어오면 Dashboard Chart에 Display 정보 전달
	 * @return
	 */
	@RequestMapping(value="/dashboardRealTime" , produces="application/json")
	public @ResponseBody ReportListInfo getSummaryRealTimeData(){
		int code = SessionManager.getInstance().getSessionCode();
		
		String date = CalendarFactory.getInstance().setCurrentDateTime(2);
		
		ReportListInfo list = reportService.getDayReportInfo(code, date, 1 ,true);
		
		return list;
	}
	
	@RequestMapping(value="/summaryInfos", produces="application/json")
	public @ResponseBody ReportBean getSummrayInfos(){
		ReportBean bean = reportService.getSummaryInfos();
		
		return bean;
	}
}
