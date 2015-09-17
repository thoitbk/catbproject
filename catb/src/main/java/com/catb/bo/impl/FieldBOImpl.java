package com.catb.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.FieldBO;
import com.catb.dao.FieldDAO;
import com.catb.model.Field;

@Service
public class FieldBOImpl implements FieldBO {
	
	@Autowired
	private FieldDAO fieldDAO;
	
	@Transactional
	public void addField(Field field) {
		fieldDAO.addField(field);
	}
	
	@Transactional
	public List<Field> getFields() {
		return fieldDAO.getFields();
	}
	
	@Transactional
	public Field getFieldById(Integer id) {
		return fieldDAO.getFieldById(id);
	}
	
	@Transactional
	public void updateField(Field field) {
		fieldDAO.updateField(field);
	}
	
	@Transactional
	public void deleteFields(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				fieldDAO.deleteField(id);
			}
		}
	}
}
