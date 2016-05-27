<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Owlet5 Project</title>
<link rel="shortcut icon" href="/res/img/favicon.ico">
<link rel="stylesheet" href="/admin/css/crossbrowser.css">
<link rel="stylesheet" href="/admin/css/bootstrap.min.css">
<link rel="stylesheet" href="/admin/css/intro-menu-style.css">
<link rel="stylesheet" href="/admin/css/common-style.css">
<link rel="stylesheet" href="/admin/css/adder/menu-config-style.css">
<link rel="stylesheet" href="/admin/css/adder/confirm-config-style.css">
<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/modernizr.custom.js"></script>
<script src="/admin/js/jquery-1.11.1.min.js"></script>
<script src="/admin/js/bootstrap.min.js"></script>
<script src="/admin/js/adder/confirm-config-script.js"></script>
<script src="/admin/js/adder/menu-confirm.js"></script>
</head>
<body>
<div id="page_wrapper">
		<!-- 상단 고정 부분 -->

		<div id="header-wrapper">

			<!-- 로고를 표시합니다. -->

			<div id="main_header_wrapper">
			<header class="main-header">
				<div id="main_logo"title=""></div>
				
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
			<!-- 메뉴가 표현됩니다. -->
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
				</div>
			</nav>
		</div>
		</div>
		<!-- 상단 고정 부분의 끝 -->

		<!-- 상단 메뉴와 하단 컨텐츠의 여백을 설정하는 요소 -->
		<div id="main_empty_filler"></div>
		<div id="content_wrapper">
				<div class="tab-head-list">
					<ul>
						<li><a href = '/admin/menuConfiguration.jsp'>그룹 구성</a></li>
						<li><a href = '/admin/menuBatchConfiguration.jsp'>메뉴 구성</a></li>
						<li><a href = '/admin/skinConfiguration.jsp'>기타 구성</a></li>
						<li class="activated-tab"><p>최종 화면</p></li>
					</ul>
				</div>
				<div class="tab-body-list" id="tab-body-list">
					<ul>
						<li class="group-list active">
							<div>
								<div class="iframe-container">
									<iframe id="confirm-frame" src="/admin/mobile/mobile-index.html" scrolling="no" seamless="seamless" class="framei-style"></iframe>
								</div>
								<div class="app-qrcode">
									<div class="qrcode-title">url QR코드 확인</div>
										<img class="code-img">
								</div>
								
								<div class="app-sns">
									<div class="sns-title">SNS 공유</div>
								</div>
								
								<div class="sns-wrapper">
									<button class="sns-btn" id="sns-twitter">트위터</button>
									<button class="sns-btn" id="sns-facebook">FaceBook</button>
								</div>
								
								<div class="btn-wrapper">
										<button class="prev-btn">이전</button>
										<button class="next-btn">완료</button>
								</div>
							</div>
						</li>
						<li class="menu-list">
							<div>2</div>
						</li>
						<li class="skin-list">
							<div>3</div>
						</li>
						<li class="final-list">
							<div>4</div>
						</li>
					</ul>
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