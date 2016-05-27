package com.tcp.owlets.bean;

/**
 * DayReport 의 기본정보 담고 있는 Bean
 * @author blessldk
 *
 */
public class ReportBean {
	private int nIndex=0;
	private String appName; //Report 에 넣기 위한 값
	private int storeCode = 0;
	private String date = "";
	private int downCount = 0;
	private int visitorCount = 0;
	private int visitCount = 0;
	private int couponCount=0;
	private int eventCount=0;
	private int useCouponCount=0;

	// summaryinfo에 필요한 데이터인데 어디 둘지 몰라서 일단 여기 둔다.
	private int replyCount = 0;
	private float starService = 0;
	private float starMood = 0;
	private float starGoods = 0;
	private int totalSales = 0;
	
	public int getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(int totalSales) {
		this.totalSales = totalSales;
	}
	public int getnIndex() {
		return nIndex;
	}
	public void setnIndex(int nIndex) {
		this.nIndex = nIndex;
	}
	public int getStoreCode() {
		return storeCode;
	}
	public void setVisitorCount(int visitorCount){
		this.visitorCount=visitorCount;
	}
	public int getVisitorCount(){
		return this.visitorCount;
	}
	public void setStoreCode(int storeCode) {
		this.storeCode = storeCode;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getDownCount() {
		return downCount;
	}
	public void setDownCount(int downCount) {
		this.downCount = downCount;
	}
	public int getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public int getCouponCount() {
		return couponCount;
	}
	public void setCouponCount(int couponCount) {
		this.couponCount = couponCount;
	}
	public int getEventCount() {
		return eventCount;
	}
	public void setEventCount(int eventCount) {
		this.eventCount = eventCount;
	}
	public int getUseCouponCount() {
		return useCouponCount;
	}
	public void setUseCouponCount(int useCouponCount) {
		this.useCouponCount = useCouponCount;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public float getStarService() {
		return starService;
	}
	public void setStarService(float starService) {
		this.starService = starService;
	}
	public float getStarMood() {
		return starMood;
	}
	public void setStarMood(float starMood) {
		this.starMood = starMood;
	}
	public float getStarGoods() {
		return starGoods;
	}
	public void setStarGoods(float starGoods) {
		this.starGoods = starGoods;
	}
}
