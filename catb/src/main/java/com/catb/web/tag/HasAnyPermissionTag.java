package com.catb.web.tag;

import org.apache.shiro.web.tags.PermissionTag;

@SuppressWarnings("serial")
public class HasAnyPermissionTag extends PermissionTag {
	
	private static final String PERMISSION_NAMES_DELIMITER = ",";
	
	public HasAnyPermissionTag() {
		
	}

	@Override
	protected boolean showTagBody(String permissionNames) {
		boolean hasAnyPermission = false;
		
		String[] permissions = permissionNames.split(PERMISSION_NAMES_DELIMITER);
		for (String permission : permissions) {
			if (isPermitted(permission.trim())) {
				hasAnyPermission = true;
				break;
			}
		}
		
		return hasAnyPermission;
	}
}
