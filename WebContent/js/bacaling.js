$(document).ajaxStart(function () {
	$("#loading").show();
	}).ajaxStop(function () {
	$("#loading").hide();
});
//===================顶部导航栏======================================
//获取通知
function getMessage(){
	var param = {"user_id":"1"};
	var servlet = "../MessageServlet?method=1";
	var json = ajaxFunc(param,servlet);
	for(var key in json){
		var str = null;
		if(json[key].status == 0){
			str = '<li><p  style="color:#fa7474;font-weight:800;">' + json[key].messageContent + "</p><span>" 
			+ json[key].sendDate + "</span></li>";
		}else{
			str = '<li><p>' + json[key].messageContent + "</p><span>" 
			+ json[key].sendDate + "</span></li>";
		}
		$("#srcoll-message").append(str);
	}
}

function getLevels(){
	var param = {"user_id":"1"};
	var servlet = "../UserInfoServlet?method=8";
	var json = ajaxFunc(param,servlet);
	for(var key in json){
		var language = setLanguage(json[key].currentLanguage,1);
		var country = setCountry(json[key].currentLanguage);
		var str = '<li class="language_list" onclick="changeLanguage(event);"><img src="../images/'
			+ country + '.png"><span class="language">'
			+ language + '</span><span class="level">level '
			+ json[key].level + '</span></li>';
		$("#user_language").append(str);
	}
}
function changeLanguage(event){
	var language = setLanguage($(event.target).html(),2);
	var param = {"language":language};
	var servlet = "../SettingsServlet?method=6";
	var json = ajaxFunc(param,servlet);
	location.reload();
}
function setLanguage(id,type){
	var language = null;
	if(type == 1){
		switch(id){
		case 1: language = "English";break;
		case 2: language = "Chinese";break;
		case 3: language = "Spanish";break;
		case 4: language = "Japanese";break;
		}
	}else if(type == 2){
		switch(id){
		case "English": language = 1;break;
		case "Chinese": language = 2;break;
		case "Spanish": language = 3;break;
		case "Japanese": language = 4;break;
		}
	}
	return language;
}
function setCountry(id){
	var country = null;
	switch(id){
		case 1: country = "uk";break;
		case 2: country = "china";break;
		case 3: country = "spain";break;
		case 4: country = "japan";break;
	}
	return country;
}
//每两秒自动获取一次未读消息  
function getMsgCount(){
	var param = {"user_id":"1"};
	var servlet = "../MessageServlet?method=3";
	var ret = ajaxFunc(param,servlet);
	if(ret.valid == true){
		$(".div-alert").animate({"opacity":"1"},100); 
		$(".new-count").show();
	}else{
		$(".new-count").hide();
		$(".div-alert").animate({"opacity":"0.5"},100); 
	}
	setTimeout("getMsgCount()",60000);  
}
function getAudioNormal(word,language){
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
        r: 0, 
        c: 'mp3',
        f: '44khz_16bit_stereo',
        ssml: false
    });
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
//------------------------------------------------------------------------------------------

function  popDiv(showId, hideId,type) {
    if(type == 1){
        $("#" + hideId).hide();
        $("#" + showId).fadeIn("fast");
        $("#shade").fadeIn("fast");
        $("#phonenum").focus();
    }else if(type == 2){
        $("." + showId).show();
        $("." + hideId).hide();
    }
}

function closeDiv(closeId, showId,type) {
    // $("#frm-sign-up").hide();
    if(type == 1){
        $("#" + closeId).fadeOut("fast");
        $("#shade").fadeOut("fast");
        $("#" + showId).show();
    }else if(type == 2){
        $("." + closeId).hide();
        $("." + showId).show();
    }
}
function getLanguageName(){
	var language_name =  null;
//	var vv= $.session.get("current_language");
	var language_id = $("#language_hide").html();
//	console.log(vv);
	switch(language_id)
	{
		case('1'): language_name = 'English'; break;
		case('2'): language_name = 'Chinese'; break;
		case('3'): language_name = 'Spanish'; break;
		case('4'): language_name = 'Japanese'; break;
		default: language_name ='Exception';
	}
//	console.log(language_name);
	return language_name;
}
//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null; 
}