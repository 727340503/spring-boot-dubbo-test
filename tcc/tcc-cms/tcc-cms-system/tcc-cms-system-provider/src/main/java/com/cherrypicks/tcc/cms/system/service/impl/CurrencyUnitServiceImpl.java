package com.cherrypicks.tcc.cms.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.system.model.CurrencyUnit;
import com.cherrypicks.tcc.cms.system.service.CurrencyUnitService;

@Service(version="1.0",timeout=5000,retries=3)
public class CurrencyUnitServiceImpl extends AbstractBaseService<CurrencyUnit> implements CurrencyUnitService {
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<CurrencyUnit> currencyUnitDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(currencyUnitDao);
	}

}
