package catb.vanthu.bo.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import catb.vanthu.auth.AuthUtil;
import catb.vanthu.bo.AuthBO;
import catb.vanthu.dao.UserDAO;

@SuppressWarnings("deprecation")
@Service
public class AuthBOImpl implements AuthBO {
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public User findUserByUsername(String username) {
		catb.vanthu.model.User user = userDAO.findUserByUsername(username);
		if (user != null) {
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new GrantedAuthorityImpl(AuthUtil.getRoleName(user.getRole())));
			User u = new User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
			return u;
		}
		return null;
	}
}
