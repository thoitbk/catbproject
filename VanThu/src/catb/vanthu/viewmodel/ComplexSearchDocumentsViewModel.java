package catb.vanthu.viewmodel;

public class ComplexSearchDocumentsViewModel {
	
	private String ssign;
	private Integer stype;
	private Integer sconfidentialLevel;
	private Integer surgentLevel;
	private Integer ssender;
	private Integer sreceiver;
	private String ssigner;
	private String sfromPublishDate;
	private String stoPublishDate;
	private String sfromSendDate;
	private String stoSendDate;
	private String sabs;
	
	public ComplexSearchDocumentsViewModel() {
		
	}

	public ComplexSearchDocumentsViewModel(String ssign, Integer stype,
			Integer sconfidentialLevel, Integer surgentLevel, Integer ssender,
			Integer sreceiver, String ssigner, String sfromPublishDate,
			String stoPublishDate, String sfromSendDate, String stoSendDate,
			String sabs) {
		this.ssign = ssign;
		this.stype = stype;
		this.sconfidentialLevel = sconfidentialLevel;
		this.surgentLevel = surgentLevel;
		this.ssender = ssender;
		this.sreceiver = sreceiver;
		this.ssigner = ssigner;
		this.sfromPublishDate = sfromPublishDate;
		this.stoPublishDate = stoPublishDate;
		this.sfromSendDate = sfromSendDate;
		this.stoSendDate = stoSendDate;
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

	public Integer getSreceiver() {
		return sreceiver;
	}

	public void setSreceiver(Integer sreceiver) {
		this.sreceiver = sreceiver;
	}

	public String getSsigner() {
		return ssigner;
	}

	public void setSsigner(String ssigner) {
		this.ssigner = ssigner;
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

	public String getSfromSendDate() {
		return sfromSendDate;
	}

	public void setSfromSendDate(String sfromSendDate) {
		this.sfromSendDate = sfromSendDate;
	}

	public String getStoSendDate() {
		return stoSendDate;
	}

	public void setStoSendDate(String stoSendDate) {
		this.stoSendDate = stoSendDate;
	}

	public String getSabs() {
		return sabs;
	}

	public void setSabs(String sabs) {
		this.sabs = sabs;
	}
}
