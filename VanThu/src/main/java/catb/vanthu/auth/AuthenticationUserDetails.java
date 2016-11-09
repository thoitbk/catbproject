package catb.vanthu.auth;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class AuthenticationUserDetails implements UserDetails {
	
	private String username;
	private String password;
	private Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
	
	public AuthenticationUserDetails(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.authorities.addAll(user.getAuthorities());
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}
}
