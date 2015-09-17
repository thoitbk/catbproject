package com.catb.bo.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.PermissionBO;
import com.catb.dao.PermissionDAO;
import com.catb.model.Permission;
import com.catb.vo.PermissionInfo;

@Service
public class PermissionBOImpl implements PermissionBO {
	
	@Autowired
	private PermissionDAO permissionDAO;
	
	@Transactional
	public List<Permission> getPermissions() {
		return permissionDAO.getPermissions();
	}
	
	@Transactional
	public void addPermission(Permission permission) {
		permissionDAO.addPermission(permission);
	}
	
	@Transactional
	public void updatePermission(Permission permission) {
		if (permission.getId() != null) {
			Permission p = permissionDAO.getPermissionById(permission.getId());
			if (p != null) {
				p.setName(permission.getName());
				p.setPerStr(permission.getPerStr());
				p.setDescription(permission.getDescription());
				
				permissionDAO.updatePermission(p);
			}
		}
	}
	
	@Transactional
	public void deletePermissions(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				permissionDAO.deletePermission(id);
			}
		}
	}
	
	@Transactional
	public Permission getPermissionById(Integer id) {
		return permissionDAO.getPermissionById(id);
	}
	
	@Transactional
	public Permission getPermissionByName(String name) {
		return permissionDAO.getPermissionByName(name);
	}
	
	@Transactional
	public Permission getPermissionByPerStr(String perStr) {
		return permissionDAO.getPermissionByPerStr(perStr);
	}
	
	@Transactional
	public List<Permission> getPermissionsByRoleId(Integer roleId) {
		return permissionDAO.getPermissionsByRoleId(roleId);
	}
	
	@Transactional
	public List<PermissionInfo> getPermissionInfoByRoleId(Integer roleId) {
		List<Permission> allPermissions = getPermissions();
		List<Permission> grantedPermissions = getPermissionsByRoleId(roleId);
		
		List<PermissionInfo> permissionInfos = new ArrayList<PermissionInfo>();
		if (allPermissions != null && grantedPermissions != null) {
			List<Integer> grantedPermissionIds = new ArrayList<Integer>();
			for (Permission permission : grantedPermissions) {
				grantedPermissionIds.add(permission.getId());
			}
			for (Permission permission : allPermissions) {
				permissionInfos.add(new PermissionInfo(permission, grantedPermissionIds.contains(permission.getId())));
			}
		}
		
		return permissionInfos;
	}
}
