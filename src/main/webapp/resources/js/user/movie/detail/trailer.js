$(function(){
	var movie = jQuery.parseJSON($("#movie_json").val());
	var result = check({ "code":movie.code, "grade":movie.grade, "screeningInfo_list":movie.screeningInfo_list });
	
	$.each(movie.trailer_list, function(index, trailer){
		var title = trailer.title;
		if(title.length > 24){ title = title.substring(0, 24) + "..."; }
		
		$("#trailer > .list > ul").append("<li>" +
													  "<img class='trailer_img' src='" + trailer.img + "'>" +
													  "<span class='play_btn'></span>" +
													  "<span class='trailer_title'>" + title + "</span>" +
												  "</li>");
		
		$("#trailer > .list > ul > li").last().click(function(){
			movie.grade = result.grade;
			
			createLayerPlayer({ "movie":movie, "trailer":trailer, "reserveBtn":result.reserveBtn });
		});
	});
});