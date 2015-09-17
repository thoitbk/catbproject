package catb.vanthu.valueobject;

public class FileMeta {
	
	private String id;
	private String fileName;
	private String fileSize;
	private String fileType;
	private String path;
	
	public FileMeta() {
		
	}

	public FileMeta(String id, String fileName, String fileSize,
			String fileType, String path) {
		this.id = id;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.fileType = fileType;
		this.path = path;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
