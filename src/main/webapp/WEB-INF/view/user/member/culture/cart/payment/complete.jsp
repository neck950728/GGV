<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<title></title>
	<script src="${pageContext.request.contextPath}/resources/js/user/member/culture/public.js"></script>
	
	<script src="${pageContext.request.contextPath}/resources/js/user/member/culture/cart/payment/complete.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/member/culture/cart/payment/complete.css">
</head>
<body>
	<div id="container">
		<div class="paymentComplete_wrap">
			<p>
				<strong>상품 결제가 완료되었습니다!</strong>
				<span>${paymentComplete.order_code}</span>
			</p>
			<dl>
				<dt>총 상품 금액</dt>
				<dd>${paymentComplete.payment_price}</dd>
				
				<dt>할인 금액</dt>
				<dd>0원</dd>
				
				<dt>총 결제 금액</dt>
				<dd>${paymentComplete.payment_price}</dd>
			</dl>
		</div>
		<div class="button_wrap">
			<a href="${pageContext.request.contextPath}/member/myPage?type=paymentList" class="payment_list">결제 내역</a> &nbsp;
			<a href="${pageContext.request.contextPath}/member/culture" class="product_list">상품 더 보기</a>
		</div>
	</div>
</body>
</html>