$(document).ready(function() {
	 if (window.history && window.history.pushState) {
		$(window).on('popstate', function () {
			window.history.forward(1);
		});
	  }

    getLessons();
});

function getLessons(){
	var param = {"user_id":"1"};
	var servlet = "../ExerciseServlet?method=5";
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