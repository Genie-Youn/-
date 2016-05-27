<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Owlet5 Project</title>
<link rel="shortcut icon" href="/res/img/favicon.ico">
<link rel="stylesheet" href="/admin/css/crossbrowser.css">
<link rel="stylesheet" href="/admin/css/bootstrap.min.css">
<link rel="stylesheet" href="/admin/css/intro-menu-style.css">
<link rel="stylesheet" href="/admin/css/adder/adder-menu-style.css">
<link rel="stylesheet" href="/admin/css/dataTables.jqueryui.css">
<link rel="stylesheet" href="/admin/css/common-style.css">
<link rel="stylesheet" href="/admin/css/jquery.dataTables.css">
<script src="/admin/js/crossbrowser.js"></script>
<script src="/admin/js/external/jquery/jquery.js"></script>
<script src="/admin/js/jquery-ui.min.js"></script>
<script src="/admin/js/intro-menu-style.js"></script>
<script src="/admin/js/modernizr.custom.js"></script>
<script src="/admin/js/external/jquery/dataTables.jqueryui.js"></script>
<script src="/admin/js/external/jquery/jquery.dataTables.min.js"></script>
<script src="/admin/js/adder/frameList-script.js"></script>
<script src="/admin/js/bootstrap.min.js"></script>
</head>
<body>
	<div id="page_wrapper">
		<div id="content_wrapper">
			<table id="frameTable" class="display" cellspacing="0" width="100%">
				<thead>
					<tr>
						<td>index</td>
						<td>Title</td>
						<td>Path</td>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td>index</td>
						<td>Title</td>
						<td>Path</td>
					</tr>
				</tfoot>
				<tbody>
					<c:forEach var="frame" items="${frameList}">
						<tr>
							<td>${frame.nIndex}</td>
							<td>${frame.title}</td>
							<td><a href="${frame.path}" target="popup" 
								onclick="PopupCenterDual('${frame.path}','NIGRAPHIC','360','640')" location="no">${frame.path}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>