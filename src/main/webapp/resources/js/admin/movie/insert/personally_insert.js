$(function(){
	$("body").on("click", ".screening_info .delete_btn", function(){
		$(this).parent().remove();
	});
});