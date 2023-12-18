<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/member/join/joinForm/emailAuthAlreadySucceeded.css">
</head>
<body>
	<div id="container">
		<div class="left">
			<img src="${pageContext.request.contextPath}/resources/images/user/member/join/joinForm/emailAuthAlreadySucceeded/complete.png" width="220px">
		</div>
		<div class="right">
			<span style="font-size:30px;">인증 완료</span><br><br>
			<span style="font-size:15px; color:#524848;">고객님께서는</span>
			<span style="font-size:15px; color:#F44336;">이미 인증을 완료</span><span style="font-size:15px; color:#524848;">하셨습니다.</span><br>
			<span style="font-size:15px; color:#524848;">가입하신 아이디로 로그인하여 서비스를 이용해 주시기 바랍니다.</span><br><br>
			
			<button class="login_button" onclick="location.href='${pageContext.request.contextPath}/member/loginForm'"><span>로그인</span></button>
		</div>
	</div>
</body>
</html>