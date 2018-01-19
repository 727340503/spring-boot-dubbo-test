package com.cherrypicks.tcc.cms.system.dto;

import java.util.Date;
import java.util.List;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class SystemRoleDetailDTO extends BaseObject {

	private static final long serialVersionUID = 3273544677238936729L;

	private Long id;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;
	private Boolean isDeleted = false;
	private Integer roleType;
	private String roleName;
	private String roleDesc;
	private List<Long> systemFuncs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

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

	public List<Long> getSystemFuncs() {
		return systemFuncs;
	}

	public void setSystemFuncs(List<Long> systemFuncs) {
		this.systemFuncs = systemFuncs;
	}

}
