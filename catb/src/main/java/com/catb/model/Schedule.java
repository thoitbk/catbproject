package com.catb.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name = "schedule")
public class Schedule implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date")
	private Date date;
	
	@Column(name = "week")
	private Integer week;
	
	@Column(name = "leader1")
	private String leader1;
	
	@Column(name = "staffs1")
	private String staffs1;
	
	@Column(name = "leader2")
	private String leader2;
	
	@Column(name = "staffs2")
	private String staffs2;
	
	@Column(name = "leader3")
	private String leader3;
	
	@Column(name = "staffs3")
	private String staffs3;
	
	@Column(name = "leader4")
	private String leader4;
	
	@Column(name = "staffs4")
	private String staffs4;
	
	@Column(name = "leader5")
	private String leader5;
	
	@Column(name = "staffs5")
	private String staffs5;
	
	@Column(name = "leader6")
	private String leader6;
	
	@Column(name = "staffs6")
	private String staffs6;
	
	@Column(name = "leader7")
	private String leader7;
	
	@Column(name = "staffs7")
	private String staffs7;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private Department department;

	public Schedule() {
		
	}

	public Schedule(Integer id, Date date, Integer week, String leader1,
			String staffs1, String leader2, String staffs2, String leader3,
			String staffs3, String leader4, String staffs4, String leader5,
			String staffs5, String leader6, String staffs6, String leader7,
			String staffs7, Department department) {
		this.id = id;
		this.date = date;
		this.week = week;
		this.leader1 = leader1;
		this.staffs1 = staffs1;
		this.leader2 = leader2;
		this.staffs2 = staffs2;
		this.leader3 = leader3;
		this.staffs3 = staffs3;
		this.leader4 = leader4;
		this.staffs4 = staffs4;
		this.leader5 = leader5;
		this.staffs5 = staffs5;
		this.leader6 = leader6;
		this.staffs6 = staffs6;
		this.leader7 = leader7;
		this.staffs7 = staffs7;
		this.department = department;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getWeek() {
		return week;
	}

	public void setWeek(Integer week) {
		this.week = week;
	}

	public String getLeader1() {
		return leader1;
	}

	public void setLeader1(String leader1) {
		this.leader1 = leader1;
	}

	public String getStaffs1() {
		return staffs1;
	}

	public void setStaffs1(String staffs1) {
		this.staffs1 = staffs1;
	}

	public String getLeader2() {
		return leader2;
	}

	public void setLeader2(String leader2) {
		this.leader2 = leader2;
	}

	public String getStaffs2() {
		return staffs2;
	}

	public void setStaffs2(String staffs2) {
		this.staffs2 = staffs2;
	}

	public String getLeader3() {
		return leader3;
	}

	public void setLeader3(String leader3) {
		this.leader3 = leader3;
	}

	public String getStaffs3() {
		return staffs3;
	}

	public void setStaffs3(String staffs3) {
		this.staffs3 = staffs3;
	}

	public String getLeader4() {
		return leader4;
	}

	public void setLeader4(String leader4) {
		this.leader4 = leader4;
	}

	public String getStaffs4() {
		return staffs4;
	}

	public void setStaffs4(String staffs4) {
		this.staffs4 = staffs4;
	}

	public String getLeader5() {
		return leader5;
	}

	public void setLeader5(String leader5) {
		this.leader5 = leader5;
	}

	public String getStaffs5() {
		return staffs5;
	}

	public void setStaffs5(String staffs5) {
		this.staffs5 = staffs5;
	}

	public String getLeader6() {
		return leader6;
	}

	public void setLeader6(String leader6) {
		this.leader6 = leader6;
	}

	public String getStaffs6() {
		return staffs6;
	}

	public void setStaffs6(String staffs6) {
		this.staffs6 = staffs6;
	}

	public String getLeader7() {
		return leader7;
	}

	public void setLeader7(String leader7) {
		this.leader7 = leader7;
	}

	public String getStaffs7() {
		return staffs7;
	}

	public void setStaffs7(String staffs7) {
		this.staffs7 = staffs7;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}
