/**
 * 
 */



var globalData = {};
animationSpan = 3000;
initFunction();
globalCouponUseCount = {};
globalCouponUseCount.count = 0;
globalCouponUseCount.ratio = 0;
globalCouponUseCount.visit = '로딩중입니다';

charts = undefined;
$(document).ready(function(){
	
	
	setInterval(function(){
		$.get('/analysis/dashboardRealTime.do', function(data){
//			console.log(data, JSON.stringify(data));
			globalData = data;
			console.log('인터벌이 끝났다.');
		});
		drawCharts();//applyServerDataToSummaryTables(globalData);
	},
	1000 * 3
	);
	
	setTimeout(function(){
		$.get('/analysis/summaryInfos.do', function(data){
			setSummaryData(data);
		});
		setInterval(function(){
			$.get('/analysis/summaryInfos.do', function(data){
			setSummaryData(data);
			});
		},
		1000 * 3
		);	
	}, 0);
//	setTimeout(function(){
//		$.get('/analysis/dashboard.do', function(data){
//			console.log(data, JSON.stringify(data));
//			globalData = data;
//		});
//		drawCharts();//applyServerDataToSummaryTables(globalData);
//	}, 0);
});

var isFlagOn = false;

function setSummaryData(jsonSummaryData){
	console.log(jsonSummaryData);
	$('.visit-summary').html(jsonSummaryData.visitorCount);
	$('.visits-summary').html(jsonSummaryData.visitCount);
	$('.download-summary').html(jsonSummaryData.downCount);
	$('.coupon-summary').html(jsonSummaryData.couponCount);
	$('.event-summary').html(jsonSummaryData.eventCount);
	globalCouponUseCount = {};
	globalCouponUseCount.count = jsonSummaryData.useCouponCount;
	globalCouponUseCount.ratio = jsonSummaryData.useCouponCount / (jsonSummaryData.visitorCount | 1);
	globalCouponUseCount.visit = jsonSummaryData.visitorCount;
	
	if(!isFlagOn){
		isFlagOn = true;
	}
}

/**
 * 구글 차트를 불러오는 초기화를 함수로 뺐다.
 */
function initFunction(){
	google.load("visualization", "1", {packages:["corechart", "table"]});
	
	$.get('/analysis/dashboard.do', function(data){
//		console.log(data, JSON.stringify(data));
		globalData = data;
//		console.log(globalData, "initFunction이 끝났다.");
		
//		google.setOnLoadCallback(drawCharts);
		if (typeof google === "object" && typeof google.visualization === "object") {
			drawCharts();
		  } else {
		    //google.load("visualization", "1", { packages:["corechart"] });
//			google.load("visualization", "1", {packages:["corechart", "table"]});
//		    google.setOnLoadCallback(drawChartColumnChartID15fa166ce74);
			google.setOnLoadCallback(drawCharts);
		  }
	});
};

/**
 * 데이터를 받아올 함수가 필요한데...
 */
function getSummaryForChartDatas(){
	//do nothing.
}


/*
 * 
 * 서버로부터 받은 정보를 알맞게 배열에 넣는 작업을 진행하는 함수요.
 */
function applyServerDataToSummaryTables(data){
	var datas = {};
	var gData = {};
	var tempArr = [];
	var downloadArr = [];
	
	gData.pageArray = [];
	
	console.log("pass here?");
	/**
	 * 페이지 뷰 데이터 삽입.
	 */
	gData.pageArray.push(['페이지 이름', '본 횟수']);
	tempArr = [];
	for(var i in globalData.pageReport){
		tempArr.push(
				[
				 globalData.pageReport[i].pageName,
				 globalData.pageReport[i].visitCount
				 ]
				);
	}
	gData.pageArray.push(tempArr.slice(0));
	
	console.log("view pass here?");
	/**
	 * 방문자, 방문횟수 출력
	 */
	tempArr = [];
	downloadArr = [];
	gData.visitArray = [];
	gData.downloadArray = [];
	gData.visitArray.push(['날짜', '방문자수', '방문횟수']);
	gData.downloadArray.push(['날짜', '다운로드 횟수']);
	for(var i in globalData.dayReport){
		tempArr.push(
				[
				 globalData.dayReport[i].date
				 ,globalData.dayReport[i].visitorCount
				 ,globalData.dayReport[i].visitCount
				 ]
				);
		downloadArr.push(
				[
				 globalData.dayReport[i].date,
				 globalData.dayReport[i].downCount
				 ]
				);
	}
	gData.visitArray.push(tempArr.slice(0));
	gData.downloadArray.push(downloadArr.slice(0));
	
	console.log("visit pass here?");
	/**
	 * 쿠폰 정보 입력
	 */
	tempArr = [];
	gData.couponArray = [];
	gData.couponArray.push(['날짜', '사용 쿠폰 수']);
	for(var i in globalData.couponReport){
		tempArr.push([
		             globalData.couponReport[i].date,
					 globalData.couponReport[i].count
					]
					);
	}
	gData.couponArray.push(tempArr.slice(0));
	
	console.log("coupon, that is, all pass here?");
	
	return gData;
}
/**
 * 5개의 대시보드 데이터가 있는데, 그리기 작업을 한번에 일괄 처리하기 위해 
 * 각 차트 데이터를 배열로 만들고 차트를 그리는, 함수를 만들었다.
 * 
 * 데이터가 하드코딩되어 있으나 이후 데이터는 서버로부터 받아야 할 것이다.
 */
function drawCharts(){
	
	var gDatas = applyServerDataToSummaryTables(globalData);
	//setBasicInfos();
	console.log(gDatas, globalData);
//	if(!checkWhetherDataAreEmpty(gDatas)){
//		console.log("ciritical! no data is come here", gDatas);
//		$('.slot-body').slice(0, $('.slot-body').length-1).html('<img src="/res/img/nodata.png">')
//		
//		return;
//	}
	
	// 위 자리를 대체한다... 차트는 두 번 로드되지 않는다..
	/*if($.isEmptyObject(globalData)){
		console.log("빈 오브젝트이다. 이는 차트의 로딩이 안 되었다는 뜻이므로 재호출한다!");
		setTimeout(function(){
			$.get('/analysis/dashboard.do', function(data){
				console.log(data, JSON.stringify(data));
				globalData = data;
			});
			drawCharts();//applyServerDataToSummaryTables(globalData);
		}, 500);
		return false;
	}*/
	
	
	
//	console.log(gDatas);
	
	var datas = {};
	datas.datas = [];
//	datas.datas.push(google.visualization.arrayToDataTable(gDatas.pageArray));
//	console.log("passssss 1");
//	datas.datas.push(google.visualization.arrayToDataTable(gDatas.visitArray));
//	console.log("passssss 2");
//	datas.datas.push(google.visualization.arrayToDataTable(gDatas.downloadArray));
//	console.log("passssss 3");
//	datas.datas.push(google.visualization.arrayToDataTable(gDatas.couponArray));
//	console.log("passssss 4");
	
	console.log('datas.datas has made');
	
	var options = {datas:[
	               { title: "daily viewCount of page 가 제목",
	            	 /*width: "100%",*/
	            	 height:"100%",
	            	 colors: ['#387637'],
	            	 /*bar : {
	            		 		groupWidth : "50%"
	            	 	   },*/
	            	 legend:{
	            		 		position: "none"
	            	 	 	},
	            	 animation:{
        	 	        duration: animationSpan,
        	 	        easing: 'out',
        	 	      }
	               },
	               
	               { title: 'Weekly visits',
	            	 colors: ['#DDDDDD', '#387637'],
	            	 animation:{
	        	 	        duration: animationSpan,
	        	 	        easing: 'out',
	        	 	      }},
	               
	               { title: 'A present status of daily app download',
	            	 colors: ['#387637'],
	            	 animation:{
	        	 	        duration: animationSpan,
	        	 	        easing: 'out',
	        	 	      }},
	               
	               { title: 'Coupons spent per day (2014)',
	            	 colors: ['#387637'],
	            	 animation:{
	        	 	        duration: animationSpan,
	        	 	        easing: 'out',
	        	 	      }},
    	 	      {
    	 	    	 title: '쿠폰 대비 방문자수',
    	 	    	 pieHole: 0.5,
    	 	    	 slices: {
    	 	            0: { color: '#3333ff' },
    	 	            1: { color: '99ccff' }
    	 	          }
    	 	      }
	             ]};
	try {
//		if(!charts){
			
				charts = {charts:[
			              	new google.visualization.ColumnChart($('#view_chart_div')[0]),
			              	new google.visualization.ColumnChart($('#visit_chart_div')[0]),
			              	new google.visualization.LineChart($('#down_chart_div')[0]),
			              	new google.visualization.LineChart($('#coupon_chart_div')[0]),
			              	new google.visualization.PieChart($('#pie_chart_div')[0])
			              ]};
//		}
	} catch(e){
		console.log('error unexpected but..', e);
		delete charts;
		initFunction();
		return;
	} finally {
		
	}
	
	
	var divList = [$('#view_chart_div'), $('#visit_chart_div'),
	               $('#down_chart_div'), $('#coupon_chart_div')];
	var dataList = [gDatas.pageArray, gDatas.visitArray, gDatas.downloadArray, gDatas.couponArray];
	dataList.push([
          ['방문자수', 'Hours per Day'],
          [
	          ['쿠폰미사용자수',    globalCouponUseCount.visit],
	          ['쿠폰사용자수',      globalCouponUseCount.count],
          ]
        ]);
	var combinedData; 
	
	//차트 그리기 페이지 추적 부분은 view를 따로 만들어야 했는데, 현재는 조건문으로 이 부분만 다르게 했다..
//	for(var i = 0; i < datas.datas.length; i++){
	for(var i = 0; i < dataList.length; i++){
		console.log(i, dataList[i][1]);
		if(dataList[i][1].length == 0){
			divList[i].html('<img src="/res/img/nodata.png">');
			datas.datas.push([]);
			continue;
		} else {
			combinedData = new Array(dataList[i][0]).concat(dataList[i][1]);
			console.log(combinedData);
			datas.datas.push(google.visualization.arrayToDataTable(combinedData));
			console.log("put data at "+i, datas.datas[i]);
		}
		if(i == 0){
			var view = new google.visualization.DataView(datas.datas[i]);
			view.setColumns(
					[ 0,
					  1,
					  { calc : 'stringify',
						sourceColumn : 1,
						type : 'string',
						role : 'annotation'}
					//,
					 // 2
					]
			);
			charts.charts[i].draw(view, options.datas[i]);
			continue;
		}
		charts.charts[i].draw(datas.datas[i], options.datas[i]);
	}

	
	if(gDatas.pageArray[1].length != 0){
//	if(combinedData){
		//표는 별도로 그려야 했다. 각각, 페이지 뷰 추적, 시간대별 방문자 수 테이블 순이다.
		var tableDatas = new google.visualization.DataTable();
		tableDatas.addColumn('string', '페이지 이름');
		tableDatas.addColumn('number', '조횟수');
		
		var loopLength = gDatas.pageArray[1].length;
		if(loopLength > 11){
			loopLength = 10; //10개만 보여줘야 해. 그런데 첫줄은 제목줄이라서...
		}
		
		var tempRowData = [];
		for(var i = 0; i < loopLength; i++){
			tempRowData.push([
			                 gDatas.pageArray[1][i][0],
			                 { v : gDatas.pageArray[1][i][1],
			                   f : gDatas.pageArray[1][i][1] + ''
			                 }
			                 ]
						);
		}
		
	//	tableDatas.addRows([
	//	              	['Why I live'						,	{ v: 10000, f: '10,000'}	],
	//	              	['There is no love'					,	{ v: 8000,  f: '8,000'} 	],
	//	              	['Being killed, Bbobbi'				,	{ v: 12500, f: '125,000'}	],
	//	              	['Peaceful daylife of coding slaves', 	{ v: 7000, f:'7,000'}		],
	//	              	['Abracadabra'						,   { v: 7000,  f: '7,000'}		],
	//	              	['C+ Java C Three Kings'			,	{ v: 7000, f: '7,000'}		],
	//	              	['Di......'							,	{ v: 7000,  f: '7,000'}		],
	//	              	['MBEF....'							,	{ v: 7000,  f: '7,000'}		],
	//	              	['Fly high, firebird'				,	{ v: 7000,  f: '7,000'}		],
	//	              	['Ha... What am I doning'			,	{ v: 7000,  f: '7,000'}		]
	//	              ]);
		tableDatas.addRows(tempRowData);
		
		var tableChart = new google.visualization.Table($('#table_view_div')[0]);
		tableChart.draw(tableDatas, { showRowNumber: true ,
			cssClassNames	: {
			headerRow: 'headerRow',
//			hoverTableRow: 'hoverTableRow', 
			tableRow: 'tableRow',
			oddTableRow: 'oddTableRow',
//			selectedTableRow: 'selectedTableRow'
					  }});
	}
	
	//수정되야함...
	//$('.slot-body').eq(4).html('<img src="/res/img/nodata.png">');
	//$('.slot-body').eq(4).css('height', '315');
	$('.center-text-div').text(globalCouponUseCount.visit);
	
	setBasicInfos();
};

/**
 * 기본 정보를 저장하는 함수이다.
 * 
 * 회사명과 홈페이지 주소가 상당히 길면 고정된 높이가 매우
 * 부자연스러워 보이므로 글씨 길이가 특정 숫자를 넘어가면
 * 그에 따라 높이를 조정하도록 하였다.
 * 
 * 역시 이후에 데이터는 서버에서 받아와야 한다.
 */
function setBasicInfos(){
	//set basic info.
//	var basicInfo = {
//		name	: "부엉이",
//		phone 	: "010-####-####",
//		company	: "달푸른밤사냥",
////		logoUrl : "http://cfs12.blog.daum.net/image/29/blog/2008/02/26/08/45/47c35311e0614&filename=%EC%88%982-1.jpg",
//		logoUrl : "/res/img/comlogo.jpg",
//		homeUrl	: "http://www.owlet.brd",
//		address	: "아마집"
//	};
	
	if(globalData.memName == null){
		console.log("no more information about member");
		return;
	}
	
	var basicInfo = {
			name 	: globalData.memName,
			phone 	: (globalData.phoneNum)?(globalData.phoneNum):('전화번호 등록 안됨'),
			company	: globalData.storeName,
			logoUrl	: globalData.image,
			homeUrl	: globalData.homepage,
			address	: globalData.address
			
	}

	var nameSlot = $('.namecard-name-slot');
	nameSlot.html(basicInfo.name);
	
	var addressSlot = $('.namecard-address-slot');
	addressSlot.html(basicInfo.address);
	
	var logoPlacer = $('#card  .back');
//	logoPlacer.css("background", "url('/res/img/darkdenim3.png') repeat");
	//logoPlacer.css("background","url(" + basicInfo.logoUrl +")");
	
//	logoPlacer.css("background", "url('/res/img/comlogo.jpg') no-repeat center,	url('/res/img/darkdenim3.png') repeat");
//	logoPlacer.css("background-size","contain");
	
	if(basicInfo.logoUrl != null){
		logoPlacer.css("background", "url('/tcpFile/res_"+globalData.storeCode+"/"+basicInfo.logoUrl+"') no-repeat center,	url('/res/img/darkdenim3.png') repeat");
		logoPlacer.css("background-size","contain");
	} else {
		logoPlacer.css("background", "url('/res/img/darkdenim3.png') repeat");
		logoPlacer.css("background-size","contain");
	}
	
	//logoPlacer.css("background-position", "center");
	//logoPlacer.css("background-repeat","no-repeat");
	
	var liaElements = $('.namecard-contact-slot ul li a');
	
	var height = 280;
	
	liaElements[0].innerHTML = basicInfo.company;

	height+= basicInfo.company.length/25 * 10;
    	
	liaElements[1].innerHTML = basicInfo.homeUrl;
	
	var lengths = (basicInfo.homeUrl)?(basicInfo.homeUrl.length):(0);
	
	height+= lengths/75 * 10;
	
	liaElements[2].innerHTML = basicInfo.phone;
	
	$('#card').css("height", height+"px");
	
	$('.namecard-size-restrict').css("height", (height+30)+"px");
}

//initFunction();

function checkWhetherDataAreEmpty(datas){
	//if(!globalData.dayReport)
	//	return false;
	///try {
//	if(!globalData.dayReport)
	if(datas.dayArray[1].length==0 &&
			datas.pageArray[1].length==0 &&
			datas.couponArray[1].length == 0){ //왜 globalData를 검색하고 있었나..
		return false;
	} else 
		return true;
	///} catch(err){
		
		//여기서 이러한 에러를 잡은 것은, 구글 차트 로딩과 페이지 로딩 속도 차에 의거, 순서가 동기화되지 못하면,
		//globalDat가 없음에, 에러를 한번 먹고 다시 불러오도록 하기 위함이었다.
		/*setTimeout(function(){
			$.get('/analysis/dashboardRealTime.do', function(data){
				console.log(data, JSON.stringify(data));
				globalData = data;
			});
			drawCharts();//applyServerDataToSummaryTables(globalData);
		}, 500);
		return false;*/
		//location.reload();
	///}
}