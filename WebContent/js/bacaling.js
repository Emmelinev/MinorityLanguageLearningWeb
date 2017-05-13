function  popDiv(showId, hideId,type) {
    if(type == 1){
        $("#" + hideId).hide();
        $("#" + showId).fadeIn("fast");
        $("#shade").fadeIn("fast");
        $("#phonenum").focus();
    }else if(type == 2){
        $("." + showId).show();
        $("." + hideId).hide();
    }
}

function closeDiv(closeId, showId,type) {
    // $("#frm-sign-up").hide();
    if(type == 1){
        $("#" + closeId).fadeOut("fast");
        $("#shade").fadeOut("fast");
        $("#" + showId).show();
    }else if(type == 2){
        $("." + closeId).hide();
        $("." + showId).show();
    }
}
function getLanguageName(){
	var language_name =  null;
//	var vv= $.session.get("current_language");
	var language_id = $("#language_hide").html();
//	console.log(vv);
	switch(language_id)
	{
		case('1'): language_name = 'English'; break;
		case('2'): language_name = 'Chinese'; break;
		case('3'): language_name = 'Spanish'; break;
		case('4'): language_name = 'Japanese'; break;
		default: language_name ='Exception';
	}
//	console.log(language_name);
	return language_name;
}