package com.cherrypicks.tcc.cms.merchant.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.merchant.service.MerchantModuleMonitorService;

@Service(version="1.0",timeout=5000,retries=3)
public class MerchantModuleMonitorServiceImpl implements MerchantModuleMonitorService {

	@Override
	public int monitor() {
		return 1;
	}

}
