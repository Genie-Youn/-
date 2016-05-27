/* jang
 * c3 차트 기반 차트 생성
 * main.jsp의 차트 생성
 */

var Configger = (function() {
	var defaultConfig = {	// no specific configs for line
		data: {
			x: 'x',
			columns: [],
			//types: types,	// 'bar', 'area', 'area-spline', 'donut', ('line')
			//groups: [
			//    //['data1', 'data2'], ['data3', 'data4'] // example
			//]	// for stacked chart
		},
		axis: {
			x: {
				type: 'category',
				tick: {
					outer: false
				}
			},
			y: {
				tick: {
					//format: d3.format('d'),
					count: 6,
					outer: false
				},
				padding: { top: 0, bottom: 0 }
			}
		},
		grid: {
			y: {
				show: true
			}
		},
		legend: {
			show: false
		},
		bar: {	// for bar chart
			width: {
				ratio: 0.3,
				max: 30
			},
			zerobased: true
		},
		donut: {
			label: {
				show: false	// 도넛 차트 퍼센트 숨김
			}
		}
	};
	
	return function(addConfig, type) {
		var config = $.extend(true, {}, defaultConfig);
		config = $.extend(true, config, addConfig);
		console.dir(config);
//		var config = $.extend(true, addConfig, defaultConfig);
		
		return config;
	}
})();

var Chart = function(data, config) {
	config.data.columns = data;
	config.axis.y.min = 0;
	config.axis.y.max = config.axis.y.max || Math.ceil((d3.max(data[1].slice(1)) + 1) / 5) * 5;
	if (data[1].length === 1) {
		config.data.columns[0].push('No data');
		config.data.columns[1].push(0);
		config.axis.y.max = 5;
		// 데이터 없음
	}
	
	var chart = c3.generate(config);
	chart.owlet = {
		addColumn: function(data, type, color) {
			var colName = data[0],
				types = {}, colors = {};
				types[colName] = type, colors[colName] = color;
				
			console.dir(types);
			console.dir(colors);
				
			chart.load({
				columns: [ data ],
				types: types,
				colors: colors
			});
			
			var datum = chart.data.shown();
			var max = 0;
			for (var i = 0; i < datum.length; i++) {
				max = d3.max([max, d3.max(chart.data.values(datum[i].id))]);
				console.dir(chart.data.values(datum[i].id));
			}
			
			chart.axis.max({
				y: Math.ceil((max + 1) / 5) * 5
			});
			
			console.log(max);
			
			return chart.owlet;
		},
		removeColumn: function(colName) {
			chart.unload({
				ids: [ colName ]
			});
			return chart.owlet;
		}
	};
	
	return chart;
};

function parseChartData(data, key, axisName, parser) {	// 필요한 데이터만 뽑아 데이터 객체의 배열을 칼럼별 배열로 바꿈
	var column = [ axisName ];
	
	for (var i = 0; i < data.length; i++) {
		column.push(parser ? parser(data[i][key]) : data[i][key]);
	}

	return column;
}

var dateParser = function(x) {
	var parser = d3.time.format('%m. %d %a');
	return parser(new Date(x));
}

$(document).ready(function() {
	var visitorChart, visitChart, pageChart, downChart, useCouponChart, totalCouponChart;
	var visitorData, visitData, pageData, downData, useCouponData, totalCouponData;
	
	var chartElems = d3.selectAll('.c3chart');
	console.dir(chartElems);
	var summaryReport = {};
	
	setTimeout(function(){
		$.get('/analysis/dashboardRealTime.do', function(data){
			var dayReports = data.dayReport,
				pageReports = data.pageReport,
				couponReports = data.couponReport;

			console.dir(data);
			var parsedDateData = parseChartData(dayReports, 'date', 'x', dateParser);
			var parsedCouponDateData = parseChartData(couponReports, 'date', 'x', dateParser);
			var parsedUseCouponData = parseChartData(couponReports, 'count', '쿠폰 사용 수');
//			var parsedTotalCouponData = parseChartData(dayReports, 'couponCount', '전체 쿠폰 수');
//			var parsedRemainCouponData = parsedTotalCouponData.map(function(d, i) {
//				return d - parsedUseCouponData[i];
//			});
			var parsedRemainCouponData = ['남은 쿠폰 수', 10];	// 서버에서 전체 쿠폰수를 보내줘야 해
			
			visitorData = [
			    parsedDateData,
			    parseChartData(dayReports, 'visitorCount', '방문자 수')
			];
			visitData = [
			    parsedDateData,
			    parseChartData(dayReports, 'visitCount', '방문 횟수')
			];
			pageData = [
			    parseChartData(pageReports, 'pageName', 'x'),
			    parseChartData(pageReports, 'visitCount', '페이지 방문 수')
			];
			downData = [
			    parsedDateData,
			    parseChartData(dayReports, 'downCount', '앱 다운로드 수')
			];
			useCouponData = [
				parsedCouponDateData,
				parsedUseCouponData
			];
			totalCouponData = [
				parsedDateData,
				parsedUseCouponData,
				parsedRemainCouponData
			];
			
//			visitorData = [
//	            ['x', '10. 23 금', '10. 24 토', '10. 25 일', '10. 26 월', '10. 27 화', '10. 28 수', '10. 29 목'],
//	            ['방문자 수', 1, 2, 6, 8, 6, 5, 7]
//            ];
//			visitData = [
//	            ['x', '10. 23 금', '10. 24 토', '10. 25 일', '10. 26 월', '10. 27 화', '10. 28 수', '10. 29 목'],
//	            ['방문 횟수', 1, 2, 6, 8, 6, 5, 7]
//	        ];
//			pageData = [
//	            ['x', '환영', '메뉴', '오시는 길', 'undefined'],
//	            ['페이지 방문 수', 1, 2, 6, 8]
//            ];
//			downData = [
//	            ['x', '10. 23 금', '10. 24 토', '10. 25 일', '10. 26 월', '10. 27 화', '10. 28 수', '10. 29 목'],
//	            ['앱 다운로드 수', 1, 2, 6, 8, 6, 5, 7]
//            ];
//			useCouponData = [
//	            ['x', '10. 23 금', '10. 24 토', '10. 25 일', '10. 26 월', '10. 27 화', '10. 28 수', '10. 29 목'],
//	            ['쿠폰 사용 수', 1, 2, 6, 8, 6, 5, 7]
//            ];
//			totalCouponData = [
//			    ['x' , '10. 29 목'],
//	            ['쿠폰 사용 수', 7], 
//	            ['남은 쿠폰 수', 4]
//            ];
			
			
			var visitorConfig = Configger({
				bindto: chartElems[0][0],
				data: {
					types: {
						'방문자 수': 'bar'
					},
					colors: {
						'방문자 수': '#a7d4eb'
					}
				},
				legend: {
					show: false
				}
			});
			var visitConfig = Configger({
				bindto: chartElems[0][1],
				data: {
					types: {
						'방문 횟수': 'bar'
					},
					colors: {
						'방문 횟수': '#a7d4eb'
					}
				},
				legend: {
					show: false
				}
			});
			var pageConfig = Configger({
				bindto: chartElems[0][2],
				data: {
					types: {
						'페이지 방문 수': 'bar'
					},
					colors: {
						'페이지 방문 수': '#a7d4eb'
					}
				},
				legend: {
					show: false
				}
			});
			var downConfig = Configger({
				bindto: chartElems[0][3],
				data: {
					types: {
						'앱 다운로드 수': 'bar'
					},
					colors: {
						'앱 다운로드 수': '#a7d4eb'
					}
				},
				legend: {
					show: false
				}
			});
			var useCouponConfig = Configger({
				bindto: chartElems[0][4],
				data: {
					types: {
						'쿠폰 사용 수': 'bar'
					},
					colors: {
						'쿠폰 사용 수': '#a7d4eb'
					}
				},
				legend: {
					show: false
				}
			});
			var totalCouponConfig = Configger({
				bindto: chartElems[0][5],
				data: {
					types: {
						x: 'donut',
						'쿠폰 사용 수': 'donut',
						'남은 쿠폰 수': 'donut'
					},
					colors: {
						'쿠폰 사용 수': '#fed555',
						'남은 쿠폰 수': '#d8dde3'
					}
				},
				legend: {
					position: 'right'
				}
			});
			
			
			//visitorChart = Chart(visitorData, visitorConfig);			
			visitChart = Chart(visitData, visitConfig);
			pageChart = Chart(pageData, pageConfig);
			downChart = Chart(downData, downConfig);
			useCouponChart = Chart(useCouponData, useCouponConfig);
			totalCouponChart = Chart(totalCouponData, totalCouponConfig);
			
			totalCouponChart.resize({ width: 350, height: 170 });
			
			
			// 도넛 차트 가운데 텍스트를 남은 쿠폰 수로 적용
			var useCouponCount = totalCouponData[1][1]
			var remainCouponCount = totalCouponData[2][1];
			var donutTitle = d3.select(totalCouponChart.element).select('.c3-chart-arcs-title');
			if (+donutTitle.text() !== useCouponCount) {
				donutTitle.text(useCouponCount).attr('opacity', 0)
				.transition().duration(1000).attr('opacity', 1);
			}
			
			// 도넛 차트 레전드에 10개 이런식으로 붙여줌
			var donutUseLegend = d3.select(totalCouponChart.element).select('.c3-legend-item-쿠폰-사용-수 text');
			var donutRemainLegend = d3.select(totalCouponChart.element).select('.c3-legend-item-남은-쿠폰-수 text');
			donutUseLegend.text(donutUseLegend.text() + ' : ' + useCouponCount + '개');
			donutRemainLegend.text(donutRemainLegend.text() + ' : ' + remainCouponCount + '개');
		});
	}, 0);
	
	var tempChart;
	var tempConfig;
	var tempData = [
	                //['x', '11. 1. 일', '11. 2. 월'],
	                ['x', '2015-11-1', '2015-11-2'],
	                ['맛', 3.8, 4.0],
	                ['위생', 5, 3],
	                ['분위기', 4, 4.3]
	                ];
	
	tempConfig = Configger({
		bindto: chartElems[0][0],
		data: {
			x: 'x',
			type: 'area-spline',
			groups: [
			         ['맛', '위생', '분위기']
			         ],
			columns: tempData
		},
		axis: {
			x: {
				type: 'timeseries',
				tick: {
					format: '%m. %d. %a'
				}
			},
			y: {
				max: 15
			}
		}
	});
	tempChart = Chart(tempData, tempConfig);
	
//	setInterval(function(){
//		$.get('/analysis/dashboardRealTime.do', function(data){
//			var dayReports = data.dayReport,
//				pageReports = data.pageReport;
//			
//			var parsedDateData = parseChartData(dayReports, 'date', 'x', dateParser);
//			var parsedUseCouponData = parseChartData(dayReports, 'useCouponCount', '쿠폰 사용 수');
//			var parsedTotalCouponData = parseChartData(dayReports, 'couponCount', '전체 쿠폰 수');
//			var parsedRemainCouponData = parsedTotalCouponData.map(function(d, i) {
//				return d - parsedUseCouponData[i];
//			})[0] = '남은 쿠폰 수';
//			
//			visitorData = [
//			    parsedDateData,
//			    parseChartData(dayReports, 'visitorCount', '방문자 수')
//			];
//			visitData = [
//			    parsedDateData,
//			    parseChartData(dayReports, 'visitCount', '방문 횟수')
//			];
//			pageData = [
//			    parseChartData(pageReports, 'pageName', 'x'),
//			    parseChartData(pageReports, 'visitCount', '페이지 방문 수')
//			];
//			downData = [
//			    parsedDateData,
//			    parseChartData(dayReports, 'downCount', '앱 다운로드 수')
//			];
//			useCouponData = [
//				parsedDateData,
//				parsedUseCouponData
//			];
//			totalCouponData = [ // useCouponCount, couponCount는 누적인가 당일인가 (당일이겠지?)
//				parsedDateData,
//				parsedUseCouponData,
//				parsedRemainCouponData
//			];
//			
//			visitorData = [
//	            ['x', '10. 23 금', '10. 24 토', '10. 25 일', '10. 26 월', '10. 27 화', '10. 28 수', '10. 29 목'],
//	            ['방문자 수', 1, 2, 6, 8, 6, 5, 7]
//            ];
//			visitData = [
//	            ['x', '10. 23 금', '10. 24 토', '10. 25 일', '10. 26 월', '10. 27 화', '10. 28 수', '10. 29 목'],
//	            ['방문 횟수', 1, 2, 6, 8, 6, 5, 7]
//	        ];
//			pageData = [
//	            ['x', '환영', '메뉴', '오시는 길', 'undefined'],
//	            ['페이지 방문 수', 1, 2, 6, 8]
//            ];
//			downData = [
//	            ['x', '10. 23 금', '10. 24 토', '10. 25 일', '10. 26 월', '10. 27 화', '10. 28 수', '10. 29 목'],
//	            ['앱 다운로드 수', 1, 2, 6, 8, 6, 5, 7]
//            ];
//			useCouponData = [
//	            ['x', '10. 23 금', '10. 24 토', '10. 25 일', '10. 26 월', '10. 27 화', '10. 28 수', '10. 29 목'],
//	            ['쿠폰 사용 수', 1, 2, 6, 8, 6, 5, 7]
//            ];
//			totalCouponData = [
//	            ['x', '10. 23 금', '10. 24 토', '10. 25 일', '10. 26 월', '10. 27 화', '10. 28 수', '10. 29 목'],
//	            ['쿠폰 사용 수', 1, 2, 6, 8, 6, 5, 7],
//	            ['남은 쿠폰 수', 0, 2, 2, 3, 4, 1, 4]
//            ];
//			
//			visitorChart.load({
//				columns: visitorData
//			});
//			visitChart.load({
//				columns: visitData
//			});
//			pageChart.load({
//				columns: pageData
//			});
//			downChart.load({
//				columns: downData
//			});
//			useCouponChart.load({
//				columns: useCouponData
//			});
//			totalCouponChart.load({
//				columns: totalCouponData
//			});
//			
//			// 도넛 차트 가운데 텍스트를 남은 쿠폰 수로 적용
//			var donutTitleData = d3.max(totalCouponData[2].slice(1));
//			var donutTitle = totalCouponChart.owlet.select('.c3-chart-arcs-title');
//			if (+donutTitle.text() !== donutTitleData) {
//				donutTitle.text(donutTitleData).attr('opacity', 0)
//				.transition().duration(1000).attr('opacity', 1);
//			}
//			// 도넛 차트 퍼센트 텍스트가 갱신될 때 같은 값임에도 불필요하게 애니메이션이 일어남 
//		});
//	}, 3000);
	
	setTimeout(function(){
		$.get('/analysis/summaryInfos.do', function(data){
			summaryReport = data;
			updateSummaryReport(summaryReport);
		});
		setInterval(function(){
			$.get('/analysis/summaryInfos.do', function(data){
				summaryReport = data;
				updateSummaryReport(summaryReport);
			});
		}, 3000);	
	}, 0);
});

function updateSummaryReport(data) {
	$('.visit-summary').find('.tile-status').html(data.visitorCount);
	$('.visits-summary').find('.tile-status').html(data.visitCount);
	$('.download-summary').find('.tile-status').html(data.downCount);
	$('.coupon-summary').find('.tile-status').html(data.couponCount);
	$('.event-summary').find('.tile-status').html(data.eventCount);
}