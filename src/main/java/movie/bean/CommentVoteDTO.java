package movie.bean;

public class CommentVoteDTO {
	private String id;
	private String vote_target;
	private String movie_code;
	private String vote_type;
	
	public String getId() {
		return id;
	}
	
	public String getVote_target() {
		return vote_target;
	}
	
	public String getMovie_code() {
		return movie_code;
	}
	
	public String getVote_type() {
		return vote_type;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setVote_target(String vote_target) {
		this.vote_target = vote_target;
	}
	
	public void setMovie_code(String movie_code) {
		this.movie_code = movie_code;
	}
	
	public void setVote_type(String vote_type) {
		this.vote_type = vote_type;
	}
}