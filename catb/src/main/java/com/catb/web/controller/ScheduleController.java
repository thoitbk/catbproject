package com.catb.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.catb.bo.DepartmentBO;
import com.catb.bo.ScheduleBO;
import com.catb.common.PropertiesUtil;
import com.catb.model.Department;
import com.catb.model.Schedule;
import com.catb.vo.UserInfo;
import com.catb.web.tag.PageInfo;
import com.catb.web.util.Util;
import com.catb.web.viewmodel.ScheduleInfo;
import com.catb.web.viewmodel.ScheduleViewModel;

@Controller
public class ScheduleController {
	
	@Autowired
	private ScheduleBO scheduleBO;
	
	@Autowired
	private DepartmentBO departmentBO;
	
	private static final String[] DAYS_IN_WEEK = {"Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"};
	
	@RequiresPermissions(value = {"schedule:manage"})
	@RequestMapping(value = "/cm/schedule/add", method = RequestMethod.GET)
	public ModelAndView showCreateSchedule(
			ModelMap modelMap, @RequestParam(value = "p", defaultValue = "1", required = false) Integer page, HttpServletRequest request) {
		ScheduleViewModel scheduleViewModel = new ScheduleViewModel();
		modelMap.addAttribute("scheduleViewModel", scheduleViewModel);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date d = Util.getFirstDayOfCurrentWeek();
		String[] dates = new String[7];
		for (int i = 0; i < 7; i++) {
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.DATE, i);
			dates[i] = dateFormat.format(c.getTime());
		}
		
		
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		Integer departmentId = userInfo.getDepartmentId();
		
		Integer pageSize = Util.getPageSize(request);
		List<Schedule> schedules = scheduleBO.getSchedules(departmentId, page, pageSize);
		PageInfo pageInfo = new PageInfo(scheduleBO.countSchedules(departmentId).longValue(), page, pageSize);
		
		modelMap.addAttribute("schedules", schedules);
		modelMap.addAttribute("pageInfo", pageInfo);
		
		modelMap.addAttribute("daysInWeek", DAYS_IN_WEEK);
		modelMap.addAttribute("dates", dates);
		
		modelMap.addAttribute("userInfo", request.getSession().getAttribute("userInfo"));
		
		return new ModelAndView("cm/schedule/add");
	}
	
	@RequiresPermissions(value = {"schedule:manage"})
	@RequestMapping(value = "/cm/schedule/add", method = RequestMethod.POST)
	public ModelAndView processCreateSchedule(
			@Valid @ModelAttribute("scheduleViewModel") ScheduleViewModel scheduleViewModel, 
			BindingResult bindingResult, HttpServletRequest request, ModelMap model) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("cm/schedule/add");
		} else {
			Schedule schedule = new Schedule();
			schedule.setDate(Util.getFirstDayOfCurrentWeek());
			schedule.setWeek(calculateWeekNumber(Util.getFirstDayOfCurrentWeek()));
			schedule.setLeader1(scheduleViewModel.getLeaders()[0]);
			schedule.setStaffs1(scheduleViewModel.getStaffs()[0]);
			schedule.setLeader2(scheduleViewModel.getLeaders()[1]);
			schedule.setStaffs2(scheduleViewModel.getStaffs()[1]);
			schedule.setLeader3(scheduleViewModel.getLeaders()[2]);
			schedule.setStaffs3(scheduleViewModel.getStaffs()[2]);
			schedule.setLeader4(scheduleViewModel.getLeaders()[3]);
			schedule.setStaffs4(scheduleViewModel.getStaffs()[3]);
			schedule.setLeader5(scheduleViewModel.getLeaders()[4]);
			schedule.setStaffs5(scheduleViewModel.getStaffs()[4]);
			schedule.setLeader6(scheduleViewModel.getLeaders()[5]);
			schedule.setStaffs6(scheduleViewModel.getStaffs()[5]);
			schedule.setLeader7(scheduleViewModel.getLeaders()[6]);
			schedule.setStaffs7(scheduleViewModel.getStaffs()[6]);
			
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
			Integer departmentId = userInfo.getDepartmentId();
			
			scheduleBO.addSchedule(schedule, departmentId);
			
			request.getSession().setAttribute("msg", PropertiesUtil.getProperty("schedule.added.successfully"));
			
			return new ModelAndView(new RedirectView(request.getContextPath() + "/cm/schedule/add"));
		}
	}
	
	private int calculateWeekNumber(Date d) {
		Calendar current = Calendar.getInstance();
		current.setTime(d);
		Calendar c = Calendar.getInstance();
		c.set(current.get(Calendar.YEAR), 0, 1, 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		long diff = current.getTimeInMillis() - c.getTimeInMillis();
		long weekTime = 7 * 24 * 60 * 60 * 1000;
		
		return (int) (diff / weekTime) + 1;
	}
	
	@RequiresPermissions(value = {"schedule:manage"})
	@RequestMapping(value = "/cm/schedule/view/{id}", method = RequestMethod.GET)
	public ModelAndView viewSchedule(@PathVariable(value = "id") Integer id, ModelMap model, HttpServletRequest request) {
		Schedule schedule = scheduleBO.getSchedule(id);
		if (schedule != null) {
			List<ScheduleInfo> scheduleInfos = new LinkedList<ScheduleInfo>();
			Date d = schedule.getDate();
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			scheduleInfos.add(new ScheduleInfo(DAYS_IN_WEEK[0] + ", " + format.format(d), schedule.getLeader1(), schedule.getStaffs1()));
			cal.add(Calendar.DATE, 1);
			scheduleInfos.add(new ScheduleInfo(DAYS_IN_WEEK[1] + ", " + format.format(cal.getTime()), schedule.getLeader2(), schedule.getStaffs2()));
			cal.add(Calendar.DATE, 1);
			scheduleInfos.add(new ScheduleInfo(DAYS_IN_WEEK[2] + ", " + format.format(cal.getTime()), schedule.getLeader3(), schedule.getStaffs3()));
			cal.add(Calendar.DATE, 1);
			scheduleInfos.add(new ScheduleInfo(DAYS_IN_WEEK[3] + ", " + format.format(cal.getTime()), schedule.getLeader4(), schedule.getStaffs4()));
			cal.add(Calendar.DATE, 1);
			scheduleInfos.add(new ScheduleInfo(DAYS_IN_WEEK[4] + ", " + format.format(cal.getTime()), schedule.getLeader5(), schedule.getStaffs5()));
			cal.add(Calendar.DATE, 1);
			scheduleInfos.add(new ScheduleInfo(DAYS_IN_WEEK[5] + ", " + format.format(cal.getTime()), schedule.getLeader6(), schedule.getStaffs6()));
			cal.add(Calendar.DATE, 1);
			scheduleInfos.add(new ScheduleInfo(DAYS_IN_WEEK[6] + ", " + format.format(cal.getTime()), schedule.getLeader7(), schedule.getStaffs7()));
			
			model.addAttribute("scheduleInfos", scheduleInfos);
		}
		
		model.addAttribute("userInfo", request.getSession().getAttribute("userInfo"));
		
		return new ModelAndView("cm/schedule/view");
	}
	
	@RequestMapping(value = "/lich-cong-tac", method = RequestMethod.GET)
	public ModelAndView showSchedule(
			@RequestParam(value = "dId", required = true) Integer departmentId, 
			ModelMap model, HttpServletRequest request) {
		Date date = Util.getFirstDayOfCurrentWeek();
		Schedule schedule = scheduleBO.getScheduleByDateAndDepartment(date, departmentId);
		Department department = departmentBO.getDepartmentById(departmentId);
		
		String leader1 = null, staffs1 = null, leader2 = null, staffs2 = null, leader3 = null, staffs3 = null,
				leader4 = null, staffs4 = null, leader5 = null, staffs5 = null, leader6 = null, staffs6 = null, leader7 = null, staffs7 = null;
		if (schedule != null) {
			leader1 = schedule.getLeader1();
			staffs1 = schedule.getStaffs1();
			leader2 = schedule.getLeader2();
			staffs2 = schedule.getStaffs2();
			leader3 = schedule.getLeader3();
			staffs3 = schedule.getStaffs3();
			leader4 = schedule.getLeader4();
			staffs4 = schedule.getStaffs4();
			leader5 = schedule.getLeader5();
			staffs5 = schedule.getStaffs5();
			leader6 = schedule.getLeader6();
			staffs6 = schedule.getStaffs6();
			leader7 = schedule.getLeader7();
			staffs7 = schedule.getStaffs7();
		}
		
		ScheduleInfo[] scheduleInfos = new ScheduleInfo[7];
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		scheduleInfos[0] = new ScheduleInfo(DAYS_IN_WEEK[0] + ", " + format.format(date), leader1, staffs1);
		cal.add(Calendar.DATE, 1);
		scheduleInfos[1] = new ScheduleInfo(DAYS_IN_WEEK[1] + ", " + format.format(cal.getTime()), leader2, staffs2);
		cal.add(Calendar.DATE, 1);
		scheduleInfos[2] = new ScheduleInfo(DAYS_IN_WEEK[2] + ", " + format.format(cal.getTime()), leader3, staffs3);
		cal.add(Calendar.DATE, 1);
		scheduleInfos[3] = new ScheduleInfo(DAYS_IN_WEEK[3] + ", " + format.format(cal.getTime()), leader4, staffs4);
		cal.add(Calendar.DATE, 1);
		scheduleInfos[4] = new ScheduleInfo(DAYS_IN_WEEK[4] + ", " + format.format(cal.getTime()), leader5, staffs5);
		cal.add(Calendar.DATE, 1);
		scheduleInfos[5] = new ScheduleInfo(DAYS_IN_WEEK[5] + ", " + format.format(cal.getTime()), leader6, staffs6);
		cal.add(Calendar.DATE, 1);
		scheduleInfos[6] = new ScheduleInfo(DAYS_IN_WEEK[6] + ", " + format.format(cal.getTime()), leader7, staffs7);
		
		Date l = cal.getTime();
		int offset = Util.getDayOffset();
		
		model.addAttribute("scheduleInfos", scheduleInfos);
		model.addAttribute("userInfo", request.getSession().getAttribute("userInfo"));
		model.addAttribute("firstDay", date);
		model.addAttribute("lastDay", l);
		model.addAttribute("offset", offset);
		
		if (department != null) {
			model.addAttribute("departmentCode", department.getCode());
		}
		
		return new ModelAndView("schedule/show");
	}
}
