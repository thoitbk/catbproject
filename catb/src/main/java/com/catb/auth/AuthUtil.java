package com.catb.auth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;

public class AuthUtil {
	
	// Getting passwordService instance from SecurityManager initialized by Spring at startup
	// to hash password
	public static String hashPasword(String password) {
		SecurityManager securityManager = SecurityUtils.getSecurityManager();
		if (securityManager instanceof DefaultSecurityManager) {
			DefaultSecurityManager defaultSecurityManager = (DefaultSecurityManager) securityManager;
			Realm[] realms = defaultSecurityManager.getRealms().toArray(new Realm[1]);
			if (realms.length > 0) {
				Realm r = realms[0];
				if (r instanceof AuthRealm) {
					AuthRealm authRealm = (AuthRealm) r;
					CredentialsMatcher credentialsMatcher = authRealm.getCredentialsMatcher();
					if (credentialsMatcher instanceof PasswordMatcher) {
						PasswordMatcher passwordMatcher = (PasswordMatcher) credentialsMatcher;
						PasswordService ps = passwordMatcher.getPasswordService();
						if (ps instanceof PasswordService) {
							PasswordService passwordService = (PasswordService) ps;
							return passwordService.encryptPassword(password);
						}
					}
				}
			}
		}
		
		return null;
	}
}
