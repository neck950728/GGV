<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/error/error500.css">
</head>
<body>
	<div id="container">
		<img src="${pageContext.request.contextPath}/resources/images/error/error500.png">
		
		<p>죄송합니다.</p>
		<p>서버 내부 문제로 인해 해당 서비스를 이용하실 수 없습니다.</p>
		
		<button onclick="location.href='${pageContext.request.contextPath}/'">
			<span>메인으로</span>
		</button>
	</div>
</body>
</html>