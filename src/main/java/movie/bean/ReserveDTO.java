package movie.bean;

public class ReserveDTO {
	private String reserve_code;
	private String id;
	private String screeningInfo_identification;
	private String movie_code;
	private String poster;
	private String title;
	private String main_region;
	private String sub_region;
	private String date_;
	private String time_;
	private int personnel;
	private String seat;
	private String total_price;
	
	public String getReserve_code() {
		return reserve_code;
	}
	
	public String getId() {
		return id;
	}
	
	public String getScreeningInfo_identification() {
		return screeningInfo_identification;
	}
	
	public String getMovie_code() {
		return movie_code;
	}
	
	public String getPoster() {
		return poster;
	}
	
	public String getTitle() {
		return title;
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

	public String getSeat() {
		return seat;
	}
	
	public String getTotal_price() {
		return total_price;
	}
	
	public void setReserve_code(String reserve_code) {
		this.reserve_code = reserve_code;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setScreeningInfo_identification(String screeningInfo_identification) {
		this.screeningInfo_identification = screeningInfo_identification;
	}
	
	public void setMovie_code(String movie_code) {
		this.movie_code = movie_code;
	}
	
	public void setPoster(String poster) {
		this.poster = poster;
	}
	
	public void setTitle(String title) {
		this.title = title;
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
	
	public void setSeat(String seat) {
		this.seat = seat;
	}
	
	public void setTotal_price(String total_price) {
		this.total_price = total_price;
	}
}