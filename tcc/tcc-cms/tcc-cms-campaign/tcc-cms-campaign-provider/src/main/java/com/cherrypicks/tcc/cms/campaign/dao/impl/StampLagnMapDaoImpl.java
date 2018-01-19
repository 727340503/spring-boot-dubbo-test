package com.cherrypicks.tcc.cms.campaign.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.campaign.dao.StampLangMapDao;
import com.cherrypicks.tcc.cms.campaign.dto.StampLangMapDetailDTO;
import com.cherrypicks.tcc.cms.campaign.model.StampLangMap;
import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;
import com.cherrypicks.tcc.util.DateUtil;

@Repository
public class StampLagnMapDaoImpl extends AbstractBaseDao<StampLangMap> implements StampLangMapDao {

	@Override
	public List<StampLangMap> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delByStampIds(List<Long> stampIdList) {
		final StringBuilder sql = new StringBuilder("DELETE FROM STAMP_LANG_MAP WHERE STAMP_ID in (:stampIdList)");
        final Map<String, List<Long>> paramMap = Collections.singletonMap("stampIdList", stampIdList);
        return super.updateForRecordWithNamedParam(sql.toString(), paramMap) > 0;
	}

	@Override
	public List<StampLangMapDetailDTO> findByStampId(Long stampId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT * FROM STAMP_LANG_MAP WHERE STAMP_ID = ? AND IS_DELETED = 0 ");
		param.setLong(stampId);
		return queryForList(sql.toString(), StampLangMapDetailDTO.class, param);
	}

	@Override
	public int updateImgByStampId(Long userId, String stampImg, Long stampId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("UPDATE STAMP_LANG_MAP SET STAMP_IMG = ?, UPDATED_BY = ?, UPDATED_TIME = ? WHERE STAMP_ID = ? AND IS_DELETED = 0 ");
		param.setString(stampImg);
		param.setString(String.valueOf(userId));
		param.setDate(DateUtil.getCurrentDate());
		param.setLong(stampId);
		
		return updateForRecord(sql.toString(), param);
	}

}
