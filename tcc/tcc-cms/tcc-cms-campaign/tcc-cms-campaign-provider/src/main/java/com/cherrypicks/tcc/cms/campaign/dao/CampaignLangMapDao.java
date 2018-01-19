package com.cherrypicks.tcc.cms.campaign.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.campaign.dto.CampaignLangMapDetailDTO;
import com.cherrypicks.tcc.cms.campaign.model.CampaignLangMap;
import com.cherrypicks.tcc.cms.dao.IBaseDao;

public interface CampaignLangMapDao extends IBaseDao<CampaignLangMap> {

	List<CampaignLangMap> findByName(final String name);

	CampaignLangMap findbyCampaignId(final Long campaignId);

	List<CampaignLangMapDetailDTO> findDetailByCampaignId(Long campaignId);

	CampaignLangMap findCampaignDefaultLangMap(Long campaignId);

}
