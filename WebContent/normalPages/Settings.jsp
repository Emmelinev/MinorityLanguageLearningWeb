<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <link href="../css/bacaling-main.css" rel='stylesheet' type='text/css' />
    <link href="../css/bacaling-settings.css" rel='stylesheet' type='text/css' />
    <script type="text/javascript" src="../js/jquery-3.0.0.js"></script>
    <script type="text/javascript" src="../js/bacaling.js"></script>
    <script type="text/javascript" src="../js/bacaling-settings.js"></script>
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
        <!--账户设置-->
        <form id="frm-change-account" enctype="multipart/form-data" method="post" action="../SettingsServlet">           
        <div class="settings-left" id="account-settings">
            <div class="head">
                <h1>Account Settings</h1>
                <input type="submit" id="submit_account" class="save-changes" value="Save changes">
            </div>
            
            <table>
                <tr>
                    <td>Username</td>
                    <td><input id="user_name" type="text" class="text-input" name="user_name"  Oninput="txtchange(event)" value=${sessionScope.user_name}></td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td><input id="email" type="text" class="text-input" name="email" Oninput="txtchange(event)" value=${sessionScope.user_email}></td>
                </tr>
                <tr>
                    <td>Voice autoplay</td>
                    <td>
                        <span id="autoplay0" class="radio-out"><input type="radio" class="radio-input" name="autoplay" value="on">on&nbsp;&nbsp;</span>
                        <span id="autoplay1" class="radio-out"><input type="radio" class="radio-input" name="autoplay" value="off">off</span></td>
                </tr>
                <tr>
                    <td>Sound effects</td>
                    <td>
                        <span id="effect0" class="radio-out"><input type="radio" class="radio-input" name="effect" value="on">on&nbsp;&nbsp;</span>
                        <span id="effect1" class="radio-out"><input type="radio" class="radio-input" name="effect" value="off">off</span></td>
                </tr>
                <tr>
                    <td>Profile picture</td>
                    <td>
                        <div class="profile-picture">
                            <span class="picture"></span>
                            <img alt="go" src="d:/university/<%=(String) request.getAttribute("profile_pic")%> " />
                            <div class="picture-right">
                                <input type="file" id="profile_pic" name="profile_pic">
                                maximum images size is 1MB
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
            <a>Deactive my account</a>
            <hr>
        </div>
         </form>
        <!--end of 账户设置-->
        <!--用户名密码-->
        <form id="frm-profile" method="post" action="../SettingsServlet?method=2">     
        <div class="settings-left" id="profile">
            <div class="head">
                <h1>Profile</h1>
                <input id="submit_profile" type="button" class="save-changes" value="Save changes">
            </div>
            <table>
                <tr>
                    <td>Current Password</td>
                    <td><input id="current_pwd" type="text" class="text-input" Oninput="txtchange(event)" name="oldPassword"></td>
                </tr>
                <tr>
                    <td>New Password</td>
                    <td><input id="new_pwd" type="text" class="text-input" Oninput="txtchange(event)"  name="modifyPassword"></td>
                </tr>
                <tr>
                    <td>Confirm Password</td>
                    <td><input id="confirm_pwd" type="text" class="text-input" Oninput="txtchange(event)" name="confirmPassword"></td>
                </tr>
            </table>
            <hr>
        </div>
        </form>
        <!-- end of用户名密码-->
        <!--通知-->
        <form id="frm-notice" method="post" action="../SettingsServlet?method=3">     
        <div class="settings-left" id="notification">
            <div class="head">
                <h1>Notification</h1>
                <input id="submit_notice" type="button" class="save-changes" value="Save changes">
            </div>
            <table>
                <tr>
                    <td>Email me when</td>
                    <td>
                        <span class="checkbox-out"><input type="checkbox" name="notice">&nbsp;&nbsp;&nbsp;&nbsp;There is an announcement<br></span>
                        <span class="checkbox-out"><input type="checkbox" name="notice">&nbsp;&nbsp;&nbsp;&nbsp;I forget to practice<br></span>
                    </td>
                </tr>
            </table>
            <hr>
        </div>
        </form>
        <!--end of 通知-->
        <!--每日目标-->
        <form id="frm-goal" method="post" action="../SettingsServlet?method=4"> 
        <div class="settings-left" id="dailyGoal">
            <div class="head">
                <h1>Daily Goal</h1>
                <input id="submit_goal" type="button" class="save-changes" value="Save changes">
            </div>
            <div class="content">
                <span class="image"></span>
                <ul>
                    <li><input type="radio" name="goal" value="1">&nbsp;&nbsp;&nbsp;&nbsp;Basic<span>1px per day</span></li>
                    <li><input type="radio" name="goal" value="2">&nbsp;&nbsp;&nbsp;&nbsp;Causal<span>10px per day</span></li>
                    <li><input type="radio" name="goal" value="3">&nbsp;&nbsp;&nbsp;&nbsp;Regular<span>30px per day</span></li>
                    <li><input type="radio" name="goal" value="4">&nbsp;&nbsp;&nbsp;&nbsp;Serious<span>50px per day</span></li>
                </ul>
            </div>
            <hr>
        </div>
        </form>
        <!--end of 每日目标-->
    </div>

    <div class="right-main">
        <div class="right-panel">
            <ul>
                <li><a href="#account-settings">Account</a></li>
                <!--<li>Learning language</li>-->
                <li><a href="#profile">Profile</a></li>
                <li><a href="#notification">Notifications</a></li>
                <li><a href="#dailyGoal">Daily Goal</a></li>
            </ul>
        </div>
    </div>
</div>
<!--end of 正文-->
</body>
</html>