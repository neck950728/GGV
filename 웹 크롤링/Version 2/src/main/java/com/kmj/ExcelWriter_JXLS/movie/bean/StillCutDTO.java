package com.kmj.ExcelWriter_JXLS.movie.bean;

public class StillCutDTO implements SubMovieInfo {
	private String key;
	private String stillCut;
	
	@Override
	public String get(int index) {
		String value = null;
		
		switch(index) {
			case 0:
				value = key;
				break;
			case 1:
				value = stillCut;
		}
		
		return value;
	}
	
	
	public String getKey() {
		return key;
	}

	public String getStillCut() {
		return stillCut;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setStillCut(String stillCut) {
		this.stillCut = stillCut;
	}
}