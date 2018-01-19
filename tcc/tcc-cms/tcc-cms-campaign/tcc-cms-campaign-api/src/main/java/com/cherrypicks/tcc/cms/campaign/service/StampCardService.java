package com.cherrypicks.tcc.cms.campaign.service;

import java.util.List;

import com.cherrypicks.tcc.cms.campaign.model.Campaign;
import com.cherrypicks.tcc.cms.campaign.model.StampCard;
import com.cherrypicks.tcc.cms.service.IBaseService;

public interface StampCardService extends IBaseService<StampCard>{

	StampCard addStampCard(final List<String> needAddStampCardLangs, final String defaultMerchantLangCode, final Campaign campaign,final String langData, final String lang);

	StampCard findbyCampaignId(Long campaignId);

	void updateStampCard(Campaign campaign, String langData, String lang);


}
