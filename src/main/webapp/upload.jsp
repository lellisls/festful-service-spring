<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>File upload</title>
</head>
<body>

	<form enctype="multipart/form-data"
		action="<%=request.getContextPath()%>/rest/carros" method="POST">
		<input name="file" type="file" /> <br> <br> <input
			type="submit" value="Enviar arquivo" />
	</form>
</body>