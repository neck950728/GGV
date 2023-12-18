var g_itemPrice;

$(function(){
	var item = find_item(g_item);
	
	$("#title").html(item.name_ko);
	
	$("#content > .img").attr("src", item.img);
	
	$("#content > .content > .price").html(item.price);
	$("#content > .content .composition > dd").html(item.composition);
	$("#content > .content .expiry > dd").html(item.expiry);
	$("#content > .content .origin > dd").html(item.origin);
	
	$("#content > .content .calculate > h3").html(item.name_ko);
	$("#content > .content .calculate .buy_price").html(item.price);
	
	$("#description").html(item.description);
	
	g_itemPrice = item.price;
});

$(function(){
	$(".calculate .minus_button").click(function(){
		var count = parseInt($(".calculate .count").html());
		if(count - 1 >= 1){
			$(".calculate .count").html(--count);
			
			var buy_price = $(".calculate .buy_price").html();
			buy_price = parseInt_comma(buy_price) - parseInt_comma(g_itemPrice);
			buy_price = thousandUnits_comma(buy_price) + "원";
			$(".calculate .buy_price").html(buy_price);
		}
	});
	
	$(".calculate .plus_button").click(function(){
		var count = parseInt($(".calculate .count").html());
		if(count + 1 <= 10){
			$(".calculate .count").html(++count);
			
			var buy_price = $(".calculate .buy_price").html();
			buy_price = parseInt_comma(buy_price) + parseInt_comma(g_itemPrice);
			buy_price = thousandUnits_comma(buy_price) + "원";
			$(".calculate .buy_price").html(buy_price);
		}else{
			alert("최대 10개까지 구매 가능합니다.");
		}
	});
});