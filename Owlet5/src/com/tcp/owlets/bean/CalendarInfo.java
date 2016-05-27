package com.tcp.owlets.bean;

public class CalendarInfo {

	private int nIndex=0;
	private String title="";
	private String startDate="";
	private String endDate="";
	private String updateDate="";
	private String contents="";
	

	/**달력에 입력한 정보의 종류 0:공지 1:Event 2:쿠폰 3:일정**/
	private int type=-1;
	/** Popup 허용 여부 Default 1 **/
	private int popupEnable=1;
	/**alarm 허용 여부 Defalut 0 **/
	private int alarmEnable=0;
	/**alarm 방법 Defalut 1 **/
	private int alarmMethod=1;
	/**alarm 시간 선택 1시간~10시간 Default 1 **/
	private int alarmClock=1;
	/**달력 알람 간격 0:시간 1:일 2:주 **/
	private int alarmPeriod=0;
	
	private String eventType = "할인";
	/** 이벤트의 타입 **/
	
	public int getnIndex() {
		return nIndex;
	}
	public void setnIndex(int nIndex) {
		this.nIndex = nIndex;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String start) {
		this.startDate = start;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String end) {
		this.endDate = end;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public int getPopupEnable() {
		return popupEnable;
	}
	public void setPopupEnable(int popupEnable) {
		this.popupEnable = popupEnable;
	}
	public int getAlarmEnable() {
		return alarmEnable;
	}
	public void setAlarmEnable(int alarmEnable) {
		this.alarmEnable = alarmEnable;
	}
	public int getAlarmMethod() {
		return alarmMethod;
	}
	public void setAlarmMethod(int alarmMethod) {
		this.alarmMethod = alarmMethod;
	}
	public int getAlarmClock() {
		return alarmClock;
	}
	public void setAlarmClock(int alarmClock) {
		this.alarmClock = alarmClock;
	}
	public int getAlarmPeriod() {
		return alarmPeriod;
	}
	public void setAlarmPeriod(int alarmPeriod) {
		this.alarmPeriod = alarmPeriod;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
}
