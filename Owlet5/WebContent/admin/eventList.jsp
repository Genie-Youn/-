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
		<!-- 상단 고정 부분 -->

		<div id="header-wrapper">

			<!-- 로고를 표시합니다. -->

			<div id="main_header_wrapper">

				<header class="main-header">
					<div id="main_logo" title=""></div>

					<section class="header_button">
						<%
					Object sessionData = session.getAttribute("UserName");
				
					String name=(String)sessionData;
				%>

						<p class="loginName"><%=name %>
							님 환영합니다.
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
							<li id="nav-menu"><a href="#" id="menu"></a></li>

						</ul>
					</div>
				</nav>
			</div>
		</div>
		<!-- 상단 고정 부분의 끝 -->

		<!-- 상단 메뉴와 하단 컨텐츠의 여백을 설정하는 요소 -->
		<div id="main_empty_filler"></div>


		<div id="content_wrapper">
			<table id="noticeTable" class="display" cellspacing="0" width="100%">
				<thead>
					<tr>
						<td>글번호</td>
						<td>제목</td>
						<td>종류</td>
						<td>기간</td>
						<td>편집</td>
						<td>삭제</td>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td>글번호</td>
						<td>제목</td>
						<td>종류</td>
						<td>기간</td>
						<td>편집</td>
						<td>삭제</td>
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
								href="/adder/eventEditPage.do?check=3&index=${event.nIndex}">수정</a></td>
							<td><a
								href="/calendar/deleteCalendarInfo.do?calID=${event.nIndex}">삭제</a></td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
			<!-- 추가하기 버튼 -->
			<div id="add">
				<form>
					<input class="addEvent" id="btn-eventadd" type="button"
						value="추가하기" />
				</form>
			</div>
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
</html>