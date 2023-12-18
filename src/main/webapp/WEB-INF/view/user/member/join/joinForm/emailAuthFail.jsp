<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/member/join/joinForm/emailAuthFail.css">
</head>
<body>
	<div id="container">
		<div class="left">
			<img src="${pageContext.request.contextPath}/resources/images/user/member/join/joinForm/emailAuthFail/fail.png" width="220px">
		</div>
		<div class="right">
			<span style="font-size:30px;">이메일 인증 오류</span><br><br>
			<span style="font-size:15px; color:#F44336;">정상적인 경로로 접근하지 않았거나 예기치 못한 오류</span><span style="font-size:15px; color:#524848;">로 인하여</span><br>
			<span style="font-size:15px; color:#524848;">이메일 인증이 정상적으로 이루어지지 않았습니다.</span>
		</div>
	</div>
</body>
</html>