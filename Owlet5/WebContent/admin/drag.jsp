<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>radio button Test </title>
		<link rel="stylesheet" href="//code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
		<script src="//code.jquery.com/jquery-1.10.2.js"></script>
		<script src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
		<script src="../admin/js/drag.js"></script>
		<link rel="stylesheet" href="../admin/css/drag.css">
	</head>

	<body>
		<div>
			<input type="radio" name="testGroup" id="test1" value="1" onclick="div_OnOff(this.value,'vertical');" />
			세로 스타일
			<br/>
			<input type="radio" name="testGroup" id="test2" value="2" checked onclick="div_OnOff(this.value,'horizontal');"/>
			가로 스타일
			<br/>
		</div>

		<div id="containment-wrapper">
		<div id="vertical" style="display:none">
		<ul id="sortable">
			<li id="draggable3" class="ui-state-default">
				<a href="main.html"><img class="drag1"
					src="../res/img/owlet3.gif"></a>
					</li>
			<li id="draggable" class="ui-state-default">
				<img class="drag1" src="../res/img/owlet4.gif">
			</li>
			<li id="draggable2" class="ui-state-default">
				<img class="drag1" src="../res/img/adder.gif">
			</li>
		</ul>
		</div>
		
		<div id="horizontal">
		<ul id="sortable2">
			<li id="draggable4" class="draggable ui-widget-content" class="ui-state-default">
				<a href="main.html"><img class="drag1"
					src="../res/img/owlet3.gif"></a>
					</li>
			<li id="draggable5" class="draggable ui-widget-content" class="ui-state-default">
				<img class="drag1" src="../res/img/owlet4.gif">
			</li>
			<li id="draggable6" class="draggable ui-widget-content" class="ui-state-default">
				<img class="drag1" src="../res/img/adder.gif">
			</li>
		</ul>
		</div>
	</div>

	</body>
</html>