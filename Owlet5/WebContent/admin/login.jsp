<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>TCP-Owlet5</title>

<script src="/admin/js/jquery-1.11.1.min.js"></script>
<script src="/admin/js/myUtil.js"></script>
<link rel="shortcut icon" href="/res/img/favicon.ico">
<link rel="stylesheet" type="text/css" href="/admin/css/style.css" />
</head>
<body>
	<div id="header-wrapper">
	<!-- 로고를 표시합니다. -->
		
		<div id="main_header_wrapper">
			<header class="main-header">
					<button class="btn btn-1" id="loginbtn">로그인</button>
					<button class="btn btn-1" id="joinbtn">회원가입</button>
				</section>
			</header>
		</div>
	</div>
	
	<div id="content">
		<div class="login-web web-1"></div>

		<div class="login-web web-2"></div>
		<div class="login-web web-3"></div>
		<div class="login-web web-4">
			<div id="body-wrapper">
		<header id="main-header">
			<h2 id="top-text"></h2>
			<p></p>
		</header>
		<br>
		<section id="main-section">
			<div>
				<form id="loginForm" name="loginForm" action="#" method="post">
					<!-- <img id="logoImg" src='/res/img/owlet.gif' width="386" height="317"><br> -->
					<div id="logoImg" ></div>
					<!-- <input class="invalid field" id="userID" placeholder="e-mail" required maxlength="20" /> -->
					<input class="normal field" id="userID" name="mail" placeholder="이메일(이메일 주소)" required maxlength="25" />
					<br> 
					
					<input class="normal field" id='userPasswd' name="passwd" type="password" placeholder="비밀번호" required maxlength="15"/>
					<br>
					<button id="login">로그인</button><br>
					<input style="display:none;" class="normal field" type="text">
					<div id="console">
						
					</div>
				</form>
			</div>
		</section>
		<br/>	
		<footer id="main-footer">
				<p> 
					▶  아직 OWLET 회원이 아니신가요?
					<a href="join.do"><font size = 3>회원가입</a></font>
				</p>
		</footer>
		</div>
		
		</div>
	</div>
	
	<footer id="main_footer">
		<div id="footer_wrapper">
			<div class="footer-container">
					<div id="main_copyright">
					</div>
				</div>
			</div>
	</footer>
</body>
</html>