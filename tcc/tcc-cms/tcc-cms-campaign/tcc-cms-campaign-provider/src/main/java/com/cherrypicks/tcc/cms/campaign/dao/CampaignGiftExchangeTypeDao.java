package com.cherrypicks.tcc.cms.campaign.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.campaign.model.CampaignGiftExchangeType;
import com.cherrypicks.tcc.cms.dao.IBaseDao;

public interface CampaignGiftExchangeTypeDao extends IBaseDao<CampaignGiftExchangeType> {

	boolean delByCampaignGiftMapIds(List<Object> campaignGiftMapIds);

	List<CampaignGiftExchangeType> findByCampGiftMapId(Long campaignGiftMapId);

}
