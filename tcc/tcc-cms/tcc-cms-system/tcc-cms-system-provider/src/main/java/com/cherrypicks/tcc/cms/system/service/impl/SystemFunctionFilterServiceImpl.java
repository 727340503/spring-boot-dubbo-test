package com.cherrypicks.tcc.cms.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.system.dao.SystemFunctionFilterDao;
import com.cherrypicks.tcc.cms.system.model.SystemFunctionFilter;
import com.cherrypicks.tcc.cms.system.service.SystemFunctionFilterService;

@Service(version = "1.0", timeout = 5000, retries = 3)
public class SystemFunctionFilterServiceImpl extends AbstractBaseService<SystemFunctionFilter> implements SystemFunctionFilterService {

	@Autowired
	private SystemFunctionFilterDao systemFunctionFilterDao;
	
	@Override
	public void setBaseDao(IBaseDao<SystemFunctionFilter> systemFunctionFilterDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(systemFunctionFilterDao);
	}

	@Override
	public List<Long> findByFilterCode(final int systemFunctionFilterCode) {
		return systemFunctionFilterDao.findByFilterCode(systemFunctionFilterCode);
	}

}
