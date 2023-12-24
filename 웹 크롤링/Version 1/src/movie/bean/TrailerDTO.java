package movie.bean;

public class TrailerDTO {
	private String key;
	private String title;
	private String video;
	private String img;
	
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