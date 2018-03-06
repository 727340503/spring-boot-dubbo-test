package com.cherrypicks.tcc.cms.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.campaign.service.CampaignModuleMonitorService;
import com.cherrypicks.tcc.cms.merchant.service.MerchantModuleMonitorService;
import com.cherrypicks.tcc.cms.system.service.SystemModuleMonitorService;

@RestController
public class MonitorController{

	@Reference(version="1.0")
	private CampaignModuleMonitorService campaignModuleMonitorService;
	
	@Reference(version="1.0")
	private MerchantModuleMonitorService merchantModuleMonitorService;
	
	@Reference(version="1.0")
	private SystemModuleMonitorService SystemModuleMonitorService;
	
	@RequestMapping(value="/campaignModuleMonitor",method=RequestMethod.GET)
	public ResultVO campaignModuleMonitor() {
		ResultVO result = new ResultVO();
		result.setResult(campaignModuleMonitorService.monitor());
		return result;
	}

	@RequestMapping(value="/merchantModuleMonitor",method=RequestMethod.GET)
	public ResultVO merchantModuleMonitor() {
		ResultVO result = new ResultVO();
		result.setResult(merchantModuleMonitorService.monitor());
		return result;
	}
	
	@RequestMapping(value="/systemModuleMonitor",method=RequestMethod.GET)
	public ResultVO systemModuleMonitor() {
		ResultVO result = new ResultVO();
		result.setResult(SystemModuleMonitorService.monitor());
		return result;
	}
}