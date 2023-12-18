var g_phone_authNum = "";
var g_email_authNum = "";

function isFindPW(){
	if(location.pathname.indexOf("findPW") != -1){
		return true;
	}else{
		return false;
	}
}

function after_reenter(){
	$(".authNum_inputField").val("");
	$(".authNum_inputField").prop("disabled", true);
	$(".authNum_inputField").css("opacity", "0.4");
	
	$(".getAuthNum").removeClass("reenter");
	$(".getAuthNum > span").html("전송");
	
	$(".destination_inputField").val("");
	$(".destination_inputField").prop("disabled", false);
	$(".destination_inputField").css("opacity", "1");
	$(".destination_inputField").focus();
}

function after_sendAuthNum(){
	$(".destination_inputField").prop("disabled", true);
	$(".destination_inputField").css("opacity", "0.4");
	
	$(".getAuthNum").addClass("reenter");
	$(".getAuthNum > span").html("재입력");
	
	$(".authNum_inputField").prop("disabled", false);
	$(".authNum_inputField").css("opacity", "1");
	$(".authNum_inputField").focus();
}


$(function(){
	$("#content").on("click", "#buttons li", function(){
		var title_sub;
		var title_warn;
		var placeholder;
		var regExp;
		var success_msg;
		var fail_msg;
		var auth_url;
		
		if($(this).hasClass("phoneAuth")){
			title_sub = "휴대폰 번호를";
			placeholder = "'-' 문자 제외";
			regExp = /^(?:(010\d{4}))(\d{4})$/;
			success_msg = "입력하신 휴대폰 번호로 인증번호를 전송하였습니다.";
			fail_msg = "형식에 맞지 않는 번호입니다.";
			auth_url = sessionStorage.getItem("contextPath") + "/member/loginForm/findID/phoneAuth";
			
			if(isFindPW()){
				title_warn = "휴대폰 번호와";
				auth_url = sessionStorage.getItem("contextPath") + "/member/loginForm/findPW/phoneAuth";
			}
		}else if($(this).hasClass("emailAuth")){
			title_sub = "이메일을";
			placeholder = "";
			regExp = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
			success_msg = "입력하신 이메일로 인증번호를 전송하였습니다.";
			fail_msg = "올바른 이메일 주소를 입력해주세요.";
			auth_url = sessionStorage.getItem("contextPath") + "/member/loginForm/findID/emailAuth";
			
			if(isFindPW()){
				title_warn = "이메일과";
				auth_url = sessionStorage.getItem("contextPath") + "/member/loginForm/findPW/emailAuth";
			}
		}
		
		if(isFindPW()){
			$("#title").append("<p class='title-warn'>" +
										 "등록된 " + title_warn + " 일치하지 않으면<br>" +
										 "인증번호가 전송되지 않습니다." +
									 "</p>");
			
			
		}
		
		$(".title-sub").html(title_sub + " 입력해주세요.");
		$("#buttons").remove();
		
		$("#content").append("<div id='auth_area'>" +
										  "<input class='destination_inputField' type='text' placeholder=\"" + placeholder + "\">" +
										  "<button class='getAuthNum'>" +
										  	  "<span>전송</span>" +
										  "</button>" +
										  "<input class='authNum_inputField' type='text' placeholder='인증번호를 입력해주세요.' maxlength='6' disabled='disabled'>" +
										  	  "<button class='next'>" +
										  	  	  "<span>다음</span>" +
										  	  "</button>" +
									  "</div>");
		
		$(".getAuthNum").click(function(){
			if($(".getAuthNum").hasClass("reenter")){
				after_reenter();
			}else{
				var destination = $(".destination_inputField").val();
				
				if(regExp.test(destination)){
					alert(success_msg);
					
					var param;
					try{
						param = { "id":g_targetID, "destination":destination };
					}catch(e){
						param = { "destination":destination };
					}
					
					$.get(auth_url, param, function(authNum){
						g_phone_authNum = authNum;
						after_sendAuthNum();
					});
				}else{
					alert(fail_msg);
				}
			}
		});
		
		$(".next").click(function(){
			var inputted_authNum = $(".authNum_inputField").val();
			if(inputted_authNum != ""){
				if(g_phone_authNum == inputted_authNum && g_phone_authNum != -1){
					successAuth();
				}else{
					alert("인증번호가 일치하지 않습니다.");
				}
			}else{
				alert("인증번호를 입력해주세요.");
			}
		});
	});
});