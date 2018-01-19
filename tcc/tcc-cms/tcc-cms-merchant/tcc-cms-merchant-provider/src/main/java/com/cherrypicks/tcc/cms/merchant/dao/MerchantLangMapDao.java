package com.cherrypicks.tcc.cms.merchant.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.merchant.model.MerchantLangMap;

public interface MerchantLangMapDao extends IBaseDao<MerchantLangMap> {

	 List<MerchantLangMap> findByName(final String name);

	 List<MerchantLangMap> findByMerchantId(final Long merchantId);

	 MerchantLangMap findDefaultMerchantLangMapByMerchantId(Long merchantId);

	MerchantLangMap findByMerchantIdAndLangCode(Long merchantId, String langCode);

	List<String> findMerchantLangCodes(Long merchantId);

	String findMerchantDefaultLangCode(Long merchantId);

}
