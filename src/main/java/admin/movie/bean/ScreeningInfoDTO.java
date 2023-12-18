package admin.movie.bean;

public class ScreeningInfoDTO {
	private String identification_code;
	private String movie_code;
	private String main_region;
	private String sub_region;
	private String screening_date;
	private String screening_time;
	private String reserved_seat;
	private int remaining_seat_count = 100;
	
	public String getIdentification_code() {
		return identification_code;
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
	
	public String getScreening_date() {
		return screening_date;
	}
	
	public String getScreening_time() {
		return screening_time;
	}
	
	public String getReserved_seat() {
		return reserved_seat;
	}

	public int getRemaining_seat_count() {
		return remaining_seat_count;
	}
	
	public void setIdentification_code(String identification_code) {
		this.identification_code = identification_code;
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
	
	public void setScreening_date(String screening_date) {
		this.screening_date = screening_date;
	}
	
	public void setScreening_time(String screening_time) {
		this.screening_time = screening_time;
	}
	
	public void setReserved_seat(String reserved_seat) {
		this.reserved_seat = reserved_seat;
	}
	
	public void setRemaining_seat_count(int remaining_seat_count) {
		this.remaining_seat_count = remaining_seat_count;
	}
}