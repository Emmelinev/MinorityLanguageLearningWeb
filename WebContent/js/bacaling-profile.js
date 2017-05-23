
$(document).ready(function() {
	 if (window.history && window.history.pushState) {
		$(window).on('popstate', function () {
			window.history.forward(1);
		});
	  }

	getLevels();
    getLevel();
    consLevelTable();
    $(".div-alert").click(function(){
    	$(".new-count").hide();
    	getMessage();
    	$(".message").slideToggle("fast");
    });
});
function consLevelTable(){
	var param = {"user_id":"1"};
	var servlet = "../UserInfoServlet?method=8";
	var json = ajaxFunc(param,servlet);
	for(var key in json){
		var language = setLanguage(json[key].currentLanguage,1);
		var country = setCountry(json[key].currentLanguage);

		var str = '<tr><td><span class="lang-icon '
			+ country + '"></span></td><td><h5><span>'
			+ language + '</span> - <span>Level '
			+ json[key].level + '</span></h5><h6>Next Level: <span>2</span> XP</h6><h6>Total XP: <span>'
			+ json[key].exp + '</span> XP</h6></td></tr>';
		$("#lang-table").append(str);
	}
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



