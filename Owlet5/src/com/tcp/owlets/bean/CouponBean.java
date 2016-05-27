package com.tcp.owlets.bean;

import com.tcp.util.ResManager;

public class CouponBean {

	private int genIndex=0;
	private int storeCode=0;
	private String name="";
	private String type=""; //0:Image 1:number 2: Bar 3: QRCode
	private int originType=-1;
	private String image="";
	private String codePath=null;
	private int count=0; //숫자코드 발급 최대량

	private String startDate;
	private String endDate;

	private int codeIndex=0;

	public int getGenIndex() {
		return genIndex;
	}
	public void setGenIndex(int genIndex) {
		this.genIndex = genIndex;
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
	public String getType() {
		return type;
	}
	/**
	 * type은 0:Image, 1: 숫자 , 2: 바코드 , 3: 큐알코드 까지 추가한다.
	 * 향후 사용하면 이에 따르기 바란다.
	 * @param type
	 */
	public void setType(int type) {

		setOriginType(type); //Type값의 기본 Table에 저장된 값을 유지할 Bean Data
		
		String value="";

		switch(type){
		case 0:
			value=ResManager.getString("Coupon.Type.Image");
			break;
		case 1:
			value=ResManager.getString("Coupon.Type.Number");
			break;
		case 2:
			value=ResManager.getString("Coupon.Type.Bar");
			break;
		case 3:
			value=ResManager.getString("Coupon.Type.QRcode");
			break;
		default :
			value="-";
			break;
		}
		this.type = value;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCodePath() {
		return codePath;
	}
	public void setCodePath(String codePath) {
		this.codePath = codePath;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getCodeIndex() {
		return codeIndex;
	}
	public void setCodeIndex(int codeIndex) {
		this.codeIndex = codeIndex;
	}
	public int getOriginType() {
		return originType;
	}
	public void setOriginType(int originType) {
		this.originType = originType;
	}
}
