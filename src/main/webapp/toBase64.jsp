<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>File upload</title>
</head>
<body>

<pre>
Convert file to Base64
<form enctype="multipart/form-data" action="<%= request.getContextPath() %>/rest/carros/toBase64" method="POST">
	<input name="file" type="file" />
	<input type="submit" value="Send file"/>
</form>
</pre>
</body>