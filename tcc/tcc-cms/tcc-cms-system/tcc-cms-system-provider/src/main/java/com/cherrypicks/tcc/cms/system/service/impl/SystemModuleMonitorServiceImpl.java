package com.cherrypicks.tcc.cms.system.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.system.service.SystemModuleMonitorService;

@Service(version="1.0",timeout=5000,retries=3)
public class SystemModuleMonitorServiceImpl implements SystemModuleMonitorService {

	@Override
	public int monitor() {
		return 1;
	}

}
