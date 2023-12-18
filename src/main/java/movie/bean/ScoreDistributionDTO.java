package movie.bean;

public class ScoreDistributionDTO {
	private double score1;
	private double score2;
	private double score3;
	private double score4;
	private double score5;
	private double score6;
	private double score7;
	private double score8;
	private double score9;
	private double score10;
	
	public void set(int index, double value) {
		switch(index) {
			case 1:
				score1 = value;
				break;
			case 2:
				score2 = value;
				break;
			case 3:
				score3 = value;
				break;
			case 4:
				score4 = value;
				break;
			case 5:
				score5 = value;
				break;
			case 6:
				score6 = value;
				break;
			case 7:
				score7 = value;
				break;
			case 8:
				score8 = value;
				break;
			case 9:
				score9 = value;
				break;
			case 10:
				score10 = value;
		}
	}
	
	
	public double getScore1() {
		return score1;
	}

	public double getScore2() {
		return score2;
	}

	public double getScore3() {
		return score3;
	}

	public double getScore4() {
		return score4;
	}

	public double getScore5() {
		return score5;
	}

	public double getScore6() {
		return score6;
	}

	public double getScore7() {
		return score7;
	}

	public double getScore8() {
		return score8;
	}

	public double getScore9() {
		return score9;
	}

	public double getScore10() {
		return score10;
	}

	public void setScore1(double score1) {
		this.score1 = score1;
	}

	public void setScore2(double score2) {
		this.score2 = score2;
	}

	public void setScore3(double score3) {
		this.score3 = score3;
	}

	public void setScore4(double score4) {
		this.score4 = score4;
	}

	public void setScore5(double score5) {
		this.score5 = score5;
	}

	public void setScore6(double score6) {
		this.score6 = score6;
	}

	public void setScore7(double score7) {
		this.score7 = score7;
	}

	public void setScore8(double score8) {
		this.score8 = score8;
	}

	public void setScore9(double score9) {
		this.score9 = score9;
	}

	public void setScore10(double score10) {
		this.score10 = score10;
	}
}