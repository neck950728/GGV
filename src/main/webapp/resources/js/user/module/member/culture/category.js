var g_cartCount = 0;
if(g_memberInfo != null){
	$.ajax({
		async:false,
		url:sessionStorage.getItem("contextPath") + "/member/culture/cart/getCartCount",
		type:"get",
		data:{ "id":g_memberInfo.id },
		
		success:function(count){
			g_cartCount = count;
		}
	});
}

$(function(){
	if(g_type != ""){
		$("#category .category > ." + g_type).css("border-bottom", "3px solid black");
		$("#category .category > ." + g_type + " > a").css("color", "black");
	}
	
	$(".cart_count").html(g_cartCount);
});