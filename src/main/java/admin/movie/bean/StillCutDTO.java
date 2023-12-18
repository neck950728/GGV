package admin.movie.bean;

import java.util.ArrayList;
import java.util.List;

import admin.movie.other.FileWrapper;
import admin.movie.other.IfNotURL_convert;
import admin.movie.other.SubMovieInfo;

public class StillCutDTO implements SubMovieInfo {
	private String code;
	private String stillCut;
	
	@Override
	public boolean isEmpty() {
		return stillCut == null;
	}
	
	@Override
	public void ifNotURL_convert(IfNotURL_convert ifNotURL_convert, String insert_type) {
		stillCut = ifNotURL_convert.convert("stillCut", code, stillCut, insert_type);
	}
	
	@Override
	public List<FileWrapper> getFiles() {
		List<FileWrapper> fileWrapperList = new ArrayList<>();
		fileWrapperList.add(new FileWrapper(stillCut));
		
		return fileWrapperList;
	}
	
	@Override
	public void set(int index, String value) {
		switch(index) {
			case 0:
				code = value;
				break;
			case 1:
				stillCut = value;
		}
	}
	
	
	public String getCode() {
		return code;
	}
	
	public String getStillCut() {
		return stillCut;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public void setStillCut(String stillCut) {
		this.stillCut = stillCut;
	}
}