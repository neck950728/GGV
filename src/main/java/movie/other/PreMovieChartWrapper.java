package movie.other;

import java.util.List;
import java.util.Map;

import admin.movie.bean.MovieDTO;

public class PreMovieChartWrapper {
	List<MovieDTO> recommendMovieList;
	List<String> preMovieChartOrder;
	Map<String, List<MovieDTO>> preMovieChart;
	
	public List<MovieDTO> getRecommendMovieList() {
		return recommendMovieList;
	}
	
	public List<String> getPreMovieChartOrder() {
		return preMovieChartOrder;
	}
	
	public Map<String, List<MovieDTO>> getPreMovieChart() {
		return preMovieChart;
	}
	
	public void setRecommendMovieList(List<MovieDTO> recommendMovieList) {
		this.recommendMovieList = recommendMovieList;
	}
	
	public void setPreMovieChartOrder(List<String> preMovieChartOrder) {
		this.preMovieChartOrder = preMovieChartOrder;
	}
	
	public void setPreMovieChart(Map<String, List<MovieDTO>> preMovieChart) {
		this.preMovieChart = preMovieChart;
	}
}