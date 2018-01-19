package com.cherrypicks.tcc.cms.campaign.service;

import java.util.List;

import com.cherrypicks.tcc.cms.campaign.dto.CampaignGiftMapDetailDTO;
import com.cherrypicks.tcc.cms.campaign.model.Campaign;
import com.cherrypicks.tcc.cms.campaign.model.CampaignGiftMap;
import com.cherrypicks.tcc.cms.service.IBaseService;

public interface CampaignGiftMapService extends IBaseService<CampaignGiftMap>{

//	List<GiftLangMap> findGiftByCampaignIdAndLangCode(Long campaignId, String langCode);

	long getCountByGiftIds(List<Object> idList);

	void addCampaignGifts(final Long userId,final Long campaignId, final Long giftId, final String externalGiftId,final Integer isReservation,final String giftExchangeTypeData, String lang);

	void updateCampaignGifts(final Long userId,final Long campaignGiftMapId, final String externalGiftId,final Integer status,final Integer isReservation, final String giftExchangeTypeData, String lang);

	void delCampaignGiftMapByIds(String campaignGiftMapIds, String lang);

	void delCampaignGiftExchangeTypeByIds(String giftEXTypeIds, String lang);

	CampaignGiftMapDetailDTO findByCampaignIdAndGiftID(Long userId,Long campaignId, Long giftId, String lang);

	boolean updateCampGiftMapStatus(Campaign campaign, Integer status);

	List<String> findGiftRelatedCampaignNames(final Long giftId, final String langCode);

}
