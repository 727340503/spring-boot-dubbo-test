package com.cherrypicks.tcc.cms.merchant.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.merchant.model.Store;

public interface StoreDao extends IBaseDao<Store> {

	long findCountByMerchantAreaIds(List<Object> merchantAreaIds);


}
