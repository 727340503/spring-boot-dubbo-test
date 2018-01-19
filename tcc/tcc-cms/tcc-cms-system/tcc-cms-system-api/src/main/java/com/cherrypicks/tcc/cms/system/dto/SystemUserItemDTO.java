package com.cherrypicks.tcc.cms.system.dto;

import java.util.Date;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class SystemUserItemDTO extends BaseObject {

	private static final long serialVersionUID = 3273544677238936729L;

	private Long id;
	private Date createdTime;
	private String userName;
	private Integer status;
	private String roleName;
	private String merchantName;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

}
