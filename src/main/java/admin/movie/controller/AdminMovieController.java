package admin.movie.controller;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import admin.movie.bean.ActorDTO;
import admin.movie.bean.DirectorDTO;
import admin.movie.bean.MovieDTO;
import admin.movie.bean.StillCutDTO;
import admin.movie.bean.TrailerDTO;
import admin.movie.other.FileWrapper;
import admin.movie.other.MovieListWrapper;
import admin.movie.other.SubMovieInfo;
import admin.movie.service.AdminMovieService;

@Controller
public class AdminMovieController {	
	@Autowired
	private AdminMovieService adminMovieService;
	
	private void fileUpload(MultipartHttpServletRequest multipart_req, String code) {
		Iterator<String> iter = multipart_req.getFileNames();
		while(iter.hasNext()) {
			String param_name = iter.next();
			String path = null;
			
			ServletContext servletContext = multipart_req.getSession().getServletContext();
			switch(param_name) {
				case "poster_file":
					path = servletContext.getRealPath("/resources/movie_related_files/poster");
					break;
				case "stillCut_file":
					path = servletContext.getRealPath("/resources/movie_related_files/stillCut");
					break;
				case "trailer_video_file":
					path = servletContext.getRealPath("/resources/movie_related_files/trailer/video");
					break;
				case "trailer_img_file":
					path = servletContext.getRealPath("/resources/movie_related_files/trailer/img");
					break;
				case "director_img_file":
					path = servletContext.getRealPath("/resources/movie_related_files/director");
					break;
				case "actor_img_file":
					path = servletContext.getRealPath("/resources/movie_related_files/actor");
			}
			
			List<MultipartFile> files = multipart_req.getFiles(param_name);
			for(MultipartFile file : files) {
				if(!file.getOriginalFilename().isEmpty()) {
					try {
						file.transferTo(new File(path, code + "_" + file.getOriginalFilename()));
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private void file_delete(String path) {
		if(!path.startsWith("http")) {
			ServletContext servletContext = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession().getServletContext();
			path = servletContext.getRealPath(path.substring(path.indexOf("/resources")));
			
			File file = new File(path);
			file.delete();
		}
	}
	
	private void file_revaildate(List<? extends SubMovieInfo> old_subMovieInfoList, List<? extends SubMovieInfo> new_subMovieInfoList, File tempArchive) {
		ServletContext servletContext = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession().getServletContext();
		
		for(SubMovieInfo subMovieInfo : new_subMovieInfoList) {
			for(FileWrapper fileWrapper : subMovieInfo.getFiles()) {
				if(!fileWrapper.getFile().startsWith("http")) {
					File file = new File(servletContext.getRealPath(fileWrapper.getPath()), fileWrapper.getName());
					file.renameTo(new File(tempArchive, fileWrapper.getName()));
				}
			}
		}
		
		for(SubMovieInfo subMovieInfo : old_subMovieInfoList) {
			for(FileWrapper fileWrapper : subMovieInfo.getFiles()) {
				if(!fileWrapper.getFile().startsWith("http")) {
					File file = new File(servletContext.getRealPath(fileWrapper.getPath()), fileWrapper.getName());
					if(file.exists()) {
						file.delete();
					}
				}
			}
		}
		
		for(SubMovieInfo subMovieInfo : new_subMovieInfoList) {
			for(FileWrapper fileWrapper : subMovieInfo.getFiles()) {
				if(!fileWrapper.getFile().startsWith("http")) {
					File file = new File(tempArchive, fileWrapper.getName());
					file.renameTo(new File(servletContext.getRealPath(fileWrapper.getPath()), fileWrapper.getName()));
				}
			}
		}
	}
	
	
	@RequestMapping(value = "/admin/movie")
	public String movie() {
		return "admin/movie/main";
	}
	
	@RequestMapping(value = "/admin/movie/insert")
	public String movie_insert() {
		return "admin/movie/insert/select_insert_type";
	}
	
	@RequestMapping(value = "/admin/movie/insert/personally_insert")
	public String movie_insert_personallyInsert() {
		return "admin/movie/insert/personally_insert";
	}
	
	@RequestMapping(value = "/admin/movie/insert/personally_insert/request")
	public String movie_insert_personallyInsert_request(MultipartHttpServletRequest multipart_req, MovieDTO movieDTO) {
		String code = adminMovieService.insertMovie(movieDTO);
		fileUpload(multipart_req, code);
		
		return "admin/movie/complete";
	}
	
	@RequestMapping(value = "/admin/movie/insert/excel_insert/request")
	@ResponseBody
	public void movie_insert_excelInsert_request(MultipartHttpServletRequest multipart_req) {
		File excel_file = null;
		
		try {
			MultipartFile multipartFile = multipart_req.getFile("excel_file");
			
			File tempArchive = new File(multipart_req.getSession().getServletContext().getRealPath("/resources/temp"));
			if(!tempArchive.exists()) {
				tempArchive.mkdirs();
			}
			
			excel_file = new File(tempArchive, multipartFile.getOriginalFilename());
			multipartFile.transferTo(excel_file);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		adminMovieService.insertMovie(excel_file);
	}
	
	@RequestMapping(value = "/admin/movie/modify/movie_list")
	public ModelAndView movie_modify_movieList(@RequestParam("page")int page) {
		MovieListWrapper movieListWrapper = adminMovieService.selectMovieList(page);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("paging", movieListWrapper.getPaging());
		modelAndView.addObject("movieList", movieListWrapper.getMovieList());
		modelAndView.setViewName("admin/movie/modify/movie_list");
		
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value = "/admin/movie/modify/movie_list/search")
	public List<MovieDTO> movie_modify_movieList_search(@RequestParam("keyword")String keyword) {
		return adminMovieService.searchMovie(keyword);
	}
	
	@RequestMapping(value = "/admin/movie/modify/movie_list/delete")
	public String aop_movie_modify_movieList_delete(@RequestParam("code")String code) {
		MovieDTO movieDTO = adminMovieService.selectMovieIncludingSubMovieInfo(code);
		adminMovieService.deleteMovie(code);
		
		// ------------------------------------------------------------------------------
		
		file_delete(movieDTO.getPoster());
		
		for(StillCutDTO stillCutDTO : movieDTO.getStillCut_list()) {
			file_delete(stillCutDTO.getStillCut());
		}
		
		for(TrailerDTO trailerDTO : movieDTO.getTrailer_list()) {
			file_delete(trailerDTO.getImg());
			file_delete(trailerDTO.getVideo());
		}
		
		for(DirectorDTO directorDTO : movieDTO.getDirector_list()) {
			file_delete(directorDTO.getImg());
		}
		
		for(ActorDTO actorDTO : movieDTO.getActor_list()) {
			file_delete(actorDTO.getImg());
		}
		
		return "admin/movie/complete";
	}
	
	@RequestMapping(value = "/admin/movie/modify")
	public ModelAndView movie_modify(@RequestParam("code")String code) {
		MovieDTO movieDTO = adminMovieService.selectMovieForModify(code);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("movie_json", new Gson().toJson(movieDTO));
		modelAndView.setViewName("admin/movie/modify/modify");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/movie/modify/request")
	public String aop_movie_modify_request(MultipartHttpServletRequest multipart_req, MovieDTO new_movieDTO) {
		// ----------------- ● update ● -----------------
		adminMovieService.modifyMovie(new_movieDTO);
		// ---------------------------------------------
		
		// ----------------- ● File Upload ● -----------------
		fileUpload(multipart_req, new_movieDTO.getCode());
		// ------------------------------------------------
		
		// ------------------------------------ ● File Revalidate ● ------------------------------------
		ServletContext servletContext = multipart_req.getSession().getServletContext();
		
		File tempArchive = new File(servletContext.getRealPath("/resources/temp"));
		if(!tempArchive.exists()) {
			tempArchive.mkdirs();
		}
		
		MovieDTO old_movieDTO = adminMovieService.selectMovieForModify(new_movieDTO.getCode());
		
		// poster
		String poster_path = servletContext.getRealPath("/resources/movie_related_files/poster");
		
		if(!new_movieDTO.getPoster().startsWith("http")) {
			String file_name = new_movieDTO.getPoster();
			file_name = file_name.substring(file_name.lastIndexOf("/") + "/".length());
			
			File file = new File(poster_path, file_name);
			file.renameTo(new File(tempArchive, file_name));
		}
		
		if(!old_movieDTO.getPoster().startsWith("http")) {
			String file_name = old_movieDTO.getPoster();
			file_name = file_name.substring(file_name.lastIndexOf("/") + "/".length());
			
			File file = new File(poster_path, file_name);
			if(file.exists()) {
				file.delete();
			}
		}
		
		File[] files = tempArchive.listFiles();
		for(int i = 0; i < files.length; i++) {
			files[i].renameTo(new File(poster_path, files[i].getName()));
		}
		
		// stillCut
		file_revaildate(old_movieDTO.getStillCut_list(), new_movieDTO.getStillCut_list(), tempArchive);
		
		// trailer
		file_revaildate(old_movieDTO.getDirector_list(), new_movieDTO.getDirector_list(), tempArchive);
		
		// director
		file_revaildate(old_movieDTO.getDirector_list(), new_movieDTO.getDirector_list(), tempArchive);
		
		// actor
		file_revaildate(old_movieDTO.getActor_list(), new_movieDTO.getActor_list(), tempArchive);
		// -------------------------------------------------------------------------------------------
		
		return "admin/movie/complete";
	}
}