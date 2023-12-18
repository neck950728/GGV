$(function(){
	$(".search input").keydown(function(key){
		if(key.keyCode == 13){ // 13 : Enter
			$(".search > button").trigger("click");
		}
	});
	
	$(".search > button").click(function(){
		var keyword = $(".search input").val();
		location.href = sessionStorage.getItem("contextPath") + "/movie/unifiedSearch?keyword=" + keyword;
	});
});

$(function(){
	$(".viewMore_btn").click(function(){
		$(".viewMore_btn").hide();
		$(".movie_list > ul:nth-child(n+2)").show();
		$(".movie_list > .ul_borderBottom:nth-of-type(n+2)").show();
	});
});