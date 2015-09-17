package com.catb.dao;

import java.util.List;

import com.catb.model.AdministrativeProcedure;
import com.catb.model.AdministrativeProcedureFile;

public interface AdministrativeProcedureDAO {
	
	public List<AdministrativeProcedure> getAdministrativeProcedures();
	public List<AdministrativeProcedure> listAdministrativeProcedures();
	public void addAdministrativeProcedure(AdministrativeProcedure administrativeProcedure);
	public void addAdministrativeProcedureFile(AdministrativeProcedureFile administrativeProcedureFile);
	public void updateAdministrativeProcedure(AdministrativeProcedure administrativeProcedure);
	public void updateAdministrativeProcedureFile(AdministrativeProcedureFile administrativeProcedureFile);
	public AdministrativeProcedure getAdministrativeProcedureById(Integer id);
	public void deleteAdministrativeProcedure(Integer id);
	public AdministrativeProcedureFile getAdministrativeProcedureFile(Integer fileId);
}
