package com.catb.model;

public enum NewsStatus {
	
	PENDING(0),
	APPROVED(1),
	DENIED(2);
	
	private int status;
	
	private NewsStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}
