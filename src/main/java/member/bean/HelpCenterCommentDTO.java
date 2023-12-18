package member.bean;

public class HelpCenterCommentDTO {
	private String identification_code;
	private String article_code;
	private String comment_;
	private String date_;
	
	public String getIdentification_code() {
		return identification_code;
	}
	
	public String getArticle_code() {
		return article_code;
	}
	
	public String getComment_() {
		return comment_;
	}
	
	public String getDate_() {
		return date_;
	}
	
	public void setIdentification_code(String identification_code) {
		this.identification_code = identification_code;
	}
	
	public void setArticle_code(String article_code) {
		this.article_code = article_code;
	}
	
	public void setComment_(String comment_) {
		this.comment_ = comment_;
	}
	
	public void setDate_(String date_) {
		this.date_ = date_;
	}
}