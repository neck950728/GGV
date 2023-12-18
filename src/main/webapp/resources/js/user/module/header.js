var g_memberInfo;
$.ajax({
	async:false,
	url:sessionStorage.getItem("contextPath") + "/member/getMemberInfo",
	type:"get",
	
	success:function(memberInfo){
		if(memberInfo == ""){
			g_memberInfo = null;
		}else{
			g_memberInfo = memberInfo;
		}
	}
});


$(function(){
	// menu 이벤트 처리
	$(".menu_wrap > .menu").mouseenter(function(){
		var menu_left = $(this).offset().left;
		var sub_menu_wrap = $(this).find(".sub_menu_wrap");
		sub_menu_wrap.css("left", (menu_left - 64) + "px");
		sub_menu_wrap.css("top", "128px");
		
		$(this).find(".sub_menu_wrap").css("display", "block");
	}).mouseleave(function(){
		$(this).find(".sub_menu_wrap").css("display", "none");
	});
	
	// sub_menu 이벤트 처리
	$(".sub_menu li").mouseenter(function(){
		$(this).css("background", "url('" + sessionStorage.getItem("contextPath") + "/resources/images/user/module/header/sub_menu_hand.png') no-repeat 100% 50%");
		$(this).css("cursor", "pointer");
		$(this).find("a").css("color", "red");
	}).mouseleave(function(){
		$(this).css("background", "none");
		$(this).find("a").css("color", "#fff");
	});
});


$(function(){
	$("#search > input").keydown(function(key){
		if(key.keyCode == 13){ // 13 : Enter
			$("#search > button").trigger("click");
		}
	});
	
	$("#search > button").click(function(){
		var keyword = $("#search > input").val();
		location.href = sessionStorage.getItem("contextPath") + "/movie/unifiedSearch?keyword=" + keyword;
	});
});