package movie.other;

import java.util.List;

import admin.movie.bean.MovieDTO;

public class TrailerListWrapper {
	List<MovieDTO> top5MovieList;
	TrailerPaging paging;
	List<MovieDTO> trailerList;
	
	public List<MovieDTO> getTop5MovieList() {
		return top5MovieList;
	}
	
	public TrailerPaging getPaging() {
		return paging;
	}
	
	public List<MovieDTO> getTrailerList() {
		return trailerList;
	}
	
	public void setTop5MovieList(List<MovieDTO> top5MovieList) {
		this.top5MovieList = top5MovieList;
	}
	
	public void setPaging(TrailerPaging paging) {
		this.paging = paging;
	}
	
	public void setTrailerList(List<MovieDTO> trailerList) {
		this.trailerList = trailerList;
	}
}