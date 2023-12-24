package com.kmj.ExcelWriter_JXLS.movie.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import com.kmj.ExcelWriter_JXLS.movie.bean.MovieDTO;

import net.sf.jxls.transformer.XLSTransformer;

/*
	참고 사이트 : https://blog.naver.com/dngu_icdi/221397982672
	
	(참고)
	JXLS Template을 사용하여 Excel Write를 할 때 하이퍼링크를 걸 수 있는 방법은 없는 것 같으므로(별도의 유틸리티 클래스를 이용해야 하는 것 같다.) 직접 하이퍼링크를 걸어주어야 한다.
	※하이퍼링크 간편하게 거는 방법 : https://blog.naver.com/dngu_icdi/221398556892
	※URL 형식의 데이터에 하이퍼링크를 걸어주는 이유는 너무 오래돼서 잘 기억은 안 나지만(당시 기록을 해두지 않았다.), 아마 Excel Read 시 URL이 잘리는 현상이 발생해서 그랬던 것 같다.
*/
public class ExcelWriter {
	public void write(List<MovieDTO> movie_list) {
		Workbook workbook = null;
		InputStream is = null;
		OutputStream os = null;
		
		try {
			Map<String, Object> beans = new HashMap<>();
			beans.put("movie_list", movie_list);
			
			File templateFile = new File(System.getProperty("user.dir"), "template" + File.separator + "template.xlsx");
			is = new BufferedInputStream(new FileInputStream(templateFile));
			
			File desktop = new File(System.getProperty("user.home"), "Desktop");
			File excelFile = new File(desktop, "영화.xlsx"); 
			os = new BufferedOutputStream(new FileOutputStream(excelFile));
			
			XLSTransformer xlsTransformer = new XLSTransformer();
			workbook = xlsTransformer.transformXLS(is, beans);
			workbook.write(os);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(workbook != null) workbook.close();
				if(is != null) is.close();
				if(os != null) os.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}