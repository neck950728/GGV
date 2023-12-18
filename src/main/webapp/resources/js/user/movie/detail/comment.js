function drawScoreDistributionChart(){
	var scoreDistribution = jQuery.parseJSON($("#scoreDistribution").val());
	
	var dataChart = [["점수", "분포율", { role:"style" }, { role:"annotation" }]];
	for(var i = 1; i <= 10; i++){
		var key = "score" + i;
		dataChart.push([i + "점", scoreDistribution[key], "#ff5162", (scoreDistribution[key] * 100).toFixed(0) + "%"]);
	}
	
	var data = google.visualization.arrayToDataTable(dataChart);
	var view = new google.visualization.DataView(data);
	var options = {
		bar:{ groupWidth:"35%" },
		backgroundColor:"transparent",
		legend:"none",
		enableInteractivity:false,
		
		hAxis:{
			textStyle:{ bold:true }
		},
		
		vAxis:{
			ticks:[0, .2, .4, .6, .8, 1],
			format:"percent",
			
			gridlines:{ color:"#d8d8d8" },
			minorGridlines:{ color:"none" }
		}
	};
	
	var chart = new google.visualization.ColumnChart(document.getElementById("scoreDistributionChart"));
	chart.draw(view, options);
	
	$("#scoreDistributionChart text[fill='#b33945']").each(function(){
		var x = parseInt($(this).attr("x"));
		var y = parseInt($(this).attr("y"));
		$(this).attr("x", x + 2);
		
		if(parseInt($(this).html()) > 85){
			$(this).attr("y", y - 27);
		}else{
			$(this).attr("y", y + 8);
		}
	});
}

function drawGenderDistributionChart(genderDistribution){	
	if(genderDistribution.man == 0 && genderDistribution.woman == 0){
		$("#genderDistributionChart").append("<img src='" + sessionStorage.getItem("contextPath") + "/resources/images/user/movie/detail/comment/notExistsComment.png' class='notExistsComment'>");
	}else{
		var dataChart = [["성별", "참여율"]];
		dataChart.push(["남자", genderDistribution.man]);
		dataChart.push(["여자", genderDistribution.woman]);
		
		var data = google.visualization.arrayToDataTable(dataChart);
		var options = {
			pieHole:0.4,
			legend:"none",
			backgroundColor:"transparent",
			enableInteractivity:false,
			slices:{
				0:{ color:"#59a8e8" },
				1:{ color:"#fd6d7c" }
			}
	    };
		
		var chart = new google.visualization.PieChart(document.getElementById("genderDistributionChart"));
		chart.draw(data, options);
	}
}

function drawAgeGroupDistributionChart(ageGroupDistribution){
	if(ageGroupDistribution.teenager == 0 && ageGroupDistribution.twenty == 0 && ageGroupDistribution.thirty == 0 && ageGroupDistribution.fortyMore == 0){
		$("#ageGroupDistributionChart").append("<img src='" + sessionStorage.getItem("contextPath") + "/resources/images/user/movie/detail/comment/notExistsComment.png' class='notExistsComment'>");
	}else{
		var dataChart = [["연령대", "참여율"]];
		dataChart.push(["10대", ageGroupDistribution.teenager]);
		dataChart.push(["20대", ageGroupDistribution.twenty]);
		dataChart.push(["30대", ageGroupDistribution.thirty]);
		dataChart.push(["40대 이상", ageGroupDistribution.fortyMore]);
		
		var data = google.visualization.arrayToDataTable(dataChart);
		var options = {
			pieHole:0.4,
			legend:"none",
			backgroundColor:"transparent",
			enableInteractivity:false,
			slices:{
				0:{ color:"#00beb3" },
				1:{ color:"#7a76bf" },
				2:{ color:"e7679d" },
				3:{ color:"8dc655" }
			}
	    };
		
		var chart = new google.visualization.PieChart(document.getElementById("ageGroupDistributionChart"));
		chart.draw(data, options);
	}
}


$(function(){
	$("#score_distribution").click(function(){
		$("#chart_type > #score_distribution").css("background-position", "0px -126px");
		$("#chart_type > #genderAndAgeGroup_distribution").css("background-position", "-90px -126px");
		
		$("#chart_area").empty();
		$("#chart_area").append("<div id='scoreDistributionChart_area'>" +
											  "<div id='scoreDistributionChart'></div>" +
										  "</div>");
		
		google.charts.load("current", { "packages":["corechart"] });
		google.charts.setOnLoadCallback(drawScoreDistributionChart);
	});
	
	
	$("#genderAndAgeGroup_distribution").click(function(){
		$("#chart_type > #genderAndAgeGroup_distribution").css("background-position", "-90px -161px");
		$("#chart_type > #score_distribution").css("background-position", "0px -161px");
		
		$("#chart_area").empty();
		$("#chart_area").append("<div id='genderDistributionChart_area' class='chart_area'>" +
											  "<div id='man_score' class='score'>" +
												  "<span class='gender_icon'></span><br>" +
												  "<span>" +
													  "<span class='star_icon'></span> " +
													  "<em></em>" +
												  "</span><br>" +
												  "<span class='gender'>남자</span>" +
											  "</div>" +
											  "<div id='woman_score' class='score'>" +
												  "<span class='gender_icon'></span><br>" +
												  "<span>" +
													  "<span class='star_icon'></span> " +
													  "<em></em>" +
												  "</span><br>" +
												  "<span class='gender'>여자</span>" +
											  "</div>" +
											  "<div class='genderDistributionChart_boxing'>" +
												  "<div id='genderDistributionChart'></div>" +
												  "<span class='title'>참여율</span>" +
											  "</div>" +
											  "<div class='boundary'></div>" +
										  "</div>" +
										  "<div id='ageGroupDistributionChart_area' class='chart_area'>" +
											  "<div class='score'>" +
												  "<ul>" +
													  "<li id='teenager'>" +
														  "<strong>10대</strong>" +
														  "<span>" +
															  "<span class='star_icon'></span> " +
															  "<em></em>" +
														  "</span>" +
													  "</li>" +
													  "<li id='twenty'>" +
														  "<strong>20대</strong>" +
														  "<span>" +
															  "<span class='star_icon'></span> " +
															  "<em></em>" +
														  "</span>" +
													  "</li>" +
													  "<li id='thirty'>" +
														  "<strong>30대</strong>" +
														  "<span>" +
															  "<span class='star_icon'></span> " +
															  "<em></em>" +
														  "</span>" +
													  "</li>" +
													  "<li id='fortyMore'>" +
														  "<strong>40대 이상</strong>" +
														  "<span>" +
															  "<span class='star_icon'></span> " +
															  "<em></em>" +
														  "</span>" +
													  "</li>" +
												  "</ul>" +
											  "</div>" +
											  "<div class='ageGroupDistributionChart_boxing'>" +
												  "<div id='ageGroupDistributionChart'></div>" +
												  "<span class='title'>참여율</span>" +
												  "<div class='ageGroup_legend'></div>" +
											  "</div>" +
										  "</div>");
		
		var genderDistribution = jQuery.parseJSON($("#genderDistribution").val());
		$("#man_score em").html(genderDistribution.man_score.toFixed(2));
		$("#woman_score em").html(genderDistribution.woman_score.toFixed(2));
		
		var ageGroupDistribution = jQuery.parseJSON($("#ageGroupDistribution").val());
		$("#teenager em").html(ageGroupDistribution.teenager_score.toFixed(2));
		$("#twenty em").html(ageGroupDistribution.twenty_score.toFixed(2));
		$("#thirty em").html(ageGroupDistribution.thirty_score.toFixed(2));
		$("#fortyMore em").html(ageGroupDistribution.fortyMore_score.toFixed(2));
		
		google.charts.load("current", { "packages":["corechart"] });
	    google.charts.setOnLoadCallback(drawGenderDistributionChart(genderDistribution));
	    google.charts.setOnLoadCallback(drawAgeGroupDistributionChart(ageGroupDistribution));
	});
});

$(function(){
	$("#score_distribution").trigger("click");
});