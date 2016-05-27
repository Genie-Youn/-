<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>TCP-��ú���. ȯ���մϴ�.</title>

<link rel="stylesheet" href="/admin/css/jquery-ui.min.css">
<link rel="stylesheet" href="/admin/css/intro-menu-style.css">
<link rel="stylesheet" href="/admin/css/intro-super-dash-style.css">
<link rel="stylesheet" href="/admin/css/chart_intro_style.css">
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/chart-common-function.js" charset='utf-8'></script>
<script src="/admin/js/modernizr.custom.js"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>



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
				<p class="mypage">
					<button class="btn btn-1 btn-1c">myPage</button>
					</p>
				<p class="logout">
					<button class="btn btn-1 btn-1c">logout</button>
					</p>
				</section>
				
				
			</header>
		</div>
		
		
		<!-- �޴��� ǥ���˴ϴ�. -->
		
		
		<div id="navigator_wrapper">
			<nav id="navigator">
				<div id="nav_frame" class="nav-frame">
					<ul class="clearfix">
						<li class="nav-button nav-btn-home focus">
								<a href="#" class="a-home">
								</a>
						</li>
						<li class="nav-button nav-btn-notice">
								<a href="#" class="a-notice">
								</a>
						</li>
						<li class="nav-button nav-btn-adder">
								<a href="#" class="a-adder">
								</a>
						</li>
						<li class="nav-button nav-btn-preview2">
								<a href="#" class="a-preview">
								</a>
						</li>
						<li class="nav-button nav-btn-stats">
								<a href="#" class="a-stats">
								</a>
						</li>
						<li class="nav-button nav-btn-logo">
								<a href="#" class="a-logo">
								</a>
						</li>
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
	
	<!-- ���� �簢���� ��� ���� ���� -->
		<div id="super-summar-wrapper">
			<div id="summary_wrapper">
				<div class="summary-unit summary-color-red">
					<a href="#">
						<i class="summary-icon-user-count">
						</i>
						<div class="info">
							321
						</div>
						<!-- <div class="status">
							���� �湮�� ��
						</div> -->
					</a>
				</div>
				<div class="summary-unit summary-color-red">
					<a href="#">
						<i class="summary-icon-user-count2">
						</i>
						<div class="info">
							321,443
						</div>
						<!-- <div class="status">
							���� �湮Ƚ��
						</div> -->
					</a>
				</div>
				<div class="summary-unit summary-color-purple">
					<a href="#">
						<i class="summary-icon-user-count3">
						</i>
						<div class="info">
							100,000,000
						</div>
						<!-- <div class="status">
							���� �� �ٿ� ��
						</div> -->
					</a>
				</div>
				<div class="summary-unit summary-color-green">
					<a href="#">
						<i class="summary-icon-user-count4">
						</i>
						<div class="info">
							3234
						</div>
						<!-- <div class="status">
							�̺�Ʈ ��
						</div> -->
					</a>
				</div>
				<div class="summary-unit summary-color-orange">
					<a href="#">
						<i class="summary-icon-user-count5">
						</i>
						<div class="info">
							50,000
						</div>
						<!-- <div class="status">
							���� ��
						</div> -->
					</a>
				</div>
			</div>
		</div>
		
		<!-- ���� �� -->
	
	<div id="content_wrapper">
		
		
		<!-- ��Ʈ�� ���� -->
		<section id="main_section">
		<div class="content-structure-wrapper">
			<div class="chart-slot">				
				<div class="chart-widget red">
					<div class="slot-title">
						<h4>
							<i class="slot-icon but-no-prefix"></i> �湮�ڼ�/�湮Ƚ��
						</h4>
						<span class="tools">

						</span>
					</div>
					<div class="slot-body">
						<div class="text-center">
								<div id='visit_chart_div'></div>
						</div>
					</div>
				</div>
			</div>
			<div class="chart-slot">
				<!-- BEGIN C P-->
				<div class="chart-widget blue">
					<div class="slot-title">
						<h4>
							<i class="slot-icon but-no-prefix"></i> �� �ٿ�ε�
						</h4>
						<span class="tools">

						</span>
					</div>
					<div class="slot-body">
						<div class="text-center">
								<div id='down_chart_div'></div>
						</div>
					</div>
				</div>
			</div>
			
		</div>
		<!-- <div class="content-structure-wrapper">
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
		</div> -->
		<div class="content-structure-wrapper">
			<div class="chart-slot">	
				<div class="chart-widget orange">
					<div class="slot-title">
						<h4>
							<i class="slot-icon but-no-prefix"></i> ���� ��볻��
						</h4>
						<span class="tools">

						</span>
					</div>
					<div class="slot-body">
						<div class="text-center">
								<div id='coupon_chart_div'></div>
						</div>
					</div>
				</div>
			</div>
			<div class="chart-slot">	
				<div class="chart-widget green">
					<div class="slot-title">
						<h4>
							<i class="slot-icon but-no-prefix"></i> ������ ��Ƚ��
						</h4>
						<span class="tools">

						</span>
					</div>
					<div class="slot-body">
						<div class="text-center-no">
								<div id='view_chart_div'></div>
								<div id='table_view_div'></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- <div class="content-structure-wrapper">
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
		</div> -->
		<div class="content-structure-wrapper">
			<div class="chart-slot">	
				<div class="chart-widget purple">
					<div class="slot-title">
						<h4>
							<i class="slot-icon"></i> �� ���ϴ� ��Ʈ�ΰ�.
						</h4>
						<span class="tools">

						</span>
					</div>
					<div class="slot-body">
						<div class="text-center">
								<div id='pie_chart_div'></div>
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
											    <p class="namecard-name-slot">���Ϲ�</p>
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
				<!-- END C P-->
			</div>
		</div>
		<!-- ��Ʈ�� �� -->
			
		</section>
	</div>

	<!-- �ϴ��� ������� ǥ�� �� -->
	
	<div id="footer_wrapper">
		<footer id="main_footer">
			<div class="footer-container">
			<div id="main_copyright">
					Copyright &copy; 2014 <b>TCP</b> All Rights Reserved.<br>
					Owlet5 is a web project of TCP club in Computer Science and Engineering ,
					 Seoul National University of Science and Technology.
				</div>
			</div>
		</footer>
	</div>
</div>
<script src="/admin/js/chart-dash-function.js" charset="utf-8"></script>
</body>
</html>