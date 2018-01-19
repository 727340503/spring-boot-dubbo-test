package com.cherrypicks.tcc.cms.merchant.dao.cache.impl;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.cache.impl.AbstractBaseCacheDao;
import com.cherrypicks.tcc.cms.merchant.dao.cache.MerchantCacheDao;
import com.cherrypicks.tcc.cms.merchant.model.Merchant;

@Repository
public class MerchantCacheDaoImpl extends AbstractBaseCacheDao<Merchant> implements MerchantCacheDao {
}
