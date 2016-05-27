package com.tcp.owlets.bean;

import com.tcp.util.ResManager;

public class AdderInfo {

	private int nIndex=0;
	
	private String title=ResManager.getString("Adder.Title.Default");
	
	private String content="";
	
	private String date="";
	
	private String path="";
	
	private int level=0;
	
	private int appIndex=0;
	
	private int parentIndex=0;
	
	private int groupIndex=0;
	
	private String groupName;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getAppIndex() {
		return appIndex;
	}

	public void setAppIndex(int appIndex) {
		this.appIndex = appIndex;
	}

	public int getParentIndex() {
		return parentIndex;
	}

	public void setParentIndex(int parentIndex) {
		this.parentIndex = parentIndex;
	}

	public int getGroupIndex() {
		return groupIndex;
	}

	public void setGroupIndex(int groupIndex) {
		this.groupIndex = groupIndex;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
