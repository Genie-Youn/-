package com.tcp.owlets.scheduler;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.InitBinder;

import com.tcp.owlets.bean.CouponBean;
import com.tcp.owlets.bean.PageInfoBean;
import com.tcp.owlets.bean.ReportBean;
import com.tcp.owlets.handler.ReportHandler;
import com.tcp.util.TaskManager;

@Service
public class ReportScheduler {
	
	@Autowired
	ReportHandler reportService;
	
	/**
	 * 매일 12시에 UseCouponHistory Table에 정보를 쌓는다.
	 */
	@Scheduled(cron= "0 00 00 * * ?")
	public void insertCouponsDailyHistory(){
		HashMap<Integer, CouponBean> map=TaskManager.getInstance().getCouponMap();
		
		for(int key : map.keySet()){
			if(map.containsKey(key)){
				reportService.insertUseCouponHistory(map.get(key));
			
				//Insert 가 끝난 후에 해당 map의 count를 초기화
				TaskManager.getInstance().getCouponMap().get(key).setCount(0);
			}
		}
	}
	
	/**
	 * 매일 AM 12시에 DayReports Table에 정보를 쌓는다.
	 */
	@Scheduled(cron= "0 00 00 * * ?")
	public void insertAppDailyReport(){
		HashMap<Integer, ReportBean> map=TaskManager.getInstance().getAppInfoMap();
		
		for(int key : map.keySet()){
			if(map.containsKey(key))
				reportService.insertDayReports(map.get(key));
		}
	}
	
	
	/**
	 * 매일 AM 12시에 DayPageReports Table에 정보를 쌓는다.
	 */
	@Scheduled(cron="0 00 00 * * ?")
	public void insertFrameInfoDailyReport(){
		HashMap<Integer, PageInfoBean> map=TaskManager.getInstance().getPageInfoMap();
		
		for(int key : map.keySet()){
			if(map.containsKey(key))
				reportService.insertPageReports(map.get(key));
		}
	}
}
