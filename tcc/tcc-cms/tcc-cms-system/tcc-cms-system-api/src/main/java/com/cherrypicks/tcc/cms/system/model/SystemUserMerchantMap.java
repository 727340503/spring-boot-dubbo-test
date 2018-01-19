package com.cherrypicks.tcc.cms.system.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class SystemUserMerchantMap extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6852718769238519521L;
	private Long userId;
	private Long merchantId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

}
