package catb.vanthu.valueobject;

import java.util.Date;

public class ComplexSearchInboundDocumentsVO {
	
	private String sign;
	private Integer type;
	private Integer confidentialLevel;
	private Integer urgentLevel;
	private Integer sender;
	private String signer;
	private Date fromPublishDate;
	private Date toPublishDate;
	private Date fromReceiveDate;
	private Date toReceiveDate;
	private String abs;
	
	public ComplexSearchInboundDocumentsVO() {
		
	}

	public ComplexSearchInboundDocumentsVO(String sign, Integer type,
			Integer confidentialLevel, Integer urgentLevel, Integer sender,
			String signer, Date fromPublishDate, Date toPublishDate,
			Date fromReceiveDate, Date toReceiveDate, String abs) {
		this.sign = sign;
		this.type = type;
		this.confidentialLevel = confidentialLevel;
		this.urgentLevel = urgentLevel;
		this.sender = sender;
		this.signer = signer;
		this.fromPublishDate = fromPublishDate;
		this.toPublishDate = toPublishDate;
		this.fromReceiveDate = fromReceiveDate;
		this.toReceiveDate = toReceiveDate;
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

	public Integer getSender() {
		return sender;
	}

	public void setSender(Integer sender) {
		this.sender = sender;
	}

	public String getSigner() {
		return signer;
	}

	public void setSigner(String signer) {
		this.signer = signer;
	}

	public Date getFromPublishDate() {
		return fromPublishDate;
	}

	public void setFromPublishDate(Date fromPublishDate) {
		this.fromPublishDate = fromPublishDate;
	}

	public Date getToPublishDate() {
		return toPublishDate;
	}

	public void setToPublishDate(Date toPublishDate) {
		this.toPublishDate = toPublishDate;
	}

	public Date getFromReceiveDate() {
		return fromReceiveDate;
	}

	public void setFromReceiveDate(Date fromReceiveDate) {
		this.fromReceiveDate = fromReceiveDate;
	}

	public Date getToReceiveDate() {
		return toReceiveDate;
	}

	public void setToReceiveDate(Date toReceiveDate) {
		this.toReceiveDate = toReceiveDate;
	}

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}
}
