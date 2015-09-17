package com.catb.bo;

import java.util.List;

import com.catb.model.Role;

public interface RoleBO {
	
	public List<Role> getRoles();
	public void addRole(Role role);
	public Role getRoleById(Integer id);
	public Role getRoleByName(String name);
	public void updateRole(Role role);
	public void deleteRoles(Integer[] ids);
	public void updatePermissionsOfRole(Integer roleId, Integer[] permissionIds);
}
