<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="../css/bacaling-head.css" rel='stylesheet' type='text/css' />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script type="text/javascript" src="../js/jquery-3.0.0.js"></script>
	<script type="text/javascript" src="../js/ValidateCode.js"></script>
	<script type="text/javascript" src="../js/bacaling.js"></script>
	<script>
		function jump()
		{
		top.location.href='Words.jsp';
		}
	</script>
	<title>Bacaling - Pleasure with Languages</title>
</head>
<body>

<header>
	<div id="head1" class="header-back1">
        <div class="header-back"></div>
        <!--左上角元素-->
        <div class="left-header">
            <div class="div-logo"><a href="index.jsp">BACALING</a></div>
            <div class="div-menu">
                <li style="background-color: #63b4ab"><a href="index.jsp">Home</a></li>
                <li><a onclick='jump();'>Words</a></li>
                <!--<li>Community</li>-->
                <!--<li>Translation</li>-->
            </div>
        </div>
        <!--右上角元素-->
        <div class="right-header">
            <div class="div-flag">
                <span class="span-flag"></span>
                <div class="drop-down">
                    &nbsp;&nbsp;&nbsp;&nbsp;LEARNING<br>
                    <ul>
                        <li>
                            <img src="../images/spain.png">
                            <span class="language">Spanish</span>
                            <span class="level">level 9</span>
                        </li>
                        <li>
                            <img src="../images/japan.png">
                            <span class="language">Japanese</span>
                            <span class="level">level 1</span>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="div-user">
                <span class="span-user"></span>
                <div class="down-arrow">
                    ${sessionScope.user_name}
                    <span></span>
                </div>
                <div class="drop-down">
                    <li><a href="MyProfile.jsp">My Profile</a></li>
                    <li><a href="Settings.jsp">Settings</a></li>
                    <li><a href="../LogoutServlet">Logout</a></li>
                </div>
            </div>
            <div class="div-login-days">
                <img src="../images/flame.png">
                <!--<span class="fire"></span>-->
                <span>${sessionScope.user_state}</span>
                <h5><span>${sessionScope.user_state}</span> day(s) streak</h5>
            </div>
            <div class="div-gems">
                <img src="../images/berry.png">
                <span>90</span>
                <h5>Berries</h5>
            </div>
            <div class="div-alert">
            </div>
        </div>
    </div>
</header>
</body>
</html>