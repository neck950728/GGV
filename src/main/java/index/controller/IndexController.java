package index.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import admin.movie.bean.MovieDTO;
import index.service.IndexService;

@Controller
public class IndexController {
	@Autowired
	private IndexService indexService;
	
	@RequestMapping(value = "/")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("requireMovingBanner", true);
		modelAndView.setViewName("user/index");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/getRandomMovie")
	@ResponseBody // https://blog.naver.com/dngu_icdi/221367310704 참고
	public MovieDTO getRandomMovie() {
		return indexService.getRandomMovie();
	}
	
	@RequestMapping(value = "/doNotSeeToday")
	@ResponseBody
	public void doNotSeeToday(HttpServletResponse response) {
		Cookie cookie = new Cookie("doNotSeeToday", "");
		cookie.setMaxAge(24 * 60 * 60);
		response.addCookie(cookie);
	}
}