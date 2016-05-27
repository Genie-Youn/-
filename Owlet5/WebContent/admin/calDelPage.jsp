<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>TCP-Owlet5</title>
<link rel="shortcut icon" href="/res/img/favicon.ico">
<link rel="stylesheet" href="/admin/css/crossbrowser.css">
<link rel="stylesheet" href="/admin/css/bootstrap.min.css">
<link rel="stylesheet" href="/admin/css/jquery-ui.min.css">
<link rel="stylesheet" href="/admin/css/intro-menu-style.css">
<link rel="stylesheet" href="/admin/css/common-style.css">
<link rel="stylesheet" href="/admin/css/calendar/calendar-delete-style.css">
<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/bootstrap.min.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/modernizr.custom.js"></script>
<script src="/admin/js/calendar/calendar-delete-script.js"></script>

</head>
<body>
<div id="loading_icon_locator"></div>
	<div id="page_wrapper">


		<div id="header-wrapper">


			<div id="main_header_wrapper">
			<header class="main-header">
				<div id="main_logo"title=""></div>
				
				<section class="header_button">
				<p class="mypage">
					<button class="btn btn-1">myPage</button>
					</p>
				<p class="logout">
					<button class="btn btn-1">logout</button>
					</p>
				</section>
				
				
			</header>
		</div>

		<div id="navigator_wrapper">
			<nav id="navigator">
				<div class="nav_frame">
					<ul class="nav nav-pills nav-justified">
						<li id="nav-menu">
							<a href="#" id="menu"></a>
						</li>
						<li id="nav-menu" class="active">
							<a href="#" id="menu"></a>
						</li>
						<li id="nav-menu">
							<a href="#" id="menu"></a>
						</li>
						<li id="nav-menu">
							<a href="#" id="menu"></a>
						</li>
						<li id="nav-menu">
							<a href="#" id="menu"></a>
						</li>
						</ul>
					</ul>
				</div>
			</nav>
		</div>
		</div>

		<div id="main_empty_filler"></div>


		<div id="content_wrapper">
			<section id="main_section">
			<form method=POST action="/calendar/bulkDelete.do">
			<input type="hidden" name="tcp">
			
			<!-- <input type="checkbox" name="all" class="check-all"> <label>Check All</label> -->
			<input type="button" class="delete-all-btn" value="전체 삭제" onclick="return false;">
			<input type="button" class="delete-part-btn" value="선택 삭제" onclick="return false;">
			<input type="button" class="came-back-btn" value="돌아가기" onclick="return false;">
			
				
				<div class="item-container">
					<c:forEach var="calendarInfo" items="${calendarInfoList}" varStatus="loop">
						<div class="item-slot" data-index="${loop.index}">
							<div class="x-btn"> X </div>
							<div class="title">${calendarInfo.title}</div>
							<div class="start-date">${calendarInfo.startDate}</div>
							<div class="end-date">${calendarInfo.endDate}</div>
							<div class="content">${calendarInfo.contents}</div>
							<div class="values">${calendarInfo.nIndex}</div>
						</div>
					</c:forEach>
				</div>
			</form>
			</section>
		</div>


		<div id="footer_wrapper">
			<footer id="main_footer">
				<div class="footer-container">
				<div id="main_copyright">
					Copyright &copy; 2014 <b>TCP</b> All Rights Reserved.<br>
					Owlet5 is a web project of TCP club in Computer Science and Engineering ,
					 Seoul National University of Science and Technology.
				</div>
				</div>
			</footer>
		</div>
	</div>


</body>
</html>