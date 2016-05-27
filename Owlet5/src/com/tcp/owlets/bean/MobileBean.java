package com.tcp.owlets.bean;

import java.util.ArrayList;

public class MobileBean {

	private int appIndex=0;
	private String appName;
	private ArrayList<AdderInfo> menuList;
	private String fColor;
	private String bColor;
	private String image;
	public int getAppIndex() {
		return appIndex;
	}
	public void setAppIndex(int appIndex) {
		this.appIndex = appIndex;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public ArrayList<AdderInfo> getMenuList() {
		return menuList;
	}
	public void setMenuList(ArrayList<AdderInfo> menuList) {
		this.menuList = menuList;
	}
	public String getfColor() {
		return fColor;
	}
	public void setfColor(String fColor) {
		this.fColor = fColor;
	}
	public String getbColor() {
		return bColor;
	}
	public void setbColor(String bColor) {
		this.bColor = bColor;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}
