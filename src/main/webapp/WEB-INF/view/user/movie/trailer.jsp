<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<script src="${pageContext.request.contextPath}/resources/js/user/movie/trailer.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/movie/trailer.css">
	<script type="text/javascript">
		// ● topHonor_trailer & trailer_recommend ●
		$(function(){
			var top5MovieList = ${top5MovieList_json};
			
			// ------------------------------------------ topHonor_trailer ------------------------------------------
			var topHonorMovie = top5MovieList[0];
			var result = check({ "trailer_list":topHonorMovie.trailer_list ,"grade":topHonorMovie.grade });
			var main_trailer = result.main_trailer;
			
			$("#topHonor_trailer video").prop("muted", true);
			$("#topHonor_trailer video > source").attr("src", main_trailer.video);
			document.getElementById("video").load();
			
			$("#topHonor_trailer > h3 > .title").html(topHonorMovie.title);
			$("#topHonor_trailer .movie_info > a").attr("href", "${pageContext.request.contextPath}/movie/detail?mID=" + topHonorMovie.code);
			$("#topHonor_trailer .movie_info > a > img").attr("src", topHonorMovie.poster);
			$("#topHonor_trailer .movie_info .movie_grade").css("background-position", result.grade);
			$("#topHonor_trailer .movie_info .movie_title").attr("href", "${pageContext.request.contextPath}/movie/detail?mID" + topHonorMovie.code);
			
			$("#topHonor_trailer .movie_info .movie_title").html(topHonorMovie.title);
			var movie_title_height = parseFloat($("#topHonor_trailer .movie_info .movie_title").css("height"));
			if(movie_title_height > 17){ $("#topHonor_trailer .movie_info > div").css("margin-top", "7px"); }
			
			$("#topHonor_trailer .movie_info .movie_genre").html(topHonorMovie.genre);
			$("#topHonor_trailer .movie_info .movie_premier").html(topHonorMovie.premier);
			$("#topHonor_trailer .movie_description > textarea").html(main_trailer.title);
			
			if(topHonorMovie.screeningInfo_list.length == 0){
				$("#topHonor_trailer .reserveBtn").remove();
			}
			// -----------------------------------------------------------------------------------------------------
			
			// ------------------------------------------------ trailer_recommend ------------------------------------------------
			$("#trailer_recommend > ul > li").each(function(index){
				var movie = top5MovieList[index + 1];
				var result = check({ "trailer_list":movie.trailer_list, "grade":movie.grade, "screeningInfo_list":movie.screeningInfo_list });
				var main_trailer = result.main_trailer;
				movie.grade = result.grade;
				
				var trailer_title = main_trailer.title;
				if(trailer_title.length > 21){ trailer_title = trailer_title.substring(0, 21) + "..."; }
				
				$(this).find("img").attr("src", main_trailer.img);
				$(this).find(".trailer_title").html(trailer_title);
				
				$(this).click(function(){
					createLayerPlayer({ "movie":movie, "trailer":main_trailer, "reserveBtn":result.reserveBtn });
				});
			});
			// -------------------------------------------------------------------------------------------------------------------
		});
		
		// ● viewAll_btn ●
		$(function(){
			$(".viewAll_btn").click(function(){
				$("#page_nav").removeAttr("searchKeyword");
				$.get("${pageContext.request.contextPath}/movie/trailer/pageMove", { "page":1 }, function(data){
					hd_trailer_revalidate(data.trailerList);
					page_nav_revalidate(data.paging);
				});
			});
		});
		
		// ● search_trailer ●
		$(function(){
			$(".search_trailer > input").keydown(function(key){
				if(key.keyCode == 13){ // 13 : Enter
					$(".search_trailer > button").trigger("click");
				}
			});
			
			$(".search_trailer > button").click(function(){
				var keyword = $(this).siblings("input").val();	
				$.get("${pageContext.request.contextPath}/movie/trailer/searchTrailer", { "keyword":keyword }, function(data){
					if(data.trailerList.length > 0){
						hd_trailer_revalidate(data.trailerList);
						page_nav_revalidate(data.paging);
						
						$("#page_nav").attr("searchKeyword", keyword);
					}else{
						$("#hd_trailer > .trailer_list > ul").empty();
						$("#hd_trailer > .trailer_list > ul").append("<span class='noSearchedResult'>검색 결과가 존재하지 않습니다.</span>");
						$("#page_nav").removeAttr("searchKeyword");
						$("#page_nav").empty();
					}
					
					$(this).siblings("input").val("");
				});
			});
		});
		
		// ● hd_trailer ●
		$(function(){
			var trailerList = ${trailerList_json};
			hd_trailer_revalidate(trailerList);
		});
		
		// ● page_nav ●
		$(function(){
			$("#page_nav").on("click", "span", function(){
				var page = $(this).attr("page");
				
				if($("#page_nav").is("[searchKeyword]")){
					var keyword = $("#page_nav").attr("searchKeyword");
					$.get("${pageContext.request.contextPath}/movie/trailer/pageMove", { "page":page, "searchKeyword":keyword }, function(data){
						hd_trailer_revalidate(data.trailerList);
						page_nav_revalidate(data.paging);
					});
				}else{
					$.get("${pageContext.request.contextPath}/movie/trailer/pageMove", { "page":page }, function(data){
						hd_trailer_revalidate(data.trailerList);
						page_nav_revalidate(data.paging);
					});
				}
			});
		});
	</script>
</head>
<body>
	<div id="container">
		<div id="center_align_wrap">
			<div id="topHonor_trailer">
				<h3>
					<span class="hd_icon">HD</span> <span class="title"></span>
				</h3>
				<div class="topHonor_trailer_left">
					<video id="video" width="100%" height="100%" controls="controls" autoplay="autoplay">
						<source>
					</video>
				</div>
				<div class="topHonor_trailer_right">
					<div class="movie_info">
						<a><img></a>
						<div>
							<span class="movie_grade"></span>
							<a class="movie_title"></a><br>
							<span class="movie_genre"></span><br>
							<span class="movie_premier"></span><br>
							<a class="reserveBtn" href="#"></a>
						</div>
					</div>
					<div class="movie_description">
						<span>영상 설명</span>
						<textarea readonly="readonly"></textarea>
					</div>
				</div>
			</div>
			<div id="trailer_recommend">
				<h3><img src="${pageContext.request.contextPath}/resources/images/user/movie/trailer/trailer_recommend.gif"></h3>
				<ul>
					<li>
						<img class="trailer_img">
						<span class="play_btn"></span>
						<span class="trailer_title"></span>
					</li>
					<li>
						<img class="trailer_img">
						<span class="play_btn"></span>
						<span class="trailer_title"></span>
					</li>
					<li>
						<img class="trailer_img">
						<span class="play_btn"></span>
						<span class="trailer_title"></span>
					</li>
					<li>
						<img class="trailer_img">
						<span class="play_btn"></span>
						<span class="trailer_title"></span>
					</li>
				</ul>
			</div>
			<div id="banner">
				<img src="${pageContext.request.contextPath}/resources/images/user/movie/trailer/banner.jpg">
			</div>
			<div id="hd_trailer">
				<h3><img src="${pageContext.request.contextPath}/resources/images/user/movie/trailer/hd_trailer.gif"></h3>
				<button class="viewAll_btn">전체 보기</button>
				<div class="search_trailer">
					<input type="text" placeholder="트레일러 검색"><button>검색</button>
				</div>
				<div class="trailer_list">
					<ul></ul>
				</div>
			</div>
			<div id="page_nav">
				<c:if test="${paging.startPage > paging.PAGE_NAV_COUNT}">
					<span class="head" page="1">처음으로</span>
					<span class="back" page="${paging.startPage - 1}">이전 10개</span>
				</c:if>
				
				<c:forEach var="i" begin="${paging.startPage}" end="${paging.endPage}" step="1">
					<c:if test="${i == paging.currentPage}">
						<span class="curPage" page="${i}">${i}</span>
					</c:if>
					<c:if test="${i != paging.currentPage}">
						<span page="${i}">${i}</span>
					</c:if>
				</c:forEach>
				
				<c:if test="${paging.endPage < paging.totalPage}">
					<span class="next" page="${paging.endPage + 1}">다음 10개</span>
					<span class="tail" page="${paging.totalPage}">끝으로</span>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>