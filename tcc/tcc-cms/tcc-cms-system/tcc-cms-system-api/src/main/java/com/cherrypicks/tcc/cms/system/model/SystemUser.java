package com.cherrypicks.tcc.cms.system.model;

import java.util.Date;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class SystemUser extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4360120061227567654L;

	private String userName;
	private String password;
	private String mobile;
	private String email;
	private Integer status;
	private String session;
	private Date sessionExpireTime;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public enum Status {
		INACTIVE(0), ACTIVE(1);

		private int code;

		private Status(int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		@Override
		public String toString() {
			return String.valueOf(this.code);
		}
	}

}
