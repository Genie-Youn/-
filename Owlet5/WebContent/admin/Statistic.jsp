<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TCP-통계 페이지.</title>
<link rel="shortcut icon" href="/res/img/favicon.ico">
<link rel="stylesheet" href="/admin/css/crossbrowser.css">
<link rel="stylesheet" href="/admin/css/bootstrap.min.css">
<link rel="stylesheet" href="/admin/css/jquery-ui.min.css">
<link rel="stylesheet" href="/admin/css/intro-menu-style.css">
<link rel="stylesheet" href="/admin/css/common-style.css">
<link rel="stylesheet" href="/admin/css/statistic/statistic-position-locator.css">
<link rel="stylesheet" href="/admin/css/chart/c3.css">
<link rel="stylesheet" type="text/css" href="/admin/css/chart.css">
<link rel="stylesheet" href="/admin/css/statistic.css">

<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/bootstrap.min.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/modernizr.custom.js"></script>
<script src="/admin/js/common-url-requester.js"></script>
<!-- <script type="text/javascript" src="https://www.google.com/jsapi"></script> -->
<script src="/admin/js/chart/d3.js"></script>
<script src="/admin/js/chart/c3.js"></script>
<script src="/admin/js/chart/charter.js"></script>
<!-- <script src="/admin/js/statistic.js"></script> -->


</head>
<body>
<div id="loading_icon_locator"></div>
<div id="page_wrapper">


	<!-- 상단 고정 부분 -->
	
	<div id="header-wrapper">
	
	
		<!-- 로고를 표시합니다. -->
		
		
		<div id="main_header_wrapper">
			<header class="main-header">
				<div id="main_logo" title="환영합니다."></div>
				
				
				<section class="header_button">
				
				<%
					Object sessionData = session.getAttribute("UserName");
				
					String name=(String)sessionData;
				%>
				
				<p class="loginName"><%=name %> 님</p>
				
				<p class="cal">
							<button class="btn btn-1">달력</button>
						</p>				
				<p class="mypage">
					<button class="btn btn-1">마이페이지</button>
					</p>
				<p class="logout">
					<button class="btn btn-1">로그아웃</button>
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
						<li id="nav-menu" class="active">
							<a href="#" id="menu"></a>
						</li>
						<li id="nav-menu">
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
					</ul>
				</div>
			</nav>
		</div>
	</div>
	<!-- 상단 고정 부분의 끝 -->
	
	
	<!-- 상단 메뉴와 하단 컨텐츠의 여백을 설정하는 요소 -->
	
	<div id="main_empty_filler">
	</div>
	
	<!-- 글 내용이 들어갑니다. -->
	
	<div id="content_wrapper">
		<section id="main_section">
				<div id="chart_slider">
					<%
						
						String index = request.getParameter("idx");
						int idx = 2;
						if (index != null) {
							idx = Integer.parseInt(index);
						}
					%>
					<div class="moving-slot slot2 <% if (idx == 0) out.print("selected-slot"); %>" data-chart-name="방문자 수"></div>
					<div class="moving-slot slot3 <% if (idx == 1) out.print("selected-slot"); %>" data-chart-name="별점"></div>
					<!-- <div class="moving-slot slot4 selected-slot" data-chart-name="시간별 접속자 수"></div> -->
					<div class="moving-slot slot4 <% if (idx == 2) out.print("selected-slot"); %>" data-chart-name="댓글 수"></div>
					<div class="moving-slot slot5 <% if (idx == 3) out.print("selected-slot"); %>" data-chart-name="상품별 판매량"></div>
					<!-- <div class="moving-slot slot6" data-chart-name="쿠폰 사용내역"></div> -->
					
					<!--  -->
					
					<div class="gradient-hider"></div>
					
					<div class="slot-left-mover"></div>
					<div class="slot-right-mover"></div>
					
					<div class="chart-title-container">
						<%
							switch (idx) {
							case 0: out.print("방문자 수"); break;
							case 1: out.print("별점"); break;
							case 2: out.print("댓글 수"); break;
							case 3: out.print("상품별 판매량"); break;
							}
						%>
					</div>
					<div class="chart-interface-container">
					
						<form id="dataSelector" name='dataSelector' method='post' action='#'>
						<!-- <span class="datespan-viewer">
							2012/202/20-2/202.32/323
						</span> -->
						<input type='hidden' class='input-hidden' name='startDate' value=''>
						<input type='hidden' class='input-hidden' name='endDate' value=''>
						<input type='hidden' class='input-hidden' name='period' value=''>
						<div class="calendar-locator">
							<input type="text" class="from-date-input"> ~ <input type="text" class="to-date-input">
						</div>
							&nbsp;&nbsp;&nbsp;
							
							<!-- <button class='date-span-btn'>기간</button> -->
							<button class='date-span-btn btn'>일</button>
							<button class='date-span-btn btn'>주</button>
							<button class='date-span-btn btn'>월</button>
							<input type="submit" class="date-span-btn search-btn btn" value="조회"></input>
						</form>
					
					</div>
					
					<div class="goto-mover-wrapper">
						<div class="goto-mover move2"></div>
						<div class="goto-mover move3"></div>
						<div class="goto-mover move4 selected-goto-mover"></div>
						<div class="goto-mover move5"></div>
						<!-- <div class="goto-mover move6"></div> -->
					</div>
				
			</div>
			
			<div id="table_viewer">
			
				<div class="chart"></div>
			
				<div class="table"></div>
				
				<div class="algebra"></div>
			</div>
			
		</section>
	</div>

	<!-- 하단의 약관정보 표현 부 -->
	
	<footer id="main_footer">
			<div id="footer_wrapper">
				<div class="footer-container">
					<div id="main_copyright"></div>
				</div>
			</div>
	</footer>
</div>
<script src="/admin/js/statistic/statistic-table-requester.js"></script>
</body>
</html>