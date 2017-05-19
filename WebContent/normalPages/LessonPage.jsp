<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
    <link href="../css/bacaling-main.css" rel='stylesheet' type='text/css' />
    <link href="../css/bacaling-lessonPage.css" rel='stylesheet' type='text/css' />
    <script type="text/javascript" src="../js/jquery-3.0.0.js"></script>
    <script type="text/javascript" src="../js/bacaling.js"></script>
    <script type="text/javascript" src="../js/bacaling-lesson.js"></script>
    <script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=appkey" type="text/javascript" charset="utf-8"></script>
    <title>Bacaling - Pleasure with languages</title>
	<title>Insert title here</title>
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
<!--<div id="loading">loading...</div>  -->
<header>
	<div id="head1" class="header-back1">
        <div class="header-back"></div>
        <!--左上角元素-->
        <div class="left-header">
            <div class="div-logo"><a href="index.jsp">BACALING</a></div>

            <div class="div-menu">
                <li><a href="index.jsp">Home</a></li>
                <li><a href="Words.jsp">Words</a></li>
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
        <h1></h1>
        <!--选择小节-->
        <div class="do-lesson"></div>
        <hr>
        <!--贴士&笔记-->
        <div class="tips-notes">
        <!-- UY BEGIN -->
	<!-- <div id="uyan_frame"></div>
		<script type="text/javascript" src="http://v2.uyan.cc/code/uyan.js?uid=2132596"></script>
	 -->		<!-- UY END -->

    <div class="right-main">
        <div class="right-panel-pink">
            <span class="lesson-icon"></span>
            <div class="level-bar">
                <div class="process">Strength:<span id="passed"></span>/<span id="num"></span></div>
            </div>
            <div class="strengthen">Strengthen &nbsp;<span id="lesson_name_strength"></span></div>
        </div>
    </div>
</div>
<!--end of 正文-->
</body>
</html>