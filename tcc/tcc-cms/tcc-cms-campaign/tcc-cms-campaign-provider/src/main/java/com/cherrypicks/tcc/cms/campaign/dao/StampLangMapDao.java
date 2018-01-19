package com.cherrypicks.tcc.cms.campaign.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.campaign.dto.StampLangMapDetailDTO;
import com.cherrypicks.tcc.cms.campaign.model.StampLangMap;
import com.cherrypicks.tcc.cms.dao.IBaseDao;

public interface StampLangMapDao extends IBaseDao<StampLangMap> {

	boolean delByStampIds(List<Long> stampIdList);

	List<StampLangMapDetailDTO> findByStampId(Long stampId);

	int updateImgByStampId(Long userId, String stampImg, Long stampId);


}
