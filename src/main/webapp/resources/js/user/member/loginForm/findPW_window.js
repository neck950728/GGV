var g_targetID = "";

$(function(){
	$("#checkID_area > button").click(function(){
		var id = $("#checkID_area > input").val();
		if(id.trim() != ""){
			$.ajax({
	        	url:sessionStorage.getItem("contextPath") + "/member/loginForm/findPW/idIsExist",
	        	type:"get",
	        	data:{ "id":id },
	        	
	        	success:function(result){
	        		if(result){
	        			g_targetID = id;
	        			
	        			$(".title-sub").html("본인 확인 방법을 선택해주세요.");
	        			$("#checkID_area").remove();
	        			
	        			$("#content").append("<div id='buttons'>" +
	        											  "<ul>" +
	        											  	  "<li class='phoneAuth'>" +
	        											  	  	  "<button>" +
	        											  	  	  	  "<span>등록된 휴대폰으로 찾기</span>" +
	        											  	  	  "</button>" +
	        											  	  "</li>" +
	        											  	  "<li class='emailAuth'>" +
	        											  	  	  "<button>" +
	        											  	  	  	  "<span>등록된 이메일로 찾기</span>" +
	        											  	  	  "</button>" +
	        												  "</li>" +
	        											  "</ul>" +
	        										  "</div>");
	        		}else{
	        			alert("존재하지 않는 아이디입니다.");
	        		}
	        	}
	        });
		}else{
			alert("아이디를 입력해주세요.");
		}
	});
});

function successAuth(){
	$(".title-main").html("비밀번호 재설정");
	$(".title-sub").html("새로운 비밀번호를 입력해주세요.");
	$(".title-warn").remove();
	$("#auth_area").remove();
	
	$("#content").append("<div id='setNewPW_area'>" +
									  "<label for='pw'>비밀번호</label> <input type='password' id='pw'><br>" +
									  "<label for='checkPW'>비밀번호 확인</label> <input type='password' id='checkPW'><br>" +
									  "<button>" +
									  	  "<span>변경</span>" +
									  "</button>" +
								  "</div>");
	
	$("#setNewPW_area > button").click(function(){
		var pw = $("#setNewPW_area > #pw").val();
		var checkPW = $("#setNewPW_area > #checkPW").val();
		
		if(pw == checkPW){
			regExp = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{8,16}$/;
			if(regExp.test(pw)){
				$.ajax({
		        	url:sessionStorage.getItem("contextPath") + "/member/loginForm/findPW/updatePassword",
		        	type:"post",
		        	data:{ "id":g_targetID, "pw":sha256(pw) },
		        	
		        	success:function(){
		        		$(".title-main").html("변경 성공");
		        		$(".title-sub").html("비밀번호가 성공적으로 변경되었습니다.");
		        		$("#setNewPW_area").remove();
		        		
		        		$("#content").append("<img src='" + sessionStorage.getItem("contextPath") + "/resources/images/user/member/loginForm/findPW_window/complete.png' width='220px'>");
		        	}
		        });
			}else{
				alert("형식에 맞지 않는 비밀번호입니다.");
			}
		}else{
			alert("비밀번호가 일치하지 않습니다.");
		}
	});
}