var helpCenterDTO = new Object();
helpCenterDTO.article_code = g_articleCode;
helpCenterDTO.uploadImages = new Array();

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
    		helpCenterDTO.uploadImages.push(data.imgName);
    	}
    });
}


var isSubmit = false;
$(function(){
	$("form").submit(function(){
		isSubmit = true;
		
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
	$(window).on("beforeunload", function(e){
		if(isSubmit == false){
			return "";
		}
	});
	
	$(window).on("unload", function(e){
		if(isSubmit == false){
			$.ajax({
		    	url:sessionStorage.getItem("contextPath") + "/member/helpCenter/modifyForm/unload",
		    	type:"post",
		    	contentType:"application/json",
		    	data:JSON.stringify(helpCenterDTO)
		    });
			
			var start = new Date().getTime();
			while((new Date().getTime() - start) < 100){ }
		}
	});
});