package com.tcp.owlets.bean;

import java.util.ArrayList;

/**
 * 대시보드 및 통계에 필요한 Data를 Json으로 보낼 때
 * 한 Request 요청으로 처리하기 위해 만든 Bean
 * PageInfo 와 Report 를 ArrayList로 처리하여 한번에 받는다.
 * 
 * @author blessldk
 *
 */
public class ReportListInfo {

	private int storeCode = 0;
	private ArrayList<ReportBean> dayReport = null;
	private ArrayList<PageInfoBean> pageReport = null;
	private ArrayList<CouponHistoryBean> couponReport=null;
	private ArrayList<CouponBean> couponGenReport = null;	// 차트 뿌릴 때 쿠폰별 정보도 알아야 하니..
	private ArrayList<ReplyBean> dayReplyReport = null;
	private ArrayList<ReplyBean> productReplyReport = null;
	
	 

	
	/* 기본정보도 같이 뿌려야 하니 추가합니다..*/
	private String memName;
	private String address;
	private String phoneNum;
	private String homepage;
	private String storeName;
	private String image;
	
	
	
	public int getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(int storeCode) {
		this.storeCode = storeCode;
	}
	public ArrayList<ReportBean> getDayReport() {
		return dayReport;
	}
	public void setDayReport(ArrayList<ReportBean> dayReport) {
		this.dayReport = dayReport;
	}
	public ArrayList<PageInfoBean> getPageReport() {
		return pageReport;
	}
	public void setPageReport(ArrayList<PageInfoBean> pageReport) {
		this.pageReport = pageReport;
	}
	public ArrayList<CouponHistoryBean> getCouponReport() {
		return couponReport;
	}
	public void setCouponReport(ArrayList<CouponHistoryBean> couponReport) {
		this.couponReport = couponReport;
	}
	
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public ArrayList<CouponBean> getCouponGenReport() {
		return couponGenReport;
	}
	public void setCouponGenReport(ArrayList<CouponBean> couponGenReport) {
		this.couponGenReport = couponGenReport;
	}
	public ArrayList<ReplyBean> getDayReplyReport() {
		return dayReplyReport;
	}
	public void setDayReplyReport(ArrayList<ReplyBean> dayReplyReport) {
		this.dayReplyReport = dayReplyReport;
	}
	public ArrayList<ReplyBean> getProductReplyReport() {
		return productReplyReport;
	}
	public void setProductReplyReport(ArrayList<ReplyBean> productReplyReport) {
		this.productReplyReport = productReplyReport;
	}
}
