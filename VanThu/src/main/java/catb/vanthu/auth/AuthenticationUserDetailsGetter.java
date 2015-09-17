package catb.vanthu.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import catb.vanthu.bo.AuthBO;

public class AuthenticationUserDetailsGetter implements UserDetailsService {
	
	@Autowired
	private AuthBO authBO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = authBO.findUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User is not found");
		}
		
		return new AuthenticationUserDetails(user);
	}
}
