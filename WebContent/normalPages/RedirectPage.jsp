<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta charset="utf-8">
		<link href="../css/bacaling-info.css" rel='stylesheet' type='text/css' />
		<script type="text/javascript" src="../js/jquery-3.0.0.js"></script>
<title>Bacaling - Pleasure with Languages</title>
</head>
<body>
<div class="panel">
    <span class="parrot parrot-logout"></span>
    <div class="info logout-info">
        <h1 class="success">Success</h1>
        You've logout from bacaling. Page will go back to login page after 3 seconds.<br>
        <a href="login.jsp">Click here to go back to login page right now.</a>
    </div>
</div>
   <%
     response.setHeader("Refresh","3;URL=login.jsp");   
    %>
</body>
</html>