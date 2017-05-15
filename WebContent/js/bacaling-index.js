$(document).ready(function() {
	 if (window.history && window.history.pushState) {
		$(window).on('popstate', function () {
			window.history.forward(1);
		});
	  }
   
    $("#language_name").prepend("<span>" + getLanguageName() + "</span>");
    console.log(getLanguageName());
    
    $(".div-alert").click(function(){
    	$(".new-count").hide();
    	getMessage();
    	$(".message").slideToggle("fast");
    });
    
    $(".close").click(function(){
    	$(".message").hide();
    	$(".div-alert").animate({"opacity":"0.5"},100); 
    });
    getMsgCount();
    getLevels();
    getLevel();
});
//获取通知
function getMessage(){
	var param = {"user_id":"1"};
	var servlet = "../MessageServlet?method=1";
	var json = ajaxFunc(param,servlet);
//	console.log(json);
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
		var language = setLanguage(json[key].language);
		var country = setCountry(json[key].language);
		var str = '<li><img src="../images/'
			+ country + '.png"><span class="language">'
			+ language + '</span><span class="level">level '
			+ json[key].level + '</span></li>';
//        <li>
//        <img src="../images/spain.png">
//        <span class="language">Spanish</span>
//        <span class="level">level 9</span>
//    </li>
		$("#user_language").append(str);
	}
}
function getLevel(){
	var param = {"user_id":"1"};
	var servlet = "../UserInfoServlet?method=7";
	var json = ajaxFunc(param,servlet);
	$("#language_level").html("Level "+json.level);
}
function getWord(){
	
}
function setLanguage(id){
	var language = null;
	switch(id){
		case "1": language = "English";break;
		case "2": language = "Chinese";break;
		case "3": language = "Spanish";break;
		case "4": language = "Japnaese";break;
	}
	return language;
}
function setcountry(id){
	var country = null;
	switch(id){
		case "1": country = "uk";break;
		case "2": country = "china";break;
		case "3": country = "spain";break;
		case "4": country = "japan";break;
	}
	return country;
}
//每两秒自动获取一次未读消息  
function getMsgCount(){
	var param = {"user_id":"1"};
	var servlet = "../MessageServlet?method=3";
	var ret = ajaxFunc(param,servlet);
	console.log("ret-"+ret);
	if(ret.valid == true){
		$(".div-alert").animate({"opacity":"1"},100); 
		$(".new-count").show();
	}else{
		$(".new-count").hide();
		$(".div-alert").animate({"opacity":"0.5"},100); 
	}
	setTimeout("getMsgCount()",60000);  
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
            console.log(data);
            json = data;
        }
    });
    return json;
}