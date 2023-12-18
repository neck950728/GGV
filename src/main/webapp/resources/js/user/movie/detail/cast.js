function actorList_append(actor_list, index){
	var en_name = "";
	
	if(actor_list[index].en_name != undefined){ en_name = "<span class='enName'>" + actor_list[index].en_name + "</span><br>"; }
	
	$(".actor > ul").append("<li>" +
										"<img src='" + actor_list[index].img + "'>" +
										"<div class='boxing'>" +
											"<h3 class='koName'>" + actor_list[index].ko_name + "</h3><br>" +
											en_name +
											"<span class='role'>" + actor_list[index].role_ + "</span>" +
										"</div>" +
									"</li>");
}

$(function(){
	var movie = jQuery.parseJSON($("#movie_json").val()); // 문자열을 JSON 객체로 변환
	
	// director
	$.each(movie.director_list, function(index, director){
		var en_name = "";
		if(director.en_name != undefined){ en_name = "<span class='enName'>" + director.en_name + "</span><br>"; }
		
		$(".director > ul").append("<li>" +
											   "<img src='" + director.img + "'>" +
											   "<div class='boxing'>" +
												   "<h3 class='koName'>" + director.ko_name + "</h3><br>" +
												   en_name +
											   "</div>" +
										   "</li>");
	});
	
	// actor
	var actor_list = movie.actor_list;
	for(var i = 0; i < 8; i++){
		if(actor_list[i] == undefined){
			break;
		}	
		actorList_append(actor_list, i);
	}
	
	if(actor_list.length > 8){
		$("#cast > .actor").append("<button class='expand_btn'>펼쳐보기</button>");
		
		$(".expand_btn").click(function(){
			for(var i = 8; i < actor_list.length; i++){
				actorList_append(actor_list, i);
			}
			$(this).css("display", "none");
		});
	}
});