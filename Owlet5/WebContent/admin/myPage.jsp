<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>TCP-Owlet5</title>
<link rel="shortcut icon" href="/res/img/favicon.ico">
<link rel="stylesheet" type="text/css" href="/admin/css/myPage.css" />
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans+Condensed:700,300,300italic'
	rel='stylesheet' type='text/css'>
<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script type="text/javascript" src="/admin/js/myPage.js"></script>
</head>
<body>
	<div class="container">

		<header>
			<img id="header" src="/res/img/myPage.png" />
		</header>
		<section class="tabs">
			<input id="tab-1" type="radio" name="radio-set"
				class="tab-selector-1" checked="checked" /> <label for="tab-1"
				class="tab-label-1">개인정보수정</label> <input id="tab-2" type="radio"
				name="radio-set" class="tab-selector-2" /> <label for="tab-2"
				class="tab-label-2">업체정보수정</label>

			<div class="clear-shadow"></div>

			<div class="content">
				<form name="mypage" method="post" action="/mypageUpdate.do"
					enctype="multipart/form-data" modelAttribute="userInfo"
					modelAttribute="storeInfos">
					<div class="content-1">
						<h2>개인 프로필</h2>
						<table class="table-1">
							<tr>
								<td class="option"><p>name</p></td>
								<td class="inform">${info.user.name}</td>
								<td></td>
							</tr>
							<tr>
								<td class="option"><p>E-mail</p></td>
								<td class="inform">${info.user.mail}</td>
								<td></td>
							</tr>
							<tr>
								<td class="option"><p>NickName</p></td>
								<td class="inform"><input class="textbox" type="text"
									path="nickName" name="nickName" value="${info.user.nickName}"></td>
								<td></td>
							</tr>
							<tr>
								<td class="option"><p>password</p></td>
								<td class="inform"><input class="textbox" type="password"
									path="passwd" name="passwd" value="${info.user.passwd}"></td>
								<td class="explain">/*15자 이내로 입력하세요.*/</td>
							</tr>
							<tr>
								<td colspan="3" align="center"><input class="modern"
									type="submit" value="적용" />&nbsp; &nbsp; <input
									class="modern" type="reset" value="종료"></td>
							</tr>
						</table>
					</div>
					<div class="content-2">
						<h2>업체 정보</h2>
						<table class="table-1">
							<tr>
								<td class="option"><p>Company</p></td>
								<td class="inform"><input class="textbox" type="text"
									path="storeName" name="storeName"
									value="${info.store.storeName}"></td>
								<td rowspan="4" class="inform"><c:choose>
										<c:when test="${info.store.image == null}">
											<img class="logo-image" src="http://placehold.it/32x32" />
										</c:when>
										<c:otherwise>
											<img class="logo-image"
												src="/tcpFile/res_${info.store.code}/${info.store.image}" />
										</c:otherwise>
									</c:choose></td>
							</tr>
							<tr>
								<td class="option"><p>phone number</p></td>
								<td class="inform"><input class="textbox" type="text"
									path="phone" name="phone" value="${info.store.phone}"></td>
								
							</tr>
							<tr>
								<td class="option"><p>homepage</p></td>
								<td class="inform"><input class="textbox" type="text"
									name="url" value="${info.store.url}" /></td>
							</tr>
							<tr>
								<td class="option"><p>store logo</p></td>
								<td class="inform"><input class="textbox file-input"
									type="file" name="logo" /></td>
								
							</tr>
							<tr>
								<td colspan="3" align="center"><input class="modern"
									type="submit" value="적용" />&nbsp; &nbsp; <input
									class="modern" type="reset" value="종료"></td>
							</tr>
						</table>
					</div>
				</form>
			</div>
		</section>
	</div>
</body>
</html>