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
	
	@Override
	public User findUserByUsername(String username) {
		return userDAO.findUserByUsername(username);
	}

	@Override
	public void updateUser(User user) {
		userDAO.updateUser(user);
	}

	@Override
	public void updateUserPassword(Integer id, String password) {
		userDAO.updateUserPassword(id, password);
	}

	@Override
	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}

	@Override
	public List<User> getUsers(int page, int pageSize) {
		return userDAO.getUsers(page, pageSize);
	}

	@Override
	public Integer countUsers() {
		return getAllUsers().size();
	}

	@Override
	public User findUserById(Integer id) {
		return userDAO.findUserById(id);
	}

	@Override
	public void updateUser(User user, Integer departmentId) {
		userDAO.updateUser(user, departmentId);
	}

	@Override
	public void deleteUser(Integer id) {
		userDAO.deleteUser(id);
	}

	@Override
	public void saveUser(User user, Integer departmentId) {
		userDAO.saveUser(user, departmentId);
	}

	@Override
	public SearchUserResult getUsers(SearchUserVO searchUserVO, int page, int pageSize) {
		return userDAO.getUsers(searchUserVO, page, pageSize);
	}
}
