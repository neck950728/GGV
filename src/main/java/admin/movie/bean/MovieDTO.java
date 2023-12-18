package admin.movie.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import admin.movie.other.IfNotURL_convert;
import admin.movie.other.SubMovieInfo;

public class MovieDTO {
	private String code;
	private String title;
	private String genre;
	private String nation;
	private String showtimes;
	private String premier;
	private String grade;
	private String poster;
	private CopyOnWriteArrayList<StillCutDTO> stillCut_list; // CopyOnWriteArrayList : ConcurrentModificationException 방지
	private CopyOnWriteArrayList<TrailerDTO> trailer_list;
	private CopyOnWriteArrayList<DirectorDTO> director_list;
	private CopyOnWriteArrayList<ActorDTO> actor_list;
	private String story;
	private double reserveRate;
	private double score;
	private int like_;
	private List<ScreeningInfoDTO> screeningInfo_list = new ArrayList<>();
	
	
	public void ifNotURL_convert(IfNotURL_convert ifNotURL_convert, String insert_type) {
		poster = ifNotURL_convert.convert("poster", code, poster, insert_type);
	}
	
	public void set(int index, String value) {
		switch(index) {
			case 0:
				code = value;
				break;
			case 1:
				title = value;
				break;
			case 2:
				genre = value;
				break;
			case 3:
				nation = value;
				break;
			case 4:
				showtimes = value;
				break;
			case 5:
				premier = value;
				break;
			case 6:
				grade = value;
				break;
			case 7:
				poster = value;
				break;
			case 8:
				story = value;
		}
	}
	
	public void setSubMovieInfo_list(int index, CopyOnWriteArrayList<? extends SubMovieInfo> subMovieInfo_list) {
		switch(index) {
			case 1:
				stillCut_list = (CopyOnWriteArrayList<StillCutDTO>)subMovieInfo_list;
				break;
			case 2:
				trailer_list = (CopyOnWriteArrayList<TrailerDTO>)subMovieInfo_list;
				break;
			case 3:
				director_list = (CopyOnWriteArrayList<DirectorDTO>)subMovieInfo_list;
				break;
			case 4:
				actor_list = (CopyOnWriteArrayList<ActorDTO>)subMovieInfo_list;
		}
	}
	
	public CopyOnWriteArrayList<? extends SubMovieInfo> getSubMovieInfo_list(int index){
		CopyOnWriteArrayList<? extends SubMovieInfo> subMovieInfo_list = null;
		
		switch(index) {
			case 1:
				subMovieInfo_list = stillCut_list;
				break;
			case 2:
				subMovieInfo_list = trailer_list;
				break;
			case 3:
				subMovieInfo_list = director_list;
				break;
			case 4:
				subMovieInfo_list = actor_list;
		}
		
		return subMovieInfo_list;
	}
	
	
	public String getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public String getGenre() {
		return genre;
	}

	public String getNation() {
		return nation;
	}

	public String getShowtimes() {
		return showtimes;
	}

	public String getPremier() {
		return premier;
	}

	public String getGrade() {
		return grade;
	}

	public String getPoster() {
		return poster;
	}

	public CopyOnWriteArrayList<StillCutDTO> getStillCut_list() {
		return stillCut_list;
	}

	public CopyOnWriteArrayList<TrailerDTO> getTrailer_list() {
		return trailer_list;
	}

	public CopyOnWriteArrayList<DirectorDTO> getDirector_list() {
		return director_list;
	}

	public CopyOnWriteArrayList<ActorDTO> getActor_list() {
		return actor_list;
	}

	public String getStory() {
		return story;
	}

	public double getReserveRate() {
		return reserveRate;
	}

	public double getScore() {
		return score;
	}

	public int getLike_() {
		return like_;
	}
	
	public List<ScreeningInfoDTO> getScreeningInfo_list() {
		return screeningInfo_list;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public void setShowtimes(String showtimes) {
		this.showtimes = showtimes;
	}

	public void setPremier(String premier) {
		this.premier = premier;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public void setStillCut_list(CopyOnWriteArrayList<StillCutDTO> stillCut_list) {
		this.stillCut_list = stillCut_list;
	}

	public void setTrailer_list(CopyOnWriteArrayList<TrailerDTO> trailer_list) {
		this.trailer_list = trailer_list;
	}

	public void setDirector_list(CopyOnWriteArrayList<DirectorDTO> director_list) {
		this.director_list = director_list;
	}

	public void setActor_list(CopyOnWriteArrayList<ActorDTO> actor_list) {
		this.actor_list = actor_list;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public void setReserveRate(double reserveRate) {
		this.reserveRate = reserveRate;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public void setLike_(int like_) {
		this.like_ = like_;
	}

	public void setScreeningInfo_list(List<ScreeningInfoDTO> screeningInfo_list) {
		this.screeningInfo_list = screeningInfo_list;
	}
}