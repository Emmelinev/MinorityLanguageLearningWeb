<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
    <link href="../css/bacaling-main.css" rel='stylesheet' type='text/css' />
    <link href="../css/bacaling-profile.css" rel='stylesheet' type='text/css' />
    <script type="text/javascript" src="../js/jquery-3.0.0.js"></script>
    <script type="text/javascript" src="../js/bacaling.js"></script>
    <title>Bacaling - Pleasure with languages</title>
</head>
<body>
	<!-- 判断是否已经登录 -->
	<%  
  		if(session.getAttribute("user_id")==null)
  		{%>
  		<div class="alert alert-danger" role="alert" align="center" style="width:70%;top:5%;left:15%;position:absolute;">
  			<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  			<span class="sr-only">Error:</span>
  			You're supposed to login before starting your study. Page will redirect to login page after 3 seconds.<a href="login.jsp" class="alert-link">Click here if the broswer didn't redirect.</a>
		</div>
    	<%
        response.setHeader("refresh","3;URL=login.jsp");
        return;
   		}
   	%>
<div id="loading">loading...</div>
<header>
	<div id="head1" class="header-back1">
        <div class="header-back"></div>
        <!--左上角元素-->
        <div class="left-header">
            <div class="div-logo"><a href="index.jsp">BACALING</a></div>

            <div class="div-menu">
            <ul>
                <li><a href="index.jsp">Home</a></li>
                <li><a href="Words.jsp">Words</a></li>
                </ul>
            </div>
        </div>
        <!--右上角元素-->
        <div class="right-header">
            <div class="div-flag">
                <span class="span-flag"></span>
                <div class="drop-down">
                    &nbsp;&nbsp;&nbsp;&nbsp;LEARNING<br>
                    <ul id="user_language"></ul>
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
                <span class="fire">${sessionScope.user_state}</span>
                <h5><span>${sessionScope.user_state}</span> day(s) streak</h5>
            </div>
            <div class="div-alert">
            	<span class="new-count">new</span>
            </div>
            <div class="message">
                    <div class="close"></div>
                    <ul id="srcoll-message"></ul>
           </div>
        </div>
    </div>
</header>
<!--正文-->

<div class="div-main">
    <div class="left-main">
        <div class="div-portrait">
            <span class="portrait"></span>
            <h2>${sessionScope.user_name}</h2>
        </div>
    </div>
    <div class="right-main">
        <div class="right-panel-grey">
            <h3>Achievements</h3>
            <div class="streak">
                <h4>STREAK</h4>
                <span class="span-fire"></span>
                <span id="streak-days">0</span>
                <span class="days">Days</span>
            </div>
            <div class="laguages">
                <h4>LANGUAGES</h4>
                <div id="scroll-lang">
                    <table>
                        <tr>
                            <td><span class="lang-icon spain"></span></td>
                            <td>
                                <h5><span>Spanish</span> - <span>Level 9</span></h5>
                                <h6>Next level: &nbsp;<span>2</span>&nbsp;XP</h6>
                                <h6>Total XP: &nbsp;<span>2248</span>&nbsp;XP</h6>
                            </td>
                        </tr>
                        <tr>
                            <td><span class="lang-icon japan"></span></td>
                            <td>
                                <h5><span>Japanese</span> - <span>Level 1</span></h5>
                                <h6>Next level: &nbsp;<span>7</span>&nbsp;XP</h6>
                                <h6>Total XP: &nbsp;<span>53</span>&nbsp;XP</h6>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<!--end of 正文-->
</body>
</html>