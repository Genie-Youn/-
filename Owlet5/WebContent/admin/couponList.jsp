<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="EUC-KR"%>
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
<link rel="stylesheet" href="/admin/css/calendar/calendar-delete-style.css">
<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/modernizr.custom.js"></script>
<script src="/admin/js/external/jquery/dataTables.jqueryui.js"></script>
<script src="/admin/js/jquery-1.11.1.min.js"></script>
<script src="/admin/js/external/jquery/jquery.dataTables.min.js"></script>
<!--  <script src="/admin/js/coupon/couponList.js"></script> -->
<script src="/admin/js/calendar/calendar-delete-script.js"></script>
<script src="/admin/js/bootstrap.min.js"></script>
</head>
<body>
<div id="page_wrapper">
		<!-- ��� ���� �κ� -->

		<div id="header-wrapper">

			<!-- �ΰ� ǥ���մϴ�. -->

			<div id="main_header_wrapper">
			<header class="main-header">
				<div id="main_logo"title="ȯ���մϴ�."></div>
				
				<section class="header_button">
				
				<%
					Object sessionData = session.getAttribute("UserName");
				
					String name=(String)sessionData;
				%>
				
				<p class="loginName"><%=name %> ��</p>
				
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
						<li id="nav-menu">
							<a href="#" id="menu"></a>
						</li>
						<li id="nav-menu">
							<a href="#" id="menu"></a>
						</li>
						<li id="nav-menu">
							<a href="#" id="menu"></a>
						</li>
						<li id="nav-menu" class="active">
							<a href="#" id="menu"></a>
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
		
		
		<div id="content_wrapper">
			<section id="main_section">
			<form method=POST action="/calendar/bulkDelete.do">
			<input type="hidden" name="tcp">
			
			<!-- <input type="checkbox" name="all" class="check-all"> <label>Check All</label> -->
			<input type="button" class="delete-all-btn" value="��ü ����" onclick="return false;">
			<input type="button" class="delete-part-btn" value="���� ����" onclick="return false;">
			<input type="button" class="came-back-btn" value="���ư���" onclick="return false;">
			
				
				<div class="item-container">
					<c:forEach var="coupon" items="${couponList}" varStatus="loop">
						<div class="item-slot" data-index="${loop.index}">
							<div class="x-btn"> X </div>
							<div class="title">${coupon.name}</div>
							<div class="content">${coupon.name}</div>
							<div class="values">${coupon.genIndex}</div>
							<div class="start-date">${coupon.startDate}</div>
							<div class="end-date">${coupon.endDate}</div>
						</div>
					</c:forEach>
				</div>
			</form>
			</section>
		</div>
		
	<!--  	<div id="content_wrapper">
				<table id="couponTable" class="display" cellspacing="0" width="100%">
					<thead>
						<tr>
							<td>����</td>
							<td>��������</td>
							<td>��������</td>
							<td>��������</td>
							<td>����</td>
							<td>����</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td>����</td>
							<td>��������</td>
							<td>��������</td>
							<td>��������</td>
							<td>����</td>
							<td>����</td>
						</tr>
					</tfoot>
					<tbody>
					<c:forEach var="coupon" items="${couponList}">
							<tr>
								<td>${coupon.name}</td>
								<td>${coupon.type}</td>
								<td>${coupon.startDate}</td>
								<td>${coupon.endDate}</td>
								<td><a
									href="/event/coupon.do?index=${coupon.genIndex}&ptype=0-0">����</a></td>
								<td><a href="/calendar/deleteCalendarInfo.do?calID=${coupon.genIndex}">����</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table> -->
			<!-- �߰��ϱ� ��ư -->
			<!--  <div id="add">
				<form>
					<input class="addCoupon" id="btn-couponadd" type="button" value="�߰��ϱ�" />
				</form>
			</div>
		</div> -->
		<!-- �ϴ��� ������� ǥ�� �� -->

		<footer id="main_footer">
			<div id="footer_wrapper">
				<div class="footer-container">
					<div id="main_copyright"></div>
				</div>
			</div>
		</footer>
	</div>
</html>