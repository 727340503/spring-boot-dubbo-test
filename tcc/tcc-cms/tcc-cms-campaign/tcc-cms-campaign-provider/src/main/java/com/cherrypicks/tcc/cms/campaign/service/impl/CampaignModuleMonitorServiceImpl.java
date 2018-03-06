package com.cherrypicks.tcc.cms.campaign.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.campaign.service.CampaignModuleMonitorService;

@Service(version="1.0",timeout=5000,retries=3)
public class CampaignModuleMonitorServiceImpl implements CampaignModuleMonitorService {

	@Override
	public int monitor() {
		return 1;
	}

}
