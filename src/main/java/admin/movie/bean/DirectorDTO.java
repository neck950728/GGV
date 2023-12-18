package admin.movie.bean;

import java.util.ArrayList;
import java.util.List;

import admin.movie.other.FileWrapper;
import admin.movie.other.IfNotURL_convert;
import admin.movie.other.SubMovieInfo;

public class DirectorDTO implements SubMovieInfo {
	private String code;
	private String ko_name;
	private String en_name;
	private String img;
	
	@Override
	public boolean isEmpty() {
		return ko_name == null;
	}
	
	@Override
	public void ifNotURL_convert(IfNotURL_convert ifNotURL_convert, String insert_type) {
		img = ifNotURL_convert.convert("director", code, img, insert_type);
	}
	
	@Override
	public List<FileWrapper> getFiles() {
		List<FileWrapper> fileWrapperList = new ArrayList<>();
		fileWrapperList.add(new FileWrapper(img));
		
		return fileWrapperList;
	}
	
	@Override
	public void set(int index, String value) {
		switch(index) {
			case 0:
				code = value;
				break;
			case 1:
				ko_name = value;
				break;
			case 2:
				en_name = value;
				break;
			case 3:
				img = value;
		}
	}
	
	
	public String getCode() {
		return code;
	}

	public String getKo_name() {
		return ko_name;
	}

	public String getEn_name() {
		return en_name;
	}

	public String getImg() {
		return img;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setKo_name(String ko_name) {
		this.ko_name = ko_name;
	}

	public void setEn_name(String en_name) {
		this.en_name = en_name;
	}

	public void setImg(String img) {
		this.img = img;
	}
}