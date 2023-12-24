package movie.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringEscapeUtils;

import movie.bean.ActorDTO;
import movie.bean.DirectorDTO;
import movie.bean.MovieDTO;
import movie.bean.StillCutDTO;
import movie.bean.TrailerDTO;

public class WebScraping {
	private final String CHANGEABLE_FOLDER = "tmp0";
	
	private List<String> inUse_key_list = new ArrayList<>();
	
	public List<MovieDTO> start() throws Exception {
		System.out.println("==================== MovieChart WebScraping Start ====================");
		String url = "https://movie.naver.com/movie/running/current.nhn?view=list&tab=normal&order=open";
		List<MovieDTO> movieChart = getMovieList(url, "movieChart");
		
		System.out.println("==================== PreMovieChart WebScraping Start ====================");
		url = "https://movie.naver.com/movie/running/premovie.nhn";
		List<MovieDTO> preMovieChart = getMovieList(url, "preMovieChart");
		
		List<MovieDTO> movie_list = new ArrayList<>();
		movie_list.addAll(movieChart);
		movie_list.addAll(preMovieChart);
		
		archive_revalidate(movie_list, "poster");
		archive_revalidate(movie_list, "trailer");
		
		return movie_list;
	}
	
	
	// 임시 key 생성
	private String getRandomKey() {
		Random rnd = new Random();
		String key = "";

		for(int i = 0; i < 7; i++) {
		    if(rnd.nextBoolean()) { // true 또는 false를 랜덤으로 반환
		    	key += (char)((int)(Math.random() * (90 - 65 + 1)) + 65); // A(65) ~ Z(90)
		    }else {
		    	key += rnd.nextInt(10); // nextInt(10) : 0 ~ 9까지의 정수를 랜덤으로 반환
		    }
		}
		
		if(inUse_key_list.contains(key)) {
			key = getRandomKey();
		}else {
			inUse_key_list.add(key);
		}
		
		return key;
	}
	
	private List<MovieDTO> getMovieList(String url, String type) {
		List<MovieDTO> movie_list = new ArrayList<>();
		List<String> inUse_key_list = new ArrayList<>();
		
		try {
			InputStream is = new URL(url).openStream();
			try(BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
				int flag = 0;
				String html = "";
				
				String line = null;
				while((line = br.readLine()) != null) {
					if(flag == 1 || line.contains("<div class=\"thumb\">")) {
						flag = 1;
						html += line + '\n';
						
						if(line.contains("개봉")) {
							String key = getRandomKey();
							inUse_key_list.add(key);
							
							int beginIndex = 0;
							int endIndex = 0;
							if(type.equals("movieChart")) {
								endIndex = line.indexOf("개봉");
								
								String premier_string = line.substring(beginIndex, endIndex).trim();
								
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
								Calendar premier = Calendar.getInstance();
								premier.setTime(sdf.parse(premier_string));
							
								int cur_year = Calendar.getInstance().get(Calendar.YEAR);
								if(premier.get(Calendar.YEAR) < (cur_year - 1)) {
									break;
								}
							}
							
							beginIndex = html.indexOf("code=") + "code=".length();
							endIndex = html.indexOf("\">", beginIndex);
							String naver_code = html.substring(beginIndex, endIndex);
							
							MovieDTO movieDTO = getMovieInfo(key, naver_code);
							if(movieDTO != null) {
								System.out.println("------- " + movieDTO.getTitle() + " -------");
								
								movieDTO.setStillCut_list(getStillCutList(key, naver_code));
								putActorListAndDirectorInfo(key, naver_code, movieDTO);
								
								if(!movieDTO.getDirector_list().isEmpty()) {
									movieDTO.setTrailer_list(getTrailerList(key, movieDTO.getTitle(), type));
								}else {
									movieDTO.setTrailer_list(new ArrayList<>());
								}
								
								movie_list.add(movieDTO);
								System.out.println();
							}
							
							flag = 0;
							html = "";
							
							Thread.sleep(5000); // 쉬는 시간
						}
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			
			System.out.println("처음부터 다시 시작");
			this.inUse_key_list.removeAll(inUse_key_list);
			movie_list = getMovieList(url, type);
		}
		
		return movie_list;
	}
	
	
	private MovieDTO getMovieInfo(String key, String naver_code) throws Exception {
		MovieDTO movieDTO = null;
		
		String url = "https://movie.naver.com/movie/bi/mi/basic.nhn?code=" + naver_code;
		InputStream is = new URL(url).openStream();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
			int flag = 0;
			String html = "";
			
			String line = null;
			while((line = br.readLine()) != null) {
				if(flag == 1 || line.contains("<div class=\"wide_info_area\"")) {
					flag = 1;
					
					if(line.contains("<div class=\"info_spec2\">")) {
						if(html.contains("재개봉")) {
							break;
						}
						
						movieDTO = new MovieDTO();
						movieDTO.setKey(key);
						
						// poster
						int beginIndex = html.indexOf("<img src=\"") + "<img src=\"".length();
						int endIndex = html.indexOf("?type", beginIndex);
						File archive = new File(new File(System.getProperty("user.dir")).getParent(), ".metadata/.plugins/org.eclipse.wst.server.core/" + CHANGEABLE_FOLDER + "/wtpwebapps/GGV/resources/movie_related_files/poster");
						
						if(endIndex != -1) {
							String poster_url = html.substring(beginIndex, endIndex);
							String extension = poster_url.substring(poster_url.lastIndexOf('.'));
							
							beginIndex = html.indexOf("alt=\"", endIndex) + "alt=\"".length();
							endIndex = html.indexOf('"', beginIndex);
							String poster_title = html.substring(beginIndex, endIndex);
							poster_title = poster_title.replace("극장판", "").trim();
							poster_title = poster_title.replace("/", "／"); poster_title = poster_title.replace(":", "："); poster_title = poster_title.replace("?", "？");
							while(poster_title.contains("&#")) { // &#Number;  →  HTML entity
								int html_entity_beginIndex = poster_title.indexOf("&#");
								int html_entity_endIndex = poster_title.indexOf(";", html_entity_beginIndex) + ";".length();
								String html_entity = poster_title.substring(html_entity_beginIndex, html_entity_endIndex);
								
								poster_title = poster_title.replace(html_entity, StringEscapeUtils.unescapeHtml(html_entity)); // StringEscapeUtils : apache-commons-lang.jar
							}
							
							movieDTO.setPoster(key + "_" + poster_title + extension);
							download(poster_url, archive, movieDTO.getPoster());
						}else {
							endIndex = html.indexOf('"', beginIndex);
							movieDTO.setPoster(html.substring(beginIndex, endIndex));
						}
						
						// title
						String title;
						beginIndex = html.indexOf("<h3 class=\"h_movie\">", endIndex);
						beginIndex = html.indexOf("<a href=\"./basic.nhn", beginIndex);
						beginIndex = html.indexOf(">", beginIndex) + ">".length();
						endIndex = html.indexOf("</a>", beginIndex);
						title = html.substring(beginIndex, endIndex);
						title = title.replace("극장판", "").trim();
						while(title.contains("&#")) {
							int html_entity_beginIndex = title.indexOf("&#");
							int html_entity_endIndex = title.indexOf(";", html_entity_beginIndex) + ";".length();
							String html_entity = title.substring(html_entity_beginIndex, html_entity_endIndex);
							
							title = title.replace(html_entity, StringEscapeUtils.unescapeHtml(html_entity));
						}
						movieDTO.setTitle(title);
						
						// genre
						String genre = "";
						endIndex = html.indexOf("<p class=\"info_spec\">", endIndex);
						while((beginIndex = html.indexOf("<a href=\"/movie/sdb/browsing/bmovie.nhn?genre", endIndex)) != -1) {
							beginIndex = html.indexOf(">", beginIndex) + ">".length();
							endIndex = html.indexOf("</a>", beginIndex);
							
							genre += html.substring(beginIndex, endIndex) + ", ";
						}
						
						if(!genre.isEmpty()) {
							movieDTO.setGenre(genre.substring(0, genre.lastIndexOf(",")));
						}else {
							movieDTO.setGenre(genre);
						}
						
						// nation
						String nation = "";
						while((beginIndex = html.indexOf("<a href=\"/movie/sdb/browsing/bmovie.nhn?nation", endIndex)) != -1) {
							beginIndex = html.indexOf(">", beginIndex) + ">".length();
							endIndex = html.indexOf("</a>", beginIndex);
							
							nation += html.substring(beginIndex, endIndex) + ", ";
						}
						
						if(!nation.isEmpty()) {
							movieDTO.setNation(nation.substring(0, nation.lastIndexOf(",")));
						}else {
							movieDTO.setNation(nation);
						}
						
						// showtimes
						beginIndex = html.indexOf("<span>", endIndex) + "<span>".length();
						endIndex = html.indexOf("</span>", beginIndex);
						if(html.substring(beginIndex, endIndex).contains("분")) {
							movieDTO.setShowtimes(html.substring(beginIndex, endIndex).trim());
						}else {
							movieDTO.setShowtimes("");
							endIndex = beginIndex;
						}
						
						// premier
						String premier = "";
						while((beginIndex = html.indexOf("<a href=\"/movie/sdb/browsing/bmovie.nhn?open", endIndex)) != -1) {
							beginIndex = html.indexOf(">", beginIndex) + ">".length();
							endIndex = html.indexOf("</a>", beginIndex);
							
							premier += html.substring(beginIndex, endIndex);
						}
						
						if(premier.length() < "○○○○.○○.○○".length()) { // 개봉일이 확정 나지 않은 영화는 제외
							System.out.println("[" + movieDTO.getTitle() + "] 제외");
							System.out.println();
							
							return null;
						}
						movieDTO.setPremier(premier);
						
						// grade
						String grade = "";
						beginIndex = html.indexOf("<a href=\"/movie/sdb/browsing/bmovie.nhn?grade", endIndex);
						if(beginIndex != -1) {
							beginIndex = html.indexOf(">", beginIndex) + ">".length();
							endIndex = html.indexOf("</a>", beginIndex);
							grade = html.substring(beginIndex, endIndex);
							
							if(!(grade.contains("전체 관람가") || grade.contains("12세 관람가") || grade.contains("15세 관람가") || grade.contains("청소년 관람불가"))) {
								grade = "";
							}
						}
						movieDTO.setGrade(grade);
						
						
						flag = 0;
						html = "";
					}
					
					html += line + '\n';
				}else if(flag == 2 || line.contains("<div class=\"story_area\">")) {
					flag = 2;
					html += line;
					
					if(line.contains("</p>")) {
						int beginIndex = html.indexOf("<h5 class=\"h_tx_story\">");
						int endIndex = 0;
						String story = "";
						if(beginIndex != -1) {
							beginIndex += "<h5 class=\"h_tx_story\">".length();
							endIndex = html.indexOf("</h5>", beginIndex);
							story = html.substring(beginIndex, endIndex) + "<br><br>";
						}
						
						beginIndex = html.indexOf("<p class=\"con_tx\">", endIndex) + "<p class=\"con_tx\">".length();
						endIndex = html.indexOf("</p>", beginIndex);
						story += html.substring(beginIndex, endIndex);
						
						story = story.replace("&nbsp;", "");
						movieDTO.setStory(story);
						
						break;
					}
				}
			}
			if(movieDTO != null) {
				if(movieDTO.getStory() == null) movieDTO.setStory("");
			}
		}
		return movieDTO;
	}
	
	
	private List<StillCutDTO> getStillCutList(String key, String naver_code) throws Exception {
		List<StillCutDTO> stillCut_list = new ArrayList<>();
		
		String url = "https://movie.naver.com/movie/bi/mi/photo.nhn?code=" + naver_code + "&page=1#movieEndTabMenu";
		InputStream is = new URL(url).openStream();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
			int flag = 0;
			String html = "";
			
			String line = null;
			while((line = br.readLine()) != null) {
				if(flag == 1 || line.contains("<ul id=\"gallery_group\">")) {
					flag = 1;
					html += line + '\n';
					
					if(html.contains("</ul>")) {
						int beginIndex = 0;
						int endIndex = 0;
						
						while((beginIndex = html.indexOf("<li class=\"_brick\"", endIndex)) != -1) {
							endIndex = html.indexOf("</li>", beginIndex) + "</li>".length();
							int temp = endIndex;
							String paragraph = html.substring(beginIndex, endIndex);
							
							if(!paragraph.contains("adult_img") && paragraph.contains("STILLCUT")) {
								beginIndex = paragraph.indexOf("<img src=\"") + "<img src=\"".length();
								endIndex = paragraph.indexOf("?type", beginIndex);
								
								StillCutDTO stillCutDTO = new StillCutDTO();
								stillCutDTO.setKey(key);
								stillCutDTO.setStillCut(paragraph.substring(beginIndex, endIndex));
								
								stillCut_list.add(stillCutDTO);
							}
							
							endIndex = temp;
						}
						
						break;
					}
				}
			}
		}
		return stillCut_list;
	}
	
	
	private void putActorListAndDirectorInfo(String key, String naver_code, MovieDTO movieDTO) throws Exception {
		String url = "https://movie.naver.com/movie/bi/mi/detail.nhn?code=" + naver_code;
		InputStream is = new URL(url).openStream();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
			int flag = 0;
			String html = "";
			
			String line = null;
			while((line = br.readLine()) != null) {
				if(flag == 1 || line.contains("<div class=\"section_group section_group_frst\">")) {
					flag = 1;
					
					if(line.contains("<div class=\"section_group\">")) {
						// --------------------------------------------- ◆ Actor ◆ ---------------------------------------------
						List<ActorDTO> actor_list = new ArrayList<>();
						int beginIndex = 0;
						int endIndex = 0;
						
						while((beginIndex = html.indexOf("<p class=\"p_thumb\">", endIndex)) != -1) {
							endIndex = html.indexOf("<ul class=\"mv_product\">", beginIndex);
							int temp = endIndex;
							String paragraph = html.substring(beginIndex, endIndex);
							
							ActorDTO actorDTO = new ActorDTO();
							actorDTO.setKey(key);
							
							// ko_name
							beginIndex = paragraph.indexOf("<a href=\"/movie/bi/pi/basic.nhn");
							beginIndex = paragraph.indexOf("title=\"", beginIndex) + "title=\"".length();
							endIndex = paragraph.indexOf('"', beginIndex);
							actorDTO.setKo_name(paragraph.substring(beginIndex, endIndex));
							
							// img
							beginIndex = paragraph.indexOf("<img src=\"https://search.pstatic.net", endIndex);
							if(beginIndex != -1) {
								beginIndex += "<img src=\"".length();
								endIndex = paragraph.indexOf("&type", beginIndex);
								actorDTO.setImg(paragraph.substring(beginIndex, endIndex));
							}else {
								beginIndex = paragraph.indexOf("<img src=\"", endIndex) + "<img src=\"".length();
								endIndex = paragraph.indexOf('"', beginIndex);
								actorDTO.setImg(paragraph.substring(beginIndex, endIndex));
							}
							
							// en_name
							beginIndex = paragraph.indexOf("<em class=\"e_name\">", endIndex);
							beginIndex = paragraph.indexOf(">", beginIndex) + ">".length();
							endIndex = paragraph.indexOf("</em>", beginIndex);
							actorDTO.setEn_name(paragraph.substring(beginIndex, endIndex));
							
							// role
							beginIndex = paragraph.indexOf("<em class=\"p_part\">", endIndex) + "<em class=\"p_part\">".length();
							endIndex = paragraph.indexOf("</em>", beginIndex);
							actorDTO.setRole_(paragraph.substring(beginIndex, endIndex));
							
							// role_detail
							beginIndex = paragraph.indexOf("<p class=\"pe_cmt\">", endIndex);
							if(beginIndex != -1) {
								beginIndex = paragraph.indexOf("<span>", beginIndex) + "<span>".length();
								endIndex = paragraph.indexOf("</span>", beginIndex);
								actorDTO.setRole_detail(paragraph.substring(beginIndex, endIndex));
							}else {
								actorDTO.setRole_detail("");
							}
							
							
							actor_list.add(actorDTO);
							endIndex = temp;
						}
						movieDTO.setActor_list(actor_list);
						// ---------------------------------------------------------------------------------------------------
						
						// -------------------------------- ◆ Director ◆ --------------------------------
						List<DirectorDTO> director_list = new ArrayList<>();
						
						while((beginIndex = html.indexOf("<p class=\"thumb_dir\">", endIndex)) != -1) {
							DirectorDTO directorDTO = new DirectorDTO();
							directorDTO.setKey(key);
							
							int temp = beginIndex;
						
							// img
							beginIndex = html.indexOf("<img src=\"https://search.pstatic.net", beginIndex);
							if(beginIndex != -1) {
								beginIndex += "<img src=\"".length();
								endIndex = html.indexOf("&type", beginIndex);
								directorDTO.setImg(html.substring(beginIndex, endIndex));
							}else {
								beginIndex = html.indexOf("<img src=\"", temp) + "<img src=\"".length();
								endIndex = html.indexOf('"', beginIndex);
								directorDTO.setImg(html.substring(beginIndex, endIndex));
							}
							
							// ko_name
							beginIndex = html.indexOf("alt=\"", endIndex) + "alt=\"".length();
							endIndex = html.indexOf('"', beginIndex);
							directorDTO.setKo_name(html.substring(beginIndex, endIndex).trim());
							
							// en_name
							beginIndex = html.indexOf("<em class=\"e_name\">", endIndex);
							beginIndex = html.indexOf(">", beginIndex) + ">".length();
							endIndex = html.indexOf("</em>", beginIndex);
							directorDTO.setEn_name(html.substring(beginIndex, endIndex).trim());
							
							
							director_list.add(directorDTO);
						}
						movieDTO.setDirector_list(director_list);
						// ----------------------------------------------------------------------------
						
						break;
					}
					
					html += line + '\n';
				}
			}
		}
	}
	
	
	private List<TrailerDTO> getTrailerList(String key, String title, String type) throws Exception {
		List<TrailerDTO> trailer_list = null;
		
		String url;
		String startPoint;
		if(type.equalsIgnoreCase("MovieChart")) {
			url = "https://ticket.maxmovie.com/movie/play";
			startPoint = "<ul class=\"currlist\">";
		}else {
			url = "https://ticket.maxmovie.com/movie/schedule";
			startPoint = "<div class=\"prebox\">";
		}
		
		InputStream is = new URL(url).openStream();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
			int flag = 0;
			String html = "";
			
			String line = null;
			while((line = br.readLine()) != null) {
				if(flag == 1 || line.contains(startPoint)) {
					flag = 1;
					
					if(line.contains("<p class=\"premore\">")) {
						int beginIndex = 0;
						int endIndex = 0;
						
						while((beginIndex = html.indexOf("<p class=\"cpost\">", endIndex)) != -1) {
							endIndex = html.indexOf("</p>", beginIndex) + "</p>".length();
							int temp = endIndex;
							String paragraph = html.substring(beginIndex, endIndex);
							
							if(paragraph.contains(title) || paragraph.replace(" ", "").contains(title)) {
								beginIndex = paragraph.indexOf("<a href=\"//www.maxmovie.com/movie/") + "<a href=\"//www.maxmovie.com/movie/".length();
								endIndex = paragraph.indexOf('"', beginIndex);
								String maxMovie_code = paragraph.substring(beginIndex, endIndex);
								trailer_list = trailerDownload(key, maxMovie_code);
								
								break;
							}
							
							endIndex = temp;
						}
						
						break;
					}
					
					html += line + '\n';
				}
			}
			if(trailer_list == null) trailer_list = new ArrayList<>();
		}
		
		return trailer_list;
	}
	
	private List<TrailerDTO> trailerDownload(String key, String maxMovie_code) throws Exception {
		List<TrailerDTO> trailer_list = new ArrayList<>();
		
		String url = "https://www.maxmovie.com/Movie/" + maxMovie_code;
		InputStream is = new URL(url).openStream();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
			int flag = 0;
			String html = "";
			
			String line = null;
			while((line = br.readLine()) != null) {
				if(flag == 1 || line.contains("<div class=\"nmvid\">")) {
					flag = 1;
					
					if(line.contains("<div class=\"nmscore\">")) {
						int beginIndex = 0;
						int endIndex = 0;
						
						while((beginIndex = html.indexOf("<li", endIndex)) != -1) {
							TrailerDTO trailerDTO = new TrailerDTO();
							trailerDTO.setKey(key);
							
							beginIndex = html.indexOf("src=\"", beginIndex) + "src=\"".length();
							endIndex = html.indexOf('"', beginIndex);
							String trailer_img = "http:" + html.substring(beginIndex, endIndex);
							trailerDTO.setImg(trailer_img);
							
							String video_file_name = trailer_img.substring(trailer_img.indexOf("v1_") + "v1_".length(), trailer_img.lastIndexOf(".")) + ".mp4";
							trailerDTO.setVideo(key + "_" + video_file_name);
							
							beginIndex = html.indexOf("title=\"", endIndex) + "title=\"".length();
							endIndex = html.indexOf('"', beginIndex);
							String trailer_title = html.substring(beginIndex, endIndex);
							trailerDTO.setTitle(trailer_title);
							System.out.println(trailerDTO.getTitle());
							
							trailer_list.add(trailerDTO);
							
							// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
							
							String video_url = "http://iphone-vod.maxmovie.com/MovieInfo/image/av/" + video_file_name.replace(" ", "%20"); // %20 : 띄어쓰기
							File archive = new File(new File(System.getProperty("user.dir")).getParent(), ".metadata/.plugins/org.eclipse.wst.server.core/" + CHANGEABLE_FOLDER + "/wtpwebapps/GGV/resources/movie_related_files/trailer/video");
							
							if(download(video_url, archive, trailerDTO.getVideo())) {
								trailer_list.remove(trailer_list.size() - 1);
							}
						}
						
						break;
					}
					
					html += line + '\n';
				}
			}
		}
		return trailer_list;
	}
	
	private boolean download(String url, File archive, String file_name) {
		File file = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		URLConnection urlConnection = null;
		int read = 0;
		boolean isError = false;
		
		try {
			urlConnection = new URL(url).openConnection();
			inputStream = urlConnection.getInputStream();
			
			if(!archive.exists()) {
				archive.mkdirs();
			}
			file = new File(archive, file_name);
			outputStream = new FileOutputStream(file);
			
			byte[] buf = new byte[2048];
			while((read = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, read);
			}
		}catch(Exception e) {
			System.out.println("(다운로드 중 오류 발생)");
			e.printStackTrace();
			
			isError = true;
		}finally {
			try {
				if(inputStream != null) inputStream.close();
				if(outputStream != null) outputStream.close();
				if(isError && file != null) file.delete();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		return isError;
	}
	
	
	// 실제 필요한 파일들만 걸러내는 함수(오류 발생 시 처음부터 다시 시작되는데 그로 인해 생긴 중복 파일들을 제거)
	private void archive_revalidate(List<MovieDTO> movie_list, String type) {
		File archive = null;
		File tempArchive = new File(new File(System.getProperty("user.dir")).getParent(), ".metadata/.plugins/org.eclipse.wst.server.core/" + CHANGEABLE_FOLDER + "/wtpwebapps/GGV/resources/temp");
		
		switch(type) {
			case "poster":
				archive = new File(new File(System.getProperty("user.dir")).getParent(), ".metadata/.plugins/org.eclipse.wst.server.core/" + CHANGEABLE_FOLDER + "/wtpwebapps/GGV/resources/movie_related_files/poster");
				
				for(MovieDTO movieDTO : movie_list) {
					String file_name = movieDTO.getPoster();
					move(archive, file_name);
				}
				break;
	
			case "trailer":
				archive = new File(new File(System.getProperty("user.dir")).getParent(), ".metadata/.plugins/org.eclipse.wst.server.core/" + CHANGEABLE_FOLDER + "/wtpwebapps/GGV/resources/movie_related_files/trailer/video");
				
				for(MovieDTO movieDTO : movie_list) {
					for(TrailerDTO trailerDTO : movieDTO.getTrailer_list()) {
						String file_name = trailerDTO.getVideo();
						move(archive, file_name);
					}
				}
		}
		
		File[] files = archive.listFiles();
		for(int i = 0; i < files.length; i++) {
			files[i].delete();
		}
		archive.delete();
		
		tempArchive.renameTo(archive);
	}
	
	private void move(File archive, String file_name) {
		File tempArchive = new File(new File(System.getProperty("user.dir")).getParent(), ".metadata/.plugins/org.eclipse.wst.server.core/" + CHANGEABLE_FOLDER + "/wtpwebapps/GGV/resources/temp");
		
		if(!tempArchive.exists()) {
			tempArchive.mkdirs();
		}
		
		File file = new File(archive, file_name);
		file.renameTo(new File(tempArchive, file_name));
	}
}