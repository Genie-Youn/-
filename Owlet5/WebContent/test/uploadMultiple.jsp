<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
 <!DOCTYPE html> 
 <html> 
  <head> 
  <meta charset="EUC-KR"> 
  <title>Insert title here</title> 
  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries --> 
  <!--[if lt IE 9]> 
   <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script> 
   <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script> 
  <![endif]--> 
  </head> 
  <body> 
     <form method="POST" action="/file/uploadMultipleFile.do" enctype="multipart/form-data">
        File1 to upload: <input type="file" name="file"><br /> 
        Name1: <input type="text" name="name"><br /> <br /> 
        File2 to upload: <input type="file" name="file"><br /> 
        Name2: <input type="text" name="name"><br /> <br />
        <input type="submit" value="Upload"> Press here to upload the file!
    </form>
  </body> 
 </html>