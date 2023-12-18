var g_stillCut_list;
var g_startIndex;
var g_endIndex;

function revalidate(start, end){
	$("#stillCut > .list > ul").empty();
	for(var i = start; i <= end; i++){
		if(g_stillCut_list[i] == undefined){
			break;
		}
		$("#stillCut > .list > ul").append("<li><img src='" + g_stillCut_list[i].stillCut + "'></li>");
	}
	$("#stillCut > .list > ul > li").first().attr("class", "on");
	$("#stillCut > .viewer > img").attr("src", $("#stillCut > .list > ul > li[class='on']").find("img").attr("src"));
}


$(function(){
	var movie = jQuery.parseJSON($("#movie_json").val());
	g_stillCut_list = movie.stillCut_list;
	
	g_startIndex = 0;
	g_endIndex = 7;
	revalidate(g_startIndex, g_endIndex);
});

$(function(){
	$("#stillCut > .list > .previous").click(function(){
		if(g_startIndex - 8 >= 0){
			g_startIndex = g_startIndex - 8;
			g_endIndex = g_startIndex + 7;
			
			revalidate(g_startIndex, g_endIndex);
		}
	});
	
	$("#stillCut > .list > .next").click(function(){
		if(g_endIndex + 1 < g_stillCut_list.length){
			g_startIndex = g_endIndex + 1;
			g_endIndex = g_startIndex + 7;
			
			revalidate(g_startIndex, g_endIndex);
		}
	});
});

$(function(){
	$("#stillCut > .list > ul").on("click", "li", function(){
		$("#stillCut > .list > ul > li[class='on']").removeClass("on");
		$(this).attr("class", "on");
		
		$("#stillCut > .viewer > img").attr("src", $("#stillCut > .list > ul > li[class='on']").find("img").attr("src"));
	});
});