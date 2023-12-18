$(function(){
	$(".search > button").click(function(){
		var keyword = $(this).siblings("input").val();
		
		if(keyword.trim().length != 0){
			var form = $("<form></form>");
			form.attr("method", "GET");
			form.attr("action", sessionStorage.getItem("contextPath") + "/member/helpCenter/" + g_type);
			
			form.append($("<input />", {type:"hidden", name:"searchKeyword", value:keyword}));
			
			form.appendTo("body");
			form.submit();
		}else{
			alert("검색어를 입력해주세요.");
		}
	});
});