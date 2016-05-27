/* jang
 * c3 차트 기반 차트 생성
 */

var dateParser = function(d) {
	var output = '';
	output += (d.getMonth() + 1) + '. ';
	output += d.getDate() + '.*';
	switch (d.getDay()) {
	case 0: output += '일'; break;
	case 1: output += '월'; break;
	case 2: output += '화'; break;
	case 3: output += '수'; break;
	case 4: output += '목'; break;
	case 5: output += '금'; break;
	case 6: output += '토'; break;
	}
	output += '요일';
	return output;
};

var Configger = function(bindto, data, addConfig) {
	var defaultConfig = {	// no specific configs for line
		bindto: bindto,
		data: {
			x: 'x',
			columns: data,
			//types: types,	// 'bar', 'area', 'area-spline', 'donut', ('line')
			//groups: [
			//    //['data1', 'data2'], ['data3', 'data4'] // example
			//]	// for stacked chart
		},
		axis: {
			x: {
				type: 'timeseries',
				tick: {
					format: dateParser,
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
			},
			y2: {
				tick: {
					count: 6,
					outer: false
				}
			},
			rotated: false
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
//		donut: {
//			label: {
//				show: false	// 도넛 차트 퍼센트 숨김
//			}
//		}
		add: function(_config) {
			var newConfig = $.extend(true, {}, this);
			return $.extend(true, newConfig, _config);
		}
	};
	
	//defaultConfig = $.extend(true, defaultConfig, addDefaultConfig);
	
	var config = $.extend(true, {}, defaultConfig);
	config = $.extend(true, config, addConfig);
	
	return config;
};


var Chart = function(config) {
	var data = config.data.columns || config.data.rows;
//	if (!data[1]) data[1] = [' ', 0];
	config.axis.y.min = 0;
	var newYmax = Math.ceil(d3.max(data[1].slice(1)) / 5) * 5;
	if (newYmax >= 1000) newYmax = Math.ceil(newYmax / 1000) * 1000;
	if (!config.data.groups) {
		config.axis.y.max = newYmax;
	}
	if (config.axis.y2.show === true) {
		var newY2max = Math.ceil(d3.max(data[2].slice(1)) / 5) * 5;
		if (newY2max >= 1000) newY2max = Math.ceil(newY2max / 1000) * 1000;
		//console.log(newY2max);
		config.axis.y2.max = newY2max;
	}
	
	if (data[1].length === 1) {
//		config.data.columns[0].push('No data');
//		config.data.columns[1].push(0);
		config.axis.y.max = 5;
		// 데이터 없음
	}
	
	var chart = c3.generate(config);
	
	chart.refresh = function() {
		//chart.flush();
		if (config.axis.x.type == 'timeseries') {
			d3.select(chart.element).selectAll('.c3-axis-x').selectAll('.tick').selectAll('text').each(function() {
				var elem = d3.select(this)
				var texts = elem.text();
				texts = texts.split('*');
				
				elem.text('');
				for (var i = 0; i < texts.length; i++) {
					var tspan = elem.append('tspan').text(texts[i]);
					if (i > 0)
			            tspan.attr('x', 0).attr('dy', '15');
					else tspan.attr('dy', '5');
				}
			});
		}
		return chart;
	}
	
	chart.refresh();
	
//	chart.owlet = {
//		config: config,
//		addColumn: function(data, type, color) {
//			var colName = data[0],
//				types = {}, colors = {};
//				types[colName] = type, colors[colName] = color;
//				
//			chart.load({
//				columns: [ data ],
//				types: types,
//				colors: colors
//			});
//			
//			chart.owlet.refresh();
//			
//			return chart.owlet;
//		},
//		removeColumn: function(colName) {
//			chart.unload({
//				ids: [ colName ]
//			});
//			
//			chart.owlet.refresh();
//			
//			return chart.owlet;
//		},
//		refresh: function() {
//			var datum = chart.data.shown();
//			var max = 0;
//			for (var i = 0; i < datum.length; i++) {
//				max = d3.max([max, d3.max(chart.data.values(datum[i].id))]);
//				//console.dir(chart.data.values(datum[i].id));
//			}
//			
////			chart.axis.max({
////				y: Math.ceil((max + 1) / 5) * 5
////			});
//			
//			if (config.axis.x.type == 'timeseries') {
//				d3.select(chart.element).selectAll('.c3-axis-x').selectAll('.tick').selectAll('text').each(function() {
//					var elem = d3.select(this)
//					var texts = elem.text();
//					texts = texts.split('*');
//					
//					elem.text('');
//					for (var i = 0; i < texts.length; i++) {
//						var tspan = elem.append('tspan').text(texts[i]);
//						if (i > 0)
//				            tspan.attr('x', 0).attr('dy', '15');
//						else tspan.attr('dy', '5');
//					}
//				});
//			}
//		}
//	};
//	
//	chart.owlet.refresh();
	return chart;
};

function parseChartData(data, key, axisName, parser) {	// 필요한 데이터만 뽑아 데이터 객체의 배열을 칼럼별 배열로 바꿈
	var column = [ axisName ];
	
	for (var i = 0; i < data.length; i++) {
		column.push(parser ? parser(data[i][key]) : data[i][key]);
	}

	return column;
}