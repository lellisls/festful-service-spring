<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<form method="post" action="<%= request.getContextPath() %>/hello" >
	<p>Nome: <input type="text" name="nome"/></p>
	<br/><br/>
	<p>Sobrenome: <input type="text" name="sobrenome"/> </p>
	<br/>
	<input type="submit" name="Enviar"/>
	
	
</form>

</body>
</html>