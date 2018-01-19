package com.cherrypicks.tcc.cms.campaign.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.campaign.dao.StampCardDao;
import com.cherrypicks.tcc.cms.campaign.model.Campaign;
import com.cherrypicks.tcc.cms.campaign.model.StampCard;
import com.cherrypicks.tcc.cms.campaign.service.StampCardLangMapService;
import com.cherrypicks.tcc.cms.campaign.service.StampCardService;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;

@Service(version="1.0",timeout=5000,retries=3)
public class StampCardServiceImpl extends AbstractBaseService<StampCard> implements StampCardService {
	
	@Autowired
	private StampCardDao stampCardDao;
	
	@Autowired
	private StampCardLangMapService stampCardLangMapService;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<StampCard> stampCardDao) {
		super.setBaseDao(stampCardDao);
	}

	@Override
	public StampCard addStampCard(List<String> needAddStampCardLangs, String defaultMerchantLangCode, Campaign campaign,String langData, String lang) {
		StampCard stampCard = new StampCard();
		stampCard.setCampaignId(campaign.getId());
		stampCard.setCreatedBy(campaign.getCreatedBy());
		stampCard.setCreatedTime(campaign.getCreatedTime());
		stampCard.setIsDeleted(false);
		stampCard.setStatus(campaign.getStatus());
		stampCard.setType(StampCard.Type.MERCHANT.getCode());
		
		//add stampcard
		stampCard = stampCardDao.add(stampCard);
		
		List<String> needAddStampLangs = new ArrayList<String>();
		needAddStampLangs.addAll(needAddStampCardLangs);
		
		//add stamp card lang map 
		stampCardLangMapService.createStampLangMap(needAddStampCardLangs,defaultMerchantLangCode,stampCard, langData, lang);
		
		return stampCard;
	}

	@Override
	public StampCard findbyCampaignId(Long campaignId) {
		return stampCardDao.findByCampaignId(campaignId);
	}

	@Override
	public void updateStampCard(Campaign campaign, String langData, String lang) {
		StampCard stampCard = stampCardDao.findByCampaignId(campaign.getId());
		
		stampCard.setStatus(campaign.getStatus());
		stampCard.setUpdatedBy(campaign.getUpdatedBy());
		stampCardDao.updateForVersion(stampCard);
		
		
		stampCardLangMapService.updateStampLangMap(stampCard, langData, lang);
		
	}
	
}
