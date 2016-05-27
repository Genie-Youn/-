package com.tcp.owlets.bean;

/**
 * @author Tcp
 * PageReports와 DayPageReports 둘 다 아우르는 Bean이다.
 * 아직 extractor와 dao 등등이 없다.. 만들어야 한다..
 */
public class PageInfoBean {
	
	private String pageName = "";   // 리턴용으로, 위 테이블에 insert되지는 않는다.
	/*
	 *   Field : time 
	 *   설명 : 본래 PageInfo 테이블에 없어도 되지만,
	 *   			 시간별 방문자 수를 체크하기 위한 용도로 사용된다.
	 *   			 서버에 만들경우 Bean 클래스를 하나 더 추가하게 되므로, ~
	 */
	private String time = "";       // DayPageReports : date  변수 이름이 날짜 정보를 함축하지 않으므로, 변수명 바꿀가 생각중
	private int visitCount = -1;  // PageReports : visitCount 
	
	// pageIndex -> 각 Frame의 index번호.  page이름을 select 할 때 사용될 듯 하다.
	private int pageIndex = -1;  // DayPageReports : pageIndex
	
	private String path;
	
	private int storeCode;

	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public int getVisitCount() {
		return visitCount;
	}
	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}
	public String getTime(){
		return this.time;
	}
	public void setTime(String time){
		this.time=time;
	}
	public int getPageIndex(){
		return this.pageIndex;
	}
	public void setPageIndex(int pageIndex){
		this.pageIndex=pageIndex;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(int storeCode) {
		this.storeCode = storeCode;
	}
}
