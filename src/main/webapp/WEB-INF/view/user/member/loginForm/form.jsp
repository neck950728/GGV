<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<script type="text/javascript">
		if(sessionStorage.getItem("contextPath") == null){
			sessionStorage.setItem("contextPath", "${pageContext.request.contextPath}");
		}
	</script>
	
	<script src="${pageContext.request.contextPath}/resources/external_js/sha256.min.js"></script>
	
	<script src="${pageContext.request.contextPath}/resources/js/user/member/loginForm/form.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/member/loginForm/form.css">
</head>
<body>
	<div id="container">
		<div class="left">
			<div class="boxing">
				<form action="${pageContext.request.contextPath}/member/loginForm/login" method="POST" id="loginForm">
					<div class="login">
						<div class="input_field">
							<input type="text" name="id" class="id" placeholder="아이디" style="margin-bottom:5px;" maxlength="20" autocomplete="off"><br>
							<input type="password" name="pw" class="pw" placeholder="비밀번호" maxlength="20">
						</div>
						<div class="login_button">
							<button><span>로그인</span></button>
						</div>
					</div>
				</form>
				<div class="error_msg"><span></span></div>
				
				<br>
				
				<div class="buttons">
					<a href="#" class="findID_btn"><img src="${pageContext.request.contextPath}/resources/images/user/member/loginForm/form/findID_btn.png"></a>
					<a href="#" class="findPW_btn"><img src="${pageContext.request.contextPath}/resources/images/user/member/loginForm/form/findPW_btn.png"></a>
					<a href="${pageContext.request.contextPath}/member/join/joinFormMain" class="join_btn"><img src="${pageContext.request.contextPath}/resources/images/user/member/loginForm/form/join_btn.png"></a>
				</div>
			</div>
		</div>
		
		<div class="right">
			<img src="${pageContext.request.contextPath}/resources/images/user/member/loginForm/form/banner.jpg">
		</div>
	</div>
</body>
</html>