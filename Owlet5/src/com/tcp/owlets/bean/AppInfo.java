package com.tcp.owlets.bean;

public class AppInfo {
	
	private int appIndex=0;
	
	private String name="";
	
	private int storeCode=0;
	
	private int version=0;

	public int getAppIndex() {
		return appIndex;
	}
	
	public void setAppIndex(int appIndex) {
		this.appIndex = appIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(int storeCode) {
		this.storeCode = storeCode;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
