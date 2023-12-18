package movie.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import admin.movie.bean.MovieDTO;
import movie.bean.AgeGroupDistributionDTO;
import movie.bean.CommentDTO;
import movie.bean.CommentVoteDTO;
import movie.bean.GenderDistributionDTO;
import movie.bean.ReserveDTO;
import movie.bean.ScoreDistributionDTO;
import movie.dao.MovieDAO;
import movie.other.CommentModuleWrapper;
import movie.other.CommentPaging;
import movie.other.CommentWrapper;
import movie.other.PreMovieChartWrapper;
import movie.other.TrailerListWrapper;
import movie.other.TrailerPaging;

@Service
public class MovieServiceImpl implements MovieService {
	@Autowired
	private MovieDAO movieDAO;
	
	private String getRandomReserveCode() {
		Random rnd = new Random();
		String reserve_code = "";

		for(int i = 1; i <= 19; i++) {
			if(i % 5 == 0) {
				reserve_code += "-";
			}else {
			    if(rnd.nextBoolean()) { // true 또는 false를 랜덤으로 반환
			    	reserve_code += (char)((int)(Math.random() * (90 - 65 + 1)) + 65); // A(65) ~ Z(90)
			    }else {
			    	reserve_code += rnd.nextInt(10); // nextInt(10) : 0 ~ 9까지의 정수를 랜덤으로 반환
			    }
			}
		}
		
		int result = movieDAO.reserveCodeDuplicateCheck(reserve_code);
		if(result > 0) {
			reserve_code = getRandomReserveCode();
		}
		
		return reserve_code;
	}
	
	
	@Override
	public List<MovieDTO> selectMovieChart() {
		return movieDAO.selectMovieChart();
	}
	
	@Override
	public PreMovieChartWrapper selectPreMovieChart() {
		List<MovieDTO> recommendMovieList = movieDAO.selectRecommendMovieListFromPreMovieChart();
		List<String> preMovieChartOrder = new ArrayList<>();
		Map<String, List<MovieDTO>> preMovieChart = movieDAO.selectPreMovieChart();
		
		Iterator<String> iter = preMovieChart.keySet().iterator();
		while(iter.hasNext()) {
			preMovieChartOrder.add(iter.next());
		}
		
		PreMovieChartWrapper preMovieChartWrapper = new PreMovieChartWrapper();
		preMovieChartWrapper.setRecommendMovieList(recommendMovieList);
		preMovieChartWrapper.setPreMovieChartOrder(preMovieChartOrder);
		preMovieChartWrapper.setPreMovieChart(preMovieChart);
		
		return preMovieChartWrapper;
	}
	
	@Override
	public TrailerListWrapper selectTrailerList() {
		List<MovieDTO> top5MovieList = movieDAO.selectTop5MovieListForTrailer();
		TrailerPaging paging = new TrailerPaging(1, movieDAO.selectTotalTrailerCount());
		List<MovieDTO> curPageTrailerList = movieDAO.selectCurPageTrailerList(paging.getStartNum(), paging.getEndNum());
		
		TrailerListWrapper trailerListWrapper = new TrailerListWrapper();
		trailerListWrapper.setTop5MovieList(top5MovieList);
		trailerListWrapper.setPaging(paging);
		trailerListWrapper.setTrailerList(curPageTrailerList);
		
		return trailerListWrapper;
	}
	
	@Override
	public TrailerListWrapper searchTrailer(String keyword) {
		TrailerPaging paging = new TrailerPaging(1, movieDAO.selectTotalSearchedTrailerCount(keyword));
		List<MovieDTO> curPageSearchedTrailerList = movieDAO.selectCurPageSearchedTrailerList(paging.getStartNum(), paging.getEndNum(), keyword);
		
		TrailerListWrapper trailerListWrapper = new TrailerListWrapper();
		trailerListWrapper.setPaging(paging);
		trailerListWrapper.setTrailerList(curPageSearchedTrailerList);
		
		return trailerListWrapper;
	}
	
	@Override
	public TrailerListWrapper trailerPageMove(int page, String searchKeyword) {
		TrailerListWrapper trailerListWrapper = new TrailerListWrapper();
		
		if(searchKeyword != null) {
			int totalSearchedTrailerCount = movieDAO.selectTotalSearchedTrailerCount(searchKeyword);
			TrailerPaging paging = new TrailerPaging(page, totalSearchedTrailerCount);
			List<MovieDTO> curPageSearchedTrailerList = movieDAO.selectCurPageSearchedTrailerList(paging.getStartNum(), paging.getEndNum(), searchKeyword); 
			
			trailerListWrapper.setPaging(paging);
			trailerListWrapper.setTrailerList(curPageSearchedTrailerList);
		}else {
			int totalTrailerCount = movieDAO.selectTotalTrailerCount();
			TrailerPaging paging = new TrailerPaging(page, totalTrailerCount);
			List<MovieDTO> curPageTrailerList = movieDAO.selectCurPageTrailerList(paging.getStartNum(), paging.getEndNum());
			
			trailerListWrapper.setPaging(paging);
			trailerListWrapper.setTrailerList(curPageTrailerList);
		}
		
		return trailerListWrapper;
	}
	
	@Override
	public MovieDTO selectMovieIncludingSubMovieInfo(String mID) {
		return movieDAO.selectMovie_includingSubMovieInfo(mID);
	}
	
	@Override
	public CommentWrapper selectComment(String mID) {
		ScoreDistributionDTO scoreDistributionDTO = movieDAO.selectScoreDistribution(mID);
		GenderDistributionDTO genderDistributionDTO = movieDAO.selectGenderDistribution(mID);
		AgeGroupDistributionDTO ageGroupDistributionDTO = movieDAO.selectAgeGroupDistribution(mID);
		
		CommentWrapper movieCommentWrapper = new CommentWrapper();
		movieCommentWrapper.setScoreDistributionDTO(scoreDistributionDTO);
		movieCommentWrapper.setGenderDistributionDTO(genderDistributionDTO);
		movieCommentWrapper.setAgeGroupDistributionDTO(ageGroupDistributionDTO);
		
		return movieCommentWrapper;
	}
	
	@Override
	public CommentModuleWrapper selectCommentModule(String mID, int page, String order, String id) {
		CommentModuleWrapper movieCommentModuleWrapper = new CommentModuleWrapper();
		
		int totalCommentCount = movieDAO.selectTotalCommentCount(mID);
		CommentPaging paging = new CommentPaging(page, totalCommentCount);
		movieCommentModuleWrapper.setPaging(paging);
		
		if(order.equals("sympathy")) {
			movieCommentModuleWrapper.setCommentList(movieDAO.selectCurPageCommentList_OrderBySympathy(mID, paging.getStartNum(), paging.getEndNum()));
			movieCommentModuleWrapper.setOrder("sympathy");
		}else {
			movieCommentModuleWrapper.setCommentList(movieDAO.selectCurPageCommentList_OrderByDateCreated(mID, paging.getStartNum(), paging.getEndNum()));
			movieCommentModuleWrapper.setOrder("dateCreated");
		}
		
		if(id != null) {
			CommentDTO myComment = movieDAO.selectMyComment(id, mID);
			movieCommentModuleWrapper.setMyComment(myComment);
		}
		
		return movieCommentModuleWrapper;
	}
	
	@Override
	public void insertComment(String id, String mID, CommentDTO commentDTO) {
		commentDTO.setId(id);
		commentDTO.setMovie_code(mID);
		movieDAO.insertComment(commentDTO);
	}
	
	@Transactional
	@Override
	public void deleteComment(String id, String mID) {
		movieDAO.deleteComment(id, mID);
	}
	
	@Override
	public String isAlreadyCommentVote(CommentVoteDTO commentVoteDTO) {
		String vote_type = "";
		
		commentVoteDTO = movieDAO.selectCommentVote(commentVoteDTO);
		if(commentVoteDTO != null) {
			vote_type = commentVoteDTO.getVote_type();
		}
		
		return vote_type;
	}
	
	@Transactional
	@Override
	public void insertCommentVote(CommentVoteDTO commentVoteDTO) {
		movieDAO.insertCommentVote(commentVoteDTO);
	}
	
	@Override
	public List<MovieDTO> selectShowingMovieList() {
		List<MovieDTO> showingMovieList = new ArrayList<>();
		List<MovieDTO> movieList = movieDAO.selectAllMovieList();
		
		for(MovieDTO movieDTO : movieList) {
			if(movieDTO.getScreeningInfo_list().size() > 0) {
				showingMovieList.add(movieDTO);
			}
		}
		
		return showingMovieList;
	}
	
	@Override
	public boolean isAlreadyReserve(ReserveDTO reserveDTO) {
		String reserved_seat = movieDAO.selectScreeningInfo(reserveDTO.getScreeningInfo_identification()).getReserved_seat();
		if(reserved_seat != null) {
			for(String seat : reserveDTO.getSeat().split(",")) {
				if(reserved_seat.contains(seat.trim())) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Transactional
	@Override
	public ReserveDTO insertReserve(ReserveDTO reserveDTO, String detail_date) {
		reserveDTO.setReserve_code(getRandomReserveCode());
		movieDAO.insertReserve(reserveDTO);
		
		reserveDTO.setDate_(detail_date);
		
		return reserveDTO;
	}
	
	@Transactional
	@Override
	public void deleteReserve(ReserveDTO reserveDTO) {
		movieDAO.deleteReserve(reserveDTO);
	}
	
	@Override
	public List<MovieDTO> selectSearchedMovieList(String keyword) {
		return movieDAO.selectSearchedMovieList(keyword);
	}
}