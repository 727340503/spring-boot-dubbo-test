package com.cherrypicks.tcc.cms.campaign.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.campaign.dto.CampaignItemDTO;
import com.cherrypicks.tcc.cms.campaign.dto.HomePageItemDTO;
import com.cherrypicks.tcc.cms.campaign.model.Campaign;
import com.cherrypicks.tcc.cms.dao.IBaseDao;

public interface CampaignDao extends IBaseDao<Campaign> {

	List<HomePageItemDTO> findHomePageCampaignList(final Long merchantId, final Integer status);

	List<CampaignItemDTO> findByCustomerId(final Long customerId);


}
