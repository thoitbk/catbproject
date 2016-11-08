package com.catb.dao;

import com.catb.model.LeaderSchedule;

public interface LeaderScheduleDAO {
	
	public void addLeaderSchedule(LeaderSchedule leaderSchedule);
	public void deleteLeaderSchedule(Integer id);
	public LeaderSchedule getLeaderSchedule();
}
