package com.catb.dao;

import java.util.List;

import com.catb.model.Role;

public interface RoleDAO {
	
	public List<Role> getRoles();
	public void addRole(Role role);
	public Role getRoleById(Integer id);
	public Role getRoleByName(String name);
	public void updateRole(Role role);
	public void deleteRole(Integer id);
	public Role fetchRoleById(Integer id);
}
