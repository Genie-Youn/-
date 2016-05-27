package com.tcp.owlets.bean;

public class MemberBean {

	private int nIndex=0;
	private String name="";
	private String mail="";
	private String passwd="";
	private String nickName="";
	private int checkLogin=0;
	
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getCheckLogin() {
		return checkLogin;
	}
	public void setCheckLogin(int checkLogin) {
		this.checkLogin = checkLogin;
	}
}
