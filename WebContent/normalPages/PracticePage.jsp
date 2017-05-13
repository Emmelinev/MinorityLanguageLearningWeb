<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <link href="../css/bacaling-main.css" rel='stylesheet' type='text/css' />
    <link href="../css/bacaling-praticePage.css" rel='stylesheet' type='text/css' />
    <script type="text/javascript" src="../js/jquery-3.0.0.js"></script>
    <script type="text/javascript" src="../js/bacaling.js"></script>
    <title>Bacaling - Pleasure with languages</title>
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

<!--正文-->

<div class="div-back">
    <div class="head">
        <span class="tn">Tips & notes</span>
        <span class="quit">Quit</span>
    </div>
    <!--进度条-->
    <div class="progress-bar">
        <div class="bar"></div>
        <span></span>
        <div class="calculator">1</div>
    </div>

    <!--图片选择题-->
    <div class="question-select-pic">
        <h2 class="question">Select translation of "<span>the boy</span>"</h2>
        <ul class="pic-group">
            <li class="choice-item">
                <div></div>
                <!--<input type="radio" checked="checked" name="1" value="la manzana 1">-->
                <span class="title">la manzana <span>1</span></span>
            </li>
            <li class="choice-item">
                <div></div>
                <!--<input type="radio" checked="checked" name="2" value="el niño 2">-->
                <span class="title">el niño <span>2</span></span>
            </li>
            <li class="choice-item">
                <div></div>
                <!--<input type="radio" checked="checked" name="3" value="el pan 3">-->
                <span class="title">el pan <span>3</span></span>
            </li>
        </ul>
    </div>
    <!--end of 图片选择题-->

    <!--翻译句子题-->
    <div class="question-translation">
        <h2 class="question">Translate this text</h2>
        <div class="main-question">
            <div class="left">
                <div class="media"></div>
                <p><span>Una</span>&nbsp;<span>niña</span></p>
            </div>
            <div class="middle"></div>
            <div class="right">
                <textarea  name="translationAnswer">Type in English</textarea>
            </div>
        </div>
    </div>
    <!--end of 翻译句子题-->

    <!--结束页-->

    <!--end of 结束页-->
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

    <!--练习回顾-->
    <div class="review">
        <h2>Check out your scorecard!</h2>
        <h3>Click the tiles below to reveal the solutions</h3>
        <div class="card-group">
            <div class="card-correct">
                <h5>Select<br>translation of:</h5>
                <span class="icon-correct-sm"></span>
                <span>the man</span>
            </div>
            <div class="card-wrong">
                <h5>Select<br>translation of:</h5>
                <span class="icon-correct-sm"></span>
                <span>the man</span>
            </div>
            <!--Translate this<br> text-->
        </div>
        <!--作答-->
        <div class="pop-answer">
            <div class="close"></div>
            <p>YOUR RESPONSE</p>
            <P>"<SPAN>the man</SPAN>"</P>
            <p>CORRECT RESPONSE</p>
            <p>"<span>the woman</span>"</p>
        </div>
    </div>
    <!--end of 练习回顾-->
    <!--end of 结束页-->

    <!--底部-->
    <!--默认底部-->
        <div class="foot-default">
        <div class="skip">Skip</div>
        <div class="btn-green check">Check</div>
    </div>
    <!--正确底部-->
    <div class="foot-correct">
        <div class="icon-correct"></div>
        <div class="correct-right">
            <span>You are correct.</span>
            <div class="btn-general report">Report a Problem</div>
        </div>
        <div class="btn-general continue">continue</div>
    </div>
    <!--错误底部-->
    <div class="foot-wrong">
        <div class="icon-wrong"></div>
        <div class="wrong-right">
            <h4>Correct solutions:</h4>
            <span>la niña</span>
            <div class="btn-general report">Report a Problem</div>
        </div>
        <div class="btn-general continue">continue</div>
    </div>
    <!--end of 错误底部-->
    <!--最终底部-->
    <div class="foot-final">
        <div class="btn-general review">Review lesson</div>
        <div class="btn-general continue">continue</div>
    </div>
    <!--end of 最终底部-->
</div>
<!--end of 正文-->
</body>
</html>