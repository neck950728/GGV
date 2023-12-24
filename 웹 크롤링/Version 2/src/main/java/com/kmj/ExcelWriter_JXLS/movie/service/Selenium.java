package com.kmj.ExcelWriter_JXLS.movie.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import com.kmj.ExcelWriter_JXLS.movie.bean.ActorDTO;
import com.kmj.ExcelWriter_JXLS.movie.bean.DirectorDTO;
import com.kmj.ExcelWriter_JXLS.movie.bean.MovieDTO;
import com.kmj.ExcelWriter_JXLS.movie.bean.StillCutDTO;
import com.kmj.ExcelWriter_JXLS.movie.bean.TrailerDTO;

public class Selenium {
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	public static final String WEB_DRIVER_PATH = "C:/chromedriver_win32/chromedriver.exe";
	
	private final String CHANGEABLE_FOLDER = "tmp0";
	
	private WebDriver driver;
	private String url;
	
	private List<String> inUse_key_list = new ArrayList<>();
	
	public Selenium(){
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		
		ChromeOptions options = new ChromeOptions();
		// options.addArguments("headless"); // WebDriver(소프트웨어에 의해 제어되는 웹 브라우저) 출력 X
		options.addArguments("mute-audio"); // Sound OFF
		
		driver = new ChromeDriver(options); // 이 시점에 chromedriver.exe 프로세스가 실행된다.(즉, 하나의 WebDriver가 실행된다.)
		driver.manage().window().maximize(); // WebDriver 최대화(click 같은 메서드를 사용할 때 WebDriver가 최소화 상태이거나 최대 크기가 아니어서 해당 Element가 화면상 가려진 경우 오류가 발생하므로 최대화시켜주는 것이 좋다.)
		
		/*
			NoSuchElement 예외가 발생하면 바로 예외를 throw하지 않고 지정 시간 동안 재탐색을 시도한다.(그래도 찾지 못하면 예외 throw)
			
			ㆍ암묵적 대기 - 대상 : 모든 Element
			ㆍ명시적 대기 - 대상 : 특정 Element
		*/
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // 암묵적 대기
		// webElement = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.className("..."))); // 명시적 대기
	}
	
	
	public List<MovieDTO> start() {
		System.out.println("==================== MovieChart WebScraping Start ====================");
		url = "http://www.cgv.co.kr/movies";
		List<MovieDTO> movieChart = getMovieList("movieChart");
		
		System.out.println("==================== PreMovieChart WebScraping Start ====================");
		url = "http://www.cgv.co.kr/movies/pre-movies.aspx";
		List<MovieDTO> preMovieChart = getMovieList("preMovieChart");
		
		List<MovieDTO> movie_list = new ArrayList<>();
		movie_list.addAll(movieChart);
		movie_list.addAll(preMovieChart);
		
		archive_revalidate(movie_list, "poster");
		archive_revalidate(movie_list, "trailer");
		
		driver.quit(); // 모든 탭 종료 및 chromedriver.exe 프로세스 종료
		
		return movie_list;
	}
	
	
	private List<MovieDTO> getMovieList(String type) {
		List<MovieDTO> movie_list = new ArrayList<>();
		List<String> inUse_key_list = new ArrayList<>();
		
		WebElement webElement;
		String main_handle = null;
		
		try {
			driver.get(url); // 활성화 상태인 탭의 URL을 이동시킨다.
			
			if(type.equals("movieChart")) {
				webElement = driver.findElement(By.className("link-more"));
				webElement.click();
				
				driver.findElement(By.cssSelector(".list-more > li > div")); // Load Wait
			}
			
			webElement = driver.findElement(By.className("sect-movie-chart"));
			List<WebElement> webElementList = webElement.findElements(By.tagName("li"));
			
			for(WebElement web_element  : webElementList) {
				if(type.equals("preMovieChart")) {
					if(web_element.findElement(By.className("box-image")).getText().contains("추천") || web_element.findElement(By.className("txt-info")).getText().contains("예정")) {
						continue;
					}
				}
				
				if(!web_element.findElement(By.className("txt-info")).getText().contains("재개봉")) {
					webElement = web_element.findElement(By.className("thumb-image"));
					
					Actions actions = new Actions(driver);
					actions.keyDown(Keys.LEFT_CONTROL).click(webElement).keyUp(Keys.LEFT_CONTROL).build().perform(); // Ctrl + Click
					
					/*
						JavascriptExecutor js = (JavascriptExecutor)driver;
						js.executeScript("window.open('URL')");
						
						Ctrl + Click 대신 위 방법을 이용하여 새 탭을 열 수도 있다.
						
						(주의)
						이 방법을 이용하여 새 탭을 열면 겉으로 보기에는 자동으로 Focus가 새 탭으로 변경되는 것처럼 보이지만
						내부적으로 실제 변경된 것은 아니기 때문에 마찬가지로 아래와 같이 새 탭으로 Focus를 변경시켜주는 코드를 작성해야 한다.
					*/
					
					main_handle = driver.getWindowHandle();
					Set<String> handles = driver.getWindowHandles();
					for(String handle : handles) {
						if(!handle.equals(main_handle)) {
							driver.switchTo().window(handle); // 다른 탭으로 Focus 변경
							
							
							String key = getRandomKey();
							inUse_key_list.add(key);
							
							MovieDTO movieDTO = getMovieInfo(key, main_handle);
							movie_list.add(movieDTO);
							
							
							driver.close(); // 활성화 상태인 탭 종료
							driver.switchTo().window(main_handle);
							
							break;
						}
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("처음부터 다시 시작");
			
			
			for(String handle : driver.getWindowHandles()) {
				if(!handle.equals(main_handle)) {
					driver.switchTo().window(handle);
					driver.close();
				}
			}
			
			driver.switchTo().window(main_handle);
			
			
			this.inUse_key_list.removeAll(inUse_key_list);
			movie_list = getMovieList(type);
		}
		
		return movie_list;
	}
	
	private MovieDTO getMovieInfo(String key, String main_handle) {
		WebElement webElement = driver.findElement(By.className("sect-base-movie"));
		
		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setKey(key);
		
		
		// - poster & title -
		String poster_url = webElement.findElement(By.cssSelector(".box-image > a")).getAttribute("href");
		String extension = poster_url.substring(poster_url.lastIndexOf('.'));
		
		String title = webElement.findElement(By.cssSelector(".box-contents > .title > strong")).getText();
		title = fileName_impossibleChar_Converter(title);
		
		File archive = new File(new File(System.getProperty("user.dir")).getParent(), ".metadata/.plugins/org.eclipse.wst.server.core/" + CHANGEABLE_FOLDER + "/wtpwebapps/GGV/resources/movie_related_files/poster");
		
		movieDTO.setPoster(key + "_" + title + extension);
		movieDTO.setTitle(title);
		download(poster_url, archive, movieDTO.getPoster());
		
		
		// - genre -
		List<WebElement> webElementList = webElement.findElements(By.cssSelector(".box-contents > .spec > dl > dt"));
		for(WebElement web_element : webElementList) {
			if(web_element.getText().contains("장르")) {
				String genre = web_element.getText();
				genre = genre.substring(genre.indexOf(':') + ":".length()).trim();
				
				if(!genre.isEmpty()) {
					movieDTO.setGenre(genre);
				}else {
					movieDTO.setGenre("미정");
				}
				
				break;
			}
		}
		
		
		// - grade & showtimes & nation & premier -
		webElementList = webElement.findElements(By.cssSelector(".box-contents > .spec > dl > dd"));
		for(WebElement web_element : webElementList) {
			if(!web_element.getAttribute("innerHTML").contains("</a>")) { // getAttribute("innerHTML") : getText 메서드와 달리 HTML 코드를 해석하지 않고 그대로 반환한다.
				if(StringUtils.countMatches(web_element.getAttribute("innerHTML"), "&nbsp;") >= 2) { // StringUtils : apache-commons-lang.jar
					String[] strAry = web_element.getAttribute("innerHTML").split("&nbsp;");
					movieDTO.setGrade(strAry[0].replace(",", ""));
					movieDTO.setShowtimes(strAry[1].replace(",", ""));
					movieDTO.setNation((strAry.length == 3) ? strAry[2] : "미정");
				}else if(StringUtils.countMatches(web_element.getText(), ".") >= 2) {
					movieDTO.setPremier(web_element.getText());
				}
			}
		}
		
		
		// story
		webElement = driver.findElement(By.className("sect-story-movie"));
		movieDTO.setStory(webElement.getAttribute("innerHTML"));
		
		
		// - trailer -
		List<TrailerDTO> trailerList = new ArrayList<>();
		
		String movieID = driver.getCurrentUrl().substring(driver.getCurrentUrl().indexOf("midx=") + "midx=".length());
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.open('http://www.cgv.co.kr/movies/detail-view/trailer.aspx?midx=" + movieID + "#menu')");
		
		String parent_handle = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		for(String handle : handles) {
			if(!handle.equals(main_handle) && !handle.equals(parent_handle)) {
				driver.switchTo().window(handle);
				
				
				webElement = driver.findElement(By.cssSelector(".sect-trailer > ul"));
				if(webElement.getAttribute("innerHTML").contains("</li>")){
					webElementList = webElement.findElements(By.cssSelector(":scope > li")); // :scope : 현재 요소 참조
					for(WebElement web_element : webElementList) {
						TrailerDTO trailerDTO = new TrailerDTO();
						trailerDTO.setKey(key);
						
						webElement = web_element.findElement(By.cssSelector(".box-contents .title"));
						trailerDTO.setTitle(fileName_impossibleChar_Converter(webElement.getText().replace("HD", "").trim()));
						
						webElement = web_element.findElement(By.cssSelector(".thumb-image > img"));
						trailerDTO.setImg(webElement.getAttribute("src"));
						
						
						webElement.click();
						
						webElement = driver.findElement(By.cssSelector(".layer-wrap .warp-pop-player > iframe"));
						driver.switchTo().frame(webElement.getAttribute("id"));
						
						String trailer_url = driver.findElement(By.cssSelector("video > source")).getAttribute("src");
						archive = new File(new File(System.getProperty("user.dir")).getParent(), ".metadata/.plugins/org.eclipse.wst.server.core/" + CHANGEABLE_FOLDER + "/wtpwebapps/GGV/resources/movie_related_files/trailer/video");
						trailerDTO.setVideo(key + "_" + trailerDTO.getTitle() + ".mp4");
						download(trailer_url, archive, trailerDTO.getVideo());
						
						trailerList.add(trailerDTO);
						
						
						driver.switchTo().parentFrame();
						
						/*
							간혹가다 이처럼 By.id, By.className, By.cssSelector 등의 메서드로는 Element에 정상적으로 접근이 안 되는 경우가 있는데(정확한 이유는 모르겠으나 대충 Element 구조가 복잡해서 그렇다고 한다.)
							이런 경우에는 By.xpath 메서드를 사용하면 된다.
							
							(참고)
							1. XPath 구하는 방법 : Chrome 개발자 도구 Open(F12) - 대상 요소 우클릭 - Copy - Copy XPath
							2. //*[@id="contents"]/div[1]/div[3] 이처럼 XPath에 큰따옴표가 포함된 경우에는 다음과 같이 작은따옴표로 처리해주어야 한다.  →  driver.findElement(By.xpath("//*[@id='contents']/div[1]/div[3]"));
						*/
						// driver.findElement(By.cssSelector(".layer-wrap .btn-close")).click();
						driver.findElement(By.xpath("/html/body/div[7]/div/div/button")).click();
					}
				}
				
				movieDTO.setTrailer_list(trailerList);
				
				
				// - director & actor -
				webElementList = driver.findElements(By.cssSelector(".tab-menu > li"));
				for(WebElement web_element : webElementList) {
					if(web_element.getText().equals("감독/출연")) {
						web_element.click();
						
						
						// director
						List<DirectorDTO> directorList = new ArrayList<>();
						
						webElement = driver.findElement(By.cssSelector(".sect-staff-director > ul"));
						if(webElement.getAttribute("innerHTML").contains("</li>")){
							webElementList = webElement.findElements(By.cssSelector(":scope > li"));
							for(WebElement wE : webElementList) {
								DirectorDTO directorDTO = new DirectorDTO();
								directorDTO.setKey(key);
								
								webElement = wE.findElement(By.cssSelector(":scope > .box-image .thumb-image > img"));
								String img = webElement.getAttribute("src");
								if(img.contains("[") && img.contains("]")) { img = img.replace(img.substring(img.indexOf("["), img.indexOf("]") + "]".length()), ""); }
								directorDTO.setImg(img);
								
								webElement = wE.findElement(By.cssSelector(":scope > .box-contents a"));
								String name = webElement.getAttribute("innerHTML").trim();
								String ko_name = name.substring(0, name.indexOf("<span>")).trim();
								String en_name = name.substring(name.indexOf("<span>") + "<span>".length(), name.indexOf("</span>"));
								directorDTO.setKo_name(ko_name);
								directorDTO.setEn_name(en_name);
								
								directorList.add(directorDTO);
							}
						}
						
						movieDTO.setDirector_list(directorList);
						
						// actor
						List<ActorDTO> actorList = new ArrayList<>();
						
						webElement = driver.findElement(By.cssSelector(".sect-staff-actor > ul"));
						if(webElement.getAttribute("innerHTML").contains("</li>")){
							webElementList = webElement.findElements(By.cssSelector(":scope > li"));
							for(WebElement wE : webElementList) {
								ActorDTO actorDTO = new ActorDTO();
								actorDTO.setKey(key);
								
								webElement = wE.findElement(By.cssSelector(":scope > .box-image .thumb-image > img"));
								String img = webElement.getAttribute("src");
								if(img.contains("[") && img.contains("]")) { img = img.replace(img.substring(img.indexOf("["), img.indexOf("]") + "]".length()), ""); }
								actorDTO.setImg(img);
								
								webElement = wE.findElement(By.cssSelector(":scope > .box-contents > dl > dt"));
								actorDTO.setRole_(webElement.getText());
								
								webElement = wE.findElement(By.cssSelector(":scope > .box-contents a"));
								String name = webElement.getAttribute("innerHTML").trim();
								String ko_name = name.substring(0, name.indexOf("<span>")).trim();
								String en_name = name.substring(name.indexOf("<span>") + "<span>".length(), name.indexOf("</span>"));
								actorDTO.setKo_name(ko_name);
								actorDTO.setEn_name(en_name);
								
								actorList.add(actorDTO);
							}
						}
						
						movieDTO.setActor_list(actorList);
						
						break;
					}
				}
				
				
				// - stillCut -
				webElementList = driver.findElements(By.cssSelector(".tab-menu > li"));
				for(WebElement web_element : webElementList) {
					if(web_element.getText().equals("스틸컷")) {
						web_element.click();
						
						
						List<StillCutDTO> stillCutList = new ArrayList<>();
						
						webElement = driver.findElement(By.cssSelector(".sect-stillcut > #stillcut_list"));
						if(webElement.getAttribute("innerHTML").contains("</div>")){
							int before_minHeight = Integer.parseInt(webElement.getCssValue("min-height").replace("px", ""));
							
							WebElement moreDataButton = driver.findElement(By.id("btnMoreData"));
							if(moreDataButton.isDisplayed()) { // isDisplayed : 화면상에 표시되어 있는지(즉, display 속성값이 none이면 false를 반환)
								moreDataButton.click();
								
								int after_minHeight = before_minHeight;
								while(before_minHeight == after_minHeight) {
									after_minHeight = Integer.parseInt(webElement.getCssValue("min-height").replace("px", ""));
								}
							}
							
							webElementList = webElement.findElements(By.tagName("div"));
							for(WebElement wE : webElementList) {
								StillCutDTO stillCutDTO = new StillCutDTO();
								stillCutDTO.setKey(key);
								stillCutDTO.setStillCut(wE.findElement(By.tagName("img")).getAttribute("src"));
								
								stillCutList.add(stillCutDTO);
							}
						}
						
						movieDTO.setStillCut_list(stillCutList);
						
						break;
					}
				}
				
				
				driver.close();
				driver.switchTo().window(parent_handle);
				
				break;
			}
		}
		
		return movieDTO;
	}
	
	
	// 임시 Key 생성
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
	
	private String fileName_impossibleChar_Converter(String str) {
		str = str.replace("/", "／");
		str = str.replace(":", "：");
		str = str.replace("?", "？");
		str = str.replace("*", "＊");
		str = str.replace("\\", "￦");
		str = str.replace("|", "｜");
		str = str.replace("<", "＜");
		str = str.replace(">", "＞");
		str = str.replace("[", "［");
		str = str.replace("]", "］");
		
		return str;
	}
	
	private void download(String url, File archive, String file_name) {
		File file = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		URLConnection urlConnection = null;
		
		try {
			urlConnection = new URL(url).openConnection();
			inputStream = urlConnection.getInputStream();
			
			if(!archive.exists()) {
				archive.mkdirs();
			}
			
			file = new File(archive, file_name);
			outputStream = new FileOutputStream(file);
			
			int read = 0;
			byte[] buf = new byte[2048];
			while((read = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, read);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(inputStream != null) inputStream.close();
				if(outputStream != null) outputStream.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
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
					move(archive, tempArchive, file_name);
				}
				break;
				
			case "trailer":
				archive = new File(new File(System.getProperty("user.dir")).getParent(), ".metadata/.plugins/org.eclipse.wst.server.core/" + CHANGEABLE_FOLDER + "/wtpwebapps/GGV/resources/movie_related_files/trailer/video");
				
				for(MovieDTO movieDTO : movie_list) {
					for(TrailerDTO trailerDTO : movieDTO.getTrailer_list()) {
						String file_name = trailerDTO.getVideo();
						move(archive, tempArchive, file_name);
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
	
	private void move(File archive, File tempArchive, String file_name) {
		if(!tempArchive.exists()) {
			tempArchive.mkdirs();
		}
		
		File file = new File(archive, file_name);
		file.renameTo(new File(tempArchive, file_name));
	}
}