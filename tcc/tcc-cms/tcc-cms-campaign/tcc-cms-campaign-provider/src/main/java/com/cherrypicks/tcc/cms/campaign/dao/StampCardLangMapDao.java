package com.cherrypicks.tcc.cms.campaign.dao;

import com.cherrypicks.tcc.cms.campaign.model.StampCardLangMap;
import com.cherrypicks.tcc.cms.dao.IBaseDao;

public interface StampCardLangMapDao extends IBaseDao<StampCardLangMap> {

	StampCardLangMap findByStampCardIdAndLangCode(Long stampCardId, String langCode);


}
