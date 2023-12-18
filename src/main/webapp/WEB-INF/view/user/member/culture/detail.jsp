<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<title></title>
	<script src="${pageContext.request.contextPath}/resources/js/user/member/culture/public.js"></script>
	
	<script type="text/javascript">
		var g_item = "${item}";
	</script>
	<script src="${pageContext.request.contextPath}/resources/js/user/member/culture/detail.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/member/culture/detail.css">
</head>
<body>
	<div id="container" item="${item}">
		<h3 id="title"></h3>
		<div id="content">
			<img class="img">
			<div class="content">
				<p class="price"></p>
				<div class="add_info">
					<dl class="composition">
						<dt>상품 구성</dt>
						<dd></dd>
					</dl>
					<dl class="expiry">
						<dt>유효기간</dt>
						<dd></dd>
					</dl>
					<dl class="origin">
						<dt>원산지</dt>
						<dd></dd>
					</dl>
				</div>
				<div class="calculate">
					<h3></h3>
					<div class="button">
						<div>
							<a class="minus_button"></a>
							<span class="count">1</span>
							<a class="plus_button"></a>
							
							<span class="buy_price"></span>
						</div>
						<div>
							<a class="cart"></a>
							<a class="payment">구매하기</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<p id="description"></p>
	</div>
</body>
</html>