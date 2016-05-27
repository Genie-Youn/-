/*
 * Statistic.jsp의 차트 관련 파일이었으나 원래 그 용도이던 statistic-table-requester.js로 대체. 이건 안 쓰고 잇음
 */

var chartElem = document.createElement('div');

//var visitorData = [['x', '2015-10-19', '2015-10-20', '2015-10-21', '2015-10-22', '2015-10-23', '2015-10-24', '2015-10-25', '2015-10-26', '2015-10-27', '2015-10-28', '2015-10-29', '2015-10-30', '2015-10-31', '2015-11-01', '2015-11-02', '2015-11-03', '2015-11-04'],
//                   ['방문자 수', 3, 5, 1, 6, 8, 4, 10, 8, 15, 17, 14, 20, 21, 21, 25, 24, 30]];
//var starData = [['x', '2015-10-19', '2015-10-20', '2015-10-21', '2015-10-22', '2015-10-23', '2015-10-24', '2015-10-25', '2015-10-26', '2015-10-27', '2015-10-28', '2015-10-29', '2015-10-30', '2015-10-31', '2015-11-01', '2015-11-02', '2015-11-03', '2015-11-04'],
//                ['서비스', 3, 3.1, 3.21, 3.21, 3.2, 3.18, 3.2, 3.5, 3.51, 3.6, 3.8, 3.82, 3.85, 3.89, 3.9, 3.95, 4],
//                ['분위기', 4.1, 3.2, 3.21, 3.21, 3.2, 3.18, 3.2, 3.5, 3.51, 3.6, 3.8, 3.82, 3.85, 3.89, 3.9, 3.95, 4],
//				['품질', 3, 3.1, 3.21, 3.21, 3.2, 3.18, 3.2, 3.5, 3.51, 3.6, 3.8, 3.82, 3.85, 3.89, 3.9, 3.95, 4]];
//var replyData = [['x', '2015-10-19', '2015-10-20', '2015-10-21', '2015-10-22', '2015-10-23', '2015-10-24', '2015-10-25', '2015-10-26', '2015-10-27', '2015-10-28', '2015-10-29', '2015-10-30', '2015-10-31', '2015-11-01', '2015-11-02', '2015-11-03', '2015-11-04'],
//                   ['댓글 수', 3, 4, 1, 5, 4, 3, 10, 7, 13, 15, 12, 19, 16, 10, 23, 20, 23]];
//var couponData = [['x', '2015-10-19', '2015-10-20', '2015-10-21', '2015-10-22', '2015-10-23', '2015-10-24', '2015-10-25', '2015-10-26', '2015-10-27', '2015-10-28', '2015-10-29', '2015-10-30', '2015-10-31', '2015-11-01', '2015-11-02', '2015-11-03', '2015-11-04'],
//                   ['쿠폰', 3, 5, 1, 6, 8, 4, 10, 8, 15, 17, 14, 20, 21, 21, 25, 24, 30]];

var jsonData;

var visitorData = [['x'], ['방문자 수']];
var starData = [['x'], ['서비스'], ['분위기'], ['품질']];
var saleCountData = [['x'], ['상품 판매량']];
var replyData = [['x'], ['댓글']];
var replyCountData = [['x'], ['댓글 수']];

function addDayToRaw(report) {
	$.each(report, function(idx, item) {
		var day = new Date(item.date).getDay();
		switch (day) {
		case 0: item.day = '일요일'; break;
		case 1: item.day = '월요일'; break;
		case 2: item.day = '화요일'; break;
		case 3: item.day = '수요일'; break;
		case 4: item.day = '목요일'; break;
		case 5: item.day = '금요일'; break;
		case 6: item.day = '토요일'; break;
		}
	});
}

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

var parseDateToDay = function(data) {
	var parsedData = [['x', '일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일']];
	
	for (var i = 1; i < data.length; i++) {
		parsedData.push([data[i][0]]);
		for (var j = 1; j < data[i].length; j++) {
			var day = new Date(data[0][j]).getDay() + 1;
			if (parsedData[i][day] === undefined) parsedData[i][day] = data[i][j];
			else parsedData[i][day] += data[i][j];
		}
	}
	
	return parsedData;
}

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

var visitorConfig;
var starConfig;
var replyConfig;

var configs;
var chart;

//var visitorControl = {
//	'날짜별': {
//		val: {
//			data: {
//				columns: visitorData
//			}
//		},
//		'막대 그래프': {
//			val: barConfig
//		},
//		'꺾은선 그래프': {
//			val: lineConfig
//		}
//	},
//	'요일별': {
//		val: {
//			data: {
//				rows: parseDateToDay(visitorData)
//			},
//			axis: {
//				x: {
//					type: 'category',
//					tick: {
//						format: null
//					}
//				}
//			}
//		},
//		'막대 그래프': {
//			val: barConfig
//		},
//		'파이 그래프': {
//			val: pieConfig
//		}
//	}
//};
//var starControl = {
//	'날짜별': {
//		val: {
//			data: {
//				columns: starData
//			}
//		},
//		'막대 그래프': {
//			val: barConfig
//		},
//		'꺾은선 그래프': {
//			val: lineConfig
//		}
//	},
//	'요소별': {
//		val: {
//			data: {
//				rows: starData
//			},
//			axis: {
//				x: {
//					type: 'category',
//					tick: {
//						format: null
//					}
//				}
//			}
//		},
//		'막대 그래프': {
//			val: barConfig
//		},
//		'파이 그래프': {
//			val: pieConfig
//		}
//	}
//};
//var replyControl = {
//	'날짜별': {
//		val: {
//			data: {
//				columns: replyData
//			}
//		},
//		'막대 그래프': {
//			val: barConfig
//		},
//		'꺾은선 그래프': {
//			val: lineConfig
//		}
//	},
//	'요일별': {
//		val: {
//			data: {
//				rows: parseDateToDay(replyData)
//			},
//			axis: {
//				x: {
//					type: 'category',
//					tick: {
//						format: null
//					}
//				}
//			}
//		},
//		'막대 그래프': {
//			val: barConfig
//		},
//		'파이 그래프': {
//			val: pieConfig
//		}
//	}
//};
//
//function controller(config, Control) {
//	var selects = [];
//	var newConfig = Configger(chartElem).add(sizeConfig);
//	//config = Configger(chartElem);
//	function inner(innerControl) {
//		var select = $('<select></select>');
//		//select.innerVar = innerControl;
//		selects.push(select);
//		var endFlag = true;
//		for (var key in innerControl) {
//			if (key !== 'val') {
//				var option = $('<option></option>').val(key).text(key);
//				select.append(option);
//				endFlag = false;
//			}
//		}
//		if (endFlag) {
//			var submit = $('<input></input>');
//			submit.on('click', function(e) {
//				e.preventDefault();
//				var innerVar = Control;
//				$.each(selects, function(index, item) {
//					innerVar = innerVar[item.val()];
//					if (innerVar) {
//						newConfig = newConfig.add(innerVar.val);
//					}
//				});
//				console.dir(newConfig);
//				chart.destroy();
//				chart = Chart(newConfig);
//			});
//			$('.table').append(submit);
//		} else {
//			$('.table').append(select);
//			select.on('change', function() {
//				var innerVar= innerControl[$(this).val()];
//				config.add(innerVar.val);
//				inner(innerVar);
//			});
//		}
//	}
//	
//	inner(Control);
//	return selects;
//}

function reportHandler(data) {
	console.log('Data recieved');
	console.dir(data);
	
	jsonData = data;
	
	visitorData = grouping(data.dayReport, 'date', 'visitorCount', 'x', '방문자 수');
	starData = grouping(data.replyReport, 'date', ['starService', 'starMood', 'starGoods'], 'x', ['서비스', '분위기', '품질'], {avarage: true});
	replyData = counting(data.replyReport, 'date', '댓글 수');
	
	visitorConfig = Configger(chartElem, visitorData).add(sizeConfig)
					.add(barConfig);
	starConfig = Configger(chartElem, starData, {
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
	replyConfig = Configger(chartElem, replyData).add(sizeConfig)
				.add(barConfig);
	
	configs = [visitorConfig.add(barConfig), starConfig, replyConfig];
	
	configs[0] = configs[0].add({
		data: {
			columns: visitorData
		}
	});
	configs[1] = configs[1].add({
		data: {
			columns: starData
		}
	});
	configs[2] = configs[2].add({
		data: {
			columns: replyData
		}
	});
	
	var number = $('.moving-slot').index($('.selected-slot'));
	if (chart) {
		chart.destroy();
		chart = null;
	}

	chart = Chart(configs[number]);
	
	$('.chart').html(chart.element);
}

$(document).ready(function() {
	console.log(new Date(''));
	$.post('/analysis/reportList.do', { date: $.datepicker.formatDate('ymmdd', new Date()), period: 1 }, reportHandler);
	slotNum = $('.moving-slot').length; //슬롯의 갯수를 구합니다. 위의 [] 모양 말이여. 밑의 함수서 슬롯 숫자 계산 시쓰여요.

//	enrollChartSwitcher();
//	
//	$('#dataSelector').on('submit', function(e) {
//		e.preventDefault();
//		
//		//console.log($('.from-date-input').val() + ' ' + $('.to-date-input').val());
//		var startDate = $('.from-date-input').val(), endDate = $('.to-date-input').val();
//		if (new Date(startDate) === 'Invalid Date' && new Date(endDate) === 'Invalid Date') {
//			$.post('/analysis/randomReportList.do', { startDate: startDate, endDate: endDate }, reportHandler);
//		}
//	});
//	
//	
//	var periodSelectBtn = $('button.date-span-btn');
//	periodSelectBtn.on('click', function(e) {
//		e.preventDefault();
//
//		var period = periodSelectBtn.index($(this));
//		console.log(period);
//		$.post('/analysis/reportList.do', { date: $.datepicker.formatDate('ymmdd', new Date()), period: period }, reportHandler);
//	});
});

function enrollChartSwitcher(){
	var mover = $('div[class^="slot-"]');
	var lefter = mover.get(0);
	var righter = mover.get(1);
	
	var moveSpeed = 300;
	var slotWidth = parseInt($('.moving-slot').css('width'));
	
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
//						getStatisticDataFromServer($('.input-hidden').eq(0).val(),
//								$('.input-hidden').eq(1).val(),
//								$('.input-hidden').eq(2).val(),
//								parseInt(number)-1,
//								false);
						if (configs[number - 1]) {
							chart.destroy();
							chart = Chart(configs[number - 1]);
						}
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
//						getStatisticDataFromServer($('.input-hidden').eq(0).val(),
//								$('.input-hidden').eq(1).val(),
//								$('.input-hidden').eq(2).val(),
//								parseInt(number)+1,
//								false);
						if (configs[number + 1]) {
							chart.destroy();
							chart = Chart(configs[number + 1]);
						}
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