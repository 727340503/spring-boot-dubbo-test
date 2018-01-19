package com.cherrypicks.tcc.cms.merchant.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.merchant.model.MerchantAreaLangMap;

public interface MerchantAreaLangMapDao extends IBaseDao<MerchantAreaLangMap> {

	List<MerchantAreaLangMap> findByMerchantAreaId(Long id);

	boolean delByMerchantAreaIds(List<Object> merchantAreaIds);


}
