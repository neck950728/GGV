<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<title></title>
	<script type="text/javascript">
		var g_articleCode = "${article_code}";
	</script>
	<script src="${pageContext.request.contextPath}/resources/js/user/member/helpCenter/writeForm.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/user/member/helpCenter/articleForm.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/member/helpCenter/articleForm.css">
	
	<!-- include jquery & bootstrap -->
	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
	
	<!-- include summernote -->
	<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css">
	
	<!--
		<script src="${pageContext.request.contextPath}/resources/summernote/js/summernote-lite.js"></script>
		<script src="${pageContext.request.contextPath}/resources/summernote/lang/summernote-ko-KR.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/summernote/css/summernote-lite.css">
	-->
</head>
<body>
	<div id="container">
		<%@include file="/WEB-INF/view/user/module/member/helpCenter/menu.jspf"%>
		
		<div id="contents">
			<form action="${pageContext.request.contextPath}/member/helpCenter/${type}/writeForm/write" method="POST">
				<input type="text" name="title" maxlength="50" placeholder="제목">
				<textarea id="summernote" name="content"></textarea>
				<input type="button" class="submit_button" value="등록">
				
				<input type="hidden" name="article_code" value="${article_code}">
			</form>
		</div>
	</div>
</body>
</html>