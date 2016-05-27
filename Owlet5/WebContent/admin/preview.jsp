<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TCP-Owlet5</title>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
<link rel="shortcut icon" href="/res/img/favicon.ico">
<link rel="stylesheet" href="/admin/css/jquery-ui.min.css">
<link rel="stylesheet" href="/admin/css/intro-menu-style.css">
<link rel="stylesheet" href="/admin/css/preview/preview.css">
<link rel="stylesheet" href="/admin/css/preview/nestable.css" />
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/modernizr.custom.js"></script>
<script src="/admin/js/preview/preview.js"></script>


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
						<li class="nav-button nav-btn-home">
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
						<li class="nav-button nav-btn-preview2 focus" >
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

		<div id="main_empty_filler"></div>

		<!-- �� ������ ���ϴ�. -->
		<div class="container">
			<section class="tabs">
				<input id="tab-1" type="radio" name="radio-set"
					class="tab-selector-1" checked="checked" /> <label for="tab-1"
					class="tab-label-1">�޴���ġ</label> <input id="tab-2" type="radio"
					name="radio-set" class="tab-selector-2" /> <label for="tab-2"
					class="tab-label-2">�޴�����</label> <input id="tab-3" type="radio"
					name="radio-set" class="tab-selector-3" /> <label for="tab-3"
					class="tab-label-3">�޴�����</label> <input id="tab-4" type="radio"
					name="radio-set" class="tab-selector-4" /> <label for="tab-4"
					class="tab-label-4">��Ų����</label>

				<div class="clear-shadow"></div>

				<div class="content">
					<div class="content-1">
						<div>
							<div class="select-mobile">
								<form>
									<div class="select-radio">
										<h3>
											<input type="radio" name="chk" value="0" checked /> ��������
										</h3>
									</div>
									<div class="select-radio">
										<h3>
											<input type="radio" name="chk" value="1" /> ��������
										</h3>
									</div>
									<div class="select-radio">
										<h3>
											<input type="radio" name="chk" value="2" /> ������ġ
										</h3>
									</div>
								</form>
							</div>
							<div class="mobile-img">
								<img src="/res/img/preview/mobile1.gif" class="mobile" /> <img
									src="/res/img/preview/mobile2.gif" class="mobile" /> <img
									src="/res/img/preview/mobile3.gif" class="mobile" />
							</div>
							<div class="mobile-explain">
								<div class="mobile-ex">
									<h3>������ ������ �����Ǹ� ���� ������ �����մϴ�.</h3>
								</div>
								<div class="mobile-ex">
									<h3>�޴��� ������ ������ �����Ǹ� ���� ������ �����մϴ�.</h3>
								</div>
								<div class="mobile-ex">
									<h3>�޴��� ȭ���� ���ϴ� ��ġ�� �����Ӱ� ��ġ�մϴ�.</h3>
								</div>
							</div>
							<div class="buttonset">
								<div class="save-button">
									<input class="modern" type="button" value=" ���� " />
								</div>
								<div class="next-button">
									<input class="modern" type="button" value=" ���� " />
								</div>
							</div>
						</div>

					</div>
					<div class="content-2">
						<div class="cf nestable-lists">

							<div class="dd" id="nestable">
								<ol class="dd-list">
									<li class="dd-item" data-id="1">
										<div class="dd-handle">Item 1</div>
									</li>
									<li class="dd-item" data-id="2">
										<div class="dd-handle">Item 2</div>
									</li>
									<li class="dd-item" data-id="3">
										<div class="dd-handle">Item 3</div>
									</li>
									<li class="dd-item" data-id="4">
										<div class="dd-handle">Item 4</div>
									</li>
									<li class="dd-item" data-id="5">
										<div class="dd-handle">Item 5</div>
									</li>
									<li class="dd-item" data-id="6">
										<div class="dd-handle">Item 6</div>
									</li>
									<li class="dd-item" data-id="11">
										<div class="dd-handle">Item 11</div>
									</li>
									<li class="dd-item" data-id="12">
										<div class="dd-handle">Item 12</div>
									</li>
								</ol>
							</div>

							<div class="dd" id="nestable2">
								<div class="dd-empty"></div>
							</div>

						</div>

							<script src="/admin/js/preview/nestable.js"></script>
					
						<script>
								$(document).ready(function() {

									var updateOutput = function(e) {
										var list = e.length ? e : $(e.target), output = list.data('output');
										if (window.JSON) {
											output.val(window.JSON.stringify(list.nestable('serialize')));
											//, null, 2));
										} else {
											output.val('JSON browser support required for this demo.');
										}
									};

									// activate Nestable for list 1
									$('#nestable').nestable({
										group : 1,
										maxDepth : 1
									}).on('change', updateOutput);

									// activate Nestable for list 2
									$('#nestable2').nestable({
										group : 1
									}).on('change', updateOutput);

									// output initial serialised data
									updateOutput($('#nestable').data('output', $('#nestable-output')));
									updateOutput($('#nestable2').data('output', $('#nestable2-output')));
									$('#nestable3').nestable();

								});
							</script>

						<div class="buttonset">
							<div class="save-button">
								<input class="modern" type="button" value=" ���� " />
							</div>
							<div class="next-button">
								<input class="modern" type="button" value=" ���� " />
							</div>
						</div>
					</div>

					<div class="content-3">

						<div class="preview_wrapper">
							<div class="mobile_locator">
								<div id="tab_location"></div>
								<input type="button" value="��������" onclick="Init()" />
							</div>
						</div>
						<div class="buttonset">
							<div class="save-button">
								<input class="modern" type="button" value=" ���� " />
							</div>
							<div class="next-button">
								<input class="modern" type="button" value=" ���� " />
							</div>
						</div>
					</div>
					<div class="content-4">
						<form>
							<div class="color_wrapper">
								<div class="color_select">
									<h1>�� ����</h1>
									<div class="explain-4">
										<p>11���� ���� �� ������ ��� ���� ��� ���� ������������ ���� �����մϴ�.</p>
									</div>
									<div class="set-4">
										<h3>�÷��̸�����</h3>
										<ul id="colorlist" class="colorlist">
											<li class="c1"><a href="#" id="color1">col</a></li>
											<li class="c2"><a href="#" id="color2">col</a></li>
											<li class="c3"><a href="#" id="color3">col</a></li>
											<li class="c4"><a href="#" id="color4">col</a></li>
											<li class="c5"><a href="#" id="color5">col</a></li>
											<li class="c6"><a href="#" id="color6">col</a></li>
											<li class="c7"><a href="#" id="color7">col</a></li>
											<li class="c8"><a href="#" id="color8">col</a></li>
											<li class="c9"><a href="#" id="color9">col</a></li>
											<li class="c10"><a href="#"id="color10">col</a></li>
											<li class="c0"><a href="#" id="color0">col</a></li>
										</ul>
									</div>
								</div>
								<div class="preview_wrapper">
									<div class="mobile_locator">
										<div id="tab_wrapper"></div>
									</div>

								</div>

							</div>

							<div class="intro_wrapper">
								<div class="intro_select">
									<h1>��Ʈ�� �̹���</h1>
									<div class="explain-4">
										<p>������ �� �������� ��Ʈ�� �̹����� ����ϼ���.</p>
									</div>
									<div class="set-4">
										<h3>�̹���:</h3>
										<input type="file" name="file" id="files" class="display-none"
											onchange="previewImage(this,'show_intro')"> <input
											id="intro-click" type="button" value="���" />
									</div>
								</div>
								<div class="preview_wrapper">
									<div class="mobile_locator">
										<div id="show_intro"></div>
									</div>

								</div>
							</div>

							<div class="logo_wrapper">
								<div class="logo_select">
									<h1>��ǥ �̹���</h1>

									<div class="explain-4">
										<p>���� �ٿ���� �� �������� ��ǥ �̹����Դϴ�. ���� ��ǥ�� �� �ִ� �̹����� ������ּ���.</p>
									</div>
									<div class="set-4">
										<h3>�̹���:</h3>
										<input type="file" name="file" id="files2"
											class="display-none"> <input id="logo-click"
											type="button" value="���" />
									</div>
								</div>
								<div class="preview_wrapper">
									<div class="mobile_locator" id="store_image">
										<div id="show_logo"></div>
									</div>

								</div>
							</div>
							<div class="buttonset">
								<div class="save-button">
									<input class="modern" type="button" value=" ���� " />
								</div>
								<div class="next-button">
									<input class="modern" type="button" value=" ���� " />
								</div>
							</div>
						</form>
					</div>
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
						Copyright &copy; 2014 Owlet CatVSDog. All Rights Reserved. <br>
						CatVSDog is a popular web design incorporated studio and shop.
					</div>
				</div>
			</footer>
		</div>
	</div>

</body>
</html>