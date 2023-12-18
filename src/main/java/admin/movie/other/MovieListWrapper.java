package admin.movie.other;

import java.util.List;

import admin.movie.bean.MovieDTO;

public class MovieListWrapper {
	private MoviePaging paging;
	private List<MovieDTO> movieList;
	
	public MoviePaging getPaging() {
		return paging;
	}
	
	public List<MovieDTO> getMovieList() {
		return movieList;
	}
	
	public void setPaging(MoviePaging paging) {
		this.paging = paging;
	}
	
	public void setMovieList(List<MovieDTO> movieList) {
		this.movieList = movieList;
	}
}