package catb.vanthu.valueobject;

public class MailAttachment {
	
	private Integer part;
	private String fileName;
	private String contentType;
	private Integer size;
	
	public MailAttachment() {
		
	}

	public MailAttachment(Integer part, String fileName, String contentType, Integer size) {
		this.part = part;
		this.fileName = fileName;
		this.contentType = contentType;
		this.size = size;
	}

	public Integer getPart() {
		return part;
	}

	public void setPart(Integer part) {
		this.part = part;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
}
