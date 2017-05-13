$(document).ready(function() {
	 if (window.history && window.history.pushState) {
		$(window).on('popstate', function () {
			window.history.forward(1);
		});
	  }
//	 $("#btn-sendVC").attr("disabled",true);
   $("#submit-account").attr("disabled",true);
   $("#submit-profile").attr("disabled",true);
   $("#submit-notice").attr("disabled",true);
   $("#submit-goal").attr("disabled",true);
   console.log($("#submit-account").attr("disabled"));
});

$(function () {
	$("#user_name").change(function () {
		$("#submit_account").attr("disabled",false);
	});
	$("#user_name").change(function () {
		$("#submit_account").attr("disabled",false);
	});
}); 


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



