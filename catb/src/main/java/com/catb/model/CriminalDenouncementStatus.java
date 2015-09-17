package com.catb.model;

public enum CriminalDenouncementStatus {
	
	NOT_SEEN(0),
	SEEN(1),
	ANSWERED_VIA_EMAIL(2);
	
	private int status;
	
	private CriminalDenouncementStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}
