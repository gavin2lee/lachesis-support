package com.lachesis.support.auth.common.vo;

import java.util.ArrayList;
import java.util.Collection;

public class AuthorizationResponseVO {
	private String id;
	private String username;
	private Collection<String> roles = new ArrayList<String>();
	private Collection<String> permissions = new ArrayList<String>();

	public AuthorizationResponseVO() {
		super();
	}

	public AuthorizationResponseVO(String id, String username) {
		super();
		this.id = id;
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Collection<String> getRoles() {
		return roles;
	}

	public void setRoles(Collection<String> roles) {
		if (roles != null) {
			this.roles.clear();
			this.roles.addAll(roles);
		}
	}

	public Collection<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Collection<String> permissions) {
		if (permissions != null) {
			this.permissions.clear();
			this.permissions.addAll(permissions);
		}
	}

	@Override
	public String toString() {
		return "AuthorizationResponseVO [id=" + id + ", username=" + username + ", roles=" + roles + ", permissions="
				+ permissions + "]";
	}
}
