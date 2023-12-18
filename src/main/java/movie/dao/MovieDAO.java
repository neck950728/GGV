package movie.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import admin.movie.bean.ActorDTO;
import admin.movie.bean.DirectorDTO;
import admin.movie.bean.MovieDTO;
import admin.movie.bean.ScreeningInfoDTO;
import admin.movie.bean.StillCutDTO;
import admin.movie.bean.TrailerDTO;
import member.bean.MemberDTO;
import movie.bean.AgeGroupDistributionDTO;
import movie.bean.CommentDTO;
import movie.bean.CommentVoteDTO;
import movie.bean.GenderDistributionDTO;
import movie.bean.ReserveDTO;
import movie.bean.ScoreDistributionDTO;

@Repository
public class MovieDAO {
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public MovieDTO selectRandomMovie() {
		MovieDTO movieDTO = null;
		movieDTO = sqlSession.selectOne("mybatis.movieMapper.selectRandomMovie");
		
		CopyOnWriteArrayList<TrailerDTO> trailer_list = new CopyOnWriteArrayList<>();
		trailer_list.addAll(sqlSession.selectList("mybatis.movieMapper.selectTrailerList", movieDTO.getCode()));
		movieDTO.setTrailer_list(trailer_list);
		
		return movieDTO;
	}
	
	
	public List<MovieDTO> selectMovieChart(){
		List<MovieDTO> movieChart = sqlSession.selectList("mybatis.movieMapper.selectMovieChart");
		for(MovieDTO movieDTO : movieChart) {
			movieDTO.setScreeningInfo_list(sqlSession.selectList("mybatis.movieMapper.selectScreeningInfoList", movieDTO.getCode()));
		}
		
		return movieChart;
	}
	
	
	public List<MovieDTO> selectRecommendMovieListFromPreMovieChart(){
		List<MovieDTO> recommendMovieList = sqlSession.selectList("mybatis.movieMapper.selectRecommendMovieListFromPreMovieChart");
		for(MovieDTO movieDTO : recommendMovieList) {
			movieDTO.setScreeningInfo_list(sqlSession.selectList("mybatis.movieMapper.selectScreeningInfoList", movieDTO.getCode()));
		}
		
		return recommendMovieList;
	}
	
	public Map<String, List<MovieDTO>> selectPreMovieChart(){
		Map<String, List<MovieDTO>> preMovieChart = new LinkedHashMap<>();
		
		List<String> premierList = sqlSession.selectList("mybatis.movieMapper.selectPremierListFromPreMovieChart");
		for(String premier : premierList) {
			List<MovieDTO> movieList = sqlSession.selectList("mybatis.movieMapper.selectPreMovieChart", premier);
			for(MovieDTO movieDTO : movieList) {
				movieDTO.setScreeningInfo_list(sqlSession.selectList("mybatis.movieMapper.selectScreeningInfoList", movieDTO.getCode()));
			}
			
			preMovieChart.put(premier , movieList);
		}
		
		return preMovieChart;
	}
	
	
	public void addLike(String code) {
		sqlSession.update("mybatis.movieMapper.addLike", code);
	}
	
	public void subtractLike(String code) {
		sqlSession.update("mybatis.movieMapper.subtractLike", code);
	}
	
	
	public List<MovieDTO> selectTop5MovieListForTrailer(){
		List<MovieDTO> top5MovieList = new ArrayList<>();
		
		int i = 1;
		while(true) {
			MovieDTO movieDTO = sqlSession.selectOne("mybatis.movieMapper.selectTopRankMovie", i);
			
			CopyOnWriteArrayList<TrailerDTO> trailerList = new CopyOnWriteArrayList<>();
			trailerList.addAll(sqlSession.selectList("mybatis.movieMapper.selectTrailerList", movieDTO.getCode()));
			
			if(trailerList.size() > 0) {
				movieDTO.setTrailer_list(trailerList);
				movieDTO.setScreeningInfo_list(sqlSession.selectList("mybatis.movieMapper.selectScreeningInfoList", movieDTO.getCode()));
				top5MovieList.add(movieDTO);
				
				if(top5MovieList.size() == 5) {
					break;
				}
			}
			
			i++;
		}
		
		return top5MovieList;
	}
	
	public int selectTotalTrailerCount() {
		return sqlSession.selectOne("mybatis.movieMapper.selectTotalTrailerCount");
	}
	
	private MovieDTO selectMovie(String code) {
		MovieDTO movieDTO = sqlSession.selectOne("mybatis.movieMapper.selectMovie", code);
		movieDTO.setScreeningInfo_list(sqlSession.selectList("mybatis.movieMapper.selectScreeningInfoList", movieDTO.getCode()));
		
		return movieDTO;
	}
	
	public List<MovieDTO> selectCurPageTrailerList(int startNum, int endNum){
		List<MovieDTO> curPageTrailerList = new ArrayList<>();
		
		Map<String, Object> param = new HashMap<>();
		param.put("startNum", startNum);
		param.put("endNum", endNum);
		
		List<TrailerDTO> trailerList = sqlSession.selectList("mybatis.movieMapper.selectCurPageTrailerList", param);
		Map<String, MovieDTO> split = new LinkedHashMap<>();
		for(TrailerDTO trailerDTO : trailerList) {
			String code = trailerDTO.getCode();
			if(!split.containsKey(code)) {
				MovieDTO movieDTO = selectMovie(code);
				movieDTO.setTrailer_list(new CopyOnWriteArrayList<>());
				movieDTO.setScreeningInfo_list(sqlSession.selectList("mybatis.movieMapper.selectScreeningInfoList", movieDTO.getCode()));
				
				split.put(code, movieDTO);
				split.get(code).getTrailer_list().add(trailerDTO);
			}else {
				split.get(code).getTrailer_list().add(trailerDTO);
			}
		}
		
		Iterator<String> iter = split.keySet().iterator();
		while(iter.hasNext()) {
			curPageTrailerList.add(split.get(iter.next()));
		}
		
		return curPageTrailerList;
	}
	
	public int selectTotalSearchedTrailerCount(String keyword) {
		return sqlSession.selectOne("mybatis.movieMapper.selectTotalSearchedTrailerCount", keyword);
	}
	
	public List<MovieDTO> selectCurPageSearchedTrailerList(int startNum, int endNum, String keyword){
		List<MovieDTO> curPageSearchedTrailerList = new ArrayList<>();
		
		Map<String, Object> param = new HashMap<>();
		param.put("startNum", startNum);
		param.put("endNum", endNum);
		param.put("keyword", keyword);
		
		List<TrailerDTO> trailerList = sqlSession.selectList("mybatis.movieMapper.selectCurPageSearchedTrailerList", param);
		Map<String, MovieDTO> split = new LinkedHashMap<>();
		for(TrailerDTO trailerDTO : trailerList) {
			String code = trailerDTO.getCode();
			if(!split.containsKey(code)) {
				MovieDTO movieDTO = selectMovie(code);
				movieDTO.setTrailer_list(new CopyOnWriteArrayList<>());
				movieDTO.setScreeningInfo_list(sqlSession.selectList("mybatis.movieMapper.selectScreeningInfoList", movieDTO.getCode()));
				
				split.put(code, movieDTO);
				split.get(code).getTrailer_list().add(trailerDTO);
			}else {
				split.get(code).getTrailer_list().add(trailerDTO);
			}
		}
		
		Iterator<String> iter = split.keySet().iterator();
		while(iter.hasNext()) {
			curPageSearchedTrailerList.add(split.get(iter.next()));
		}
		
		return curPageSearchedTrailerList;
	}
	
	
	public MovieDTO selectMovie_includingSubMovieInfo(String code) {
		MovieDTO movieDTO = selectMovie(code);
		
		CopyOnWriteArrayList<StillCutDTO> stillCut_list = new CopyOnWriteArrayList<>();
		stillCut_list.addAll(sqlSession.selectList("mybatis.movieMapper.selectStillCutList", movieDTO.getCode()));
		movieDTO.setStillCut_list(stillCut_list);
		
		CopyOnWriteArrayList<TrailerDTO> trailer_list = new CopyOnWriteArrayList<>();
		trailer_list.addAll(sqlSession.selectList("mybatis.movieMapper.selectTrailerList", movieDTO.getCode()));
		movieDTO.setTrailer_list(trailer_list);
		
		CopyOnWriteArrayList<DirectorDTO> director_list = new CopyOnWriteArrayList<>();
		director_list.addAll(sqlSession.selectList("mybatis.movieMapper.selectDirectorList", movieDTO.getCode()));
		movieDTO.setDirector_list(director_list);
		
		CopyOnWriteArrayList<ActorDTO> actor_list = new CopyOnWriteArrayList<>();
		actor_list.addAll(sqlSession.selectList("mybatis.movieMapper.selectActorList", movieDTO.getCode()));
		movieDTO.setActor_list(actor_list);
		
		return movieDTO;
	}
	
	public ScoreDistributionDTO selectScoreDistribution(String movie_code) {
		Map<String, Object> param = new HashMap<>();
		param.put("movie_code", movie_code);
		
		int totalCommentCount = sqlSession.selectOne("mybatis.movieMapper.selectTotalCommentCount", movie_code);
		ScoreDistributionDTO scoreDistributionDTO = new ScoreDistributionDTO();
		if(totalCommentCount != 0) {
			for(int i = 1; i <= 10; i++) {
				param.put("score", i);
				int curScoreDistribution = sqlSession.selectOne("mybatis.movieMapper.selectScoreDistribution", param);
				scoreDistributionDTO.set(i, (double)curScoreDistribution / totalCommentCount);
			}
		}
		
		return scoreDistributionDTO;
	}
	
	public GenderDistributionDTO selectGenderDistribution(String movie_code) {
		Map<String, Object> param = new HashMap<>();
		param.put("movie_code", movie_code);
		
		GenderDistributionDTO genderDistributionDTO = new GenderDistributionDTO();
		param.put("gender", "남");
		genderDistributionDTO.setMan(sqlSession.selectOne("mybatis.movieMapper.selectGenderDistribution", param));
		genderDistributionDTO.setMan_score(sqlSession.selectOne("mybatis.movieMapper.selectScorePerGender", param));
		
		param.put("gender", "여");
		genderDistributionDTO.setWoman(sqlSession.selectOne("mybatis.movieMapper.selectGenderDistribution", param));
		genderDistributionDTO.setWoman_score(sqlSession.selectOne("mybatis.movieMapper.selectScorePerGender", param));
		
		return genderDistributionDTO;
	}
	
	public AgeGroupDistributionDTO selectAgeGroupDistribution(String movie_code) {
		Map<String, Object> param = new HashMap<>();
		param.put("movie_code", movie_code);
		
		AgeGroupDistributionDTO ageGroupDistributionDTO = new AgeGroupDistributionDTO();
		param.put("age_group", "10대");
		ageGroupDistributionDTO.setTeenager(sqlSession.selectOne("mybatis.movieMapper.selectAgeGroupDistribution", param));
		ageGroupDistributionDTO.setTeenager_score(sqlSession.selectOne("mybatis.movieMapper.selectScorePerAgeGroup", param));
		
		param.put("age_group", "20대");
		ageGroupDistributionDTO.setTwenty(sqlSession.selectOne("mybatis.movieMapper.selectAgeGroupDistribution", param));
		ageGroupDistributionDTO.setTwenty_score(sqlSession.selectOne("mybatis.movieMapper.selectScorePerAgeGroup", param));
		
		param.put("age_group", "30대");
		ageGroupDistributionDTO.setThirty(sqlSession.selectOne("mybatis.movieMapper.selectAgeGroupDistribution", param));
		ageGroupDistributionDTO.setThirty_score(sqlSession.selectOne("mybatis.movieMapper.selectScorePerAgeGroup", param));
		
		param.put("age_group", "40대 이상");
		ageGroupDistributionDTO.setFortyMore(sqlSession.selectOne("mybatis.movieMapper.selectAgeGroupDistribution", param));
		ageGroupDistributionDTO.setFortyMore_score(sqlSession.selectOne("mybatis.movieMapper.selectScorePerAgeGroup", param));
		
		return ageGroupDistributionDTO;
	}
	
	public int selectTotalCommentCount(String movie_code) {
		return sqlSession.selectOne("mybatis.movieMapper.selectTotalCommentCount", movie_code);
	}
	
	public List<CommentDTO> selectCurPageCommentList_OrderBySympathy(String movie_code, int startNum, int endNum){
		Map<String, Object> param = new HashMap<>();
		param.put("movie_code", movie_code);
		param.put("startNum", startNum);
		param.put("endNum", endNum);
		
		return sqlSession.selectList("mybatis.movieMapper.selectCommentList_OrderBySympathy", param);
	}
	
	public List<CommentDTO> selectCurPageCommentList_OrderByDateCreated(String movie_code, int startNum, int endNum){
		Map<String, Object> param = new HashMap<>();
		param.put("movie_code", movie_code);
		param.put("startNum", startNum);
		param.put("endNum", endNum);
		
		return sqlSession.selectList("mybatis.movieMapper.selectCommentList_OrderByDateCreated", param);
	}
	
	public CommentDTO selectMyComment(String id, String movie_code) {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("movie_code", movie_code);
		
		return sqlSession.selectOne("mybatis.movieMapper.selectMyComment", param);
	}
	
	public void insertComment(CommentDTO commentDTO) {
		MemberDTO memberDTO = sqlSession.selectOne("mybatis.memberMapper.selectMember", commentDTO.getId());
		commentDTO.setGender(memberDTO.getGender());
		
		// ------------------------------------ 만 나이 계산 ------------------------------------
		int age = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREAN);
			Date birthDay = sdf.parse(memberDTO.getBirth());
			
			GregorianCalendar today = new GregorianCalendar();
			GregorianCalendar birth = new GregorianCalendar();
			birth.setTime(birthDay);
			  
			int factor = 0;
			if(today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
				factor = -1;
			}
			age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR) + factor;
		}catch(Exception e) {
			e.printStackTrace();
		}
		// -----------------------------------------------------------------------------------
		
		if(age >= 10) {
			if(age < 20) { commentDTO.setAge_group("10대"); }
			else if(age < 30) { commentDTO.setAge_group("20대"); }
			else if(age < 40) { commentDTO.setAge_group("30대"); }
			else { commentDTO.setAge_group("40대 이상"); }
		}else {
			commentDTO.setAge_group("10대 미만");
		}
		
		sqlSession.insert("mybatis.movieMapper.insertComment", commentDTO);
	}
	
	private void deleteCommentVote(String vote_target, String movie_code) {
		Map<String, Object> param = new HashMap<>();
		param.put("vote_target", vote_target);
		param.put("movie_code", movie_code);
		
		sqlSession.delete("mybatis.movieMapper.deleteCommentVote", param);
	}
	
	public void deleteComment(String id, String movie_code) {
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("movie_code", movie_code);
		
		sqlSession.delete("mybatis.movieMapper.deleteComment", param);
		deleteCommentVote(id, movie_code);
	}
	
	private void addSympathy(CommentVoteDTO commentVoteDTO) {
		sqlSession.update("mybatis.movieMapper.addSympathy", commentVoteDTO);
	}
	
	private void addNotSympathy(CommentVoteDTO commentVoteDTO) {
		sqlSession.update("mybatis.movieMapper.addNotSympathy", commentVoteDTO);
	}
	
	public CommentVoteDTO selectCommentVote(CommentVoteDTO commentVoteDTO) {
		return sqlSession.selectOne("mybatis.movieMapper.selectCommentVote", commentVoteDTO);
	}
	
	public void insertCommentVote(CommentVoteDTO commentVoteDTO) {
		if(commentVoteDTO.getVote_type().equals("sympathy")) {
			addSympathy(commentVoteDTO);
		}else {
			addNotSympathy(commentVoteDTO);
		}
		
		sqlSession.insert("mybatis.movieMapper.insertCommentVote", commentVoteDTO);
	}
	
	
	public List<MovieDTO> selectAllMovieList() {
		List<MovieDTO> movieList = sqlSession.selectList("mybatis.movieMapper.selectAllMovieList");
		for(MovieDTO movieDTO : movieList) {
			movieDTO.setScreeningInfo_list(sqlSession.selectList("mybatis.movieMapper.selectScreeningInfoList", movieDTO.getCode()));
		}
		
		return movieList;
	}
	
	public int reserveCodeDuplicateCheck(String reserve_code) {
		return sqlSession.selectOne("mybatis.movieMapper.selectReserveCodeDuplicateCheck", reserve_code);
	}
	
	public ScreeningInfoDTO selectScreeningInfo(String identification_code) {
		return sqlSession.selectOne("mybatis.movieMapper.selectScreeningInfo", identification_code);
	}
	
	private void addReserved_seat(ReserveDTO reserveDTO) {
		ScreeningInfoDTO screeningInfoDTO = selectScreeningInfo(reserveDTO.getScreeningInfo_identification());
		String reserved_seat = screeningInfoDTO.getReserved_seat();
		if(reserved_seat != null && !reserved_seat.isBlank()) {
			reserved_seat += ", " + reserveDTO.getSeat();
		}else {
			reserved_seat = reserveDTO.getSeat();
		}
		
		screeningInfoDTO.setReserved_seat(reserved_seat);
		sqlSession.update("mybatis.movieMapper.addReserved_seat", screeningInfoDTO);
	}
	
	private void subtractReserved_seat(ReserveDTO reserveDTO) {
		ScreeningInfoDTO screeningInfoDTO = selectScreeningInfo(reserveDTO.getScreeningInfo_identification());
		String old_reservedSeat = screeningInfoDTO.getReserved_seat();
		String new_reservedSeat = "";
		
		old_reservedSeat = old_reservedSeat.replace(reserveDTO.getSeat(), "");
		for(String seat : old_reservedSeat.split(", ")) {
			if(seat.length() != 0) {
				if(new_reservedSeat.length() != 0) {
					new_reservedSeat += ", " + seat;
				}else {
					new_reservedSeat = seat;
				}
			}
		}
		
		screeningInfoDTO.setReserved_seat(new_reservedSeat);
		sqlSession.update("mybatis.movieMapper.subtractReserved_seat", screeningInfoDTO);
	}
	
	private void addRemaining_seat_count(ReserveDTO reserveDTO) {
		sqlSession.update("mybatis.movieMapper.addRemaining_seat_count", reserveDTO);
	}
	
	private void subtractRemaining_seat_count(ReserveDTO reserveDTO) {
		sqlSession.update("mybatis.movieMapper.subtractRemaining_seat_count", reserveDTO);
	}
	
	public void insertReserve(ReserveDTO reserveDTO) {
		sqlSession.insert("mybatis.movieMapper.insertReserve", reserveDTO);

		addReserved_seat(reserveDTO);
		subtractRemaining_seat_count(reserveDTO);
	}
	
	public void deleteReserve(ReserveDTO reserveDTO) {
		sqlSession.delete("mybatis.movieMapper.deleteReserve", reserveDTO);
		
		subtractReserved_seat(reserveDTO);
		addRemaining_seat_count(reserveDTO);
	}
	
	
	public List<MovieDTO> selectSearchedMovieList(String keyword){
		List<MovieDTO> searchedMovieList = sqlSession.selectList("mybatis.movieMapper.selectSearchedMovieList", keyword);
		for(MovieDTO movieDTO : searchedMovieList) {
			movieDTO.setScreeningInfo_list(sqlSession.selectList("mybatis.movieMapper.selectScreeningInfoList", movieDTO.getCode()));
		}
		
		return searchedMovieList;
	}
}