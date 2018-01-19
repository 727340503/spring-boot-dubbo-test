package com.cherrypicks.tcc.cms.merchant.dto;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class StoreItemDTO extends BaseObject {

	private static final long serialVersionUID = -2482879148474361543L;
	private Long id;
	private String name;
	private String merchantName;
	private Integer status;
	private String externalStoreId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getExternalStoreId() {
		return externalStoreId;
	}

	public void setExternalStoreId(String externalStoreId) {
		this.externalStoreId = externalStoreId;
	}

}
