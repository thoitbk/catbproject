package com.catb.dao;

import java.util.List;

import com.catb.model.Permission;

public interface PermissionDAO {
	
	public List<Permission> getPermissions();
	public void addPermission(Permission permission);
	public void updatePermission(Permission permission);
	public void deletePermission(Integer id);
	public Permission getPermissionById(Integer id);
	public Permission getPermissionByName(String name);
	public Permission getPermissionByPerStr(String perStr);
	public List<Permission> getPermissionsByRoleId(Integer roleId);
	public List<Permission> getPermissionsByIds(Integer[] ids);
}
