package com.cherrypicks.tcc.cms.campaign.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.campaign.dao.StampAdjustReasonLangMapDao;
import com.cherrypicks.tcc.cms.campaign.dto.StampAdjustReasonLangMapDTO;
import com.cherrypicks.tcc.cms.campaign.model.StampAdjustReasonLangMap;
import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;

@Repository
public class StampAdjustReasonLangMapDaoImpl extends AbstractBaseDao<StampAdjustReasonLangMap> implements StampAdjustReasonLangMapDao {

	@Override
	public List<StampAdjustReasonLangMap> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args) {
		return null;
	}

	@Override
	public boolean delByStampAdjustReasonIds(List<Object> stampAdjustReasonIds) {
		final StringBuilder sql = new StringBuilder("DELETE FROM STAMP_ADJUST_REASON_LANG_MAP WHERE STAMP_ADJUST_REASON_ID IN (:stampAdjustReasonIds)");
        final Map<String, List<Object>> paramMap = Collections.singletonMap("stampAdjustReasonIds", stampAdjustReasonIds);
        return super.updateForRecordWithNamedParam(sql.toString(), paramMap) > 0;
	}

	@Override
	public List<StampAdjustReasonLangMapDTO> findByStampAdjustReasonId(final Long stampAdjustReasonId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT ID,TITLE,CONTENT,LANG_CODE FROM STAMP_ADJUST_REASON_LANG_MAP ")
			.append("WHERE STAMP_ADJUST_REASON_ID = ? AND IS_DELETED = 0 ");
		
		param.setLong(stampAdjustReasonId);
		
		return queryForList(sql.toString(), StampAdjustReasonLangMapDTO.class, param);
	}

}
