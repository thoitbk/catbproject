package com.catb.dao;

import java.util.List;

import com.catb.model.Permission;
import com.catb.model.User;

public interface UserDAO {
	
	public List<User> getUsers();
	public void addUser(User user);
	public User getUserByUsername(String username);
	public List<Permission> getPermissionsByRole(String roleName);
	public User getUserById(Integer id);
	public void updateUser(User user);
	public void deleteUser(Integer id);
	public List<User> getUsersByRoleId(Integer roleId);
	public List<User> getUsersDontHaveRoleId(Integer roleId);
	public User fetchUserByUsername(String username);
}
