<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/index.css">
</head>
<body>
	<div class="wrap">
		<div class="logo">
			<a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/resources/images/public/logo(large).png"></a>
		</div>
		<div class="buttons">
			<button onclick="location.href='${pageContext.request.contextPath}/admin/movie'" style="background:linear-gradient(to right, #ff7877, red);">영화</button>
			<button style="background:linear-gradient(to left, #ffd280, orange);">미정</button>
			<button style="background:linear-gradient(to right, #ffffb0, yellow);">미정</button>
		</div>
	</div>
</body>
</html>