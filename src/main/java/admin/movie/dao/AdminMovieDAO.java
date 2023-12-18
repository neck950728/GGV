package admin.movie.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import movie.bean.ReserveDTO;

@Repository
public class AdminMovieDAO {
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public int movieCodeDuplicateCheck(String code) {
		return sqlSession.selectOne("mybatis.adminMovieMapper.movieCodeDuplicateCheck", code);
	}
	
	public int identificationCodeDuplicateCheck(String code) {
		return sqlSession.selectOne("mybatis.adminMovieMapper.identificationCodeDuplicateCheck", code);
	}
	
	public void insertMovie(MovieDTO movieDTO) {
		sqlSession.insert("mybatis.adminMovieMapper.insertMovie", movieDTO);
		
		if(movieDTO.getStillCut_list().size() > 0) {
			Map<String, Object> stillCutList = new HashMap<>();
			stillCutList.put("stillCutList", movieDTO.getStillCut_list());

			sqlSession.insert("mybatis.adminMovieMapper.insertStillCutList", stillCutList);
		}
		
		if(movieDTO.getTrailer_list().size() > 0) {
			Map<String, Object> trailerList = new HashMap<>();
			trailerList.put("trailerList", movieDTO.getTrailer_list());
			
			sqlSession.insert("mybatis.adminMovieMapper.insertTrailerList", trailerList);
		}
		
		if(movieDTO.getDirector_list().size() > 0) {
			Map<String, Object> directorList = new HashMap<>();
			directorList.put("directorList", movieDTO.getDirector_list());
			
			sqlSession.insert("mybatis.adminMovieMapper.insertDirectorList", directorList);
		}

		if(movieDTO.getActor_list().size() > 0) {
			Map<String, Object> actorList = new HashMap<>();
			actorList.put("actorList", movieDTO.getActor_list());
			
			sqlSession.insert("mybatis.adminMovieMapper.insertActorList", actorList);
		}
		
		if(movieDTO.getScreeningInfo_list().size() > 0) {
			Map<String, Object> screeningInfoList = new HashMap<>();
			screeningInfoList.put("screeningInfoList", movieDTO.getScreeningInfo_list());
			
			sqlSession.insert("mybatis.adminMovieMapper.insertScreeningInfoList", screeningInfoList);
		}
	}
	
	
	public int selectTotalMovieCount() {
		return sqlSession.selectOne("mybatis.adminMovieMapper.selectTotalMovieCount");
	}
	
	public List<MovieDTO> selectCurPageMovieList(int startNum, int endNum) {
		Map<String, Object> param = new HashMap<>();
		param.put("startNum", startNum);
		param.put("endNum", endNum);
		
		List<MovieDTO> curPageMovieList = sqlSession.selectList("mybatis.adminMovieMapper.selectCurPageMovieList", param);
		for(MovieDTO movieDTO : curPageMovieList) {
			CopyOnWriteArrayList<DirectorDTO> director_list = new CopyOnWriteArrayList<>();
			director_list.addAll(sqlSession.selectList("mybatis.movieMapper.selectDirectorList", movieDTO.getCode()));
			movieDTO.setDirector_list(director_list);
		}
		
		return curPageMovieList;
	}
	
	public List<MovieDTO> selectSearchedMovieList(String keyword) {
		List<MovieDTO> searchedMovieList = sqlSession.selectList("mybatis.adminMovieMapper.selectSearchedMovieList", keyword);
		for(MovieDTO movieDTO : searchedMovieList) {
			CopyOnWriteArrayList<DirectorDTO> director_list = new CopyOnWriteArrayList<>();
			director_list.addAll(sqlSession.selectList("mybatis.movieMapper.selectDirectorList", movieDTO.getCode()));
			movieDTO.setDirector_list(director_list);
		}
		
		return searchedMovieList;
	}
	
	public MovieDTO selectMovie_includingSubMovieInfo(String code) {
		MovieDTO movieDTO = sqlSession.selectOne("mybatis.movieMapper.selectMovie", code);
		
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
	
	public void deleteMovie(String code) {
		sqlSession.delete("mybatis.adminMovieMapper.deleteMovie", code);
		sqlSession.delete("mybatis.adminMovieMapper.deleteStillCut", code);
		sqlSession.delete("mybatis.adminMovieMapper.deleteTrailer", code);
		sqlSession.delete("mybatis.adminMovieMapper.deleteDirector", code);
		sqlSession.delete("mybatis.adminMovieMapper.deleteActor", code);
		sqlSession.delete("mybatis.adminMovieMapper.deleteScreeningInfo", code);
		sqlSession.delete("mybatis.adminMovieMapper.deleteComment", code);
		sqlSession.delete("mybatis.adminMovieMapper.deleteCommentVote", code);
		sqlSession.delete("mybatis.adminMovieMapper.deleteReserve", code);
		sqlSession.delete("mybatis.adminMovieMapper.deleteWishList", code);
	}
	
	public MovieDTO selectMovieForModify(String code) {
		MovieDTO movieDTO = selectMovie_includingSubMovieInfo(code);
		movieDTO.setScreeningInfo_list(sqlSession.selectList("mybatis.adminMovieMapper.selectScreeningInfoList", code));
		
		return movieDTO;
	}
	
	public void updateMovie(MovieDTO new_movieDTO) {
		sqlSession.update("mybatis.adminMovieMapper.updateMovie", new_movieDTO);
		
		sqlSession.delete("mybatis.adminMovieMapper.deleteStillCut", new_movieDTO.getCode());
		if(new_movieDTO.getStillCut_list().size() > 0) {
			Map<String, Object> stillCutList = new HashMap<>();
			stillCutList.put("stillCutList", new_movieDTO.getStillCut_list());
			
			sqlSession.insert("mybatis.adminMovieMapper.insertStillCutList", stillCutList);
		}
		
		sqlSession.delete("mybatis.adminMovieMapper.deleteTrailer", new_movieDTO.getCode());
		if(new_movieDTO.getTrailer_list().size() > 0) {
			Map<String, Object> trailerList = new HashMap<>();
			trailerList.put("trailerList", new_movieDTO.getTrailer_list());
			
			sqlSession.insert("mybatis.adminMovieMapper.insertTrailerList", trailerList);
		}
		
		sqlSession.delete("mybatis.adminMovieMapper.deleteDirector", new_movieDTO.getCode());
		if(new_movieDTO.getDirector_list().size() > 0) {
			Map<String, Object> directorList = new HashMap<>();
			directorList.put("directorList", new_movieDTO.getDirector_list());
			
			sqlSession.insert("mybatis.adminMovieMapper.insertDirectorList", directorList);
		}
		
		sqlSession.delete("mybatis.adminMovieMapper.deleteActor", new_movieDTO.getCode());
		if(new_movieDTO.getActor_list().size() > 0) {
			Map<String, Object> actorList = new HashMap<>();
			actorList.put("actorList", new_movieDTO.getActor_list());
			
			sqlSession.insert("mybatis.adminMovieMapper.insertActorList", actorList);
		}
		
		
		List<String> identification_code_list = new ArrayList<>();
		for(ScreeningInfoDTO screeningInfoDTO : new_movieDTO.getScreeningInfo_list()) {
			identification_code_list.add(screeningInfoDTO.getIdentification_code());
		}
		
		List<ReserveDTO> reserveList = sqlSession.selectList("mybatis.adminMovieMapper.selectReserveList", new_movieDTO.getCode());
		for(ReserveDTO reserveDTO : reserveList) {
			if(!identification_code_list.contains(reserveDTO.getScreeningInfo_identification())) {
				sqlSession.delete("mybatis.adminMovieMapper.deleteReserveForUpdateMovie", reserveDTO.getScreeningInfo_identification());
			}
		}
		
		sqlSession.delete("mybatis.adminMovieMapper.deleteScreeningInfo", new_movieDTO.getCode());
		if(new_movieDTO.getScreeningInfo_list().size() > 0) {
			Map<String, Object> screeningInfoList = new HashMap<>();
			screeningInfoList.put("screeningInfoList", new_movieDTO.getScreeningInfo_list());
			
			sqlSession.insert("mybatis.adminMovieMapper.insertScreeningInfoList", screeningInfoList);
		}
	}
}