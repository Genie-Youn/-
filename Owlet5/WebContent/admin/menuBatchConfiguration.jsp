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
<link rel="stylesheet" href="/admin/css/adder/menu-batch-config-style.css">
<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/modernizr.custom.js"></script>
<script src="/admin/js/jquery-1.11.1.min.js"></script>
<script src="/admin/js/bootstrap.min.js"></script>
<script src="/admin/js/adder/menu-other-config-script.js"></script>
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
						<li>
						<a href = '/admin/menuConfiguration.jsp'>그룹 구성</a>
						</li>
						<li class="activated-tab"><p>메뉴 구성</p></li>
						<li>
						<a href = '/admin/skinConfiguration.jsp'>기타 구성</a>
						</li>
						<li><a href = '/admin/confirmConfiguration.jsp'>최종 화면</a></li>
					</ul>
				</div>
				<div class="tab-body-list">
					<ul>
						<li class="group-list active">
							<div>
								<div class="group-config-wrapper">
									<div class="top-header"><p>메뉴 구성</p></div>
									<div class="content">
									
										<div class="frame-list-wrapper">
											<div class="list-header">
												<div class="whole-selector">전체선택</div>
												<div class="list-name">페이지 목록</div>
											</div>
											<div class="data-info-panel">
												비었습니다.
												<br>모든 페이지가 오른쪽으로 이동했거나
												<br>페이지를 만드시지 않으셨어요.
												<br>페이지는 위 앱만들기 메뉴 클릭 후,
												<br>추가하기 버튼을 누르셔서 만들 수 있습니다.								
											</div>
											<ul class="ul-list">
												<li class="frame-item">프레임 1</li>
												<li class="frame-item">프레임 2</li>
												<li class="frame-item">프레임 3</li>
												<li class="frame-item">프레임 4</li>
												<li class="frame-item">프레임 5</li>
												<li class="frame-item">프레임 6</li>
												<li class="frame-item">프레임 72222</li>
												<li class="group-item">그룹 1</li>
												<li class="group-item">그룹 2</li>
												<li class="group-item">그룹 3</li>
												<li class="group-item">그룹 4</li>
											</ul>
											<div class="drag-info-panel">
												빼내려면 여기 두면 된단다.											
											</div>
										</div>
										
										<div class="group-list-wrapper">
											<div class="text-viewer">
											</div>
											<ul class="slot-wrapper">
												<li class="rect-slot">메뉴슬롯</li>
												<li class="rect-slot">메뉴슬롯</li>
												<li class="rect-slot">메뉴슬롯</li>
												<li class="rect-slot">메뉴슬롯</li>
												<li class="rect-slot">메뉴슬롯</li>
											</ul>
										</div>
										
										<div class="btn-wrapper">
											<button class="prev-btn">이전</button>
											<button class="save-btn">저장</button>
											<button class="next-btn">다음</button>
										</div>
									</div>
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