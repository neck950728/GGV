<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<title></title>
	<script src="${pageContext.request.contextPath}/resources/js/user/member/culture/public.js"></script>
	
	<script type="text/javascript">
		var g_type = "${type}";
		var g_watchedMovies = ${watchedMovies_json};
		var g_reserveList = ${reserveList_json};
		var g_paymentList = ${paymentList_json};	
	</script>
	<script src="${pageContext.request.contextPath}/resources/js/user/member/myPage.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/member/myPage.css">
	
	<script src="${pageContext.request.contextPath}/resources/external_js/jquery-ui.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/external_css/jquery-ui.css">
	
	<script src="${pageContext.request.contextPath}/resources/external_js/sha256.min.js"></script>
</head>
<body>
	<div id="container">
		<div id="center_align_wrap">
			<div id="my_info">
				<div class="profile">
					<div class="left">
						<span class="profile_bg"></span>
						<img src="${pageContext.request.contextPath}/resources/images/user/member/myPage/default_profile.gif">
					</div>
					<div class="right">
						<p>
							<span class="name">님</span><span class="id"></span>
							<img src="${pageContext.request.contextPath}/resources/images/user/member/myPage/changeInfo_button.png" class="changeInfo"><br>
							<span>환영합니다!</span>
						</p>
					</div>
				</div>
				<div class="other">
					<ul>
						<li class="wishList">
							<div class="boxing">
								<img src="${pageContext.request.contextPath}/resources/images/user/member/myPage/wishList_icon.png">
								<span>위시리스트</span>
							</div>
						</li>
						<li class="watchedMovies">
							<div class="boxing">
								<img src="${pageContext.request.contextPath}/resources/images/user/member/myPage/watchedMovies_icon.png">
								<span>내가 본 영화</span>
							</div>
						</li>
						<li class="reserveList">
							<div class="boxing">
								<img src="${pageContext.request.contextPath}/resources/images/user/member/myPage/reserveList_icon.png">
								<span>예매 내역</span>
							</div>
						</li>
						<li class="paymentList">
							<div class="boxing">
								<img src="${pageContext.request.contextPath}/resources/images/user/member/myPage/paymentList_icon.png">
								<span>구매 내역</span>
							</div>
						</li>
					</ul>
				</div>
			</div>
			<div id="show">
				<div class="side_menu">
					<ul>
						<li class="simple_profile">
							<div class="image">
								<span class="bg"></span>
								<img src="${pageContext.request.contextPath}/resources/images/user/member/myPage/default_profile.gif">
							</div>
							<div class="name">
								<span></span>
							</div>
						</li>
						<li class="wishList_button  button">
							<em></em><br>
							<span>위시리스트</span>
						</li>
						<li class="watchedMovies_button  button">
							<em>0</em><br>
							<span>내가 본 영화</span>
						</li>
						<li class="reserveList_button  button">
							<em>0</em><br>
							<span>예매 내역</span>
						</li>
						<li class="paymentList_button  button">
							<em>0</em><br>
							<span>구매 내역</span>
						</li>
					</ul>
				</div>
				<div class="detail">
				</div>
			</div>
		</div>
	</div>
</body>
</html>