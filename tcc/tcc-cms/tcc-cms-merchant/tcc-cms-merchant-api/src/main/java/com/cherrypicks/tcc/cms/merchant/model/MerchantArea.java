package com.cherrypicks.tcc.cms.merchant.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class MerchantArea extends BaseModel {
	/** 版本号 */
	private static final long serialVersionUID = 4696593090687497538L;

	private Long merchantId;

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

}