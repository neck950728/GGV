<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/user/module/movie/detail/header.jspf"%>
<!DOCTYPE html>

				<div id="stillCut">
					<div class="list">
						<button class="previous">이전</button>
						<ul>
						</ul>
						<button class="next">다음</button>
					</div>
					<div class="viewer">
						<img>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<textarea id="movie_json" class="hidden">${movie_json}</textarea>
</body>
</html>