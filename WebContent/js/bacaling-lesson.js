$(document).ready(function() {
	 if (window.history && window.history.pushState) {
		$(window).on('popstate', function () {
			window.history.forward(1);
		});
	  }
	 getBarList();
	 getProgress();
});
function getBarList(){
	var lesson_id = getUrlParam("lessonId");
	var param = {"lesson":lesson_id};
	var servlet = "../LessonServlet?method=2";
	var json = ajaxFunc(param,servlet);

	for(var key in json){
		var str = '<div class="bar" id="bar-"' 
			+ json[key].barId + '><h3>' 
			+ json[key].barName + '<span class="status-'
			+ json[key].status +'"></span></h3><p>' 
			+ json[key].barMember + '</p><div class="btn-' 
			+ json[key].status + '"><a href="PracticePage.jsp?barId='
			+ json[key].barId +'&status='
			+ json[key].status +'">'
			+ setStatus(json[key].status) + '</a></div></div>';
		console.log(str);
		$(".do-lesson").append(str);
	}
}
function getProgress(){
	var lesson_id = getUrlParam("lessonId");
	var param = {"lesson":lesson_id};
	var servlet = "../LessonServlet?method=3";
	var json = ajaxFunc(param,servlet);
	var img_url = "../images/"+json.img+".png";
	$(".lesson-icon").css("background-image",'url('+img_url+')');
	$("#passed").html(json.passed);
	$("#num").html(json.number);
	$(".process").css("width",json.progress*100+'%');
	$("h1").html(json.name);
	$("#lesson_name_strength").html(json.name);
}
function setStatus(status){
	var txt = null;
	switch(status){
	case 0: txt="Start";break;
	case 1: txt="Redo";break;
	}
	return txt;
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


