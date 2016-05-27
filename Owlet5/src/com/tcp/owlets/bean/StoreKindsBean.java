package com.tcp.owlets.bean;
/*
 * StroeKindsBean - 윤지수
 * 2015-11-10
 */
public class StoreKindsBean {
	private int storeCode; 
	private int address;
	private int groupType;
	private int timeType;
	private String startTime;
	private String endTime;
	private String startTimeHour;
	private String startTimeMin;
	private String endTimeHour;
	private String endTimeMin;
	
	
	public String getStartTimeHour() {
		return startTimeHour;
	}
	public void setStartTimeHour(String startTimeHour) {
		this.startTimeHour = startTimeHour;
	}
	public String getStartTimeMin() {
		return startTimeMin;
	}
	public void setStartTimeMin(String startTimeMin) {
		this.startTimeMin = startTimeMin;
	}
	public String getEndTimeHour() {
		return endTimeHour;
	}
	public void setEndTimeHour(String endTimeHour) {
		this.endTimeHour = endTimeHour;
	}
	public String getEndTimeMin() {
		return endTimeMin;
	}
	public void setEndTimeMin(String endTimeMin) {
		this.endTimeMin = endTimeMin;
	}
	public int getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(int storeCode) {
		this.storeCode = storeCode;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	public int getGroupType() {
		return groupType;
	}
	public void setGroupType(int groupType) {
		this.groupType = groupType;
	}
	public int getTimeType() {
		return timeType;
	}
	public void setTimeType(int timeType) {
		this.timeType = timeType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	
	

}
