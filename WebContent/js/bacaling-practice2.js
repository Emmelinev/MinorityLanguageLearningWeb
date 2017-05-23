var qaList = null;
var questions = new Array();
var answers = new Array();
var types = new Array();
var userAnswerFlag = [0,0,0,0,0,0,0,0,0,0];
var userAnswers = new Array();
var expIds = new Array();
var wordIds = new Array();
var currentCount = 0;
var hasWrong = new Array();
var redo = 0;
//var opId = 0;
$(document).ready(function() {
	 if (window.history && window.history.pushState) {
		$(window).on('popstate', function () {
			window.history.forward(1);
		});
	  }
	 $(".question-select-pic").css("display","none");
	 $(".question-translation").css("display","none");
	 $(".question-missing").css("display","none");
	 $(".question-listening").css("display","none");
	 $(".ending").css("diaplay","none");
	 
	 $(".foot-default").css("display","none");
	 $(".foot-correct").css("display","none");
	 $(".foot-wrong").css("display","none");
	 $(".foot-final").css("display","none");
 
		initial();
		getMsgCount();
		getLevels();
		getLevel();
		 $(".div-alert").click(function(){
		    	$(".new-count").hide();
		    	getMessage();
		    	$(".message").slideToggle("fast");
		    });
	 $("#answer-check").click(function(){
		 answerCheck();
	 });
	 $(".skip").click(function(){
		 skipQuestion();
	 });
	 $("#continue-practice-right").click(function(){
		 nextPage();
	 });
	 $("#continue-practice-wrong").click(function(){
		 nextPage();
	 });
	 $(".input-div").click(function(event){
		 if($(".question-translation").css("display")=="block"){
			 console.log($(this).html());
			 $("textarea").append($(this).html());
		 }
		 if($(".question-listening").css("display")=="block"){
			 console.log($(this).html());
//			 $("#listening-input").attr("value",)
			 var s = $("#listening-input").val+$(this).html();
			 $("#listening-input").attr("value",s);
		 }
         
     });
	 $(document).keydown(function(event){  
		if(event.keyCode == '13'){ 
			if($(".foot-default").css("display")=="block"){
				answerCheck();
				return true;
			}
			if($(".foot-correct").css("display")=="block"){
				 nextPage();
				 return true;
			}
			if($(".foot-wrong").css("display")=="block"){
				nextPage();
				return true;			}
		}else if (event.keyCode == '39'){ 
			if($(".foot-correct").css("display")=="block"){
				 nextPage();
				 return true;
			}
			if($(".foot-wrong").css("display")=="block"){
				 nextPage();
				 return true;
			}
		} 
	}); 
	 $("textarea").mousedown(function(){
		if($("textarea").val()=="Type in English"){
			$("textarea").val("");
		}
	 });
	 $(".foot-final .review").click(function(){
		 for(var key = 0; key<10; key++){
			 console.log("key"+key);
			 var str = null;
			 if(userAnswerFlag[key] == 1){
				 str = '<div class="card-correct" id="card'+key+'" onclick="cardClick(event)"><h5>Select<br>question of:</h5>'
					 + '<span class="icon-correct-sm"></span><span>'
					 + questions[key]+'</span></div>';
			 }else{
				 str = '<div class="card-wrong" id="card'+key+'" onclick="cardClick(event)"><h5>Select<br>question of:</h5>'
					 + '<span class="icon-correct-sm"></span><span>'
					 + questions[key]+'</span></div>';
			 }
			 $(".card-group").append(str);
		 }
		 $(".review-panel").css("display","block");
	 });	 
	 $(".review-panel .close").click(function(){
		$(".review-panel").fadeOut(); 
	 });
	 
});
function sumArray(a){
	var sum = 0;
	for(var i in a){
		sum += a[i];
	}
	return sum;
}
function nextPage(){
//	if(types[currentCount-1]==0){
//		$("textarea").attr("disabled",false);
//	}if(types[currentCount-1]==1){
//		$("select").attr("disabled",false);
//	}if(types[currentCount-1]==2){
//		$("input").attr("disabled",false);
//	}	
	 if(currentCount < 10){
//		 还未做完10道题目
		 generateQuestions(currentCount); 
		 generateFoot(0);
	 }else if(hasWrong.length>0){
//		 做完全部题目，但是有错题
		 generateQuestions(hasWrong[0]); 
		 generateFoot(0);
	 }else{//		 
		 postAnswers();
		 generateFoot(3);
		 currentCount = 0;
	 }
	 $("textarea").val("");
	 $("#listening-input").val("");
}
function answerCheck(){	 
//	if(types[currentCount-1]==0){
//		$("textarea").attr("disabled",true);
//	}if(types[currentCount-1]==1){
//		$("select").attr("disabled",true);
//	}if(types[currentCount-1]==2){
//		$("input").attr("disabled",true);
//	}
//	 console.log("answer check");
	if(currentCount <= 10){
		if(checkAnswer(currentCount,1)){
			var p = (getRightCount(userAnswerFlag))*10;
			$(".bar").css("width",p+"%");	
			console.log("p-"+$(".bar").css("width"));
			generateFoot(1);
		}else{
			generateFoot(2);
		}
	}else{
		if(checkAnswer(hasWrong[0],2)){
			var p = (parseInt($(".bar").css("width").replace("%",""))/540)*100+10;
			$(".bar").css("width",p+'%');
			console.log("p-"+$(".bar").css("width"));
			generateFoot(1);
		}else{
			generateFoot(2);
		}
	}
	console.log(hasWrong);
}
function getRightCount(a){
	var r = 0;
	for(var i in a){
		if(a[i]==1) r++; 
	}
	return r;
}
function skipQuestion(){
	userAnswers[currentCount-1] = "passed";
	userAnswerFlag[currentCount-1] = -1;
	nextPage();
}
function cardClick(event){
	console.log(event.target.id);
	var cardId = event.target.id.replace("card","");

	console.log("cardId="+cardId);
//	<div class="close"></div>
	var str = '<div class="pop-answer" id="pop'+cardId+'"  onclikc="closePop(event);">YOUR RESPONSE</p>'
		 + '<P>"<SPAN>'+ userAnswers[cardId] +'</SPAN>"</P><p>CORRECT RESPONSE</p>'
		 + '<p>"<span>'+ answers[cardId] +'</span>"</p></div>';
	$("#card"+cardId).append(str);
	$("#pop"+cardId).fadeToggle();
//	$(".pop-answer").remove();
}
function closePop(event){
//	 $(".pop-answer .close").click(function(){
//			$(".pop-answer").css("display","none"); 
//		 });
//	console.log("target-"+event.target.id);
//	var popId = event.target.id.replace("pop","");
	$(".pop-answer").remove();
}
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
	generateFoot(0);
}
function randomWord(pa){
	var random = randomValue(0,pa.length-1);
	if(pa[random] == "," && pa[random] == "."){
		randomWord(word);
//		var random = randomValue(0,pa.length-1);
	}else{
		return random;
	}
}
function generateQuestions(key){
	console.log(key);
		switch(qaList[key].type){
		case 0: 
//			翻译题
			var p = convertToP(qaList[key].exampleSentence);
			$(".question-translation").css("display","block");
			$(".question-select-pic").css("display","none");
			 $(".question-missing").css("display","none");
			 $(".question-listening").css("display","none");
			$(".question-translation p").html(p);
			$(".question-translation p").attr("value",questions[currentCount]);
			if(window.localStorage.autoplay == "1"){
				playAudio($(".question-translation p").attr("value"),"3",0);
			}			
			 $(".main-question p span").mouseover(function(){
				 console.log($(this).html());
				 var word = $(this).html();
				 playAudio(word,"3",0);
			 });
			break;
		case 1: 
////		    选词填空		
			var o1 = qaList[key].option1;
			var o2 = qaList[key].option2;
			var os = new Array();		
			var pa = qaList[key].exampleSentence.replace(","," ,").replace("."," .").split(" ");
			var rand = randomWord(pa);
			console.log("random-"+rand);
//			opId = rand;
			os[0] = o1;os[1] = o2;os[2] = pa[rand];
			os = shuffleArray(os,os.length);//乱序
			console.log(os);
			
			var selector = '<p>';
			for(var i in pa){
				if(i == rand){
					pa[i] = '<select class="selector"><option>'
						+ os[0]+'</option><option>'
						+ os[1]+'</option><option>'
						+ os[2]+'</option></select>'
				}
				selector = selector + pa[i] + " ";
			}
			selector += '</p>';
			console.log(selector);
			$(".question-translation").css("display","none");
			 $(".question-missing").css("display","block");
			 $(".question-listening").css("display","none");
			 $(".question-select-pic").css("display","none");
			$(".question-missing .main-question").html(selector);
			$("select").attr("value",rand);
			break;
		case 2: 
////		    听写题
			$(".question-translation").css("display","none");
			 $(".question-missing").css("display","none");
			 $(".question-listening").css("display","block");
			 $(".question-select-pic").css("display","none");
			$(".question-listening .main-question").attr("value",qaList[key].exampleSentence);
//			$("#practice-content").append(str);
			if(window.localStorage.autoplay == "1"){
				playAudio($(".question-listening .main-question").attr("value"),"3",0);
			}
			
			$(".normal-speed").click(function(){
				playAudio($(".question-listening .main-question").attr("value"),"3",0);
			});
			$(".slow-speed").click(function(){
				playAudio($(".question-listening .main-question").attr("value"),"3",-5);
			});
			break;
		case 3: 
			var o1 = qaList[key].option1.split(",");
			var o2 = qaList[key].option2.split(",");
			var os = new Array();		

			os[0] = o1;os[1] = o2;os[2] = qaList[key].exampleSentence;
			os = shuffleArray(os.length);
			$(".question-translation").css("display","none");
			 $(".question-missing").css("display","none");
			 $(".question-listening").css("display","none");
			 $(".question-select-pic").css("display","block");
			$(".choice-item .t1").html(os[0][0] + ' <span>1</span>');
			$(".choice-item .t2").html(os[1][0] + ' <span>2</span>');
			$(".choice-item .t3").html(os[2][0] + ' <span>3</span>');
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
function randomValue(range){
    return Math.floor( ( Math.random() * range ) + 0 );  
	
}
function shuffleArray(a,num) {  
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
function checkAnswer(count,type){
	if(type == 1){
		var key = count - 1;
		if(types[key]==0){
//			userAnswers[key] = $("#translation"+key).val();
			
			userAnswers[key] = $(".question-translation textarea").val();
		}
		if(types[key]==1){
			userAnswers[key] = $(".selector").find("option:selected").text();
			answers[key] = questions[key].replace(","," ,").split(" ")[$("select").attr("value")];
		}
		if(types[key]==2){
			userAnswers[key] = $("#listening-input").val();
			answers[key] = questions[key];
		}
		if(types[key]==3){
			userAnswers[key] = $("#choice-item-selected span").val();
		}
		console.log(userAnswers[key]);
		console.log(answers[key]);
		if($.trim(userAnswers[key]).toLowerCase().replace(",","").replace(".","").replace(" ","") == $.trim(answers[key]).toLowerCase().replace(",","").replace(".","").replace(" ","")){
			userAnswerFlag[key] = 1;
			$(".foot-default").css("display","none");
			var audio = $("#success")[0];
			audio.play();
			generateFoot(1);
			return true;
		}else{
			userAnswerFlag[key] = 0;
//			hasWrong[key] = 1;
			hasWrong.push(key);
			$(".foot-default").css("display","none");
			var audio = $("#fail")[0];
			audio.play();
			generateFoot(2);
			return false;
		}	
	}else{
		var answer = null;
		if(types[count]==0){
			answer = $("#translation"+count).val();
		}
		if(types[count]==1){
			answer = $(".selector").find("option:selected").text();
//			answers[count] = questions[count].replace(","," ,").split(" ")[$("select").attr("value")];
		}
		if(types[count]==2){
			answer = $("#listening-input").val();
//			answers[count] = questions[count];
		}
		if(types[count]==3){
			answer = $("#choice-item-selected span").val();
		}
		console.log(answer);
		console.log(answers[count]);
//		if($.trim(answer).toLowerCase().replace(",","").replace(".","").replace(" ","") == $.trim(answers[count]).toLowerCase().replace(",","").replace(".","").replace(" ","")){
		if(true){
			hasWrong.shift();
			$(".foot-default").css("display","none");
			if(window.localStorage.effect == "1"){
				var audio = $("#success")[0];
				audio.play();
			}
			generateFoot(1);
			return true;
		}else{
			$(".foot-default").css("display","none");
			if(window.localStorage.effect == "1"){
				var audio = $("#fail")[0];
				audio.play();
			}
			generateFoot(2);
			return false;
		}	
	}	
}

function generateFoot(type){
	var str = null;
	if(type == 0){
		$(".ending").css("diaplay","none");
		$(".foot-default").css("display","block");
		$(".foot-correct").css("display","none");
		$(".foot-wrong").css("display","none");
		$(".foot-final").css("display","none");
	}
	if(type == 1){
		$(".ending").css("diaplay","none");
		$(".foot-default").css("display","none");
		$(".foot-correct").css("display","block");
		$(".foot-wrong").css("display","none");
		$(".foot-final").css("display","none");
	}
	if(type == 2){
		$(".ending").css("diaplay","none");
		$(".foot-default").css("display","none");
		$(".foot-correct").css("display","none");
		$(".foot-wrong").css("display","block");
		$(".foot-final").css("display","none");
		$(".foot-wrong span").html(answers[currentCount-1]);
	}
	if(type == 3){
		var audio = $("#win")[0];
		audio.play();
		$(".ending").css("display","block");
		 $(".question-select-pic").css("display","none");
		 $(".question-translation").css("display","none");
		 $(".question-missing").css("display","none");
		 $(".question-listening").css("display","none");
		$(".foot-default").css("display","none");
		$(".foot-correct").css("display","none");
		$(".foot-wrong").css("display","none");
		$(".foot-final").css("display","block");
		 $(".continue-quit").click(function(){
			 location.href = "index.jsp";
		 });
	}
}
//function 
function postAnswers(){
	var barId = getUrlParam("barId");
	var userAnswer = userAnswers.toString();
	var param = {"userAnswers":userAnswer,"types":types.toString(),"userAnswerFlag":userAnswerFlag.toString(),"expIds":expIds.toString(),"wordIds":wordIds.toString(),"barId":barId};
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