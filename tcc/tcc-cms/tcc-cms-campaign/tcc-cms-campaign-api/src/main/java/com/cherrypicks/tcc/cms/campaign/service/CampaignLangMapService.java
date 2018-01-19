package com.cherrypicks.tcc.cms.campaign.service;

import java.util.List;

import com.cherrypicks.tcc.cms.campaign.dto.CampaignLangMapDetailDTO;
import com.cherrypicks.tcc.cms.campaign.model.Campaign;
import com.cherrypicks.tcc.cms.campaign.model.CampaignLangMap;
import com.cherrypicks.tcc.cms.service.IBaseService;

public interface CampaignLangMapService extends IBaseService<CampaignLangMap>{

	void createCampLangMap(final List<String> neddAddCampaignLangs,final String defaultCampaignLangCode, final Campaign campaign, final String langData, final String lang);

	void updateCampLangMap(Campaign campaign, String langData, String lang);

	List<CampaignLangMapDetailDTO> findCampLangMapDetailByCampaignId(final Long merchantId,final Long campaignId);

	CampaignLangMap findCampaignDefaultLangMap(final Long campaignId);

}
