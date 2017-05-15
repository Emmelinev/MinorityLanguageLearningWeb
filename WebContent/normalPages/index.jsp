<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="../css/bacaling-main.css" rel='stylesheet' type='text/css' />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script type="text/javascript" src="../js/jquery-3.0.0.js"></script>
	<script type="text/javascript" src="../js/ValidateCode.js"></script>
	<script type="text/javascript" src="../js/bacaling.js"></script>
	<script type="text/javascript" src="../js/bacaling-index.js"></script>
	<title>Bacaling - Pleasure with Languages</title>
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
<!-- <iframe src="headPage.jsp"></iframe> -->
<header>
	<div id="head1" class="header-back1">
        <div class="header-back"></div>
        <!--左上角元素-->
        <div class="left-header">
            <div class="div-logo"><a href="index.jsp">BACALING</a></div>
            <div class="div-menu">
                <li style="background-color: #63b4ab"><a href="index.jsp">Home</a></li>
                <li><a href="Words.jsp">Words</a></li>
            </div>
        </div>
        <!--右上角元素-->
        <div class="right-header">
            <div class="div-flag">
                <span class="span-flag"></span>
                <div class="drop-down">
                    &nbsp;&nbsp;&nbsp;&nbsp;LEARNING<br>
                    <ul id="user_language">
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
            <div class="div-alert">
            	<span class="new-count">new</span>
            </div>
            <div class="message">
                    <div class="close"></div>
                    <ul id="srcoll-message">
                    <!-- 
                        <li>
                            <p>Welcome to Bacaling! Joy with your learning.</p>
                            <span>2017/04/10</span>
                        </li>
                    -->
                    </ul>
           </div>
        </div>
    </div>
</header>

<!--正文-->
<p id="language_hide" style = "display:none;">${sessionScope.current_language}</p>
<div class="div-main">
    <div class="left-main">
        <div class="div-title">
            <!--国旗图标-->
            <span class="flag"></span>
            <!--语言指示-->
            <h2 id="language_name"> Skills</h2>
            <!--等级-->
            <h4 id="language_level">LEVEL 9</h4>
        </div>
        <!--主要部分-->
        <ul class="div-main" id="lesson_list">
            <li class="lesson Basics1">
                <!--图标-->
                <div class="logo">
                    <a href="LessonPage.jsp">
                        <img src="../images/chicken%20(3).png">
                    </a>

                </div>
                <!--<span class="logo"></span>-->
                <div class="level-bar">
                    <div class="level"></div>
                </div>
                <h5>Basics1</h5>
            </li>
            <li class="lesson Basics2">
                <!--图标-->
                <div class="logo">
                    <a href="login.jsp">
                        <img src="../images/chicken%20(2).png">
                    </a>

                </div>
                <!--<span class="logo"></span>-->
                <div class="level-bar">
                    <div class="level"></div>
                </div>
                <h5>Basics2</h5>
            </li>
            <li class="lesson Phrases">
                <!--图标-->
                <div class="logo">
                    <img src="../images/sun.png">
                </div>
                <!--<span class="logo"></span>-->
                <div class="level-bar">
                    <div class="level"></div>
                </div>
                <h5>Phrases</h5>
            </li>
            <li class="lesson Food">
                <!--图标-->
                <div class="logo">
                    <img src="../images/carrot%20(1).png">
                </div>
                <!--<span class="logo"></span>-->
                <div class="level-bar">
                    <div class="level"></div>
                </div>
                <h5>Food</h5>
            </li>
            <li class="lesson Animals">
                <!--图标-->
                <div class="logo">
                    <img src="../images/dove.png">
                </div>
                <!--<span class="logo"></span>-->
                <div class="level-bar">
                    <div class="level"></div>
                </div>
                <h5>Animals</h5>
            </li>
        </ul>
        <!--<div style="clear:both;"></div>-->
    </div>

    <div class="right-main">
        <div class="right-panel div-daily-goal">
            <div class="daily-title">
                <h1>Daily Goal</h1>
                <!--设置图标-->
                <span></span>
            </div>
            <div class="portrait">
                <!--头像-->
                <span></span>
                Learning a language requires practice every day.
            </div>
            <div></div>
            <div class="line>"></div>
            <span class="btn-strength"><a>Strengthen skills</a></span>
        </div>
        <div class="right-panel">
            <h1>My Words</h1>
        </div>
    </div>
</div>
<!--end of 正文-->
</body>
</html>