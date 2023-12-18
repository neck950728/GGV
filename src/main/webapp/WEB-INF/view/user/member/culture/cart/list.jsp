<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<title></title>
	<script src="${pageContext.request.contextPath}/resources/js/user/member/culture/public.js"></script>
	
	<script type="text/javascript">
		var g_cart = ${cart_json};
	</script>
	<script src="${pageContext.request.contextPath}/resources/js/user/member/culture/cart/list.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/member/culture/cart/list.css">
</head>
<body>
	<div id="container">
		<div class="cart_list">
			<table>
				<tr>
					<th class="checkbox">
						<input type="checkbox" id="all_check"><label for="all_check"></label>
					</th>
					<th class="name">상품명</th>
					<th class="price">판매 금액</th>
					<th class="count">수량</th>
					<th class="buy_price">구매 금액</th>
					<th class="delete"></th>
				</tr>
			</table>
		</div>
		<div class="totalPrice_wrap">
			<table>
				<tr>
					<th>총 상품 금액</th>
					<th>할인 금액</th>
					<th>총 결제 금액</th>
				</tr>
				<tr>
					<td class="total_price"></td>
					<td class="sale">0원</td>
					<td class="payment_price"></td>
				</tr>
			</table>
		</div>
		<a class="payment_button">구매하기</a>
	</div>
</body>
</html>