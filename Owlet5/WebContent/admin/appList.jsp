<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Owlet5 Project</title>
<link rel="stylesheet" href="/admin/css/intro-menu-style.css">
<link rel="stylesheet" href="/admin/css/adder/adder-menu-style.css">
<link rel="stylesheet" href="/admin/css/dataTables.jqueryui.css">
<link rel="stylesheet" href="/admin/css/jqueryui.dataTables.css">
<link rel="stylesheet" href="/admin/css/bootstrap.min.css">
<link rel="stylesheet" href="/admin/css/crossbrowser.css">
<link rel="stylesheet" href="/admin/css/common-style.css">
<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/modernizr.custom.js"></script>
<script src="/admin/js/external/jquery/dataTables.jqueryui.js"></script>
<script src="/admin/js/jquery-1.11.1.min.js"></script>
<script src="/admin/js/external/jquery/jquery.dataTables.min.js"></script>
<script src="/admin/js/adder/frameList-script.js"></script>
<script src="/admin/js/bootstrap.min.js"></script>
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
							<li><a href="/analysis/main.do">대시보드</a></li>
							<li><a href="/notice/noticeList.do?tcp=">공지사항</a></li>
							<li class="active"><a href="/adder/editor.do">앱만들기</a></li>
							<li><a href="/analysis/list.do">분석하기</a></li>
							<li><a href="/calcou/main.do">달력,쿠폰</a></li>
						</ul>
					</div>
				</nav>
			</div>
		</div>
		<!-- 상단 고정 부분의 끝 -->

		<!-- 상단 메뉴와 하단 컨텐츠의 여백을 설정하는 요소 -->
		<div id="main_empty_filler"></div>
		<div id="content_wrapper">
			<!-- 추가하기 버튼 -->
			<div id="add">
				<form>
					<input class="addApp" id="btn-add" type="button" value="추가하기" />
				</form>
			</div>
			<center>
				<table id="frameTable" class="display" cellspacing="0" width="100%">
					<thead>
						<tr>
							<td>앱 이름</td>
							<td>앱 버전</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td>앱 이름</td>
							<td>앱 버전</td>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach var="app" items="${appList}">
							<tr>
								<td>${app.name}</td>
								<td>${app.version}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</center>
		</div>
		<!-- 하단의 약관정보 표현 부 -->

		<div id="footer_wrapper">
			<footer id="main_footer">
				<div class="footer-container">
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

					<div id="main_copyright">
						Copyright &copy; 2014 Owlet CatVSDog. All Rights Reserved.<br>
						CatVSDog is a popular web design incorporated studio and shop.
					</div>
				</div>
			</footer>
		</div>
</body>
</html>