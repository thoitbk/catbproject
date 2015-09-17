package catb.vanthu.bo;

import org.springframework.security.core.userdetails.User;

public interface AuthBO {
	
	public User findUserByUsername(String username);
}
