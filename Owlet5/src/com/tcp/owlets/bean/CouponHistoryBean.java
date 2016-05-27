package com.tcp.owlets.bean;

public class CouponHistoryBean {

	private int genIndex=0;
	private String date="";
	private int count=0;
	private String name="";
	
	public int getGenIndex() {
		return genIndex;
	}
	public void setGenIndex(int genIndex) {
		this.genIndex = genIndex;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
