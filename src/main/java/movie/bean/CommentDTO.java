package movie.bean;

public class CommentDTO {
	private String id;
	private String gender;
	private String age_group;
	private String movie_code;
	private int score;
	private String review;
	private String dateCreated;
	private int sympathy;
	private int notSympathy;
	
	public String getId() {
		return id;
	}
	
	public String getGender() {
		return gender;
	}
	
	public String getAge_group() {
		return age_group;
	}
	
	public String getMovie_code() {
		return movie_code;
	}
	
	public int getScore() {
		return score;
	}
	
	public String getReview() {
		return review;
	}
	
	public String getDateCreated() {
		return dateCreated;
	}
	
	public int getSympathy() {
		return sympathy;
	}
	
	public int getNotSympathy() {
		return notSympathy;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public void setAge_group(String age_group) {
		this.age_group = age_group;
	}
	
	public void setMovie_code(String movie_code) {
		this.movie_code = movie_code;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void setReview(String review) {
		this.review = review;
	}
	
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public void setSympathy(int sympathy) {
		this.sympathy = sympathy;
	}
	
	public void setNotSympathy(int notSympathy) {
		this.notSympathy = notSympathy;
	}
}