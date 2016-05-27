<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Owlet5 Project</title>
<link rel="shortcut icon" href="/res/img/favicon.ico">
<link rel="stylesheet" href="/admin/css/crossbrowser.css">
<link rel="stylesheet" href="/admin/css/bootstrap.min.css">
<link rel="stylesheet" href="/admin/css/intro-menu-style.css">
<link rel="stylesheet" href="/admin/css/dataTables.jqueryui.css">
<link rel="stylesheet" href="/admin/css/common-style.css">
<link rel="stylesheet" href="/admin/css/jquery.dataTables.css">
<link rel="stylesheet" href="/admin/css/event/event.css">
<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/modernizr.custom.js"></script>
<script src="/admin/js/external/jquery/dataTables.jqueryui.js"></script>
<script src="/admin/js/jquery-1.11.1.min.js"></script>
<script src="/admin/js/external/jquery/jquery.dataTables.min.js"></script>
<script src="/admin/js/noticeList.js"></script>
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
							<li id="nav-menu"><a href="#" id="menu"></a></li>

						</ul>
					</div>
				</nav>
			</div>
		</div>
		<!-- ��� ���� �κ��� �� -->

		<!-- ��� �޴��� �ϴ� �������� ������ �����ϴ� ��� -->
		<div id="main_empty_filler"></div>


		<div id="content_wrapper">
			<table id="noticeTable" class="display" cellspacing="0" width="100%">
				<thead>
					<tr>
						<td>�۹�ȣ</td>
						<td>����</td>
						<td>����</td>
						<td>�Ⱓ</td>
						<td>����</td>
						<td>����</td>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td>�۹�ȣ</td>
						<td>����</td>
						<td>����</td>
						<td>�Ⱓ</td>
						<td>����</td>
						<td>����</td>
					</tr>
				</tfoot>
				<tbody>
					<c:forEach var="event" items="${eventList}">
						<tr>
							<td>${event.nIndex}</td>
							<td>${event.name}</td>
							<td>${event.type}</td>
							<td>${event.startDate}<br>${event.endDate}</td>
							<td><a
								href="/adder/eventEditPage.do?check=3&index=${event.nIndex}">����</a></td>
							<td><a
								href="/calendar/deleteCalendarInfo.do?calID=${event.nIndex}">����</a></td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
			<!-- �߰��ϱ� ��ư -->
			<div id="add">
				<form>
					<input class="addEvent" id="btn-eventadd" type="button"
						value="�߰��ϱ�" />
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

	</div>
</html>