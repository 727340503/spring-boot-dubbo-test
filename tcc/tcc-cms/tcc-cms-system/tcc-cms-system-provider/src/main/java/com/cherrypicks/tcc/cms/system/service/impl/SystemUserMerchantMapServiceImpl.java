package com.cherrypicks.tcc.cms.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.system.dao.SystemUserMerchantMapDao;
import com.cherrypicks.tcc.cms.system.model.SystemUserMerchantMap;
import com.cherrypicks.tcc.cms.system.service.SystemUserMerchantMapService;

@Service(version="1.0",timeout=5000,retries=3)
public class SystemUserMerchantMapServiceImpl extends AbstractBaseService<SystemUserMerchantMap> implements SystemUserMerchantMapService {

	@Autowired
	private SystemUserMerchantMapDao systemUserMerchantMapDao;
	
	@Override
	public void setBaseDao(IBaseDao<SystemUserMerchantMap> systemUserMerchantMap) {
		// TODO Auto-generated method stub
		super.setBaseDao(systemUserMerchantMap);
	}

	@Override
	public SystemUserMerchantMap findByUserId(final Long userId) {
		return systemUserMerchantMapDao.findByUserId(userId);
	}
	
	
}
