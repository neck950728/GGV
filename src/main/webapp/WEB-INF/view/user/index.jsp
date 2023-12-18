<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<script type="text/javascript">
    	sessionStorage.setItem("contextPath", "${pageContext.request.contextPath}");
	</script>
	
	<script src="${pageContext.request.contextPath}/resources/js/user/index.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/index.css">
	
	<script src="${pageContext.request.contextPath}/resources/external_js/swiper.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/external_css/swiper.min.css">
</head>
<body>
	<div id="container">
		<div id="center_align_wrap">
			<div id="text_banner">
				<img src="${pageContext.request.contextPath}/resources/images/user/index/text_banner.jpg">
			</div>
			<div id="main_banner">
				<div class="swiper-container">
					<div class="swiper-wrapper">
						<div class="swiper-slide"><a href="#"><img src="${pageContext.request.contextPath}/resources/images/user/index/main_banner01.jpg"></a></div>
						<div class="swiper-slide"><a href="#"><img src="${pageContext.request.contextPath}/resources/images/user/index/main_banner02.jpg"></a></div>
						<div class="swiper-slide"><a href="#"><img src="${pageContext.request.contextPath}/resources/images/user/index/main_banner03.jpg"></a></div>
						<div class="swiper-slide"><a href="#"><img src="${pageContext.request.contextPath}/resources/images/user/index/main_banner04.jpg"></a></div>
						<div class="swiper-slide"><a href="#"><img src="${pageContext.request.contextPath}/resources/images/user/index/main_banner05.jpg"></a></div>
						<div class="swiper-slide"><a href="#"><img src="${pageContext.request.contextPath}/resources/images/user/index/main_banner06.jpg"></a></div>
						<div class="swiper-slide"><a href="#"><img src="${pageContext.request.contextPath}/resources/images/user/index/main_banner07.jpg"></a></div>
						<div class="swiper-slide"><a href="#"><img src="${pageContext.request.contextPath}/resources/images/user/index/main_banner08.jpg"></a></div>
						<div class="swiper-slide"><a href="#"><img src="${pageContext.request.contextPath}/resources/images/user/index/main_banner09.jpg"></a></div>
						<div class="swiper-slide"><a href="#"><img src="${pageContext.request.contextPath}/resources/images/user/index/main_banner10.jpg"></a></div>
					</div>
					<div class="swiper-button-prev swiper-button-white"></div>
					<div class="swiper-button-next swiper-button-white"></div>
					<div class="swiper-pagination swiper-pagination-white"></div>
				</div>
			</div>
			
			<div id="movie_selection">
				<div class="title"><img src="${pageContext.request.contextPath}/resources/images/user/index/movie_selection.gif"></div>
				<div class="recommend_movie">
					<div class="left">
						<video id="video" width="100%" height="100%" controls="controls" autoplay="autoplay">
							<source>
						</video>
					</div>
					<div class="right"><img></div>
				</div>
			</div>
		</div>
	</div>
	
	<c:if test="${cookie.doNotSeeToday == null}">
		<div id="popup_bg"></div>
		<div id="popup">
			<div id="center">
				<img src="${pageContext.request.contextPath}/resources/images/public/logo(small).png"><br>
				<span>본 사이트는 PC Chrome 브라우저에 최적화되어 있습니다.</span>
			</div>
			<div id="bottom">
				<input type="checkbox" onchange="doNotSeeToday(this)">
				<span>오늘 하루 보지 않기</span>
			</div>
		</div>
	</c:if>
</body>
</html>