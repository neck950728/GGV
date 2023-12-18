function uploadSummernoteImageFile(file, el){
	var formData = new FormData();
	formData.append("file", file);
	
	$.ajax({
    	url:sessionStorage.getItem("contextPath") + "/member/helpCenter/uploadSummernoteImageFile?article_code=" + g_articleCode,
    	processData:false,
    	contentType:false,
    	type:"post",
    	data:formData,
    	success:function(data){
    		$(el).summernote("insertImage", data.url);
    	}
    });
}


var isSubmit = false;
$(function(){
	$("form").submit(function(){
		isSubmit = true; // submit하는 경우에는 beforeunload, unload 이벤트의 로직을 거치지 않겠다.
		
		$(".note-editable img").each(function(index, element) {
			var src = $(element).attr("src");
			var img = src.substring(src.lastIndexOf("/") + "/".length);
			
			var input = $("<input>");
			input.attr("type", "hidden");
			input.attr("name", "uploadImages[" + index + "]");
			input.attr("value", img);
			$("form").append(input);
		});
	});
});

$(function(){
	// beforeunload : 현재 머무르고 있는 페이지를 떠나려고 할 때 발생하는 이벤트
	$(window).on("beforeunload", function(e){
		if(isSubmit == false){
			/*
	 			몇몇 브라우저들은 보안상의 문제로 인해 alert, confirm, prompt와 같은 대화 상자 관련 메서드들을 막아두었다.
	 			대신 return 값으로 메시지를 지정하면 해당 메시지로 구성된 confirm 창이 출력된다.
	 			
	 			(참고)
				Chrome 브라우저는 51 버전부터 사용자 지정 메시지를 더 이상 지원하지 않는다고 한다.
				즉, 이제 Chrome 브라우저에서 반환값은 의미가 없다.(항상 기본 메시지로만 구성된 confirm 창이 출력됨)
				※단, 반환값이 더 이상 의미가 없다 하더라도 아래와 같이 아무런 문자열이라도 반환은 해주어야 confirm 창이 출력된다.
			*/
			return "";
		}
	});
	
	// unload : 현재 머무르고 있는 페이지를 떠날 때 발생하는 이벤트
	$(window).on("unload", function(e){
		if(isSubmit == false){
			$.ajax({
				/*
					Chrome 브라우저는 사용자가 페이지를 닫을 때 즉, beforeunload 및 unload 시에 동기식 요청을 막아두었다.
					
					(참고)
					AJAX 동기식 요청(async:false)은 jQuery 1.8 버전부터 더 이상 기술 지원을 하지 않는다고 한다.(deprecated)
					즉, 사용 시 작동은 하나(경고 메시지 나타남) 더 이상 사용을 권하진 않는다.
				*/
				// async:false
		    	url:sessionStorage.getItem("contextPath") + "/member/helpCenter/writeForm/unload",
		    	type:"get",
		    	data:{ "article_code":g_articleCode }
		    });
			
			/*
				위의 비동기식 요청이 서버에 전달되기도 전에 페이지가 종료되는 것을 방지하기 위해 반복문을 이용하여 시간을 지연시킨 것이다.
				
				(참고)
				setTimeout, delay와 같은 메서드를 이용한 지연은 스레드를 절전 상태로 전환하여 대기시키는 것이기 때문에
				이때 브라우저는 스레드가 절전 상태이므로 사용 중인 상태가 아니라 판단하여 그런 것인지
				스레드를 그냥 바로 종료시켜버림으로써 예상과는 다르게 지연이 되지 않고 페이지가 즉시 종료되어 버린다.
				그래서 스레드를 절전 상태로 전환하지 않으면서 시간을 지연시키기 위한 방법으로 반복문을 이용한 것이다.
			*/
			var start = new Date().getTime();
			while((new Date().getTime() - start) < 100){ } // 100ms(0.1초) 지연
		}
	});
});