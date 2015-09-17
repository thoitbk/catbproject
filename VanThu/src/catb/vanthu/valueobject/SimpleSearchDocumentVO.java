package catb.vanthu.valueobject;

public class SimpleSearchDocumentVO {
	
	private String documentInfo;
	private Integer year;
	private Integer month;
	
	public SimpleSearchDocumentVO() {
		
	}

	public SimpleSearchDocumentVO(String documentInfo, Integer year,
			Integer month) {
		this.documentInfo = documentInfo;
		this.year = year;
		this.month = month;
	}

	public String getDocumentInfo() {
		return documentInfo;
	}

	public void setDocumentInfo(String documentInfo) {
		this.documentInfo = documentInfo;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}
}
