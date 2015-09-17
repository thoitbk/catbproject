package com.catb.auth;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

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
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.catb.bo.UserBO;
import com.catb.model.User;

// Don't need to store salt in separated column here because salt value is encoded in hashed password and
// is already stored in the password field.
// If don't use PasswordService, we can manually generate salts and store them in separated column. And when
// implementing doGetAuthenticationInfo, we have to select salt value and set it to AuthenticationInfo
public class AuthRealm extends JdbcRealm {
	
	private UserBO userBO;
	
	public void setUserBO(UserBO userBO) {
		this.userBO = userBO;
	}
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        
        SimplePrincipalCollection principalCollection = new SimplePrincipalCollection(username, "authRealm");
        super.doClearCache(principalCollection);
        
        String[] queryResults = getPasswordForUser(username);
        String password = queryResults[0];
        if (password == null) {
            throw new UnknownAccountException("No account found for user [" + username + "]");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password.toCharArray(), getName());
        
        return info;
	}
	
	private String[] getPasswordForUser(String username) {

        String[] result = new String[1];
        User user = userBO.getUserByUsername(username);
        if (user == null) {
            throw new AuthenticationException("User: [" + username + "] doesn't exist");
        }
        result[0] = user.getPassword();

        return result;
    }
	
	@Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        String username = (String) getAvailablePrincipal(principals);
        
        Set<String> roleNames = getRoleNamesForUser(username);
        Set<String> permissions = getPermissions(username, roleNames);
        
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        
        return info;
    }
	
	protected Set<String> getRoleNamesForUser(String username) {
		return userBO.getRoleNamesForUser(username);
    }

    protected Set<String> getPermissions(String username, Collection<String> roleNames) {
    	Set<String> permissions = new LinkedHashSet<String>();
    	for (String roleName : roleNames) {
    		permissions.addAll(userBO.getPermissionStringsByRoleName(roleName));
    	}
    	
        return permissions;
    }

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}
}
