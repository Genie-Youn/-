package com.tcp.owlets.bean;

import com.tcp.util.ResManager;

public class StoreInfo {

	private int code=0;
	private String storeName=ResManager.getString("Store.name.Default");
	private String Image=null;
	private String phone=null;
	private int address=0;
	private String url=null;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String name) {
		this.storeName = name;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
