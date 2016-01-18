package com.catb.bo.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.ScheduleBO;
import com.catb.dao.DepartmentDAO;
import com.catb.dao.ScheduleDAO;
import com.catb.model.Department;
import com.catb.model.Schedule;

@Service
public class ScheduleBOImpl implements ScheduleBO {
	
	@Autowired
	private ScheduleDAO scheduleDAO;
	
	@Autowired
	private DepartmentDAO departmentDAO;
	
	@Transactional
	public void addSchedule(Schedule schedule, Integer departmentId) {
		if (departmentId != null) {
			Department d = departmentDAO.getDepartmentById(departmentId);
			if (d != null) {
				schedule.setDepartment(d);
			}
			
			Schedule s = scheduleDAO.getScheduleByDateAndDepartment(schedule.getDate(), departmentId);
			if (s != null) {
				scheduleDAO.deleteSchedule(s.getId());
			}
			scheduleDAO.addSchedule(schedule);
		}
	}
	
	@Transactional
	public List<Schedule> getSchedules(Integer departmentId, Integer page,
			Integer pageSize) {
		return scheduleDAO.getSchedules(departmentId, page, pageSize);
	}
	
	@Transactional
	public Integer countSchedules(Integer departmentId) {
		return scheduleDAO.countSchedules(departmentId);
	}
	
	@Transactional
	public Schedule getSchedule(Integer id) {
		return scheduleDAO.getSchedule(id);
	}
	
	@Transactional
	public Schedule getScheduleByDateAndDepartment(Date date,
			Integer departmentId) {
		return scheduleDAO.getScheduleByDateAndDepartment(date, departmentId);
	}
}
