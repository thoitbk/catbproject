package com.catb.web.viewmodel;

public class ScheduleViewModel {
	
	private String[] leaders;
	private String[] staffs;
	
	public ScheduleViewModel() {
		
	}

	public ScheduleViewModel(String[] leaders, String[] staffs) {
		this.leaders = leaders;
		this.staffs = staffs;
	}

	public String[] getLeaders() {
		return leaders;
	}

	public void setLeaders(String[] leaders) {
		this.leaders = leaders;
	}

	public String[] getStaffs() {
		return staffs;
	}

	public void setStaffs(String[] staffs) {
		this.staffs = staffs;
	}
}
