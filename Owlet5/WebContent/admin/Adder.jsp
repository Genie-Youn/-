<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TCP-Owlet5</title>

<link rel="shortcut icon" href="/res/img/favicon.ico">
<link rel="stylesheet" href="/admin/css/crossbrowser.css">
<link rel="stylesheet" href="/admin/css/bootstrap.min.css">
<link rel="stylesheet" href="/admin/css/jquery-ui.min.css">
<link rel="stylesheet" href="/admin/css/intro-menu-style.css">
<link rel="stylesheet" href="/admin/css/common-style.css">
<link rel="stylesheet" href="/admin/css/adder/adder-menu-style.css">
<style type="text/css">
.modern {
	font-family: '나눔고딕', NanumGothic, '돋움', Dotum, AppleGothic, sans-serif;
	display: inline-block;
	font-weight: bold;
	/* margin: 10px;*/
	padding: 8px 15px;
	background: #599be5;
	border: 1px solid rgba(0,0,0,0.15);
	border-radius: 4px;
	transition: all 0.3s ease-out;
	box-shadow: inset 0 1px 0 rgba(255,255,255,0.5), 0 2px 2px rgba(0,0,0,0.3), 0 0 4px 1px rgba(0,0,0,0.2);
	/* Font styles */
	text-decoration: none;
	color:white;
}
</style>
<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/bootstrap.min.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/modernizr.custom.js"></script>
<script src="/admin/js/adder/adder-menu-script.js"></script>
<script class="doksoft_maps_google" src="http://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places,weather"></script>
<script class="doksoft_maps_google" src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/markerwithlabel/src/markerwithlabel_packed.js"></script>
<script src="/admin/js/adder/adder-editor-script.js"></script>
</head>
<body>
<div id="loading_icon_locator" style="display:block;"></div>
<div id="page_wrapper" style="display:none;">


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
							<a href="#" id="menu"></a>
						</li>
						<li id="nav-menu">
							<a href="#" id="menu"></a>
						</li>
						<li id="nav-menu">
							<a href="#" id="menu"></a>
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
		
		<article id="editor_wrapper">
			<div id="editor_contents">
				<div id="editor_area">
							<script src="/admin/ckeditor/ckeditor.js"></script>
							
							<form method="post" name='formsEditor' action='/adder/insertFrame.do' modelAttribute=frameInfo>
							
								<div id="adder_info_wrapper">
									<ul>
										<li id="title">제목</li>
										<li><input class="title_input" placeholder="제목을 입력하세요!" type="text" maxlength="20" name="title"></li>
										<!-- <li>
											<a class="cke_button_off cke_combo_button">
												<input type="button" class="go-preview-btn cke_combo_text" value="미리보기">
											</a>
										</li> -->
									</ul>
								</div>
								<textarea name="content" id="editor1"></textarea>
								<input type="hidden" name="continue" value="2">
								<input type="hidden" id="hidden_index" name="index" value=0>
							</form>
							
							
							<script>
				                 CKEDITOR.replace( 'editor1' ,{
				                    filebrowserUploadUrl: '/uploader/upload.php'
				                });

				                CKEDITOR.instances.editor1.config.height = 580;
				                CKEDITOR.instances.editor1.config.resize_minHeight = 300;
				                CKEDITOR.instances.editor1.config.resize_maxHeight = 680;
				                CKEDITOR.instances.editor1.config.extraPlugins = 'colorbutton,panelbutton,justify,font,owletmap,doksoft_maps';
								CKEDITOR.instances.editor1.config.extraAllowedContent = 'iframe img;*{*}[*](*)';
								CKEDITOR.instances.editor1.config.enterMode = CKEDITOR.ENTER_DIV;//CKEDITOR.ENTER_DIV; // inserts <div></div>
								CKEDITOR.instances.editor1.on('change', function() {isCkeditorContentChanged = true;});
								
				                $(document).ready(function(){
					                $('#submit_btn').bind("click", function(){
					                	var data = CKEDITOR.instances.editor1.getData();
					                	console.log(data);
					                	return false;
					                });
				                });
				            </script>
				            
				            
						</div>
				<div id="button_area" >
					<input type="button" value="저장 후 계속" class="form-to-adder modern">
					<input type="button" value="저장 후 종료" class="form-to-preview modern">
					<input type="reset" value="취소" class="form_reset modern">
					<input type="button" id="go_preview_btn" value="미리보기" class="modern">
					<input type="hidden" class="hidden-input" name="number" value="0">
				</div>
			</div>
		</article>
		
		<article id="viewer_wrapper">
			<div id="preview_wrapper">
				<div id="mobile_locator">
				</div>
			</div>
			<div id="preview_btn_wrapper">
				<input type="button" id="return_preview_btn" value="돌아가기" class="modern">
			</div>
		</article>
		
		<div id="editor_switch_interface" style="display:none">
			<ul>
				<li></li>
			</ul>
		</div>
		</section>
	</div>

	<!-- 하단의 약관정보 표현 부 -->
	
	<footer id="main_footer">
		<div id="footer_wrapper">
			<div class="footer-container">
					<div id="main_copyright">
					</div>
				</div>
			</div>
	</footer>
</div>
</body>
</html>