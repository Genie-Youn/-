<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TCP-��� ������.</title>
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
	<!-- ��� ���� �κ��� �� -->
	
	
	<!-- ��� �޴��� �ϴ� �������� ������ �����ϴ� ��� -->
	
	<div id="main_empty_filler">
	</div>
	
	<!-- �� ������ ���ϴ�. -->
	
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
					<div class="moving-slot slot2 <% if (idx == 0) out.print("selected-slot"); %>" data-chart-name="�湮�� ��"></div>
					<div class="moving-slot slot3 <% if (idx == 1) out.print("selected-slot"); %>" data-chart-name="����"></div>
					<!-- <div class="moving-slot slot4 selected-slot" data-chart-name="�ð��� ������ ��"></div> -->
					<div class="moving-slot slot4 <% if (idx == 2) out.print("selected-slot"); %>" data-chart-name="��� ��"></div>
					<div class="moving-slot slot5 <% if (idx == 3) out.print("selected-slot"); %>" data-chart-name="��ǰ�� �Ǹŷ�"></div>
					<!-- <div class="moving-slot slot6" data-chart-name="���� ��볻��"></div> -->
					
					<!--  -->
					
					<div class="gradient-hider"></div>
					
					<div class="slot-left-mover"></div>
					<div class="slot-right-mover"></div>
					
					<div class="chart-title-container">
						<%
							switch (idx) {
							case 0: out.print("�湮�� ��"); break;
							case 1: out.print("����"); break;
							case 2: out.print("��� ��"); break;
							case 3: out.print("��ǰ�� �Ǹŷ�"); break;
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
							
							<!-- <button class='date-span-btn'>�Ⱓ</button> -->
							<button class='date-span-btn btn'>��</button>
							<button class='date-span-btn btn'>��</button>
							<button class='date-span-btn btn'>��</button>
							<input type="submit" class="date-span-btn search-btn btn" value="��ȸ"></input>
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

	<!-- �ϴ��� ������� ǥ�� �� -->
	
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