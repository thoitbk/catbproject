package catb.vanthu.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import catb.vanthu.bo.UserBO;
import catb.vanthu.dao.UserDAO;
import catb.vanthu.model.User;
import catb.vanthu.valueobject.SearchUserResult;
import catb.vanthu.valueobject.SearchUserVO;

@Service
public class UserBOImpl implements UserBO {
	
	@Autowired
	private UserDAO userDAO;
	
	public User findUserByUsername(String username) {
		return userDAO.findUserByUsername(username);
	}

	public void updateUser(User user) {
		userDAO.updateUser(user);
	}

	public void updateUserPassword(Integer id, String password) {
		userDAO.updateUserPassword(id, password);
	}

	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}

	public List<User> getUsers(int page, int pageSize) {
		return userDAO.getUsers(page, pageSize);
	}

	public Integer countUsers() {
		return getAllUsers().size();
	}

	public User findUserById(Integer id) {
		return userDAO.findUserById(id);
	}

	public void updateUser(User user, Integer departmentId) {
		userDAO.updateUser(user, departmentId);
	}

	public void deleteUser(Integer id) {
		userDAO.deleteUser(id);
	}

	public void saveUser(User user, Integer departmentId) {
		userDAO.saveUser(user, departmentId);
	}

	public SearchUserResult getUsers(SearchUserVO searchUserVO, int page, int pageSize) {
		return userDAO.getUsers(searchUserVO, page, pageSize);
	}
}
