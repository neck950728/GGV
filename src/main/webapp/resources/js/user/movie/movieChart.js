function check(param){
	var title = param.title;
	var grade = param.grade;
	var screeningInfo_list = param.screeningInfo_list;
	var code = param.code;
	
	
	if(title.length > 11){ title = title.substring(0, 11) + "..."; }
	
	switch(grade){
		case "전체":
			grade = "-30px 0px";
			break;
		case "12세 이상":
			grade = "-30px -30px";
			break;
		case "15세 이상":
			grade = "-30px -60px";
			break;
		case "청소년 관람불가":
			grade = "-30px -90px";
			break;
		default:
			grade = "-30px -180px";
	}
	
	var reserveBtn = "";
	if(screeningInfo_list.length > 0){
		reserveBtn = "<a class='reserveBtn' href='" + sessionStorage.getItem("contextPath") + "/movie/reserve?code=" + code + "'>" +
							  "<img src='" + sessionStorage.getItem("contextPath") + "/resources/images/user/movie/movieChart/reserveBtn.png'>" +
						  "</a>";
	}
	
	var isContainsInWishList = false;
	if(g_wishList.indexOf(code) != -1){
		isContainsInWishList = true;
	}
	
	return { "title":title, "grade":grade, "reserveBtn":reserveBtn, "isContainsInWishList":isContainsInWishList };
}


var g_index;
var g_wishList = "";
$(function(){
	if(g_memberInfo != null){ // g_memberInfo 선언 위치 : /resources/js/user/module/header.js
		$.each(g_memberInfo.wishList, function(index, value){
			g_wishList += " " + value.movie_code;
		});
		g_wishList.trim();
	}
});

// line1, line2 콘텐츠 세팅
$(function(){
	var cur_line_startIndex = 0;
	
	$("#movieChart > ul").each(function(){
		$(this).find("li").each(function(){
			if(!$(this).hasClass("banner")){
				g_index = cur_line_startIndex + $(this).index();
				var movie = g_movieChart[g_index];
				var result = check({ "title":movie.title, "grade":movie.grade, "screeningInfo_list":movie.screeningInfo_list, "code":movie.code });
				
				$(this).find(".poster > a").attr("href", sessionStorage.getItem("contextPath") + "/movie/detail?mID=" + movie.code);
				$(this).find(".poster > a > img").attr("src", movie.poster);
				$(this).find(".movie_info .grade").css("background-position", result.grade);
				$(this).find(".movie_info .title").attr("href", sessionStorage.getItem("contextPath") + "/movie/detail?mID=" + movie.code);
				$(this).find(".movie_info .title").html(result.title);
				$(this).find(".movie_info .reserveRate").html(movie.reserveRate + "%");
				$(this).find(".movie_info .premier").html(movie.premier + " 개봉");
				$(this).find(".movie_info").append(result.reserveBtn);
				$(this).find(".like > button").attr("code", movie.code);
				$(this).find(".like > .count > span").html(movie.like_);
				
				if(result.isContainsInWishList){
					$(this).find(".like > button").addClass("on");
				}
			}
		});
		cur_line_startIndex = 3;
	});
});

// viewMore_btn 이벤트 처리
$(function(){
	$("#viewMore_btn").click(function(){
		var count = 0;
		var last_line_num = parseInt($("#movieChart > ul").last().attr("class").substr(4)); // ex) line2  →  2
		var line_num = last_line_num + 1;
		
		for(var i = g_index + 1; i < g_movieChart.length; i++){
			var movie = g_movieChart[i];
			var result = check({ "title":movie.title, "grade":movie.grade, "screeningInfo_list":movie.screeningInfo_list, "code":movie.code });
			var li = "<li>";
			var like_button = "<button code='" + movie.code + "'>";
			
			if(count % 4 == 0){
				$("#movieChart").append("<ul class='line" + line_num + "'></ul>");
			}else if(count % 4 == 3){
				li = "<li class='endOfLine'>";
			}
			
			if(result.isContainsInWishList){
				like_button = "<button class='on' code='" + movie.code + "'>";
			}
			
			$(".line" + line_num).append(li +
													  "<div class='poster'>" +
														  "<a href='" + sessionStorage.getItem("contextPath") + "/movie/detail?mID=" + movie.code + "'><img src='" + movie.poster + "'></a>" +
													  "</div>" +
													  "<div class='movie_info'>" + 
														  "<div style='margin-top:5px; margin-bottom:5px;'>" +
															  "<span class='grade' style='background-position:" + result.grade + ";'>등급</span> " +
															  "<a href='" + sessionStorage.getItem("contextPath") + "/movie/detail?mID=" + movie.code + "' class='title'>" + result.title + "</a>" +
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
			
			if(count % 4 == 3){ line_num++; }
			count++;
		}
		
		$(this).css("display", "none");
	});
});