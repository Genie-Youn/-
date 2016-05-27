$(document).ready(function() {
	initCharts();
	$.get('/analysis/dashboardRealTime.do', drawCharts);
	setInterval(function() {
		$.get('/analysis/dashboardRealTime.do', updateCharts);
	}, 5000);
	
	$.get('/analysis/summaryInfos.do', updateSummaryInfos);
	setInterval(function() {
		$.get('/analysis/summaryInfos.do', updateSummaryInfos);
	}, 5000);
});

//c3 차트 만들때 필요한 config 객체 ( 필요한 대로 갖다 붙여 쓸 것임 )
var sizeConfig = {
	size: { height: 200 }
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

var chartElems, charts = [], configs = [];

function initCharts() {
	chartElems = document.getElementsByClassName('chart');
}

function parseDashboardData(jsonData) {
	var datas = [];
	
	datas[0] = grouping(jsonData.dayReport, 'date', 'visitorCount', 'x', '방문자 수');
	datas[1] = grouping(jsonData.dayReplyReport, 'date', ['starService', 'starMood', 'starGoods'], 'x', ['서비스', '분위기', '품질']);
	datas[2] = grouping(jsonData.dayReplyReport, 'date', 'replyCount', 'x', '댓글 수');
	
	for (var i = 0; i < jsonData.productReplyReport.length; i++) {
		jsonData.productReplyReport[i].pdPrice *= jsonData.productReplyReport[i].replyCount;
	}
	datas[3] = grouping(jsonData.productReplyReport, 'pdName', ['replyCount', 'pdPrice'], 'x', ['판매량', '매출']);
	
	var data = datas[1];
	for (var i = 1; i < data.length; i++) {	// 별점 데이터 소수점 첫째 자리까지
		for (var j = 1; j <data[i].length; j++) {
			data[i][j] = data[i][j].toPrecision(2);
		}
	}
	
	return datas;
}

function drawCharts(jsonData) {
	var datas = parseDashboardData(jsonData);
	
	//datas[0] = [['x', '2015-11-10', '2015-11-11'], ['방문자 수', 10, 20]];
	
	for (var i = 1; i < datas[0][0].length; i++) {
		datas[0][0][i] = new Date(datas[0][0][i]).format('MM/dd E');
	}
	
	configs[0] = Configger(chartElems[0]).add(sizeConfig).add(pieConfig).add(categoryConfig).add({
		data: {
			//rows: [['x', '2015-11-10', '2015-11-11'], ['방문자 수', 10, 20]]
			rows: datas[0]
		}
	});
	configs[1] = Configger(chartElems[1], datas[1]).add(sizeConfig).add({
		data: {
			groups: [['서비스', '분위기', '품질']],
			type: 'area-spline'
		},
		axis: {
			y: {
				max: 15
			}
		}
	});
	configs[2] = Configger(chartElems[2], datas[2]).add(sizeConfig).add(barConfig);
	configs[3] = Configger(chartElems[3], datas[3]).add(sizeConfig).add(barConfig).add(categoryConfig).add({
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
	
	for (var i = 0; i < 4; i++) {
		charts[i] = Chart(configs[i]);
	}
}

function updateCharts(jsonData) {
	var datas = parseDashboardData(jsonData);
	
	for (var i = 1; i < datas[0][0].length; i++) {
		datas[0][0][i] = new Date(datas[0][0][i]).format('MM/dd E');
	}
	
	charts[0].load({
		rows: datas[0]
	});
	charts[0].refresh();
	for (var i = 1; i < 4; i++) {
		charts[i].load({
			columns: datas[i]
		});
		charts[i].refresh();
	}
}

function updateSummaryInfos(data) {
	var avgStar = (data.starService + data.starMood + data.starGoods) / 3;
	
	var summaryElems = $('.summary-content .emphasis');
	summaryElems.eq(0).html(data.visitorCount);
	summaryElems.eq(1).html(avgStar.toPrecision(2));
	summaryElems.eq(2).html(data.replyCount);
	
	var sales = data.totalSales, scale = 0;
//	while (sales > 10000) {
//		sales /= 10000;
//		scale++;
//	}
//	switch (scale) {
//	case 0: break;
//	case 1: sales += '만'; break;
//	case 2: sales += '억'; break;
//	
//	}
	sales /= 10000;
	summaryElems.eq(3).html(sales);
}

Date.prototype.format = function(f) {
    if (!this.valueOf()) return " ";
 
    var weekName = ["일", "월", "화", "수", "목", "금", "토"];
    var d = this;
     
    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {
        switch ($1) {
            case "yyyy": return d.getFullYear();
            case "yy": return (d.getFullYear() % 1000).zf(2);
            case "MM": return (d.getMonth() + 1);
            case "dd": return d.getDate();
            case "E": return weekName[d.getDay()] + '요일';
            case "HH": return d.getHours().zf(2);
            case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);
            case "mm": return d.getMinutes().zf(2);
            case "ss": return d.getSeconds().zf(2);
            case "a/p": return d.getHours() < 12 ? "오전" : "오후";
            default: return $1;
        }
    });
};