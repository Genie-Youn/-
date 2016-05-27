package com.tcp.owlets.bean;

/*
 *  댓글 정보 - nIndex가 primary key
 *  janpro
 */
public class ReplyBean {
	private int nIndex;
	private String date;
	private int storeCode;
	private int pdIndex;
	private String contents;
	private String author;
	private float starService;
	private float starMood;
	private float starGoods;
	private int replyCount;
	
	// product 칼럼으로 데이터 추출할때 필요
	private String pdName;
	private int pdPrice;
	
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public int getnIndex() {
		return nIndex;
	}
	public void setnIndex(int nIndex) {
		this.nIndex = nIndex;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public float getStarService() {
		return starService;
	}
	public void setStarService(float starService) {
		this.starService = starService;
	}
	public float getStarMood() {
		return starMood;
	}
	public void setStarMood(float starMood) {
		this.starMood = starMood;
	}
	public float getStarGoods() {
		return starGoods;
	}
	public void setStarGoods(float starGoods) {
		this.starGoods = starGoods;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getPdIndex() {
		return pdIndex;
	}
	public void setPdIndex(int pdIndex) {
		this.pdIndex = pdIndex;
	}
	public int getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(int storeCode) {
		this.storeCode = storeCode;
	}
	public String getPdName() {
		return pdName;
	}
	public void setPdName(String pdName) {
		this.pdName = pdName;
	}
	public int getPdPrice() {
		return pdPrice;
	}
	public void setPdPrice(int pdPrice) {
		this.pdPrice = pdPrice;
	}
}
