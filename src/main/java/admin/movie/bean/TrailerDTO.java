package admin.movie.bean;

import java.util.ArrayList;
import java.util.List;

import admin.movie.other.FileWrapper;
import admin.movie.other.IfNotURL_convert;
import admin.movie.other.SubMovieInfo;

public class TrailerDTO implements SubMovieInfo {
	private String code;
	private String title;
	private String video;
	private String img;
	
	@Override
	public boolean isEmpty() {
		return title == null;
	}
	
	@Override
	public void ifNotURL_convert(IfNotURL_convert ifNotURL_convert, String insert_type) {
		video = ifNotURL_convert.convert("trailer/video", code, video, insert_type);
		img = ifNotURL_convert.convert("trailer/img", code, img, insert_type);
	}
	
	@Override
	public List<FileWrapper> getFiles() {
		List<FileWrapper> fileWrapperList = new ArrayList<>();
		fileWrapperList.add(new FileWrapper(video));
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
				title = value;
				break;
			case 2:
				video = value;
				break;
			case 3:
				img = value;
		}
	}
	
	
	public String getCode() {
		return code;
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
	
	public void setCode(String code) {
		this.code = code;
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