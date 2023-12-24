package main;

import java.util.List;

import movie.bean.MovieDTO;
import movie.service.ExcelWriter;
import movie.service.WebScraping;

public class Main {
	public static void main(String[] args) throws Exception {
		WebScraping webScraping = new WebScraping();
		List<MovieDTO> movie_list = webScraping.start();
		
		ExcelWriter excelWriter = new ExcelWriter();
		excelWriter.write(movie_list);
	}
}