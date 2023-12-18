<%@page import="java.util.UUID"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<title>GGV</title>
	
	<script src="${pageContext.request.contextPath}/resources/js/user/member/join/joinForm/form.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/member/join/joinForm/form.css">
	
	<script src="${pageContext.request.contextPath}/resources/external_js/jquery-ui.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/external_css/jquery-ui.css">
	
	<script src="${pageContext.request.contextPath}/resources/external_js/sha256.min.js"></script>
</head>
<body>
	<div class="joinFormWrap">
		<div class="center_align_wrap">
			<div class="logo">
				<a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/resources/images/public/logo(large).png"></a>
			</div>
			
			<form action="${pageContext.request.contextPath}/member/join/joinForm/emailAuth" method="POST" id="joinForm">
				<div class="id instantly_check form_contents">
					<span>아이디</span><br>
					<div class="inputField_area"><input type="text" name="id" class="use_tooltip" tooltip_name="id_tooltip"></div>
					<div class="check_icon_area"></div>
					<span class="error_msg" style="float:left;"></span>
					
					<div id="id_tooltip" class="tooltip">
						<span>ㆍ첫 글자는 소문자로 시작해야 합니다.</span><br>
						<span>ㆍ대문자는 사용할 수 없으며 특수문자는 밑줄 문자만 사용 가능합니다.</span><br>
						<span>ㆍ길이는 5 ~ 12자이어야 합니다.</span>
					</div>
				</div><br>
				
				<div class="pw instantly_check form_contents">
					<span>비밀번호</span><br>
					<div class="inputField_area"><input type="password" name="pw" class="use_tooltip" tooltip_name="pw_tooltip"></div>
					<div class="check_icon_area"></div>
					
					<div id="pw_tooltip" class="tooltip">
						<span>ㆍ영문 대소문자 외 최소 1개의 숫자 혹은 특수문자를 포함하여야 합니다.</span><br>
						<span>ㆍ길이는 8 ~ 16자이어야 합니다.</span>
					</div>
				</div><br>
				
				<div class="confirm_pw instantly_check form_contents">
					<span>비밀번호 확인</span><br>
					<div class="inputField_area"><input type="password"></div>
					<div class="check_icon_area"></div>
				</div><br>
	
	
				<div class="name after_check form_contents">
					<span>이름</span><br>
					<input type="text" name="name">
					<span class="error_msg">올바른 이름을 입력해주세요.</span>
				</div><br>
				
				<div class="birth after_check form_contents">
					<span>생년월일</span><br>
					<input type="text" name="birth" id="calendar" readonly="readonly">
					<span class="error_msg">생년월일을 입력해주세요.</span>
				</div><br>
				
				<div class="gender after_check form_contents">
					<span>성별</span><br>
					<div class="gender_radioBtn_area">
						<label for="man" class="man_label">남자</label>
						<input type="radio" name="gender" id="man" value="남">
						<label for="woman" class="woman_label">여자</label>
						<input type="radio" name="gender" id="woman" value="여">
					</div>
					<span class="error_msg">성별을 선택해주세요.</span>
				</div><br>
				
				<div class="email after_check form_contents">
					<span>이메일</span><br>
					<input type="text" name="email" class="use_tooltip" tooltip_name="email_tooltip" maxlength="35">
					<span class="error_msg">올바른 이메일 주소를 입력해주세요.</span>
					
					<div id="email_tooltip" class="tooltip">
						<span>인증 메일이 전송되므로 반드시 정확히 입력해주세요.</span>
					</div>
				</div><br>
				
				<div class="phone after_check form_contents">
					<span>휴대폰</span><br>
					<div class="phone_auth_area">
						<input type="text" name="phone" class="phone_inputField" placeholder="'-' 문자 제외">
						<button type="button" class="getAuthNum"><span>인증번호 받기</span></button>
						<input type="text" class="authNum_inputField" placeholder="인증번호를 입력해주세요." maxlength="6" disabled="disabled">
					</div>
					<span class="error_msg"></span>
				</div><br>
				
				<div class="join_btn">
					<button type="button"><span>가입하기</span></button> <!-- button default type : submit -->
				</div>
			</form>
		</div>
	</div>
	
	<input type="hidden" class="identification_value" value="<%= UUID.randomUUID().toString() %>">
</body>
</html>