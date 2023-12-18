// recommendMovieList 콘텐츠 세팅
$(function(){
	$(".recommendMovieList > li").each(function(index){
		if(!$(this).hasClass("banner")){
			var recommendMovie = g_recommendMovieList[index];
			var result = check({ "title":recommendMovie.title, "grade":recommendMovie.grade, "screeningInfo_list":recommendMovie.screeningInfo_list, "code":recommendMovie.code });
			
			$(this).find(".poster > a").attr("href", sessionStorage.getItem("contextPath") + "/movie/detail?mID=" + recommendMovie.code);
			$(this).find(".poster > a > img").attr("src", recommendMovie.poster);
			$(this).find(".movie_info .grade").css("background-position", result.grade);
			$(this).find(".movie_info .title").attr("href", sessionStorage.getItem("contextPath") + "/movie/detail?mID=" + recommendMovie.code);
			$(this).find(".movie_info .title").html(result.title);
			$(this).find(".movie_info .reserveRate").html(recommendMovie.reserveRate + "%");
			$(this).find(".movie_info .premier").html(recommendMovie.premier + " 개봉");
			$(this).find(".movie_info").append(result.reserveBtn);
			$(this).find(".like > button").attr("code", recommendMovie.code);
			$(this).find(".like > .count > span").html(recommendMovie.like_);
			
			if(result.isContainsInWishList){
				$(this).find(".like > button").addClass("on");
			}
		}
	});
});

// preMovieChart 콘텐츠 세팅
$(function(){
	$.each(g_preMovieChartOrder, function(index, value){
		var premier = value.replace(/\./gi, ""); // 마침표 제거(https://blog.naver.com/dngu_icdi/221453472122 참고)
		$("#preMovieChart").append("<ul class='" + premier + "'><h1>" + value + "</h1></ul>");
		
		var movieList = g_preMovieChart[value]; // preMovieChart.value가 아닌 이유 : https://blog.naver.com/dngu_icdi/221146006489 마지막 내용 참고
		$.each(movieList, function(index, value){
			var movie = value;
			var result = check({ "title":movie.title, "grade":movie.grade, "screeningInfo_list":movie.screeningInfo_list, "code":movie.code });
			var li = "<li>";
			var like_button = "<button code='" + movie.code + "'>";
			
			if(index % 4 == 3){ li = "<li class='endOfLine'>"; }
			if(result.isContainsInWishList){ like_button = "<button class='on' code='" + movie.code + "'>"; }
			
			$("#preMovieChart > ." + premier).append(li +
																		 "<div class='poster'>" +
																		 	 "<a href='" + sessionStorage.getItem("contextPath") + "/movie/detail?mID=" + movie.code + "'><img src='" + movie.poster + "'></a>" +
																		 "</div>" +
																			 "<div class='movie_info'>" + 
																				 "<div style='margin-top:5px; margin-bottom:5px;'>" +
																					 "<span class='grade' style='background-position:" + result.grade + ";'>등급</span> " +
																					 "<a href='" + sessionStorage.getItem("contextPath") + "/movie/detail/mID=" + movie.code + "' class='title'>" + result.title + "</a>" +
																			 "</div>" +
																			 "<div style='display:inline-block;'>" +
																				 "<span class='reserveRate_label'>예매율</span> <span class='reserveRate'>" + movie.reserveRate + "%</span><br>" +
																				 "<span class='premier'>" + movie.premier + " 개봉</span>" +
																			 "</div>" +
																			 result.reserveBtn +
																		 "</div>" +
																		 "<div class='like'>" +
																			 like_button + "</button> " +
																			 "<span class='count'>" +
																				 "<span>" + movie.like_ + "</span>" +
																				 "<i class='corner-LT'></i>" +
																				 "<i class='corner-RT'></i>" +
																				 "<i class='corner-LB'></i>" +
																				 "<i class='corner-RB'></i>" +
																				 "<i class='corner-arrow'></i>" +
																			 "</span>" +
																		 "</div>" +
																	 "</li>");
		});
	});
});