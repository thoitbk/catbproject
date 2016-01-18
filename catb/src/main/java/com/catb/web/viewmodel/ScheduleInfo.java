package com.catb.web.viewmodel;

public class ScheduleInfo {
	
	private String date;
	private String leader;
	private String staffs;
	
	public ScheduleInfo() {
		
	}

	public ScheduleInfo(String date, String leader, String staffs) {
		this.date = date;
		this.leader = leader;
		this.staffs = staffs;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getStaffs() {
		return staffs;
	}

	public void setStaffs(String staffs) {
		this.staffs = staffs;
	}
}
