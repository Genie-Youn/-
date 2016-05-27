package com.tcp.owlets.db;

import java.util.ArrayList;

/**
 * 서비스 시작 시, DB에 없는 테이블을
 * 자동으로 만들어주기 위한 테이블 정보 저장 장소 MYSQL 기준임
 * 
 * 각각의 테이블에 대한 정보를 메소드로 만들고
 * 
 * standardTable 메소드에 add해주면 끝이다.
 * 
 * 복사 붙여넣기 해서 하나둘씩 만들면된다.
 * 
 * @author blessldk
 *
 */

public class TableStructure {


	public ArrayList<TableInfo> standardTable(){
		ArrayList<TableInfo> tableList = new ArrayList<TableInfo>();

		tableList.add(getMembers());
		tableList.add(getStores());
		tableList.add(getMember_Store_Maps());
		tableList.add(getCouponInfos());
		tableList.add(getCouponKinds());
		tableList.add(getEvents());
		tableList.add(getUseCoupons());
		tableList.add(getUseCouponHistory());
		tableList.add(getNotices());
		tableList.add(getDayReports());
		tableList.add(getPageReports());
		tableList.add(getDayPageReports());
		tableList.add(getAppInfos());
		tableList.add(getFrameInfos());
		tableList.add(getAppSettingInfos());
		tableList.add(getCalendarInfos());
		tableList.add(getCalendarKinds());
		tableList.add(getCalendarStoreMaps());
		tableList.add(getFrameKinds());
		tableList.add(getGroupInfos());

		/*
		 * Owlet6를 위한 Table 추가
		 */
		tableList.add(getUserInfos());
		tableList.add(getReviewInfos());
		tableList.add(getStoreKinds());
		/*
		 * Owlet6를 위한 Table 추가 - 윤지수
		 * 2015-11-09
		 */
		tableList.add(getImageInfos());
		tableList.add(getStore_Image_Maps());
		tableList.add(getReply());
		
		tableList.add(getProductInfos());
		tableList.add(getProductKinds());
		
		return tableList;
	}

	private TableInfo getMembers(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();



		columnList.add(new ColumnInfo("nIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("name" , " NVARCHAR(50) NOT NULL"));
		columnList.add(new ColumnInfo("mail" , " NVARCHAR(200) NOT NULL"));
		columnList.add(new ColumnInfo("password" , " NVARCHAR(30) NOT NULL"));
		columnList.add(new ColumnInfo("nickName" , " NVARCHAR(50) NOT NULL"));

		table.setTableName("members");
		table.setColumnList(columnList);

		return table;
	}

	private TableInfo getStores(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();



		columnList.add(new ColumnInfo("code" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("name" , " NVARCHAR(100) NOT NULL"));
		columnList.add(new ColumnInfo("Image" , " NVARCHAR(200) "));
		columnList.add(new ColumnInfo("phone" , " NVARCHAR(15) "));
		columnList.add(new ColumnInfo("address" , " INT "));
		columnList.add(new ColumnInfo("url" , " NVARCHAR(200) "));

		table.setTableName("stores");
		table.setColumnList(columnList);

		return table;
	}

	private TableInfo getMember_Store_Maps(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();



		columnList.add(new ColumnInfo("nIndex" , " INT NOT NULL"));
		columnList.add(new ColumnInfo("code" , " INT NOT NULL PRIMARY KEY"));

		table.setTableName("member_store_maps");
		table.setColumnList(columnList);

		return table;
	}

	private TableInfo getCouponInfos(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();



		columnList.add(new ColumnInfo("genIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("storeCode" , " INT NOT NULL"));
		columnList.add(new ColumnInfo("name" , " NVARCHAR(200) "));

		table.setTableName("couponInfos");
		table.setColumnList(columnList);

		return table;
	}

	private TableInfo getCouponKinds(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("genIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("type" , " INT NOT NULL"));
		columnList.add(new ColumnInfo("imagePath" , " NVARCHAR(100) "));
		columnList.add(new ColumnInfo("codePath" , " NVARCHAR(100) "));
		columnList.add(new ColumnInfo("count" , " INT "));

		table.setTableName("couponKinds");
		table.setColumnList(columnList);

		return table;
	}

	private TableInfo getEvents(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();



		columnList.add(new ColumnInfo("nIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("name" , " NVARCHAR(200) NOT NULL"));
		columnList.add(new ColumnInfo("content" , " NVARCHAR(1024) "));
		columnList.add(new ColumnInfo("type" , " NVARCHAR(200) NOT NULL "));
		table.setTableName("events");
		table.setColumnList(columnList);

		return table;
	}

	private TableInfo getUseCoupons(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();



		columnList.add(new ColumnInfo("nIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("genIndex" , " INT NOT NULL"));
		columnList.add(new ColumnInfo("number" , " NVARCHAR(30) "));
		// checkOut 추가됨.
		columnList.add(new ColumnInfo("checkOut"," INT DEFAULT 0"));
		columnList.add(new ColumnInfo("count" , " INT DEFAULT 0"));
		columnList.add(new ColumnInfo("nEnable" , " INT DEFAULT 0 "));

		table.setTableName("useCoupons");
		table.setColumnList(columnList);

		return table;
	}

	private TableInfo getUseCouponHistory(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();



		columnList.add(new ColumnInfo("genIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("date" , " DATE NOT NULL"));
		columnList.add(new ColumnInfo("count" , " INT NOT NULL"));

		table.setTableName("useCouponHistory");
		table.setColumnList(columnList);

		return table;
	}

	private TableInfo getNotices(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("nIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("name" , " NVARCHAR(100) NOT NULL"));
		columnList.add(new ColumnInfo("content" , " NVARCHAR(1024) NOT NULL"));

		table.setTableName("notices");
		table.setColumnList(columnList);

		return table;
	}

	private TableInfo getDayReports(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("nIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("storeCode" , " INT NOT NULL"));
		columnList.add(new ColumnInfo("date" , " DATE NOT NULL"));
		columnList.add(new ColumnInfo("downCount" , " INT"));
		columnList.add(new ColumnInfo("visitCount" , " INT"));
		columnList.add(new ColumnInfo("visitorCount" , " INT"));

		table.setTableName("dayReports");
		table.setColumnList(columnList);

		return table;
	}

	private TableInfo getPageReports(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("nIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("visitCount" , " INT NOT NULL"));

		table.setTableName("pageReports");
		table.setColumnList(columnList);

		return table;
	}

	private TableInfo getDayPageReports(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("nIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("pageIndex" , " INT NOT NULL"));
		columnList.add(new ColumnInfo("count" , " INT NOT NULL"));
		columnList.add(new ColumnInfo("Date" , " DATETIME NOT NULL"));

		table.setTableName("dayPageReports");
		table.setColumnList(columnList);

		return table;
	}

	private TableInfo getAppInfos(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("appIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("name" , " NVARCHAR(20) NOT NULL"));
		columnList.add(new ColumnInfo("storeCode" , " INT NOT NULL"));
		columnList.add(new ColumnInfo("version" , " INT NOT NULL"));
		columnList.add(new ColumnInfo("confirm" , " INT NOT NULL default 0")); //APP이 끝까지 완성이 되었는지 판단한다.

		table.setTableName("appInfos");
		table.setColumnList(columnList);

		return table;
	}

	private TableInfo getFrameInfos(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("nIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("title" , " NVARCHAR(200) NOT NULL"));
		columnList.add(new ColumnInfo("content" , " TEXT NOT NULL"));
		columnList.add(new ColumnInfo("date" , " DATETIME NOT NULL"));
		columnList.add(new ColumnInfo("path" , " NVARCHAR(200) NOT NULL"));
		columnList.add(new ColumnInfo("nlevel" , " INT NOT NULL"));
		// 추가된 사항
		columnList.add(new ColumnInfo("appIndex"," INT"));
		columnList.add(new ColumnInfo("groupIndex"," INT"));

		table.setTableName("frameInfos");
		table.setColumnList(columnList);

		return table;
	}

	private TableInfo getAppSettingInfos(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("appIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("layout" , " INT NOT NULL"));
		columnList.add(new ColumnInfo("color" , " NVARCHAR(14) NOT NULL"));
		columnList.add(new ColumnInfo("image" , " NVARCHAR(50) NOT NULL"));
		columnList.add(new ColumnInfo("bgColor" , " NVARCHAR(50) NOT NULL"));
		columnList.add(new ColumnInfo("pathfile" , " NVARCHAR(50) NOT NULL"));

		table.setTableName("appSettingInfos");
		table.setColumnList(columnList);

		return table;
	}

	private TableInfo getCalendarInfos(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("nIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("title" , " NVARCHAR(100) NOT NULL"));
		columnList.add(new ColumnInfo("startDate" , " DATETIME NOT NULL"));
		columnList.add(new ColumnInfo("endDate" , " DATETIME NOT NULL"));
		columnList.add(new ColumnInfo("updateDate" , " DATETIME NOT NULL"));

		table.setTableName("calendarInfos");
		table.setColumnList(columnList);
		return table;
	}

	private TableInfo getCalendarKinds(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("nIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("type" , " INT NOT NULL"));
		columnList.add(new ColumnInfo("popupEnable" , " INT NOT NULL DEFAULT 1"));
		columnList.add(new ColumnInfo("alarmEnable" , " INT NOT NULL DEFAULT 0"));
		columnList.add(new ColumnInfo("alarmMethod" , " INT NOT NULL DEFAULT 0"));
		columnList.add(new ColumnInfo("alarmClock" , " INT NOT NULL DEFAULT 0"));
		columnList.add(new ColumnInfo("alarmPeriod" , " INT NOT NULL DEFAULT 0"));

		table.setTableName("calendarKinds");
		table.setColumnList(columnList);

		return table;
	}

	private TableInfo getCalendarStoreMaps(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("nIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("code" , " INT NOT NULL"));

		table.setTableName("calendar_store_maps");
		table.setColumnList(columnList);

		return table;
	}

	private TableInfo getFrameKinds(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("nIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("type" , " INT NOT NULL"));
		columnList.add(new ColumnInfo("nEnable" , " INT NOT NULL"));
		columnList.add(new ColumnInfo("menuEnable" , " INT NOT NULL"));
		columnList.add(new ColumnInfo("menuNum" , " INT NOT NULL"));

		table.setTableName("frameKinds");
		table.setColumnList(columnList);

		return table;
	}

	private TableInfo getGroupInfos(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("nIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("storeCode" , " INT NOT NULL"));
		columnList.add(new ColumnInfo("name" , " NVARCHAR(50) "));

		table.setTableName("groupInfos");
		table.setColumnList(columnList);

		return table;
	}

	private TableInfo getUserInfos(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("userIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("userName" , " VARCHAR(20) NOT NULL"));
		columnList.add(new ColumnInfo("userID" , " VARCHAR(50) NOT NULL"));
		columnList.add(new ColumnInfo("password" , " VARCHAR(20) NOT NULL"));
		columnList.add(new ColumnInfo("nickName" , " VARCHAR(30) NOT NULL"));
		columnList.add(new ColumnInfo("userBirth" , " DATE"));
		columnList.add(new ColumnInfo("gender" , " INT"));

		table.setTableName("userInfos");
		table.setColumnList(columnList);		


		return table;
	}

	private TableInfo getReviewInfos(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("nIndex" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("userIndex" , " INT NOT NULL"));
		columnList.add(new ColumnInfo("contents" , " TEXT"));
		columnList.add(new ColumnInfo("rate" , " DOUBLE NOT NULL"));
		columnList.add(new ColumnInfo("addDate" , " DATETIME NOT NULL"));
		columnList.add(new ColumnInfo("storeCode" , " INT NOT NULL"));

		table.setTableName("reviewInfos");
		table.setColumnList(columnList);		


		return table;
	}

	private TableInfo getStoreKinds(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("storeCode" , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("address" , " INT NOT NULL"));
		columnList.add(new ColumnInfo("groupType" , " INT NOT NULL"));
		columnList.add(new ColumnInfo("timeType" , " INT NOT NULL")); //영업시간 분류 (0: 24시간 , 1: 토요일 휴무 , 2: 일요일 휴무 , 3: 평일)
		columnList.add(new ColumnInfo("startTime" , " TIME NOT NULL")); // 영업 시작 시간
		columnList.add(new ColumnInfo("endTime" , " TIME NOT NULL")); // 영업 종료 시간

		/*
		 * menu 추가 - 윤지수
		 * 2015-11-10
		 */
		
//		columnList.add(new ColumnInfo("products" , " NVARCHAR(1000) NOT NULL")); // 다른 테이블로 빼는게 낫나? 걍 배열로?
		table.setTableName("storeKinds");
		table.setColumnList(columnList);		


		return table;
	}
	/*
	 * Owlet6를 위한 테이블 추가 윤지수
	 * 2015-11-09
	 */
	private TableInfo getImageInfos(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("nIndex " , " INT NOT NULL PRIMARY KEY"));
		columnList.add(new ColumnInfo("number " , " INT NOT NULL"));
		columnList.add(new ColumnInfo("url " , " NVARCHAR(50) NOT NULL"));
		columnList.add(new ColumnInfo("storeCode " , " INT NOT NULL")); 

		table.setTableName("imageInfos");
		table.setColumnList(columnList);		


		return table;
	}
	private TableInfo getStore_Image_Maps(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("nIndex " , " INT NOT NULL"));
		columnList.add(new ColumnInfo("storeCode " , " INT NOT NULL"));

		table.setTableName("Store_Image_Maps");
		table.setColumnList(columnList);		


		return table;
	}
	
	// reply page를 하나로 한정시키거나 page db 내부로 흡수 요망
	private TableInfo getReply(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("nIndex " , " INT NOT NULL AUTO_INCREMENT PRIMARY KEY")); //seq
		columnList.add(new ColumnInfo("contents " , " NVARCHAR(1000) NOT NULL"));
		columnList.add(new ColumnInfo("author " , " NVARCHAR(20) NOT NULL"));
		columnList.add(new ColumnInfo("starService " , " INT"));
		columnList.add(new ColumnInfo("starMood " , " INT"));
		columnList.add(new ColumnInfo("starGoods " , " INT"));
		columnList.add(new ColumnInfo("replyDate " , " DATETIME"));
//		columnList.add(new ColumnInfo("product " , " NVARCHAR(50) NOT NULL")); //상품 
		columnList.add(new ColumnInfo("pdIndex " , " INT NOT NULL")); //상품 
		columnList.add(new ColumnInfo("storeCode " , " INT NOT NULL")); // unique 

		table.setTableName("Reply");
		table.setColumnList(columnList);		


		return table;
	}
	private TableInfo getReplyReports(){	// 리뷰페이지 하나면 dayReports에 들어갈 수도, php서버에서 스케줄링?
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("nIndex " , " INT NOT NULL AUTO_INCREMENT PRIMARY KEY")); //seq
		columnList.add(new ColumnInfo("storeCode " , " INT NOT NULL")); // unique 
		columnList.add(new ColumnInfo("date " , " DATE"));
		columnList.add(new ColumnInfo("pdIndex " , " INT"));
		columnList.add(new ColumnInfo("replyCount " , " INT"));
		columnList.add(new ColumnInfo("starService " , " FLOAT"));
		columnList.add(new ColumnInfo("starMood " , " FLOAT"));
		columnList.add(new ColumnInfo("starGoods " , " FLOAT"));

		table.setTableName("dayeplyReports");
		table.setColumnList(columnList);		


		return table;
	}
	
	
	/**
	 * 기본적인 제품 정보 
	 * @return
	 */
	private TableInfo getProductInfos(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("pdIndex " , " INT NOT NULL AUTO_INCREMENT PRIMARY KEY")); //제품 키 값 Auto\로 증가..귀찮으니까 나중엔 code로 만들어
		columnList.add(new ColumnInfo("pdName " , " NVARCHAR(50) NOT NULL")); //상품
		columnList.add(new ColumnInfo("pdPrice " , " INT"));

		table.setTableName("productInfos");
		table.setColumnList(columnList);		


		return table;
	}

	/**
	 * 제품에 대한 분류가 세분화 되야 해서 필요한 내용이 많다.
	 * 
	 * 따라서 각 등록된 제품 인덱스 별로 storekind와 제품 kind 를 나눠서 등록한다.
	 * 
	 * 향후 제품 kind의 INT TYPE 을 더 추가하기에는 관리가 어렵다고 판단 될때는 column 을 더 늘리는 방향으로 한다.
	 * 
	 * 이때 Query 에는 지장이 없기 위해 JDBCTeplate 사용 시에는 Extractor 만들기 보다는 직접 Interface 오버라이드 해서 쓰는게 좋을듯...주절주절...
	 * @return
	 */
	private TableInfo getProductKinds(){
		TableInfo table = new TableInfo();

		ArrayList<ColumnInfo> columnList = new ArrayList<ColumnInfo>();

		columnList.add(new ColumnInfo("pdIndex " , " INT NOT NULL AUTO_INCREMENT PRIMARY KEY")); //제품 키 값 Auto\로 증가..귀찮으니까 나중엔 code로 만들어
		columnList.add(new ColumnInfo("storeKind " , " INT NOT NULL")); //상점 분류 코드 값
		columnList.add(new ColumnInfo("pdKind " , " INT")); //상점 별로 제품 코드 값 
		columnList.add(new ColumnInfo("storeCode ", " INT"));
		table.setTableName("productKinds");
		table.setColumnList(columnList);		


		return table;
	}
}
