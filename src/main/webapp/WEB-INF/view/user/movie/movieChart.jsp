<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<script src="${pageContext.request.contextPath}/resources/js/user/movie/public.js"></script>
	
	<script type="text/javascript">
		var g_movieChart = ${movieChart_json};
	</script>
	<script src="${pageContext.request.contextPath}/resources/js/user/movie/movieChart.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/movie/movieChart.css">
</head>
<body>
	<div id="container">
		<div id="title">
			<span>무비차트</span>
			<div>
				<a href="${pageContext.request.contextPath}/movie/movieChart" style="color:red;">상영작</a> &nbsp; &nbsp;
				<a href="${pageContext.request.contextPath}/movie/preMovieChart" style="color:#757373;">상영 예정작</a>
			</div>
		</div>
		
		<div id="movieChart">
			<ul class="line1">
				<li>
					<div class="rank">
						<span>No.1</span>
					</div>
					<div class="poster">
						<a><img></a>
					</div>
					<div class="movie_info">
						<div style="margin-top:5px; margin-bottom:5px;">
							<span class="grade">등급</span>
							<a class="title"></a>
						</div>
						<div style="display:inline-block;">
							<span class="reserveRate_label">예매율</span> <span class="reserveRate"></span><br>
							<span class="premier"></span>
						</div>
					</div>
					<div class="like">
						<button></button>
						<span class="count">
							<span></span>
							<i class="corner-LT"></i>
							<i class="corner-RT"></i>
							<i class="corner-LB"></i>
							<i class="corner-RB"></i>
							<i class="corner-arrow"></i>
						</span>
					</div>
				</li>
				<li>
					<div class="rank">
						<span>No.2</span>
					</div>
					<div class="poster">
						<a><img></a>
					</div>
					<div class="movie_info">
						<div style="margin-top:5px; margin-bottom:5px;">
							<span class="grade">등급</span>
							<a class="title"></a>
						</div>
						<div style="display:inline-block;">
							<span class="reserveRate_label">예매율</span> <span class="reserveRate"></span><br>
							<span class="premier"></span>
						</div>
					</div>
					<div class="like">
						<button></button>
						<span class="count">
							<span></span>
							<i class="corner-LT"></i>
							<i class="corner-RT"></i>
							<i class="corner-LB"></i>
							<i class="corner-RB"></i>
							<i class="corner-arrow"></i>
						</span>
					</div>
				</li>
				<li>
					<div class="rank">
						<span>No.3</span>
					</div>
					<div class="poster">
						<a><img></a>
					</div>
					<div class="movie_info">
						<div style="margin-top:5px; margin-bottom:5px;">
							<span class="grade">등급</span>
							<a class="title"></a>
						</div>
						<div style="display:inline-block;">
							<span class="reserveRate_label">예매율</span> <span class="reserveRate"></span><br>
							<span class="premier"></span>
						</div>
					</div>
					<div class="like">
						<button></button>
						<span class="count">
							<span></span>
							<i class="corner-LT"></i>
							<i class="corner-RT"></i>
							<i class="corner-LB"></i>
							<i class="corner-RB"></i>
							<i class="corner-arrow"></i>
						</span>
					</div>
				</li>
				<li class="banner endOfLine">
					<div>
						<div class="banner_img"><img src="${pageContext.request.contextPath}/resources/images/user/movie/movieChart/banner.jpg" width="150px"></div>
						<div class="banner_txt">
							<img src="${pageContext.request.contextPath}/resources/images/user/movie/movieChart/ad_icon.png"> <span>1만원 캐시백</span><br>
							<span style="display:block; margin-top:7px; padding:5px; border-top:3px solid white; border-bottom:3px solid white;">
								GGV 50% 할인받자!
							</span>
						</div>
					</div>
				</li>
			</ul>
			
			<ul class="line2">
				<li>
					<div class="poster">
						<a><img></a>
					</div>
					<div class="movie_info">
						<div style="margin-top:5px; margin-bottom:5px;">
							<span class="grade">등급</span>
							<a class="title"></a>
						</div>
						<div style="display:inline-block;">
							<span class="reserveRate_label">예매율</span> <span class="reserveRate"></span><br>
							<span class="premier"></span>
						</div>
					</div>
					<div class="like">
						<button></button>
						<span class="count">
							<span></span>
							<i class="corner-LT"></i>
							<i class="corner-RT"></i>
							<i class="corner-LB"></i>
							<i class="corner-RB"></i>
							<i class="corner-arrow"></i>
						</span>
					</div>
				</li>
				<li>
					<div class="poster">
						<a><img></a>
					</div>
					<div class="movie_info">
						<div style="margin-top:5px; margin-bottom:5px;">
							<span class="grade">등급</span>
							<a class="title"></a>
						</div>
						<div style="display:inline-block;">
							<span class="reserveRate_label">예매율</span> <span class="reserveRate"></span><br>
							<span class="premier"></span>
						</div>
					</div>
					<div class="like">
						<button></button>
						<span class="count">
							<span></span>
							<i class="corner-LT"></i>
							<i class="corner-RT"></i>
							<i class="corner-LB"></i>
							<i class="corner-RB"></i>
							<i class="corner-arrow"></i>
						</span>
					</div>
				</li>
				<li>
					<div class="poster">
						<a><img></a>
					</div>
					<div class="movie_info">
						<div style="margin-top:5px; margin-bottom:5px;">
							<span class="grade">등급</span>
							<a class="title"></a>
						</div>
						<div style="display:inline-block;">
							<span class="reserveRate_label">예매율</span> <span class="reserveRate"></span><br>
							<span class="premier"></span>
						</div>
					</div>
					<div class="like">
						<button></button>
						<span class="count">
							<span></span>
							<i class="corner-LT"></i>
							<i class="corner-RT"></i>
							<i class="corner-LB"></i>
							<i class="corner-RB"></i>
							<i class="corner-arrow"></i>
						</span>
					</div>
				</li>
				<li class="endOfLine">
					<div class="poster">
						<a><img></a>
					</div>
					<div class="movie_info">
						<div style="margin-top:5px; margin-bottom:5px;">
							<span class="grade">등급</span>
							<a class="title"></a>
						</div>
						<div style="display:inline-block;">
							<span class="reserveRate_label">예매율</span> <span class="reserveRate"></span><br>
							<span class="premier"></span>
						</div>
					</div>
					<div class="like">
						<button></button>
						<span class="count">
							<span></span>
							<i class="corner-LT"></i>
							<i class="corner-RT"></i>
							<i class="corner-LB"></i>
							<i class="corner-RB"></i>
							<i class="corner-arrow"></i>
						</span>
					</div>
				</li>
			</ul>
		</div>
		
		<button id="viewMore_btn"><span>더 보기 </span><img src="${pageContext.request.contextPath}/resources/images/public/viewMore_btn.png"></button>
	</div>
</body>
</html>