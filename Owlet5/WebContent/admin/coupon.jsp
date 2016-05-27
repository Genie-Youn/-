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

		<!-- ��� ���� �κ� -->

		<div id="header-wrapper">

			<!-- �ΰ� ǥ���մϴ�. -->

			<div id="main_header_wrapper">
				<header class="main-header">
					<div id="main_logo" title="ȯ���մϴ�."></div>

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

			<!-- �޴��� ǥ���˴ϴ�. -->

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
		<!-- ��� ���� �κ��� �� -->

		<!-- ��� �޴��� �ϴ� �������� ������ �����ϴ� ��� -->

		<div id="main_empty_filler"></div>

		<!-- �� ������ ���ϴ�. -->

		<div id="content_wrapper">
			<section id="main_section">
				<form method="POST" name="coupon" id="coupon_form"
					action="/event/insertCoupons.do" modelAttribute="couponInfo"
					modelAttribute="calInfo" enctype="multipart/form-data">
					<div class="coupon_name">
						<b>���� :</b> <input type="text" id="coupon_title" class="textbox"
							size="100" name="name" /> <br> <span
							class="title_warn warning-text warning-hide">�Է����ּ���</span>
					</div>
					<div class="coupon_select">
						<b> &nbsp;���� �߱� ��� :</b>&nbsp;&nbsp; <input type="radio"
							name="group" class="coupon_radio" value="0"
							onclick="div_OnOff(this.value,'image');" /> �̹��� ���� <input
							type="radio" name="group" class="coupon_radio" value="1"
							onclick="div_OnOff(this.value,'number');" /> ���� ���� <br> <span
							class="radio_warn warning-text warning-hide">�������ּ���</span>
					</div>
					<div id="image" style="display: none">
						<input id="browse-click" type="button" value="���� ���" />
						<div id="edit_image"></div>
						<input type="file" name="file" id="files" class="display-none">
						<input type="hidden" class="textbox" size=5 name="count" value=0>

					</div>
					<div id="number" style="display: none">

						<div class="coupon_count">
							<b>���� ���� ���� : </b> <input type="text" id="coupon_count"
								class="textbox" size=5 name="count">�� <br> <span
								class="count_warn warning-text warning-hide">0~999 ���� ����
								�Է����ּ���.</span>
						</div>
					</div>
					<div class="coupon_date">
						<b> �Ͻ� :</b> <input type="text" id="coupon_startDate"
							class="textbox" size=15 name="startDate" /> ~ <input type="text"
							id="coupon_endDate" class="textbox" size=15 name="endDate" /> <br>
						<span class="date_warn warning-text warning-hide">��¥��
							�Է����ּ��� ex)20140101</span>
					</div>




					<div class="coupon_pop">
						<b>�˾� ���� :</b> &nbsp;&nbsp;<input type="radio" name="popupEnable"
							class="coupon_radio" value="1" /> ��� <input type="radio"
							name="popupEnable" class="coupon_radio" value="0" checked /> �����
					</div>
					<div class="coupon_alarm">
						<b>�˶� ���� :</b> &nbsp;&nbsp;<input type="radio" name="alarmEnable"
							class="coupon_radio" value="1" /> ��� <input type="radio"
							name="alarmEnable" class="coupon_radio" value="0" checked /> �����
					</div>

					<div id="button_area">
						<input type="submit" value="����" class="modern"> <input
							type="reset" value="���" class="modern">
					</div>
				</form>
			</section>
		</div>

		<!-- �ϴ��� ������� ǥ�� �� -->

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