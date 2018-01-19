package com.cherrypicks.tcc.cms.system.dto;

import java.util.Date;
import java.util.List;

import com.cherrypicks.tcc.cms.dto.BaseObject;
import com.cherrypicks.tcc.cms.system.model.SystemFunction;


public class AuthorizeSystemUserDetailDTO extends BaseObject {

	private static final long serialVersionUID = 1512980861119240484L;

	private Long id;
	private Long systemRoleId;
	private Integer roleType;
	private Long merchantId;
	private String password;
	private String mobile;
	private String email;
	private Integer status;
	private String session;
	private Date sessionExpireTime;
	private List<SystemFunction> systemFuncs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSystemRoleId() {
		return systemRoleId;
	}

	public void setSystemRoleId(Long systemRoleId) {
		this.systemRoleId = systemRoleId;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public Date getSessionExpireTime() {
		return sessionExpireTime;
	}

	public void setSessionExpireTime(Date sessionExpireTime) {
		this.sessionExpireTime = sessionExpireTime;
	}

	public List<SystemFunction> getSystemFuncs() {
		return systemFuncs;
	}

	public void setSystemFuncs(List<SystemFunction> systemFuncs) {
		this.systemFuncs = systemFuncs;
	}

}
