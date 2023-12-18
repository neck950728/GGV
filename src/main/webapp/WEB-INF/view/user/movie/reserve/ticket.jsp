<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/user/movie/reserve/ticket.css">

	<script src="${pageContext.request.contextPath}/resources/external_js/jquery.mCustomScrollbar.concat.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/external_css/jquery.mCustomScrollbar.css">
	
	<script type="text/javascript">
		function ticket_clear(){
			ticket_theater_init();
			ticket_date_init();
			ticket_time_init();
			
			tnb_clear();
		}
		
		function ticket_theater_init(){
			$(".main_region > .on").removeClass("on");
			$(".main_region > li > em").html(0);
			
			$(".sub_region > .selected").removeClass("selected");
			$(".sub_region > .screening").removeClass("screening");
			
			$(".main_region > .seoul").trigger("click");
		}
		
		function ticket_date_init(){
			$(".date > .slider").empty();
			draw_date(null, new Date(), "none");
		}
		
		function ticket_time_init(){
			$(".time > ul").remove();
			$(".time").append("<ul></ul>");
			$(".time > ul").append("<li class='initial'>" +
											  "영화, 극장, 날짜를<br>" +
											  "선택해주세요." +
										  "</li>");
		}
		
		function tnb_clear(){
			tnb_theater_init();
			tnb_seat_init();
			tnb_payment_init();
		}
		
		function tnb_theater_init(){
			$(".tnb_theater").empty();
			$(".tnb_theater").append("<div class='placeholder'></div>");
		}
		
		function tnb_seat_init(){
			$(".tnb_seat").empty();
			$(".tnb_seat").append("<div class='placeholder'></div>");
		}
		
		function tnb_payment_init(){
			$(".tnb_payment").empty();
			$(".tnb_payment").append("<div class='placeholder'></div>");
		}
		
		
		
		var g_selected_movieCode = "${param.code}";
		var g_showingMovieList = ${showingMovieList_json};
		$(function(){
			/*
				(다른 방법)
				for(var key in g_showingMovieList) {
					console.log("key : " + key);
					console.log("value : " + g_showingMovieList[key]);
				}
			*/
			
			$.each(g_showingMovieList, function(key, value){
				switch(value.grade){
					case "전체":
						grade = "-30px 0px";
						break;
					case "12세 이상":
						grade = "-51px 0px";
						break;
					case "15세 이상":
						grade = "-72px 0px";
						break;
					case "청소년 관람불가":
						grade = "-93px -0px";
						break;
					default:
						grade = "-114px 0px";
				}
				
				var title = value.title;
				if(value.code != g_selected_movieCode){
					$(".movie > ul").append("<li code='" + value.code + "'>" +
														"<div>" +
															"<span class='grade'></span>" +
															"<span class='movie_title'>" + title + "</span>" +
														"</div>" +
													"</li>");
					
					$(".movie > ul li").last().find(".grade").css("background-position", grade);
					
					while(parseInt($(".movie > ul li").last().find(".movie_title").css("height")) > 14){ 
						title = title.substring(0, title.length - 1);
						$(".movie > ul li").last().find(".movie_title").html(title + "...");
					}
				}else{
					$(".movie > ul").prepend("<li code='" + value.code + "'>" +
														 "<div>" +
															 "<span class='grade'></span>" +
															 "<span class='movie_title'>" + title + "</span>" +
														 "</div>" +
													 "</li>");
					
					$(".movie > ul li").first().find(".grade").css("background-position", grade);
					
					while(parseInt($(".movie > ul li").first().find(".movie_title").css("height")) > 14){ 
						title = title.substring(0, title.length - 1);
						$(".movie > ul li").first().find(".movie_title").html(title + "...");
					}
				}
			});
			
			$(".movie > ul").mCustomScrollbar({
				theme:"dark-3",
				scrollInertia:250,
				mouseWheel:{
					deltaFactor:100
				}
			});
			
			// --------------------------------------------------------------------------------
			
			ticket_date_init();
		});
		
		
		$(function(){
			$("#ticket").on("click", ".movie > ul li", function(){
				if(!$(this).hasClass("selected")){
					$(".movie > ul .selected").removeClass("selected");
					$(this).addClass("selected");
					
					// --------------------------------------------------------------------------------------------
					
					ticket_clear();
					
					var code = $(this).attr("code");
					var movie = g_showingMovieList[code];
					$.each(movie.screeningInfo_list, function(index, screeningInfo){
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
						
						$(".sub_region > li[main_region='" + main_region + "']").each(function(index, element){
							// this == element
							
							var sub_region = $(this).find("span").html(); // == $(element).find("span").html();
							if(screeningInfo.sub_region == sub_region){
								if(!$(this).hasClass("screening")){
									$(this).addClass("screening");
									
									var screening_count = parseInt($(".main_region > ." + main_region + " > em").html());
									$(".main_region > ." + main_region + " > em").html(++screening_count);
								}
							}
						});
					});
					
					// --------------------------------------------------------------------------------------------
					
					$(".tnb_movie").empty();
					$(".tnb_movie").append("<img class='poster' src='" + movie.poster + "'>" +
													"<span class='title'>" + movie.title + "</span>");
					
					
					if($(".seat_select_button").hasClass("on")){
						 $(".seat_select_button").removeClass("on");
						 $(".seat_select_button").addClass("off");
					}
				}
			});
			
			$(".movie > ul li[code='" + g_selected_movieCode + "']").trigger("click");
		});
		
		
		$(function(){
			$(".theater > .main_region > li").click(function(){
				var main_region = $(this).attr("class").split(" ")[0];
				
				$(".main_region > .selected").removeClass("selected");
				$(this).addClass("selected");
				
				$(".sub_region > li[main_region='" + main_region + "']").show();
				$(".sub_region > li[main_region!='" + main_region + "']").hide(); // 다른 방법 : $(".sub_region > li").not("[main_region='" + main_region + "']").hide();
			});
			
			$("#ticket").on("click", ".theater > .sub_region > .screening", function(){
				if(!$(this).hasClass("selected")){
					var mainRegion_en = $(this).attr("main_region");
					var mainRegion_ko = $(".main_region > ." + mainRegion_en + " > i").html();
					
					$(".theater > .sub_region > .selected").removeClass("selected");
					$(".main_region > .on").removeClass("on");
					
					$(this).addClass("selected");
					$(".main_region > ." + mainRegion_en).addClass("on");
					
					// ---------------------------------------------------------------------------------------------------
					
					$(".date > .slider").empty();
					
					var screeningDate_array = [];
					var sub_region = $(this).find("span").html();
					
					var code = $(".movie > ul .selected").attr("code");
					var movie = g_showingMovieList[code];
					$.each(movie.screeningInfo_list, function(index, screeningInfo){
						if(screeningInfo.main_region == mainRegion_ko && screeningInfo.sub_region == sub_region){
							screeningDate_array.push(screeningInfo.screening_date);
						}
					});
					
					screeningDate_array.sort();
					var start_date = new Date(screeningDate_array[0]);
					var end_date = new Date(screeningDate_array[screeningDate_array.length - 1]);
					
					if(start_date.getFullYear() != end_date.getFullYear() || start_date.getMonth() != end_date.getMonth()){
						draw_date(screeningDate_array, start_date, "start_date");
						draw_date(screeningDate_array, end_date, "end_date");
					}else{
						draw_date(screeningDate_array, start_date, "none");
					}
					
					ticket_time_init();
					
					// ---------------------------------------------------------------------------------------------------
					
					$(".tnb_theater").empty();
					$(".tnb_theater").append("<div class='row  theater'>" +
														   "<h3>극장</h3>" +
														   "<span>CGV " + $(this).find("span").html() + "</span>" +
													   "</div>" +
													   "<div class='row  date'>" +
														   "<h3>일시</h3>" +
														   "<span></span>" +
													   "</div>");
					
					
					if($(".seat_select_button").hasClass("on")){
						 $(".seat_select_button").removeClass("on");
						 $(".seat_select_button").addClass("off");
					}
				}
			});
			
			$(".theater > .main_region > .seoul").trigger("click");
		});
		
		function draw_date(screeningDate_array, date, type){
			$(".date > .slider").append("<ul class='" + type + "'>" +
													"<li class='header'>" +
														"<span class='year'>" + date.getFullYear() + "</span><br>" +
														"<span class='month'>" + (date.getMonth() + 1) + "</span>" +
													"</li>" +
												"</ul>");
			
			// ----------------------------------------------------------------------------------------------------
			
			var week = new Array("일", "월", "화", "수", "목", "금", "토", "일");
			var last_day = new Date(date.getFullYear(), date.getMonth() + 1, 0); // 월 + 1, 0 : 해당 월의 마지막 날짜 구하기
			for(var i = 1; i <= last_day.getDate(); i++){
				var date_ = new Date(date.getFullYear(), date.getMonth(), i);
				var date_string = date_.getFullYear() + "." + (date_.getMonth() + 1) + "." + date_.getDate();
				
				$(".slider > ." + type).append("<li id='day' val='" + date_string + "'>" +
															"<a>" +
																"<span class='dayOfWeek'>" + week[date_.getDay()] + "</span>" +
																"<span class='day'>" + i + "</span>" +
															"</a>" +
														"</li>");
				
				if(week[date_.getDay()] == "토"){
					$(".slider > ." + type + " > #day").last().find("a").css("color", "#31597e");
				}else if(week[date_.getDay()] == "일"){
					$(".slider > ." + type + " > #day").last().find("a").css("color", "#ad2727");
				}
			}
			
			if(screeningDate_array != null){
				for(var i = 0; i < screeningDate_array.length; i++){
					var val = screeningDate_array[i];
					$(".date ul li[val='" + val + "']").addClass("selection");
				}
			}else{
				$(".date > .slider > ul .header").css("opacity", "0.3");
			}
			
			// ----------------------------------------------------------------------------------------------------
			
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
			$("#ticket").on("click", ".date .next_btn", function(){
				$(".date .slider").animate({ left:"-120px" }, 300);
			});
			
			$("#ticket").on("click", ".date .before_btn", function(){
				$(".date .slider").animate({ left:"0px" }, 300);
			});
			
			$("#ticket").on("click", ".date .selection", function(){
				if(!$(this).hasClass("selected")){
					$(".date .selected").removeClass("selected");
					$(this).addClass("selected");
					
					// -----------------------------------------------------------------------------------------------------------------------------------------
					
					$(".time > ul").remove();
					$(".time").append("<ul></ul>");
					
					var screeningInfo_array = [];
					var main_region = $(".theater > .main_region > .on > i").html();
					var sub_region = $(".theater > .sub_region > .selected > span").html();
					var screening_date = $(this).attr("val");
					
					var code = $(".movie > ul .selected").attr("code");
					var movie = g_showingMovieList[code];
					$.each(movie.screeningInfo_list, function(index, screeningInfo){
						if(screeningInfo.main_region == main_region && screeningInfo.sub_region == sub_region && screeningInfo.screening_date == screening_date){
							screeningInfo_array.push(screeningInfo);
						}
					});
					
					$.each(screeningInfo_array, function(index, screeningInfo){
						$(".time > ul").append("<li>" +
														  "<span class='screening_time'><a>" + screeningInfo.screening_time + "</a></span>" +
														  "<span class='empty_seat'>" + screeningInfo.remaining_seat_count + "석</span>" +
													  "</li>");
					});
					
					$(".time > ul").mCustomScrollbar({
						theme:"dark-3",
						scrollInertia:250,
						mouseWheel:{
							deltaFactor:100
						}
					});
					
					// -----------------------------------------------------------------------------------------------------------------------------------------
					
					$(".tnb_theater > .date > span").html($(this).attr("val") + "(" + $(this).find(".dayOfWeek").html() + ") ");
					
					
					if($(".seat_select_button").hasClass("on")){
						 $(".seat_select_button").removeClass("on");
						 $(".seat_select_button").addClass("off");
					}
				}
			});
		});
		
		
		$(function(){
			$("#ticket").on("click", ".time > ul .screening_time", function(){
				$(".time > ul .selected").removeClass("selected");
				$(this).addClass("selected");
				
				// ----------------------------------------------------------------------
				
				var date = $(".tnb_theater > .date > span").html().split(" ")[0];
				$(".tnb_theater > .date > span").html(date + " " + $(this).find("a").html());
				
				
				if($(".seat_select_button").hasClass("off")){
					$(".seat_select_button").removeClass("off");
					$(".seat_select_button").addClass("on");
				}
			});
		});
		
		
		var g_identification_code;
		const g_tnbContainer_centerAlignWrap_original_width = 946;
		const g_backButton_width = 106;
		const g_backButton_marginRight = 40;
		$(function(){
			$(".seat_select_button").click(function(){
				if($(this).hasClass("off")){
					if($(".movie > ul .selected").length == 0){
						alert("영화를 선택해주세요.");
					}else if($(".theater > .sub_region > .selected").length == 0){
						alert("극장을 선택해주세요.");
					}else if($(".date .selected").length == 0){
						alert("날짜를 선택해주세요.");
					}else if($(".time > ul .selected").length == 0){
						alert("시간을 선택해주세요.");
					}
				}else if($(this).hasClass("on")){
					var main_region = $(".theater > .main_region > .on > i").html();
					var sub_region = $(".theater > .sub_region > .selected > span").html();
					var screening_date = $(".date .selected").attr("val");
					var screening_time = $(".time .selected > a").html();
					
					var code = $(".movie > ul .selected").attr("code");
					var movie = g_showingMovieList[code];
					
					var reserved_seat;
					$.each(movie.screeningInfo_list, function(index, screeningInfo){
						if(screeningInfo.main_region == main_region && screeningInfo.sub_region == sub_region &&
						   screeningInfo.screening_date == screening_date && screeningInfo.screening_time == screening_time){
								g_identification_code = screeningInfo.identification_code;
								reserved_seat = screeningInfo.reserved_seat;
								
								return false;
						}
					});
					
					$("#ticket").css("display", "none");
					$("#container > .banner.left").after("<div id='seat_selection'>" +
																		"<div class='title'>" +
																			"<h3></h3>" +
																		"</div>" +
																		"<div class='personnel'>" +
																			"<div class='adult'>" +
																				"<span>성인</span>" +
																				"<ul></ul>" +
																				"<input type='radio' name='personnel_type' checked='checked' disabled='true' value='adult'>" +
																			"</div>" +
																			"<div class='teenager'>" +
																				"<span>청소년</span>" +
																				"<ul></ul>" +
																				"<input type='radio' name='personnel_type' disabled='true' value='teenager'>" +
																			"</div>" +
																			"<div class='child'>" +
																				"<span>어린이</span>" +
																				"<ul></ul>" +
																				"<input type='radio' name='personnel_type' disabled='true' value='child'>" +
																			"</div>" +
																		"</div>" +
																		"<div id='seat'>" +
																			"<div class='screen'>" +
																				"<span></span>" +
																			"</div>" +
																			"<div class='line'>" +
																				"<ul></ul>" +
																			"</div>" +
																			"<div class='seat'>" +
																			"</div>" +
																			"<div class='legend'>" +
																				"<div class='Economy'>" +
																					"<span class='icon'></span>" +
																					"<span class='text'>Economy Zone</span>" +
																				"</div>" +
																				"<div class='Standard'>" +
																					"<span class='icon'></span>" +
																					"<span class='text'>Standard Zone</span>" +
																				"</div>" +
																				"<div class='Prime'>" +
																					"<span class='icon'></span>" +
																					"<span class='text'>Prime Zone</span>" +
																				"</div>" +
																			"</div>" +
																		"</div>" +
																	"</div>");
					
					$("#seat_selection > .personnel > div").each(function(){
						for(var i = 0; i <= 8; i++){
							$(this).find("ul").append("<li>" +
																 "<a>" + i + "</a>" +
															 "</li>");
						}
						
						$(this).find("ul > li").first().addClass("selected");
					});
					
					switch(movie.grade){
						case "12세 관람가": case "15세 관람가":
							$(".personnel > .child").remove();
							break;
						case "청소년 관람불가":
							$(".personnel > .child").remove();
							$(".personnel > .teenager").remove();
					}
					
					
					var alphabet_charCode = 65; // 65 : A
					var zone = "Economy";
					for(var i = 0; i < 10; i++){
						var line = String.fromCharCode(alphabet_charCode + i);
						$("#seat > .line > ul").append("<li>" + line + "</li>");
						
						switch(i){
							case 3:
								zone = "Standard";
								break;
							case 6:
								zone = "Prime";
						}
						
						$("#seat > .seat").append("<ul id='" + line + "_line' class='" + zone + "'></ul>");
						for(var j = 1; j <= 10; j++){
							$("#seat > .seat > #" + line + "_line").append("<li id='no" + j + "'><a class='" + (line + j) + "'>" + j + "</a></li>");
						}
					}
					
					if(reserved_seat){
						reserved_seat = reserved_seat.replace(/ /g, "");
						reserved_seat = reserved_seat.split(",");
						for(var i = 0; i < reserved_seat.length; i++){
							$("#seat_selection > #seat ." + reserved_seat[i]).parent().addClass("reserved");
						}
					}
					
					// ----------------------------------------------------------------------------------------------------------------------------------------------------------
					
					$("#tnb_container > .center_align_wrap").css("width", (g_tnbContainer_centerAlignWrap_original_width + g_backButton_width + g_backButton_marginRight) + "px");
					$("#tnb_container > .center_align_wrap").prepend("<span class='backButton'></span>");
					
					$(".tnb_seat").empty();
					$(".tnb_seat").append("<div class='row  personnel'>" +
													   "<h3>인원</h3>" +
													   "<span>0명</span>" +
												   "</div>" +
												   "<div class='row  seat'>" +
													   "<h3>좌석</h3>" +
													   "<div></div>" +
												   "</div>");
					
					$("#tnb_container .seat_select_button").css("display", "none");
					$("#tnb_container > .center_align_wrap").append("<span class='complete_button'></span>");
				}
			});
		});
		
		
		
		$(function(){
			$("#container").on("click", "#seat_selection > .personnel > div > ul > li", function(){
				var before = $(this).parent().find(".selected");
				var before_selected_personnel = parseInt($(before).find("a").html());
				
				var after = $(this);
				var after_selected_personnel = parseInt($(after).find("a").html());
				
				if(before_selected_personnel != after_selected_personnel){
					var ageGroup = $(after).parents("div").attr("class");
					var selected_seat_count = $("#seat_selection > #seat > .seat > ul > ." + ageGroup + "_selected").length;
					if(after_selected_personnel < selected_seat_count){
						alert("선택하신 좌석수가 예매 인원보다 많습니다.");
					}else{
						before.removeClass("selected");
						after.addClass("selected");
						
						if(after_selected_personnel > 0){
							$(this).parents("." + ageGroup).find("input").attr("disabled", false);
						}else{
							$(this).parents("." + ageGroup).find("input").attr("disabled", true);
						}
					}
					
					// ----------------------------------------------------------------------------------------------------
					
					var total_personnel = 0;
					$("#seat_selection > .personnel > div").each(function(){
						total_personnel += parseInt($(this).find("ul > .selected > a").html());
					});
					
					if(total_personnel > 0){
						$("#seat_selection > #seat").css("opacity", "1");
						$(".tnb_seat > .personnel > span").html(total_personnel + "명");
					}else{
						$("#seat_selection > #seat").css("opacity", "0.4");
						$(".tnb_seat > .personnel > span").html("0명");
					}
				}
			});
		});
		
		
		var g_total_price = 0;
		$(function(){
			$("#container").on("mouseenter", "#seat_selection > #seat > .seat > ul > li:not(.reserved)", function(){
				var checked_ageGroup = $("input[name='personnel_type']:checked").val();
				if(parseInt($("#seat_selection > #seat").css("opacity")) == 1){
					$(this).css("background-color", $("#" + checked_ageGroup + "_info").attr("color"));
				}
			});
			
			$("#container").on("mouseleave", "#seat_selection > #seat > .seat > ul > li:not(.reserved)", function(){
				if(parseInt($("#seat_selection > #seat").css("opacity")) == 1){
					$(this).css("background-color", "#636363");
				}
			});
			
			
			$("#container").on("click", "#seat_selection > #seat > .seat > ul > li:not(.reserved)", function(){
				if(parseInt($("#seat_selection > #seat").css("opacity")) == 1){
					var selected_seat;
					var line;
					var num;
					line = $(this).parent().attr("id").split("_")[0];
					num = parseInt($(this).find("a").html());
					selected_seat = line + num;
					
					if($(this).attr("class") != undefined){ // 해당 좌석이 이미 선택되어 있는 경우(Unset)
						var selected_seat_ageGroup = $(this).attr("class").substring(0, $(this).attr("class").indexOf("_selected"));
						
						$(this).removeAttr("class");
						$(this).find("a").css("background-color", "");
						
						// ------------------------------------------------------------------------------------------------------
						
						$(".tnb_seat > .seat > div > span").each(function(index, element){
							if($(this).html().indexOf(selected_seat) != -1){
								if(index == 0){
									if($(".tnb_seat > .seat > div > span").eq(1).length > 0){
										var second = $(".tnb_seat > .seat > div > span").eq(1).html();
										second = second.replace(", ", "");
										$(".tnb_seat > .seat > div > span").eq(1).html(second);
									}
									$(this).remove();
								}else{
									$(this).remove();
								}
								
								return false;
							}
						});
						
						var personnel = $(".tnb_payment > ." + selected_seat_ageGroup + " > .personnel").html();
						personnel = parseInt(personnel.replace("×", ""));
						if(isNaN(personnel)){
							$(".tnb_payment > ." + selected_seat_ageGroup).remove();
						}else{
							if(personnel > 2){
								$(".tnb_payment > ." + selected_seat_ageGroup + " > .personnel").html("×" + (personnel - 1));
							}else{
								$(".tnb_payment > ." + selected_seat_ageGroup + " > .personnel").html("");
							}
						}
						
						g_total_price -= parseInt($("#" + selected_seat_ageGroup + "_info").attr("price"));
						if(g_total_price == 0){
							$(".tnb_payment > .total_price").remove();
						}else{
							var str_total_price = g_total_price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","); // 천 단위마다 콤마 추가
							$(".tnb_payment > .total_price > span").html(str_total_price + "원");
						}
					}else{ // 해당 좌석이 선택되어 있지 않은 경우(Set)
						var checked_radioBtn_ageGroup = $("input[name='personnel_type']:checked").val();
						var this_ageGroup_personnel = parseInt($("#seat_selection > .personnel > ." + checked_radioBtn_ageGroup + "> ul > .selected > a").html());
						if($("#seat > .seat > ul > ." + checked_radioBtn_ageGroup + "_selected").length < this_ageGroup_personnel){
							switch(checked_radioBtn_ageGroup){
								case "adult":
									$(this).addClass("adult_selected");
									break;
								case "teenager":
									$(this).addClass("teenager_selected");
									break;
								case "child":
									$(this).addClass("child_selected");
							}
							
							$(this).find("a").css("background-color", $("#" + checked_radioBtn_ageGroup + "_info").attr("color"));
							
							// -----------------------------------------------------------------------------------------------
							
							if($(".tnb_seat > .seat > div > span").length == 0){
								$(".tnb_seat > .seat > div").append("<span>" + selected_seat + "</span>");
							}else{
								$(".tnb_seat > .seat > div").append("<span>, " + selected_seat + "</span>");
							}
							
							if($(".tnb_payment > .placeholder").length != 0){
								$(".tnb_payment").empty();
							}
							
							var ageGroup_en = $("#" + checked_radioBtn_ageGroup + "_info").attr("ageGroup_en");
							var ageGroup_ko = $("#" + checked_radioBtn_ageGroup + "_info").attr("ageGroup_ko");
							var price = parseInt($("#" + checked_radioBtn_ageGroup + "_info").attr("price"));
							if($(".tnb_payment > ." + ageGroup_en).length != 0){
								var personnel = $(".tnb_payment > ." + ageGroup_en + " > .personnel").html();
								personnel = parseInt(personnel.replace("×", ""));
								if(isNaN(personnel)){
									$(".tnb_payment > ." + ageGroup_en + " > .personnel").html("×2");
								}else{
									$(".tnb_payment > ." + ageGroup_en + " > .personnel").html("×" + (personnel + 1));
								}
							}else{
								var str_price = price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
								$(".tnb_payment").prepend("<div class='row  " + ageGroup_en + "'>" +
																		 "<h3>" + ageGroup_ko + "</h3>" +
																		 "<span class='price'>" + str_price + "원</span>" +
																		 "<span class='personnel'></span>" +
																	 "</div>");
							}
							
							g_total_price += price;
							var str_total_price = g_total_price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
							if($(".tnb_payment > .total_price").length != 0){
								$(".tnb_payment > .total_price > span").html(str_total_price + "원");
							}else{
								$(".tnb_payment").append("<div class='row  total_price'>" +
																		"<h3>총금액</h3>" +
																		"<span>" + str_total_price + "원</span>" +
																	"</div>");
							}
						}else{
							var checked_radioBtn_ageGroup = $("input[name='personnel_type']:checked").val();
							alert($("#" + checked_radioBtn_ageGroup + "_info").attr("ageGroup_ko") + " 좌석을 이미 모두 선택하셨습니다.");
						}
					}
				}
			});
		});
		
		
		$(function(){
			$("#tnb_container").on("click", ".backButton", function(){
				$("#seat_selection").remove();
				$("#ticket").css("display", "");
				
				// -------------------------------------------------------------------------------------------------------
				
				$("#tnb_container .backButton").remove();
				$("#tnb_container > .center_align_wrap").css("width", g_tnbContainer_centerAlignWrap_original_width + "px");
				
				tnb_seat_init();
				tnb_payment_init();
				g_total_price = 0;
				
				$("#tnb_container .complete_button").remove();
				$("#tnb_container .seat_select_button").css("display", "");
			});
		});
		
		
		$(function(){
			$("#tnb_container").on("click", ".complete_button", function(){
				var total_personnel = parseInt($(".tnb_seat > .personnel > span").html().replace("명", ""));
				var selected_seat = $("#seat > .seat > ul > li[class*='selected']").length;
				
				if(total_personnel > 0 && total_personnel == selected_seat){
					var movie_code = $("#ticket > .movie .selected").attr("code");
					var poster = $(".tnb_movie > .poster").attr("src");
					var title = $(".tnb_movie > .title").html();
					var main_region = $("#ticket > .theater > .main_region > .on > i").html();
					var sub_region = $(".tnb_theater > .theater > span").html().split(" ")[1];
					var date = $("#ticket > .date .selected").attr("val");
					var detail_date = $(".tnb_theater > .date > span").html().split(" ")[0];
					var time = $(".tnb_theater > .date > span").html().split(" ")[1];
					var personnel = parseInt($(".tnb_seat > .personnel > span").html().replace("명", ""));
					
					var seat = $(".tnb_seat > .seat > div").html();
					seat = seat.replace(/\<span\>/g, "");
					seat = seat.replace(/\<\/span\>/g, "");
					
					var total_price = $(".tnb_payment > .total_price > span").html();
					
					$("body").append("<form action='${pageContext.request.contextPath}/movie/reserve/request' method='POST'></form>");
					$("form").append("<input type='hidden' name='id' value='" + g_memberInfo.id + "'>"); // g_memberInfo 선언 위치 : /resources/js/user/module/header.js
					$("form").append("<input type='hidden' name='screeningInfo_identification' value='" + g_identification_code + "'>");
					$("form").append("<input type='hidden' name='movie_code' value='" + movie_code + "'>");
					$("form").append("<input type='hidden' name='poster' value='" + poster + "'>")
					$("form").append("<input type='hidden' name='title' value='" + title + "'>");
					$("form").append("<input type='hidden' name='main_region' value='" + main_region + "'>");
					$("form").append("<input type='hidden' name='sub_region' value='" + sub_region + "'>");
					$("form").append("<input type='hidden' name='date_' value='" + date + "'>");
					$("form").append("<input type='hidden' name='detail_date' value='" + detail_date + "'>");
					$("form").append("<input type='hidden' name='time_' value='" + time + "'>");
					$("form").append("<input type='hidden' name='personnel' value='" + personnel + "'>");
					$("form").append("<input type='hidden' name='seat' value='" + seat + "'>");
					$("form").append("<input type='hidden' name='total_price' value='" + total_price + "'>");
					$("form").submit();
				}else{
					alert("좌석 선택이 완료되지 않았습니다.");
				}
			});
		});
	</script>
</head>
<body>
	<div id="container">
		<img src="${pageContext.request.contextPath}/resources/images/user/movie/reserve/ticket/banner.jpg" class="banner  left">
	
		<div id="ticket">
			<div class="movie">
				<h3 class="title">영화</h3>
				<ul></ul>
			</div>
			<div class="theater">
				<h3 class="title">극장</h3>
				<ul class='main_region'>
					<li class='seoul'>
						<i>서울</i>(<em></em>)
					</li>
					<li class='gyeonggi'>
						<i>경기</i>(<em></em>)
					</li>
					<li class='incheon'>
						<i>인천</i>(<em></em>)
					</li>
					<li class='gangwon'>
						<i>강원</i>(<em></em>)
					</li>
					<li class='daegu'>
						<i>대구</i>(<em></em>)
					</li>
					<li class='gyeongsang'>
						<i>경상</i>(<em></em>)
					</li>
				</ul>
				<ul class='sub_region'>
					<li main_region='seoul'><span>강남</span></li>
					<li main_region='seoul'><span>강변</span></li>
					<li main_region='seoul'><span>구로</span></li>
					<li main_region='seoul'><span>명동</span></li>
					<li main_region='seoul'><span>목동</span></li>
					<li main_region='gyeonggi'><span>구리</span></li>
					<li main_region='gyeonggi'><span>김포</span></li>
					<li main_region='gyeonggi'><span>동탄</span></li>
					<li main_region='incheon'><span>계양</span></li>
					<li main_region='incheon'><span>부평</span></li>
					<li main_region='incheon'><span>청라</span></li>
					<li main_region='gangwon'><span>강릉</span></li>
					<li main_region='gangwon'><span>원주</span></li>
					<li main_region='gangwon'><span>인제</span></li>
					<li main_region='gangwon'><span>춘천</span></li>
					<li main_region='daegu'><span>대구 수성</span></li>
					<li main_region='daegu'><span>대구 월성</span></li>
					<li main_region='daegu'><span>대구 칠곡</span></li>
					<li main_region='daegu'><span>대구 한일</span></li>
					<li main_region='gyeongsang'><span>거제</span></li>
					<li main_region='gyeongsang'><span>구미</span></li>
					<li main_region='gyeongsang'><span>김해</span></li>
					<li main_region='gyeongsang'><span>안동</span></li>
					<li main_region='gyeongsang'><span>창원</span></li>
					<li main_region='gyeongsang'><span>포항</span></li>
				</ul>
			</div>
			<div class="date">
				<h3 class="title">날짜</h3>
				<div class="slider">
				</div>
			</div>
			<div class="time">
				<h3 class="title">시간</h3>
				<ul>
					<li class="initial">
						영화, 극장, 날짜를<br>
						선택해주세요.
					</li>
				</ul>
			</div>
		</div>
		
		<img src="${pageContext.request.contextPath}/resources/images/user/movie/reserve/ticket/banner.jpg" class="banner  right">
	</div>
	<div id="tnb_container">
		<div class="center_align_wrap">
			<div class="tnb_movie">
				<div class="placeholder"></div>
			</div>
			<div class="tnb_theater">
				<div class="placeholder"></div>
			</div>
			<div class="tnb_seat">
				<div class="placeholder"></div>
			</div>
			<div class="tnb_payment">
				<div class="placeholder"></div>
			</div>
			
			<span class="seat_select_button  off">
			</span>
		</div>
	</div>
	
	<span id="adult_info" ageGroup_en="adult" ageGroup_ko="성인" price="8000" color="red" class="hidden"></span>
	<span id="teenager_info" ageGroup_en="teenager" ageGroup_ko="청소년" price="6000" color="blue" class="hidden"></span>
	<span id="child_info" ageGroup_en="child" ageGroup_ko="어린이" price="4000" color="green" class="hidden"></span>
</body>
</html>