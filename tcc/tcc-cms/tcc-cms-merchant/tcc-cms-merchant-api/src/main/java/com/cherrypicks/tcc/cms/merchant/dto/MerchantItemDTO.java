package com.cherrypicks.tcc.cms.merchant.dto;

import java.util.Date;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class MerchantItemDTO extends BaseObject {

	private static final long serialVersionUID = -6235549112480760216L;

	private Long id;
	private Integer status;
	private String name;
	private Date createdTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
