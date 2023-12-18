function successAuth(){
	var destination = $(".destination_inputField").val();
	$.get(sessionStorage.getItem("contextPath") + "/member/loginForm/findID/successAuth", { "used_destination":destination }, function(foundIDList){
		if(foundIDList.length < 1){
			$(".title-sub").html("고객님의 정보와 일치하는 아이디가 존재하지 않습니다.");
			$("#auth_area").remove();
			
			$("#content").append("<img src='" + sessionStorage.getItem("contextPath") + "/resources/images/user/member/loginForm/findID_window/sorry.png' width='300px'>");
		}else{
			$(".title-sub").html("고객님의 정보와 일치하는 아이디 목록입니다.");
			$("#auth_area").remove();
			
			var div = $("<div></div>");
			div.attr("id", "foundIDList");
			
			var ul = $("<ul></ul>");
			$.each(foundIDList, function(index, id){
				ul.append("<li>" + (index + 1) + ". " + id + "</li>");
			});
			
			div.append(ul);
			
			$("#content").append(div);
		}
	});
}