<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>TCP-Owlet5</title>
<link rel="shortcut icon" href="/res/img/favicon.ico">
<link rel="stylesheet" href="/admin/css/crossbrowser.css">
<link rel="stylesheet" href="/admin/css/bootstrap.min.css">
<link rel="stylesheet" href="/admin/css/jquery-ui.min.css">
<link rel="stylesheet" href="/admin/css/intro-menu-style.css">
<link rel="stylesheet" href="/admin/css/common-style.css">
<link rel="stylesheet" href="/admin/css/coupon/coupon.css">
<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/bootstrap.min.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/modernizr.custom.js"></script>
<script src="/admin/js/coupon/coupon.js"></script>

</head>
<body>

	<div id="page_wrapper">

		<!-- 상단 고정 부분 -->

		<div id="header-wrapper">

			<!-- 로고를 표시합니다. -->

			<div id="main_header_wrapper">
				<header class="main-header">
					<div id="main_logo" title="환영합니다."></div>

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
							<li id="nav-menu"><a href="#" id="menu"></a></li>
							<li id="nav-menu"><a href="#" id="menu"></a></li>
							<li id="nav-menu"><a href="#" id="menu"></a></li>
							<li id="nav-menu"><a href="#" id="menu"></a></li>
							<li id="nav-menu" class="active"><a href="#" id="menu"></a>
							</li>
						</ul>
						</ul>
					</div>
				</nav>
			</div>
		</div>
		<!-- 상단 고정 부분의 끝 -->

		<!-- 상단 메뉴와 하단 컨텐츠의 여백을 설정하는 요소 -->

		<div id="main_empty_filler"></div>

		<!-- 글 내용이 들어갑니다. -->

		<div id="content_wrapper">
			<section id="main_section">
				<form method="POST" name="coupon" id="coupon_form"
					action="/event/insertCoupons.do" modelAttribute="couponInfo"
					modelAttribute="calInfo" enctype="multipart/form-data">
					<div class="coupon_name">
						<b>행사명 :</b> <input type="text" id="coupon_title" class="textbox"
							size="100" name="name" /> <br> <span
							class="title_warn warning-text warning-hide">입력해주세요</span>
					</div>
					<div class="coupon_select">
						<b> &nbsp;쿠폰 발급 방식 :</b>&nbsp;&nbsp; <input type="radio"
							name="group" class="coupon_radio" value="0"
							onclick="div_OnOff(this.value,'image');" /> 이미지 쿠폰 <input
							type="radio" name="group" class="coupon_radio" value="1"
							onclick="div_OnOff(this.value,'number');" /> 숫자 쿠폰 <br> <span
							class="radio_warn warning-text warning-hide">선택해주세요</span>
					</div>
					<div id="image" style="display: none">
						<input id="browse-click" type="button" value="쿠폰 등록" />
						<div id="edit_image"></div>
						<input type="file" name="file" id="files" class="display-none">
						<input type="hidden" class="textbox" size=5 name="count" value=0>

					</div>
					<div id="number" style="display: none">

						<div class="coupon_count">
							<b>쿠폰 발행 갯수 : </b> <input type="text" id="coupon_count"
								class="textbox" size=5 name="count">개 <br> <span
								class="count_warn warning-text warning-hide">0~999 사이 값을
								입력해주세요.</span>
						</div>
					</div>
					<div class="coupon_date">
						<b> 일시 :</b> <input type="text" id="coupon_startDate"
							class="textbox" size=15 name="startDate" /> ~ <input type="text"
							id="coupon_endDate" class="textbox" size=15 name="endDate" /> <br>
						<span class="date_warn warning-text warning-hide">날짜를
							입력해주세요 ex)20140101</span>
					</div>




					<div class="coupon_pop">
						<b>팝업 설정 :</b> &nbsp;&nbsp;<input type="radio" name="popupEnable"
							class="coupon_radio" value="1" /> 허용 <input type="radio"
							name="popupEnable" class="coupon_radio" value="0" checked /> 비허용
					</div>
					<div class="coupon_alarm">
						<b>알람 설정 :</b> &nbsp;&nbsp;<input type="radio" name="alarmEnable"
							class="coupon_radio" value="1" /> 허용 <input type="radio"
							name="alarmEnable" class="coupon_radio" value="0" checked /> 비허용
					</div>

					<div id="button_area">
						<input type="submit" value="저장" class="modern"> <input
							type="reset" value="취소" class="modern">
					</div>
				</form>
			</section>
		</div>

		<!-- 하단의 약관정보 표현 부 -->

		<div id="footer_wrapper">
			<footer id="main_footer">
				<div class="footer-container">
					<div id="main_copyright">
						Copyright &copy; 2014 <b>TCP</b> All Rights Reserved.<br>
						Owlet5 is a web project of TCP club in Computer Science and
						Engineering , Seoul National University of Science and Technology.
					</div>
				</div>
			</footer>
		</div>
	</div>
</body>
</html>