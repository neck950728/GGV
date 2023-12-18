<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<script src="${pageContext.request.contextPath}/resources/js/user/movie/unifiedSearch.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/movie/unifiedSearch.css">
</head>
<body>
	<div id="container">
		<div class="search">
			<div class="search_input"><input type="text" value="${keyword}"></div>
			<button>검색</button>
		</div>
		
		<div class="movie_info">
			<!-- taglib 선언 위치 : /WEB-INF/view/user/module/header.jspf -->
			
			<c:choose>
				<c:when test="${searchedMovieList.size() == 0}">
					<div class="not_searched">
						<h2>검색 결과가 없습니다.</h2>
						<ul>
							<li>ㆍ단어의 철자가 정확한지 확인해주세요.</li>
							<li>ㆍ검색어의 단어 수를 줄이거나, 다른 검색어로 검색해보세요.</li>
							<li>ㆍ보다 일반적인 검색어로 다시 검색해보세요.</li>
						</ul>
					</div>
				</c:when>
				<c:otherwise>
					<c:set var="PER_LINE_COUNT" value="4" />
					
					<div class="total_movie">
						<span>영화(${searchedMovieList.size()}건)</span>
						<c:if test="${searchedMovieList.size() > PER_LINE_COUNT}">
							<img src="${pageContext.request.contextPath}/resources/images/public/viewMore_btn.png" class="viewMore_btn">
						</c:if>
					</div>
					<div class="movie_list">
						<!-- <fmt:formatNumber var="i" value="${searchedMovieList.size() / PER_LINE_COUNT}" pattern="#" /> --> <!-- formatNumber : 올림 처리 -->
						<fmt:parseNumber var="i" value="${searchedMovieList.size() / PER_LINE_COUNT}" integerOnly="true" /> <!-- parseNumber : 버림 처리 -->
						<c:if test="${searchedMovieList.size() % PER_LINE_COUNT > 0}">
							<c:set var="i" value="${i + 1}" />
						</c:if>
						
						<c:set var="begin" value="0" />
						<c:set var="end" value="3" />
						
						<c:forEach begin="1" end="${i}">
							<ul>
								<c:forEach var="movie" items="${searchedMovieList}" begin="${begin}" end="${end}">
									<li>
										<a href="${pageContext.request.contextPath}/movie/detail?mID=${movie.code}" class="poster"><img src="${movie.poster}"></a>
										<div class="info">
											<a class="title">
												<c:if test="${fn:length(movie.title) > 12}">
													${fn:substring(movie.title, 0, 12)}...
												</c:if>
												<c:if test="${fn:length(movie.title) <= 12}">
													${movie.title}
												</c:if>
											</a>
											<span class="reserveRate">예매율 ${movie.reserveRate}%</span><br>
											<span class="premier">${movie.premier} 개봉</span>
											<c:if test="${movie.screeningInfo_list.size() > 0}">
												<a class="reserveBtn" href="${pageContext.request.contextPath}/movie/reserve?code=${movie.code}"></a>
											</c:if>
										</div>
									</li>
								</c:forEach>
								
								<c:set var="begin" value="${begin + PER_LINE_COUNT}" />
								<c:set var="end" value="${end + PER_LINE_COUNT}" />
							</ul>
							<div class="ul_borderBottom"></div>
						</c:forEach>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
</html>