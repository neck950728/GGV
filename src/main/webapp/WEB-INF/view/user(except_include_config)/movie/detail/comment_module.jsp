<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
<head>
	<script type="text/javascript">
		var g_memberInfo = window.parent.g_memberInfo;
	</script>
	
	<script type="text/javascript">
		var g_movieCode = "${movie_code}";
		var g_commentList = ${commentList_json};
		var g_myComment = ${myComment_json};
	</script>
	<script src="${pageContext.request.contextPath}/resources/js/user/movie/detail/comment_module.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/movie/detail/comment_module.css">
</head>
<body>
	<div class="comment" id="bookmark">
		<div class="result">
			<div class="heading">
				<h4>300자 평</h4>
				<em>|</em>
				<span class="total"></span>
				<div class="sorting_list">
					<c:if test="${order == 'sympathy'}">
						<a href="${pageContext.request.contextPath}/movie/detail/commentModule?mID=${movie_code}&page=1&order=sympathy#bookmark" class="on" style="margin-right:4px;">호감순</a>
						<a href="${pageContext.request.contextPath}/movie/detail/commentModule?mID=${movie_code}&page=1&order=dateCreated#bookmark">최신순</a>
					</c:if>
					<c:if test="${order == 'dateCreated'}">
						<a href="${pageContext.request.contextPath}/movie/detail/commentModule?mID=${movie_code}&page=1&order=sympathy#bookmark" style="margin-right:4px;">호감순</a>
						<a href="${pageContext.request.contextPath}/movie/detail/commentModule?mID=${movie_code}&page=1&order=dateCreated#bookmark" class="on">최신순</a>
					</c:if>
				</div>
			</div>
			<ul class="result_list">
			</ul>
		</div>
		<div id="page_nav">
			<c:if test="${paging.startPage > paging.PAGE_NAV_COUNT}">
				<a href="${pageContext.request.contextPath}/movie/detail/commentModule?mID=${movie_code}&page=1&order=${order}#bookmark" class="head">처음으로</a>
				<a href="${pageContext.request.contextPath}/movie/detail/commentModule?mID=${movie_code}&page=${paging.startPage - 1}&order=${order}#bookmark" class="back">이전 10개</a>
			</c:if>
			
			<c:forEach var="i" begin="${paging.startPage}" end="${paging.endPage}" step="1">
				<c:if test="${i == paging.currentPage}">
					<a href="${pageContext.request.contextPath}/movie/detail/commentModule?mID=${movie_code}&page=${i}&order=${order}#bookmark" class="curPage">${i}</a> 
				</c:if>
				<c:if test="${i != paging.currentPage}">
					<a href="${pageContext.request.contextPath}/movie/detail/commentModule?mID=${movie_code}&page=${i}&order=${order}#bookmark">${i}</a>
				</c:if>
			</c:forEach>
			
			<c:if test="${paging.endPage < paging.totalPage}">
				<a href="${pageContext.request.contextPath}/movie/detail/commentModule?mID=${movie_code}&page=${paging.endPage + 1}&order=${order}#bookmark" class="next">다음 10개</a>
				<a href="${pageContext.request.contextPath}/movie/detail/commentModule?mID=${movie_code}&page=${paging.totalPage}&order=${order}#bookmark" class="tail">끝으로</a>
			</c:if>
		</div>
	</div>
</body>
</html>