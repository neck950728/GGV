// main_banner
$(function(){
	// http://idangero.us/swiper/api 참고
	var swiper = new Swiper('.swiper-container', {
		effect:'fade',
		loop:true,
		autoplay:{ delay:2500, disableOnInteraction:false },
		navigation:{ nextEl:'.swiper-button-next', prevEl:'.swiper-button-prev' },
		pagination:{ el:'.swiper-pagination', clickable:true },
	});
});


// movie_selection
function getRandomMovie(){
	$.get(sessionStorage.getItem("contextPath") + "/getRandomMovie", { }, function(data){
		while(data.trailer_list.length == 0){
			getRandomMovie();
			return;
		}
		
		var trailer;
		$.each(data.trailer_list, function(index, value){
			var title = value.title;
			if(title.indexOf("예고편") != -1){
				trailer = value.video;
				return false;
			}
		});
		
		if(trailer == undefined){
			trailer = data.trailer_list[0].video;
		}
		
		$(".left > video").prop("muted", true);
		$(".left > video > source").attr("src", trailer);
		document.getElementById("video").load();
		
		$(".right > img").attr("src", data.poster);
	});
}

$(function(){
	getRandomMovie();
});


// popup
function doNotSeeToday(checkbox){
	if(checkbox.checked == true){
		$.get(sessionStorage.getItem("contextPath") + "/doNotSeeToday", { }, function(){
			$("#popup_bg").remove();
			$("#popup").remove();
		});
	}
}