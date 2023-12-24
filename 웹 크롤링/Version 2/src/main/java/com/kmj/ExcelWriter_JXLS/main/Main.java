package com.kmj.ExcelWriter_JXLS.main;

import java.util.List;

import com.kmj.ExcelWriter_JXLS.movie.bean.MovieDTO;
import com.kmj.ExcelWriter_JXLS.movie.service.ExcelWriter;
import com.kmj.ExcelWriter_JXLS.movie.service.Selenium;

public class Main {
	public static void main(String[] args) throws Exception {
		Selenium selenium = new Selenium();
		List<MovieDTO> movie_list = selenium.start();
		
		ExcelWriter excelWriter = new ExcelWriter();
		excelWriter.write(movie_list);
		
		/*
			마지막에 아래와 같은 오류가 발생할 텐데 이 오류는 버그로 인해 발생하는 오류라고 하니 크게 신경 쓰지 않아도 될 것 같다.
			
 			SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
			SLF4J: Defaulting to no-operation (NOP) logger implementation
			SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
			WARNING: An illegal reflective access operation has occurred
			WARNING: Illegal reflective access by org.apache.poi.openxml4j.util.ZipSecureFile$1 (file:/C:/Users/Kmj/.m2/repository/org/apache/poi/poi-ooxml/3.14/poi-ooxml-3.14.jar) to field java.io.FilterInputStream.in
			WARNING: Please consider reporting this to the maintainers of org.apache.poi.openxml4j.util.ZipSecureFile$1
			WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
			WARNING: All illegal access operations will be denied in a future release
		*/
	}
}