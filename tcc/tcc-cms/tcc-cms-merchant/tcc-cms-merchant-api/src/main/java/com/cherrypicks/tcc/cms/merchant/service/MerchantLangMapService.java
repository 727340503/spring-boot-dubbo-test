package com.cherrypicks.tcc.cms.merchant.service;

import java.util.List;

import com.cherrypicks.tcc.cms.merchant.model.Merchant;
import com.cherrypicks.tcc.cms.merchant.model.MerchantLangMap;
import com.cherrypicks.tcc.cms.service.IBaseService;

public interface MerchantLangMapService extends IBaseService<MerchantLangMap>{

	List<MerchantLangMap> createMerchantLangMap(final Merchant merchant, final String langData, final String lang);

	List<MerchantLangMap> updateMerchantLangMap(final Merchant merchant,final String defaultLangCode, final String langData, final String lang);

	List<MerchantLangMap> findByMerchantId(final Long merchantId);

	MerchantLangMap findMerchantDefaultNameById(Long merchantId);
	
	List<String> findMerchantLangCodes(final Long merchantId);

	String findMerchantDefaultLangCode(Long merchantId);
	
//	List<MerchantLangMap> findDetailByMerchantId(final Long merchantId);

}
