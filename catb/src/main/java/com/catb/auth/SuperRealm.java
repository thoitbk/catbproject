package com.catb.auth;

import java.util.Arrays;
import java.util.HashSet;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class SuperRealm extends AuthorizingRealm {
	
	private static final String USERNAME = "_tho.admin.catb_";
	private static final String PASSWORD = "12!@tdops34#$";
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
		
		String username = (String) getAvailablePrincipal(principals);
		if (username != null && username.equals(USERNAME)) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.setRoles(new HashSet<String>(Arrays.asList("*")));
			info.setStringPermissions(new HashSet<String>(Arrays.asList("*")));
			
			return info;
		}
		
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        
        if (!username.equals(USERNAME)) {
        	throw new UnknownAccountException("No account found for user [" + username + "]");
        }
        
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, PASSWORD.toCharArray(), getName());
        
        return info;
	}
}
