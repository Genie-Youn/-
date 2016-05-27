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
<link rel="stylesheet" href="/admin/css/adder/skin-config-style.css">
<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/modernizr.custom.js"></script>
<script src="/admin/js/jquery-1.11.1.min.js"></script>
<script src="/admin/js/bootstrap.min.js"></script>
<script src="/admin/js/adder/skin-config-script.js"></script>
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
				<p class="totalUrl">
							<button class="btn btn-1">UrlInfo</button>
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
						<li class="activated-tab"><p>기타 구성</p></li>
						<li><a href = '/admin/confirmConfiguration.jsp'>최종 화면</a></li>
					</ul>
				</div>
				<div class="tab-body-list">
					<ul>
						<li class="group-list active">
							<div>
								<div class="skin-top-header">
								<p>기타 구성</p>
								</div>
								<div>
									<ul class="partion-of-configset">
										<li class="skin-list">
											<b>앱 색상</b>
											<ul class="color-container front">
												
												<li class="color-item cgray color-selected"></li>
												<li class="color-item cyellow"></li>
												<li class="color-item cpink"></li>
												<li class="color-item cgreen"></li>
												<li class="color-item csky"></li>
												
												<li class="color-item cblue"></li>
												<li class="color-item cpurple"></li>
												<li class="color-item credorange"></li>
												<li class="color-item corange"></li>
												<li class="color-item cred"></li>
												
												<li class="color-item colive"></li>
											</ul>
											
											<div class="mobile-locator">
												<div class="viewport-div">
													<div class="background-element">
													</div>
													
													<div class="menu-element">
													</div>
												</div>
											</div>
											<p>11종의 색상 중 마음에 드는 것을 골라 <br>앱의 고유색상으로 설정 가능합니다.</p>
										</li>
										<li class="skin-list">
											<b>인트로 이미지</b>
											<input type="file" class="file-intro-input" name="files">
											<div class="mobile-locator">
												<div class="viewport-div background-port">
												</div>
											</div>
											<p>시작할 때 보여지는 인트로 이미지를 등록하세요.</p>
										</li>
									</ul>
									<div class="btn-wrapper">
										<button class="prev-btn">이전</button>
										<button class="save-btn">저장</button>
										<button class="next-btn">다음</button>
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