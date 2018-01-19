package com.cherrypicks.tcc.cms.system.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class SystemRoleFunctionMap extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6706646535260446624L;
	private Long roleId;
	private Long funcId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getFuncId() {
		return funcId;
	}

	public void setFuncId(Long funcId) {
		this.funcId = funcId;
	}

}
