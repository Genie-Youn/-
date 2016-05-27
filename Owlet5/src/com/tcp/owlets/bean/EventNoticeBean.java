package com.tcp.owlets.bean;

import java.text.ParseException;

import com.tcp.util.CalendarFactory;

/**
 * Event와 Notive 에서 같이 사용하는 Bean형태다.
 * 
 * Main Menu UI 변경하면서 변경함.
 * 
 * (15.08.21 이벤트 타입추가 - Youn js )
 * @author blessldk 
 *
 * 
 */
public class EventNoticeBean {

	private int nIndex=0;

	private String name="";

	private String context="";

	private String startDate="";

	private String endDate="";

	private int storeCode=0;

	private String type = "-";

	private int optionIndex=-1;

	public int getnIndex() {
		return nIndex;
	}

	public void setnIndex(int nIndex) {
		this.nIndex = nIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
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

	public void setShowStartDate(String startDate) throws ParseException{
		this.startDate = CalendarFactory.getInstance().setCustomDateTime(startDate, 4);
	}
	public void setShowEndDate(String endDate) throws ParseException{
		this.endDate = CalendarFactory.getInstance().setCustomDateTime(endDate, 4);
	}
	public int getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(int storeCode) {
		this.storeCode = storeCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;

		int value=0;
		
		switch(type){
		case "할인":
			value=0;
			break;
		case "사은품":
			value=1;
			break;
		case "행사":
			value=2;
			break;
		default:
			value=0;
			break;
		}
		setOptionIndex(value);
	}

	public int getOptionIndex() {
		return optionIndex;
	}
	
	public void setOptionIndex(int value){
		this.optionIndex = value;
	}
}
