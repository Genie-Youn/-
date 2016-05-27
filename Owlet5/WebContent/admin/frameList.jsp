<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TCP-Owlet5</title>
<link rel="shortcut icon" href="/res/img/favicon.ico">
<link rel="stylesheet" href="/admin/css/crossbrowser.css">
<link rel="stylesheet" href="/admin/css/bootstrap.min.css">
<link rel="stylesheet" href="/admin/css/intro-menu-style.css">
<link rel="stylesheet" href="/admin/css/adder/adder-menu-style.css">
<link rel="stylesheet" href="/admin/css/dataTables.jqueryui.css">
<link rel="stylesheet" href="/admin/css/common-style.css">
<link rel="stylesheet" href="/admin/css/jquery.dataTables.css">
<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/modernizr.custom.js"></script>
<script src="/admin/js/external/jquery/dataTables.jqueryui.js"></script>
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
					<div id="main_logo" title=""></div>
					<section class="header_button">
					<%
					Object sessionData = session.getAttribute("UserName");
				
					String name=(String)sessionData;
				%>

						<p class="loginName"><%=name %>
							�� ȯ���մϴ�.
						</p>

						<section class="header_button">
							<p class="cal">
								<button class="btn btn-1">�޷�</button>
							</p>
							<p class="mypage">
								<button class="btn btn-1">����������</button>
							</p>
							<p class="logout">
								<button class="btn btn-1">�α׾ƿ�</button>
							</p>
						</section>
					</header>
			</div>
			<!-- �޴��� ǥ���˴ϴ�. -->
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
							<a href="#" id="menu-unabled"></a>
						</li>
						<li id="nav-menu">
							<a href="#" id="menu-unabled"></a>
						</li>
						<li id="nav-menu">
							<a href="#" id="menu-unabled"></a>
						</li>
						</ul>
					</div>
				</nav>
			</div>
		</div>
		<!-- ��� ���� �κ��� �� -->

		<!-- ��� �޴��� �ϴ� �������� ������ �����ϴ� ��� -->
		<div id="main_empty_filler"></div>
		<div id="content_wrapper">
				<table id="frameTable" class="display" cellspacing="0" width="100%">
					<thead>
						<tr>
							<td>�۹�ȣ</td>
							<td>����</td>
							<td>���</td>
							<td>����</td>
							<td>����</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td>�۹�ȣ</td>
							<td>����</td>
							<td>���</td>
							<td>����</td>
							<td>����</td>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach var="frame" items="${frameList}">
							<tr>
								<td>${frame.nIndex}</td>
								<td>${frame.title}</td>
								<td><a href="${frame.path}" target="popup" 
								onclick="PopupCenterDual('${frame.path}','NIGRAPHIC','360','640')" >${frame.path}</td>
								<td><a href="/adder/editFrame.do?index=${frame.nIndex}">����</a></td>
								<td><a
									href="/adder/deleteFrame.do?frameIndex=${frame.nIndex}">����</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			<!-- �߰��ϱ� ��ư -->
			<div id="add">
				<form>
					<input class="addMenu" id="btn-menu" type="button" value="�޴�����" />				
					<input class="addFrame" id="btn-add" type="button" value="�߰��ϱ�" />
				</form>
			</div>
		</div>
		<!-- �ϴ��� ������� ǥ�� �� -->
		<footer id="main_footer">
			<div id="footer_wrapper">
				<div class="footer-container">
					<div id="main_copyright"></div>
				</div>
			</div>
		</footer>
	</div>
</body>
</html>