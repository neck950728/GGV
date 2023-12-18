<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<title></title>
	<script type="text/javascript">
		g_articleCode = "${helpCenter.article_code}";
	</script>
	<script src="${pageContext.request.contextPath}/resources/js/user/member/helpCenter/detail.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/member/helpCenter/detail.css">
</head>
<body>
	<div id="container">
		<%@include file="/WEB-INF/view/user/module/member/helpCenter/menu.jspf"%>
				
		<div id="contents">
			<div id="title">
				<h3 class="title">${helpCenter.title}</h3>
				<div class="boxing">
					<p class="date">
						<span>등록일</span>
						${helpCenter.date_}
					</p>
					<p class="hits">
						<span>조회수</span>
						${helpCenter.hits}
					</p>
				</div>
			</div>
			<div id="content">
				${helpCenter.content}
			</div>
			<c:if test="${sessionScope.id eq 'admin'}">
				<div id="comment_input">
					<textarea></textarea>
					<button onclick="insert_comment()">등록</button>
				</div>
			</c:if>
			<div id="comment">
				<ul>
					<c:forEach var="comment" items="${helpCenterCommentList}">
						<li class="comment">
							<img src="${pageContext.request.contextPath}/resources/images/user/member/helpCenter/detail/admin_icon.png">
							<div>
								<p>${comment.comment_}</p>
								<div>
									<span class="writer">관리자</span>
									<span>|</span>
									<span class="date">${comment.date_}</span>
									<c:if test="${sessionScope.id eq 'admin'}">
										<div class="buttons">
											<span onclick="update_comment('${comment.identification_code}')" class="update">수정</span>
											<span onclick="delete_comment('${comment.identification_code}')" class="delete">삭제</span>
										</div>
									</c:if>
							 	</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div id="buttons">
				<c:if test="${sessionScope.id eq helpCenter.id || sessionScope.id eq 'admin'}">
					<c:if test="${sessionScope.id eq helpCenter.id}">
						<button class="modify mine" onclick="location.href='${pageContext.request.contextPath}/member/helpCenter/${type}/modifyForm?article_code=${helpCenter.article_code}'">
							<span>수정</span>
						</button>
					</c:if>
					<button class="delete mine" onclick="delete_article()">
						<span>삭제</span>
					</button>
				</c:if>
				
				<button onclick="location.href='${pageContext.request.contextPath}/member/helpCenter/${type}'" class="list">
					<span>목록</span>
				</button>
			</div>
		</div>
	</div>
</body>
</html>