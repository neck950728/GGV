package movie.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import admin.movie.bean.MovieDTO;
import movie.bean.CommentDTO;
import movie.bean.CommentVoteDTO;
import movie.bean.ReserveDTO;
import movie.other.CommentModuleWrapper;
import movie.other.CommentWrapper;
import movie.other.PreMovieChartWrapper;
import movie.other.TrailerListWrapper;
import movie.service.MovieService;

@Controller
public class MovieController {
	@Autowired
	private MovieService movieService;
	
	
	@RequestMapping(value = "/movie/movieChart")
	public ModelAndView movieChart() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("movieChart_json", new Gson().toJson(movieService.selectMovieChart()));
		modelAndView.addObject("requireMovingBanner", true);
		modelAndView.setViewName("user/movie/movieChart");
		
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/movie/preMovieChart")
	public ModelAndView preMovieChart() {
		PreMovieChartWrapper preMovieChartWrapper = movieService.selectPreMovieChart();
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("recommendMovieList_json", new Gson().toJson(preMovieChartWrapper.getRecommendMovieList()));
		modelAndView.addObject("preMovieChartOrder_json", new Gson().toJson(preMovieChartWrapper.getPreMovieChartOrder()));
		modelAndView.addObject("preMovieChart_json", new Gson().toJson(preMovieChartWrapper.getPreMovieChart()));
		modelAndView.addObject("requireMovingBanner", true);
		modelAndView.setViewName("user/movie/preMovieChart");
		
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/movie/trailer")
	public ModelAndView trailer() {
		TrailerListWrapper trailerListWrapper = movieService.selectTrailerList();
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("top5MovieList_json", new Gson().toJson(trailerListWrapper.getTop5MovieList()));
		modelAndView.addObject("paging", trailerListWrapper.getPaging());
		modelAndView.addObject("trailerList_json", new Gson().toJson(trailerListWrapper.getTrailerList()));
		modelAndView.setViewName("user/movie/trailer");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/movie/trailer/searchTrailer")
	@ResponseBody
	public TrailerListWrapper trailer_searchTrailer(@RequestParam("keyword")String keyword){
		return movieService.searchTrailer(keyword);
	}
	
	@RequestMapping(value = "/movie/trailer/pageMove")
	@ResponseBody
	public TrailerListWrapper trailer_pageMove(@RequestParam("page")int page, @RequestParam(value = "searchKeyword", required = false)String searchKeyword){
		return movieService.trailerPageMove(page, searchKeyword);
	}
	
	
	private ModelAndView detail(String mID, String view_type) {
		MovieDTO movieDTO = movieService.selectMovieIncludingSubMovieInfo(mID);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("movie", movieDTO);
		modelAndView.addObject("movie_json", new Gson().toJson(movieDTO));
		modelAndView.addObject("view_type", view_type);
		modelAndView.setViewName("user/movie/detail/" + view_type);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/movie/detail")
	public ModelAndView detail_main(@RequestParam("mID")String mID) {
		return detail(mID, "main");
	}
	
	@RequestMapping(value = "/movie/detail/trailer")
	public ModelAndView detail_trailer(@RequestParam("mID")String mID) {
		return detail(mID, "trailer");
	}
	
	@RequestMapping(value = "/movie/detail/stillCut")
	public ModelAndView detail_stillCut(@RequestParam("mID")String mID) {
		return detail(mID, "stillCut");
	}
	
	@RequestMapping(value = "/movie/detail/cast")
	public ModelAndView detail_cast(@RequestParam("mID")String mID) {
		return detail(mID, "cast");
	}
	
	@RequestMapping(value = "/movie/detail/comment")
	public ModelAndView detail_comment(@RequestParam("mID")String mID) {
		CommentWrapper commentWrapper = movieService.selectComment(mID);
		
		ModelAndView modelAndView = detail(mID, "comment");
		modelAndView.addObject("scoreDistribution_json", new Gson().toJson(commentWrapper.getScoreDistributionDTO()));
		modelAndView.addObject("genderDistribution_json", new Gson().toJson(commentWrapper.getGenderDistributionDTO()));
		modelAndView.addObject("ageGroupDistribution_json", new Gson().toJson(commentWrapper.getAgeGroupDistributionDTO()));
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/movie/detail/commentModule")
	public ModelAndView detail_commentModule(HttpSession session, @RequestParam("mID")String mID, @RequestParam("page")int page, @RequestParam("order")String order) {
		CommentModuleWrapper commentModuleWrapper = movieService.selectCommentModule(mID, page, order, (String)session.getAttribute("id"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("movie_code", mID);
		modelAndView.addObject("order", commentModuleWrapper.getOrder());
		modelAndView.addObject("paging", commentModuleWrapper.getPaging());
		modelAndView.addObject("commentList_json", new Gson().toJson(commentModuleWrapper.getCommentList()));
		modelAndView.addObject("myComment_json", new Gson().toJson(commentModuleWrapper.getMyComment()));
		modelAndView.setViewName("user(except_include_config)/movie/detail/comment_module");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/movie/detail/commentModule/register")
	@ResponseBody
	public void detail_commentModule_register(HttpSession session, @RequestParam("mID")String mID, CommentDTO commentDTO) {
		movieService.insertComment((String)session.getAttribute("id"), mID, commentDTO);
		
		// return "redirect:/movie/detail/commentModule?mID=" + mID + "&page=1&order=sympathy";
	}
	
	@RequestMapping(value = "/movie/detail/commentModule/delete")
	@ResponseBody
	public void detail_commentModule_delete(@RequestParam("id")String id, @RequestParam("mID")String mID) {
		movieService.deleteComment(id, mID);
		
		// return "redirect:/movie/detail/commentModule?mID=" + mID + "&page=1&order=sympathy";
	}
	
	@RequestMapping(value = "/movie/detail/commentModule/isAlreadyVote")
	@ResponseBody
	public String detail_commentModule_isAlreadyVote(CommentVoteDTO commentVoteDTO) {
		return movieService.isAlreadyCommentVote(commentVoteDTO);
	}
	
	@RequestMapping(value = "/movie/detail/commentModule/vote")
	@ResponseBody
	public void detail_commentModule_vote(CommentVoteDTO commentVoteDTO) {
		movieService.insertCommentVote(commentVoteDTO);
	}
	
	
	@RequestMapping(value = "/movie/reserve")
	public ModelAndView reserve(HttpSession session) {
		Map<String, MovieDTO> showingMovieList = new HashMap<>();
		for(MovieDTO movieDTO : movieService.selectShowingMovieList()) {
			showingMovieList.put(movieDTO.getCode(), movieDTO);
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("showingMovieList_json", new Gson().toJson(showingMovieList));
		modelAndView.setViewName("user/movie/reserve/ticket");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/movie/reserve/request")
	public ModelAndView aop_reserve_request(ReserveDTO reserveDTO, @RequestParam("detail_date")String detail_date) {
		ModelAndView modelAndView = new ModelAndView();
		
		if(movieService.isAlreadyReserve(reserveDTO)) {
			modelAndView.addObject("movie_code", reserveDTO.getMovie_code());
			modelAndView.setViewName("user/movie/reserve/fail");
			
			return modelAndView;
		}
		
		modelAndView.addObject("reserve_detail", movieService.insertReserve(reserveDTO, detail_date));
		modelAndView.setViewName("user/movie/reserve/complete");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/movie/reserve/cancel")
	@ResponseBody
	public void aop_reserve_cancel(ReserveDTO reserveDTO) {
		movieService.deleteReserve(reserveDTO);
	}
	
	
	@RequestMapping(value = "/movie/unifiedSearch")
	public ModelAndView unifiedSearch(@RequestParam("keyword")String keyword) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("keyword", keyword);
		modelAndView.addObject("searchedMovieList", movieService.selectSearchedMovieList(keyword));
		modelAndView.setViewName("user/movie/unifiedSearch");
		
		return modelAndView;
	}
}