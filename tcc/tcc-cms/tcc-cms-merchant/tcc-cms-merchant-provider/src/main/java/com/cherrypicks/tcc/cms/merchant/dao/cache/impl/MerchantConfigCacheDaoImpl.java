package com.cherrypicks.tcc.cms.merchant.dao.cache.impl;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.cache.impl.AbstractBaseCacheDao;
import com.cherrypicks.tcc.cms.merchant.dao.cache.MerchantConfigCacheDao;
import com.cherrypicks.tcc.cms.merchant.model.MerchantConfig;

@Repository
public class MerchantConfigCacheDaoImpl extends AbstractBaseCacheDao<MerchantConfig> implements MerchantConfigCacheDao {
}
