package com.tcp.owlets.handler;

import java.util.ArrayList;

import javax.naming.ldap.PagedResultsControl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcp.owlets.bean.CouponBean;
import com.tcp.owlets.bean.PageInfoBean;
import com.tcp.owlets.bean.ReportBean;
import com.tcp.owlets.bean.ReportListInfo;
import com.tcp.owlets.dao.ReportSqlMapper;

@Service
public class ReportHandler {
	@Autowired
	ReportSqlMapper reportDao;
	
	public ReportListInfo getDayReportInfo(int storeCode, String date, int period, boolean isDash) {
		return reportDao.getDayReportInfo(storeCode, date, period, isDash);
	}

	public ReportListInfo getDayReportInfoRandom(int storeCode, String startDate,
			String endDate) {
		// TODO Auto-generated method stub
		return reportDao.getDayReportInfoRandom(storeCode, startDate, endDate);
	}

	public void insertUseCouponHistory(CouponBean bean) {
		reportDao.insertDailyUseCouponHistory(bean);
		
	}
	
	public void insertDayReports(ReportBean bean){
		reportDao.insertDailyReportHistory(bean);
	}
	
	public void insertPageReports(PageInfoBean bean){
		reportDao.insertPageReport(bean);
	}


	public ArrayList<CouponBean> getImageCouponList() {
		ArrayList<CouponBean> couponList = new ArrayList<CouponBean>();
		
		couponList = reportDao.getImageCouponList();
		
		return couponList;
	}

	public ArrayList<ReportBean> getAppList() {
		ArrayList<ReportBean> reportList = new ArrayList<ReportBean>();
		
		reportList = reportDao.getAppList();
		
		return reportList;
	}

	public ArrayList<PageInfoBean> getFrameList() {
		ArrayList<PageInfoBean> pageList = new ArrayList<PageInfoBean>();
		
		pageList = reportDao.getFrameList();
		
		return pageList;
	}

	public ReportBean getSummaryInfos(){
		ReportBean bean = reportDao.getSumSummrayInfo();
		
		return bean;
	}
	
	
}
