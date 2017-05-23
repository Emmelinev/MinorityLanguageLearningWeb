<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <link href="../css/bacaling-main.css" rel='stylesheet' type='text/css' />
    <link href="../css/bacaling-praticePage.css" rel='stylesheet' type='text/css' />
    <script type="text/javascript" src="../js/jquery-3.0.0.js"></script>
    <script type="text/javascript" src="../js/jquery.voicerss-tts.min.js"></script>
    <script type="text/javascript" src="../js/bacaling.js"></script>
    <script type="text/javascript" src="../js/bacaling-practice2.js"></script>
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
<!--<div id="loading">loading...</div>  -->
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

<div class="div-back">
    <div class="head">
        <span class="tn">Tips & notes</span>
        <span class="quit"><a href="index.jsp">Quit</a></span>
    </div>
    <!--进度条-->
    <div class="progress-bar">
        <div class="bar"></div>
        <span></span>
        <div class="calculator">1</div>
    </div>
    <div id="practice-content">
    <!--  
    <!--ͼƬѡձ͢-->
    <div class="question-select-pic">
        <h2 class="question">Select translation of "<span>the boy</span>"</h2>
        <ul class="pic-group">
            <li class="choice-item">
                <div class="p1"></div>
                <!--<input type="radio" checked="checked" name="1" value="la manzana 1">-->
                <span class="title t1">la manzana <span>1</span></span>
            </li>
            <li class="choice-item">
                <div class="p2"></div>
                <!--<input type="radio" checked="checked" name="2" value="el ni?o 2">-->
                <span class="title t2">el ni?o <span>2</span></span>
            </li>
            <li class="choice-item">
                <div class="p3"></div>
                <!--<input type="radio" checked="checked" name="3" value="el pan 3">-->
                <span class="title t3">el pan <span>3</span></span>
            </li>
        </ul>
    </div>
    <!--end of ͼƬѡձ͢-->

    <!--׭ӫߤؓ͢-->
    <div class="question-translation">
        <h2 class="question">Translate this text</h2>
        <div class="main-question">
            <div class="left">
                <div class="media"></div>
                <p><span>Una</span>&nbsp;<span>ni?a</span></p>
            </div>
            <div class="middle"></div>
            <div class="right">
                <textarea name="translationAnswer">Type in English</textarea>
                <div class="input-group">
                    <div id="Ntilde_0" class="input-div">&Ntilde;</div>
                    <div id="ntilde_1" class="input-div">&ntilde;</div>
                    <div id="Aacute_0" class="input-div">&Aacute;</div>
                    <div id="Aacute_1" class="input-div">&aacute;</div>
                    <div id="Eacute_0" class="input-div">&Eacute;</div>
                    <div id="eacute_1" class="input-div">&eacute;</div>
                    <div id="Oacute_0" class="input-div">&Oacute;</div>
                    <div id="oacute_1" class="input-div">&oacute;</div>
                </div>
            </div>
        </div>
    </div>
    <!--end of ׭ӫߤؓ͢-->
    
    <!--ѡՊͮࠕ-->
    <div class="question-missing">
        <h2 class="question">Select the missing word</h2>
        <div class="main-question">
            <p>Ella&nbsp;<select>
                <option id="o1">es</option>
                <option id="o2">eres</option>
            </select>&nbsp;una mujer.</p>
        </div>
    </div>
    <!--end of ѡՊͮࠕ-->
    <!--ͽд͢-->
    <div class="question-listening">
        <h2 class="question">Type what you hear</h2>
        <div class="main-question">
            <div class="normal-speed">
                speek
                <div class="slow-speed">slow </div>
            </div>
            <input type="text" name="listenInput" id="listening-input">
         <!--    <div class="input-group">
                    <div id="Ntilde_0" class="input-div">&Ntilde;</div>
                    <div id="ntilde_1" class="input-div">&ntilde;</div>
                    <div id="Aacute_0" class="input-div">&Aacute;</div>
                    <div id="Aacute_1" class="input-div">&aacute;</div>
                    <div id="Eacute_0" class="input-div">&Eacute;</div>
                    <div id="eacute_1" class="input-div">&eacute;</div>
                    <div id="Oacute_0" class="input-div">&Oacute;</div>
                    <div id="oacute_1" class="input-div">&oacute;</div>
                </div> -->
        </div>
    </div>
    <!--end of ͽд͢-->

    <!--ޡ˸ҳ-->

    <!--end of ޡ˸ҳ-->
    <div class="ending">
        <h2>Lesson complete! +10 XP</h2>
        <h3>You met your daily goal!</h3>
        <div class="graphs">
            <div class="roll">
                <span class="fire"></span>
                <h4><span>10</span>/<span>10</span></h4>
                <h5>xp gained</h5>
            </div>
            <div class="line"></div>
            <div class="graph"></div>
        </div>
    </div>
    <div class="scroll-circle"></div>

    <!--wϰܘڋ-->
    <div class="review-panel">
    <div class="title">
    	<h2>Check out your scorecard!</h2>
        <h3>Click the tiles below to reveal the solutions</h3>
        <div class="close"></div>
    </div>
        
       <div class="card-group">
          <!--   <div class="card-correct">
                <h5>Select<br>translation of:</h5>
                <span class="icon-correct-sm"></span>
                <span>the man</span>
            </div>
            <div class="card-wrong">
                <h5>Select<br>translation of:</h5>
                <span class="icon-correct-sm"></span>
                <span>the man</span>
            </div>
            Translate this<br> text--> 
        </div> 
        <!--طհ-->

    </div>
    <!--end of wϰܘڋ-->
    <!--end of ޡ˸ҳ-->

    <!--֗ҿ-->
    <!--Ĭɏ֗ҿ-->
        <div class="foot-default">
        <div class="skip">Skip</div>
        <div id="answer-check" class="btn-green check">Check</div>
    </div>
    <!--ֽȷ֗ҿ-->
    <div class="foot-correct">
        <div class="icon-correct"></div>
        <div class="correct-right">
            <span>You are correct.</span>
            <div class="btn-general report">Report a Problem</div>
        </div>
        <div id="continue-practice-right" class="btn-general continue">continue</div>
    </div>
    <!--խϳ֗ҿ-->
    <div class="foot-wrong">
        <div class="icon-wrong"></div>
        <div class="wrong-right">
            <h4>Correct solutions:</h4>
            <span>la nia</span>
            <div class="btn-general report">Report a Problem</div>
        </div>
        <div id="continue-practice-wrong" class="btn-general continue">continue</div>
    </div>
    <!--end of խϳ֗ҿ-->
    <!--خו֗ҿ-->
    <div class="foot-final">
        <div class="btn-general review">Review lesson</div>
        <div id="continue-quit" class="btn-general continue">continue</div>
    </div>
    <!--end of خו֗ҿ-->
    </div>
</div>
<audio id="fail" src="../audio/fail.wav"></audio>  
<audio id="success" src="../audio/success.wav"></audio>  
<audio id="win" src="../audio/win.wav"></audio>  
<!--end of 正文-->
</body>
</html>