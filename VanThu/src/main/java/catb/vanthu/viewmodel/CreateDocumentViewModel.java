package catb.vanthu.viewmodel;

import java.util.Date;

public class CreateDocumentViewModel {
	
	private String sign;
	private Integer type;
	private Date publishDate;
	private Integer confidentialLevel;
	private Integer urgentLevel;
	private String signer;
	private String abs;
	private Integer[] senders;
	private Integer[] receivers;
	
	public CreateDocumentViewModel() {
		
	}

	public CreateDocumentViewModel(String sign, Integer type, Date publishDate,
			Integer confidentialLevel, Integer urgentLevel, String signer,
			String abs) {
		this.sign = sign;
		this.type = type;
		this.publishDate = publishDate;
		this.confidentialLevel = confidentialLevel;
		this.urgentLevel = urgentLevel;
		this.signer = signer;
		this.abs = abs;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Integer getConfidentialLevel() {
		return confidentialLevel;
	}

	public void setConfidentialLevel(Integer confidentialLevel) {
		this.confidentialLevel = confidentialLevel;
	}

	public Integer getUrgentLevel() {
		return urgentLevel;
	}

	public void setUrgentLevel(Integer urgentLevel) {
		this.urgentLevel = urgentLevel;
	}

	public String getSigner() {
		return signer;
	}

	public void setSigner(String signer) {
		this.signer = signer;
	}

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	public Integer[] getSenders() {
		return senders;
	}

	public void setSenders(Integer[] senders) {
		this.senders = senders;
	}

	public Integer[] getReceivers() {
		return receivers;
	}

	public void setReceivers(Integer[] receivers) {
		this.receivers = receivers;
	}
}
