package com.catb.bo;

import java.util.List;

import com.catb.model.Field;

public interface FieldBO {
	
	public void addField(Field field);
	public List<Field> getFields();
	public Field getFieldById(Integer id);
	public void updateField(Field field);
	public void deleteFields(Integer[] ids);
}
