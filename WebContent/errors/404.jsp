<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="../css/bacaling-info.css" rel='stylesheet' type='text/css' />
	<title>Bacaling 404 : Page not Found</title>
</head>
<body>
<div class="panel">
    <span class="parrot parrot-error"></span>
    <div class="info info-404">
        <h1 class="pnf404">404</h1>
        Page not found. Page will go back to index page after 3 seconds.<br>
        <a href="login.jsp">Click here to go back to index page right now.</a>
    </div>
</div>
   <%
     response.setHeader("Refresh","3;URL=index.jsp");   
    %>
</body>
</html>