package com.cherrypicks.tcc.cms.campaign.service;

import com.cherrypicks.tcc.cms.campaign.dto.GiftDetailDTO;
import com.cherrypicks.tcc.cms.campaign.model.Gift;
import com.cherrypicks.tcc.cms.service.IBaseService;

public interface GiftService extends IBaseService<Gift>{

	Gift createGift(Long userId, Long merchantId, String langData, String lang);

	void updateGift(Long userId, Long giftId, String langData, String lang);

	void deleteByIds(String ids,String lang);

	GiftDetailDTO findGiftDetail(final Long userId, final Long giftId, final String lang);

}
