package admin.movie.other;

import java.io.File;

import utility.HttpServletReq;

public class IfNotURL_convert { // Callback 함수 용도로 사용
	public String convert(String folder, String code, String value, String insert_type) {
		if(!value.startsWith("http")) {
			if(insert_type.equals("excel")) {
				String originalFileName = value.substring(value.indexOf("_") + "_".length());
				file_renameTo(folder, code, value, originalFileName);
				value = originalFileName;
			}
			
			value = HttpServletReq.request.getContextPath() + "/resources/movie_related_files/" + folder + "/" + code + "_" + value;
		}
		
		return value;
	}
	
	private void file_renameTo(String folder, String code, String value, String originalFileName) {
		String file_path = HttpServletReq.request.getSession().getServletContext().getRealPath("/resources/movie_related_files/" + folder);
		
		File file = new File(file_path, value);
		file.renameTo(new File(file_path, code + "_" + originalFileName));
	}
}