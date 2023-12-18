package member.bean;

public class WatchedMovieDTO {
	private String reserve_code;
	private String id;
	private String movie_code;
	private String main_region;
	private String sub_region;
	private String date_;
	private String time_;
	private int personnel;
	
	public String getReserve_code() {
		return reserve_code;
	}
	
	public String getId() {
		return id;
	}
	
	public String getMovie_code() {
		return movie_code;
	}
	
	public String getMain_region() {
		return main_region;
	}
	
	public String getSub_region() {
		return sub_region;
	}
	
	public String getDate_() {
		return date_;
	}
	
	public String getTime_() {
		return time_;
	}
	
	public int getPersonnel() {
		return personnel;
	}
	
	public void setReserve_code(String reserve_code) {
		this.reserve_code = reserve_code;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setMovie_code(String movie_code) {
		this.movie_code = movie_code;
	}
	
	public void setMain_region(String main_region) {
		this.main_region = main_region;
	}
	
	public void setSub_region(String sub_region) {
		this.sub_region = sub_region;
	}
	
	public void setDate_(String date_) {
		this.date_ = date_;
	}
	
	public void setTime_(String time_) {
		this.time_ = time_;
	}
	
	public void setPersonnel(int personnel) {
		this.personnel = personnel;
	}
}