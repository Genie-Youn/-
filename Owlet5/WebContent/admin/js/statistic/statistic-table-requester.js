/**
 *  This code for requesting chart data & getting 
 *  response of dat
 */

/*
 * google chart로 뿌렸었으나 지금은 c3.js(d3)로 뿌려줌. google chart는 맨 밑에 싸그리 주석
 */

  $(document).ready(function(){
	  var gotoMovers = $('.goto-mover');
	  var moverLength = gotoMovers.length;
	  var totalLength = (4+15)*moverLength;
	  
	  var wholeLength = $('#main_section').css('width');
	  
	  wholeLength = parseInt(wholeLength);
	  
	  $('.goto-mover-wrapper').css('width', totalLength);
	  $('.goto-mover-wrapper').css('left', (wholeLength/2 - totalLength/2));
	  
	  enrollChartSwitcher();
	  
	  setDatepicker();
	  
	  // 기본적으로 댓글 수의 주간 차트를 뿌려줄 것임
	  selectDateByWeek();
	  searchData();
  });
  
  function setDatepicker() {
	  $('.from-date-input').datepicker({
		  maxDate:"+0m +0w",
		  monthNames: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)',
			           '7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
			           monthNamesShort: ['1월','2월','3월','4월','5월','6월',
			           '7월','8월','9월','10월','11월','12월'],
			           dayNames: ['일','월','화','수','목','금','토'],
			           dayNamesShort: ['일','월','화','수','목','금','토'],
			           dayNamesMin: ['일','월','화','수','목','금','토'],
			           yearRange: 'c-99:c+99',
			           changeMonth: true,
			           changeYear: true,
			           dateFormat:'yy/mm/dd'
	  });
	  $('.to-date-input').datepicker({
		  maxDate:"+0m +0w",
		  monthNames: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)',
			           '7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
			           monthNamesShort: ['1월','2월','3월','4월','5월','6월',
			           '7월','8월','9월','10월','11월','12월'],
			           dayNames: ['일','월','화','수','목','금','토'],
			           dayNamesShort: ['일','월','화','수','목','금','토'],
			           dayNamesMin: ['일','월','화','수','목','금','토'],
			           yearRange: 'c-99:c+99',
			           changeMonth: true,
			           changeYear: true,
			           dateFormat:'yy/mm/dd'
	  });
	  
	  $('.from-date-input').datepicker("option", "onClose", function ( selectedDate ) {
		  if(selectedDate)
	        $(".to-date-input").datepicker( "option", "minDate", selectedDate );
		  
			  	$('.input-hidden').eq(0).val(selectedDate.replace('/','').replace('/','').substring(2));
			  	$('.input-hidden').eq(2).val(-1);
	    });
	  $('.to-date-input').datepicker("option", "onClose", function ( selectedDate ) {
		  if(selectedDate)
	        $(".from-date-input").datepicker( "option", "maxDate", selectedDate );
		  
			  $('.input-hidden').eq(1).val(selectedDate.replace('/','').replace('/','').substring(2));
			  $('.input-hidden').eq(2).val(-1);
	    });
	    //console.log($('.ui-datepicker .ui-datepicker-calendar tr').onmousemove);
	    
	    
	    //prevent default function from anything.
	    $('.date-span-btn').each(function(i){
	    	$(this).on('click', function(){
	    		arrayDateSelectFunc[i]();
	    		return false;
	    	});
	    });
	}
  
  //closure isn't needed.
  
function selectDateBySpan(){
	$('.calendar-locator').css('display', 'inline-block');
	$('.datespan-viewer').css('display', 'none');
	
	$('.input-hidden').eq(0).val($('.from-date-input').val().replace('/','').replace('/',''));
	$('.input-hidden').eq(1).val($('.to-date-input').val().replace('/','').replace('/',''));
  	$('.input-hidden').eq(2).val(-1);
}

Date.prototype.format = function(f) {
    if (!this.valueOf()) return " ";
 
    var weekName = ["일", "월", "화", "수", "목", "금", "토"];
    var d = this;
     
    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
            case "yyyy": return d.getFullYear();
            case "yy": return (d.getFullYear() % 1000).zf(2);
            case "MM": return (d.getMonth() + 1).zf(2);
            case "dd": return d.getDate().zf(2);
            case "E": return weekName[d.getDay()];
            case "HH": return d.getHours().zf(2);
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case "mm": return d.getMinutes().zf(2);
            case "ss": return d.getSeconds().zf(2);
            case "a/p": return d.getHours() < 12 ? "오전" : "오후";
            default: return $1;
        }
    });
};

Date.prototype.getLastDate = function(){
	switch(this.getMonth()){
		case 1: 
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:return 31;
		
		case 4:
		case 6:
		case 9:
		case 11: return 30;
	}
	
	if (((this.getYear() % 4 == 0) && (this.getYear() % 100 != 0)) || (this.getYear()% 400 == 0))
			return 29;
		else
			return 28;
};
 
String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
Number.prototype.zf = function(len){return this.toString().zf(len);};

/**
 * 오늘자 날짜 선택
 */
function selectDateByDay(){
	//$('.calendar-locator').css('display', 'none');
	$('.datespan-viewer').css('display', 'inline');
	$('.calendar-locator').css('display', 'inline-block');
	var Dates = new Date();
	
	$('.datespan-viewer').html(Dates.format("yyyy/MM/dd E"));
	
	setHiddenField([Dates.format('yyMMdd'), Dates.format('yyMMdd'), 0]);
	$('.from-date-input').val(Dates.format('yyyyMMdd'));
	$('.to-date-input').val(Dates.format('yyyyMMdd'));
}
/**
 * 주단위 날짜 선택
 */
function selectDateByWeek(){
	//$('.calendar-locator').css('display', 'none');
	$('.datespan-viewer').css('display', 'inline');
	$('.calendar-locator').css('display', 'inline-block');
	
	var Dates = new Date();
	
	var DatesAfter = new Date();
	DatesAfter.setDate(Dates.getDate()-7);
	
	var stringFormat = DatesAfter.format("yyyy/MM/dd E") + ' ~ ' + Dates.format("yyyy/MM/dd E");
	
	$('.datespan-viewer').html(stringFormat);
	
	setHiddenField([DatesAfter.format('yyMMdd'), Dates.format('yyMMdd'), 1]);
	$('.from-date-input').val(DatesAfter.format('yyyyMMdd'));
	$('.to-date-input').val(Dates.format('yyyyMMdd'));
}
/**
 * 월단위 날짜 선택 - 날짜 출력을 바꿔줍니다.
 */
function selectDateByMonth(){
	//$('.calendar-locator').css('display', 'none');
	$('.datespan-viewer').css('display', 'inline');
	$('.calendar-locator').css('display', 'inline-block');
	
var Dates = new Date();
	
	var DatesAfter = new Date();
	DatesAfter.setDate(Dates.getDate()-Dates.getLastDate());
	
	var stringFormat = DatesAfter.format("yyyy/MM/dd E") + ' ~ ' + Dates.format("yyyy/MM/dd E");
	
	$('.datespan-viewer').html(stringFormat);
	
	
	setHiddenField([DatesAfter.format('yyMMdd'), Dates.format('yyMMdd'), 2]);
	$('.from-date-input').val(DatesAfter.format('yyyyMMdd'));
	$('.to-date-input').val(Dates.format('yyyyMMdd'));
}

function searchData(){
	
//	var numSelected = -1;
//	$('.goto-mover').each(function(i, el){ if($(this).is('.selected-goto-mover')) numSelected = i;});
	var num = $('.moving-slot').index($('.selected-slot'));
	
	getStatisticDataFromServer($('.input-hidden').eq(0).val(),
			$('.input-hidden').eq(1).val(),
			$('.input-hidden').eq(2).val(),
			num,
			true);
}

var arrayDateSelectFunc = [/*selectDateBySpan, */selectDateByDay, selectDateByWeek, selectDateByMonth, searchData];

function enrollChartSwitcher(){
	var mover = $('div[class^="slot-"]');
	var lefter = mover.get(0);
	var righter = mover.get(1);
	
	var moveSpeed = 300;
	var slotWidth = parseInt($('.moving-slot').css('width'));

	var idx = $('.moving-slot').index($('.moving-slot.selected-slot'));
	$('.moving-slot').css({
		left : '+=' + (2 - idx) * slotWidth
	});

	$(lefter).bind('click', function(){
			var centerSlot = $('.selected-slot');
			var left = parseInt(centerSlot.css('left'));
			
			var classNames = centerSlot.attr('class');
			if(!classNames) {
				return;
			}
			var number = $('.moving-slot').index(centerSlot);
			
			if(number > 0){
				centerSlot.removeClass('selected-slot');
				$('.moving-slot').finish().animate({
						left : '+='+slotWidth
					}, moveSpeed,
					function() {
						var nowSelected = $('.moving-slot:nth-child(' + (number) + ')').addClass('selected-slot');
						
						var dataChartName = nowSelected.data('chart-name');
						$('.chart-title-container').html(dataChartName);
						//do request
//						chartChanger(parseInt(number)-1);
						getStatisticDataFromServer($('.input-hidden').eq(0).val(),
								$('.input-hidden').eq(1).val(),
								$('.input-hidden').eq(2).val(),
								parseInt(number)-1,
								false);
//						
//						if (configs[number - 1]) {
//							chart.destroy();
//							chart = Chart(configs[number - 1]);
//						}
					}
				);
				$('.selected-goto-mover').removeClass('selected-goto-mover').prev().addClass('selected-goto-mover');
			}
		}).next().bind('click', function(){
			var centerSlot = $('.selected-slot');
			var left = parseInt(centerSlot.css('left'));
			
			var classNames = centerSlot.attr('class');
			if(!classNames) {
				return;
			}
			var number = $('.moving-slot').index(centerSlot);
			
			if(number < slotNum - 1){ //갯수보다 더 많이 못 넘어가요. 오른쪽으로..
				centerSlot.removeClass('selected-slot');
				$('.moving-slot').finish().animate({
						left : '-='+slotWidth
					}, moveSpeed,
					function() {
						var nowSelected = $('.moving-slot:nth-child(' + (number + 2) + ')').addClass('selected-slot');
						
						var dataChartName = nowSelected.data('chart-name');
						$('.chart-title-container').html(dataChartName);
						//do request
//						chartChanger(parseInt(number)+1);
						getStatisticDataFromServer($('.input-hidden').eq(0).val(),
								$('.input-hidden').eq(1).val(),
								$('.input-hidden').eq(2).val(),
								parseInt(number)+1,
								false);
						
//						if (configs[number + 1]) {
//							chart.destroy();
//							chart = Chart(configs[number + 1]);
//						}
					}
				);
				$('.selected-goto-mover').removeClass('selected-goto-mover').next().addClass('selected-goto-mover');
			}
		});
	
		var gotoMovers = $('.goto-mover');
		gotoMovers.each(function(i){
			$(this).bind('click', function(e){
				$('.goto-mover').removeClass('selected-goto-mover');
				$(this).addClass('selected-goto-mover');
				var classNames = $(this).attr('class');
				if(!classNames) {
					return;
				}
				var number = classNames.split(' ')[1].substring(4);
				
				var nowLeft = parseInt($('.selected-slot').css('left'));
				var targetLeft = parseInt($('.slot'+number).css('left'));
				
				var moveDistance = nowLeft - targetLeft;
				
				$('.moving-slot').removeClass('selected-slot');
				
				$('.moving-slot').finish().animate({
						left:'+='+moveDistance+'px'
					}, moveSpeed*1.5,
					function(){
						//do request
						//chartChanger(number);
						getStatisticDataFromServer($('.input-hidden').eq(0).val(),
								$('.input-hidden').eq(1).val(),
								$('.input-hidden').eq(2).val(),
								number,
								false);
					}
				);
				var dataChartName = $('.slot'+number).addClass('selected-slot').data('chart-name');;
				$('.chart-title-container').html(dataChartName);
			});
		});
}

var slotNum = $('.moving-slot').length; //슬롯의 갯수를 구합니다. 위의 [] 모양 말이여. 밑의 함수서 슬롯 숫자 계산 시쓰여요.
var slotAnimatedCount = 0;

// c3 차트 만들때 필요한 config 객체 ( 필요한 대로 갖다 붙여 쓸 것임 )
var sizeConfig = {
	size: { width: 800, height: 450 }
};
var barConfig = {
	data: {
		type: 'bar'
	}
};
var pieConfig = {
	data: {
		type: 'pie'
	}
};
var lineConfig = {
	data: {
		type: 'line'
	}
};
var horizonConfig = {
	axis: {
		rotated: true
	}
};
var categoryConfig = {
	axis: {
		x: {
			type: 'category',
			tick: {
				format: null
			}
		}
	}
};

function grouping(report, xName, yName, xNameTo, yNameTo, option) {
	if (!Array.isArray(yName)) {
		yName = [yName], yNameTo = [yNameTo];	// multi-data에 대응
	}
	//var data = [['x']];
	var data = [[xNameTo]];
	var option = option || {}, toArray = option.toArray, avarage = option.avarage;
	
	var columnLength = yName.length;
	
	for (var i = 0; i < columnLength; i++) {
		data.push([yNameTo[i]]);
	}
	
	var last = 0, count = 0;
	for (var i = 0; i < report.length; i++) {
		var item = report[i];
		if (data[0][last] !== item[xName]) {
			data[0].push(item[xName]);
			for (var j = 0; j < columnLength; j++) {
				if (avarage && !isNaN(data[j + 1][last])) data[j + 1][last] /= count;
				data[j + 1].push(toArray ? [item[yName[j]]] : item[yName[j]]);
			}
			last++; count = 1;
		} else {
			if (toArray) {
				for (var j = 0; j < columnLength; j++) {
					data[j + 1][last].push(item[yName[j]]);
				}
			} else {
				for (var j = 0; j < columnLength; j++) {
					data[j + 1][last] += item[yName[j]];
				}
			}
			count++;
		}
	}
	for (var j = 0; j < columnLength; j++) {
		if (avarage && !isNaN(data[j + 1][last])) data[j + 1][last] /= count;
	}
	
	return data;
}

function counting(report, xName, yName) {
	var data = {
			x: [null]
	};
	
	var last = 1;
	for (var i = 0; i < report.length; i++) {
		var item = report[i], yKey = item[yName] ? item[yName] : yName;	// yName이 report내에 있는 키면 그 안의 값을 키값으로, 아니면 yName을 키값으로 데이터 카운팅
		if (data.x[last - 1] !== item[xName]) {
			data.x.push(item[xName]);
			last++;
		}
		
		if (!data[yKey]) {
			data[yKey] = [];
			for (var j = 0; j < last; j++) data[yKey][j] = 0;
			data[yKey][last - 1] = 1;
		} else {
			if (data[yKey][last - 1]) data[yKey][last - 1]++;
			else data[yKey][last - 1] = 1;
		}
	}
	
	var newData = [];
	for (var key in data) {
		data[key][0] = key;
		newData.push(data[key]);
	}
	
	if (!newData[1]) newData[1] = [yName];
	return newData;
}

var chartElem = document.createElement('div'), chart;

function chartChanger(index, jsonData) {
	var config;
	var data;

	switch(index){
		case 0:
			data = jsonData.dayReport;
			data = grouping(data, 'date', 'visitorCount', 'x', '방문자 수');
			config = Configger(chartElem, data).add(barConfig).add(sizeConfig);
			if (chart) {
				chart.destroy();
				chart = null;
			}
			chart = Chart(config);
			break;
		case 1:
			data = jsonData.dayReplyReport;
			data = grouping(data, 'date', ['starService', 'starMood', 'starGoods'], 'x', ['서비스', '분위기', '품질']);
			for (var i = 1; i < data.length; i++) {
				for (var j = 1; j <data[i].length; j++) {
					data[i][j] = data[i][j].toPrecision(2);
				}
			}
			
			config = Configger(chartElem, data).add({
				data: {
					type: 'area-spline',
					groups: [['서비스', '분위기', '품질']]
				},
				axis: {
					y: {
						max: 15
					}
				}
			}).add(sizeConfig);
			if (chart) {
				chart.destroy();
				chart = null;
			}
			chart = Chart(config);
			break;
		case 2:
			data = jsonData.dayReplyReport;
			data = grouping(data, 'date', 'replyCount', 'x', '댓글 수');
			config = Configger(chartElem, data).add(barConfig).add(sizeConfig);
			if (chart) {
				chart.destroy();
				chart = null;
			}
			chart = Chart(config);
			break;
		case 3:
			data = jsonData.productReplyReport;
			data = grouping(data, 'pdName', ['replyCount', 'pdPrice'], 'x', ['판매량', '매출']);
			for (var i = 1; i < data[1].length; i++) {
				data[2][i] *= data[1][i];
			}
			
			config = Configger(chartElem, data).add(barConfig).add(sizeConfig).add(categoryConfig).add({
				data: {
					types: {
						'판매량': 'bar',
						'매출': 'bar'
					},
					axes: {
						'판매량': 'y',
						'매출': 'y2'
					}
				},
				axis: {
					y2: {
						show: true
					}
				}
			});
			if (chart) {
				chart.destroy();
				chart = null;
			}
			chart = Chart(config);
			break;
			break;
	}
	$('.chart').html(chart.element);
}


function getStatisticDataFromServer(start, end, period, index, isSkip){
	
		// 이 함수는 콜백이라 무려 4번(여러번) 호출됩니다... 제길.
		if(!isSkip){
		
			slotAnimatedCount = (slotAnimatedCount + 1)%slotNum;
			
			if(slotAnimatedCount != slotNum-1){
				return;
			}
		
		}
	
	var url = '/analysis/reportList.do';
	var dataobject = {};
	if(period < 0){
		url = '/analysis/randomReportList.do';
		dataobject.startDate = start;
		dataobject.endDate = end;		
	} else {
		dataobject.date = end;
		dataobject.period = period;
	}
	
	
	//var returnedData = {};
	$.ajax({
		url : url,
		type : 'POST',
		data : dataobject,
		success : function (jsondata){
//			alert(jsondata);
			//returnedData = jsondata;
			chartChanger(index, jsondata);
		}
	});
	
	//return returnedData;
}

function setHiddenField(dataArray){
//	getStatisticDataFromServer($('.input-hidden').eq(0).val(),
//			$('.input-hidden').eq(1).val(),
//			$('.input-hidden').eq(2).val());
	///var numSelected = -1;
	///$('.goto-mover').each(function(i, el){ if($(this).is('.selected-goto-mover')) numSelected = i;});
	
	$('.input-hidden').each(function(index, element){
		$(this).val(dataArray[index]);
	});
	
	//chartChanger(numSelected+2, true);
	/*getStatisticDataFromServer($('.input-hidden').eq(0).val(),
			$('.input-hidden').eq(1).val(),
			$('.input-hidden').eq(2).val(),
			numSelected+2,
			true);*/
}

//////////////////////////////////////////////////////////////////////////////////////////////




///**
// *  This code for requesting chart data & getting 
// *  response of dat
// */
//
////$(document).ready(function(){
//	  google.load("visualization", "1", {packages:["table"]});
//	  
//	  
//	  $(document).ready(function(){
//		  google.setOnLoadCallback(drawTable);
//		  selectDateByDay();
//		  getStatisticDataFromServer($('.input-hidden').eq(0).val(),
//					$('.input-hidden').eq(1).val(),
//					$('.input-hidden').eq(2).val());
//	  });
//	  
//      var defaultData = {
//      		alternatingRowStyle : true,
//    		showRowNumber	: true,
//    		page			: 'enable',
//    		pageSize		: 30,
//    		cssClassNames	: {
//        			//headerRow: 'headerRow',
//        			//hoverTableRow: 'hoverTableRow', 
//        			//tableRow: 'tableRow',
//        			//oddTableRow: 'oddTableRow',
//					//selectedTableRow: 'selectedTableRow'
//    						  },
//    	};
//      function drawTables(dataArray) {
//          var data = new google.visualization.DataTable();
//          var oneRows = dataArray[1];
//          ddd = dataArray;
//          for(var i in oneRows){
//        	  //data.addColumn(typeof oneRows[i], oneRows[i]);
//        	  data.addColumn(typeof oneRows[i], dataArray[0][i]);
//          }
//          data.addRows(dataArray.slice(1));
//          var table = new google.visualization.Table(
//          		$('#table_viewer > div.table').get(0)
//          		);
//
//          table.draw(data, defaultData);
//          //$('.charts-inline-block.charts-custom-button.charts-custom-button-collapse-left').last().click();
//      }
//      function drawTable() {
//        var data = new google.visualization.DataTable();
//        data.addColumn('string', 'Name');
//        data.addColumn('number', 'Salary');
//        data.addColumn('boolean', 'Full Time Employee');
//        data.addRows(118);
//        
//        //tf 였다가 Nf였다가..
////        console.log(data);
////        for(var i=0; i < data.Nf.length ; i++){
////        	data.setCell(i , 0 , '홍' + i + '동' , null ,{'className':  'center-text google-visualization-table-td'});
////        	data.setCell(i , 1 , 10000+(i*100) , 10000+(i*100)+'원' ,{'className':  'center-text google-visualization-table-td'});
////        	data.setCell(i , 2 , true ,null , {'className':  'center-text google-visualization-table-td'});
////
////        }
//
//        var table = new google.visualization.Table(
//        		$('#table_viewer > div.table').get(0)
//        		);
//
////        table.draw(data, defaultData);
//        
//        google.visualization.events.addListener(table, 'sort', function(c, a, i){
//        });
//        
//        google.visualization.events.addListener(table, 'select', function(){
//        	
//        	$('.table table tr').filter(function(index){
//        		return !($(this).hasClass('oddTableRow') || $(this).hasClass('tableRow')) && !$(this).hasClass('headerRow');
//        	}).addClass(function(index){
//        		
//        		var prv = $(this).prev();
//        		
//        		if(prv.hasClass('headerRow') || prv.hasClass('oddTableRow'))
//        			return 'tableRow';
//        		else {
//        			return 'oddTableRow';
//        		}
//        	});
//        	
//        });
//        
//        $('.charts-inline-block.charts-custom-button.charts-custom-button-collapse-left').last().click();
//      }
//      google.load("visualization", "1", {packages:["corechart"]});
//      //google.setOnLoadCallback(drawChart);
//      function drawChart() {
//
//        var data = google.visualization.arrayToDataTable([
//          ['Year', 'Sales', 'Expenses', { role: 'style' } ],
//          ['2004',  1000,      400, 'width:10px'],
//          ['2005',  1170,      460, 'width:10px'],
//          ['2006',  660,       1120,'width:10px'],
//          ['2004',  1000,      400, 'width:10px'],
//          ['2005',  1170,      460, 'width:10px'],
//          ['2006',  660,       1120,'width:10px'],
//          ['2004',  1000,      400, 'width:10px'],
//          ['2005',  1170,      460, 'width:10px'],
//          ['2006',  660,       1120,'width:10px'],
//          ['2004',  1000,      400, 'width:10px'],
//          ['2005',  1170,      460, 'width:10px'],
//          ['2006',  660,       1120,'width:10px'],
//          ['2004',  1000,      400, 'width:10px'],
//          ['2005',  1170,      460, 'width:10px'],
//          ['2006',  660,       1120,'width:10px'],
//          ['2004',  1000,      400, 'width:10px'],
//          ['2005',  1170,      460, 'width:10px'],
//          ['2006',  660,       1120,'width:10px'],
//          ['2004',  1000,      400, 'width:10px'],
//          ['2005',  1170,      460, 'width:10px'],
//          ['2006',  660,       1120,'width:10px'],
//          ['2004',  1000,      400, 'width:10px'],
//          ['2005',  1170,      460, 'width:10px'],
//          ['2006',  660,       1120,'width:10px'],
//          ['2004',  1000,      400, 'width:10px'],
//          ['2005',  1170,      460, 'width:10px'],
//          ['2006',  660,       1120,'width:10px'],
//          ['2004',  1000,      400, 'width:10px'],
//          ['2005',  1170,      460, 'width:10px'],
//          ['2006',  660,       1120,'width:10px'],
//          ['2004',  1000,      400, 'width:10px'],
//          ['2005',  1170,      460, 'width:10px'],
//          ['2006',  660,       1120,'width:10px'],
//          ['2004',  1000,      400, 'width:10px'],
//          ['2005',  1170,      460, 'width:10px'],
//          ['2006',  660,       1120,'width:10px'],
//          ['2004',  1000,      400, 'width:10px'],
//          ['2005',  1170,      460, 'width:10px'],
//          ['2006',  660,       1120,'width:10px'],
//          ['2004',  1000,      400, 'width:10px'],
//          ['2005',  1170,      460, 'width:10px'],
//          ['2006',  660,       1120,'width:10px'],
//          ['2004',  1000,      400, 'width:10px'],
//          ['2005',  1170,      460, 'width:10px'],
//          ['2006',  660,       1120,'width:10px'],
//          ['2004',  1000,      400, 'width:10px'],
//          ['2005',  1170,      460, 'width:10px'],
//          ['2006',  660,       1120,'width:10px'],
//          ['2004',  1000,      400, 'width:10px'],
//          ['2005',  1170,      460, 'width:10px'],
//          ['2006',  660,       1120,'width:10px'],
//          ['2004',  1000,      400, 'width:10px'],
//          ['2005',  1170,      460, 'width:10px'],
//          ['2006',  660,       1120,'width:10px'],
//          ['2004',  1000,      400, 'width:10px'],
//          ['2007',  1030,      540, 'width:10px']
//        ]);
//
//        var options = {
//          title: 'Company Performance',
//          hAxis: {title: 'Year', titleTextStyle: {color: '#338434'}}
//        };
//
//        var chart = new google.visualization.ColumnChart($('#table_viewer .chart').get(0));
//
//        chart.draw(data, options);
//
//      }
//      
//  $(document).ready(function(){
//	  var gotoMovers = $('.goto-mover');
//	  var moverLength = gotoMovers.length;
//	  var totalLength = (4+15)*moverLength;
//	  
//	  var wholeLength = $('#main_section').css('width');
//	  
//	  wholeLength = parseInt(wholeLength);
//	  
//	  $('.goto-mover-wrapper').css('width', totalLength);
//	  $('.goto-mover-wrapper').css('left', (wholeLength/2 - totalLength/2));
//	  
//	  enrollChartSwitcher();
//	  
//	  setDatepicker();
//  });
//  
//  function setDatepicker() {
//	  $('.from-date-input').datepicker({
//		  maxDate:"+0m +0w",
//		  monthNames: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)',
//			           '7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
//			           monthNamesShort: ['1월','2월','3월','4월','5월','6월',
//			           '7월','8월','9월','10월','11월','12월'],
//			           dayNames: ['일','월','화','수','목','금','토'],
//			           dayNamesShort: ['일','월','화','수','목','금','토'],
//			           dayNamesMin: ['일','월','화','수','목','금','토'],
//			           yearRange: 'c-99:c+99',
//			           changeMonth: true,
//			           changeYear: true,
//			           dateFormat:'yy/mm/dd'
//	  });
//	  $('.to-date-input').datepicker({
//		  maxDate:"+0m +0w",
//		  monthNames: ['1월(JAN)','2월(FEB)','3월(MAR)','4월(APR)','5월(MAY)','6월(JUN)',
//			           '7월(JUL)','8월(AUG)','9월(SEP)','10월(OCT)','11월(NOV)','12월(DEC)'],
//			           monthNamesShort: ['1월','2월','3월','4월','5월','6월',
//			           '7월','8월','9월','10월','11월','12월'],
//			           dayNames: ['일','월','화','수','목','금','토'],
//			           dayNamesShort: ['일','월','화','수','목','금','토'],
//			           dayNamesMin: ['일','월','화','수','목','금','토'],
//			           yearRange: 'c-99:c+99',
//			           changeMonth: true,
//			           changeYear: true,
//			           dateFormat:'yy/mm/dd'
//	  });
//	  
//	  $('.from-date-input').datepicker("option", "onClose", function ( selectedDate ) {
//		  if(selectedDate)
//	        $(".to-date-input").datepicker( "option", "minDate", selectedDate );
//		  
//			  	$('.input-hidden').eq(0).val(selectedDate.replace('/','').replace('/',''));
//			  	$('.input-hidden').eq(2).val(-1);
//	    });
//	  $('.to-date-input').datepicker("option", "onClose", function ( selectedDate ) {
//		  if(selectedDate)
//	        $(".from-date-input").datepicker( "option", "maxDate", selectedDate );
//		  
//			  $('.input-hidden').eq(1).val(selectedDate.replace('/','').replace('/',''));
//			  $('.input-hidden').eq(2).val(-1);
//	    });
//	    console.log($('.ui-datepicker .ui-datepicker-calendar tr').onmousemove);
//	    
//	    
//	    //prevent default function from anything.
//	    $('.date-span-btn').each(function(i){
//	    	$(this).on('click', function(){
//	    		arrayDateSelectFunc[i]();
//	    		return false;
//	    	});
//	    });
//	}
//  
//  //closure isn't needed.
//  
//function selectDateBySpan(){
//	$('.calendar-locator').css('display', 'inline-block');
//	$('.datespan-viewer').css('display', 'none');
//	
//	$('.input-hidden').eq(0).val($('.from-date-input').val().replace('/','').replace('/',''));
//	$('.input-hidden').eq(1).val($('.to-date-input').val().replace('/','').replace('/',''));
//  	$('.input-hidden').eq(2).val(-1);
//}
//
//Date.prototype.format = function(f) {
//    if (!this.valueOf()) return " ";
// 
//    var weekName = ["일", "월", "화", "수", "목", "금", "토"];
//    var d = this;
//     
//    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
//        switch ($1) {
//            case "yyyy": return d.getFullYear();
//            case "yy": return (d.getFullYear() % 1000).zf(2);
//            case "MM": return (d.getMonth() + 1).zf(2);
//            case "dd": return d.getDate().zf(2);
//            case "E": return weekName[d.getDay()];
//            case "HH": return d.getHours().zf(2);
//            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
//            case "mm": return d.getMinutes().zf(2);
//            case "ss": return d.getSeconds().zf(2);
//            case "a/p": return d.getHours() < 12 ? "오전" : "오후";
//            default: return $1;
//        }
//    });
//};
//
//Date.prototype.getLastDate = function(){
//	switch(this.getMonth()){
//		case 1: 
//		case 3:
//		case 5:
//		case 7:
//		case 8:
//		case 10:
//		case 12:return 31;
//		
//		case 4:
//		case 6:
//		case 9:
//		case 11: return 30;
//	}
//	
//	if (((this.getYear() % 4 == 0) && (this.getYear() % 100 != 0)) || (this.getYear()% 400 == 0))
//			return 29;
//		else
//			return 28;
//};
// 
//String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;};
//String.prototype.zf = function(len){return "0".string(len - this.length) + this;};
//Number.prototype.zf = function(len){return this.toString().zf(len);};
//
///**
// * 오늘자 날짜 선택
// */
//function selectDateByDay(){
//	//$('.calendar-locator').css('display', 'none');
//	$('.datespan-viewer').css('display', 'inline');
//	$('.calendar-locator').css('display', 'inline-block');
//	var Dates = new Date();
//	
//	$('.datespan-viewer').html(Dates.format("yyyy/MM/dd E"));
//	
//	setHiddenField([Dates.format('yyyyMMdd'), Dates.format('yyyyMMdd'), 0]);
//	$('.from-date-input').val(Dates.format('yyyyMMdd'));
//	$('.to-date-input').val(Dates.format('yyyyMMdd'));
//}
///**
// * 주단위 날짜 선택
// */
//function selectDateByWeek(){
//	//$('.calendar-locator').css('display', 'none');
//	$('.datespan-viewer').css('display', 'inline');
//	$('.calendar-locator').css('display', 'inline-block');
//	
//	var Dates = new Date();
//	
//	var DatesAfter = new Date();
//	DatesAfter.setDate(Dates.getDate()-7);
//	
//	var stringFormat = DatesAfter.format("yyyy/MM/dd E") + ' ~ ' + Dates.format("yyyy/MM/dd E");
//	
//	$('.datespan-viewer').html(stringFormat);
//	
//	setHiddenField([DatesAfter.format('yyyyMMdd'), Dates.format('yyyyMMdd'), 1]);
//	$('.from-date-input').val(DatesAfter.format('yyyyMMdd'));
//	$('.to-date-input').val(Dates.format('yyyyMMdd'));
//}
///**
// * 월단위 날짜 선택 - 날짜 출력을 바꿔줍니다.
// */
//function selectDateByMonth(){
//	//$('.calendar-locator').css('display', 'none');
//	$('.datespan-viewer').css('display', 'inline');
//	$('.calendar-locator').css('display', 'inline-block');
//	
//var Dates = new Date();
//	
//	var DatesAfter = new Date();
//	DatesAfter.setDate(Dates.getDate()-Dates.getLastDate());
//	
//	var stringFormat = DatesAfter.format("yyyy/MM/dd E") + ' ~ ' + Dates.format("yyyy/MM/dd E");
//	
//	$('.datespan-viewer').html(stringFormat);
//	
//	
//	setHiddenField([DatesAfter.format('yyyyMMdd'), Dates.format('yyyyMMdd'), 2]);
//	$('.from-date-input').val(DatesAfter.format('yyyyMMdd'));
//	$('.to-date-input').val(Dates.format('yyyyMMdd'));
//}
//
//function searchData(){
//	
//	var numSelected = -1;
//	$('.goto-mover').each(function(i, el){ if($(this).is('.selected-goto-mover')) numSelected = i;});
//	
//	getStatisticDataFromServer($('.input-hidden').eq(0).val(),
//			$('.input-hidden').eq(1).val(),
//			$('.input-hidden').eq(2).val(),
//			numSelected+2,
//			true);
//}
//
//var arrayDateSelectFunc = [/*selectDateBySpan, */selectDateByDay, selectDateByWeek, selectDateByMonth, searchData];
//
//function enrollChartSwitcher(){
//	var mover = $('div[class^="slot-"]');
//	var lefter = mover.get(0);
//	var righter = mover.get(1);
//	
//	var moveSpeed = 300;
//	var slotWidth = parseInt($('.moving-slot').css('width'));
//	
//	$(lefter).bind('click', function(){
//			var centerSlot = $('.selected-slot');
//			var left = parseInt(centerSlot.css('left'));
//			
//			var classNames = centerSlot.attr('class');
//			if(!classNames) {
//				return;
//			}
//			var number = classNames.split(' ')[1].substring(4);
//			
//			if(number > 2){ //그저 2가 최소라고 생각하세요. 첫 슬롯 번호요.
//				centerSlot.removeClass('selected-slot');
//				$('.moving-slot').finish().animate({
//						left : '+='+slotWidth
//					}, moveSpeed,
//					function() {
//						$('.slot'+(number-1)).addClass('selected-slot');
//						
//						var dataChartName = $('.slot'+(number-1)).data('chart-name');
//						$('.chart-title-container').html(dataChartName);
//						//do request
////						chartChanger(parseInt(number)-1);
//						getStatisticDataFromServer($('.input-hidden').eq(0).val(),
//								$('.input-hidden').eq(1).val(),
//								$('.input-hidden').eq(2).val(),
//								parseInt(number)-1,
//								false);
//					}
//				);
//				$('.selected-goto-mover').removeClass('selected-goto-mover').prev().addClass('selected-goto-mover');
//			}
//		}).next().bind('click', function(){
//			var centerSlot = $('.selected-slot');
//			var left = parseInt(centerSlot.css('left'));
//			
//			var classNames = centerSlot.attr('class');
//			if(!classNames) {
//				return;
//			}
//			var number = classNames.split(' ')[1].substring(4);
//			
//			if(number < slotNum+1){ //갯수보다 더 많이 못 넘어가요. 오른쪽으로..
//				centerSlot.removeClass('selected-slot');
//				$('.moving-slot').finish().animate({
//						left : '-='+slotWidth
//					}, moveSpeed,
//					function() {
//						$('.slot'+(parseInt(number)+1)).addClass('selected-slot');
//						
//						var dataChartName = $('.slot'+(parseInt(number)+1)).data('chart-name');
//						$('.chart-title-container').html(dataChartName);
//						//do request
////						chartChanger(parseInt(number)+1);
//						getStatisticDataFromServer($('.input-hidden').eq(0).val(),
//								$('.input-hidden').eq(1).val(),
//								$('.input-hidden').eq(2).val(),
//								parseInt(number)+1,
//								false);
//					}
//				);
//				$('.selected-goto-mover').removeClass('selected-goto-mover').next().addClass('selected-goto-mover');
//			}
//		});
//	
//		var gotoMovers = $('.goto-mover');
//		gotoMovers.each(function(i){
//			$(this).bind('click', function(e){
//				$('.goto-mover').removeClass('selected-goto-mover');
//				$(this).addClass('selected-goto-mover');
//				var classNames = $(this).attr('class');
//				if(!classNames) {
//					return;
//				}
//				var number = classNames.split(' ')[1].substring(4);
//				
//				var nowLeft = parseInt($('.selected-slot').css('left'));
//				var targetLeft = parseInt($('.slot'+number).css('left'));
//				
//				var moveDistance = nowLeft - targetLeft;
//				
//				$('.moving-slot').removeClass('selected-slot');
//				
//				$('.moving-slot').finish().animate({
//						left:'+='+moveDistance+'px'
//					}, moveSpeed*1.5,
//					function(){
//						//do request
//						//chartChanger(number);
//						getStatisticDataFromServer($('.input-hidden').eq(0).val(),
//								$('.input-hidden').eq(1).val(),
//								$('.input-hidden').eq(2).val(),
//								number,
//								false);
//					}
//				);
//				var dataChartName = $('.slot'+number).addClass('selected-slot').data('chart-name');;
//				$('.chart-title-container').html(dataChartName);
//			});
//		});
//}
//
//var slotNum = $('.moving-slot').length; //슬롯의 갯수를 구합니다. 위의 [] 모양 말이여. 밑의 함수서 슬롯 숫자 계산 시쓰여요.
//var slotAnimatedCount = 0;
//
//
///**
// * 현재는 구글의 코드를 단지 복사 붙여넣기...
// * 
// * 그냥 switch 분기문입니다.
// * @param index
// * @param isFourNeglect : 이렇게 하긴 싫었는데 바빠서... 이거 존재하면 4횟수 무시합니다..
// */
//function chartChanger(index, jdfs){
//	
//	
//	var charts = ['column', 'histogram', 'donut', 'line', 'area'];
//	
////	var jsonDatafromServer = getStatisticDataFromServer($('.input-hidden').eq(0).val(),
////			$('.input-hidden').eq(1).val(),
////			$('.input-hidden').eq(2).val());
//	
////	var jdfs = jsonDatafromServer;
//	
//	console.log(jdfs);
//	
//	hhh = jdfs;
//	
//	var arraysOfDatas = [];
//	
//	
//	switch(index-1){ //왜 -1을 했냐고 묻는다면,,, 1 큰 값이 들어와서요.
//		case 1:
//			
//			arraysOfDatas.push(['날짜', '방문자수', '방문횟수']);
//			
//			$.each(jdfs.dayReport, function(index, element, arrays){
//				console.log(index, element, arrays);
//				arraysOfDatas.push([element.date, element.visitCount, element.visitorCount]);
//			});
//			
//			if(arraysOfDatas.length == 1){
//				
//				$('#table_viewer .chart').empty().html('<img src="/res/img/nodata.png">');
//				$('#table_viewer .chart').css('height', 400);
//				break;
//			}
//			
//			 var data = google.visualization.arrayToDataTable(
//					 //[
//			    /*['Year', 'Sales', 'Expenses'],
//			    ['2004',  1000,      400],
//			    ['2005',  1170,      460],
//			    ['2006',  660,       1120],
//			    ['2007',  1030,      540]*/
//			    arraysOfDatas
//			  //]
//			 );
//
//			  var options = {
//			    title: '방문자/방문횟수',
//			    hAxis: {title: '방문자/방문횟수', titleTextStyle: {color: 'red'}}
//			  };
//			  $('#table_viewer .chart').css('height', '400px');
//			  var chart = new google.visualization.ColumnChart($('#table_viewer .chart').get(0));
//
//			  chart.draw(data, options);
//			break;
//		case 2:
//			
//			arraysOfDatas.push(['Date',  '다운로드 수']);
//			
//			$.each(jdfs.dayReport, function(index, element, arrays){
//				arraysOfDatas.push([element.date, element.downCount]);
//			});
//			
//			if(arraysOfDatas.length == 1){
//				
//				$('#table_viewer .chart').empty().html('<img src="/res/img/nodata.png">');
//				$('#table_viewer .chart').css('height', 400);
//				break;
//			}
//			
//			var data = google.visualization.arrayToDataTable(
////
////              ['Director (Year)',  'Rotten Tomatoes', 'IMDB'],
////              ['Alfred Hitchcock (1935)', 8.4,         7.9],
////              ['Ralph Thomas (1959)',     6.9,         6.5],
////              ['Don Sharp (1978)',        6.5,         6.4],
////              ['James Hawes (2008)',      4.4,         6.2]
////            ]
//					arraysOfDatas
//              );
//
//            var options = {
//              title: '앱 다운로드 수',//'The decline of \'The 39 Steps\'',
////              vAxis: {title: 'Accumulated Rating'},
//              isStacked: true
//            };
//            $('#table_viewer .chart').css('height', '400px');
//            var chart = new google.visualization.SteppedAreaChart($('#table_viewer .chart').get(0));
//
//            chart.draw(data, options);
//			break;
//		case 3: 
//			
//			arraysOfDatas.push(['Date', 'Count']);
//			
//			$.each(jdfs.couponReport, function(index, element, arrays){
//				arraysOfDatas.push([element.date, element.count]);
//			});
//			
//			if(arraysOfDatas.length == 1){
//				
//				$('#table_viewer .chart').empty().html('<img src="/res/img/nodata.png">');
//				$('#table_viewer .chart').css('height', 400);
//				break;
//			}
//			
//			var data = google.visualization.arrayToDataTable(
////					[
////              ['Year', 'Sales', 'Expenses'],
////              ['2004',  1000,      400],
////              ['2005',  1170,      460],
////              ['2006',  660,       1120],
////              ['2007',  1030,      540]
////            ]
//					arraysOfDatas
//					);
//
//            var options = {
//              title: '쿠폰 사용 내역'
//            };
//            $('#table_viewer .chart').css('height', '400px');
//
//            var chart = new google.visualization.LineChart($('#table_viewer .chart').get(0));
//
//            chart.draw(data, options);
//			break;
//		case 4:
//			var tempMap = {};
//			arraysOfDatas.push(['Date', '1순위', '2순위', '3순위', '4순위', '5순위'/*, { role: 'annotation' }*/]);
//			var tableArrayOfDatas = [];
//			tableArrayOfDatas.push(['Date', '1순위', '이름', '2순위', '이름', '3순위', '이름', '4순위', '이름', '5순위', '이름']);
//			//해쉬 맵을 만든다.
//			$.each(jdfs.pageReport, function(index, element, arrays){
////				arraysOfDatas.push([element.date, element.count]);
//				var key = element.time.substring(0, 10);
//				if(key in tempMap){
//					tempMap[key].push(element);
//				} else {
//					tempMap[key] = new Array(element);
//				}
//			});
//			
//			var tempArray = [];
//			var tableTempArray = [];
//			for(var key in tempMap){
//				tempArray.push(key);
//				tableTempArray.push(key);
//				for(var i = 0; i < 5; i++){
//					tempArray.push((tempMap[key][i])?(tempMap[key][i].visitCount):(0));
//					tableTempArray.push((tempMap[key][i])?(tempMap[key][i].visitCount):(0));
//					tableTempArray.push((tempMap[key][i])?(tempMap[key][i].pageName):('페이지 없음'));
//				}
////				tempArray.push(tempMap[key][5]);
//				arraysOfDatas.push(tempArray);
//				tableArrayOfDatas.push(tableTempArray);
//				tempArray = [];
//				tableTempArray = [];
//			}
//			
//			console.log(arraysOfDatas);
//			console.log(tableArrayOfDatas);
//			
//			if(arraysOfDatas.length == 1){
//				
//				$('#table_viewer .chart').empty().html('<img src="/res/img/nodata.png">');
//				$('#table_viewer .chart').css('height', 400);
//				break;
//			}
//			
//	        var data = google.visualization.arrayToDataTable(
////	        		[
////	          ['Year', 'Sales', 'Expenses'],
////	          ['2013',  1000,      400],
////	          ['2014',  1170,      460],
////	          ['2015',  660,       1120],
////	          ['2016',  1030,      540]
////	        ]
//	        		arraysOfDatas
//	        		);
//
//	        var options = {
//	          title: 'Company Performance'
////	        	  ,
////	          hAxis: {title: 'Year',  titleTextStyle: {color: '#333'}},
////	          vAxis: {minValue: 0}
//	        };
//
////	        var chart = new google.visualization.AreaChart($('#table_viewer .chart').get(0));
////	        var chart = new google.visualization.ColumnChart($('#table_viewer .chart').get(0));
////	        chart.draw(data, options);
//	        var height = arraysOfDatas.length * 60;
//	        //console.log(height);
//	        if(height < 400)
//	        	$('#table_viewer .chart').css('height', '400px');
//	        else
//	        	$('#table_viewer .chart').css('height', height);
//	        
//                var chart = new google.visualization.BarChart($('#table_viewer .chart').get(0));
//    	        chart.draw(data, options);
//	        
//	        arraysOfDatas = tableArrayOfDatas;
//	        
//			break;
//		case 5://never used.. 삭제됐어..
//	        var data = google.visualization.arrayToDataTable([
//	          ['Task', 'Hours per Day'],
//	          ['Work',     11],
//	          ['Eat',      2],
//	          ['Commute',  2],
//	          ['Watch TV', 2],
//	          ['Sleep',    7]
//	        ]);
//
//	        var options = {
//	          title: 'My Daily Activities',
//	          pieHole: 0.4,
//	        };
//
//	        var chart = new google.visualization.PieChart($('#table_viewer .chart').get(0));
//	        chart.draw(data, options);
//			break;
//	}
//	
//	drawTables(arraysOfDatas);
//	
//}
//
//
//function getStatisticDataFromServer(start, end, period, index, isSkip){
//	
//		// 이 함수는 콜백이라 무려 4번(여러번) 호출됩니다... 제길.
//		
//		if(!isSkip){
//		
//			slotAnimatedCount = (slotAnimatedCount + 1)%slotNum;
//			
//			if(slotAnimatedCount != slotNum-1){
//				return;
//			}
//		
//		}
//	
//	var url = '/analysis/reportList.do';
//	var dataobject = {};
//	if(period < 0){
//		url = '/analysis/randomReportList.do';
//		dataobject.startDate = start;
//		dataobject.endDate = end;		
//	} else {
//		dataobject.date = end;
//		dataobject.period = period;
//	}
//	
//	
//	//var returnedData = {};
//	$.ajax({
//		url : url,
//		type : 'POST',
//		data : dataobject,
//		success : function (jsondata){
////			alert(jsondata);
//			console.log(jsondata);
//			//returnedData = jsondata;
//			chartChanger(index, jsondata);
//		}
//	});
//	
//	//return returnedData;
//}
//
//function setHiddenField(dataArray){
////	getStatisticDataFromServer($('.input-hidden').eq(0).val(),
////			$('.input-hidden').eq(1).val(),
////			$('.input-hidden').eq(2).val());
//	///var numSelected = -1;
//	///$('.goto-mover').each(function(i, el){ if($(this).is('.selected-goto-mover')) numSelected = i;});
//	
//	$('.input-hidden').each(function(index, element){
//		$(this).val(dataArray[index]);
//	});
//	
//	//chartChanger(numSelected+2, true);
//	/*getStatisticDataFromServer($('.input-hidden').eq(0).val(),
//			$('.input-hidden').eq(1).val(),
//			$('.input-hidden').eq(2).val(),
//			numSelected+2,
//			true);*/
//}