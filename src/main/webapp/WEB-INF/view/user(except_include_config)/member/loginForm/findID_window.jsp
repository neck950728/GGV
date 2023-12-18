<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<script src="${pageContext.request.contextPath}/resources/js/user/member/loginForm/public.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/member/loginForm/public.css">
	
	<script src="${pageContext.request.contextPath}/resources/js/user/member/loginForm/findID_window.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/member/loginForm/findID_window.css">
</head>
<body>
	<div id="container">
		<div id="content">
			<div id="logo">
				<img src="${pageContext.request.contextPath}/resources/images/public/logo(small).png">
			</div>
			<div id="title">
				<p class="title-main">아이디 찾기</p>
				<p class="title-sub">
					아이디를 잊으셨나요?<br>
					본인 확인 방법을 선택해주세요.
				</p>
			</div>
			<div id="buttons">
				<ul>
					<li class="phoneAuth">
						<button>
							<span>등록된 휴대폰으로 찾기</span>
						</button>
					</li>
					<li class="emailAuth">
						<button>
							<span>등록된 이메일로 찾기</span>
						</button>
					</li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>