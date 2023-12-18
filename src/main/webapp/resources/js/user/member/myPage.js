var g_email_authNum = "";
var g_phone_authNum = "";

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

function setting(list, type, default_content_count){
	$("#show > .detail").empty();
	$("#show > .detail").append("<ul class='" + type + "'></ul>");
	
	if(list.length == 0){
		var ko_type;
		switch(type){
			case "wishList":
				ko_type = "위시리스트가";
				break;
			case "watchedMovies":
				ko_type = "내가 본 영화가";
				break;
			case "reserveList":
				ko_type = "예매 내역이";
				break;
			case "paymentList":
				ko_type = "구매 내역이";
		}
		
		var height = parseInt($("#show > .side_menu").css("height"));
		$("#show > .detail > ." + type).append("<span class='none' style='height:" + height + "px;'>" + ko_type + " 존재하지 않습니다.</span>");
	}else{
		if(list.length > default_content_count){
			$("#show > .detail").append("<button class='" + type + "_viewMore_btn'>" +
													   "<span>더 보기</span>" +
												   "</button>");
			
			$("." + type + "_viewMore_btn").click(function(){
				// https://blog.naver.com/dngu_icdi/221908450625 참고
				$("#show > .detail > ." + type + " > li:nth-child(n+" + (default_content_count + 1) + ")").show();
				
				$(this).remove();
			});
		}
	}
}

function after_remove(data, target, type, default_content_count){
	var length;
	switch(type){
		case "wishList":
			// 배열 원소 제거 방법 : https://love2dev.com/blog/javascript-remove-from-array 참고
			g_memberInfo.wishList = g_memberInfo.wishList.filter(function(value, index, ary){
				return data.movie_code != value.movie_code;
			});
			
			length = g_memberInfo.wishList.length;
			break;
		case "watchedMovies":
			g_watchedMovies = g_watchedMovies.filter(function(value, index, ary){
				return data.reserve_code != value.reserve_code;
			});
			
			length = g_watchedMovies.length;
			break;
		case "reserveList":
			g_reserveList = g_reserveList.filter(function(value, index, ary){
				return data.reserve_code != value.reserve_code;
			});
			
			length = g_reserveList.length;
			break;
		case "paymentList":
			g_paymentList = g_paymentList.filter(function(value, index, ary){
				return !(data.order_code == value.order_code && data.item == value.item);
			});
			
			length = g_paymentList.length;
	}
	
	target.remove();
	
	if(length > 0){
		$("#show > .detail > ." + type + " > li:nth-child(" + default_content_count + ")").show();
		if($("#show > .detail > ." + type + " > li").length <= default_content_count){ $("." + type + "_viewMore_btn").remove(); }
	}else{
		setting(new Array(0), type, default_content_count);
	}
	
	var list_count = parseInt($("#show > .side_menu ." + type + "_button > em").html());
	$("#show > .side_menu ." + type + "_button > em").html(--list_count);
}


$(function(){
	// g_memberInfo 선언 위치 : /resources/js/user/module/header.js
	$("#my_info > .profile > .right .name").prepend(g_memberInfo.name);
	$("#my_info > .profile > .right .id").html("(" + g_memberInfo.id + ")");
	
	$("#show > .side_menu .simple_profile > .name > span").html(g_memberInfo.name + "님");
	
	$("#show > .side_menu .wishList_button > em").html(g_memberInfo.wishList.length);
	$("#show > .side_menu .watchedMovies_button > em").html(g_watchedMovies.length);
	$("#show > .side_menu .reserveList_button > em").html(g_reserveList.length);
	$("#show > .side_menu .paymentList_button > em").html(g_paymentList.length);
});

$(function(){
	$("#my_info > .other li").click(function(){
		if($(this).hasClass("wishList")){
			$("#show > .side_menu .wishList_button").trigger("click");
		}else if($(this).hasClass("watchedMovies")){
			$("#show > .side_menu .watchedMovies_button").trigger("click");
		}else if($(this).hasClass("reserveList")){
			$("#show > .side_menu .reserveList_button").trigger("click");
		}else if($(this).hasClass("paymentList")){
			$("#show > .side_menu .paymentList_button").trigger("click");
		}
	});
});

$(function(){
	$("#show > .side_menu .button").click(function(){
		if(!$(this).hasClass("on")){
			$("#show > .side_menu .button.on").removeClass("on");
			$(this).addClass("on");
			
			if($(this).hasClass("wishList_button")){
				const DEFAULT_CONTENT_COUNT = 8;
				setting(g_memberInfo.wishList, "wishList", DEFAULT_CONTENT_COUNT);
				
				$.each(g_memberInfo.wishList, function(index, value){
					$.ajax({
						async:false,
			        	url:sessionStorage.getItem("contextPath") + "/member/myPage/selectMovieInfoForMyPage",
			        	type:"get",
			        	data:{ "movie_code":value.movie_code },
			        	
			        	success:function(data){
			        		var title = data.title;
							if(title.length > 12){ title = title.substring(0, 12) + "..."; }
							
							$("#show > .detail > .wishList").append("<li>" +
																					  "<img src='" + data.poster + "' class='poster'>" +
																					  "<i class='delete_button'></i>" +
																					  "<h1>" + title + "</h1>" +
																					  "<span>" + data.premier + " 개봉</span>" +
																				  "</li>");
							
							$(".wishList > li").last().find(".delete_button").click(function(){
								var target = $(this).parent();
								$.get(sessionStorage.getItem("contextPath") + "/member/wishList/deleteWishList", { "id":value.id, "movie_code":value.movie_code }, function(){
									after_remove(value, target, "wishList", DEFAULT_CONTENT_COUNT);
								});
							});
			        	}
			        });
				});
				
				$("#show > .detail > .wishList > li:nth-child(n+" + (DEFAULT_CONTENT_COUNT + 1) + ")").hide();
			}else if($(this).hasClass("watchedMovies_button")){
				const DEFAULT_CONTENT_COUNT = 3;
				setting(g_watchedMovies, "watchedMovies", DEFAULT_CONTENT_COUNT);
				
				$.each(g_watchedMovies, function(index, value){
					$.ajax({
						async:false,
			        	url:sessionStorage.getItem("contextPath") + "/member/myPage/selectMovieInfoForMyPage",
			        	type:"get",
			        	data:{ "movie_code":value.movie_code },
			        	
			        	success:function(data){
			        		$("#show > .detail > .watchedMovies").append("<li>" +
			        																			"<div class='poster'>" +
			        																				"<img src='" + data.poster + "'>" +
			        																				"<i class='delete_button'></i>" +
			        																			"</div>" +
			        																			"<div class='boxing'>" +
			        																				"<h1>" + data.title + "</h1>" +
			        																				"<span>" +
			        																					value.date_ + " " + value.time_ + "<br>" +
			        																					"CGV " + value.sub_region + " / " + value.personnel + "명" +
			        																				"</span><br>" +
			        																				"<a href='" + sessionStorage.getItem("contextPath") + "/movie/detail/comment?mID=" + value.movie_code + "' class='go_write'>" +
			        																					"<span>평점/리뷰 작성하러 가기</span>" +
			        																				"</a>" +
			        																			"</div>" +
			        																		"</li>");
			        		
			        		$(".watchedMovies > li").last().find(".delete_button").click(function(){
								if(confirm("목록에서 제거하시겠습니까?")){
									var target = $(this).parents("li");
									$.get(sessionStorage.getItem("contextPath") + "/member/myPage/deleteWatchedMovies", { "reserve_code":value.reserve_code }, function(){
										after_remove(value, target, "watchedMovies", DEFAULT_CONTENT_COUNT);
									});
								}
							});
			        	}
			        });
				});
				
				$("#show > .detail > .watchedMovies > li:nth-child(n+" + (DEFAULT_CONTENT_COUNT + 1) + ")").hide();
			}else if($(this).hasClass("reserveList_button")){
				const DEFAULT_CONTENT_COUNT = 3;
				setting(g_reserveList, "reserveList", DEFAULT_CONTENT_COUNT);
				
				$.each(g_reserveList, function(index, value){
					var week = new Array("일", "월", "화", "수", "목", "금", "토", "일");
					var date_ = new Date(value.date_);
					
					$("#show > .detail > .reserveList").append("<li>" +
																				   "<img src='" + value.poster + "'>" +
																				   "<div class='boxing'>" +
																					   "<h1>" + value.title + "</h1>" +
																					   "<span>" +
																						   value.date_ + "(" + week[date_.getDay()] + ") " + value.time_ + "<br>" +
																						   "CGV " + value.sub_region + " / " + value.personnel + "명(" + value.seat + ")" +
																					   "</span><br>" +
																					   "<button class='delete_button'>" +
																						   "<span>예매 취소</span>" +
																					   "</button>" +
																				   "</div>" +
																			   "</li>");
					
					$(".reserveList > li").last().find(".delete_button").click(function(){
						if(confirm("예매를 취소하시겠습니까?")){
							var target = $(this).parents("li");
							$.post(sessionStorage.getItem("contextPath") + "/movie/reserve/cancel", value, function(){
								after_remove(value, target, "reserveList", DEFAULT_CONTENT_COUNT);
							});
						}
					});
				});
				
				$("#show > .detail > .reserveList > li:nth-child(n+" + (DEFAULT_CONTENT_COUNT + 1) + ")").hide();
			}else if($(this).hasClass("paymentList_button")){
				const DEFAULT_CONTENT_COUNT = 3;
				setting(g_paymentList, "paymentList", DEFAULT_CONTENT_COUNT);
				
				$.each(g_paymentList, function(index, value){
					var item = find_item(value.item);
					$("#show > .detail > .paymentList").append("<li>" +
																				   "<img src='" + item.img + "'>" +
																				   "<div class='boxing'>" +
																				   	   "<h1>" + item.name_ko + "</h1>" +
																				   	   "<h3>주문 번호</h3>" +
																				   	   "<span>" + value.order_code + "</span><br>" +
																				   	   "<h3>결제 금액</h3>" +
																				   	   "<span>" + thousandUnits_comma(parseInt_comma(value.price) * value.count) + "원(" + value.count + "개)</span><br>" +
																				   	   "<button class='delete_button'>" +
																				   	   	   "<span>구매 취소</span>" +
																				   	   "</button>" +
																				   "</div>" +
																			   "</li>");
					
					$(".paymentList > li").last().find(".delete_button").click(function(){
						if(confirm("구매를 취소하시겠습니까?")){
							var target = $(this).parents("li");
							$.post(sessionStorage.getItem("contextPath") + "/member/culture/cart/payment/cancel", value, function(){
								after_remove(value, target, "paymentList", DEFAULT_CONTENT_COUNT);
							});
						}
					});
				});
				
				$("#show > .detail > .paymentList > li:nth-child(n+" + (DEFAULT_CONTENT_COUNT + 1) + ")").hide();
			}
		}
	});
	
	// default setting
	switch(g_type){
		case "wishList":
			$("#show > .side_menu .wishList_button").trigger("click");
			break;
		case "watchedMovies":
			$("#show > .side_menu .watchedMovies_button").trigger("click");
			break;
		case "reserveList":
			$("#show > .side_menu .reserveList_button").trigger("click");
			break;
		case "paymentList":
			$("#show > .side_menu .paymentList_button").trigger("click");
			break;
		default:
			$("#show > .side_menu .wishList_button").trigger("click");
	}
});

$(function(){
	$(".changeInfo").click(function(){
		var gender;
		if(g_memberInfo.gender == "남"){
			gender = "<label><input type='radio' name='gender' value='남' checked='checked'>남자</label> &nbsp;" +
						 "<label><input type='radio' name='gender' value='여'>여자</label>";
		}else{
			gender = "<label><input type='radio' name='gender' value='남'>남자</label> &nbsp;" +
						 "<label><input type='radio' name='gender' value='여' checked='checked'>여자</label>";
		}
		
		var phone = g_memberInfo.phone.replace(/010/g, '');
		
		$("#show > .side_menu .button.on").removeClass("on");
		
		$("#show > .detail").empty();
		$("#show > .detail").append("<div class='change_info'>" +
												   "<form id='changeInfo_form'>" +
													   "<div class='header'>" +
														   "<h3>기본 정보</h3>" +
													   "</div>" +
													   "<div class='body'>" +
														   "<table>" +
															    "<tr class='id'>" +
																   "<th>아이디</th>" +
																   "<td>" + g_memberInfo.id + "</td>" +
																   "<input type='hidden' name='id' value='" + g_memberInfo.id + "'>" +
															   "</tr>" +
															   "<tr class='name'>" +
																   "<th>이름</th>" +
																   "<td><input type='text' name='name' value='" + g_memberInfo.name + "'></td>" +
															   "</tr>" +
															   "<tr class='pw'>" +
																   "<th>비밀번호</th>" +
																   "<td>" +
																	   "<input type='password' name='pw'>" +
																	   "<span class='error_msg'></span>" +
																   "</td>" +
																   "<td>" +
																	   "<input type='password' name='confirm_pw'>" +
																	   "<span class='error_msg'></span>" +
																   "</td>" +
															   "</tr>" +
															   "<tr class='birth'>" +
																   "<th>생년월일</th>" +
																   "<td><input type='text' name='birth' value='" + g_memberInfo.birth + "' readonly='readonly'></td>" +
															   "</tr>" +
															   "<tr class='gender'>" +
																   "<th>성별</th>" +
																   "<td>" +
																	   gender +
																   "</td>" +
															   "</tr>" +
															   "<tr class='email'>" +
																   "<th>이메일</th>" +
																   "<td>" +
																	   "<input type='text' name='email' value='" + g_memberInfo.email + "'>" +
																	   "&nbsp; <button type='button'>인증</button>" +
																	   "<span class='error_msg'></span>" +
																   "</td>" +
															   "</tr>" +
															   "<tr class='phone'>" +
																   "<th>휴대폰</th>" +
																   "<td>" +
																	   "<input type='text' name='phone01' value='010' disabled>" +
																	   " - <input type='text' name='phone02' value='" + phone.substring(0, 4) + "' maxlength='4' onpaste='javascript:return false' onfocusout='removeChar(event)' onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;'>" +
																	   " - <input type='text' name='phone03' value='" + phone.substring(4) + "' maxlength='4' onpaste='javascript:return false' onfocusout='removeChar(event)' onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' style='ime-mode:disabled;'>" +
																	   "&nbsp; <button type='button'>인증</button>" +
																	   "<span class='error_msg'></span>" +
																   "</td>" +
															   "</tr>" +
														   "</table>" +
													   "</div>" +
													   "<div class='footer'>" +
														   "<button type='button' class='modify_btn'><span>수정</span></button>" +
													   "</div>" +
												   "</form>" +
											   "</div>");
		
		$(".birth input").datepicker({
			dateFormat:"yy년 mm월 dd일",
			prevText:"이전달", nextText:"다음달",
			changeMonth:true, changeYear:true,
			monthNamesShort:["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
			dayNamesMin:["일", "월", "화", "수", "목", "금", "토"],
			yearRange:"c-70:c",
			maxDate:new Date()
		});
		
		$(".birth input").focus(function(){
			$(".ui-datepicker-year > option").each(function(){
				var year = $(this).attr("value");
				$(this).html(year + "년");
			});
		});
		
		
		$(".email button").click(function(){
			if($(".email input").val() == g_memberInfo.email){
				alert("기존의 이메일과 동일하여 인증받으실 필요가 없습니다.");
			}else{
				$(".email button").remove();
				$(".email input").prop("readonly", true);
				$(".email > td > .error_msg").before("<input type='text' name='emailAuth' class='auth'>");
				
				alert("인증번호를 전송하였습니다.");
				
				var email = $(".email input").eq(0).val();
				$.get(sessionStorage.getItem("contextPath") + "/member/myPage/changeInfo/emailAuth", { "email":email }, function(authNum){
					g_email_authNum = authNum;
				});
			}
			
			$(".email .error_msg").html("");
		});
		
		
		$(".phone button").click(function(){
			var phone = $(".phone input[name='phone01']").val() + $(".phone input[name='phone02']").val() + $(".phone input[name='phone03']").val();
			
			if(phone == g_memberInfo.phone){
				alert("기존의 휴대폰 번호와 동일하여 인증받으실 필요가 없습니다.");
			}else{
				$(".phone button").remove();
				$(".phone input").prop("readonly", true);
				$(".phone > td > .error_msg").before("<input type='text' name='phoneAuth' class='auth'>");
				
				alert("인증번호를 전송하였습니다.");
				
				$.get(sessionStorage.getItem("contextPath") + "/member/myPage/changeInfo/phoneAuth", { "phone":phone }, function(authNum){
					g_phone_authNum = authNum;
				});
			}
			
			$(".phone .error_msg").html("");
		});
		
		
		$(".change_info .modify_btn").click(function(){
			var pw = $(".pw input[name='pw']").val();
			if(pw != ""){
				var pw_regExp = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{8,16}$/;
				if(pw_regExp.test(pw)){
					$(".pw input[name='pw']").siblings(".error_msg").html("");
				}else{
					$(".pw input[name='pw']").siblings(".error_msg").html("사용하실 수 없는 비밀번호입니다.");
					return false;
				}
			}else{
				$(".pw input[name='pw']").siblings(".error_msg").html("");
			}
			
			var confirm_pw = $(".pw input[name='confirm_pw']").val();
			if(confirm_pw != "" || pw != ""){
				var pw = $(".pw input[name='pw']").val();
				if(confirm_pw == pw){
					$(".pw input[name='confirm_pw']").siblings(".error_msg").html("");
				}else{
					$(".pw input[name='confirm_pw']").siblings(".error_msg").html("비밀번호가 일치하지 않습니다.");
					return false;
				}
			}else{
				$(".pw input[name='confirm_pw']").siblings(".error_msg").html("");
			}
			
			
			if($(".email input").val() != g_memberInfo.email){
				if($(".email input[name='emailAuth']").length > 0){
					var email_authNum = $(".email input[name='emailAuth']").val();
					if(email_authNum == g_email_authNum){
						$(".email .error_msg").html("");
					}else{
						$(".email .error_msg").html("인증번호가 일치하지 않습니다.");
						return false;
					}
				}else{
					$(".email .error_msg").html("이메일 인증이 필요합니다.");
					return false;
				}
			}
			
			
			var phone = $(".phone input[name='phone01']").val() + $(".phone input[name='phone02']").val() + $(".phone input[name='phone03']").val();
			if(phone != g_memberInfo.phone){
				if($(".phone input[name='phoneAuth']").length > 0){
					var phone_authNum = $(".phone input[name='phoneAuth']").val();
					if(phone_authNum == g_phone_authNum){
						$(".phone input[name='phoneAuth']").siblings(".error_msg").html("");
					}else{
						$(".phone input[name='phoneAuth']").siblings(".error_msg").html("인증번호가 일치하지 않습니다.");
						return false;
					}
				}else{
					$(".phone .error_msg").html("휴대폰 인증이 필요합니다.");
					return false;
				}
			}
			
			$(".change_info .phone").append("<input type='hidden' name='phone' value='" + phone + "'>");
			
			
			$(".pw input[name='pw']").val(sha256(pw));
			
			$.post(sessionStorage.getItem("contextPath") + "/member/myPage/changeInfo/request", $("#changeInfo_form").serialize(), function(){
				alert("정보가 변경되었습니다.");
				location.reload();
			});
		});
	});
});