package com.kmj.ExcelWriter_JXLS.movie.bean;

import java.util.List;

public class MovieDTO {
	private String key;
	private String title;
	private String genre;
	private String nation;
	private String showtimes;
	private String premier;
	private String grade;
	private String poster;
	private List<StillCutDTO> stillCut_list;
	private List<TrailerDTO> trailer_list;
	private List<DirectorDTO> director_list;
	private List<ActorDTO> actor_list;
	private String story;
	
	public String get(int index) {
		String value = null;
		
		switch(index) {
			case 0:
				value = key;
				break;
			case 1:
				value = title;
				break;
			case 2:
				value = genre;
				break;
			case 3:
				value = nation;
				break;
			case 4:
				value = showtimes;
				break;
			case 5:
				value = premier;
				break;
			case 6:
				value = grade;
				break;
			case 7:
				value = poster;
				break;
			case 8:
				value = story;
		}
		
		return value;
	}
	
	
	public String getKey() {
		return key;
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

	public List<StillCutDTO> getStillCut_list() {
		return stillCut_list;
	}

	public List<TrailerDTO> getTrailer_list() {
		return trailer_list;
	}

	public List<DirectorDTO> getDirector_list() {
		return director_list;
	}

	public List<ActorDTO> getActor_list() {
		return actor_list;
	}

	public String getStory() {
		return story;
	}

	public void setKey(String key) {
		this.key = key;
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

	public void setStillCut_list(List<StillCutDTO> stillCut_list) {
		this.stillCut_list = stillCut_list;
	}

	public void setTrailer_list(List<TrailerDTO> trailer_list) {
		this.trailer_list = trailer_list;
	}

	public void setDirector_list(List<DirectorDTO> director_list) {
		this.director_list = director_list;
	}

	public void setActor_list(List<ActorDTO> actor_list) {
		this.actor_list = actor_list;
	}

	public void setStory(String story) {
		this.story = story;
	}
}