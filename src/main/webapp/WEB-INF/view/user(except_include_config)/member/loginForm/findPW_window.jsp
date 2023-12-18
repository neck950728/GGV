<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<script src="${pageContext.request.contextPath}/resources/external_js/sha256.min.js"></script>
	
	<script src="${pageContext.request.contextPath}/resources/js/user/member/loginForm/public.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/member/loginForm/public.css">
	
	<script src="${pageContext.request.contextPath}/resources/js/user/member/loginForm/findPW_window.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/member/loginForm/findPW_window.css">
</head>
<body>
	<div id="container">
		<div id="content">
			<div id="logo">
				<img src="${pageContext.request.contextPath}/resources/images/public/logo(small).png">
			</div>
			<div id="title">
				<p class="title-main">비밀번호 찾기</p>
				<p class="title-sub">
					어떤 계정의 비밀번호를 찾으시겠습니까?
				</p>
			</div>
			<div id="checkID_area">
				<label for="id">ID</label>
				<input type="text" id="id">
				<button>
					<span>다음</span>
				</button>
			</div>
		</div>
	</div>
</body>
</html>