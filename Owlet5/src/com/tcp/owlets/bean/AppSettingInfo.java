package com.tcp.owlets.bean;

public class AppSettingInfo {

	private int appIndex=0;
	private int layout=0;
	private String color="";
	private String image="";
	private String bgColor="";
	private String pathFile="";

	public int getLayout() {
		return layout;
	}

	public void setLayout(int layout) {
		this.layout = layout;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}

	public int getAppIndex() {
		return appIndex;
	}
	public void setAppIndex(int appIndex) {
		this.appIndex = appIndex;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}
}
