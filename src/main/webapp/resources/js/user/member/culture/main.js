$(function(){
	$.get(sessionStorage.getItem("contextPath") + "/resources/json/user/member/culture.json", { }, function(json){
		json.product_list.forEach(function(product){
			$("#product_list > ul").append("<li class='product' type='" + product.type_en + "'>" +
														 "<div class='title'>" +
														 	 "<h1>" + product.type_ko + "</h1>" +
														 	 "<button onclick=\"location.href='" + sessionStorage.getItem("contextPath") + "/member/culture/more?type=" + product.type_en + "'\"></button>" +
														 "</div>" +
														 "<ul>" +
														 "</ul>" +
													 "</li>");
			
			$.each(product.item, function(index, item){
				if(index == 3){ return false; }
				
				$("#product_list > ul > li[type='" + product.type_en + "'] > ul").append("<li item='" + item.name_en + "'>" +
																													"<img src='" + sessionStorage.getItem("contextPath") + item.img + "'>" +
																													"<div class='txt'>" +
																														"<h3 class='name'>" + item.name_ko + "</h3>" +
																														"<p class='description'>" + item.description + "</p>" +
																														"<p class='price'>" + item.price + "</p>" +
																													"</div>" +
																													"<ul class='buttons'>" +
																														"<li><a class='cart'>장바구니</a></li>" +
																														"<li><a class='payment'>구매</a></li>" +
																													"</ul>" +
																												"</li>");
			});
		});
	});
});

$(function(){
	$("#product_list > ul").on("click", "> li > ul > li", function(event){
		if(!$(event.target).is(".cart")){
			location.href = sessionStorage.getItem("contextPath") + "/member/culture/detail?type=" + $(this).parents(".product").attr("type") + "&item=" + $(this).attr("item");
		}
	});
	
	$("#product_list > ul").on("mouseenter", "> li > ul > li", function(){
		$(this).find(".buttons").css("display", "block");
	}).on("mouseleave", "> li > ul > li", function(){
		$(this).find(".buttons").css("display", "none");
	});
});