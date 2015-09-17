package com.catb.dao;

import java.util.List;

import com.catb.model.Field;

public interface FieldDAO {
	
	public void addField(Field field);
	public List<Field> getFields();
	public Field getFieldById(Integer id);
	public void updateField(Field field);
	public void deleteField(Integer id);
}
