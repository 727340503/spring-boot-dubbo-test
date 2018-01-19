package com.cherrypicks.tcc.cms.campaign.dao;

import com.cherrypicks.tcc.cms.campaign.model.StampCard;
import com.cherrypicks.tcc.cms.dao.IBaseDao;

public interface StampCardDao extends IBaseDao<StampCard> {

	StampCard findByCampaignId(Long campaignId);

}
