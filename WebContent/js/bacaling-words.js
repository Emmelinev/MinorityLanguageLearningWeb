var tableObject = null;//获取id为tableSort的table对象
var tbHead = null;//获取table对象下的thead
var tbHeadTh = null;//获取thead下的tr下的th
var tbBody = null;//获取table对象下的tbody
var tbBodyTr = null;//获取tbody下的tr
var sortIndex = -1; //初始化索引

$(document).ready(function() {
	 if (window.history && window.history.pushState) {
		$(window).on('popstate', function () {
			window.history.forward(1);
		});
	  }
    var data = getTable();
    var count_rows = 0;
    getLevels();
    getLevel();
    getMsgCount();
    $(".div-alert").click(function(){
    	$(".new-count").hide();
    	getMessage();
    	$(".message").slideToggle("fast");
    });
    var table_str = '<table id="words"><thead><tr><th type="text">Word</th><th type="text">Part of speech</th><th type="text">Last practiced</th><th type="number">Strength</th></tr></thead><tbody>';
    for(var key in data){
        var td_class = null;
        switch(data[key].strengthLevel)
        {
	        case 4: td_class = '"icon-strength-level icon-strength-level-4"'; break;
	        case 3: td_class = '"icon-strength-level icon-strength-level-3"'; break;
	        case 2: td_class = '"icon-strength-level icon-strength-level-2"'; break;
	        default: td_class = '"icon-strength-level icon-strength-level-1"';
        }

        table_str = table_str + '<tr class="rows"><td>' 
        			+ data[key].word + "</td><td>" 
        			+ data[key].wordClass + "</td><td>"
        			+ data[key].lastPracticed + ' days ago</td><td class="word-level"><span class=' 
        			+ td_class + '></span>' 
        			+ data[key].strengthLevel + '</td></tr>';
        count_rows++;
    }
    
    table_str = table_str+"</tbody></table>";
    $("#language_name").prepend("<span>" + getLanguageName() + "</span>");
    $("#word-list").append(table_str);
    $("#count-row").prepend("<span>" + count_rows + "</span>")
    tableObject = $('#words');
    tbHead = tableObject.children('thead');
    tbHeadTh = tbHead.find('tr th');
    tbBody = tableObject.children('tbody');
    tbBodyTr = tbBody.find('tr');
    sortIndex = -1;
    var status = {"0":0,"1":0,"2":0,"3":0};
    tbHeadTh.each(function() {
    	 var thisIndex = tbHeadTh.index($(this));
    	 console.log(thisIndex);
         $(this).click(function() {
        	 iconStatus(thisIndex,status);
             var dataType = $(this).attr("type"); 
             sortTable(thisIndex, dataType);
         });
    });
});
function iconStatus(index,status){
	switch(status[index]){
	case 0:
		$("tr").children().eq(index).attr("class","ascending");
		$("tr").children().eq(index).siblings().removeClass("ascending decending");
		status[index]=1; 
		break;
	case 1:
		$("tr").children().eq(index).attr("class","decending");
		$("tr").children().eq(index).siblings().removeClass("ascending decending");
		status[index]=0; 
		break;
	}
}
function getRow(){
	$("#Button1").click(function(){
		$("#words tr");
	})	
}

$(function () {
	$(".rows").click(function () {
		$(this).addClass('selected') 
		.siblings().removeClass('selected')
		.end();

		var getValue = $(this).children().eq(0).text();
		var param = {"word":getValue};
		var servlet = "../WordServlet?method=3";
		var json = ajaxFunc(param,servlet);
		var td_class = $(this).children().eq(3).find("span").attr('class');
		$(".right-unselected").slideUp();
	//	$(".right-unselected").slideToggle();
	//	$(".right-unselected").slideToggle();
	//	$(".right-selected").slideToggle();
	//	$(".right-selected").slideToggle();
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
function sortTable(index, type) {
    var trsValue = new Array();
    tbBodyTr.each(function() {
        var tds = $(this).find('td');
        trsValue.push(type + ".separator" + $(tds[index]).html() + ".separator" + $(this).html());
        $(this).html("");
    });
    var len = trsValue.length;
    if(index == sortIndex){
        trsValue.reverse();
    } else {
        for(var i = 0; i < len; i++){
            type = trsValue[i].split(".separator")[0];
            for(var j = i + 1; j < len; j++){
                value1 = trsValue[i].split(".separator")[1];
                value2 = trsValue[j].split(".separator")[1];
                if(type == "number"){
                    value1 = value1 == "" ? 0 : value1;
                    value2 = value2 == "" ? 0 : value2;
                    if(parseFloat(value1) > parseFloat(value2)){
                        var temp = trsValue[j];
                        trsValue[j] = trsValue[i];
                        trsValue[i] = temp;
                    }
                }else {
                    if (value1.localeCompare(value2) > 0) {
                        var temp = trsValue[j];
                        trsValue[j] = trsValue[i];
                        trsValue[i] = temp;
                    }
                }
            }
        }
    }
    for(var i = 0; i < len; i++){
        $("tbody tr:eq(" + i + ")").html(trsValue[i].split(".separator")[2]);
    }
    sortIndex = index;
}



