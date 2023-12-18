function onlyNumber(event){
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if((keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39){
		return;
	}else{
		return false;
	}
}

function removeChar(event){
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;
	if(keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39){
		return;
	}else{
		event.target.value = event.target.value.replace(/[^0-9]/g, "");
	}
}


// 자동완성 기능 끄기
$(function(){
	$("input[type='text']").each(function(){
		$(this).attr("autocomplete", "off");
	});
});

// 이미지 및 동영상 관련 input field는 직접 편집할 수 없도록 설정
$(function(){
	$(".img, .video").prop("readonly", true);
});

// 등록 가능한 확장자 제한
$(function(){
	$("input[class='img_file']").each(function(){
		$(this).attr("accept", ".jpg, .png, .gif, .bmp, .tif");
	});
	
	$("input[class='video_file']").each(function(){
		$(this).attr("accept", ".mp4, .avi, .wmv");
	});
});


// premier
$(function(){
	$(".premier > input").datepicker({
		dateFormat:"yy.mm.dd",
		prevText:"이전달", nextText:"다음달",
		changeMonth:true, changeYear:true,
		monthNamesShort:["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
		yearRange:"c-15:c+5",
		dayNamesMin:["일", "월", "화", "수", "목", "금", "토"]
	});
	
	$(".premier > input").focus(function(){
		$(".ui-datepicker-year > option").each(function(){
			var year = $(this).attr("value");
			$(this).html(year + "년");
		});
	});
});


// upload_btn
$(function(){
	$(".body").on("click", ".upload_btn > li > span", function(){
		if($(this).siblings(".sub_menu").find("li").length == 0){
			if($(this).parents(".upload_btn").hasClass("video_upload_btn")){
				$(this).siblings(".sub_menu").append("<li class='url_register'><span>URL 등록</span></li>" + 
																  "<li class='video_register'><span>동영상 등록</span></li>");
			}else{
				$(this).siblings(".sub_menu").append("<li class='url_register'><span>URL 등록</span></li>" + 
																  "<li class='img_register'><span>사진 등록</span></li>");
			}
		}
	}).on("mouseleave", ".upload_btn", function(){
		$(this).find(".sub_menu").empty();
	});
});

// upload_btn's sub_menu
$(function(){
	$(".body").on("mouseenter", ".upload_btn .sub_menu > li", function(){
		$(this).css("background-color", "#6b6b6b");
	}).on("mouseleave", ".upload_btn .sub_menu > li", function(){
		$(this).css("background-color", "#484848");
	});
	
	$(".body").on("click", ".upload_btn .sub_menu > li", function(){
		var parents = $(this).parents(".form_contents");
		var benchmark;
		if(parents.hasClass("multiple")){
			var type = parents.attr("class").split("  ")[0];
			benchmark = $(this).parents("." + type + "_info");
		}else{
			benchmark = parents;
		}
		
		if($(this).hasClass("img_register")){
			benchmark.find("input[class='img_file']").trigger("click");
		}else if($(this).hasClass("video_register")){
			benchmark.find("input[class='video_file']").trigger("click");
		}else{
			var target;
			if($(this).parents(".upload_btn").hasClass("video_upload_btn")){
				target = benchmark.find(".video").attr("name");
			}else{
				target = benchmark.find(".img").attr("name");
			}
			
			$("body").append("<div class='url_input_dialog_bg'></div>" +
									"<div class='url_input_dialog' target='" + target + "'>" +
										"<button class='close_btn'></button>" +
										"<div>" +
											"<span>URL</span><br>" +
											"<input type='text'><br>" +
											"<button class='register_btn'>등록</button>" +
										"</div>" +
									"</div>");
			
			$(".url_input_dialog > div > input").focus();
		}
	});
});


// url_input_dialog
$(function(){
	$(document).on("click", ".url_input_dialog > .close_btn", function(){
		$(".url_input_dialog_bg").remove();
		$(".url_input_dialog").remove();
	});
	
	$(document).on("keydown", ".url_input_dialog > div > input", function(key){
		if(key.keyCode == 13){ // 13 : Enter
			$(".url_input_dialog > div > .register_btn").trigger("click");
		}
	});
	
	$(document).on("click", ".url_input_dialog > div > .register_btn", function(){
		var url = $(".url_input_dialog > div > input").val();
		
		var target = $(".url_input_dialog").attr("target");
		$("input[name='" + target + "']").val(url);
		
		$(".url_input_dialog_bg").remove();
		$(".url_input_dialog").remove();
	});
});


// input[type='file']
$(function(){
	$(".body").on("change", "input[type='file']", function(){
		var filePath = $(this).val();
		var beginIndex = filePath.lastIndexOf("\\") + "\\".length;
		var fileName = filePath.substr(beginIndex);
		
		var parents = $(this).parents(".form_contents");
		var benchmark;
		if(parents.hasClass("multiple")){
			var type = parents.attr("class").split("  ")[0];
			benchmark = $(this).parents("." + type + "_info");
		}else{
			benchmark = parents;
		}
		
		if($(this).hasClass("video_file")){
			benchmark.find(".video").val(fileName);
		}else{
			benchmark.find(".img").val(fileName);
		}
	});
});


//plus & minus button
$(function(){
	$(".plus_btn").click(function(){
		var parent = $(this).parent();
		var type;
		var index = parseInt(parent.find("div > ul").last().attr("index")) + 1;
		
		if(parent.hasClass("stillCut")){
			type = ".stillCut";
			add_stillCut(index, "stillCut_info")
		}else if(parent.hasClass("trailer")){	
			type = ".trailer";
			add_trailer(index, "trailer_info");
		}else if(parent.hasClass("director")){
			type = ".director";
			add_director(index, "director_info");
		}else if(parent.hasClass("actor")){
			type = ".actor";
			add_actor(index, "actor_info");
		}
		
		if($(type + " > div > ul").length > 4){
			$(type + " > div").css({ "max-height":"160px", "overflow-y":"auto" });
		}
	});
	
	
	$(".body").on("click", ".minus_btn", function(){
		var type = "." + $(this).parents(".form_contents").attr("class").split("  ")[0];
		
		if($(type + " > div > ul").length == 1){
			alert("하나 이상은 반드시 입력해야 합니다.");
		}else{
			$(this).parents(type + "_info").remove();
			if($(type + " > div > ul").length <= 4){
				$(type + " > div").css({ "max-height":"", "overflow-y":"" });
			}
		}
	});
});

function add_stillCut(index, class_){
	if(class_.indexOf("existing") != -1){
		$(".stillCut > div").append("<ul class='" + class_ + "' index='" + index + "'>" +
			   "<li class='stillCut'><input type='text' class='img' name='stillCut_list[" + index + "].stillCut'></li>" +
			   "<li><img src='" + sessionStorage.getItem("contextPath") + "/resources/images/admin/movie/insert/personally_insert/minus_icon.png' class='minus_btn'></li>" +
		   "</ul>");
	}else{
		$(".stillCut > div").append("<ul class='" + class_ + "' index='" + index + "'>" +
												"<li class='stillCut'><input type='text' class='img' name='stillCut_list[" + index + "].stillCut'></li>" +
												"<li>" +
													"<input type='file' class='img_file' name='stillCut_file'>" +
													"<ul class='upload_btn'>" +
														"<li>" +
															"<span>사진 업로드</span>" +
															"<ul class='sub_menu'></ul>" +
														"</li>" +
													"</ul>" +
												"</li>" +
												"<li><img src='" + sessionStorage.getItem("contextPath") + "/resources/images/admin/movie/insert/personally_insert/minus_icon.png' class='minus_btn'></li>" +
											"</ul>");
	}
}

function add_trailer(index, class_){
	$(".trailer > div").append("<ul class='" + class_ + "' index='" + index + "'>" +
										  "<li class='trailer_title'><input type='text' name='trailer_list[" + index + "].title' placeholder='제목'></li>" +
										  "<li class='trailer_video'><input type='text' class='video' name='trailer_list[" + index + "].video'></li>" +
										  "<li style='margin-right:6px;'>" +
											  "<input type='file' class='video_file' name='trailer_video_file'>" +
											  "<ul class='upload_btn  video_upload_btn'>" +
												  "<li>" +
													  "<span>동영상 업로드</span>" +
													  "<ul class='sub_menu'></ul>" +
												  "</li>" +
											  "</ul>" +
										  "</li>" +
										  "<li class='trailer_img'><input type='text' class='img' name='trailer_list[" + index + "].img'></li>" +
										  "<li>" +
											  "<input type='file' class='img_file' name='trailer_img_file'>" +
											  "<ul class='upload_btn'>" +
												  "<li>" +
													  "<span>사진 업로드</span>" +
													  "<ul class='sub_menu'></ul>" +
												  "</li>" +
											  "</ul>" +
										  "</li>" +
										  "<li><img src='" + sessionStorage.getItem("contextPath") + "/resources/images/admin/movie/insert/personally_insert/minus_icon.png' class='minus_btn'></li>" +
									  "</ul>");
}

function add_director(index, class_){
	$(".director > div").append("<ul class='" + class_ + "' index='" + index + "'>" +
											 "<li class='director_ko_name'><input type='text' name='director_list[" + index + "].ko_name' placeholder='이름(한글)'></li>" +
											 "<li class='director_en_name'><input type='text' name='director_list[" + index + "].en_name' class='not_required' placeholder='이름(영문)'></li>" +
											 "<li class='director_img'><input type='text' class='img' name='director_list[" + index + "].img'></li>" +
											 "<li>" +
												 "<input type='file' class='img_file' name='director_img_file'>" +
												 "<ul class='upload_btn'>" +
													 "<li>" +
														 "<span>사진 업로드</span>" +
														 "<ul class='sub_menu'></ul>" +
													 "</li>" +
												 "</ul>" +
											 "</li>" +
											 "<li><img src='" + sessionStorage.getItem("contextPath") + "/resources/images/admin/movie/insert/personally_insert/minus_icon.png' class='minus_btn'></li>" +
										 "</ul>");
}

function add_actor(index, class_){
	$(".actor > div").append("<ul class='" + class_ + "' index='" + index + "'>" +
										  "<li class='actor_ko_name'><input type='text' name='actor_list[" + index + "].ko_name' placeholder='이름(한글)'></li>" +
										  "<li class='actor_en_name'><input type='text' name='actor_list[" + index + "].en_name' class='not_required' placeholder='이름(영문)'></li>" +
										  "<li class='actor_role'>" +
										  	  "<select style='width:70px;' name='actor_list[" + index + "].role_'>" +
										  	  	  "<option value='주연'>주연</option>" +
										  	  	  "<option value='조연'>조연</option>" +
										  	  	  "<option value='단연'>단역</option>" +
										  	  "</select>" +
										  "</li>" +
										  "<li class='actor_img'><input type='text' class='img' name='actor_list[" + index + "].img'></li>" +
										  "<li>" +
										  	  "<input type='file' class='img_file' name='actor_img_file'>" +
										  	  "<ul class='upload_btn'>" +
										  	  	  "<li>" +
										  	  	  	  "<span>사진 업로드</span>" +
										  	  	  	  "<ul class='sub_menu'></ul>" +
										  	  	  "</li>" +
										  	  "</ul>" +
										  "</li>" +
										  "<li><img src='" + sessionStorage.getItem("contextPath") + "/resources/images/admin/movie/insert/personally_insert/minus_icon.png' class='minus_btn'></li>" +
									  "</ul>");
}


// register_btn
$(function(){
	$(".register_btn > button").click(function(){
		var not_inputted_count = 0;

		$(".form_contents input[type='text'], .form_contents textarea").each(function(){
			if(!$(this).hasClass("not_required")){
				if($(this).val() == ""){
					$(this).css("border", "2px solid #ff3b3b");
					not_inputted_count++;
				}else{
					$(this).css("border", "1px solid #a5a5a5");
				}
			}
		});
		
		if(not_inputted_count > 0){
			$(window).scrollTop(0);
		}else{
			create_screeningInfo_window();
		}
	});
});

var start_date;
var end_date;
function create_screeningInfo_window(){
	$("body").append("<div class='screening_info_bg'></div>" +
							"<div class='screening_info'>" +
								"<h1 class='title'>상영정보</h1>" +
								"<button class='close_btn'>닫기</button>" +
								"<div class='theater'>" +
									"<h3 class='title'>극장</h3>" +
									"<ul class='main_region'>" +
										"<li class='seoul'>서울</li>" +
										"<li class='gyeonggi'>경기</li>" +
										"<li class='incheon'>인천</li>" +
										"<li class='gangwon'>강원</li>" +
										"<li class='daegu'>대구</li>" +
										"<li class='gyeongsang'>경상</li>" +
									"</ul>" +
									"<ul class='sub_region'>" +
										"<li main_region='seoul'><span>강남</span></li>" +
										"<li main_region='seoul'><span>강변</span></li>" +
										"<li main_region='seoul'><span>구로</span></li>" +
										"<li main_region='seoul'><span>명동</span></li>" +
										"<li main_region='seoul'><span>목동</span></li>" +
										"<li main_region='gyeonggi'><span>구리</span></li>" +
										"<li main_region='gyeonggi'><span>김포</span></li>" +
										"<li main_region='gyeonggi'><span>동탄</span></li>" +
										"<li main_region='incheon'><span>계양</span></li>" +
										"<li main_region='incheon'><span>부평</span></li>" +
										"<li main_region='incheon'><span>청라</span></li>" +
										"<li main_region='gangwon'><span>강릉</span></li>" +
										"<li main_region='gangwon'><span>원주</span></li>" +
										"<li main_region='gangwon'><span>인제</span></li>" +
										"<li main_region='gangwon'><span>춘천</span></li>" +
										"<li main_region='daegu'><span>대구 수성</span></li>" +
										"<li main_region='daegu'><span>대구 월성</span></li>" +
										"<li main_region='daegu'><span>대구 칠곡</span></li>" +
										"<li main_region='daegu'><span>대구 한일</span></li>" +
										"<li main_region='gyeongsang'><span>거제</span></li>" +
										"<li main_region='gyeongsang'><span>구미</span></li>" +
										"<li main_region='gyeongsang'><span>김해</span></li>" +
										"<li main_region='gyeongsang'><span>안동</span></li>" +
										"<li main_region='gyeongsang'><span>창원</span></li>" +
										"<li main_region='gyeongsang'><span>포항</span></li>" +
									"</ul>" +
								"</div>" +
								"<div class='date'>" +
									"<h3 class='title'>날짜</h3>" +
									"<div class='slider'>" +
									"</div>" +
								"</div>" +
								"<div class='time'>" +
									"<h3 class='title'>시간</h3>" +
									"<div class='timePicker'>" +
										"<div class='hour'>" +
											"<button class='hour_up_button up_button'></button>" +
											"<input type='text' maxlength='2' onpaste='javascript:return false' onfocusout='removeChar(event)' onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;' readonly='readonly'>" +
											"<button class='hour_down_button down_button'></button>" +
										"</div>" +
										"<div class='minute'>" +
											"<button class='minute_up_button up_button'></button>" +
											"<input type='text' maxlength='2' onpaste='javascript:return false' onfocusout='removeChar(event)' onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;' readonly='readonly'>" +
											"<button class='minute_down_button down_button'></button>" +
										"</div>" +
										"<div class='timeslot'>" +
											"<button class='timeslot_up_button up_button'></button>" +
											"<input type='text' class='timeslot_inputField' value='AM' readonly='readonly'>" +
											"<button class='timeslot_down_button down_button'></button>" +
										"</div>" +
									"</div>" +
								"</div>" +
								"<div class='selected_list'>" +
									"<ul></ul>" +
									"<button class='jump_btn'>건너뛰기</button>" +
									"<button class='complete_btn'>선택 완료</button>" +
								"</div>" +
								"<button class='add_btn'>추가</button>" +
							"</div>");

	$(".theater > .main_region > .seoul").trigger("click");
	
	// 공식 사이트 : http://manos.malihu.gr/jquery-custom-content-scroller/#get-started-section
	$(".selected_list > ul").mCustomScrollbar({
		theme:"light-3",
		scrollInertia:250, // 마우스 휠 스크롤 시 부드러운 정도
		mouseWheel:{
		deltaFactor:50 // 마우스 휠 스크롤 시 움직이는 정도(단위 : 픽셀)
		}
	});
	
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/*
		- JavaScript Date 객체 생성자 -
		new Date();
		new Date(milliseconds);
		new Date(dateString); // ex) new Date("2020.01.10 20:30");
		new Date(year, month [, day, hour, minute, second, milliseconds]); // ex) new Date(2020, 0, 10);  →  0(1월) ~ 11(12월)
	*/
	
	var today = new Date();
	today.setHours(0); today.setMinutes(0); today.setSeconds(0); today.setMilliseconds(0);
	
	var split_premier = $("input[name='premier']").val().split(".");
	var premier = new Date(split_premier[0], parseInt(split_premier[1]) - 1, split_premier[2]); // 0(1월) ~ 11(12월)
	
	// 상영 가능 기간 설정
	if(today.getTime() >= premier.getTime()){
		start_date = new Date(today.getTime());
		end_date = new Date(today.getTime());
		end_date.setDate(end_date.getDate() + 7);
	}else{
		start_date = new Date(premier.getTime());
		end_date = new Date(premier.getTime());
		end_date.setDate(end_date.getDate() + 7);
	}
	
	if(start_date.getFullYear() != end_date.getFullYear() || start_date.getMonth() != end_date.getMonth()){
		draw_date(start_date, "start_date");
		draw_date(end_date, "end_date");
	}else{
		draw_date(start_date, "none");
	}
	
	// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	if(window.location.pathname == sessionStorage.getItem("contextPath") + "/admin/movie/modify"){
		// 기존 상영정보 세팅
		var movie_info = jQuery.parseJSON($("#movie_info").val());
		$.each(movie_info.screeningInfo_list, function(index, screeningInfo){
			var main_region;
			switch(screeningInfo.main_region){
				case "서울":
					main_region = "seoul";
					break;
				case "경기":
					main_region = "gyeonggi";
					break;
				case "인천":
					main_region = "incheon";
					break;
				case "강원":
					main_region = "gangwon";
					break;
				case "대구":
					main_region = "daegu";
					break;
				case "경상":
					main_region = "gyeongsang";	
			}
			
			var week = new Array("일", "월", "화", "수", "목", "금", "토", "일");
			var screening_date = new Date(screeningInfo.screening_date);
			
			var date = screeningInfo.screening_date + "(" + week[screening_date.getDay()] + ")";
			var reserve_related_data = "";
			if(screeningInfo.reserved_seat != undefined){
				reserve_related_data = "<span class='reserved_seat' style='display:none;'>" + screeningInfo.reserved_seat + "</span>" +
												"<span class='remaining_seat_count' style='display:none;'>" + screeningInfo.remaining_seat_count + "</span>";
			}
			
			$(".selected_list > ul .mCSB_container").append("<li class='existing'>" +
																				 "<span class='identification_code' style='display:none;'>" + screeningInfo.identification_code + "</span>" +
																				 "<span class='theater' main_region='" + main_region + "'>GGV" + screeningInfo.sub_region + "</span> &nbsp;" +
																				 "<span class='date'>" + date + "</span> &nbsp;" +
																				 "<span class='time'>" + screeningInfo.screening_time + "</span> &nbsp;" +
																				 reserve_related_data +
																				 "<button class='delete_btn'></button>" +
																			 "</li>");
		});
	}
}

function draw_date(param, type){
	$(".date > .slider").append("<ul class='" + type + "'>" +
											"<li class='header'>" +
												"<span class='year'>" + param.getFullYear() + "</span><br>" +
												"<span class='month'>" + (param.getMonth() + 1) + "</span>" +
											"</li>" +
										"</ul>");
	
	// ------------------------------------------------------------------------------------------------------
	
	var week = new Array("일", "월", "화", "수", "목", "금", "토", "일");
	var last_day = new Date(param.getFullYear(), param.getMonth() + 1, 0); // 월 + 1, 0 : 해당 월의 마지막 날짜 구하기
	for(var i = 1; i <= last_day.getDate(); i++){
		var date = new Date(param.getFullYear(), param.getMonth(), i);
		
		$(".slider > ." + type).append("<li id='day'>" +
													"<a>" +
														"<span class='dayOfWeek'>" + week[date.getDay()] + "</span>" +
														"<span class='day'>" + i + "</span>" +
													"</a>" +
												"</li>");
		
		if(type == "start_date"){
			if(i >= param.getDate()){
				$(".slider > ." + type + " > #day").last().addClass("selection");
			}
		}else if(type == "end_date"){
			if(i < param.getDate()){
				$(".slider > ." + type + " > #day").last().addClass("selection");
			}
		}else{
			if(i >= param.getDate() && i < param.getDate() + 7){
				$(".slider > ul > #day").last().addClass("selection");
			}
		}
		
		if(week[date.getDay()] == "토"){
			$(".slider > ." + type + " > #day").last().find("a").css("color", "#31597e");
		}else if(week[date.getDay()] == "일"){
			$(".slider > ." + type + " > #day").last().find("a").css("color", "#ad2727");
		}
	}
	
	// ------------------------------------------------------------------------------------------------------
	
	$(".slider > ." + type).mCustomScrollbar({
		theme:"dark-3",
		scrollInertia:250,
		mouseWheel:{
			deltaFactor:100
		}
	});
	
	if(type == "start_date"){
		$(".slider > ." + type + " .header").append("<button class='next_btn'>다음</button>");
		$(".slider >." + type + " .mCSB_inside > .mCSB_container").css("margin-right", "22px");
	}else if(type == "end_date"){
		$(".slider > ." + type + " .header").append("<button class='before_btn'>이전</button>");
		$(".slider > ." + type + " .mCSB_inside > .mCSB_container").css("margin", "0px");
	}else{
		$(".slider > ul .mCSB_inside > .mCSB_container").css("margin-right", "5px");
	}
}

$(function(){
	$("body").on("click", ".theater > .main_region > li", function(){
		var main_region = $(this).attr("class").split(" ")[0];
		
		$(".main_region > .selected").removeClass("selected");
		$(this).addClass("selected");
		
		$(".sub_region > li[main_region='" + main_region + "']").show();
		$(".sub_region > li[main_region!='" + main_region + "']").hide(); // 다른 방법 : $(".sub_region > li").not("[main_region='" + main_region + "']").hide();
	});
});

$(function(){
	$("body").on("click", ".theater > .sub_region > li", function(){
		var main_region = $(this).attr("main_region");
		
		if($(this).hasClass("selected")){
			$(this).removeClass("selected");
			$(".main_region > ." + main_region).removeClass("on");
		}else{
			$(".theater > .sub_region > .selected").removeClass("selected");
			$(".main_region > .on").removeClass("on");
			
			$(this).addClass("selected");
			$(".main_region > ." + main_region).addClass("on");
		}
		
		if($(".theater > .sub_region > .selected").length == 0){
			$(".date .selected").removeClass("selected");
			
			$(".timePicker input").not(".timeslot_inputField").val("");
			$(".timePicker input").prop("readonly", true);
		}
	});
});

$(function(){
	$("body").on("click", ".date .next_btn", function(){
		$(".date .slider").animate({ left:"-120px" }, 300);
	});
	
	$("body").on("click", ".date .before_btn", function(){
		$(".date .slider").animate({ left:"0px" }, 300);
	});
	
	$("body").on("click", ".date .selection", function(){
		if($(".theater > .sub_region > .selected").length > 0){
			if($(this).hasClass("selected")){
				$(this).removeClass("selected");
			}else{
				$(".date .selected").removeClass("selected");
				$(this).addClass("selected");
			}
			
			if($(".date .selected").length > 0){
				$(".timePicker input").prop("readonly", false);
			}else{
				$(".timePicker input").not(".timeslot_inputField").val("");
				$(".timePicker input").prop("readonly", true);
			}
		}else{
			alert("극장을 선택해주세요.");
		}
	});
});

$(function(){
	$("body").on("click", ".timePicker button", function(){
		if($(".date .selected").length > 0){
			var parent = $(this).parent().attr("class");
			var button_type = $(this).attr("class").split(" ")[1];
			
			if(button_type == "up_button"){
				if(parent == "hour"){
					var hour = parseInt($(".hour > input").val()) + 1;
					if(hour > 12 || isNaN(hour)){ // 값이 NaN인 경우 : 아무것도 입력되지 않았을 때.
						$(".hour > input").val("1");
					}else if(hour <= 12){
						$(".hour > input").val(hour);
					}
				}else if(parent == "minute"){
					var minute = parseInt($(".minute > input").val()) + 1;
					if(minute > 59 || isNaN(minute)){
						$(".minute > input").val("0");
					}else if(minute <= 59){
						$(".minute > input").val(minute);
					}else{
						$(".minute > input").val("0");
					}
				}else if(parent == "timeslot"){
					var timeslot = $(".timeslot > input").val();
					if(timeslot == "AM"){
						$(".timeslot > input").val("PM");
					}else{
						$(".timeslot > input").val("AM");
					}
				}
			}else if(button_type == "down_button"){
				if(parent == "hour"){
					var hour = parseInt($(".hour > input").val()) - 1;
					if(hour < 1 || isNaN(hour)){
						$(".hour > input").val("12");
					}else if(hour >= 1){
						$(".hour > input").val(hour);
					}
				}else if(parent == "minute"){
					var minute = parseInt($(".minute > input").val()) - 1;
					if(minute < 0 || isNaN(minute)){
						$(".minute > input").val("59");
					}else if(minute >= 0){
						$(".minute > input").val(minute);
					}
				}else if(parent == "timeslot"){
					$(".timeslot > .up_button").trigger("click");
				}
			}
		}else{
			alert("날짜를 선택해주세요.");
		}
	});
	
	$("body").on("keyup", ".timePicker input", function(){
		var parent = $(this).parent().attr("class");
		var num = parseInt($(this).val());
		
		if(parent == "hour"){
			if(num < 1){
				$(this).val("1");
			}else if(num > 12){
				$(this).val("12");
			}
		}else if(parent == "minute"){
			if(num > 59){
				$(this).val("59");
			}
		}
	});
});

$(function(){
	$("body").on("click", ".screening_info > .add_btn", function(){
		var theater_selected_count = $(".theater > .sub_region > .selected").length;
		var date_selected_count = $(".date .selected").length;
		var hour = $(".time .hour > input").val();
		var minute = $(".time .minute > input").val();
		
		if(theater_selected_count == 0 || date_selected_count == 0 || hour == "" || minute == ""){
			alert("선택되지 않은 항목이 있습니다.");
		}else{
			var year = $(".date .selected").siblings(".header").find(".year").html();
			var month = $(".date .selected").siblings(".header").find(".month").html();
			var day = $(".date .selected .day").html();
			var dayOfWeek = $(".date .selected .dayOfWeek").html();
			var date = year + "." + month + "." + day + "(" + dayOfWeek + ")";
			
			if($(".time .timeslot > input").val() == "PM"){
				hour = parseInt(hour) + 12;
			}
			
			if(new Date(year, month - 1, day, hour, minute).getTime() < new Date().getTime()){
				alert("과거를 상영 기간으로 선택할 수 없습니다.");
			}else{
				var theater = $(".theater > .sub_region > .selected > span").html();
				var main_region = $(".theater > .sub_region > .selected").attr("main_region");
				
				if(parseInt(hour) < 10){ hour = "0" + hour; }
				if(parseInt(minute) < 10){ minute = "0" + minute; }
				var time = hour + ":" + minute;
				
				$(".selected_list > ul .mCSB_container").append("<li>" +
																					 "<span class='theater' main_region='" + main_region + "'>GGV" + theater + "</span> &nbsp;" +
																					 "<span class='date'>" + date + "</span> &nbsp;" +
																					 "<span class='time'>" + time + "</span> &nbsp;" +
																					 "<button class='delete_btn'></button>" +
																				 "</li>");
				
				// -----------------------------------------------------------------------------------------------------------------------------------------
				
				$(".theater > .sub_region > .selected").removeClass("selected");
				$(".main_region > .on").removeClass("on");
				
				$(".date .selected").removeClass("selected");
				
				$(".timePicker input").not(".timeslot_inputField").val("");
				$(".timePicker input").prop("readonly", true);
				
				
				$(".theater > .main_region > .seoul").trigger("click");
				$(".date > .slider > ul").mCustomScrollbar("scrollTo", "top", { scrollInertia:0 });
				if($(".date .slider").css("left") == "-120px"){ $(".date .slider").animate({ left:"0px" }, 0); }
			}
		}
	});
});

$(function(){
	$("body").on("click", ".screening_info .jump_btn", function(){
		if(confirm("건너뛰시겠습니까?\n(건너뛸 시 기존 상영정보가 모두 제거됩니다.)")){
			$("form").submit();
		}
	});
	
	$(function(){
		$("body").on("click", ".screening_info .complete_btn", function(){
			if($(".selected_list > ul .mCSB_container > li").length > 0){
				var grade = $("select[name='grade'] > option:selected").val();
				if(grade != "미정"){
					var error_count = 0;
					$(".selected_list > ul .mCSB_container > li").each(function(index){
						var date = $(this).find(".date").html();
						date = date.substring(0, date.indexOf("("));
						var time = $(this).find(".time").html();
						
						if(new Date(date + " " + time).getTime() < new Date().getTime()){
							error_count++;
							return false;
						}
					});
					
					if(error_count == 0){
						if(confirm("등록하시겠습니까?")){
							$(".selected_list > ul .mCSB_container > li").each(function(index){
								var identification_code = $(this).find(".identification_code").html();
								var main_region = $(this).find(".theater").attr("main_region");
								var sub_region = $(this).find(".theater").html().substring(3);
								var date = $(this).find(".date").html();
								date = date.substring(0, date.indexOf("("));
								var time = $(this).find(".time").html();
								var reserved_seat = $(this).find(".reserved_seat").html();
								var remaining_seat_count = parseInt($(this).find(".remaining_seat_count").html());
								
								switch(main_region){
									case "seoul":
										main_region = "서울";
										break;
									case "gyeonggi":
										main_region = "경기";
										break;
									case "incheon":
										main_region = "인천";
										break;
									case "gangwon":
										main_region = "강원";
										break;
									case "daegu":
										main_region = "대구";
										break;
									case "gyeongsang":
										main_region = "경상";
								}
								
								$("form").append("<input type='hidden' name='screeningInfo_list[" + index + "].main_region' value='" + main_region + "'>");
								$("form").append("<input type='hidden' name='screeningInfo_list[" + index + "].sub_region' value='" + sub_region + "'>");
								$("form").append("<input type='hidden' name='screeningInfo_list[" + index + "].screening_date' value='" + date + "'>");
								$("form").append("<input type='hidden' name='screeningInfo_list[" + index + "].screening_time' value='" + time + "'>");
								if($(this).hasClass("existing")){
									$("form").append("<input type='hidden' name='screeningInfo_list[" + index + "].identification_code' value='" + identification_code + "'>");
									
									if(reserved_seat != undefined){
										$("form").append("<input type='hidden' name='screeningInfo_list[" + index + "].reserved_seat' value='" + reserved_seat + "'>");
										$("form").append("<input type='hidden' name='screeningInfo_list[" + index + "].remaining_seat_count' value='" + remaining_seat_count + "'>");
									}
								}
							});
							
							$("form").submit();
						}
					}else{
						alert("과거를 상영 기간으로 선택할 수 없습니다.");
					}
				}else{
					alert("시청 등급을 선택해주세요.");
				}
			}else{
				alert("상영정보를 선택해주세요.");
			}
		});
	});
});

$(function(){
	$("body").on("click", ".screening_info > .close_btn", function(){
		$(".screening_info_bg").remove();
		$(".screening_info").remove();
	});
});