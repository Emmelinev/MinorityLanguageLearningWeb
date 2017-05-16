<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
    <link href="../css/bacaling-main.css" rel='stylesheet' type='text/css' />
    <link href="../css/bacaling-lessonPage.css" rel='stylesheet' type='text/css' />
    <script type="text/javascript" src="../js/jquery-3.0.0.js"></script>
    <script type="text/javascript" src="../js/bacaling.js"></script>
    <script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=appkey" type="text/javascript" charset="utf-8"></script>
    <title>Bacaling - Pleasure with languages</title>
	<title>Insert title here</title>
</head>
<body>
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
                <span>${sessionScope.user_state}</span>
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
        <h1>Basics1</h1>
        <!--选择小节-->
        <div class="do-lesson">
            <div class="bar">
                <h3>LESSON 1<span></span></h3>
                <p>el, hombre, la, mujer, yo, un, una, soy, niño, niña</p>
                <div class="btn-go"><a href="PracticePage.jsp">Redo</a></div>
            </div>
            <div class="bar">
                <h3>LESSON 2<span></span></h3>
                <p>él, es, ella, come, tú, eres, usted, manzanas</p>
                <div class="btn-go">Redo</div>
            </div>
            <div class="bar">
                <h3>LESSON 3<span></span></h3>
                <p>pan, como, comes, agua, leche, bebe, bebo, bebes</p>
                <div class="btn-go">Start</div>
            </div>
        </div>
        <hr>
        <!--贴士&笔记-->
        <div class="tips-notes">
        <!-- UY BEGIN -->
		<div id="uyan_frame"></div>
		<script type="text/javascript" src="http://v2.uyan.cc/code/uyan.js?uid=2132596"></script>
		<!-- UY END -->
          <!--   <h2>Tips and notes</h2>
            <div class="passage">
                <h5>Masculine and Feminine Nouns</h5>
                <p>
                    &nbsp;&nbsp;&nbsp;&nbsp;In Spanish all nouns are masculine or feminine.
                    Usually, nouns that end with an "o" are masculine,
                    and nouns that end with an "a" are feminine.
                    For example, "manzana" (apple) is feminine and "diario" (newspaper) is masculine.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;The articles "el" and "un" are used with masculine nouns,
                    and the articles "la" and "una" are used with feminine nouns.
                    "The apple" is "la manzana" and "a newspaper" is "un diario."<br>
                </p>
            </div>
            <div class="passage">
                <h5>Accent Marks</h5>
                <p>
                    &nbsp;&nbsp;&nbsp;&nbsp;Vowels in Spanish can have an accent mark, such as the "u" in "menú" (menu).
                    One use of the accent mark is to indicate which syllable should be stressed in the pronunciation.
                    For example, in "teléfono" (telephone), the second "e" has the most stress.<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;Accent marks are also used to distinguish homophones.
                    For example, "él" and "el" are homophones because they have the same pronunciation.
                    However, "él" is a masculine pronoun (meaning "he" or "him")
                    and "el" is a masculine article (meaning "the").<br>
                </p>
            </div>
        </div>
    </div>--> 

    <div class="right-main">
        <div class="right-panel-pink">
            <span class="lesson-icon"></span>
            <div class="level-bar">

                <div class="process">Strength:<span>3</span>/<span>5</span></div>
            </div>
            <div class="strengthen">Strengthen &nbsp;<span>Basics 1</span></div>
        </div>
    </div>
</div>
<!--end of 正文-->
</body>
</html>