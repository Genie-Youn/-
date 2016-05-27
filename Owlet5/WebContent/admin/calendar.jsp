<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8' />

<title>TCP-Owlet5</title>
<script src='/admin/calendar/lib/jquery.min.js'></script>

<!-- Owlet Project link  -->
<link rel="shortcut icon" href="/res/img/favicon.ico">
<link rel="stylesheet" href="/admin/css/crossbrowser.css">
<link rel="stylesheet" href="/admin/css/bootstrap.min.css">
<link rel="stylesheet" href="/admin/css/jquery-ui.min.css">
<link rel="stylesheet" href="/admin/css/intro-menu-style.css">
<link rel="stylesheet" href="/admin/css/common-style.css">
<link rel="stylesheet" href="/admin/css/calendar/calendar-style.css">
<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/bootstrap.min.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/modernizr.custom.js"></script>

<!--  Calendar Link -->
<link href='/admin/calendar/fullcalendar.css' rel='stylesheet' />
<link href='/admin/calendar/fullcalendar.print.css' rel='stylesheet'
	media='print' />
<link href='/admin/calendar/cal.css' rel='stylesheet' />
<script src='/admin/calendar/lib/moment.min.js'></script>
<script src='/admin/calendar/fullcalendar.min.js'></script>
<script src='/admin/calendar/lib/cal.js'></script>

<!--jquery ui-->
<!-- <script src='/admin/calendar/dialog/js/jquery-ui.js'></script>
<script src='/admin/calendar/dialog/js/jquery-ui.min.js'></script> -->

</head>
<body>
<div id="loading_icon_locator"></div>
	<div id="page_wrapper">

	<div id="header-wrapper">

	<div id="main_header_wrapper">
			<header class="main-header">
				<div id="main_logo" title="환영합니다.">
				</div>
				
				<section class="header_button">
				
				<%
					Object sessionData = session.getAttribute("UserName");
				
					String name=(String)sessionData;
				%>
				
				<p class="loginName"><%=name %> 님 환영합니다.</p>
				
				<p class="home">
							<button class="btn btn-1">home</button>
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
	</div>
	<!-- 상단 메뉴와 하단 컨텐츠의 여백을 설정하는 요소 -->

		<div id="main_empty_filler"></div>
		
		<div id="content_wrapper">
		  <div id='calendar_btn'>
			<button class="delete-btn fc-delete-button fc-button fc-state-default fc-corner-left fc-corner-right">일정 삭제</button> 
		 </div>
			
			
			<!-- 상단 고정 부분의 끝 -->
			<div id='calendar'></div>
			
			
			<div id='dialog_form'>
					<h3> 등록할 요소 선택 </h3>
				<button class="notice-btn">공지 추가하기</button>
				<button class="event-btn">이벤트 추가하기</button>
				<button class="coupon-btn">쿠폰 추가하기</button>
				<button class="edit-btn">수정하기</button>
				<button class="close-btn">끄기</button>
			</div>
			
			<div id="content_viewer">
				<div class="title"></div>
				<div class="menu-option"></div>
				<div class="time-span"></div>
				<div class="content"></div>				
			</div>
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
