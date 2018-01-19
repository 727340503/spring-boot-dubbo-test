package com.cherrypicks.tcc.cms.system.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class SystemUserRoleMap extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6644272876793733341L;
	private Long userId;
	private Long roleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
