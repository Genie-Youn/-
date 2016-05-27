<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8' />

<script src='/admin/calendar/lib/jquery.min.js'></script>
<title>TCP-Owlet5</title>

<!-- Owlet Project link  -->
<link rel="shortcut icon" href="/res/img/favicon.ico">
<link rel="stylesheet" href="/admin/css/crossbrowser.css">
<link rel="stylesheet" href="/admin/css/bootstrap.min.css">
<link rel="stylesheet" href="/admin/css/jquery-ui.min.css">
<link rel="stylesheet" href="/admin/css/intro-menu-style.css">
<link rel="stylesheet" href="/admin/css/common-style.css">
<link rel="stylesheet" href="/admin/css/calcou-style.css">
<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/bootstrap.min.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/modernizr.custom.js"></script>
<style>
/* 넣어보고 싶었어요.. */

.col-xs-6:hover {
	background-color:rgba(240,240,240,0.3);
}

</style>
</head>
<body>
<div id="loading_icon_locator"></div>
<div id="page_wrapper">

	<div id="header-wrapper">
		<div id="main_header_wrapper">
			<header class="main-header">
				<div id="main_logo"title="환영합니다."></div>
				
				<section class="header_button">
				
				<p class="cal">
							<button class="btn btn-1">Calendar</button>
						</p>		
				<p class="mypage">
					<button class="btn btn-1">myPage</button>
					</p>
				<p class="logout">
					<button class="btn btn-1">logout</button>
					</p>
				</section>
				
				
			</header>
		</div>
	

		<!-- 메뉴가 표현됩니다 -->
	
		<div id="navigator_wrapper">
			<nav id="navigator">
				<div class="nav_frame">
					<ul class="nav nav-pills nav-justified">
						<li id="nav-menu">
							<a href="#" id="menu"></a>
						</li>
						<li id="nav-menu">
							<a href="#" id="menu"></a>
						</li>
						<li id="nav-menu">
							<a href="#" id="menu"></a>
						</li>
						<li id="nav-menu"  class="active">
							<a href="#" id="menu"></a>
						</li>
						<li id="nav-menu">
							<a href="#" id="menu"></a>
						</li>
					</ul>
				</div>
			</nav>
		</div>
	</div>
	<!-- 상단 메뉴와 하단 컨텐츠의 여백을 설정하는 요소 -->

	<div id="main_empty_filler"></div>
		
	<div id="content_wrapper">
		<section id="main_section">
			<h1>달력,쿠폰 <small>달력에 이벤트와 공지를 등록하세요, 쿠폰 관리하세요</small></h1>
			<hr class="headline_underline"/>
			
			
			<div class="content_empty_filter"></div>
			
			<div class="row">
				<div class="col-xs-6" align="center">
					<a href="/calendar/calendar.do"><img src="/res/img/test_calendar_btn.png" /></a>
					<!-- <div class="calendar_button" ></div> -->
					<h3>달력</h3>
					<p>공지,이벤트,쿠폰 추가가능</p>
				</div>
				<div class="col-xs-6" align="center">
					<a href="/event/couponList.do"><img src="/res/img/test_coupon_btn.png" /></a>
					<!-- <div class="coupon_button" ></div> -->
					<h3>쿠폰</h3>
					<p>쿠폰 목록 및 쿠폰 추가가능</p>
				</div>
			</div>
			
			<div class="content_empty_filter"></div>
			
		</section>
	</div>
	
	<!-- 하단의 약관정보 표현 부 -->

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