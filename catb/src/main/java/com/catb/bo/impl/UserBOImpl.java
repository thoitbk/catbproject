package com.catb.bo.impl;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.catb.bo.DepartmentBO;
import com.catb.bo.PositionBO;
import com.catb.bo.UserBO;
import com.catb.dao.RoleDAO;
import com.catb.dao.UserDAO;
import com.catb.model.Department;
import com.catb.model.Permission;
import com.catb.model.Position;
import com.catb.model.Role;
import com.catb.model.User;

@Service
public class UserBOImpl implements UserBO {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private DepartmentBO departmentBO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private PositionBO positionBO;
	
	@Transactional
	public List<User> getUsers() {
		return userDAO.getUsers();
	}

	@Transactional
	public void addUser(User user) {
		userDAO.addUser(user);
	}
	
	@Transactional
	public User getUserByUsername(String username) {
		return userDAO.getUserByUsername(username);
	}
	
	@Transactional
	public Set<Role> getRolesForUser(String username) {
		User user = userDAO.fetchUserByUsername(username);
		
		return user != null ? user.getRoles() : null;
	}
	
	@Transactional
	public Set<String> getRoleNamesForUser(String username) {
		Set<Role> roles = getRolesForUser(username);
		Set<String> roleNames = new LinkedHashSet<String>();
		if (roles != null) {
			for (Role role : roles) {
				String roleName = role.getName();
				if (roleName != null) {
					roleNames.add(roleName);
				}
			}
		}
		
		return roleNames;
	}
	
	@Transactional
	public Set<String> getPermissionStringsByRoleName(String roleName) {
		List<Permission> permissions = userDAO.getPermissionsByRole(roleName);
		Set<String> permissionSet = new LinkedHashSet<String>();
		for (Permission p : permissions) {
			if (p.getPerStr() != null) {
				permissionSet.add(p.getPerStr());
			}
		}
		
		return permissionSet;
	}
	
	@Transactional
	public void addUser(User user, Integer positionId, Integer departmentId) {
		if (positionId != null) {
			Position position = positionBO.getPositionById(positionId);
			if (position != null) {
				user.setPosition(position);
			}
		}
		if (departmentId != null) {
			Department department = departmentBO.getDepartmentById(departmentId);
			if (department != null) {
				user.setDepartment(department);
			}
		}
		
		userDAO.addUser(user);
	}

	@Transactional
	public User getUserById(Integer id) {
		User user = userDAO.getUserById(id);
		if (user != null) {
			Hibernate.initialize(user.getPosition());
			Hibernate.initialize(user.getDepartment());
		}
		
		return user;
	}
	
	@Transactional
	public void updateUser(User user, Integer positionId, Integer departmentId) {
		if (user.getId() != null) {
			User u = userDAO.getUserById(user.getId());
			if (u != null) {
				u.setUsername(user.getUsername());
				u.setFullName(user.getFullName());
				if (user.getPassword() != null) {
					u.setPassword(user.getPassword());
				}
				u.setGender(user.getGender());
				u.setHomePhoneNumber(user.getHomePhoneNumber());
				u.setMobileNumber(user.getMobileNumber());
				u.setOfficePhoneNumber(user.getOfficePhoneNumber());
				u.setAddress(user.getAddress());
				u.setEmail(user.getEmail());
				u.setDescription(user.getDescription());
				if (positionId != null) {
					Position position = positionBO.getPositionById(positionId);
					if (position != null) {
						u.setPosition(position);
					}
				} else {
					u.setPosition(null);
				}
				if (departmentId != null) {
					Department department = departmentBO.getDepartmentById(departmentId);
					if (department != null) {
						u.setDepartment(department);
					}
				} else {
					u.setDepartment(null);
				}
				
				userDAO.updateUser(u);
			}
		}
	}
	
	@Transactional
	public void deleteUsers(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				userDAO.deleteUser(id);
			}
		}
	}
	
	@Transactional
	public List<User> getUsersByRoleId(Integer roleId) {
		return userDAO.getUsersByRoleId(roleId);
	}
	
	@Transactional
	public List<User> getUsersDontHaveRoleId(Integer roleId) {
		return userDAO.getUsersDontHaveRoleId(roleId);
	}
	
	@Transactional
	public void assignRoleToUser(Integer roleId, Integer userId) {
		Role role = roleDAO.getRoleById(roleId);
		if (role != null) {
			User user = userDAO.getUserById(userId);
			if (user != null) {
				role.getUsers().add(user);
				user.getRoles().add(role);
				
				roleDAO.updateRole(role);
				userDAO.updateUser(user);
			}
		}
	}
	
	@Transactional
	public void assignRoleToUsers(Integer roleId, Integer[] userIds) {
		if (userIds != null && userIds.length > 0) {
			for (Integer userId : userIds) {
				assignRoleToUser(roleId, userId);
			}
		}
	}
	
	@Transactional
	public void revokeRoleFromUser(Integer roleId, Integer userId) {
		Role role = roleDAO.getRoleById(roleId);
		if (role != null) {
			User user = userDAO.getUserById(userId);
			if (user != null) {
				role.getUsers().remove(user);
				user.getRoles().remove(role);
				
				roleDAO.updateRole(role);
				userDAO.updateUser(user);
			}
		}
	}
	
	@Transactional
	public void revokeRoleFromUsers(Integer roleId, Integer[] userIds) {
		if (userIds != null && userIds.length > 0) {
			for (Integer userId : userIds) {
				revokeRoleFromUser(roleId, userId);
			}
		}
	}
	
	@Transactional
	public void updateUser(User user) {
		if (user.getId() != null) {
			User u = userDAO.getUserById(user.getId());
			if (u != null) {
				u.setUsername(user.getUsername());
				u.setFullName(user.getFullName());
				u.setOfficePhoneNumber(user.getOfficePhoneNumber());
				u.setMobileNumber(user.getMobileNumber());
				u.setHomePhoneNumber(user.getHomePhoneNumber());
				u.setAddress(user.getAddress());
				u.setEmail(user.getEmail());
				
				userDAO.updateUser(u);
			}
		}
	}
	
	@Transactional
	public void updateUserPassword(User user) {
		if (user.getId() != null) {
			User u = userDAO.getUserById(user.getId());
			if (u != null) {
				u.setPassword(user.getPassword());
				
				userDAO.updateUser(u);
			}
		}
	}
}
