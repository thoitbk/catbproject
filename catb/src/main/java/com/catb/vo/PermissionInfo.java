package com.catb.vo;

import com.catb.model.Permission;

public class PermissionInfo {
	
	private Permission permission;
	private Boolean granted;
	
	public PermissionInfo() {
		
	}

	public PermissionInfo(Permission permission, Boolean granted) {
		this.permission = permission;
		this.granted = granted;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public Boolean getGranted() {
		return granted;
	}

	public void setGranted(Boolean granted) {
		this.granted = granted;
	}
}
