package movie.other;

import movie.bean.AgeGroupDistributionDTO;
import movie.bean.GenderDistributionDTO;
import movie.bean.ScoreDistributionDTO;

public class CommentWrapper {
	ScoreDistributionDTO scoreDistributionDTO;
	GenderDistributionDTO genderDistributionDTO;
	AgeGroupDistributionDTO ageGroupDistributionDTO;
	
	public ScoreDistributionDTO getScoreDistributionDTO() {
		return scoreDistributionDTO;
	}
	
	public GenderDistributionDTO getGenderDistributionDTO() {
		return genderDistributionDTO;
	}
	
	public AgeGroupDistributionDTO getAgeGroupDistributionDTO() {
		return ageGroupDistributionDTO;
	}
	
	public void setScoreDistributionDTO(ScoreDistributionDTO scoreDistributionDTO) {
		this.scoreDistributionDTO = scoreDistributionDTO;
	}
	
	public void setGenderDistributionDTO(GenderDistributionDTO genderDistributionDTO) {
		this.genderDistributionDTO = genderDistributionDTO;
	}
	
	public void setAgeGroupDistributionDTO(AgeGroupDistributionDTO ageGroupDistributionDTO) {
		this.ageGroupDistributionDTO = ageGroupDistributionDTO;
	}
}