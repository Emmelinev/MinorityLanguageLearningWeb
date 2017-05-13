<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <link href="../css/bacaling-main.css" rel='stylesheet' type='text/css' />
    <link href="../css/bacaling-words.css" rel='stylesheet' type='text/css' />
    <script type="text/javascript" src="../js/jquery-3.0.0.js"></script>
    <script type="text/javascript" src="../js/jquerySession.js"></script>
    <script type="text/javascript" src="../js/jquery.voicerss-tts.min.js"></script>
    <script type="text/javascript" src="../js/bacaling.js"></script>
    <script type="text/javascript" src="../js/bacaling-words.js"></script>
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
<p id="language_hide" style="display:none">${sessionScope.current_language}</p>
<div class="div-main">
    <div class="left-main">
        <h1 id="language_name"><!--<span>Spanish</span>-->&nbsp; words learned</h1>
        <h4 id="count-row"><!-- <span>5</span>--> &nbsp;Words</h4>
        <div class="word-table" id="word-list">
        <!-- 
            <table>
                <tr>
                    <th>Word</th>
                    <th>Part of speech</th>
                    <th>Last practiced</th>
                    <th>Strength</th>
                </tr>
                <tr>
                    <td>ellos</td>
                    <td>Pronoun</td>
                    <td>3 months ago</td>
                    <td><span class="icon-strength-level icon-strength-level-4"></span></td>
                </tr>
                <tr>
                    <td>son</td>
                    <td>Verb</td>
                    <td>3 months ago</td>
                    <td><span class="icon-strength-level icon-strength-level-4"></span></td>
                </tr>
                <tr>
                    <td>soy</td>
                    <td>Verb</td>
                    <td>3 months ago</td>
                    <td><span class="icon-strength-level icon-strength-level-4"></span></td>
                </tr>
                <tr>
                    <td>sus</td>
                    <td>Determiner</td>
                    <td>3 months ago</td>
                    <td><span class="icon-strength-level icon-strength-level-4"></span></td>
                </tr>
                <tr>
                    <td>los</td>
                    <td>Determiner</td>
                    <td>3 months ago</td>
                    <td><span class="icon-strength-level icon-strength-level-4"></span></td>
                </tr>
            </table> -->
        </div>
    </div>

    <!--右边栏-->
    <div class="right-main">
        <div class="right-panel-fix">
            <!--默认卡-->
            <div class="right-unselected">
                <h2>Spaced repetition</h2>
                <p>Bacaling's algorithms figure out when you should practice words to
                    get them into your long-term memory.</p>
                <table>
                    <tr>
                        <td><span class="icon-strength-level icon-strength-level-4"></span></td>
                        <td>Still strong</td>
                    </tr>
                    <tr>
                        <td><span class="icon-strength-level icon-strength-level-3"></span></td>
                        <td>Pretty good</td>
                    </tr>
                    <tr>
                        <td><span class="icon-strength-level icon-strength-level-2"></span></td>
                        <td>Time to practice</td>
                    </tr>
                    <tr>
                        <td><span class="icon-strength-level icon-strength-level-1"></span></td>
                        <td>Overdue</td>
                    </tr>
                </table>
                <div class="btn-review">Review flashcards</div>
            </div>
            <!--end of 默认卡-->
            <!--单词概况-->
            <div class="right-selected">
                <div class="head"  onclick="popDiv('right-unselected','right-selected',2)">
                    <span class="span-back"></span>BACK
                </div>
                <div class="div-word">
                    <div class="media"></div>
                    <span class="word">word</span>
                </div>
                <h4>(Masculine)</h4>
                <table>
                    <tr>
                        <td class="left">
                            <p5>TRANSLATION</p5><hr>
                            <span id="word_translation">they</span>
                        </td>
                        <td class="right" id="strength_td">
                            <p5>STRENGTH</p5><hr>
                            <span id="strength" class="icon-strength-level icon-strength-level-4"></span>
                        </td>
                    </tr>
                    <tr>
                        <td class="left">
                            <p5>SKILL</p5><hr>
                            <span id="of_lesson">Basics2</span>
                        </td>
                        <td class="right">
                            <p5>PART OF SPEECH</p5><hr>
                            <span id="word_class">Pronoun</span>
                        </td>
                    </tr>
                </table>
                <div class="detail">More details</div>
            </div>
            <!--end of 单词概况-->
        </div>
    </div>
</div>
<!--end of 正文-->
</body>
</html>