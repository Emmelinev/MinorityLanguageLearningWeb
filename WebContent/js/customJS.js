var phoneValid = false;
var pwdValid = false;
var confirmValid = false;
var checkcodeValid = false;
$(document).ready(function(){
     $("#btn-sendVC").attr("disabled",true);
    $("#btn-signup").attr("disabled",true);
});

function checkNull(obj){
    var modVal = $.trim($("#" + obj).val()).length;
    if(modVal > 0){
        return true;
    }else{
        return false;
    }
}

function checkReg(obj,pattern){
    var reg = new RegExp(pattern);
    var str = $("#"+obj).val();
    if(reg.test(str)){
        return true;
    }else{
        return false;
    }
}

function checkIdentical(obj1,obj2){
    if($("#"+obj1).val() == $("#"+obj2).val()){
        console.log("true");
        return true;
    }else {
        console.log("false");
        return false;
    }
}

function ajaxValid(queryParam,servlet){
	var result = false;
    $.ajax({
        async : false,
        cache : false,
        type : 'POST',
        url : servlet,
        data:queryParam,
        dataType:"json",
        error : function(data) {
        },
        success : function(data){
        	console.log(data.valid);
        	result = data.valid;
        }
    });
//    console.log("can return?");
    return result;
}
function checkRemote(queryParam,servlet){
//    var rst = ajaxValid(queryParam,servlet);
    if(ajaxValid(queryParam,servlet)){
    	console.log("reutrn true");
        return true;
    }else{
    	console.log("return false");
        return false;
    }
}

function appendTip(father,tipName,tipCss,tipInfo){
//	console.log(div);
    $("#"+father).append(tipName);
    $("#"+tipName).append(tipInfo);
    $("#"+tipName).css(tipCss);
}
function removeElem(obj){
//	console.log("remove");
//	console.log($("#"+obj).length);
    if ($("#"+obj).length && $("#"+obj).length>0){
    	$("#"+obj).empty();
        $("#"+obj).remove();
    }
}
function phonenumOninput(event){
    removeElem("phoneTip");
    var father = "phoneTd";
    //验证非空
    if(checkNull("phonenum")) {
    	removeElem("phoneTip");
    	phoneValid = true;
    }else{
    	removeElem("checkcodeTip");
    	phoneValid = false;
        var div = '<div class="tip tip-normal" id="phoneTip"></div>';
        var phonecss = {
            right: '-150px',
            top: '20px',
        };
        var info = "Necessary information!!";
//        appendTip(father,div,phonecss,info);
        $("#phoneTd").append(div);
//        $("#phoneTip").append(info);
        $("#phoneTip").html(info);
        $("#phoneTip").css(phonecss);
    }
    //验证是否是手机号
    if (checkReg("phonenum", "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$")) {
    	removeElem("phoneTip");
    	phoneValid = true;
    }else{
    	removeElem("checkcodeTip");
    	phoneValid = false;
        var div = '<div class="tip tip-closer" id="phoneTip"></div>';
        var phonecss = {
            right: '-90px',
            top: '20px',
        };
        var info = "Invalid value!";
//        appendTip(father,div,phonecss,info);
        $("#phoneTd").append(div);
//        $("#phoneTip").append(info);
        $("#phoneTip").html(info);
        $("#phoneTip").css(phonecss);
    }
//    验证用户名是否存在
//    var value = $("#phonenum").val();
//    var str = {"phonenum":value};
//    var query = jQuery.param(str);
//    var servlet = "../UserInfoServlet?method=1";
//    if(!ajaxValid(query,servlet)) {
//    	removeElem("phoneTip");
//    	phoneValid = true;
//    }else{
//    	removeElem("checkcodeTip");
//    	phoneValid = false;
//        var div = '<div class="tip tip-normal" id="phoneTip"></div>';
//        var phonecss = {
//            right: '-150px',
//            top: '20px',
//        };
//        var info = "Already existed phone!";
////        appendTip(father,div,phonecss,info);
//        $("#phoneTd").append(div);
////        $("#phoneTip").append(info);
//        $("#phoneTip").html(info);
//        $("#phoneTip").css(phonecss);
//    }
    enableButton(phoneValid,pwdValid,confirmValid,checkcodeValid);
    enableSendVC(phoneValid);
}
function passwordOninput(event){
    removeElem("pwdTip");

    var father = "pwdTd";
    //验证非空
    if(checkNull("pwd")){
    	removeElem("pwdTip");
    	removeElem("confirmTip");
    	pwdValid = true;
    }else {
    	removeElem("checkcodeTip");
    	pwdValid = false;
//        var div = '<div class="tip tip-normal" id="phoneTip"></div>';
        var div = '<div class="tip tip-normal" id="pwdTip"></div>';
        var pwdcss = {
            right: '-150px',
            top: '110px',
        };
        var info = "Necessary information!!";
//        appendTip(father,div,pwdcss,info);
        $("#pwdTd").append(div);
        $("#pwdTip").append(info);
        $("#pwdTip").css(pwdcss);
    }
    //验证和确认密码相同
    if(checkIdentical("pwd","repwd")){
    	removeElem("pwdTip");
    	removeElem("confirmTip");
    	pwdValid = true;
    	confirmValid = true;
    }else {
    	removeElem("checkcodeTip");
    	pwdValid = false;
    	confirmValid = false;
        var div = '<div class="tip tip-normal" id="pwdTip"></div>';
        var pwdcss = {
            right: '-150px',
            top: '110px',
        };
        var info = "Not identical with confirm password!";
//        appendTip(father,div,pwdcss,info);
        $("#pwdTd").append(div);
        $("#pwdTip").append(info);
        $("#pwdTip").css(pwdcss);
    }
    enableButton(phoneValid,pwdValid,confirmValid,checkcodeValid);
}
function confirmOninput(event){
    removeElem("confirmTip");
    // removeElem("confirmTip2");
    var father = "confirmTd";
    //验证非空
    var father = "checkcodeTd";
    
    if(checkNull("repwd")){
    	removeElem("confirmTip");
    	removeElem("pwdTip");
    	confirmValid = true;
    	pwdValid = true;
    }else {
    	pwdValid = false;
    	confirmValid = false;
        var div = '<div class="tip tip-normal" id="confirmTip"></div>';
        var confirmcss = {
            right: '-150px',
            top: '180px',
        };
        var info = "Necessary information!!";
//        appendTip(father,div,confirmcss,info);
        $("#confirmTd").append(div);
        $("#confirmTip").append(info);
        $("#confirmTip").css(confirmcss);
    }
    //验证是否和密码相同
    if(checkIdentical("repwd","pwd")){
    	removeElem("confirmTip");
    	removeElem("pwdTip");
    	confirmValid = true;
    }else {
    	removeElem("checkcodeTip");
    	confirmValid = false;
        var div = '<div class="tip tip-normal" id="confirmTip"></div>';
        var confirmcss = {
            right: '-150px',
            top: '180px',
        };
        var info = "Not identical with confirm password.";
//        appendTip(father,div,confirmcss,info);
        $("#confirmTd").append(div);
        $("#confirmTip").append(info);
        $("#confirmTip").css(confirmcss);
    }
    enableButton(phoneValid,pwdValid,confirmValid,checkcodeValid);
}
function checkcodeOninput(event){
    removeElem("checkcodeTip");
    var value = $("#verifyCode").val();
    var str = {"verifyCode":value};
    var query = jQuery.param(str);
    var servlet = "../UserInfoServlet?method=6";
    var reg = new RegExp("^\\d{6}$");
    var father = "checkcodeTd";
    
    //验证非空
    if(checkNull("verifyCode")){
    	removeElem("checkcodeTip");
    	checkcodeValid = true;
    }else {
    	removeElem("checkcodeTip");
    	checkcodeValid = false;
        var div = '<div class="tip tip-normal" id="checkcodeTip"></div>';
        var checkcodess = {
            right: '-150px',
            top: '250px',
        };
        var info = "Necessary information!!";
//        appendTip(father,div,checkcodess,info);
        $("#checkcodeTd").append(div);
        $("#checkcodeTip").append(info);
        $("#checkcodeTip").css(checkcodess);
    }
    //验证是否为6位数字
    if(checkReg("verifyCode","^\\d{6}$")){
    	removeElem("checkcodeTip");
    	checkcodeValid = true;
    }else {
    	removeElem("checkcodeTip");
    	checkcodeValid = false;
        var div = '<div class="tip tip-normal" id="checkcodeTip"></div>';
        var checkcodess = {
            right: '-150px',
            top: '250px',
        };
        var info = "Supposed to be six-digit number!";
//        appendTip(father,div,checkcodess,info);
        $("#checkcodeTd").append(div);
        $("#checkcodeTip").append(info);
        $("#checkcodeTip").css(checkcodess);
    }
    //验证是否正确
    
    if(ajaxValid(query,servlet)){
    	removeElem("checkcodeTip");
    	checkcodeValid = true;
    }else {
    	removeElem("checkcodeTip");
    	checkcodeValid = false;
        var div = '<div class="tip tip-closer" id="checkcodeTip"></div>';
        var checkcodess = {
            right: '-90px',
            top: '250px',
        };
        var info = "Incorrect input!";
//        appendTip(father,div,checkcodess,info);
        $("#checkcodeTd").append(div);
        $("#checkcodeTip").append(info);
        $("#checkcodeTip").css(checkcodess);
    }
    enableButton(phoneValid,pwdValid,confirmValid,checkcodeValid);
}

function enableButton(phoneValid,pwdValid,confirmValid,checkcodeValid){
	console.log("phone-"+phoneValid);
	console.log("pwd-"+pwdValid);
	console.log("confirm-"+confirmValid);
	console.log("check-"+checkcodeValid);
    if(phoneValid & pwdValid & confirmValid & checkcodeValid){
        $("#btn-signup").attr("disabled",false);
    }else{
        $("#btn-signup").attr("disabled",true);
    }
}
function enableSendVC(phoneValid){
	if(phoneValid){
		$("#btn-sendVC").attr("disabled",false);
	}
	else{
		$("#btn-sendVC").attr("disabled",true);
	}
}
