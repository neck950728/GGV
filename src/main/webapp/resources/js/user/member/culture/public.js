function find_item(target){
	var result;
	
	$.ajax({
		async:false,
		url:sessionStorage.getItem("contextPath") + "/resources/json/user/member/culture.json",
		type:"get",
		data:{ },
		
		success:function(json){
			var isFind = false;
			$.each(json.product_list, function(index, product){
				if(isFind == true){
					return false;
				}
				
				$.each(product.item, function(index, item){
					if(item.name_en == target){
						isFind = true;
						result = { "img":sessionStorage.getItem("contextPath") + item.img, "name_ko":item.name_ko, "name_en":item.name_en, "composition":item.composition, "expiry":item.expiry, "origin":item.origin, "description":item.description, "price":item.price };
						
						return false;
					}
				});
			});
		}
	});
	
	return result;
}


$(function(){
	$("#container").on("click", ".cart", function(){
		var id = g_memberInfo != null ? g_memberInfo.id : null;
		var item = $(this).parents("[item]").attr("item"); // https://blog.naver.com/dngu_icdi/222425532222 참고
		var price = $(this).parents("[item]").find(".price").html();
		var count = $(".calculate .count").html() == undefined ? 1 : $(".calculate .count").html();
		
		$.ajax({
			url:sessionStorage.getItem("contextPath") + "/member/culture/cart/insert",
			type:"post",
			data:{ "id":id, "item":item, "price":price, "count":count },
			
			success:function(result){
				if(result > 0){
					if(confirm("장바구니에 등록되었습니다.\n장바구니로 이동하시겠습니까?")){
						location.href = sessionStorage.getItem("contextPath") + "/member/culture/cart";
					}
				}else{
					alert("최대 10개까지 구매 가능합니다.\n장바구니에 등록된 수량을 확인해주세요.");
				}
			}
		});
	});
});

$(function(){
	$("#container").on("click", ".payment", function(){
		var payment = new Object();
		
		var cart_list = new Array();
		
		var cart = new Object();
		cart.id = g_memberInfo != null ? g_memberInfo.id : null;
		cart.item = $(this).parents("[item]").attr("item");
		cart.price = $(this).parents("[item]").find(".price").html();
		var count = $(this).parents("[item]").find(".count").html();
		cart.count = count != undefined ? count : 1;
		
		cart_list.push(cart);
		
		payment.cart_list = cart_list;
		payment.payment_price = thousandUnits_comma(parseInt_comma(cart.price) * cart.count) + "원";
		
		payment_request(payment);
	});
});

function payment_request(payment){
	$.ajax({
		url:sessionStorage.getItem("contextPath") + "/member/culture/cart/payment",
		type:"post",
		contentType:"application/json",
		data:JSON.stringify(payment),
		
		success:function(result){
			var form = $("<form></form>");
			form.attr("method", "POST");
			form.attr("action", sessionStorage.getItem("contextPath") + "/member/culture/cart/payment/complete");
			
			
			form.append($("<input />", { type:"hidden", name:"order_code", value:result.order_code }));
			form.append($("<input />", { type:"hidden", name:"payment_price", value:result.payment_price }));
			
			$.each(result.cart_list, function(index, cart){
				form.append($("<input />", { type:"hidden", name:"cart_list[" + index + "].id", value:cart.id }));
				form.append($("<input />", { type:"hidden", name:"cart_list[" + index + "].item", value:cart.item }));
				form.append($("<input />", { type:"hidden", name:"cart_list[" + index + "].price", value:cart.price }));
				form.append($("<input />", { type:"hidden", name:"cart_list[" + index + "].count", value:cart.count }));
			});
			
			
			form.appendTo("body");
			form.submit();
		}
	});
}


function parseInt_comma(str){
	return parseInt(str.replace(/,/g, "")); // 콤마 제거
}

function thousandUnits_comma(int){
	return String(int).replace(/\B(?=(\d{3})+(?!\d))/g, ","); // 천 단위마다 콤마 추가
}