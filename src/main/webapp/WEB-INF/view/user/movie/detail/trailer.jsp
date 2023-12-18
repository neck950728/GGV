<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/user/module/movie/detail/header.jspf"%>
	
				<div id="trailer">
					<div class="heading">
						<h2>트레일러</h2> <span>${movie.trailer_list.size()}건</span>
					</div>
					<div class="list">
						<ul></ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<textarea id="movie_json" class="hidden">${movie_json}</textarea>
</body>
</html>