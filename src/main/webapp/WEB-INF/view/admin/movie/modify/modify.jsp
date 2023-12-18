<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<script src="${pageContext.request.contextPath}/resources/js/admin/movie/public.js"></script>
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin/movie/insert/personally_insert.css">
	
	<script src="${pageContext.request.contextPath}/resources/js/admin/movie/modify/modify.js"></script>
	
	<script src="${pageContext.request.contextPath}/resources/external_js/jquery-ui.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/external_css/jquery-ui.css">
	
	<script src="${pageContext.request.contextPath}/resources/external_js/jquery.mCustomScrollbar.concat.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/external_css/jquery.mCustomScrollbar.css">
	
	<script type="text/javascript">
		function IfNotURLConvert(param){
			if(!param.startsWith("http")){
				var beginIndex = param.indexOf("_", param.lastIndexOf("/")) + "_".length;
				param = param.substring(beginIndex);
			}
			
			return param;
		}
	
		function basic_setting(){
			// 자동완성 기능 끄기
			$("input[type='text']").each(function(){
				$(this).attr("autocomplete", "off");
			});
			
			
			// 이미지 및 동영상 관련 input field는 직접 편집할 수 없도록 설정
			$(".img, .video").prop("readonly", true);
			
			
			// 등록 가능한 확장자 제한
			$("input[class='img_file']").each(function(){
				$(this).attr("accept", ".jpg, .png, .gif, .bmp, .tif");
			});
			
			$("input[class='video_file']").each(function(){
				$(this).attr("accept", ".mp4, .avi, .wmv");
			});
		}
		
		
		// 기존 영화 정보 세팅
		$(function(){
			var movie = ${movie_json};
			
			var grade = "미정";
			if(movie.grade != undefined){ grade = movie.grade; }
			
			var poster = IfNotURLConvert(movie.poster);
			
			$("input[name='code']").val(movie.code);
			$(".title > input").val(movie.title);
			$(".genre > input").val(movie.genre);
			$(".nation > input").val(movie.nation);
			$(".showtimes > input").val(movie.showtimes);
			$(".premier > input").val(movie.premier);
			$(".grade > select").val(grade).prop("selected", true);
			$(".poster > input[type='text']").val(poster);
			$(".story > textarea").val(movie.story);
			
			// stillCut
			if(movie.stillCut_list.length == 0){
				add_stillCut(0, "stillCut_info");
			}else{
				if(movie.stillCut_list.length > 4){
					$(".stillCut > div").css({ "max-height":"160px", "overflow-y":"auto" });
				}
				
				$.each(movie.stillCut_list, function(index, stillCut){
					add_stillCut(index, "stillCut_info  existing");
					var just_added = $(".stillCut .stillCut_info").last();
					just_added.find("input[type='text']").val(IfNotURLConvert(stillCut.stillCut));
				});
			}
			
			// trailer
			if(movie.trailer_list.length == 0){
				add_trailer(0, "trailer_info");
			}else{
				if(movie.trailer_list.length > 4){
					$(".trailer > div").css({ "max-height":"160px", "overflow-y":"auto" });
				}
				
				$.each(movie.trailer_list, function(index, trailer){
					add_trailer(index, "trailer_info  existing");
					var just_added = $(".trailer .trailer_info").last();
					just_added.find(".trailer_title > input").val(trailer.title);
					just_added.find(".trailer_video > input").val(IfNotURLConvert(trailer.video));
					just_added.find(".trailer_img > input").val(IfNotURLConvert(trailer.img));
				});
			}
			
			// director
			if(movie.director_list.length == 0){
				add_director(0, "director_info");
			}else{
				if(movie.director_list.length > 4){
					$(".director > div").css({ "max-height":"160px", "overflow-y":"auto" });
				}
				
				$.each(movie.director_list, function(index, director){
					add_director(index, "director_info  existing");
					var just_added = $(".director .director_info").last();
					just_added.find(".director_ko_name > input").val(director.ko_name);
					just_added.find(".director_en_name > input").val(director.en_name);
					just_added.find(".director_img > input").val(IfNotURLConvert(director.img));
				});
			}
			
			// actor
			if(movie.actor_list.length == 0){
				add_actor(0, "actor_info");
			}else{
				if(movie.actor_list.length > 4){
					$(".actor > div").css({ "max-height":"160px", "overflow-y":"auto" });
				}
					
				$.each(movie.actor_list, function(index, actor){
					add_actor(index, "actor_info  existing");
					var just_added = $(".actor .actor_info").last();
					just_added.find(".actor_ko_name > input").val(actor.ko_name);
					just_added.find(".actor_en_name > input").val(actor.en_name);
					just_added.find(".actor_role > select").val(actor.role_).prop("selected", true);
					just_added.find(".actor_img > input").val(IfNotURLConvert(actor.img));
				});
			}
			
			basic_setting();
		});
	</script>
</head>
<body>
	<div id="container">
		<div id="center_align_wrap">
			<form action="${pageContext.request.contextPath}/admin/movie/modify/request" enctype="multipart/form-data" method="POST">
				<div class="register_wrap">
					<div class="header">
						<h1>Movie Register</h1>
					</div>
					
					<div class="body">
						<input type="hidden" name="code">
					
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
							<div></div>
						</div>
						<div class="trailer  form_contents  multiple">
							<span class="label">트레일러</span><img src="${pageContext.request.contextPath}/resources/images/admin/movie/insert/personally_insert/plus_icon.png" class="plus_btn">
							<div></div>
						</div>
						<div class="director  form_contents  multiple">
							<span class="label">감독</span><img src="${pageContext.request.contextPath}/resources/images/admin/movie/insert/personally_insert/plus_icon.png" class="plus_btn">
							<div></div>
						</div>
						<div class="actor  form_contents  multiple">
							<span class="label">출연진</span><img src="${pageContext.request.contextPath}/resources/images/admin/movie/insert/personally_insert/plus_icon.png" class="plus_btn">
							<div></div>
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
	
	<textarea id="movie_info" style="display:none;">${movie_json}</textarea>
</body>
</html>