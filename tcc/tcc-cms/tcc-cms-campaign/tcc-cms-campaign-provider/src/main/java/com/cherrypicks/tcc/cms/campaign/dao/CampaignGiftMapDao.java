package com.cherrypicks.tcc.cms.campaign.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.campaign.dto.CampaignGiftMapDetailDTO;
import com.cherrypicks.tcc.cms.campaign.model.CampaignGiftMap;
import com.cherrypicks.tcc.cms.dao.IBaseDao;

public interface CampaignGiftMapDao extends IBaseDao<CampaignGiftMap> {

//	List<GiftLangMap> findGiftByCampaignIdAndLangCode(Long campaignId, String langCode);

	long getCountByGiftIds(List<Object> giftIds);

	CampaignGiftMapDetailDTO findByCampaignIdAndGiftId(Long campaignId, Long giftId);

	boolean updateStatusByCampId(Long campId, Integer status);

	List<String> findGiftRelatedCampaignNames(final Long giftId, final String langCode);

}
