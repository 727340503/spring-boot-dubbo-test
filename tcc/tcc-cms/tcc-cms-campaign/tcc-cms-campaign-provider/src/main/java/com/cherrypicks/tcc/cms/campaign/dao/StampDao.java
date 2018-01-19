package com.cherrypicks.tcc.cms.campaign.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.campaign.dto.StampDetailDTO;
import com.cherrypicks.tcc.cms.campaign.model.Stamp;
import com.cherrypicks.tcc.cms.dao.IBaseDao;

public interface StampDao extends IBaseDao<Stamp> {

	boolean delByStampIds(List<Long> stampIdList);

	List<StampDetailDTO> findDetailListByStampCardId(Long stampCardId);

	List<Stamp> findByStampCardId(final Long stampCardId);

	List<Stamp> findByStampIds(final List<Long> ids);

	int findCampaignStampTotalQty(final Long campaignId);


}
