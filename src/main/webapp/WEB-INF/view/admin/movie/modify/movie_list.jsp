<%@page import="admin.movie.bean.DirectorDTO"%>
<%@page import="java.util.concurrent.CopyOnWriteArrayList"%>
<%@page import="admin.movie.bean.MovieDTO"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<script src="${pageContext.request.contextPath}/resources/js/admin/movie/modify/movie_list.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/movie/modify/movie_list.css">
</head>
<body>
	<div id="container">
		<div id="center_align_wrap">
			<button class='viewAll_btn' onclick="location.href='${pageContext.request.contextPath}/admin/movie/modify/movie_list?page=1'">전체 보기</button>
			<div class="search">
				<input type="text">
				<button>검색</button>
			</div>
			<div class="division">
				<ul>
					<li class="poster">포스터</li>
					<li class="title">제목</li>
					<li class="director">감독</li>
					<li class="genre">장르</li>
					<li class="grade">등급</li>
					<li class="premier">개봉일</li>
				</ul>
			</div>
			<ul class='movie_list'>
				<%
					List<MovieDTO> movie_list = (List<MovieDTO>)request.getAttribute("movieList");
					for(MovieDTO movieDTO : movie_list){
						String director = "";
						for(DirectorDTO directorDTO : movieDTO.getDirector_list()){
							director += directorDTO.getKo_name() + ", ";
						}
						
						if(director.isEmpty()){
							director = "(알 수 없음)";
						}else{
							director = director.substring(0, director.lastIndexOf(","));
						}
				%>
						<li class="movie">
							<ul class="movie_info">
								<li class="poster">
									<a href="${pageContext.request.contextPath}/admin/movie/modify?code=<%= movieDTO.getCode() %>"><img src="<%= movieDTO.getPoster() %>"></a>
								</li>
								<li class="title">
									<span><a href="${pageContext.request.contextPath}/admin/movie/modify?code=<%= movieDTO.getCode() %>"><%= movieDTO.getTitle() %></a></span>
								</li>
								<li class="director"><span><%= director %></span></li>
								<li class="genre"><span><%= movieDTO.getGenre() %></span></li>
								<c:if test="<%= movieDTO.getGrade() != null %>">
									<li class="grade"><span><%= movieDTO.getGrade() %></span></li>
								</c:if>
								<c:if test="<%= movieDTO.getGrade() == null %>">
									<li class="grade"><span>미정</span></li>
								</c:if>
								<li class="premier"><span><%= movieDTO.getPremier() %></span></li>
								<li class="delete_btn">
									<span>
										<a href="${pageContext.request.contextPath}/admin/movie/modify/movie_list/delete?code=<%= movieDTO.getCode() %>"><img src="${pageContext.request.contextPath}/resources/images/admin/movie/modify/movie_list/delete_icon.png"></a>
									</span>
								</li>
							</ul>
						</li>
				<%
					}
				%>
			</ul>
			<div id="page_nav">
				<c:if test="${paging.startPage > paging.PAGE_NAV_COUNT}">
					<a href="${pageContext.request.contextPath}/admin/movie/modify/movie_list?page=1" class="head">처음으로</a>
					<a href="${pageContext.request.contextPath}/admin/movie/modify/movie_list?page=${paging.startPage - 1}" class="back">이전 10개</a>
				</c:if>
				
				<c:forEach var="i" begin="${paging.startPage}" end="${paging.endPage}" step="1">
					<c:if test="${i == paging.currentPage}">
						<a href="${pageContext.request.contextPath}/admin/movie/modify/movie_list?page=${i}" class="curPage">${i}</a>
					</c:if>
					<c:if test="${i != paging.currentPage}">
						<a href="${pageContext.request.contextPath}/admin/movie/modify/movie_list?page=${i}">${i}</a>
					</c:if>
				</c:forEach>
				
				<c:if test="${paging.endPage < paging.totalPage}">
					<a href="${pageContext.request.contextPath}/admin/movie/modify/movie_list?page=${paging.endPage + 1}" class="next">다음 10개</a>
					<a href="${pageContext.request.contextPath}/admin/movie/modify/movie_list?page=${paging.totalPage}" class="tail">끝으로</a>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>