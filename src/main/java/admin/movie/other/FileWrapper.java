package admin.movie.other;

public class FileWrapper {
	private String file;
	private String name;
	private String path;
	
	public FileWrapper(String file) {
		this.file = file;
		
		if(!file.startsWith("http")) {
			this.name = file.substring(file.lastIndexOf("/") + "/".length());
			this.path = file.substring(file.indexOf("/resources"), file.lastIndexOf("/"));
		}else {
			this.name = null;
			this.path = null;
		}
	}
	
	public String getFile() {
		return file;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPath() {
		return path;
	}
}