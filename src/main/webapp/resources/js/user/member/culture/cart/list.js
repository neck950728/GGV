var g_total_price = 0;
// var g_sale = 0;
var g_payment_price = 0;

$(function(){
	$.each(g_cart, function(index, cart){
		var item = find_item(cart.item);
		
		var buy_price = parseInt_comma(item.price) * cart.count;
		g_total_price += buy_price;
		
		$(".cart_list > table > tbody").append("<tr item='" + item.name_en + "'>" +
															   "<td>" +
															   	   "<input type='checkbox' id='" + item.name_en + "'>" +
															   	   "<label for='" + item.name_en + "'></label>" +
															   "</td>" +
															   "<td class='name'>" +
															   	   "<img src='" + item.img + "'>" +
															   	   "<div class='txt'>" +
															   	   	   "<p>" + item.name_ko + "</p>" +
															   	   	   "<span>" + item.description + "</span>" +
															   	   "</div>" +
															   "</td>" +
															   "<td class='price'>" + item.price + "</td>" +
															   "<td class='count'>" +
															   	   "<span>" + cart.count + "</span>" +
															   	   "<a class='plus'></a>" +
															   	   "<a class='minus'></a>" +
															   "</td>" +
															   "<td class='buy_price'>" +
															   	   "<span>" + thousandUnits_comma(buy_price) + "</span>" +
															   "</td>" +
															   "<td class='delete_button'>" +
															   	   "<button type='button'></button>" +
															   "</td>" +
														   "</tr>");
	});
	
	$(".total_price").html(thousandUnits_comma(g_total_price) + "원");
	$(".payment_price").html(thousandUnits_comma(g_payment_price = g_total_price) + "원");
});

$(function(){
	$("#all_check").change(function(){
		$("input[type='checkbox']").prop("checked", this.checked);
	});
});

$(function(){
	$(".cart_list").on("click", ".count > .plus", function(){
		var count = parseInt($(this).siblings("span").html());
		if(count < 10){
			$(this).siblings("span").html(++count);
			
			var price = $(this).parent().siblings(".price").html();
			g_total_price += parseInt_comma(price);
			$(".total_price").html(thousandUnits_comma(g_total_price) + "원");
			$(".payment_price").html(thousandUnits_comma(g_payment_price = g_total_price) + "원");
		}else{
			alert("최대 10개까지 구매 가능합니다.");
		}
	});
	
	$(".cart_list").on("click", ".count > .minus", function(){
		var count = parseInt($(this).siblings("span").html());
		if(count > 1){
			$(this).siblings("span").html(--count);
			
			var price = $(this).parent().siblings(".price").html();
			g_total_price -= parseInt_comma(price);
			$(".total_price").html(thousandUnits_comma(g_total_price) + "원");
			$(".payment_price").html(thousandUnits_comma(g_payment_price = g_total_price) + "원");
		}
	});
	
	$(".cart_list").on("click", ".count > a", function(){
		var price = $(this).parent().siblings(".price").html();
		var count = parseInt($(this).siblings("span").html());
		
		var buy_price = parseInt_comma(price) * count;
		var buy_price = thousandUnits_comma(buy_price) + "원";
		$(this).parent().siblings(".buy_price").html(buy_price);
	});
});

$(function(){
	$(".cart_list").on("click", ".delete_button > button", function(){
		var item = $(this).parents("[item]").attr("item");
		
		if(confirm("선택하신 상품을 삭제하시겠습니까?")){
			$.get(sessionStorage.getItem("contextPath") + "/member/culture/cart/delete", { "id":g_memberInfo.id, "item":item }, function(){
				location.reload();
			});
		}
	});
});

$(function(){
	$(".payment_button").click(function(){
		var payment = new Object();
		
		var cart_list = new Array();
		$(".cart_list [item]").each(function(){
			var cart = new Object();
			cart.id = g_memberInfo.id;
			cart.item = $(this).attr("item");
			cart.price = $(this).find(".price").html();
			cart.count = $(this).find(".count > span").html();
			
			cart_list.push(cart);
		});
		
		payment.cart_list = cart_list;
		payment.payment_price = thousandUnits_comma(g_payment_price) + "원";
		
		payment_request(payment);
	});
});