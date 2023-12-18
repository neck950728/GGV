<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<title></title>
	<script src="${pageContext.request.contextPath}/resources/js/user/member/helpCenter/main.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/member/helpCenter/main.css">
</head>
<body>
	<div id="container">
		<%@include file="/WEB-INF/view/user/module/member/helpCenter/menu.jspf"%>
		
		<div id="contents">
			<div class="header">
				<div class="search">
					<input type="text">
					<button type="button">
						<span>검색</span>
					</button>
				</div>
				<div class="write_button">
					<a href="${pageContext.request.contextPath}/member/helpCenter/${type}/writeForm">글쓰기</a>
				</div>
			</div>
			<table class="board">
				<tr>
					<th width="7%">번호</th>
					<th width="50%">제목</th>
					<th width="18%">작성자</th>
					<th width="15%">작성일</th>
					<th width="10%">조회수</th>
				</tr>
				<c:forEach var="article" items="${helpCenterList}">
					<tr>
						<td>${article.num}</td>
						<td><a href="${pageContext.request.contextPath}/member/helpCenter/${type}/detail?article_code=${article.article_code}">${article.title}</a></td>
						<td>${article.id}</td>
						<td>${fn:split(article.date_, " ")[0]}</td> <!-- taglib 선언 위치 : /WEB-INF/view/user/module/header.jspf -->
						<td>${article.hits}</td>
					</tr>
				</c:forEach>
			</table>
			<div class="page_nav">
				<c:if test="${paging.startPage > paging.PAGE_NAV_COUNT}">
					<a href="${pageContext.request.contextPath}/member/helpCenter/${type}?page=1" class="head">처음으로</a>
					<a href="${pageContext.request.contextPath}/member/helpCenter/${type}?page=${paging.startPage - 1}" class="back">이전 10개</a>
				</c:if>
				
				<c:forEach var="i" begin="${paging.startPage}" end="${paging.endPage}" step="1">
					<c:if test="${i == paging.currentPage}">
						<a href="${pageContext.request.contextPath}/member/helpCenter/${type}?page=${i}" class="curPage">${i}</a>
					</c:if>
					<c:if test="${i != paging.currentPage}">
						<a href="${pageContext.request.contextPath}/member/helpCenter/${type}?page=${i}">${i}</a>
					</c:if>
				</c:forEach>
				
				<c:if test="${paging.endPage < paging.totalPage}">
					<a href="${pageContext.request.contextPath}/member/helpCenter/${type}?page=${paging.endPage + 1}" class="next">다음 10개</a>
					<a href="${pageContext.request.contextPath}/member/helpCenter/${type}?page=${paging.totalPage}" class="tail">끝으로</a>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>