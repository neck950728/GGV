package admin.movie.service;

import java.io.File;
import java.util.List;

import admin.movie.bean.MovieDTO;
import admin.movie.other.MovieListWrapper;

public interface AdminMovieService {
	String insertMovie(MovieDTO movieDTO);
	void insertMovie(File excelFile);
	MovieListWrapper selectMovieList(int page);
	List<MovieDTO> searchMovie(String keyword);
	MovieDTO selectMovieIncludingSubMovieInfo(String code);
	void deleteMovie(String code);
	MovieDTO selectMovieForModify(String code);
	void modifyMovie(MovieDTO new_movieDTO);
}