<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/user/module/movie/detail/header.jspf"%>
<!DOCTYPE html>

				<div id="comment">
					<div class="chart">
						<div id="chart_type">
							<a id="score_distribution">점수 분포</a><a id="genderAndAgeGroup_distribution">남녀별ㆍ연령별</a>
						</div>
						<div id="chart_area">
						</div>
					</div>
					<iframe src="${pageContext.request.contextPath}/movie/detail/commentModule?mID=${movie.code}&page=1&order=sympathy" scrolling="no" id="commentModule"></iframe>
				</div>
			</div>
		</div>
	</div>
	
	<textarea id="scoreDistribution" class="hidden">${scoreDistribution_json}</textarea>
	<textarea id="genderDistribution" class="hidden">${genderDistribution_json}</textarea>
	<textarea id="ageGroupDistribution" class="hidden">${ageGroupDistribution_json}</textarea>
</body>
</html>