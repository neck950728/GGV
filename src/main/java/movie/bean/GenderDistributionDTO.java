package movie.bean;

public class GenderDistributionDTO {
	private int man;
	private int woman;
	private double man_score;
	private double woman_score;
	
	public int getMan() {
		return man;
	}
	
	public int getWoman() {
		return woman;
	}
	
	public double getMan_score() {
		return man_score;
	}
	
	public double getWoman_score() {
		return woman_score;
	}
	
	public void setMan(int man) {
		this.man = man;
	}
	
	public void setWoman(int woman) {
		this.woman = woman;
	}
	
	public void setMan_score(double man_score) {
		this.man_score = man_score;
	}
	
	public void setWoman_score(double woman_score) {
		this.woman_score = woman_score;
	}
}