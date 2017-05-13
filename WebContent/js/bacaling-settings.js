var user_name = null;
var user_email = null;
var current_pwd = null;
var new_pwd = null;
var confirm_pwd = null;
$(document).ready(function() {
	 if (window.history && window.history.pushState) {
		$(window).on('popstate', function () {
			window.history.forward(1);
		});
	  }
   $("#submit_account").attr("disabled",true);
   $("#submit_profile").attr("disabled",true);
   $("#submit_notice").attr("disabled",true);
   $("#submit_goal").attr("disabled",true);

	user_name = $("#user_name").val();
	user_email = $("#email").val();
	current_pwd = $("#current_pwd").val();
	new_pwd = $("#new_pwd").val();
	confirm_pwd = $("#confirm_pwd").val();
});
function txtchange(event){
	console.log(event.target.id);
	var elemId = event.target.id;
	if(checkChanged(getVal(elemId),$("#"+elemId).val())&&checkNull(elemId)){
		   $("#"+getButton(elemId)).attr("disabled",false);
	    }else{
	    	$("#"+getButton(elemId)).attr("disabled",true);
	    }
}
function checkChanged(orig_value,new_value){
	if($.trim(orig_value) == $.trim(new_value)){
		return false;
	}else{
		return true;
	}
}
function checkNull(obj){
	if($.trim($("#"+obj).val()).length > 0){
		return true;
	}else{
		return false;
	}
}
function getButton(name){
	var ret = null;
	switch(name){
	case "user_name": ret = "submit_account"; break;
	case "email": ret = "submit_account"; break;
	case "current_pwd": ret = "submit_profile";break;
	case "new_pwd": ret = "submit_profile"; break;
	case "confirm_pwd": ret = "submit_profile"; break;
	}
	console.log("return button-"+ret);
	return ret;
}
function getVal(name){
	var ret = null;
	switch(name){
	case "user_name": ret = user_name; break;
	case "email": ret = user_email; break;
	case "current_pwd": ret = current_pwd;break;
	case "new_pwd": ret = new_pwd; break;
	case "confirm_pwd": ret = confirm_pwd; break;
	}
	console.log("return value-"+ret);
	return ret;
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
function getTable(){
    var str = {"userId":"2","cLanguage":"3"};
    var param = jQuery.param(str);
    var servlet = "../WordServlet?method=1";
    return ajaxFunc(param,servlet);
}



