<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="../css/bacaling-main.css" rel='stylesheet' type='text/css' />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script type="text/javascript" src="../js/jquery-3.0.0.js"></script>
	<script type="text/javascript" src="../js/jquery.voicerss-tts.min.js"></script>
	<script type="text/javascript" src="../js/jQueryRotate.2.2.js"></script>
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
<div id="loading">loading...</div>
<header>
	<div id="head1" class="header-back1">
        <div class="header-back"></div>
        <!--左上角元素-->
        <div class="left-header">
            <div class="div-logo"><a href="index.jsp">BACALING</a></div>

            <div class="div-menu">
            <ul>
                <li style="background-color: #63b4ab"><a href="index.jsp">Home</a></li>
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
        </ul>
        <!--<div style="clear:both;"></div>-->
    </div>

    <div class="right-main">
        <div class="right-panel">
               <h2>Daily Word</h2>
                <div class="right-selected">
                <div class="head">
                    <div class="span-refresh"></div>Random a new word
                </div>
                <div class="div-word">
                    <div class="media" id="play_word"></div>
                    <span class="word"></span>                
                </div>
                <h4 id="word_pronunce"></h4>
                <table>
                    <tr>
                        <td class="left">
                            <p5>TRANSLATION</p5><hr>
                            <span id="translation"></span>
                        </td>
                        <td class="right">
                            <p5>PART OF SPEECH</p5><hr>
                            <span id="word_class"></span>
                        </td>
                    </tr>
                </table>
                    <h3>Examples</h3>
                    <hr>
                <ul class="examples">
                    <li>
                        <div id="play_sentence" class="play"></div>
                        <div class="example">
                            <h4 id="sentence"></h4>
                            <p id="sentence_example"></p>
                        </div>
                    </li>
                </ul>
                </div>
        </div>
    </div>
</div>
<!--end of 正文-->
</body>
</html>