package com.cherrypicks.tcc.cms.campaign.service;

import java.util.List;

import com.cherrypicks.tcc.cms.campaign.model.StampCard;
import com.cherrypicks.tcc.cms.campaign.model.StampCardLangMap;
import com.cherrypicks.tcc.cms.service.IBaseService;

public interface StampCardLangMapService extends IBaseService<StampCardLangMap>{

	void createStampLangMap(List<String> needAddStampCardLangs, String defaultMerchantLangCode, StampCard stampCard,String langData,
			String lang);

	void updateStampLangMap(StampCard stampCard, String langData, String lang);


}
