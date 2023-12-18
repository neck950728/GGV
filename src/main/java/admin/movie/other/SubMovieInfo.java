package admin.movie.other;

import java.util.List;

public interface SubMovieInfo {
	public boolean isEmpty();
	public void ifNotURL_convert(IfNotURL_convert ifNotURL_convert, String insert_type);
	public List<FileWrapper> getFiles();
	public void set(int index, String value);
}