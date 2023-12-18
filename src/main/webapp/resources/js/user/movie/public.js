// like 버튼 이벤트 처리
$(function(){
	$(".like > button").click(function(){
		var id = (g_memberInfo != null) ? g_memberInfo.id : null;
		var code = $(this).attr("code");
		var button = $(this);
		var count = $(this).siblings(".count").find("span");
		
		if($(this).hasClass("on")){
			$.get(sessionStorage.getItem("contextPath") + "/member/wishList/deleteWishList", { "id":id, "movie_code":code }, function(){
				button.removeClass("on");
				count.html(parseInt(count.html()) - 1);
				alert("위시리스트에서 제거되었습니다.");
			});
		}else{
			$.ajax({
				url:sessionStorage.getItem("contextPath") + "/member/wishList/insertWishList",
				type:"get",
				data:{ "id":id, "movie_code":code },
				beforeSend:function(xhr){
					xhr.setRequestHeader("loginInterceptor", "confirm");
				},
				
				success:function(){
					button.addClass("on");
					count.html(parseInt(count.html()) + 1);
				}
			});
		}
	});
});