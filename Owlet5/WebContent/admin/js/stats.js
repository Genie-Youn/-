google.load('visualization', '1', {
	packages : [ "corechart" ]
});
google.setOnLoadCallback(drawVisualization);
function drawVisualization() {
	var data = google.visualization.arrayToDataTable([
			[ '구분항목', '방문횟수', '방문자수' ], [ '월요일', 5, 2],
			[ '화요일', 7, 1], [ '수요일', 9, 3],
			[ '목요일', 8, 8], [ '금요일', 6, 4],
			[ '토요일', 2, 1], [ '일요일', 10, 7], ]);

	var options = {
		title : '요일별 방문자수',
		hAxis : {
			title : '요일',
			titleTextStyle : {
				color : 'green'
			}
		}
	};

	var chart = new google.visualization.ColumnChart(document
			.getElementById('char_div'));

	chart.draw(data, options);
}