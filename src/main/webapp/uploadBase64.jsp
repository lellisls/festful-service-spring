<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Upload base64 image</title>
</head>
<body>
	<pre>
	Upload base64 image:
	<form enctype="application/x-www-form-urlencoded"
		action="<%=request.getContextPath()%>/rest/carros/postFotoBase64"
		method="POST">
		
		<input name="fileName" type="text" />
		<textarea name="base64" type="textarea" cols="60" rows="10">
		</textarea>
		<input type="submit" value="Send image" />
	</form>
	</pre>
</body>
</html>