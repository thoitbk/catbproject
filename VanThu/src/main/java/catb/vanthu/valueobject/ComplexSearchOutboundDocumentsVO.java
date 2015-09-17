package catb.vanthu.valueobject;

import java.util.Date;

public class ComplexSearchOutboundDocumentsVO {
	
	private String sign;
	private Integer type;
	private Integer confidentialLevel;
	private Integer urgentLevel;
	private Integer receiver;
	private String signer;
	private Date fromPublishDate;
	private Date toPublishDate;
	private Date fromSendDate;
	private Date toSendDate;
	private String abs;
	
	public ComplexSearchOutboundDocumentsVO() {
		
	}

	public ComplexSearchOutboundDocumentsVO(String sign, Integer type,
			Integer confidentialLevel, Integer urgentLevel, Integer receiver,
			String signer, Date fromPublishDate, Date toPublishDate,
			Date fromSendDate, Date toSendDate, String abs) {
		this.sign = sign;
		this.type = type;
		this.confidentialLevel = confidentialLevel;
		this.urgentLevel = urgentLevel;
		this.receiver = receiver;
		this.signer = signer;
		this.fromPublishDate = fromPublishDate;
		this.toPublishDate = toPublishDate;
		this.fromSendDate = fromSendDate;
		this.toSendDate = toSendDate;
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

	public Integer getReceiver() {
		return receiver;
	}

	public void setReceiver(Integer receiver) {
		this.receiver = receiver;
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

	public Date getFromSendDate() {
		return fromSendDate;
	}

	public void setFromSendDate(Date fromSendDate) {
		this.fromSendDate = fromSendDate;
	}

	public Date getToSendDate() {
		return toSendDate;
	}

	public void setToSendDate(Date toSendDate) {
		this.toSendDate = toSendDate;
	}

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}
}
