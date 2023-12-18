function check(param){
	var trailer_list = param.trailer_list;
	var code = param.code;
	var grade = param.grade;
	var screeningInfo_list = param.screeningInfo_list;
	
	if(trailer_list != undefined){
		var main_trailer;
		$.each(trailer_list, function(index, value){
			if(value.title.indexOf("예고편") != -1){
				main_trailer = { "title":value.title, "video":value.video, "img":value.img };
				return false; // == break
			}
		});
		
		if(main_trailer == undefined){
			main_trailer = trailer_list[0];
		}
	}
	
	switch(grade){
		case "전체":
			grade = "-30px 0px";
			break;
		case "12세 이상":
			grade = "-51px 0px";
			break;
		case "15세 이상":
			grade = "-72px 0px";
			break;
		case "청소년 관람불가":
			grade = "-93px -0px";
			break;
		default:
			grade = "-114px 0px";
	}
	
	var reserveBtn = "";
	if(screeningInfo_list != undefined && screeningInfo_list.length > 0){
		reserveBtn = "<a class='reserveBtn' href='" + sessionStorage.getItem("contextPath") + "/movie/reserve?code=" + code + "'></a>";
	}
	
	return { "main_trailer":main_trailer, "grade":grade, "reserveBtn":reserveBtn };
}


function hd_trailer_revalidate(param){
	$("#hd_trailer > .trailer_list > ul").empty();
	
	$.each(param, function(index, movie){
		var result = check({ "code":movie.code, "grade":movie.grade, "screeningInfo_list":movie.screeningInfo_list });
		movie.grade = result.grade;
		
		$.each(movie.trailer_list, function(index, trailer){			
			var title = trailer.title;
			if(title.length > 21){ title = title.substring(0, 21) + "..."; }
			
			$("#hd_trailer > .trailer_list > ul").append("<li>" +
																		"<img class='trailer_img' src='" + trailer.img + "'>" +
																		"<span class='play_btn'></span>" +
																		"<span class='trailer_title'>" + title + "</span>" +
																	"</li>");
			
			$("#hd_trailer > .trailer_list > ul > li").last().click(function(){
				createLayerPlayer({ "movie":movie, "trailer":trailer, "reserveBtn":result.reserveBtn });
			});
		});
	});
}


function page_nav_revalidate(paging){
	$("#page_nav").empty();
	
	if(paging.startPage > paging.page_NAV_COUNT){
		$("#page_nav").append("<span class='head' page='1'>처음으로</span> " +
										 "<span class='back' page='" + (paging.startPage - 1) + "'>이전 10개</span> ");
	}
	
	for(var i = paging.startPage; i <= paging.endPage; i++){
		if(i == paging.currentPage){
			$("#page_nav").append("<span class='curPage' page='" + i + "'>" + i + "</span> ");
		}else{
			$("#page_nav").append("<span page='" + i + "'>" + i + "</span> ");
		}
	}
	
	if(paging.endPage < paging.totalPage){
		$("#page_nav").append("<span class='next' page='" + (paging.endPage + 1) + "'>다음 10개</span> " +
										 "<span class='tail' page='" + paging.totalPage + "'>끝으로</span>");
	}
}


function createLayerPlayer(param){
	var movie = param.movie;
	var trailer = param.trailer;
	var reserveBtn = param.reserveBtn;
	
	$("body").append("<div class='layer_player_bg'></div>" +
							"<div class='layer_player_wrap'>" +
								"<div class='layer_player'>" +
									"<h3>" +
										"<span class='hd_icon'>HD</span> <span class='title'>" + movie.title + "</span>" +
										"<button class='close_btn'></button>" +
									"</h3>" +
									"<div class='layer_player_left'>" +
										"<video id='' width='100%' height='100%' controls='controls' autoplay='autoplay'>" +
											"<source src=\"" + trailer.video + "\">" +
										"</video>" +
									"</div>" +
									"<div class='layer_player_right'>" +
										"<div class='movie_info'>" +
											"<a href='" + sessionStorage.getItem("contextPath") + "/movie/detail?mID=" + movie.code + "'><img src='" + movie.poster + "'></a>" +
											"<div>" +
												"<span class='movie_grade' style='background-position:" + movie.grade + ";'></span> " +
												"<a href='" + sessionStorage.getItem("contextPath") + "/movie/detail?mID=" + movie.code + "' class='movie_title'>" + movie.title + "</a><br>" +
												"<span class='movie_genre' style='display:inline-block;'>" + movie.genre + "</span><br>" +
												"<span class='movie_premier'>" + movie.premier + "</span><br>" +
												reserveBtn +
											"</div>" +
										"</div>" +
									"</div>" +
									"<div class='movie_description'>" +
										"<span>영상 설명</span><br>" +
										"<textarea readonly=''>" + trailer.title + "</textarea>" +
									"</div>" +
								"</div>" +
							"</div>");
	
	// 제목 또는 장르가 너무 길어서 두 줄을 차지하게 되면 지저분해 보이므로 적당한 간격을 두어 최대한 깔끔하게 보이도록 한다.
	if(movie.title.length > 9 || movie.genre.length > 15){ $(".layer_player .movie_genre").css("margin-top", "7px"); }
	
	// 닫기 버튼 이벤트 처리
	$(".layer_player .close_btn").click(function(){
		$(".layer_player_bg").remove();
		$(".layer_player_wrap").remove();
	});
}