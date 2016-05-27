<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Owlet5 Maker</title>
<link rel="stylesheet" href="/admin/css/bootstrap.min.css">
<link rel="stylesheet" href="/admin/css/jquery-ui.min.css">
<link rel="stylesheet" href="/admin/css/intro-menu-style.css">
<link rel="stylesheet" href="/admin/css/crossbrowser.css">
<link rel="stylesheet" href="/admin/css/common-style.css">
<link rel="stylesheet" href="/admin/css/adder/adder-intro-style.css">
<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/modernizr.custom.js"></script>
<script src="/admin/js/bootstrap.min.js"></script>
<script src="/admin/js/jquery-1.11.1.min.js"></script>
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

						<%
					Object sessionData = session.getAttribute("UserName");
				
					String name=(String)sessionData;
				%>

						<p class="loginName"><%=name %>
							�� ȯ���մϴ�.
						</p>
						<p class="cal">
							<button class="btn btn-1">Calendar</button>
						</p>
						<p class="mypage">
							<button class="btn btn-1">URLInfo</button>
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
							<li id="nav-menu" class="active"><a href="#" id="menu"></a></li>
							<li id="nav-menu"><a href="#" id="menu"></a></li>
							<li id="nav-menu"><a href="#" id="menu"></a></li>
							<li id="nav-menu"><a href="#" id="menu"></a></li>
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
			<div class="container">
				<div class="jumbotron">
					<h1>�� �����</h1>
					<p>
						�� ����� �����Դϴ�. <br /> �Ʒ��� ��Ŀ� ���� �ۼ����ֽʽÿ�. <br /> �̸��� version ���� ��
						ȭ���� �ϳ��� ����� �˴ϴ�.
					</p>
				</div>

				<form method="post" action="/adder/insertAppInfo.do"
					modelAttribute="appInfo">

					<div class="form-group">
						<label class="col-xs-2 control-label"><b>�̸� : </b></label>
						<div class="col-xs-5">
							<input type="text" class="form-control" name="name">
						</div>
					</div>
					<br />
					<div class="form-group">
						<label class="col-xs-2 control-label"><b>Version : </b></label>
						<div class="col-xs-5">
							<select name="version" class="form-control">
								<option value="1">1.0
								<option value="2">2.0
								<option value="3">3.0
								<option value="4">4.0
								<option value="5">5.0
							</select>
						</div>
					</div>

					<div class="form-group">
						<div class="col-xs-12" align="right">
							<input type="submit" value="Save" class="btn btn-primary" />
						</div>
					</div>
				</form>
			</div>
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
</body>
</html>
