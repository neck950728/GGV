$(function(){
	var width;
	var disableDragAndDrop;
	var insert; // ['insert', ['picture', 'link', 'video']]
	var view; // ['view', ['fullscreen', 'help']]
	
	if(g_type == "public"){
		width = 715;
		disableDragAndDrop = true;
		insert = "";
		view = "";
	}else{
		width = 745;
		disableDragAndDrop = false;
		insert = ['insert', ['picture']];
		view = "";
	}
	
	$("#summernote").summernote({
		width:width,
		height:400,
		maxHeight:800,
		focus:true, // 에디터 로딩 후 포커스 여부
		lang:"ko-KR",
		disableDragAndDrop:disableDragAndDrop,
		
		toolbar:[
			['fontname', ['fontname']],
			['fontsize', ['fontsize']],
			['style', ['bold', 'italic', 'underline', 'strikethrough', 'clear']],
			['color', ['forecolor', 'color']],
			['table', ['table']],
			['para', ['ul', 'ol', 'paragraph']],
			['height', ['height']],
			insert,
			view
		],
		fontNames:['sans-serif', 'Arial', 'Arial Black', 'Comic Sans MS', 'Courier New', '맑은 고딕', '궁서', '굴림체', '굴림', '돋움체', '바탕체'],
		fontSizes:['8', '9', '10', '11', '12', '14', '16', '18', '20', '22', '24', '28', '30', '36', '50', '72'],
		
		callbacks:{
			onImageUpload:function(files){
				for(var i = files.length - 1; i >= 0; i--){
					uploadSummernoteImageFile(files[i], this);
				}
			}
		}
	});
	
	if(location.pathname.indexOf("modifyForm") != -1){
		$("#summernote").summernote("code", g_articleContent);
	}
	
	// -----------------------------------------------------------------------------------------------------------------
	
	$("input[name='title']").css("width", width);
});

$(function(){
	$(".submit_button").click(function(){
		var title = $("input[name='title']").val().trim();
		if(title.length == 0){
			alert("제목을 입력해주세요.");
			return;
		}
		
		var content = $("textarea[name='content']").val().trim();
		if(content.length == 0){
			alert("내용을 입력해주세요.");
			return;
		}
		
		$("form").submit();
	});
});