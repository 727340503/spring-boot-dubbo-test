package com.cherrypicks.tcc.cms.merchant.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class MerchantFunctionFilterMap extends BaseModel {

	private static final long serialVersionUID = -1328827941735964248L;

	private Long merchantId;
	private Long funcId;

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getFuncId() {
		return funcId;
	}

	public void setFuncId(Long funcId) {
		this.funcId = funcId;
	}

}
