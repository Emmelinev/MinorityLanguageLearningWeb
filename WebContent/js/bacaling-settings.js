var user_name = null;
var user_email = null;
var current_pwd = null;
var new_pwd = null;
var confirm_pwd = null;
var notice = null;
var autoplay = null;
var effect = null;
var goal = null;
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
	new_pwd = $("#new_pwd").val();
	confirm_pwd = $("#confirm_pwd").val();
	loadSettings(); //加载用户设置
	radioChange(); //监听radio值变化
	getLevels(); //导航栏，用户等级列表
    getLevel();	 //导航栏图标
    getMsgCount();
    checkboxChange(0);checkboxChange(1); //监听多选框值变化
    liRadioChange(0);liRadioChange(1);liRadioChange(2);liRadioChange(3); //监听list值变化
    checkPwd(); //监听密码输入值变化
    imgChange();//监听图片上传值变化  
    $("#deactive_account").click(function(){
    	$(".pop-div p").html('<p>'+popContext(1)+'</p>');
    	$(".background-div").show();
    	$(".pop-div").show();
    });

    $(".pop-no").click(function(){
    	console.log($(".pop-no").html());
    	$(".background-div").hide();
    	$(".pop-div").hide();
    });
    $(".pop-yes").click(function(){
    	deactive_account();
    });
});
function popContext(type){
	var p = null;
	switch(type){
	case 1: p = 'Are you sure to deactive your account? After this you can never login our website with this name.';break;
	}
	return p;
}
function txtchange(event,type){
	var elemId = event.target.id;
//	console.log("type:"+type);
	if(type == 1){
		if(!checkChanged(getVal(elemId),$("#"+elemId).val())&&checkNull(elemId)){
			   $("#"+getButton(elemId)).attr("disabled",false);
			   $("#"+elemId).attr("class","text-input");
		    }else{
		    	$("#"+getButton(elemId)).attr("disabled",true);
		    	$("#"+elemId).attr("class","text-input-wrong");
		    }
	}else{
		if(!checkChanged($("#new_pwd").val(),$("#confirm_pwd").val())&&checkChanged(current_pwd,$("#"+elemId).val())&&checkNull(elemId)){
			   $("#"+getButton(elemId)).attr("disabled",false);
			   $("#new_pwd").attr("class","text-input");
			   $("#confirm_pwd").attr("class","text-input");
		    }else{
		    	$("#"+getButton(elemId)).attr("disabled",true);
		    	$("#new_pwd").attr("class","text-input-wrong");
		    	$("#confirm_pwd").attr("class","text-input-wrong");
		    }
	}
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
	return ret;
}
function checkPwd(){
	$("#current_pwd").change(function(){
		if(current_pwd != $("#current_pwd").val()){
			$("#current_pwd").attr("class","text-input-wrong");
		}else{
			$("#current_pwd").attr("class","text-input");
		}
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
//	console.log("return button-"+ret);
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
            json = data;
        }
    });
    return json;
}
function checkPhotoSize(obj){
	$("#span-ext").css("color","#000");
	$("#span-size").css("color","#000");
    photoExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();
    if(photoExt=='.jpg'||photoExt=='.png'||photoExt=='jpeg'||photoExt=='bmp'||photoExt=='gif'){
    }else{
    	$("#span-ext").css("color","#fa7474");
        return false;
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
    fileSize=Math.round(fileSize/(1024*1024));
    console.log("filesize-"+fileSize);
    if(fileSize>=1){
    	$("#span-size").css("color","#fa7474");
        return false;
    }
}
function deactive_account(){
	var str = {"userId":"2"};
    var param = jQuery.param(str);
    var servlet = "../SettingsServlet?method=7";
    var json = ajaxFunc(param,servlet);
    if(json.valid==true){
    	$(window).attr('location','RedirectPage.jsp');
    }
}
function loadSettings(){
	var str = {"userId":"2"};
    var param = jQuery.param(str);
    var servlet = "../SettingsServlet?method=8";
    var json = ajaxFunc(param,servlet);
    current_pwd = json.password;
    $("#user_name").attr("value",json.name);
    $("#email").attr("value",json.email);
    var style = {
    				"background" : '#fff url('+json.img+') center no-repeat',
    				"background-size" : "50px 50px"
    			}
    $("#img-profile").css(style);
    setRadio("autoplay",json.autoplay);
    setRadio("effect",json.effect);
    setCheckbox("email",json.notice);
    setRiRadio(json.goal);
}
function setRadio(obj,value){
	if(value == 0){
		a_account[obj] = "2";
		$('input[name="'+obj+'"][value="off"]').attr("checked",true);
		$("#"+obj+"0").attr("class","radio-out");
		$("#"+obj+"1").attr("class","radio-out-selected");
	}else if(value == 1){
		a_account[obj] = "1";
		$('input[name="'+obj+'"][value="on"]').attr("checked",true);
		$("#"+obj+"1").attr("class","radio-out");
		$("#"+obj+"0").attr("class","radio-out-selected");
	}
}
function setCheckbox(obj,value){
	console.log("value-"+value);
	switch(value){
	case 0: 
		console.log("this is 0");
		$("input:checkbox").eq(0).attr("checked",false);
		$("input:checkbox").eq(1).attr("checked",false);
		notice = 0;
		break;
	case 1:
		console.log("this is 1");
		$("input:checkbox").eq(0).attr("checked",true);
		$("input:checkbox").eq(1).attr("checked",false);
		$("#mail0").attr("class","checkbox-out-selected");
		notice = 1;
		a_notice["notice"]="1";
		break;
	case 2:
		console.log("this is 2");
		$("input:checkbox").eq(0).attr("checked",false);
		$("input:checkbox").eq(1).attr("checked",true);
		$("#mail1").attr("class","checkbox-out-selected");
		notice = 2;
		a_notice["reminder"]="1";
		break;
	case 3:
		console.log("this is 3");
		$("input:checkbox").eq(0).attr("checked",false);
		$("input:checkbox").eq(1).attr("checked",true);
		$("#mail0").attr("class","checkbox-out-selected");
		$("#mail1").attr("class","checkbox-out-selected");
		notice = 3;
		a_notice["notice"]="1";
		a_notice["reminder"]="1";
		break;
	}
}
function setRiRadio(value){
	$('input[name="goal"][value="'+value+'"]').attr("checked",true);
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
	});
}
function checkboxChange(type){
	var index = null;
	if(type=='0') index = "notice";
	else if(type=='1') index = "reminder";
	$("#mail"+type).click(function(){
		if(a_notice[index] == "0"){
			$("input:checkbox").eq(type).attr("checked",true);
			a_notice[index] = "1";
			$("#mail"+type).attr("class","checkbox-out-selected");
		}else{
			$("input:checkbox").eq(type).attr("checked",false);
			a_notice[index] = "0";
			$("#mail"+type).attr("class","checkbox-out");
		}
		
		var reminder = null;
		(parseInt(a_notice["reminder"])==1) ? reminder = 2 : reminder = 0;
		var sum = parseInt(a_notice["notice"]) + reminder;
		if(sum != notice){
			$("#submit_notice").attr("disabled",false);
		}else{
			$("#submit_notice").attr("disabled",true);
		}
		
		if(sum == 0){
			$("input:checkbox").eq(2).attr("checked",true);
		}
	});
}
function liRadioChange(index){
//	$("#goal"+index).bind("click",function(){
//		$("input:radio[name='goal']").each(function (){
//			$(this).removeAttr("checked"); 
//		});		
//		$("input:radio[name='goal']").eq(index).attr("checked",true);
//		$("#goal"+index).attr("class","li-selected");
//	});

	$("input:radio[name='goal']").change(function (){
		var ckd = $("input:radio[name='goal']:checked").val();
		$("#submit_goal").attr("disabled",false);
		if(ckd == "1"){
			a_goal["goal"]= "0";
			$("#goal"+index-1).attr("class","li-selected");
			console.log(a_goal["goal"]);
			$("#goal"+index-1).siblings().removeClass("li-selected");
		}else if(ckd == "2"){
			a_account["autoplay"] = "1";
			$("#goal"+index-1).attr("class","li-selected");
			$("#goal"+index-1).siblings().removeClass("li-selected");
		}else if(ckd == "3"){
			a_account["autoplay"] = "2";
			$("#goal"+index-1).attr("class","li-selected");
			$("#goal"+index-1).siblings().removeClass("li-selected");
		}else if(ckd == "4"){
			a_account["autoplay"] = "3";
			$("#goal"+index-1).attr("class","li-selected");
			$("#goal"+index-1).siblings().removeClass("li-selected");
		}
	});
}
function imgChange(){
	$("#profile_pic").change(function(e){
		if(checkPhotoSize(this) == false){
			$("#submit_account").attr("disabled",true);
		}else{
			var file = e.target.files[0];
	        preview2(file);
	        $("#submit_account").attr("disabled",false);
		}
	});
}
function preview2(file) {
    var reader = new FileReader();
    reader.onload = function(e) {
        var $img = $('<img>').attr("src", e.target.result);
        $('#img-profile').css("background","#ffffff").empty().append($img);
    }
    reader.readAsDataURL(file)
}