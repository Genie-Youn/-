package com.tcp.owlets.bean;


public class MemberStoreMapBean {
	private int nIndex = 0;
	private int storeCode =0;
	
	private String name="";
	
	public int getnIndex() {
		return nIndex;
	}
	public void setnIndex(int nIndex) {
		this.nIndex = nIndex;
	}
	public int getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(int storeCode) {
		this.storeCode = storeCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
