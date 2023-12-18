$(function(){
	$("body").on("click", ".screening_info .delete_btn", function(){
		var message = "기존 상영정보입니다.\n" +
							 "정말 제거하시겠습니까?"
		
		if($(this).parent().hasClass("existing")){
			if(confirm(message)){
				$(this).parent().remove();
			}
		}else{
			$(this).parent().remove();
		}
	});
});