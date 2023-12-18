function insert_comment(){
	if(confirm("댓글을 등록하시겠습니까?")){
		var form = $("<form></form>");
		form.attr("method", "POST");
		form.attr("action", sessionStorage.getItem("contextPath") + "/member/helpCenter/" + g_type + "/detail/insertComment"); // g_type 선언 위치 : /WEB-INF/view/user/module/member/helpCenter/menu.jspf
		
		form.append($("<input />", {type:"hidden", name:"article_code", value:g_articleCode}));
		form.append($("<input />", {type:"hidden", name:"comment_", value:$("#comment_input > textarea").val()}));
		
		form.appendTo("body");
		form.submit();
	}
}

var update_comment_identificationCode;
function update_comment(identification_code){
	update_comment_identificationCode = identification_code;
}

$(function(){
	$(".comment .update").click(function(){
		var comment = $(this).parents(".comment").find("p");
		comment.replaceWith("<textarea class='update_comment_input'>" + comment.html() + "</textarea>");
		
		$(this).replaceWith("<span class='update_submit'>수정</span>");
		$(".update_submit").css({"border":"1px solid #d4d4d4", "background-color":"#e71a0f", "color":"white"});
	});
	
	$(".comment").on("click", ".update_submit", function(){
		if(confirm("댓글을 수정하시겠습니까?")){
			var form = $("<form></form>");
			form.attr("method", "POST");
			form.attr("action", sessionStorage.getItem("contextPath") + "/member/helpCenter/" + g_type + "/detail/updateComment");
			
			form.append($("<input />", {type:"hidden", name:"article_code", value:g_articleCode}));
			form.append($("<input />", {type:"hidden", name:"comment_", value:$(this).parents(".comment").find("textarea").val()}));
			form.append($("<input />", {type:"hidden", name:"identification_code", value:update_comment_identificationCode}));
			
			form.appendTo("body");
			form.submit();
		}
	});
});

function delete_comment(identification_code){
	if(confirm("댓글을 삭제하시겠습니까?")){
		var form = $("<form></form>");
		form.attr("method", "POST");
		form.attr("action", sessionStorage.getItem("contextPath") + "/member/helpCenter/" + g_type + "/detail/deleteComment");
		
		form.append($("<input />", {type:"hidden", name:"article_code", value:g_articleCode}));
		form.append($("<input />", {type:"hidden", name:"identification_code", value:identification_code}));
		
		form.appendTo("body");
		form.submit();
	}
}

function delete_article(){
	if(confirm("게시글을 삭제하시겠습니까?")){
		location.href = sessionStorage.getItem("contextPath") + "/member/helpCenter/" + g_type + "/detail/delete?article_code=" + g_articleCode;
	}
}