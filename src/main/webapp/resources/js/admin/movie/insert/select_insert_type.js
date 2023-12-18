$(function(){
	$(".excel_insert_btn").click(function(){
		$("body").append("<div class='dialog_bg'></div>" +
								"<div class='excel_insert_dialog'>" +
									"<button class='close_btn'></button>" +
									"<form>" +
										"<div>" +
											"<input type='text' readonly='readonly'>" +
											"<button type='button' class='find_file_btn'>파일 찾기</button>" +
											"<input type='file' name='excel_file' accept='.xls, .xlsx', style='display:none;'>" +
										"</div>" +
									"</form>" +
									"<button class='register_btn'>등록</button>" +
								"</div>");
	});
});

$(function(){
	$(document).on("click", ".excel_insert_dialog > .close_btn", function(){
		$(".dialog_bg").remove();
		$(".excel_insert_dialog").remove();
	});
	
	$(document).on("click", ".excel_insert_dialog .find_file_btn", function(){
		$(".excel_insert_dialog input[type='file']").trigger("click");
	});
	
	$(document).on("change", ".excel_insert_dialog input[type='file']", function(){
		var filePath = $(this).val();
		var fileName = filePath.substring(filePath.lastIndexOf("\\") + "\\".length);
		
		$(".excel_insert_dialog input[type='text']").val(fileName);
	});

	$(document).on("click", ".excel_insert_dialog > .register_btn", function(){
		$(".dialog_bg").css({ "background-image":"url('" + sessionStorage.getItem("contextPath") + "/resources/images/public/loading.gif')",
									 "background-repeat":"no-repeat",
									 "background-position":"50% 50%",
									 "background-size":"45px 45px" });
		
		$(".excel_insert_dialog").css("display", "none");
		
		$("body").append("<span class='wait'>" +
									"영화를 등록 중입니다.<br>" +
									"잠시만 기다려 주십시오." +
								"</span>");
		
		// ---------------------------------------------------------------------------------------------------------------------------
		
		// https://blog.naver.com/dngu_icdi/221392129480 참고
		var form = $(".excel_insert_dialog > form")[0];
        var formData = new FormData(form);
        formData.append("fileObj", $(".excel_insert_dialog input[type='file']")[0].files[0]);
        
        $.ajax({
        	url:sessionStorage.getItem("contextPath") + "/admin/movie/insert/excel_insert/request",
        	processData:false,
        	contentType:false,
        	type:"post",
        	data:formData,
        	success:function(){
    			$(".excel_insert_dialog").remove();
        		$(".wait").remove();
        		
        		$("body").append("<div class='success_dialog'>" +
        									"<button class='close_btn'></button>" +
        									"<div>" +
        										"<span>등록이 완료되었습니다.</span><br>" +
        										"<button class='confirm_btn'>확인</button>" +
        									"</div>" +
        								"</div>");
        	}
        });
	});
});

$(function(){
	$(document).on("click", ".success_dialog > .close_btn, .success_dialog .confirm_btn", function(){
		$(".dialog_bg").remove();
		$(".success_dialog").remove();
	});
});