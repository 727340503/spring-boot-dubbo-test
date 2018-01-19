package com.cherrypicks.tcc.cms.campaign.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.campaign.dto.StampAdjustReasonLangMapDTO;
import com.cherrypicks.tcc.cms.campaign.model.StampAdjustReasonLangMap;
import com.cherrypicks.tcc.cms.dao.IBaseDao;

public interface StampAdjustReasonLangMapDao extends IBaseDao<StampAdjustReasonLangMap> {

	boolean delByStampAdjustReasonIds(final List<Object> stampAdjustReasonIds);

	List<StampAdjustReasonLangMapDTO> findByStampAdjustReasonId(final Long stampAdjustReasonId);


}
