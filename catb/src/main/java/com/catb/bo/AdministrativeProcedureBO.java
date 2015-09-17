package com.catb.bo;

import java.util.List;

import com.catb.model.AdministrativeProcedure;
import com.catb.model.AdministrativeProcedureFile;

public interface AdministrativeProcedureBO {

	public List<AdministrativeProcedure> getAdministrativeProcedures();
	public List<AdministrativeProcedure> listAdministrativeProcedures();
	public void addAdministrativeProcedure(
			AdministrativeProcedure administrativeProcedure,
			Integer departmentId, Integer fieldId,
			List<AdministrativeProcedureFile> files);
	public AdministrativeProcedure fetchAdministrativeProcedureById(Integer id);
	public void updateAdministrativeProcedure(
			AdministrativeProcedure administrativeProcedure,
			Integer departmentId, Integer fieldId,
			List<AdministrativeProcedureFile> files);
	public void deleteAdministrativeProcedures(Integer[] ids);
	public AdministrativeProcedureFile getAdministrativeProcedureFile(Integer fileId);
}
