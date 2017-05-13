$(document).ready(function() {
	 if (window.history && window.history.pushState) {
		$(window).on('popstate', function () {
			window.history.forward(1);
		});
	  }
    var data = getTable();
    var count_rows = 0;
    for(var key in json){
    	var progress = json[key].progress * 100 + '%';
    	var str = '<li class="lesson"><div class="logo"><a href="LessonPage.jsp?lesson=' 
    		+ json[key].lesson_id + '"><img src="' 
    		+ json[key].lesson_img + '"></a></div><div class="level-bar" id="' 
    		+ json[key].lesson_name + '"><div class="level"></div></div><h5>' 
    		+ json[key].lesson_name + '</h5></li>';
        $("#lesson_list").append(str);
        $("#"+json[key].lesson_name).attr("width",progress);
    }
    
    var table_str = '<table id="words"><tr><th>Word</th><th>Part of speech</th><th>Last practiced</th><th>Strength</th></tr>';
    for(var key in data){
        var td_class = null;
        switch(data[key].strengthLevel)
        {
	        case '4': td_class = '"icon-strength-level icon-strength-level-4"'; break;
	        case '3': td_class = '"icon-strength-level icon-strength-level-3"'; break;
	        case '2': td_class = '"icon-strength-level icon-strength-level-2"'; break;
	        default: td_class = '"icon-strength-level icon-strength-level-1"';
        }

        table_str = table_str + '<tr class="rows"><td>' 
        			+ data[key].word + "</td><td>" 
        			+ data[key].wordClass + "</td><td>"
        			+ data[key].lastPracticed + " days ago</td><td><span class=" 
        			+ td_class + "></span></td></tr>";
        count_rows++;
    }
    
    table_str = table_str+"</table>";
    $("#language_name").prepend("<span>" + getLanguageName() + "</span>");
    $("#word-list").append(table_str);
    $("#count-row").prepend("<span>" + count_rows + "</span>")
//    console.log("2-"+table_str);
});

function getRow(){
//	$("table").find("tr").eq(3).find("td").eq(1);
	$("#Button1").click(function(){
		$("#words tr");
	})
	
}

$(function () {
	$(".rows").click(function () {
		$(this).addClass('selected') 
		.siblings().removeClass('selected')
		.end();
	//	alert($(this).children().eq(0).text());
		var getValue = $(this).children().eq(0).text();
		var param = {"word":getValue};
		console.log(param);
		var servlet = "../WordServlet?method=3";
		var json = ajaxFunc(param,servlet);
		var td_class = $(this).children().eq(3).find("span").attr('class');
		console.log(td_class);
		console.log(json);
		$(".right-unselected").slideUp();
		$(".right-selected").slideDown();
		$(".right-selected .word").html(json.word);
		$("#word_class").html(json.word_class);
		$("#of_lesson").html(json.lesson);
		$("#word_translation").html(json.word_tanslation);
		$("#strength").attr('class',td_class);
		console.log($("#strength").attr('classs'));		
	});
	
	$(".media").click(function(){
		getAudioNormal($(".right-selected .word").html());
	});

}); 

function getAudioNormal(word){
//	var getValue = $(".right-selected .word").html();
//	var param = {"word":getValue};
//	var servlet = "../WordServlet?method=4";
//	var json = ajaxFunc(param,servlet);
//	console.log(json);
//	$(".div-word").append(json);
	$.speech({
        key: 'a5d6cea34d5c4aac84b4790307adeebe',
        src: word,
        hl: 'es-es',
        r: 5, 
        c: 'mp3',
        f: '44khz_16bit_stereo',
        ssml: false
    });
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


