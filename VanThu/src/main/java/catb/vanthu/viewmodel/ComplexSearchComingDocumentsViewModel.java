package catb.vanthu.viewmodel;

public class ComplexSearchComingDocumentsViewModel {
	
	private String ssign;
	private Integer stype;
	private Integer sconfidentialLevel;
	private Integer surgentLevel;
	private Integer ssender;
	private String sfromPublishDate;
	private String stoPublishDate;
	private String sfromReceiveDate;
	private String stoReceiveDate;
	private String sabs;
	
	public ComplexSearchComingDocumentsViewModel() {
		
	}

	public ComplexSearchComingDocumentsViewModel(String ssign, Integer stype,
			Integer sconfidentialLevel, Integer surgentLevel, Integer ssender,
			String sfromPublishDate, String stoPublishDate,
			String sfromReceiveDate, String stoReceiveDate, String sabs) {
		this.ssign = ssign;
		this.stype = stype;
		this.sconfidentialLevel = sconfidentialLevel;
		this.surgentLevel = surgentLevel;
		this.ssender = ssender;
		this.sfromPublishDate = sfromPublishDate;
		this.stoPublishDate = stoPublishDate;
		this.sfromReceiveDate = sfromReceiveDate;
		this.stoReceiveDate = stoReceiveDate;
		this.sabs = sabs;
	}

	public String getSsign() {
		return ssign;
	}

	public void setSsign(String ssign) {
		this.ssign = ssign;
	}

	public Integer getStype() {
		return stype;
	}

	public void setStype(Integer stype) {
		this.stype = stype;
	}

	public Integer getSconfidentialLevel() {
		return sconfidentialLevel;
	}

	public void setSconfidentialLevel(Integer sconfidentialLevel) {
		this.sconfidentialLevel = sconfidentialLevel;
	}

	public Integer getSurgentLevel() {
		return surgentLevel;
	}

	public void setSurgentLevel(Integer surgentLevel) {
		this.surgentLevel = surgentLevel;
	}

	public Integer getSsender() {
		return ssender;
	}

	public void setSsender(Integer ssender) {
		this.ssender = ssender;
	}

	public String getSfromPublishDate() {
		return sfromPublishDate;
	}

	public void setSfromPublishDate(String sfromPublishDate) {
		this.sfromPublishDate = sfromPublishDate;
	}

	public String getStoPublishDate() {
		return stoPublishDate;
	}

	public void setStoPublishDate(String stoPublishDate) {
		this.stoPublishDate = stoPublishDate;
	}

	public String getSfromReceiveDate() {
		return sfromReceiveDate;
	}

	public void setSfromReceiveDate(String sfromReceiveDate) {
		this.sfromReceiveDate = sfromReceiveDate;
	}

	public String getStoReceiveDate() {
		return stoReceiveDate;
	}

	public void setStoReceiveDate(String stoReceiveDate) {
		this.stoReceiveDate = stoReceiveDate;
	}

	public String getSabs() {
		return sabs;
	}

	public void setSabs(String sabs) {
		this.sabs = sabs;
	}
}
