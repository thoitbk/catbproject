package catb.vanthu.valueobject;

import java.util.Date;
import java.util.List;

public class MailContentVO {
	
	private List<String> destinationAddresses;
	private String sign;
	private Date publishDate;
	private String signer;
	private List<String> senders;
	private String abs;
	private List<String> files;
	
	public MailContentVO() {
		
	}
	
	public MailContentVO(List<String> destinationAddresses, String sign,
			Date publishDate, String signer, List<String> senders, String abs,
			List<String> files) {
		this.destinationAddresses = destinationAddresses;
		this.sign = sign;
		this.publishDate = publishDate;
		this.signer = signer;
		this.senders = senders;
		this.abs = abs;
		this.files = files;
	}
	
	public List<String> getDestinationAddresses() {
		return destinationAddresses;
	}

	public void setDestinationAddresses(List<String> destinationAddresses) {
		this.destinationAddresses = destinationAddresses;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getSigner() {
		return signer;
	}

	public void setSigner(String signer) {
		this.signer = signer;
	}

	public List<String> getSenders() {
		return senders;
	}

	public void setSenders(List<String> senders) {
		this.senders = senders;
	}

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}
}
