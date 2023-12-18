<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<script type="text/javascript">
		alert("이미 예약된 좌석입니다.");
		location.href="${pageContext.request.contextPath}/movie/reserve?code=${movie_code}";
	</script>
</head>
<body>
	
</body>
</html>