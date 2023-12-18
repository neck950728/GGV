package admin.movie.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import admin.movie.bean.ActorDTO;
import admin.movie.bean.DirectorDTO;
import admin.movie.bean.MovieDTO;
import admin.movie.bean.ScreeningInfoDTO;
import admin.movie.bean.StillCutDTO;
import admin.movie.bean.TrailerDTO;
import admin.movie.dao.AdminMovieDAO;
import admin.movie.other.IfNotURL_convert;
import admin.movie.other.MovieListWrapper;
import admin.movie.other.MoviePaging;
import admin.movie.other.SubMovieInfo;
import net.sf.jxls.transformer.XLSTransformer;

@Service
public class AdminMovieServiceImpl implements AdminMovieService {
	@Autowired
	private AdminMovieDAO adminMovieDAO;
	
	private String getRandomMovieCode() {
		Random rnd = new Random();
		String code = "";

		for(int i = 0; i < 15; i++) {
		    if(rnd.nextBoolean()) { // true 또는 false를 랜덤으로 반환
		    	code += (char)((int)(Math.random() * (90 - 65 + 1)) + 65); // A(65) ~ Z(90)
		    }else {
		    	code += rnd.nextInt(10); // nextInt(10) : 0 ~ 9까지의 정수를 랜덤으로 반환
		    }
		}
		
		int result = adminMovieDAO.movieCodeDuplicateCheck(code);
		if(result > 0) {
			code = getRandomMovieCode();
		}
		
		return code;
	}
	
	private String getRandomIdentificationCode() {
		Random rnd = new Random();
		String code = "";

		for(int i = 0; i < 15; i++) {
		    if(rnd.nextBoolean()) {
		    	code += (char)((int)(Math.random() * (90 - 65 + 1)) + 65);
		    }else {
		    	code += rnd.nextInt(10);
		    }
		}
		
		int result = adminMovieDAO.identificationCodeDuplicateCheck(code);
		if(result > 0) {
			code = getRandomIdentificationCode();
		}
		
		return code;
	}
	
	private SubMovieInfo getSubMovieInfo(int index) {
		SubMovieInfo subMovieInfo = null;
		
		switch(index) {
			case 1:
				subMovieInfo = new StillCutDTO();
				break;
			case 2:
				subMovieInfo = new TrailerDTO();
				break;
			case 3:
				subMovieInfo = new DirectorDTO();
				break;
			case 4:
				subMovieInfo = new ActorDTO();
		}
		
		return subMovieInfo;
	}
	
	
	@Transactional
	@Override
	public String insertMovie(MovieDTO movieDTO) {
		String code = getRandomMovieCode();
		movieDTO.setCode(code);
		
		IfNotURL_convert ifNotURL_convert = new IfNotURL_convert();
		movieDTO.ifNotURL_convert(ifNotURL_convert, "personally");
		
		int subMovieInfo_count = 4;
		for(int i = 1; i <= subMovieInfo_count; i++) {
			List<? extends SubMovieInfo> subMovieInfo_list = movieDTO.getSubMovieInfo_list(i);
			
			for(SubMovieInfo subMovieInfo : subMovieInfo_list) {
				if(subMovieInfo.isEmpty()) {
					subMovieInfo_list.remove(subMovieInfo);
				}else {
					subMovieInfo.set(0, code);
					subMovieInfo.ifNotURL_convert(ifNotURL_convert, "personally");
				}
			}
		}
		
		for(ScreeningInfoDTO screeningInfoDTO : movieDTO.getScreeningInfo_list()) {
			screeningInfoDTO.setIdentification_code(getRandomIdentificationCode());
			screeningInfoDTO.setMovie_code(code);
		}
		
		adminMovieDAO.insertMovie(movieDTO);
		
		return code;
	}
	
	@Override
	public void insertMovie(File excelFile) {
		File excel_file = excelFile;
		InputStream is = null;
		Workbook workbook = null;
		
		try {
			/*
				● 업로드된 Excel 파일 읽기 ●
				
				(참고)
				JXLS Reader를 이용하면 좀 더 간편하게 Excel Read를 할 수 있지만,
				사용법이 꽤나 까다로워 보여서 그냥 단순 JXLS로 Excel Read하였다.
			*/
			List<MovieDTO> movie_list = new ArrayList<>();
			List<String> inUse_code_list = new ArrayList<>();
			int cur_row_index_list[] = {1, 1, 1, 1};
			
			XLSTransformer xlsTransformer = new XLSTransformer();
			is = new BufferedInputStream(new FileInputStream(excel_file));
			workbook = xlsTransformer.transformXLS(is, new HashMap<String, Object>());
			
			Sheet movie_sheet = workbook.getSheetAt(0);
			for(int i = 1; i <= movie_sheet.getLastRowNum(); i++) { // Sheet.getLastRowNum() 반환값 : 마지막 행 index
				Row row = movie_sheet.getRow(i);
				String key = row.getCell(0).getStringCellValue();
				
				MovieDTO movieDTO = new MovieDTO();
				String code;
				while(true) {
					code = getRandomMovieCode();
					if(!inUse_code_list.contains(code)) {
						movieDTO.set(0, code);
						inUse_code_list.add(code);
						break;
					}
				}
				
				for(int j = 1; j < row.getLastCellNum(); j++) { // Sheet.Row.getLastCellNum() 반환값 : 마지막 열 index + 1
					String value = row.getCell(j).getStringCellValue();
					if(value.startsWith("http")) {
						value = row.getCell(j).getHyperlink().getAddress();
					}
					
					movieDTO.set(j, value);
				}
				
				for(int k = 1; k < workbook.getNumberOfSheets(); k++) { // Workbook.getNumberOfSheets() 반환값 : 시트 개수 
					CopyOnWriteArrayList<SubMovieInfo> subMovieInfo_list = new CopyOnWriteArrayList<>();
					
					Sheet sheet = workbook.getSheetAt(k);
					row = sheet.getRow(cur_row_index_list[k - 1]);
					try {
						while(key.equals(row.getCell(0).getStringCellValue()) || row.getCell(0).getStringCellValue().isEmpty()) {
							if(row.getCell(0).getStringCellValue().isEmpty()) {
								cur_row_index_list[k - 1] += 1;
								row = sheet.getRow(cur_row_index_list[k - 1]);
							}else {
								SubMovieInfo subMovieInfo = getSubMovieInfo(k);
								subMovieInfo.set(0, code);
								
								for(int l = 1; l < row.getLastCellNum(); l++) {
									String value = row.getCell(l).getStringCellValue();
									if(value.startsWith("http")) {
										value = row.getCell(l).getHyperlink().getAddress();
									}
									subMovieInfo.set(l, value);
								}
								subMovieInfo.ifNotURL_convert(new IfNotURL_convert(), "excel");
								subMovieInfo_list.add(subMovieInfo);
								
								cur_row_index_list[k - 1] += 1;
								row = sheet.getRow(cur_row_index_list[k - 1]);
							}
						}
					}catch(NullPointerException e) {
						// 더 이상 데이터가 없을 때 NullPointerException 발생(마지막 행 다음)
					}
					
					movieDTO.setSubMovieInfo_list(k, subMovieInfo_list);
				}
				movieDTO.ifNotURL_convert(new IfNotURL_convert(), "excel");
				movie_list.add(movieDTO);
			}
			
			// ----------------------------------------------------------------------------------------------------------
			
			// ● insert ●
			for(MovieDTO movieDTO : movie_list) {
				adminMovieDAO.insertMovie(movieDTO);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				workbook.close();
				is.close();
				excel_file.delete();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public MovieListWrapper selectMovieList(int page) {
		MoviePaging paging = new MoviePaging(page, adminMovieDAO.selectTotalMovieCount());
		List<MovieDTO> curPageMovieList = adminMovieDAO.selectCurPageMovieList(paging.getStartNum(), paging.getEndNum());
		
		MovieListWrapper movieListWrapper = new MovieListWrapper();
		movieListWrapper.setPaging(paging);
		movieListWrapper.setMovieList(curPageMovieList);
		
		return movieListWrapper;
	}
	
	@Override
	public List<MovieDTO> searchMovie(String keyword) {
		return adminMovieDAO.selectSearchedMovieList(keyword);
	}
	
	@Override
	public MovieDTO selectMovieIncludingSubMovieInfo(String code) {
		return adminMovieDAO.selectMovie_includingSubMovieInfo(code);
	}
	
	@Transactional
	@Override
	public void deleteMovie(String code) {
		adminMovieDAO.deleteMovie(code);
	}
	
	@Override
	public MovieDTO selectMovieForModify(String code) {
		MovieDTO movieDTO = adminMovieDAO.selectMovieForModify(code);
		return movieDTO;
	}
	
	@Transactional
	@Override
	public void modifyMovie(MovieDTO new_movieDTO) {
		IfNotURL_convert ifNotURL_convert = new IfNotURL_convert();
		new_movieDTO.ifNotURL_convert(ifNotURL_convert, "");
		
		int subMovieInfo_count = 4;
		for(int i = 1; i <= subMovieInfo_count; i++) {
			List<? extends SubMovieInfo> subMovieInfo_list = new_movieDTO.getSubMovieInfo_list(i);
			
			for(SubMovieInfo subMovieInfo : subMovieInfo_list) {
				if(subMovieInfo.isEmpty()) {
					subMovieInfo_list.remove(subMovieInfo);
				}else {
					subMovieInfo.set(0, new_movieDTO.getCode());
					subMovieInfo.ifNotURL_convert(ifNotURL_convert, "");
				}
			}
		}
		
		for(ScreeningInfoDTO screeningInfoDTO : new_movieDTO.getScreeningInfo_list()) {
			if(screeningInfoDTO.getIdentification_code() == null) { screeningInfoDTO.setIdentification_code(getRandomIdentificationCode()); }
			screeningInfoDTO.setMovie_code(new_movieDTO.getCode());
		}
		
		adminMovieDAO.updateMovie(new_movieDTO);
	}
}