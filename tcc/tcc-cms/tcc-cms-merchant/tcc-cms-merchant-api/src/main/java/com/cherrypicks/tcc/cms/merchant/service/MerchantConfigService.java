package com.cherrypicks.tcc.cms.merchant.service;

import com.cherrypicks.tcc.cms.merchant.model.MerchantConfig;
import com.cherrypicks.tcc.cms.service.IBaseService;

public interface MerchantConfigService extends IBaseService<MerchantConfig>{

	MerchantConfig findByMerchantId(final Long merchantId);

	void updateMerchantConfig(final MerchantConfig merchantConfig);

	MerchantConfig createMerchantConfig(final MerchantConfig merchantConfig, final String lang);

	void updateMerchantConfigCache(Long merchantId);

}
