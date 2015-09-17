package com.catb.bo;

import java.util.List;
import java.util.Set;

import com.catb.model.Role;
import com.catb.model.User;

public interface UserBO {
	
	public List<User> getUsers();
	public void addUser(User user);
	public User getUserByUsername(String username);
	public Set<Role> getRolesForUser(String username);
	public Set<String> getRoleNamesForUser(String username);
	public Set<String> getPermissionStringsByRoleName(String roleName);
	public void addUser(User user, Integer positionId, Integer departmentId);
	public User getUserById(Integer id);
	public void updateUser(User user, Integer positionId, Integer departmentId);
	public void deleteUsers(Integer[] ids);
	public List<User> getUsersByRoleId(Integer roleId);
	public List<User> getUsersDontHaveRoleId(Integer roleId);
	public void assignRoleToUser(Integer roleId, Integer userId);
	public void assignRoleToUsers(Integer roleId, Integer[] userIds);
	public void revokeRoleFromUser(Integer roleId, Integer userId);
	public void revokeRoleFromUsers(Integer roleId, Integer[] userIds);
	public void updateUser(User user);
	public void updateUserPassword(User user);
}
