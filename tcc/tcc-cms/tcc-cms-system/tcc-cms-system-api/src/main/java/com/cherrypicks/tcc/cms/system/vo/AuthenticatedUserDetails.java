package com.cherrypicks.tcc.cms.system.vo;

import java.io.Serializable;
import java.util.List;

import com.cherrypicks.tcc.cms.system.model.SystemFunction;
import com.cherrypicks.tcc.cms.system.model.SystemRole;


public class AuthenticatedUserDetails implements Serializable {

	private static final long serialVersionUID = -9100561330610006469L;

	private String session;

	private Long systemUserId;

	private Long merchantId;

	private String userName;

	private SystemRole systemUserRole;

	private List<SystemFunction> systemFuncs;

	private List<SystemFunction> systemActions;

	private List<String> merchantLangCodes;

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public Long getSystemUserId() {
		return systemUserId;
	}

	public void setSystemUserId(Long systemUserId) {
		this.systemUserId = systemUserId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public SystemRole getSystemUserRole() {
		return systemUserRole;
	}

	public void setSystemUserRole(SystemRole systemUserRole) {
		this.systemUserRole = systemUserRole;
	}

	public List<SystemFunction> getSystemFuncs() {
		return systemFuncs;
	}

	public void setSystemFuncs(List<SystemFunction> systemFuncs) {
		this.systemFuncs = systemFuncs;
	}

	public List<SystemFunction> getSystemActions() {
		return systemActions;
	}

	public void setSystemActions(List<SystemFunction> systemActions) {
		this.systemActions = systemActions;
	}

	public List<String> getMerchantLangCodes() {
		return merchantLangCodes;
	}

	public void setMerchantLangCodes(List<String> merchantLangCodes) {
		this.merchantLangCodes = merchantLangCodes;
	}

}