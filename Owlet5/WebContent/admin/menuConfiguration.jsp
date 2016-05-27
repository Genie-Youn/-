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
<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/modernizr.custom.js"></script>
<script src="/admin/js/jquery-1.11.1.min.js"></script>
<script src="/admin/js/bootstrap.min.js"></script>
<script src="/admin/js/adder/menu-config-script.js"></script>
<script src="/admin/js/adder/jquery.ddslick.min.js"></script>
<script src="/admin/js/adder/jquery.event.drag-2.2.js"></script>
<script src="/admin/js/adder/jquery.event.drop-2.2.js"></script>

</head>
<body>
	<div id="page_wrapper">
		<!-- 상단 고정 부분 -->

		<div id="header-wrapper">

			<!-- 로고를 표시합니다. -->

			<div id="main_header_wrapper">
				<header class="main-header">
					<div id="main_logo" title=""></div>

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
					<li class="activated-tab"><p>그룹 구성</p></li>
					<li><a href = '/admin/menuBatchConfiguration.jsp'>메뉴 구성</a></li>
					<li><a href = '/admin/skinConfiguration.jsp'>기타 구성</a></li>
					<li><a href = '/admin/confirmConfiguration.jsp'>최종 화면</a></li>
				</ul>
			</div>
			<div class="tab-body-list">
				<ul>
					<li class="group-list active">
						<div>
							<div class="group-config-wrapper">
								<div class="top-header"><p>그룹 구성</p></div>
								<div class="content">

									<div class="frame-list-wrapper">
										<div class="list-header">
											<div class="whole-selector">전체선택</div>
											<div class="list-name">페이지 목록</div>
										</div>
										<div class="data-info-panel">
											비었습니다. <br>모든 페이지가 오른쪽으로 이동했거나 <br>페이지를 만들지 않으셨어요.
											<br>페이지는 위 앱만들기 메뉴 클릭 후, <br>추가하기 버튼을 클릭하여 만들 수
											있습니다.
										</div>
										<ul class="ul-list">
											<li class="frame-item">프레임 1</li>
											<li class="frame-item">프레임 2</li>
											<li class="frame-item">프레임 3</li>
											<li class="frame-item">프레임 4</li>
											<li class="frame-item">프레임 5</li>
											<li class="frame-item">프레임 6</li>
											<li class="frame-item">프레임 7</li>
										</ul>
										<div class="drag-info-panel">빼내려면 여기 두면 된단다.</div>
									</div>

									<div class="group-list-wrapper">
										<div class="list-header">
											<div class="whole-selector">전체선택</div>
											<div class="group-check"></div>
										</div>
										<div class="data-info-panel">
											그룹에 데이터가 없네요...<br> 왼쪽에서 데이터를 드래그해서 옮겨주세요<br> 드래그가
											안 된다면 그룹을 선택해 주세요.<br> 그룹이 없다면 만들어 주세요.
										</div>
										<ul class="ul-list">
											<!-- <li class="frame-item">프레임 8</li>
												<li class="frame-item">프레임 9</li>
												<li class="frame-item">프레임 0</li>
												<li class="frame-item">프레임 a</li>
												<li class="frame-item">프레임 b</li>
												<li class="frame-item">프레임 c</li>
												<li class="frame-item">프레임 d</li> -->
										</ul>
										<div class="drag-info-panel">집어넣으려면 여기 두면 된단다.</div>
									</div>

									<div class="btn-wrapper">
										<button class="make-frame-btn">페이지 생성 페이지</button>
										<button class="group-create-btn">그룹생성/제거</button>
										<button class="next-btn">다음</button>

									</div>
								</div>
							</div>
							<div class="create-container">
								<div class="group-first">
									<button class="group-back-btn">돌아가기</button>
									<button class="group-delete-all-btn">모두 삭제</button>
								</div>
								<div class="group-second">
								<br>그룹명 : <input type="text" name="groupName"
									class="group-name">
								<button class="group-add-btn">그룹 추가</button>
								</div>
								<div class="group-container">
									<ul>
										<!-- <li>그룹1<button>-</button><button>변경</button></li>
											<li>그룹2<button>-</button><button>변경</button></li>
											<li>그룹3<button>-</button><button>변경</button></li>
											<li>그룹4<button>-</button><button>변경</button></li> -->
									</ul>
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
				<!-- 
					<ul id="footer-menu" class="footer-ul-style">
						<li class=""><a href="#">Privacy</a></li>
						<li class=""><a href="#">Terms</a></li>
						<li class=""><a href="#">Advertise</a></li>
						<li class=""><a href="#">Affiliates</a></li>
						<li class=""><a href="#">Newsletter</a></li>
						<li class=""><a href="#">About</a></li>
						<li class=""><a href="#" rel="nofollow" title=""
							target="_blank">Journal</a></li>
						<li class=""><a href="#">Contact Us</a></li>
					</ul>
					 -->
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