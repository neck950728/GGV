// ==================================== ● moving_banner ● ====================================
function setPosition(start_point, end_point){
	// -------------------------------- left --------------------------------
	var search_right = $("#search").offset().left + $("#search").outerWidth();
	$("#moving_banner").css("left", (search_right + 33) + "px");
	// ---------------------------------------------------------------------
	
	// ------------------------------------------ top ------------------------------------------
	var scrollTop = $(window).scrollTop();
	var scrollBottom = scrollTop + $(window).height();
	
	if(scrollTop >= start_point){
		if(scrollBottom >= end_point){
			$("#moving_banner").css("position", "absolute");
			$("#moving_banner").css("top", (end_point - $("#moving_banner").outerHeight()) + "px");
		}else{
			$("#moving_banner").css("position", "fixed");
			$("#moving_banner").css("top", "10px");
		}
	}else{
		$("#moving_banner").css("position", "absolute");
		$("#moving_banner").css("top", start_point + "px");
	}
	// ----------------------------------------------------------------------------------------
}

$(function(){
	var im_wrap_top = parseFloat($("#im_wrap").offset().top);
	var im_wrap_bottom = im_wrap_top + $("#im_wrap").outerHeight();
	
	var moving_banner_beginning_position = im_wrap_bottom + 15;
	
	/*
		var footer_banner_top = parseFloat($("#footer_banner").offset().top);
		var footer_banner_bottom = footer_banner_top + $("#footer_banner").outerHeight();
	*/
	var footer_banner_bottom = 1457.65625; // 이유는 잘 모르겠으나 새로 고침 시 잘못된 위칫값이 나와서 고정값을 사용한 것이다.
	
	
	if($("#moving_banner").length > 0){ // 존재 여부 확인
		// - 초기 위치 설정 -
		setPosition(moving_banner_beginning_position, footer_banner_bottom);
		
		
		// - scroll 이벤트 처리 -
		var beforeScrollTop = 0;
		
		$(window).scroll(function(){
			var scrollTop = $(window).scrollTop();
			var scrollBottom = scrollTop + $(window).height();
			
			var moving_banner_top = parseFloat($("#moving_banner").offset().top);
			var moving_banner_bottom = moving_banner_top + $("#moving_banner").outerHeight();
			
			if(scrollTop > beforeScrollTop){ // scroll down
				if(scrollTop >= moving_banner_beginning_position){
					if(moving_banner_bottom >= footer_banner_bottom){
						$("#moving_banner").css("position", "absolute");
						$("#moving_banner").css("top", (footer_banner_bottom - $("#moving_banner").outerHeight()) + "px");
					}else{
						$("#moving_banner").css("position", "fixed");
						$("#moving_banner").css("top", "10px");
					}
				}
			}else if(scrollTop < beforeScrollTop){ // scroll up
				if(scrollBottom <= footer_banner_bottom || moving_banner_top - scrollTop > 10){
					if(scrollTop <= moving_banner_beginning_position){
						$("#moving_banner").css("position", "absolute");
						$("#moving_banner").css("top", moving_banner_beginning_position + "px");
					}else{
						$("#moving_banner").css("position", "fixed");
						$("#moving_banner").css("top", "10px");	
					}
				}
			}
			
			beforeScrollTop = scrollTop;
		});
		
		
		// - resize 이벤트 처리 -
		$(window).resize(function(){
			setPosition(moving_banner_beginning_position, footer_banner_bottom);
		});
		
		
		// - TOP 버튼 -
		$("#top_btn").click(function(){
			$(window).scrollTop(0); // scroll 이벤트 발생(scroll up)
		});
	}
});
//=======================================================================================
