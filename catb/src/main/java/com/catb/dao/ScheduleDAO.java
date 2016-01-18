package com.catb.dao;

import java.util.Date;
import java.util.List;

import com.catb.model.Schedule;

public interface ScheduleDAO {
	
	public void addSchedule(Schedule schedule);
	public void updateSchedule(Schedule schedule);
	public void deleteSchedule(Integer id);
	public Schedule getScheduleByDateAndDepartment(Date date, Integer departmentId);
	public List<Schedule> getSchedules(Integer departmentId, Integer page, Integer pageSize);
	public Integer countSchedules(Integer departmentId);
	public Schedule getSchedule(Integer id);
}
