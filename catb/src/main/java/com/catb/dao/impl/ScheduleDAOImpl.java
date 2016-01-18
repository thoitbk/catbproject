package com.catb.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.catb.dao.ScheduleDAO;
import com.catb.model.Schedule;

@Repository
public class ScheduleDAOImpl implements ScheduleDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void addSchedule(Schedule schedule) {
		Session session = sessionFactory.getCurrentSession();
		session.save(schedule);
	}

	public void updateSchedule(Schedule schedule) {
		Session session = sessionFactory.getCurrentSession();
		session.update(schedule);
	}

	public void deleteSchedule(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		if (id != null) {
			Schedule schedule = (Schedule) session.get(Schedule.class, id);
			if (schedule != null) {
				session.delete(schedule);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Schedule getScheduleByDateAndDepartment(Date date,
			Integer departmentId) {
		Session session = sessionFactory.getCurrentSession();
		String q = "SELECT s FROM Schedule s INNER JOIN FETCH s.department d WHERE s.date = :date AND d.id = :id";
		Query query = session.createQuery(q);
		
		query.setParameter("date", date);
		query.setParameter("id", departmentId);
		
		List<Schedule> schedules = (List<Schedule>) query.list();
		
		return schedules == null || schedules.size() == 0 ? null : schedules.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Schedule> getSchedules(Integer departmentId, Integer page, Integer pageSize) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT s FROM Schedule s INNER JOIN s.department d WHERE d.id = :departmentId ORDER BY s.id DESC";
		Query query = session.createQuery(select);
		query.setParameter("departmentId", departmentId);
		
		query.setFirstResult((page - 1) * pageSize);
		query.setMaxResults(pageSize);
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public Integer countSchedules(Integer departmentId) {
		Session session = sessionFactory.getCurrentSession();
		String select = "SELECT s FROM Schedule s INNER JOIN s.department d WHERE d.id = :departmentId";
		Query query = session.createQuery(select);
		query.setParameter("departmentId", departmentId);
		
		List<Schedule> schedules = (List<Schedule>) query.list();
		return schedules == null ? 0 : schedules.size();
	}

	public Schedule getSchedule(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		return (Schedule) session.get(Schedule.class, id);
	}
}
