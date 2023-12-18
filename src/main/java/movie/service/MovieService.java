package movie.service;

import java.util.List;

import admin.movie.bean.MovieDTO;
import movie.bean.CommentDTO;
import movie.bean.CommentVoteDTO;
import movie.bean.ReserveDTO;
import movie.other.CommentModuleWrapper;
import movie.other.CommentWrapper;
import movie.other.PreMovieChartWrapper;
import movie.other.TrailerListWrapper;

public interface MovieService {
	List<MovieDTO> selectMovieChart();
	PreMovieChartWrapper selectPreMovieChart();
	TrailerListWrapper selectTrailerList();
	TrailerListWrapper searchTrailer(String keyword);
	TrailerListWrapper trailerPageMove(int page, String searchKeyword);
	MovieDTO selectMovieIncludingSubMovieInfo(String mID);
	CommentWrapper selectComment(String mID);
	CommentModuleWrapper selectCommentModule(String mID, int page, String order, String id);
	void insertComment(String id, String mID, CommentDTO commentDTO);
	void deleteComment(String id, String mID);
	String isAlreadyCommentVote(CommentVoteDTO commentVoteDTO);
	void insertCommentVote(CommentVoteDTO commentVoteDTO);
	List<MovieDTO> selectShowingMovieList();
	boolean isAlreadyReserve(ReserveDTO reserveDTO);
	ReserveDTO insertReserve(ReserveDTO reserveDTO, String detail_date);
	void deleteReserve(ReserveDTO reserveDTO);
	List<MovieDTO> selectSearchedMovieList(String keyword);
}