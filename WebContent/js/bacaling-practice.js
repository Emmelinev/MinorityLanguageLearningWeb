var qaList = null;
var questions = new Array();
var answers = new Array();
var types = new Array();
var userAnswerFlag = [0,0,0,0,0,0,0,0,0,0];
var userAnswers = new Array();
var expIds = new Array();
var wordIds = new Array();
var currentCount = 0;
$(document).ready(function() {
	 if (window.history && window.history.pushState) {
		$(window).on('popstate', function () {
			window.history.forward(1);
		});
	  }
	 initial();    
});
function initial(){
	var barId = getUrlParam("barId");
	var status = getUrlParam("status");
	var param = {"barId":barId,"status":status};
	var servlet = "../ExerciseServlet?method=1";
	var json = ajaxFunc(param,servlet);
	var list = null;
	qaList = json;
	console.log(json);
	for(var key in json){
		questions[key] = json[key].exampleSentence;
		answers[key] = json[key].answer;
		types[key] = json[key].type;
		expIds[key] = json[key].questionID;
		wordIds[key] = json[key].wordId;
	}
	$(".bar").css("width","0%");
	$(".calculator").html(currentCount+1);
	console.log(questions);
	console.log(answers);
	console.log(types);
	generateQuestions(0);
}
function generateQuestions(key){	
		var str = null;
		var p = convertToP(qaList[key].exampleSentence);
		switch(qaList[key].type){
		case 0: 
//			翻译题
			str = '<div class="question-translation">'
				+ '<h2 class="question">Translate this text</h2><div class="main-question">'
				+ '<div class="left"><p>'+p+'</p>'
				+ '</div><div class="middle"></div><div class="right">'
				+ '<textarea name="translationAnswer" id="translation'
				+ key +'">Type in English</textarea></div></div></div>';
			$(".div-back").append(str);
			 $(".main-question p span").mouseover(function(){
				 console.log($(this).html());
				 var word = $(this).html();
				 playAudio(word,"3",0);
			 });
			break;
		case 1: 
//		    选词填空			
			var o1 = qaList[key].option1;
			var o2 = qaList[key].option2;
			var os = new Array();		
			var pa = qaList[key].exampleSentence.replace(","," ,").replace("."," .").split(" ");
			var random = randomValue(0,pa.length-1);
			while(pa[random] != "," && pa[random] != "."){
				var random = randomValue(0,pa.length-1);
			}
			os[0] = o1;os[1] = o2;os[2] = pa[random];
			os = shuffleArray(os.length);//乱序
			
			var selector = '<p>';
			for(var i in pa){
				if(pa[i] != "," || pa[i] != "."){
					if(i == ranodm){
						pa[i] = '<select class="selector"><option>'
							+ os[0]+'</option><option>'
							+ os[1]+'</option><option>'
							+ os[2]+'</option></select>'
					}
				}
				selector = selector + pa[i] + " ";
			}
			selector += '</p>';
			console.log(selector);
			str = '<div class="question-missing"><h2 class="question">Select the missing word</h2>'
				+ '<div class="main-question">'+selector+'</div></div>';
			$(".div-back").append(str);
			break;
		case 2: 
//		    听写题
			str = ' <div class="question-listening"><h2 class="question">Type what you hear</h2>'
				+ '<div class="main-question" value="' 
				+ qaList[key].exampleSentence + '"><div class="normal-speed">speek'
				+ '<div class="slow-speed">slow </div><input id="listening'
				+ key +'" type="text" name="listenInput">'
				+ '</div></div>';
			$(".div-back").append(str);
			playAudio($(".main-question").attr("value"),"3",0);
			$(".normal-speed").click(function(){
				playAudio($(".main-question").attr("value"),"3",0);
			});
			$(".slow-speed").click(function(){
				playAudio($(".main-question").attr("value"),"3",-5);
			});
			break;
		case 3: 
			var o1 = qaList[key].option1.split(",");
			var o2 = qaList[key].option2.split(",");
			var os = new Array();		

			os[0] = o1;os[1] = o2;os[2] = qaList[key].exampleSentence;
			os = shuffleArray(os.length);
			
			str = '<div class="question-select-pic"><h2 class="question">Select translation of "<span>'
				+ qaList[key].answer + '</span>"</h2><ul class="pic-group">'
				+ '<li class="choice-item"><div class="p1"></div><span class="title">' 
				+ os[0][0] + ' <span>1</span></span></li>'
				+ '<li class="choice-item"><div class="p2"></div><span class="title">'
				+ os[1][0] +' <span>2</span></span></li>'
				+ '<li class="choice-item"><div class="p3"></div><span class="title">' 
				+ os[2][0] + ' <span>3</span></span></li></ul></div>';
			$(".div-back").append(str);
			$(".pic-group li .p1").css({"background":'url("../images/'+os[0][1]+'") no-repeat center',"background-size":"140px 140px"});
			$(".pic-group li .p2").css({"background":'url("../images/'+os[1][1]+'") no-repeat center',"background-size":"140px 140px"});
			$(".pic-group li .p3").css({"background":'url("../images/'+os[2][1]+'") no-repeat center',"background-size":"140px 140px"});
			break;
		}
		currentCount += 1;
}
function convertToP(sentence){
	var p = '<span>';
	var sentenceArray = sentence.replace(","," ,").replace("."," .").split(' ');
	console.log(sentenceArray);
	for(var s in sentenceArray){
		p = p + sentenceArray[s] + ' </span><span>' ;
	}
	console.log(p);
	return p;
}
function randomValue(start,range){
	 if(!range && range!=0 || !start){return "?";}   
    return Math.floor( ( Math.random() * range ) + start );  
	
}
function shuffleArray(num) {  
	var a=[];  
    for (var i = 0; i < num; i++) {  
        aLuanXu[i] = i;  
    }  
    for (var i = 0; i < num; i++) {  
        var iRand = parseInt(num * Math.random());  
        var temp = a[i];  
        a[i] = a[iRand];  
        a[iRand] = temp;  
    }  
    return a;   
}   
function playAudio(word,language,speed){
	var lan = null;
	switch(language){
	case "1": lan = 'en-gb';break;
	case "2": lan = 'zh-cn';break;
	case "3": lan = 'es-es';break;
	case "4": lan = 'ja-jp';break;
	}
	$.speech({
        key: 'a5d6cea34d5c4aac84b4790307adeebe',
        src: word,
        hl: lan,
        r: speed, 
        c: 'mp3',
        f: '44khz_16bit_stereo',
        ssml: false
    });
}
function checkAnswer(currentCount){
	if(types[currentCount]==0){
		userAnswers[currentCount] = $("#translation"+currentCount).html();
	}
	if(types[currentCount]==1){
		userAnswers[currentCount] = $(".selector").find("option:selected").text();
	}
	if(types[currentCount]==2){
		userAnswers[currentCount] = $("#listening"+currentCount).val();
	}
	if(types[currentCount]==3){
		userAnswers[currentCount] = $("#choice-item-selected span").html();
	}
	console.log(userAnswer[currentCount]);
	if(userAnswers[currentCount].toLowerCase() == currentCount[currentCount].toLowerCase()){
		userAnswerFlag[currentCount] = 1;
		return true;
	}else{
		userAnswerFlag[currentCount] = 0;
		return false;
	}	
}
function passQ(key){
	userAnswers[key] = "passed";
	userAnswerFlag[key] = -1;
}
function generateFoot(type,key){
	var str = null;
	if(type == 0){
		str = '<div class="foot-default"><div class="skip">Skip</div>'
			+ '<div class="btn-green check answer-check">Check</div></div>';
		$(".div-back").append(str);
		 $(".answer-check").click(function(){
			checkAnswer(currentCount);
			var p = (currentCount+1)*10;
			$(".bar").css("width",p+"%");
		 });
		 $(".Skip").click(function(){
			 passQ(currentCount);
		 });
	}
	if(type == 1){
		str = '<div class="foot-correct"><div class="icon-correct"></div>'
			+ '<div class="correct-right"><span>You are correct.</span>'
			+ '<div class="btn-general report">Report a Problem</div></div>'
			+ '<div class="continue-practice-right btn-general continue">continue</div></div';
		$(".div-back").append(str);
		 $(".continue-practice-right").click(function(){
			 if(currentCount != 10){
				 generateQuestions(currentCount); 
			 }else{
				 postAnswers();
				 generateFoot(t,currentCount);
				 currentCount = 0;
			 }
		 });
	}
	if(type == 2){
		str = '<div class="foot-wrong"><div class="icon-wrong"></div>'
			+ '<div class="wrong-right"><h4>Correct solutions:</h4><span>'
			+ answers[key] +'</span><div class="btn-general report">Report a Problem</div>'
			+ '</div><div class="continue-practice-wrong btn-general continue">continue</div></div>';
		$(".div-back").append(str);
		 $(".continue-practice-wrong").click(function(){
			 if(currentCount != 10){
				 generateQuestions(currentCount); 
			 }else{
				 postAnswers();
				 generateFoot(t,currentCount);
				 currentCount = 0;
			 }
		 });
	}
	if(type == 3){
		str = '<div class="foot-final"><div class="btn-general review">Review lesson</div>'
			+ '<div class="continue-quit btn-general continue">continue</div></div>';
		$(".div-back").append(str);
		 $(".continue-quit").click(function(){
			 location.href = "index.jsp";
		 });
	}
}
function postAnswers(){
	var barId = getUrlParam("barId");
	var param = {"userAnswers":userAnswers,"types":types,"userAnswerFlag":userAnswerFlag,"expIds":expIds,"wordIds":wordIds,"barId":barId};
	var servlet = "../ExerciseServlet?method=3";
	var json = ajaxFunc(param,servlet);
}
function ajaxFunc(param,servlet){
    var json = null;
    $.ajax({
        async : false,
        cache : false,
        type : 'POST',
        url : servlet,
        data: param,
        dataType:"json",
        error : function(data) {
        },
        success : function(data){
            json = data;
        }
    });
    return json;
}