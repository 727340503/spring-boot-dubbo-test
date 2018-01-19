package com.cherrypicks.tcc.cms.merchant.service;

import com.cherrypicks.tcc.cms.merchant.model.MerchantFunctionFilterMap;
import com.cherrypicks.tcc.cms.service.IBaseService;

public interface MerchantFunctionFilterMapService extends IBaseService<MerchantFunctionFilterMap>{

	
	void addCouponManagementModule(final Long merchantId);
	
	void removeCouponManagementModule(final Long userId,final Long merchantId);

}
