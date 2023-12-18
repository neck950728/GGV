package movie.bean;

public class AgeGroupDistributionDTO {
	private int teenager; // 10대
	private int twenty; // 20대
	private int thirty; // 30대
	private int fortyMore; // 40대 이상
	private double teenager_score;
	private double twenty_score;
	private double thirty_score;
	private double fortyMore_score;
	
	public int getTeenager() {
		return teenager;
	}
	
	public int getTwenty() {
		return twenty;
	}
	
	public int getThirty() {
		return thirty;
	}
	
	public int getFortyMore() {
		return fortyMore;
	}
	
	public double getTeenager_score() {
		return teenager_score;
	}
	
	public double getTwenty_score() {
		return twenty_score;
	}
	
	public double getThirty_score() {
		return thirty_score;
	}
	
	public double getFortyMore_score() {
		return fortyMore_score;
	}
	
	public void setTeenager(int teenager) {
		this.teenager = teenager;
	}
	
	public void setTwenty(int twenty) {
		this.twenty = twenty;
	}
	
	public void setThirty(int thirty) {
		this.thirty = thirty;
	}
	
	public void setFortyMore(int fortyMore) {
		this.fortyMore = fortyMore;
	}
	
	public void setTeenager_score(double teenager_score) {
		this.teenager_score = teenager_score;
	}
	
	public void setTwenty_score(double twenty_score) {
		this.twenty_score = twenty_score;
	}
	
	public void setThirty_score(double thirty_score) {
		this.thirty_score = thirty_score;
	}
	
	public void setFortyMore_score(double fortyMore_score) {
		this.fortyMore_score = fortyMore_score;
	}
}