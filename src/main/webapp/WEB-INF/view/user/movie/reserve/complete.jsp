<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/movie/reserve/complete.css">
</head>
<body>
	<div id="container">
		<img src="${pageContext.request.contextPath}/resources/images/user/movie/reserve/ticket/banner.jpg" class="banner  left">
		
		<div id="reserve_result">
			<div id="title">
			</div>
			<div id="reserve_info">
				<img class="poster" src="${reserve_detail.poster}">
				<ul>
					<li>
						<p>예매 번호</p>
						<span class="emphasis">${reserve_detail.reserve_code}</span>
					</li>
					<li>
						<p>영화</p>
						<span>${reserve_detail.title}</span>
					</li>
					<li>
						<p>극장</p>
						<span>CGV ${reserve_detail.sub_region}</span>
					</li>
					<li>
						<p>일시</p>
						<span>${reserve_detail.date_} ${reserve_detail.time_}</span>
					</li>
					<li>
						<p>인원</p>
						<span>${reserve_detail.personnel}명</span>
					</li>
					<li>
						<p>좌석</p>
						<span>${reserve_detail.seat}</span>
					</li>
					<li>
						<p>결제 금액</p>
						<span class="emphasis">${reserve_detail.total_price}</span>
					</li>
				</ul>
			</div>
		</div>
		
		<img src="${pageContext.request.contextPath}/resources/images/user/movie/reserve/ticket/banner.jpg" class="banner  right">
	</div>
</body>
</html>