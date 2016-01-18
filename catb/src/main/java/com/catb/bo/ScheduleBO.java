package com.catb.bo;

import java.util.Date;
import java.util.List;

import com.catb.model.Schedule;

public interface ScheduleBO {
	
	public void addSchedule(Schedule schedule, Integer departmentId);
	public List<Schedule> getSchedules(Integer departmentId, Integer page, Integer pageSize);
	public Integer countSchedules(Integer departmentId);
	public Schedule getSchedule(Integer id);
	public Schedule getScheduleByDateAndDepartment(Date date, Integer departmentId);
}
