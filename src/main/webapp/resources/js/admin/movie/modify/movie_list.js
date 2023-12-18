$(function(){
	$(".search > input").keydown(function(key){
		if(key.keyCode == 13){ // 13 : Enter
			$(".search > button").trigger("click");
		}
	});
	
	$(".search > button").click(function(){
		var keyword = $(".search > input").val();
		$.get(sessionStorage.getItem("contextPath") + "/admin/movie/modify/movie_list/search", { "keyword":keyword }, function(result){
			$(".movie_list").empty();
			$("#page_nav").empty();
			
			$.each(result, function(index, movie){
				var director = "";
				$.each(movie.director_list, function(index, director_){
					director += director_.ko_name + ", ";
				});
				
				if(director.length == 0){
					director = "(알 수 없음)";
				}else{
					director = director.substring(0, director.lastIndexOf(","));
				}
				
				
				var grade = "미정";
				if(movie.grade != null){ grade = movie.grade; }
				
				
				$(".movie_list").append("<li class='movie'>" +
													"<ul class='movie_info'>" +
														"<li class='poster'>" +
															"<a href='" + sessionStorage.getItem("contextPath") + "/admin/movie/modify?code=" + movie.code + "'><img src='" + movie.poster + "'></a>" +
														"</li>" +
														"<li class='title'>" +
															"<span><a href='" + sessionStorage.getItem("contextPath") + "/admin/movie/modify?code=" + movie.code + "'>" + movie.title + "</a></span>" +
														"</li>" +
														"<li class='director'><span>" + director + "</span></li>" +
														"<li class='genre'><span>" + movie.genre + "</span></li>" +
														"<li class='grade'><span>" + grade + "</span></li>" +
														"<li class='premier'><span>" + movie.premier + "</span></li>" +
														"<li class='delete_btn'>" +
															"<span>" +
																"<a href='" + sessionStorage.getItem("contextPath") + "/admin/movie/modify/movie_list/delete?code=" + movie.code + "'>" +
																"<img src='" + sessionStorage.getItem("contextPath") + "/resources/images/admin/movie/modify/movie_list/delete_icon.png'></a>" +
															"</span>" +
														"</li>" +
													"</ul>" +
												"</li>");
			});
			
			set_containerHeight();
		});
	});
});

$(function(){
	$(".movie_list").on("click", ".delete_btn img", function(){
		var title = $(this).parents(".movie_info").find(".title a").html();
		if(!confirm("제목 : " + title + "\n영화를 제거하시겠습니까?")){
			return false;
		}
	});
});