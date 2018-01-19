package com.cherrypicks.tcc.cms.merchant.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.merchant.model.MerchantFunctionFilterMap;

public interface MerchantFunctionFilterMapDao extends IBaseDao<MerchantFunctionFilterMap> {

	void delByMerchantIdAndSystemFuncs(final Long merchantId, final List<Long> systemFunctionIds);

	int findByMerchantIdAndFilterCode(final Long merchantId, final int couponManagementFilterCode);


}
