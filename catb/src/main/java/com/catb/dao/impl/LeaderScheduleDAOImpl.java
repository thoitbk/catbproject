package com.catb.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.catb.dao.LeaderScheduleDAO;
import com.catb.model.LeaderSchedule;

@Repository
public class LeaderScheduleDAOImpl implements LeaderScheduleDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void addLeaderSchedule(LeaderSchedule leaderSchedule) {
		Session session = sessionFactory.getCurrentSession();
		session.save(leaderSchedule);
	}

	public void deleteLeaderSchedule(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		LeaderSchedule leaderSchedule = (LeaderSchedule) session.get(LeaderSchedule.class, id);
		if (leaderSchedule != null) {
			session.delete(leaderSchedule);
		}
	}

	@SuppressWarnings("unchecked")
	public LeaderSchedule getLeaderSchedule() {
		Session session = sessionFactory.getCurrentSession();
		String select = "FROM LeaderSchedule";
		
		List<LeaderSchedule> leaderSchedules = (List<LeaderSchedule>) session.createQuery(select).list();
		if (leaderSchedules != null && leaderSchedules.size() > 0) {
			return leaderSchedules.get(0);
		}
		
		return null;
	}
}
