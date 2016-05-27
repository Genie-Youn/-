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
<link rel="stylesheet"
	href="/admin/css/adder/enrollPage-style-proto.css">
<style type="text/css">
.modern {
	font-family: '�������', NanumGothic, '����', Dotum, AppleGothic, sans-serif;
	display: inline-block;
	font-weight: bold;
	/* margin: 10px;*/
	padding: 8px 15px;
	background: #599be5;
	border: 1px solid rgba(0, 0, 0, 0.15);
	border-radius: 4px;
	transition: all 0.3s ease-out;
	box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.5), 0 2px 2px
		rgba(0, 0, 0, 0.3), 0 0 4px 1px rgba(0, 0, 0, 0.2);
	/* Font styles */
	text-decoration: none;
	color: white;
}
</style>
<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/bootstrap.min.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/modernizr.custom.js"></script>
<script src="/admin/js/adder/adder-menu-script.js"></script>
<script src="/admin/js/adder/adder-editor-script.js"></script>
<script src="/admin/js/calendar/enroll-script.js"></script>

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

		<!-- �� ������ ���ϴ�. -->

		<div id="content_wrapper">
			<section id="main_section">

				<article id="editor_wrapper">
					<div id="editor_contents">
						<div id="editor_area">
							<script src="/admin/ckeditor/ckeditor.js"></script>

							<!-- 							��ΰ� �����ִ� -->
							<form method="post" name='formsEditor'
								action='/calendar/calendarInsert.do' modelAttribute=info>

								<div id="adder_info_wrapper">
									<ul>
										<li>����</li>
										<li><input class="title_input" placeholder="������ �Է��ϼ���!"
											type="text" maxlength="20" name="title"></li>
										<!-- <li>
											<a class="cke_button_off cke_combo_button">
												<input type="button" class="go-preview-btn cke_combo_text" value="�̸�����">
											</a>
										</li> -->
										<li class='date-range-input'><input
											class="from-date-input" placeholder="���۳�¥" type="text"
											name="startDate"> ~ <input class="to-date-input"
											placeholder="���ᳯ¥" type="text" name="endDate"></li>
									</ul>
								</div>
								<textarea name="contents" id="editor1">:&lt;p&gt;&lt;span style="color:#D3D3D3"&gt;�̱��� ����� ���� �ۼ��Ͻñ� �ٶ��ϴ�.&lt;/span&gt;&lt;/p&gt;</textarea>
								<input type="hidden" name="continue" value="2">


								<!-- ������ �̿� �κ� -->
								<input type="hidden" name="tcp"> <input type="hidden"
									id="hidden_input_type" name="type" value=0>
								<!-- 								�߰��� �κ��ϼ�								 -->
								<input type="hidden" id="hidden_input_calID" name="calID">


								<!-- id�� ���� ���� �ָ��Ͽ� �ϴ� ������.. -->
								<div class="option-wrapper">
									<div class="row">
										<div class="col-xs-2">
											<span class="sub-title">�˾� ���</span>
										</div>
										<div class="col-xs-8">
											<input type="radio" name="popupEnable" value="00" />
											���&nbsp;&nbsp; <input type="radio" name="popupEnable"
												value="01" /> ����
										</div>
									</div>

									<hr class="form-delimiter" />

									<div class="row">
										<div class="col-xs-2">
											<span class="sub-title">�˸� ���</span>
										</div>
										<div class="col-xs-4">
											<input type="radio" name="alarmEnable" value="00" />
											���&nbsp;&nbsp; <input type="radio" name="alarmEnable"
												value="01" /> ����
										</div>

										<div class="col-xs-6">
											<div class="row">
												<div class="col-xs-4">
													<span class="sub-title">�˸� ���</span>
												</div>
												<div class="col-xs-7">
													<select name="alarmMethod" class="form-control input-sm">
														<option value="00">SMS
														<option value="01">E-Mail
														<option value="02">SMS and E-Mail
													</select>
												</div>
											</div>

											<br />

											<div class="row">
												<div class="col-xs-4">
													<span class="sub-title">�˸� �ֱ�</span>
												</div>
												<div class="col-xs-7">
													<select name="alarmPeriod" class="form-control input-sm">
														<option value="00">�ð�����
														<option value="01">�Ϸ縶��
														<option value="02">���ָ���
													</select>
												</div>
											</div>

											<br>
											<div class="row">
												<div class="col-xs-4">
													<span class="sub-title">����</span>
												</div>
												<!-- �̺�Ʈ ���� ���� �߰� / ������ -->
												<div class="col-xs-7">
													<select id="event_type" name="eventType"
														class="form-control input-sm">
														<option value="����">����
														<option value="����ǰ">����ǰ
														<option value="���">���
													</select>
												</div>
											</div>
										</div>
									</div>
								</div>


								<!-- <fieldset class="line" id="using_popup">
										<legend>�˾� ���</legend>
										<select name="popupEnable">
											<option value="00">���
												<option value="01">������� ����
										</select>
								</fieldset>
								
								<fieldset class="line" id="using_alarm">
									<legend>�˶� ���</legend>
									<select name="alarmEnable">
										<option value="00">���
										<option value="01">������� ����
									
									</select>
								</fieldset>
								
								<fieldset class="�˶� ���� ���">
									<legend>alarmMethod</legend>
									<select name="alarmMethod">
											<option value="00">SMS
											
											<option value="01">E-Mail
											
											<option value="02">SMS and E-Mail
									
										</select>
								</fieldset> 
								
								<fieldset class="line">
									<legend>�˶� �ֱ�</legend>
									<select name="alarmPeriod">
										   <option value="00">�ð�����
										
											<option value="01">�Ϸ縶��
										
											<option value="02">���ָ���
								   
										</select>
								</fieldset>  -->
							</form>


							<script>
				                 CKEDITOR.replace( 'editor1' ,{
				                    filebrowserUploadUrl: '/uploader/upload.php',
				                    toolbarGroups : [
 							                        { name: 'basicstyles', groups: [ 'basicstyles'/* , 'cleanup'  */] },
 							                        { name: 'paragraph',   groups: [ 'list', 'align', 'bidi' ] },
 							                		{ name: 'styles' },
 							                		{ name: 'colors' },
 							                		{ name: 'clipboard',   groups: [ 'clipboard', 'undo' ] },
 							                		{ name: 'links' },
 							                		{ name: 'document',	   groups: [ 'mode', 'document', 'doctools' ] },
 							                		{ name: 'others' }
 							                	]
				                });

				                CKEDITOR.instances.editor1.config.height = 300;  // ���� �������� ������
				                CKEDITOR.instances.editor1.config.resize_minHeight = 300; // ���� ������ �ּҳ���
				                CKEDITOR.instances.editor1.config.resize_maxHeight = 680; // ���� ������ �ִ����
				                CKEDITOR.instances.editor1.config.extraPlugins = 'colorbutton,panelbutton,justify,font,owletmap';
								CKEDITOR.instances.editor1.config.enterMode = CKEDITOR.ENTER_BR;//CKEDITOR.ENTER_DIV; // inserts <div></div>
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
						<div id="button_area">
							<input type="button" value="���� �� ����"
								class="form-to-preview modern"> <input type="reset"
								value="���" class="form_reset modern"> <input
								type="button" id="go_preview_btn" value="�̸�����" class="modern">
							<input type="hidden" class="hidden-input" name="number" value="0">
						</div>
					</div>
				</article>

				<article id="viewer_wrapper">
					<div id="preview_wrapper">
						<div id="mobile_locator"></div>
					</div>
					<div id="preview_btn_wrapper">
						<input type="button" id="return_preview_btn" value="���ư���"
							class="modern">
					</div>
				</article>

				<div id="editor_switch_interface" style="display: none">
					<ul>
						<li></li>
					</ul>
				</div>
			</section>
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
	</div>
</body>
</html>