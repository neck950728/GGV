<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<script src="${pageContext.request.contextPath}/resources/js/admin/movie/insert/select_insert_type.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/movie/insert/select_insert_type.css">
</head>
<body>
	<div id="container">
		<div class="vertical_align_wrap">
			<div class="buttons">
				<button onclick="location.href='${pageContext.request.contextPath}/admin/movie/insert/personally_insert'" class="personally_insert_btn">직접 등록</button> &emsp; &emsp;
				<button type="button" class="excel_insert_btn">Excel 파일 등록</button>
			</div>
		</div>
	</div>
</body>
</html>