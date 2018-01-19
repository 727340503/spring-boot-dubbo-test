package com.cherrypicks.tcc.cms.campaign.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.campaign.dto.GiftLangMapDTO;
import com.cherrypicks.tcc.cms.campaign.model.GiftLangMap;
import com.cherrypicks.tcc.cms.dao.IBaseDao;

public interface GiftLangMapDao extends IBaseDao<GiftLangMap> {

	boolean delByGiftIds(List<Object> idList);

	List<GiftLangMapDTO> findByGiftId(Long giftId);


}
