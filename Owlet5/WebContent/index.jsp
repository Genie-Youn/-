<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Owlet</title>
</head>
<body>
<body>

	<%
		String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
	
		String name = request.getContextPath();
	
	    String redirectURL =path+name + "/login.do";
	    response.sendRedirect(redirectURL);
	%>
</body>
</html>