package com.cherrypicks.tcc.cms.merchant.dto;

import java.util.Date;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class MerchantConfigItemDTO extends BaseObject {

	private static final long serialVersionUID = -6235549112480760216L;

	private Long id;
	private Long merchantId;
	private String merchantName;
	private Date updatedTime;
	private Date createdTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
