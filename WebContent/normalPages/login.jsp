<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <link href="../css/bacaling-login.css" rel='stylesheet' type='text/css' />
    <script type="text/javascript" src="../js/jquery-3.0.0.js"></script>
    <script type="text/javascript" src="../js/bacaling.js"></script>
    <script type="text/javascript" src="../js/customJS.js"></script>
    <title>Bacaling - Pleasure with Languages</title>
</head>

<body>
	<!-- 判断是否已经登录 -->
	<%  
  		if(session.getAttribute("user_id")!=null)
  		{%>
  		<div class="alert alert-danger" role="alert" align="center" style="width:70%;top:5%;left:15%;position:absolute;">
  			<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
  			<span class="sr-only">Error:</span>
  			You've already logged in! The broswer will go back to the index page after 3 seconds later.<a href="login.jsp" class="alert-link">Click here if the browser doesn't redirect.</a>
		</div>
    	<%
        response.setHeader("refresh","3;URL=index.jsp");
        return;
   		}
   	%>
<div id="shade" class="shade"></div>
<iframe src="BerryAnimation.html"></iframe>
<!-- <div class="tip">Necessary information!</div> -->
<header>
    <script type="text/javascript">
        $(function(){
            $(".btn-sign-in").click(function(){
                $(".login").show("slow");
                $("#userName").focus();
            });
            $(document).bind("click",function(e){
                var target  = $(e.target);
                if(target.closest(".login-form,.btn-sign-in").length == 0){
                    $(".login-form").hide();
                };
                e.stopPropagation();
            })
        })
    </script>
    <div class="back">
        <div class="headercontent">
            BACALING
            <span class="btn-sign-in" onclick="popDiv('login-form','shade',2)">Login</span>
        </div>
    </div>
    <div id="login-form" class="login-form">
        <form id="loginFrm" method="post" action="../LoginServlet">
            <input type="text" class="input-name" name="userName"  value="username/phone num" autofocus="autofocus" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'username/phone num';}"><br>
            <input type="password" class="input-pwd" name="password" value=" ******" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = ' ******';}"><br>
            <div class="div-loginbtn">
                <input type="submit" class="btn-login" value="Go" >
                <a href="#foo" class="a-forget" data-toggle="modal" data-target="#RetrieveModal" >Forgot password?</a>
            </div>
        </form>
    </div>
</header>

<div class="content">
    <div id="" class="right">
        <span class="title">Pleasure with Languages</span>
        <span class="btn-start" ><a onclick="popDiv('frm-sign-up','right','1')">START</a> </span>
    </div>
</div>

<!--注册弹窗-->
<form id="frm-sign-up" class="frm-sign-up" method="post" action="../SignupServlet">
    <div>
        <h1>Sign up</h1>
        <div class="div-pic"></div>
    </div>
    <div class="div-close"  onclick="closeDiv('frm-sign-up','right',1)"></div>

    <table class="tb-log" cellspacing="0" cellpadding="0">
        <tr >
            <td id="phoneTd">&nbsp;&nbsp;&nbsp;phone num.<input type="text" name="phonenum" id="phonenum" class="ipt-table ipt-tel" Oninput="phonenumOninput(event)"></td>
        </tr>
        <tr>
            <td id="pwdTd">&nbsp;&nbsp;&nbsp;&nbsp;password<input type="password" name="pwd" id="pwd" class="ipt-table ipt-pwd" Oninput="passwordOninput(event)"></td>
        </tr>
        <tr>
            <td id="confirmTd">&nbsp;&nbsp;confirm pass<input type="password" name="repwd" id="repwd" class="ipt-table ipt-pwd2" Oninput="confirmOninput(event)"></td>
        </tr>
        <tr>
            <td id="checkcodeTd">&nbsp;&nbsp;&nbsp;&nbsp;check code<input id="verifyCode" name="verifyCode" type="text" class="ipt-table ipt-verify"  Oninput="checkcodeOninput(event)"/>
                <button type="button" class="btn-send" id="btn-sendVC" onclick="sendCode(this)">Send code</button></td>
        </tr>
    </table>
    <input id="btn-signup" type="submit" name="sub" value="Bingo" class="btn-signup">
</form>
<!--end of 注册弹窗-->
</body>
</html>
