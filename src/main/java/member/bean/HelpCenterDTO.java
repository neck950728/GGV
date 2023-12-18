package member.bean;

public class HelpCenterDTO {
	private String article_code;
	private int num;
	private String id;
	private String title;
	private String content;
	private String date_;
	private int hits;
	private String isPrivate = "FALSE";
	private String[] uploadImages;
	
	public String getArticle_code() {
		return article_code;
	}
	
	public int getNum() {
		return num;
	}
	
	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getDate_() {
		return date_;
	}
	
	public int getHits() {
		return hits;
	}
	
	public String getIsPrivate() {
		return isPrivate;
	}
	
	public String[] getUploadImages() {
		return uploadImages;
	}
	
	public void setArticle_code(String article_code) {
		this.article_code = article_code;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setDate_(String date_) {
		this.date_ = date_;
	}
	
	public void setHits(int hits) {
		this.hits = hits;
	}
	
	public void setIsPrivate(String isPrivate) {
		this.isPrivate = isPrivate;
	}
	
	public void setUploadImages(String[] uploadImages) {
		this.uploadImages = uploadImages;
	}
}