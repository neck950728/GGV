package movie.bean;

public class ActorDTO {
	private String key;
	private String ko_name;
	private String en_name;
	private String role_;
	private String role_detail;
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
	
	public String getRole_() {
		return role_;
	}
	
	public String getRole_detail() {
		return role_detail;
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
	
	public void setRole_detail(String role_detail) {
		this.role_detail = role_detail;
	}
	
	public void setImg(String img) {
		this.img = img;
	}
}