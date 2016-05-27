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
		<!-- ��� ���� �κ� -->

		<div id="header-wrapper">

			<!-- �ΰ� ǥ���մϴ�. -->

			<div id="main_header_wrapper">
				<header class="main-header">
					<div id="main_logo" title="ȯ���մϴ�."></div>

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
			<!-- �޴��� ǥ���˴ϴ�. -->
			<div id="navigator_wrapper">
				<nav id="navigator">
					<div class="nav_frame">
						<ul class="nav nav-pills nav-justified">
							<li><a href="/analysis/main.do">��ú���</a></li>
							<li><a href="/notice/noticeList.do?tcp=">��������</a></li>
							<li class="active"><a href="/adder/editor.do">�۸����</a></li>
							<li><a href="/analysis/list.do">�м��ϱ�</a></li>
							<li><a href="/calcou/main.do">�޷�,����</a></li>
						</ul>
					</div>
				</nav>
			</div>
		</div>
		<!-- ��� ���� �κ��� �� -->

		<!-- ��� �޴��� �ϴ� �������� ������ �����ϴ� ��� -->
		<div id="main_empty_filler"></div>
		<div id="content_wrapper">
			<!-- �߰��ϱ� ��ư -->
			<div id="add">
				<form>
					<input class="addApp" id="btn-add" type="button" value="�߰��ϱ�" />
				</form>
			</div>
			<center>
				<table id="frameTable" class="display" cellspacing="0" width="100%">
					<thead>
						<tr>
							<td>�� �̸�</td>
							<td>�� ����</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td>�� �̸�</td>
							<td>�� ����</td>
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
		<!-- �ϴ��� ������� ǥ�� �� -->

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