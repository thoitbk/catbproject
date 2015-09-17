package com.catb.model;

public enum CommentStatus {
	
	PENDING_FOR_ANSWER(0),
	ANSWERED_NOT_SHOWED(1),
	ANSWERED_AND_SHOWED(2);
	
	private int status;
	
	private CommentStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}
