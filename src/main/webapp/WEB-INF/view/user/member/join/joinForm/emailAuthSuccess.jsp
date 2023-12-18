<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/member/join/joinForm/emailAuthSuccess.css">
</head>
<body>
	<div id="container">
		<div class="welcome_wrap">
			<div class="left">
				<img src="${pageContext.request.contextPath}/resources/images/user/member/join/joinForm/emailAuthSuccess/welcome01.png">
			</div>
			<div class="right">
				<img src="${pageContext.request.contextPath}/resources/images/user/member/join/joinForm/emailAuthSuccess/welcome02.png" style="width:200px; margin-bottom:5px;"><br>
				<div style="float:left;"><img src="${pageContext.request.contextPath}/resources/images/public/logo(small).png" style="width:105px;"></div>
				<div style="float:left; padding:10px 0px 0px 5px;"><span style="font-size:32px;"> 회원가입이 완료되었습니다.</span></div>
			</div>
		</div>
		<div class="button_wrap">
			<button class="main_button" onclick="location.href='${pageContext.request.contextPath}/'"><span>메인으로 이동</span></button> &nbsp;
			<button class="login_button" onclick="location.href='${pageContext.request.contextPath}/member/loginForm'"><span>로그인</span></button>
		</div>
	</div>
</body>
</html>