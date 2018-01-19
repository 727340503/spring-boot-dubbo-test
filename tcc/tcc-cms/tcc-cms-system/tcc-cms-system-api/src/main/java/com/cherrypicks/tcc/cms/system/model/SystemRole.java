package com.cherrypicks.tcc.cms.system.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class SystemRole extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4981001571241488686L;
	private Integer roleType;
	private String roleName;
	private String roleDesc;

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	
	public enum Roletype{
		PLATFORM("PLATFORM",1),MALL("Mall",2);
		
		String value;
		int code;
		
		private Roletype(String value,int code) {
			this.value = value;
			this.code = code;
		}
		
		public String getValue(){
			return value;
		}
		
		public int getCode(){
			return code;
		}
	}

}
