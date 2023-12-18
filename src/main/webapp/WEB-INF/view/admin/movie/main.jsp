<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<script src="${pageContext.request.contextPath}/resources/js/admin/movie/main.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/movie/main.css">
</head>
<body>
	<div id="container">
		<div class="vertical_align_wrap">
			<div class="buttons">
				<button onclick="location.href='${pageContext.request.contextPath}/admin/movie/insert'" class="insert_btn">추가</button> &emsp; &emsp;
				<button onclick="location.href='${pageContext.request.contextPath}/admin/movie/modify/movie_list?page=1'" class="update_btn">수정</button>
			</div>
		</div>
	</div>
</body>
</html>