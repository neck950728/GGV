<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<script src="${pageContext.request.contextPath}/resources/js/admin/movie/public.js"></script>
	
	<script src="${pageContext.request.contextPath}/resources/js/admin/movie/insert/personally_insert.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/movie/insert/personally_insert.css">
	
	<script src="${pageContext.request.contextPath}/resources/external_js/jquery-ui.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/external_css/jquery-ui.css">
	
	
	<script src="${pageContext.request.contextPath}/resources/external_js/jquery.mCustomScrollbar.concat.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/external_css/jquery.mCustomScrollbar.css">
</head>
<body>
	<div id="container">
		<div id="center_align_wrap">
			<form action="${pageContext.request.contextPath}/admin/movie/insert/personally_insert/request" enctype="multipart/form-data" method="POST">
				<div class="register_wrap">
					<div class="header">
						<h1>Movie Register</h1>
					</div>
					
					<div class="body">
						<div class="title  form_contents">
							<span class="label">영화명</span>
							<input type="text" name="title">
						</div>
						<div class="genre  form_contents">
							<span class="label">장르</span>
							<input type="text" name="genre" class="not_required">
						</div>
						<div class="nation  form_contents">
							<span class="label">국가</span>
							<input type="text" name="nation" class="not_required">
						</div>
						<div class="showtimes  form_contents">
							<span class="label">영상 길이</span>
							<input type="text" name="showtimes" class="not_required">
						</div>
						<div class="premier  form_contents">
							<span class="label">개봉일</span>
							<input type="text" name="premier" readonly="readonly">
						</div>
						<div class="grade  form_contents">
							<span class="label">등급</span>
							<select name="grade" style="width:544px;">
								<option value="전체">전체</option>
								<option value="12세 이상">12세 이상</option>
								<option value="15세 이상">15세 이상</option>
								<option value="청소년 관람불가">청소년 관람불가</option>
								<option value="미정">미정</option>
							</select>
						</div>
						<div class="poster  form_contents">
							<span class="label">포스터</span>
							<input type="text" class="img" name="poster" style="width:456px;">
							<input type="file" class="img_file" name="poster_file">
							<ul class="upload_btn">
								<li>
									<span>사진 업로드</span>
									<ul class="sub_menu"></ul>
								</li>
							</ul>
						</div>
						<div class="stillCut  form_contents  multiple" style="margin-top:60px;">
							<span class="label">스틸컷</span><img src="${pageContext.request.contextPath}/resources/images/admin/movie/insert/personally_insert/plus_icon.png" class="plus_btn">
							<div>
								<ul class="stillCut_info" index="0">
									<li class="stillCut"><input type="text" class="img" name="stillCut_list[0].stillCut"></li>
									<li>
										<input type="file" class="img_file" name="stillCut_file">
										<ul class="upload_btn">
											<li>
												<span>사진 업로드</span>
												<ul class="sub_menu"></ul>
											</li>
										</ul>
									</li>
									<li><img src="${pageContext.request.contextPath}/resources/images/admin/movie/insert/personally_insert/minus_icon.png" class="minus_btn"></li>
								</ul>
							</div>
						</div>
						<div class="trailer  form_contents  multiple">
							<span class="label">트레일러</span><img src="${pageContext.request.contextPath}/resources/images/admin/movie/insert/personally_insert/plus_icon.png" class="plus_btn">
							<div>
								<ul class="trailer_info" index="0">
									<li class="trailer_title"><input type="text" name="trailer_list[0].title" placeholder='제목'></li>
									<li class="trailer_video"><input type="text" class="video" name="trailer_list[0].video"></li>
									<li style="margin-right:6px;">
										<input type="file" class="video_file" name="trailer_video_file">
										<ul class="upload_btn  video_upload_btn">
											<li>
												<span>동영상 업로드</span>
												<ul class="sub_menu"></ul>
											</li>
										</ul>
									</li>
									<li class="trailer_img"><input type="text" class="img" name="trailer_list[0].img"></li>
									<li>
										<input type="file" class="img_file" name="trailer_img_file">
										<ul class="upload_btn">
											<li>
												<span>사진 업로드</span>
												<ul class="sub_menu"></ul>
											</li>
										</ul>
									</li>
									<li><img src='${pageContext.request.contextPath}/resources/images/admin/movie/insert/personally_insert/minus_icon.png' class='minus_btn'></li>
								</ul>
							</div>
						</div>
						<div class="director  form_contents  multiple">
							<span class="label">감독</span><img src="${pageContext.request.contextPath}/resources/images/admin/movie/insert/personally_insert/plus_icon.png" class="plus_btn">
							<div>
								<ul class="director_info" index="0">
									<li class="director_ko_name"><input type="text" name="director_list[0].ko_name" placeholder="이름(한글)"></li>
									<li class="director_en_name"><input type="text" name="director_list[0].en_name" class="not_required" placeholder="이름(영문)"></li>
									<li class="director_img"><input type="text" class="img" name="director_list[0].img"></li>
									<li>
										<input type="file" class="img_file" name="director_img_file">
										<ul class="upload_btn">
											<li>
												<span>사진 업로드</span>
												<ul class="sub_menu"></ul>
											</li>
										</ul>
									</li>
									<li><img src="${pageContext.request.contextPath}/resources/images/admin/movie/insert/personally_insert/minus_icon.png" class="minus_btn"></li>
								</ul>
							</div>
						</div>
						<div class="actor  form_contents  multiple">
							<span class="label">출연진</span><img src="${pageContext.request.contextPath}/resources/images/admin/movie/insert/personally_insert/plus_icon.png" class="plus_btn">
							<div>
								<ul class="actor_info" index="0">
									<li class="actor_ko_name"><input type="text" name="actor_list[0].ko_name" placeholder="이름(한글)"></li>
									<li class="actor_en_name"><input type="text" name="actor_list[0].en_name" class="not_required" placeholder="이름(영문)"></li>
									<li class="actor_role">
										<select style="width:70px;" name="actor_list[0].role_">
											<option value="주연">주연</option>
											<option value="조연">조연</option>
											<option value="단역">단역</option>
										</select>
									</li>
									<li class="actor_img"><input type="text" class="img" name="actor_list[0].img"></li>
									<li>
										<input type="file" class="img_file" name="actor_img_file">
										<ul class="upload_btn">
											<li>
												<span>사진 업로드</span>
												<ul class="sub_menu"></ul>
											</li>
										</ul>
									</li>
									<li><img src="${pageContext.request.contextPath}/resources/images/admin/movie/insert/personally_insert/minus_icon.png" class="minus_btn"></li>
								</ul>
							</div>
						</div>
						<div class="story  form_contents">
							<span class="label">줄거리</span>
							<textarea name="story" class="not_required"></textarea>
						</div>
					</div>
					
					<div class="footer">
						<div class="register_btn"><button type="button">등록</button></div>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>