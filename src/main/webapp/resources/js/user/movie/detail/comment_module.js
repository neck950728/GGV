function append_comment(comment){
	var score = comment.score + "0%";
	var delete_btn = "";
	
	if(g_memberInfo.id == comment.id || g_memberInfo.id == "admin"){
		delete_btn = "<button class='delete_btn'>삭제</button>";
	}
	
	$(".result_list").append("<li id='" + comment.id + "' >" +
										"<div class='score'>" +
											"<span class='off'>" +
												"<span class='on'>점수</span>" +
											"</span> " +
											"<span class='score_count'>" + comment.score + "</span>" +
										"</div>" +
										"<div class='review'>" +
											"<p>" + comment.review + "</p>" +
											"<div>" +
												"<span class='writer'>" + comment.id + "</span> " +
												"<em>|</em> " +
												"<span class=''>" + comment.dateCreated + "</span>" +
												delete_btn +
											"</div>" +
										"</div>" +
										"<div class='buttons'>" +
											"<button class='sympathy'>" +
												"<span class='icon'>공감</span>" +
												"<span class='count'>" + comment.sympathy + "</span>" +
											"</button> " +
											"<button class='notSympathy'>" +
												"<span class='icon'>비공감</span>" +
												"<span class='count'>" + comment.notSympathy + "</span>" +
											"</button>" +
										"</div>" +
									"</li>");
	
	$(".result_list > li").last().find(".score .on").css("width", score);
}

function score_revalidate(score){
	$(".input .score > .score_count").html(score);
	$(".input .score > div > span").css("background-position", "-9999px -9999px");
	
	for(var i = 1; i <= score; i++){
		var element = $(".input .score > div > span[title='" + i + "']");
		if(element.hasClass("left")){
			element.css("background-position", "1px -925px");
		}else{
			element.css("background-position", "-11px -925px");
		}
	}
}


$(function(){
	if(g_myComment == null){
		$(".comment").prepend("<div class='input'>" +
											"<form>" +
												"<input type='hidden' name='mID' value='" + g_movieCode + "'>" +
												"<div class='score'>" +
													"<div>" +
														"<span title='0'>0점</span>" +
														"<span title='1' class='left'>1점</span>" +
														"<span title='2' class='right'>2점</span>" +
														"<span title='3' class='left'>3점</span>" +
														"<span title='4' class='right'>4점</span>" +
														"<span title='5' class='left'>5점</span>" +
														"<span title='6' class='right'>6점</span>" +
														"<span title='7' class='left'>7점</span>" +
														"<span title='8' class='right'>8점</span>" +
														"<span title='9' class='left'>9점</span>" +
														"<span title='10' class='right'>10점</span>" +
													"</div> " +
													"<span class='score_count'>0</span>" +
													"<input type='hidden' name='score'>" +
												"</div>" +
												"<div class='inputField'>" +
													"<textarea maxlength='300' name='review'></textarea>" +
													"<p class='counter'>" +
														"<span class='cur_count'>0</span><em>/300</em>" +
													"</p>" +
												"</div>" +
												"<button type='button' class='register_btn'>등록</button>" +
											"</form>" +
										"</div>");
	}
	
	// --------------------------------------------------------------------------------------------
	
	$(".result .total").html("총 " + g_commentList.length + "건");
	
	// --------------------------------------------------------------------------------------------
	
	if(g_myComment != null){
		append_comment(g_myComment);
		$(".result_list > li").last().addClass("myComment");
	}
	
	$.each(g_commentList, function(index, comment){
		append_comment(comment);
	});
	
	// --------------------------------------------------------------------------------------------
	
	/*
		$("iframe").on("load", function(){
			var iframe_height = this.contentWindow.document.body.offsetHeight;
			$(this).height(iframe_height);
		});
		
 		위와 같이 부모 frame 측에서 iframe 로드 시에 높이를 지정하는 방법도 생각해볼 수 있을 텐데
 		아쉽게도 위 방법은 로드 완료 이후 실행되는 로직에는 영향을 받지 않기 때문에
 		현재 페이지와 같이 로드 완료 이후 콘텐츠를 추가하는 방식에서는 적합하지 않다.
		(쉽게 말해 위 방법을 사용하면 콘텐츠가 추가되기 전 높이로 설정되어 버린다.)
	*/
	var iframe = window.parent.document.getElementsByTagName("iframe");
	$(iframe).css("height", $("html").css("height"));
});

var g_selected_score = 0;
$(function(){
	$(".input .score > div > span").mouseenter(function(){
		var score = parseInt($(this).attr("title"));
		score_revalidate(score);
	}).mouseleave(function(){
		score_revalidate(g_selected_score);
	});
	
	$(".input .score > div > span").click(function(){
		g_selected_score = parseInt($(this).attr("title"));
		$("input[name='score']").attr("value", g_selected_score);
	});
});

$(function(){
	$(".inputField > textarea").click(function(){
		if(g_memberInfo == null){
			$(".inputField > textarea").prop("readonly", true);
			if(confirm("로그인이 필요한 서비스입니다.\n로그인 페이지로 이동하시겠습니까?")){
				parent.document.location.href = sessionStorage.getItem("contextPath") + "/member/loginForm"; // 부모 frame 페이지 이동
			}
		}
	});
	
	$(".inputField > textarea").bind("keydown keyup", function(){
		var review = $(this).val();
		$(".inputField > .counter > .cur_count").html(review.length);
	});
});

$(function(){
	$(".input .register_btn").click(function(){
		if(g_memberInfo != null){
			var review = $(".inputField > textarea").val();
			if(g_selected_score == 0){
				alert("평점을 선택해주세요.");
			}else if(review.trim().length == 0){
				alert("내용을 입력해주세요.");
			}else{
				$.ajax({
					url:sessionStorage.getItem("contextPath") + "/movie/detail/commentModule/register",
					type:"post",
					data:$(this).parent().serialize(),
					beforeSend:function(xhr){
						xhr.setRequestHeader("loginInterceptor", "childWindow");
					},
					
					success:function(){
						parent.document.location.reload();
					}
				});
			}
		}else{
			if(confirm("로그인이 필요한 서비스입니다.\n로그인 페이지로 이동하시겠습니까?")){
				parent.document.location.href = sessionStorage.getItem("contextPath") + "/member/loginForm";
			}
		}
	});
});

$(function(){
	$(".result_list .delete_btn").click(function(){
		var id = $(this).siblings(".writer").html();
		
		if(confirm("평점을 삭제하시겠습니까?")){
			$.ajax({
				url:sessionStorage.getItem("contextPath") + "/movie/detail/commentModule/delete",
				type:"post",
				data:{ "mID":g_movieCode, "id":id },
				beforeSend:function(xhr){
					xhr.setRequestHeader("loginInterceptor", "childWindow");
				},
				
				success:function(){
					parent.document.location.reload();
				}
			});
		}
	});
});

$(function(){
	$(".result_list .buttons > button").click(function(){
		var vote_target = $(this).parents("li").attr("id");
		var vote_type = $(this).attr("class");
		
		if(g_memberInfo != null){
			if(g_memberInfo.id == vote_target){
				alert("내 평점에는 공감/비공감이 불가능합니다.");
			}else{
				var param = { "id":g_memberInfo.id, "vote_target":vote_target, "movie_code":g_movieCode, "vote_type":vote_type };
				
				$.ajax({
					url:sessionStorage.getItem("contextPath") + "/movie/detail/commentModule/isAlreadyVote",
					type:"get",
					data:param,
					beforeSend:function(xhr){
						xhr.setRequestHeader("loginInterceptor", "childWindow");
					},
					
					success:function(result){
						if(result == ""){
							$.get(sessionStorage.getItem("contextPath") + "/movie/detail/commentModule/vote", param, function(){
								if(vote_type == "sympathy"){
									var count = $(".result_list > li[id='" + vote_target + "'] .sympathy > .count");
									count.html(parseInt(count.html()) + 1);
								}else{
									var count = $(".result_list > li[id='" + vote_target + "'] .notSympathy > .count");
									count.html(parseInt(count.html()) + 1);
								}
							});
						}else{
							switch(result){
								case "sympathy":
									result = "공감";
									break;
								case "notSympathy":
									result = "비공감";
							}
							
							alert("이미 " + result + "하셨습니다.");
						}
					}
				});
			}
		}else{
			if(confirm("로그인이 필요한 서비스입니다.\n로그인 페이지로 이동하시겠습니까?")){
				parent.document.location.href = sessionStorage.getItem("contextPath") + "/member/loginForm";
			}
		}
	});
});