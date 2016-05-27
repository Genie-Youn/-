<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>TCP-Owlet5</title>
<link rel="shortcut icon" href="/res/img/favicon.ico">
<link rel="stylesheet" href="/admin/css/crossbrowser.css">
<link rel="stylesheet" href="/admin/css/bootstrap.min.css">
<link rel="stylesheet" href="/admin/css/jquery-ui.min.css">
<link rel="stylesheet" href="/admin/css/intro-menu-style.css">
<link rel="stylesheet" href="/admin/css/intro-super-dash-style.css">
<!-- <link rel="stylesheet" href="/admin/css/chart_intro_style.css"> -->
<link rel="stylesheet" href="/admin/css/common-style.css">
<link rel="stylesheet" href="/admin/css/chart/c3.css">
<link rel="stylesheet" type="text/css" href="/admin/css/chart.css">
<link rel="stylesheet" type="text/css" href="/admin/css/main-style.css">
<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/bootstrap.min.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/chart/d3.js"></script>
<script src="/admin/js/chart/c3.js"></script>
<script src="/admin/js/chart/charter.js"></script>
<script src="/admin/js/main.js"></script>
<script src="/admin/js/modernizr.custom.js"></script>



</head>
<body>
<div id="loading_icon_locator"></div>
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
							<button class="btn btn-1">Ķ����</button>
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
						<li id="nav-menu" class="active">
							<a href="#" id="menu"></a>
						</li>
						<li id="nav-menu">
							<a href="#" id="menu"></a>
						</li>
						<li id="nav-menu">
							<a href="#" id="menu"></a>
						</li>
						<li id="nav-menu">
							<a id="menu-unabled" ></a>
						</li>
						<li id="nav-menu">
							<a id="menu-unabled" ></a>
						</li>
						<li id="nav-menu">
							<a id="menu-unabled" ></a>
						</li>
						</ul>
					</ul>
				</div>
			</nav>
		</div>
	</div>
	<!-- ��� ���� �κ��� �� -->
	
	
	<!-- ��� �޴��� �ϴ� �������� ������ �����ϴ� ��� -->
	
	<div class="wrapper">
		<div class="content-head">
			<%
				String storeName = (String)session.getAttribute("StoreName");
			%>
			<div class="main-title"><%=storeName %> ��Ȳ</div>
			<div class="sub-title">�ֱ� �������� �ֱ�� ǥ�õ˴ϴ�.</div>
			<!-- <div class="right">
				<div>�������� �̺�Ʈ <b class="emphasis">2</b>��</div>
				<a class="btn lightblue" href="#">�ٷΰ���</a>
			</div> -->
		</div>
		<div class="content-wrapper">
			<div class="content-head">
				<div class="main-title">��� ���</div>
				<div class="sub-title">�ֱ� ������ �� ��ü ��Ȳ�Դϴ�.</div>
			</div>
			<div class="content-body">
				<!-- <div id="super-summar-wrapper"> -->
					<!-- <div id="summary_wrapper">
						<div class="summary-unit">
							<a href="#">
								<i class="summary-icon-user-count">
								</i>
								<div class="info visit-summary">
									<div class="status">
										�湮�� ��<br>
										<b class="tile-status"></b> ��
									</div>
								</div>
							</a>
						</div>
					</div> -->
					<div id="summary-wrapper">
						<div class="summary-unit">
							<a href="#">
								<i class="summary-icon-user-count">
								</i>
								<div class="summary-content">
									�湮�� ��<br>
									<b class="emphasis">0</b> ��
								</div>
							</a>
						</div>
						<div class="summary-unit">
							<a href="#">
								<i class="summary-icon-user-count2">
								</i>
								<div class="summary-content">
									��� ����<br>
									<b class="emphasis">0</b> / 5.0
								</div>
							</a>
						</div>
						<div class="summary-unit">
							<a href="#">
								<i class="summary-icon-user-count3">
								</i>
								<div class="summary-content">
									��� ��<br>
									<b class="emphasis">0</b> ��
								</div>
							</a>
						</div>
						<div class="summary-unit">
							<a href="#">
								<i class="summary-icon-user-count4">
								</i>
								<div class="summary-content">
									�� ����<br>
									<b class="emphasis">0</b> ����
								</div>
							</a>
						</div>
					</div>
				<!-- </div> -->
			</div>
		</div
		><div class="content-wrapper half-width">
			<div class="content-head">
				<div class="main-title">�湮�� ��</div>
				<div class="right">
					<a class="btn gray no-margin" href="/analysis/list.do?idx=0">+������</a>
				</div>
			</div>
			<div class="content-body">
				<div class="chart"></div>
			</div>
		</div
		><div class="content-wrapper half-width">
			<div class="content-head">
				<div class="main-title">����</div>
				<div class="right">
					<a class="btn gray no-margin" href="/analysis/list.do?idx=1">+������</a>
				</div>
			</div>
			<div class="content-body">
				<div class="chart"></div>
			</div>
		</div
		><div class="content-wrapper">
			<div class="content-head">
				<div class="main-title">���</div>
				<div class="right">
					<a class="btn gray no-margin" href="/analysis/list.do?idx=2">+������</a>
				</div>
			</div>
			<div class="content-body">
				<div class="chart"></div>
			</div>
		</div
		><div class="content-wrapper">
			<div class="content-head">
				<div class="main-title">��ǰ�� �Ǹŷ�</div>
				<div class="right">
					<a class="btn gray no-margin" href="/analysis/list.do?idx=3">+������</a>
				</div>
			</div>
			<div class="content-body">
				<div class="chart"></div>
			</div>
		</div>
	</div>
	
	<!-- <div id="main_empty_filler">
	</div>
	�� ������ ���ϴ�.
	
	���� �簢���� ��� ���� ����
		<div id="super-summar-wrapper">
			<div id="summary_wrapper">
				<div class="summary-unit summary-color-red">
					<a href="#">
						<i class="summary-icon-user-count">
						</i>
						<div class="info visit-summary">
							<div class="status">
								�湮�� ��<br>
								<b class="tile-status"></b> ��
							</div>
						</div>
					</a>
				</div>
				<div class="summary-unit summary-color-red">
					<a href="#">
						<i class="summary-icon-user-count2">
						</i>
						<div class="info visits-summary">
							<div class="status">
								�湮Ƚ��<br>
								<b class="tile-status"></b> ȸ
							</div>
						</div>
					</a>
				</div>
				<div class="summary-unit summary-color-purple">
					<a href="#">
						<i class="summary-icon-user-count3">
						</i>
						<div class="info download-summary">
							<div class="status">
								�� �ٿ�ε� ��<br>
								<b class="tile-status"></b> ȸ
							</div>
						</div>
					</a>
				</div>
				<div class="summary-unit summary-color-green">
					<a href="#">
						<i class="summary-icon-user-count4">
						</i>
						<div class="info coupon-summary">
							<div class="status">
								���/���� ���� ��<br>
								<b class="tile-status"></b>/800 ��
							</div>
						</div>
					</a>
				</div>
				<div class="summary-unit summary-color-orange">
					<a href="#">
						<i class="summary-icon-user-count5">
						</i>
						<div class="info event-summary">
							<div class="status">
								���� ��
							</div>
						</div>
					</a>
				</div>
			</div>
		</div>
		
		���� ��
	
	<div id="content_wrapper">
		
		
		��Ʈ�� ����
		<section id="main_section">
		<div class="content-structure-wrapper">
			<div class="chart-slot">				
				<div class="chart-widget red">
					<div class="slot-title">
						<h4>
							<i class="slot-icon but-no-prefix"></i> �湮�ڼ�
						</h4>
						<span class="tools">
						 	<a href="javascript:;" class="slot-icon-down"></a>
							<a href="javascript:;" class="slot-icon-delete"></a> 
						</span>
					</div>
					<div class="slot-body">
						<div class="text-center">
								<div id='visit_chart_div'>
									<div class="c3chart"></div>
								</div>
						</div>
					</div>
				</div>
			</div>
			<div class="chart-slot">
				BEGIN C P
				<div class="chart-widget blue">
					<div class="slot-title">
						<h4>
							<i class="slot-icon but-no-prefix"></i> �湮 Ƚ��
						</h4>
						<span class="tools">
							<a href="javascript:;" class="slot-icon-down"></a>
							<a href="javascript:;" class="slot-icon-delete"></a>
						</span>
					</div>
					<div class="slot-body">
						<div class="text-center">
								<div id='down_chart_div'>
									<div class="c3chart"></div>
								</div>
						</div>
					</div>
				</div>
			</div>
			
		</div>
		<div class="content-structure-wrapper">
			<div class="chart-slot" style="display:none">				
				<div class="chart-widget blue">
					<div class="slot-title">
						<h4>
							<i class="slot-icon"></i> �ð��� �湮�� ��
						</h4>
						<span class="tools">
							<a href="javascript:;" class="slot-icon-down"></a>
							<a href="javascript:;" class="slot-icon-delete"></a>
						</span>
					</div>
					<div class="slot-body">
						<div class="text-center">
								<div id='hours_chart_div'></div>
								<div id='table_hours_div'></div>						
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="content-structure-wrapper">
			<div class="chart-slot">	
				<div class="chart-widget green">
					<div class="slot-title">
						<h4>
							<i class="slot-icon but-no-prefix"></i> ������ ��ȸ��
						</h4>
						<span class="tools">
							<a href="javascript:;" class="slot-icon-down"></a>
							<a href="javascript:;" class="slot-icon-delete"></a>
						</span>
					</div>
					<div class="slot-body">
						<div class="text-center">
								<div id='view_chart_div'>
									<div class="c3chart"></div>
								</div>
								<div id='table_view_div'></div>
						</div>
					</div>
				</div>
			</div>
			<div class="chart-slot">	
				<div class="chart-widget orange">
					<div class="slot-title">
						<h4>
							<i class="slot-icon but-no-prefix"></i> �� �ٿ�ε� ��
						</h4>
						<span class="tools">
							<a href="javascript:;" class="slot-icon-down"></a>
							<a href="javascript:;" class="slot-icon-delete"></a>
						</span>
					</div>
					<div class="slot-body">
						<div class="text-center">
								<div id='coupon_chart_div'>
									<div class="c3chart"></div>
								</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="content-structure-wrapper">
			<div class="chart-slot-long"> or maybe chart-slot for short type slot	
				<div class="chart-widget orange">
					<div class="slot-title">
						<h4>
							<i class="slot-icon"></i> ���� ��볻��
						</h4>
						<span class="tools">
							<a href="javascript:;" class="slot-icon-down"></a>
							<a href="javascript:;" class="slot-icon-delete"></a>
						</span>
					</div>
					<div class="slot-body">
						<div class="text-center">
								<div id='coupon_chart_div'></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="content-structure-wrapper">
			<div class="chart-slot-long">	
				<div class="chart-widget purple">
					<div class="slot-title">
						<h4>
							<i class="slot-icon but-no-prefix"></i> ���� ��볻��
						</h4>
						<span class="tools">
							<a href="javascript:;" class="slot-icon-down"></a>
							<a href="javascript:;" class="slot-icon-delete"></a>
						</span>
					</div>
					<div class="slot-body" id="coupon-chart-slot">
						<div class="text-center">
								<div id='coupon_chart_div' class="half-width">
									<div class="c3chart"></div>
								</div>
								<div id='pie_chart_div' class="half-width">
									<div id="pie-chart-frame">
											<div class="c3chart"></div>
									</div>
								</div>
						</div>
						</div>
					</div>
				</div>
			</div>
			<div class="chart-slot">	
				<div class="chart-widget yellow">
					<div class="slot-title">
						<h4>
							<i class="slot-icon but-no-prefix"></i> �⺻����
						</h4>
						<span class="tools">
							<a href="javascript:;" class="slot-icon-down"></a>
							<a href="javascript:;" class="slot-icon-delete"></a>
						</span>
					</div>
					<div class="slot-body">
						<div class="text-center namecard-size-restrict">
								<div id="card-container">
								    <div id="card">
								        <div class="back">Logo<br>Image</div>
								        
								        <div class="front">
								        
								        
									        <div id="name_card">
											  <div class="namecard-container">
											    <p class="namecard-name-slot">����</p>
											    <p class="namecard-address-slot">����</p>
											    
											    <div class="namecard-contact-slot">
											    	<ul>
												        <li><a >ȸ���</a>
												        <li><a >http://www.xml.cu.kr</a>
												        <li><a >###-####-####</a></li>
											        </ul>
											    </div>
											    <div class="namecard-background-slot"></div>
											  </div>  
											</div>
											
											<div class="namecard-cutter-tl">
										
											</div>
											
											<div class="namecard-cutter-br">
										
											</div>
											
								        </div>
								    </div>
								</div>
						</div>
					</div>
				</div>
				END C P
			</div>
		</div>
		��Ʈ�� ��
			
		</section>
	</div> -->

	<!-- �ϴ��� ������� ǥ�� �� -->
	
<!--  	<div id="footer_wrapper">
		<footer id="main_footer">
			<div class="footer-container">
				<div id="main_copyright">
				    Copyright &copy; 2014 <b>TCP</b> All Rights Reserved.<br>
					Owlet5 is a web project of TCP club in Computer Science and Engineering ,
					 Seoul National University of Science and Technology.
				</div>
			</div>
		</footer>
	</div>-->

	<footer id="main_footer">
		<div id="footer_wrapper">
			<div class="footer-container">
					<div id="main_copyright">
					</div>
				</div>
			</div>
	</footer>
	</div>
<!-- <script src="/admin/js/chart-dash-function.js" charset="utf-8"></script>-->
</body>
</html>