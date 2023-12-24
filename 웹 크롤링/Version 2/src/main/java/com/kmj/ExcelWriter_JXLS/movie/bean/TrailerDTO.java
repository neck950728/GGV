package com.kmj.ExcelWriter_JXLS.movie.bean;

public class TrailerDTO implements SubMovieInfo {
	private String key;
	private String title;
	private String video;
	private String img;
	
	@Override
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
				value = video;
				break;
			case 3:
				value = img;
		}
		
		return value;
	}
	
	
	public String getKey() {
		return key;
	}

	public String getTitle() {
		return title;
	}

	public String getVideo() {
		return video;
	}

	public String getImg() {
		return img;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public void setImg(String img) {
		this.img = img;
	}
}