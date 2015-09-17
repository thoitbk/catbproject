package com.catb.bo;

import java.util.List;

import com.catb.model.Permission;
import com.catb.vo.PermissionInfo;

public interface PermissionBO {
	
	public List<Permission> getPermissions();
	public void addPermission(Permission permission);
	public void updatePermission(Permission permission);
	public void deletePermissions(Integer[] ids);
	public Permission getPermissionById(Integer id);
	public Permission getPermissionByName(String name);
	public Permission getPermissionByPerStr(String perStr);
	public List<Permission> getPermissionsByRoleId(Integer roleId);
	public List<PermissionInfo> getPermissionInfoByRoleId(Integer roleId);
}
