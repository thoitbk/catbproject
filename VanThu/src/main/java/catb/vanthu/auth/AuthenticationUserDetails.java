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
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
