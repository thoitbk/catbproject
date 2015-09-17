package com.catb.bo.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.AdministrativeProcedureBO;
import com.catb.dao.AdministrativeProcedureDAO;
import com.catb.dao.DepartmentDAO;
import com.catb.dao.FieldDAO;
import com.catb.model.AdministrativeProcedure;
import com.catb.model.AdministrativeProcedureFile;
import com.catb.model.Department;
import com.catb.model.Field;

@Service
public class AdministrativeProcedureBOImpl implements AdministrativeProcedureBO {
	
	@Autowired
	private AdministrativeProcedureDAO administrativeProcedureDAO;
	
	@Autowired
	private DepartmentDAO departmentDAO;
	
	@Autowired
	private FieldDAO fieldDAO;
	
	@Transactional
	public List<AdministrativeProcedure> getAdministrativeProcedures() {
		return administrativeProcedureDAO.getAdministrativeProcedures();
	}
	
	@Transactional
	public void addAdministrativeProcedure(
			AdministrativeProcedure administrativeProcedure,
			Integer departmentId, Integer fieldId,
			List<AdministrativeProcedureFile> files) {
		Department department = null;
		if (departmentId != null) {
			department = departmentDAO.getDepartmentById(departmentId);
		}
		administrativeProcedure.setDepartment(department);
		Field field = null;
		if (fieldId != null) {
			field = fieldDAO.getFieldById(fieldId);
		}
		administrativeProcedure.setField(field);
		
		administrativeProcedureDAO.addAdministrativeProcedure(administrativeProcedure);
		
		if (files != null && files.size() > 0) {
			for (AdministrativeProcedureFile file : files) {
				file.setAdministrativeProcedure(administrativeProcedure);
				administrativeProcedureDAO.addAdministrativeProcedureFile(file);
			}
		}
	}
	
	@Transactional
	public AdministrativeProcedure fetchAdministrativeProcedureById(Integer id) {
		AdministrativeProcedure administrativeProcedure = administrativeProcedureDAO.getAdministrativeProcedureById(id);
		if (administrativeProcedure != null) {
			Hibernate.initialize(administrativeProcedure.getDepartment());
			Hibernate.initialize(administrativeProcedure.getField());
			Hibernate.initialize(administrativeProcedure.getAdministrativeProcedureFiles());
		}
		
		return administrativeProcedure;
	}
	
	@Transactional
	public void updateAdministrativeProcedure(
			AdministrativeProcedure administrativeProcedure,
			Integer departmentId, Integer fieldId,
			List<AdministrativeProcedureFile> files) {
		AdministrativeProcedure a = administrativeProcedureDAO.getAdministrativeProcedureById(administrativeProcedure.getId());
		if (a != null) {
			a.setCode(administrativeProcedure.getCode());
			a.setContent(administrativeProcedure.getContent());
			a.setDescription(administrativeProcedure.getDescription());
			a.setName(administrativeProcedure.getName());
			a.setPublishedDate(administrativeProcedure.getPublishedDate());
			a.setSqNumber(administrativeProcedure.getSqNumber());
			a.setValidDuration(administrativeProcedure.getValidDuration());
			Department department = null;
			if (departmentId != null && departmentId >= 0) {
				department = departmentDAO.getDepartmentById(departmentId);
			}
			a.setDepartment(department);
			Field field = null;
			if (fieldId != null && fieldId >= 0) {
				field = fieldDAO.getFieldById(fieldId);
			}
			a.setField(field);
			
			if (files != null && files.size() > 0) {
				a.getAdministrativeProcedureFiles().clear();
				a.getAdministrativeProcedureFiles().addAll(files);
				for (AdministrativeProcedureFile file : files) {
					file.setAdministrativeProcedure(a);
					administrativeProcedureDAO.addAdministrativeProcedureFile(file);
				}
			}
			
			administrativeProcedureDAO.updateAdministrativeProcedure(a);
		}
	}
	
	@Transactional
	public void deleteAdministrativeProcedures(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				administrativeProcedureDAO.deleteAdministrativeProcedure(id);
			}
		}
	}
	
	@Transactional
	public List<AdministrativeProcedure> listAdministrativeProcedures() {
		return administrativeProcedureDAO.listAdministrativeProcedures();
	}
	
	@Transactional
	public AdministrativeProcedureFile getAdministrativeProcedureFile(Integer fileId) {
		return administrativeProcedureDAO.getAdministrativeProcedureFile(fileId);
	}
}
