package com.cherrypicks.tcc.cms.campaign.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.campaign.dao.StampCardLangMapDao;
import com.cherrypicks.tcc.cms.campaign.model.StampCardLangMap;
import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;

@Repository
public class StampCardLangMapDaoImpl extends AbstractBaseDao<StampCardLangMap> implements StampCardLangMapDao {

	@Override
	public List<StampCardLangMap> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StampCardLangMap findByStampCardIdAndLangCode(Long stampCardId, String langCode) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT * FROM STAMP_CARD_LANG_MAP WHERE STAMP_CARD_ID = ? AND LANG_CODE = ? ");
		param.setLong(stampCardId);
		param.setString(langCode);
		
		return query(sql.toString(), StampCardLangMap.class, param);
	}

}
