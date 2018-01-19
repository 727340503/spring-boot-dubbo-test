package com.cherrypicks.tcc.cms.campaign.service;

import java.util.List;

import com.cherrypicks.tcc.cms.campaign.dto.StampDetailDTO;
import com.cherrypicks.tcc.cms.campaign.model.Campaign;
import com.cherrypicks.tcc.cms.campaign.model.Stamp;
import com.cherrypicks.tcc.cms.service.IBaseService;
/***
 * 暂时取消
 * @author rahul
 *
 */
public interface StampService extends IBaseService<Stamp>{

	void updateStamp(final Long userId, final Long campaignId, final String langData, final String lang);

	void delByIds(final Long userId,final String stampIds,final Long campaignId,final String lang);

	List<StampDetailDTO> findByCampaignId(final Long userId,final Long campaignId, final String lang);

	void addCampaignDefaultStamp(final Long userId,final Campaign campaign,final String lang);

}
