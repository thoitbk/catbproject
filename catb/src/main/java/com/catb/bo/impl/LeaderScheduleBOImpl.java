package com.catb.bo.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catb.bo.LeaderScheduleBO;
import com.catb.dao.LeaderScheduleDAO;
import com.catb.model.LeaderSchedule;

@Service
public class LeaderScheduleBOImpl implements LeaderScheduleBO {
	
	@Autowired
	private LeaderScheduleDAO leaderScheduleDAO;
	
	@Transactional
	public void addLeaderSchedule(LeaderSchedule leaderSchedule) {
		LeaderSchedule s = leaderScheduleDAO.getLeaderSchedule();
		if (s != null) {
			leaderScheduleDAO.deleteLeaderSchedule(s.getId());
		}
		
		leaderScheduleDAO.addLeaderSchedule(leaderSchedule);
	}
	
	@Transactional
	public LeaderSchedule getLeaderSchedule() {
		return leaderScheduleDAO.getLeaderSchedule();
	}
}
