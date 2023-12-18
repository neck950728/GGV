// 자동완성 기능 끄기
$(function(){
	$("input[type='text']").each(function(){
		$(this).attr("autocomplete", "off");
	});
});


// Tooltip
$(function(){
	$(".use_tooltip").focusin(function(){
		var inputField_top = parseFloat($(this).offset().top);
		var inputField_bottom = inputField_top + $(this).outerHeight();
		
		$("#" + $(this).attr("tooltip_name")).css("top", inputField_bottom + "px");
		$("#" + $(this).attr("tooltip_name")).stop().fadeIn(300);
	}).focusout(function(){
		$("#" + $(this).attr("tooltip_name")).stop().fadeOut(300);
	});
});


// 아이디, 비밀번호
$(function(){
	$(".inputField_area").focusout(function(){
		var target = $(this).parent();
		var inputted_value = $(this).find("input").val();
		var regExp;

		if(inputted_value != ""){
			if(target.hasClass("id")){
				regExp = /^[a-z]{1}[a-z0-9_]{4,11}$/;
				if(regExp.test(inputted_value)){
					// 아이디 중복확인
					$.ajax({
						/*
							async:false : 작동 방식 전환(비동기 → 동기)
							즉, Tab 키를 눌렀을 시 focusout의 코드가 전부 실행되고 나서야 비로소 focusin의 코드가 실행된다.
							
							(async:false를 사용한 이유)
							AJAX는 비동기적으로 작동하므로 AJAX의 코드보다 focusin의 코드가 먼저 실행이 완료되어버려
							Tooltip의 위치가 잘못 설정되는 경우를 방지하기 위함이다.
						*/
						async:false,
						
						url:sessionStorage.getItem("contextPath") + "/member/join/joinForm/duplicateCheck",
						type:"get",
						data:{ "id":inputted_value },
						
						success:function(result){
							if(result){
								$(".id").removeAttr("checked");
								$(".id > .check_icon_area").html("<img src='" + sessionStorage.getItem("contextPath") + "/resources/images/user/member/join/joinForm/form/impossible.png' width='35px' height='35px'>");
								$(".id > .error_msg").html("이미 사용 중인 아이디입니다.");
								$(".id > .error_msg").css("display", "block");
							 }else{
								$(".id").attr("checked", "");
								$(".id > .check_icon_area").html("<img src='" + sessionStorage.getItem("contextPath") + "/resources/images/user/member/join/joinForm/form/possible.png' width='35px' height='35px'>");
								$(".id > .error_msg").css("display", "none");
							 }
						}
					});
				}else{
					$(".id").removeAttr("checked");
					$(".id > .check_icon_area").html("<img src='" + sessionStorage.getItem("contextPath") + "/resources/images/user/member/join/joinForm/form/impossible.png' width='35px' height='35px'>");
					$(".id > .error_msg").html("사용하실 수 없는 아이디입니다.");
					$(".id > .error_msg").css("display", "block");
				}
			}else if(target.hasClass("pw")){
				regExp = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{8,16}$/;
				if(regExp.test(inputted_value)){
					target.attr("checked", "");
					$(this).siblings(".check_icon_area").html("<img src='" + sessionStorage.getItem("contextPath") + "/resources/images/user/member/join/joinForm/form/possible.png' width='35px' height='35px'>");
					
					var confirm_pw = $(".confirm_pw").find("input").val();
					if(target.hasClass("pw") && confirm_pw != ""){
						if(inputted_value == confirm_pw){
							$(".confirm_pw").attr("checked", "");
							$(".confirm_pw").find(".check_icon_area").html("<img src='" + sessionStorage.getItem("contextPath") + "/resources/images/user/member/join/joinForm/form/possible.png' width='35px' height='35px'>");
						}else{
							$(".confirm_pw").removeAttr("checked");
							$(".confirm_pw").find(".check_icon_area").html("<img src='" + sessionStorage.getItem("contextPath") + "/resources/images/user/member/join/joinForm/form/impossible.png' width='35px' height='35px'>");
						}
					}
				}else{
					target.removeAttr("checked");
					$(this).siblings(".check_icon_area").html("<img src='" + sessionStorage.getItem("contextPath") + "/resources/images/user/member/join/joinForm/form/impossible.png' width='35px' height='35px'>");
					
					$(".confirm_pw").removeAttr("checked");
					$(".confirm_pw").find(".check_icon_area").html("");
				}
			}else if(target.hasClass("confirm_pw")){
				if($(".pw").is("[checked]")){ // 속성 존재 여부 확인
					var pw = $(".pw input").val();
					
					if(inputted_value == pw){
						target.attr("checked", "");
						$(this).siblings(".check_icon_area").html("<img src='" + sessionStorage.getItem("contextPath") + "/resources/images/user/member/join/joinForm/form/possible.png' width='35px' height='35px'>");
					}else{
						target.removeAttr("checked");
						$(this).siblings(".check_icon_area").html("<img src='" + sessionStorage.getItem("contextPath") + "/resources/images/user/member/join/joinForm/form/impossible.png' width='35px' height='35px'>");
					}
				}else{
					target.removeAttr("checked");
					$(this).siblings(".check_icon_area").html("");
				}
			}
		}else{
			target.removeAttr("checked");
			$(this).siblings(".check_icon_area").html("");
			
			if(target.hasClass("id")){
				$(this).siblings(".error_msg").css("display", "none");
			}else if(target.hasClass("pw")){
				$(".confirm_pw").removeAttr("checked");
				$(".confirm_pw").find(".check_icon_area").html("");
			}
		}
	});
});


// 생년월일
$(function(){
	$("#calendar").datepicker({
		dateFormat:"yy년 mm월 dd일",
		prevText:"이전달", nextText:"다음달",
		changeMonth:true, changeYear:true,
		monthNamesShort:["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
		dayNamesMin:["일", "월", "화", "수", "목", "금", "토"],
		yearRange:"c-70:c",
		maxDate:new Date()
	});
	
	$("#calendar").focus(function(){
		$(".ui-datepicker-year > option").each(function(){
			var year = $(this).attr("value");
			$(this).html(year + "년");
		});
	});
});


// 성별
$(function(){
	$(".man_label").click(function(){
		$(this).addClass("on");
		$(".woman_label").removeClass("on");
	});
	
	$(".woman_label").click(function(){
		$(this).addClass("on");
		$(".man_label").removeClass("on");
	});
});


// 휴대폰
$(function(){
	$(".getAuthNum").click(function(){
		if($(".getAuthNum").hasClass("reenter")){
			$(".authNum_inputField").val("");
			$(".authNum_inputField").prop("disabled", true);
			$(".authNum_inputField").css("opacity", "0.4");
			
			$(".getAuthNum").removeClass("reenter");
			$(".getAuthNum > span").html("인증번호 받기");
			
			$(".phone_inputField").val("");
			$(".phone_inputField").prop("disabled", false);
			$(".phone_inputField").css("opacity", "1");
			$(".phone_inputField").focus();
		}else{
			var phone_regExp = /^(?:(010\d{4}))(\d{4})$/;
			var phone = $(".phone_inputField").val();
			
			if(phone_regExp.test(phone)){
				var identification_value = $(".identification_value").val();
				$.get(sessionStorage.getItem("contextPath") + "/member/join/joinForm/phoneAuth", { "identification_value":identification_value, "phone":phone }, function(){
					$(".phone_inputField").prop("disabled", true);
					$(".phone_inputField").css("opacity", "0.4");
					
					$(".getAuthNum").addClass("reenter");
					$(".getAuthNum > span").html("재입력");
					
					$(".authNum_inputField").prop("disabled", false);
					$(".authNum_inputField").css("opacity", "1");
					$(".authNum_inputField").focus();
				});
			}else{
				$("body").append("<div id='alert_dialog'>" +
											"<img src='" + sessionStorage.getItem("contextPath") + "/resources/images/user/member/join/joinForm/form/alert_img.png'><br><br>" +
											"<span>형식에 맞지 않는 번호입니다.</span>" +
										"</div>");
				
				$("#alert_dialog").dialog({
					open:function(){
						$(this).parents(".ui-dialog").attr("tabindex", -1)[0].focus();
						$(this).parents(".ui-dialog").find(".ui-dialog-title").html("잘못된 번호");
						$(this).parents(".ui-dialog").find(".ui-dialog-title").css({ "width":"100%", "display":"block", "text-align":"center" });
					},
					close:function(){
						$("#alert_dialog").remove();
					},
					width:330,
					height:220,
			        modal:true,
			        resizable:false,
				});
			}
		}
	});
});


// 가입 버튼
var g_prevention_many_click = 0;
$(function(){
	$(".join_btn > button").click(function(){
		if(g_prevention_many_click == 0){
			var name_regExp = /^[가-힣]{2,5}$/;
			var email_regExp = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
			var not_checked_count = 0;
			
			// 회원가입 하는 사이에 다른 사람이 동일한 아이디로 먼저 가입해버리는 불상사가 발생할 수도 있으니 한 번 더 검사
			$(".id > .inputField_area").trigger("focusout");
			
			if(name_regExp.test($(".name > input").val())){
				$(".name").attr("checked", "");
			}else{
				$(".name").removeAttr("checked");
			}
			
			if($(".birth > input").val() != ""){
				$(".birth").attr("checked", "");
			}else{
				$(".birth").removeAttr("checked");
			}
			
			if($("input:radio[name=gender]").is(":checked")){ // 체크 여부 확인
				$(".gender").attr("checked", "");
			}else{
				$(".gender").removeAttr("checked");
			}
			
			if(email_regExp.test($(".email > input").val())){
				$(".email").attr("checked", "");
			}else{
				$(".email").removeAttr("checked");
			}
			
			if($(".phone_inputField").prop("disabled") == false){
				$(".phone").removeAttr("checked");
				$(".phone > .error_msg").html("인증이 필요합니다.");
			}else{
				var identification_value = $(".identification_value").val();
				var inputted_authNum = $(".authNum_inputField").val();
				$.ajax({
					async:false,
		        	url:sessionStorage.getItem("contextPath") + "/member/join/joinForm/phoneAuthCheck",
		        	type:"get",
		        	data:{ "identification_value":identification_value, "inputted_authNum":inputted_authNum },
		        	success:function(result){
		        		if(result){
							$(".phone").attr("checked", "");
						 }else{
							 $(".phone").removeAttr("checked");
							 $(".phone > .error_msg").html("인증번호가 일치하지 않습니다.");
						 }
		        	}
		        });
			}
			
			// -----------------------------------------------------------------------------------------
			
			$(".instantly_check").each(function(){
				if(!($(this).is("[checked]"))){
					not_checked_count++;
				}
			});
			
			$(".after_check").each(function(){
				if($(this).is("[checked]")){
					$(this).find(".error_msg").css("display", "none");
				}else{
					not_checked_count++;
					$(this).find(".error_msg").css("display", "block");
				}
			});
			
			// -----------------------------------------------------------------------------------------
			
			if(not_checked_count == 0){
				g_prevention_many_click = 1;
				
				var pw = $("input[name=pw]").val();
				$("input[name=pw]").val(sha256(pw));
				
				$(".phone_inputField").prop("disabled", false); // disabled 상태 : 값 전송 안 됨
				
				$("#joinForm").submit();
			}else{
				$(window).scrollTop(0);
			}
		}
	});
});


// 세션 속성 제거
$(function(){
	$(window).on("unload", function(){
		var identification_value = $(".identification_value").val();
		$.get(sessionStorage.getItem("contextPath") + "/member/join/joinForm/unload", { "identification_value":identification_value });
		
		var start = new Date().getTime();
		while((new Date().getTime() - start) < 100){ }
	});
});