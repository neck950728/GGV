$(function(){
	$.get(sessionStorage.getItem("contextPath") + "/resources/json/user/member/culture.json", { }, function(json){
		$.each(json.product_list, function(index, product){
			if(g_type == product.type_en){ // g_type 선언 위치 : /WEB-INF/view/user/module/member/culture/category.jspf
				$("#title > span").before(product.type_ko);
				$("#title > span").html(product.title);
				
				product.item.forEach(function(item){
					$("#item_list").append("<li item='" + item.name_en + "'>" +
													   "<div class='img'>" +
													   	   "<img src='" + sessionStorage.getItem("contextPath") + item.img + "'>" +
													   	   "<ul class='buttons'>" +
													   	   	   "<li><a class='cart'>장바구니</a></li>" +
													   	   	   "<li><a class='payment'>구매</a></li>" +
													   	   "</ul>" +
													   "</div>" +
													   "<div class='txt'>" +
													   	   "<h3 class='name'>" + item.name_ko + "</h3>" +
													   	   "<p class='composition'>" + item.composition + "</p>" +
													   	   "<p class='price'>" + item.price + "</p>" +
													   "</div>" +
												   "</li>");
				});
				
				return false;
			}
		});
	});
});

$(function(){
	$("#item_list").on("click", "> li", function(e){
		if(!$(e.target).is(".cart")){
			location.href = sessionStorage.getItem("contextPath") + "/member/culture/detail?type=" + g_type + "&item=" + $(this).attr("item");
		}
	});
	
	$("#item_list").on("mouseenter", "> li", function(){
		$(this).find(".buttons").css("display", "block");
	}).on("mouseleave", "> li", function(){
		$(this).find(".buttons").css("display", "none");
	});
});