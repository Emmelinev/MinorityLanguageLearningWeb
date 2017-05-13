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

function setRead(){
	var param = {}
}

//每两秒自动获取一次未读消息  
function getMsgCount(){
	var param = {"user_id":"1"};
	var servlet = "../MessageServlet?method=3";
	var ret = ajaxFunc(param,servlet);
	console.log("ret-"+ret);
	if(ret.valid == true){
//		$(".div-alert").attr("opacity","1");
		$(".div-alert").animate({"opacity":"1"},100); 
		$(".new-count").show();
	}else{
		$(".new-count").hide();
//		$(".div-alert").attr("opacity","0.5");
		$(".div-alert").animate({"opacity":"0.5"},100); 
	}
	setTimeout("getMsgCount()",60000);  
}  
