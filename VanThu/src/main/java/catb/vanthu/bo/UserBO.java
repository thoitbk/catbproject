package catb.vanthu.bo;

import java.util.List;

import catb.vanthu.model.User;
import catb.vanthu.valueobject.SearchUserResult;
import catb.vanthu.valueobject.SearchUserVO;

public interface UserBO {
	
	public User findUserByUsername(String username);
	public void updateUser(User user);
	public void updateUserPassword(Integer id, String password);
	public List<User> getAllUsers();
	public List<User> getUsers(int page, int pageSize);
	public Integer countUsers();
	public User findUserById(Integer id);
	public void updateUser(User user, Integer departmentId);
	public void deleteUser(Integer id);
	public void saveUser(User user, Integer departmentId);
	public SearchUserResult getUsers(SearchUserVO searchUserVO, int page, int pageSize);
}
