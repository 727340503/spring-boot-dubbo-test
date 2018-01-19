package com.cherrypicks.tcc.cms.campaign.dao.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.campaign.dao.GiftLangMapDao;
import com.cherrypicks.tcc.cms.campaign.dto.GiftLangMapDTO;
import com.cherrypicks.tcc.cms.campaign.model.GiftLangMap;
import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;

@Repository
public class GiftLangMapDaoImpl extends AbstractBaseDao<GiftLangMap> implements GiftLangMapDao {

	@Override
	public List<GiftLangMap> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		return null;
	}

	@Override
	public boolean delByGiftIds(List<Object> giftIds) {
		final StringBuilder sql = new StringBuilder("DELETE FROM GIFT_LANG_MAP WHERE ID IN (:giftIds) ");
		
		final Map<String, Collection<Object>> paramMap = Collections.singletonMap("giftIds", giftIds);
		return super.updateForRecordWithNamedParam(sql.toString(), paramMap) > 0;
	}

	@Override
	public List<GiftLangMapDTO> findByGiftId(Long giftId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT GL.ID,GL.NAME,GL.IMAGE,GL.DESCR,GL.LANG_CODE FROM GIFT_LANG_MAP GL ")
			.append("LEFT JOIN GIFT G ON G.ID = GL.GIFT_ID ")
	//		.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.LANG_CODE = SL.LANG_CODE AND S.MERCHANT_ID = ML.MERCHANT_ID AND ML.IS_DELETED = 0 ")
			.append("WHERE G.IS_DELETED = 0 AND GL.GIFT_ID = ? ");
		
		param.setLong(giftId);
		
		return queryForList(sql.toString(), GiftLangMapDTO.class, param);
	}

}
