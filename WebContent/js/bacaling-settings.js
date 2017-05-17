var user_name = null;
var user_email = null;
var current_pwd = null;
var new_pwd = null;
var confirm_pwd = null;
var a_account = {"user_name":"0","email":"0","autoplay":"0","effect":"0","profile_pic":"0"};
var a_profile = {"new_pwd":"0"};
var a_notice = {"notice":"0","reminder":"0"};
var a_goal = {"goal":"0"};
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
	
	radioChange();
	getLevels();
    getLevel();	
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
function radioChange(){
	$("input:radio[name='autoplay']").change(function (){
		var ckd = $("input:radio[name='autoplay']:checked").val();
		$("#submit_account").attr("disabled",false);
		if(ckd == "on"){
			a_account["autoplay"] = "1";
			$("#autoplay0").attr("class","radio-out-selected");
			$("#autoplay1").attr("class","radio-out");
		}else if(ckd == "off"){
			a_account["autoplay"] = "2";
			$("#autoplay0").attr("class","radio-out");
			$("#autoplay1").attr("class","radio-out-selected");
		}
		console.log(a_account);
	});
	$("input:radio[name='effect']").change(function (){
		var ckd = $("input:radio[name='effect']:checked").val();
		$("#submit_account").attr("disabled",false);
		if(ckd == "on"){
			a_account["effect"] = "1";
			$("#effect0").attr("class","radio-out-selected");
			$("#effect1").attr("class","radio-out");
		}else if(ckd == "off"){
			a_account["effect"] = "2";
			$("#effect0").attr("class","radio-out");
			$("#effect1").attr("class","radio-out-selected");
		}
		console.log(a_account);
	});
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
function getPhotoSize(obj){
    photoExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
    if(photoExt=='.jpg'||photoExt=='.png'||photoExt=='jpeg'||photpExt=='bmp'||photoExt=='gif'){
//        return true;
    }else{
    	alert("请上传后缀名为jpg的照片!");
//        return false;
    }
    var fileSize = 0;
    var isIE = /msie/i.test(navigator.userAgent) && !window.opera;            
    if (isIE && !obj.files) {          
         var filePath = obj.value;            
         var fileSystem = new ActiveXObject("Scripting.FileSystemObject");   
         var file = fileSystem.GetFile (filePath);               
         fileSize = file.Size;         
    }else {  
         fileSize = obj.files[0].size;     
    } 
//    1*1024*1024/1024*100/100
    fileSize=Math.round(fileSize/1024*1024); //单位为KB
    console.log("filesize-"+fileSize);
    if(fileSize>=1){
        alert("照片最大尺寸为1MB，请重新上传!");
        return false;
    }
}


