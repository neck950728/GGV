<%@page import="java.util.Enumeration"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/member/join/joinForm/emailAuth.css">
</head>
<body>
	<div id="container">
		<div class="left">
			<img src="${pageContext.request.contextPath}/resources/images/user/member/join/joinForm/emailAuth/mail_img.png">
		</div>
		<div class="right">
			<span style="font-size:25px;">인증 메일을 확인해주세요.</span><br><br>
			<span style="font-size:15px; color:#F44336;">${param.email}</span><span style="font-size:15px;">으로 인증 메일을 전송하였습니다.</span><br>
			<span style="font-size:12px; color:#828282;">1시간 이내로 인증을 받으셔야 정상적으로 가입이 완료됩니다.</span>
		</div>
	</div>
</body>
</html>