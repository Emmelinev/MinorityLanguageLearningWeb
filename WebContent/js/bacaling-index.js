$(document).ready(function() {
	 if (window.history && window.history.pushState) {
		$(window).on('popstate', function () {
			window.history.forward(1);
		});
	  }
    $("#language_name").prepend("<span>" + getLanguageName() + "</span>");
    
    $(".div-alert").click(function(){
    	$(".new-count").hide();
    	getMessage();
    	$(".message").slideToggle("fast");
    });
    
    $(".close").click(function(){
    	$(".message").hide();
    	$(".div-alert").animate({"opacity":"0.5"},100); 
    });
    $("#play_sentence").click(function(){
    	getAudioNormal($("#sentence").html(),$("#language_hide").html());
    });
    $("#play_word").click(function(){
    	getAudioNormal($(".word").html(),$("#language_hide").html());
    });
    $(".head").click(function(){
    	getWord();
    });
    getMsgCount();
    getLevels();
    getLevel();
    getWord();
    getLessons();
});


function getLevel(){
	var param = {"user_id":"1"};
	var servlet = "../UserInfoServlet?method=7";
	var json = ajaxFunc(param,servlet);
	$("#language_level").html("Level "+json.level);
	var img_url = "../images/"+setCountry(json.language)+".png";
    $(".span-flag").css('background-image','url('+img_url+')');
    $(".flag").css('background-image','url('+img_url+')');
}
function getWord(){
	var param = {"user_id":"1"};
	var servlet = "../WordServlet?method=5";
	var json = ajaxFunc(param,servlet);

	$(".word").html(json.word);
	$("#translation").html(json.word_tanslation);
	$("#word_class").html(json.word_class);
	$("#sentence").html(json.example);
	$("#sentence_example").html(json.translation);
	var lan = $("#language_hide").html();
	if(lan == 4){
		$("#word_pronunce").html("<"+json.pronunce+">");
	}else{
		$("#word_pronunce").hide();
	}
}
function getLessons(){
	var param = {"user_id":"1"};
	var servlet = "../LessonServlet?method=1";
	var json = ajaxFunc(param,servlet);
	for(var key in json){
		var str = '<li id="'
			+ json[key].lessonName +'" class="lesson ' 
			+ json[key].lessonName + '"><div class="logo"><a href="LessonPage.jsp?lessonId='
			+ json[key].lessonId +'"><img src="' 
			+ json[key].lessonImg + '"></a></div><div class="level-bar"><div class="level"  id="level-'+ json[key].lessonName +'"></div></div><h5>'
			+ json[key].lessonName + '</h5></li>';
		$("#lesson_list").append(str);
		$("#level-"+json[key].lessonName).css("width",json[key].progress*100+"%");
		$("#level-"+json[key].lessonName).css("background-color",progressLevel(json[key].progress));
	}
}

function progressLevel(progress){
	var color = null;
	if(progress <= 0.34 ){
		color = "#EE5C42";
	}
	if(progress > 0.34 && progress <= 0.78){
		color = "#f1c232";
	}
	if(progress > 0.78 && progress <1){
		color = "#EEE685";
	}
	if(progress == 1){
		color = "#9BCD9B";
	}
	return color;
}

