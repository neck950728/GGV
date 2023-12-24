package movie.bean;

public class DirectorDTO {
	private String key;
	private String ko_name;
	private String en_name;
	private String img;
	
	public String getKey() {
		return key;
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
	
	public void setKey(String key) {
		this.key = key;
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