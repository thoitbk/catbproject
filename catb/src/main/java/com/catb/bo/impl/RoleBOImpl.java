package com.catb.bo.impl;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catb.bo.RoleBO;
import com.catb.dao.PermissionDAO;
import com.catb.dao.RoleDAO;
import com.catb.model.Permission;
import com.catb.model.Role;

@Service
public class RoleBOImpl implements RoleBO {
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private PermissionDAO permissionDAO;
	
	@Transactional
	public List<Role> getRoles() {
		return roleDAO.getRoles();
	}
	
	@Transactional
	public void addRole(Role role) {
		roleDAO.addRole(role);
	}
	
	@Transactional
	public Role getRoleById(Integer id) {
		return roleDAO.getRoleById(id);
	}
	
	@Transactional
	public Role getRoleByName(String name) {
		return roleDAO.getRoleByName(name);
	}
	
	@Transactional
	public void updateRole(Role role) {
		if (role.getId() != null) {
			Role r = roleDAO.getRoleById(role.getId());
			if (r != null) {
				r.setName(role.getName());
				r.setDescription(role.getDescription());
				
				roleDAO.updateRole(r);
			}
		}
	}
	
	@Transactional
	public void deleteRoles(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				roleDAO.deleteRole(id);
			}
		}
	}
	
	@Transactional
	public void updatePermissionsOfRole(Integer roleId, Integer[] permissionIds) {
		Role role = roleDAO.getRoleById(roleId);
		if (role != null) {
			if (permissionIds != null) {
				List<Permission> permissions = permissionDAO.getPermissionsByIds(permissionIds);
				if (permissions != null) {
					Set<Permission> currentPermissions = role.getPermissions(); 
					for (Permission currentPermission : currentPermissions) {
						currentPermission.getRoles().remove(role);
						permissionDAO.updatePermission(currentPermission);
					}
					
					for (Permission permission : permissions) {
						permission.getRoles().add(role);
						permissionDAO.updatePermission(permission);
					}
					
					role.getPermissions().clear();
					role.getPermissions().addAll(permissions);
					roleDAO.updateRole(role);
				}
			}
		}
	}
}
