package com.kmj.ExcelWriter_JXLS.movie.bean;

public class ActorDTO implements SubMovieInfo {
	private String key;
	private String ko_name;
	private String en_name;
	private String role_;
	private String img;
	
	@Override
	public String get(int index) {
		String value = null;
		
		switch(index) {
			case 0:
				value = key;
				break;
			case 1:
				value = ko_name;
				break;
			case 2:
				value = en_name;
				break;
			case 3:
				value = role_;
				break;
			case 4:
				value = img;
		}
		
		return value;
	}
	
	
	public String getKey() {
		return key;
	}

	public String getKo_name() {
		return ko_name;
	}

	public String getEn_name() {
		return en_name;
	}

	public String getRole_() {
		return role_;
	}

	public String getImg() {
		return img;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setKo_name(String ko_name) {
		this.ko_name = ko_name;
	}

	public void setEn_name(String en_name) {
		this.en_name = en_name;
	}

	public void setRole_(String role_) {
		this.role_ = role_;
	}

	public void setImg(String img) {
		this.img = img;
	}
}