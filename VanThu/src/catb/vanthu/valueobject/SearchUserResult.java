package catb.vanthu.valueobject;

import java.util.List;

import catb.vanthu.model.User;

public class SearchUserResult {
	
	private List<User> users;
	private Integer size;
	
	public SearchUserResult() {
		
	}

	public SearchUserResult(List<User> users, Integer size) {
		this.users = users;
		this.size = size;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
}
