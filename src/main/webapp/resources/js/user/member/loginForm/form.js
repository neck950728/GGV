$(function(){
	$("#loginForm").submit(function(){
		var id = $(".id").val();
		var pw = $(".pw").val();
		var id_regExp = /^[a-z]{1}[a-z0-9_]{4,11}$/;
		var pw_regExp = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{8,16}$/;
		
		if(id == "" || pw == ""){
			$(".error_msg > span").html("아이디 및 비밀번호를 모두 입력해주세요.");
			$(".error_msg").css("display", "block");
			return false;
		}else{
			if(!(id_regExp.test(id) && pw_regExp.test(pw))){
				$(".error_msg > span").html("아이디 또는 비밀번호가 일치하지 않습니다.");
				$(".error_msg").css("display", "block");
				return false;
			}
		}
		
		var isSame = false;
		$.ajax({
			async:false,
			url:sessionStorage.getItem("contextPath") + "/member/loginForm/loginCheck",
			type:"post",
			data:{ "id":id, "pw":sha256(pw) },
			
			success:function(result){
				isSame = result;
				if(!isSame){
					$(".error_msg > span").html("아이디 또는 비밀번호가 일치하지 않습니다.");
					$(".error_msg").css("display", "block");
				}
			}
		});
		
		if(!isSame){
			return false;
		}
	});
});

$(function(){
	$(".findID_btn").click(function(){
		window.open(sessionStorage.getItem("contextPath") + "/member/loginForm/findID", "findID_window", "width=500px, height=600px");
	});
	
	$(".findPW_btn").click(function(){
		window.open(sessionStorage.getItem("contextPath") + "/member/loginForm/findPW", "findPW_window", "width=500px, height=600px");
	});
});