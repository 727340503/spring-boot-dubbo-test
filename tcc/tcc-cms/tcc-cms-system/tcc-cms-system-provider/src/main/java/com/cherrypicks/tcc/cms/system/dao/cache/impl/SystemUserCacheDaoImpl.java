package com.cherrypicks.tcc.cms.system.dao.cache.impl;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.cache.impl.AbstractBaseCacheDao;
import com.cherrypicks.tcc.cms.system.dao.cache.SystemUserCacheDao;
import com.cherrypicks.tcc.cms.system.model.SystemUser;

@Repository
public class SystemUserCacheDaoImpl extends AbstractBaseCacheDao<SystemUser> implements SystemUserCacheDao {
}
