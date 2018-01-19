package com.cherrypicks.tcc.cms.campaign.dao.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.campaign.dao.StampDao;
import com.cherrypicks.tcc.cms.campaign.dto.StampDetailDTO;
import com.cherrypicks.tcc.cms.campaign.model.Stamp;
import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;

@Repository
public class StampDaoImpl extends AbstractBaseDao<Stamp> implements StampDao {

	@Override
	public List<Stamp> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delByStampIds(List<Long> stampIdList) {
		final StringBuilder sql = new StringBuilder("DELETE FROM STAMP WHERE ID in (:stampIdList)");
		final Map<String, List<Long>> paramMap = Collections.singletonMap("stampIdList", stampIdList);
		return super.updateForRecordWithNamedParam(sql.toString(), paramMap) > 0;
	}

	@Override
	public List<StampDetailDTO> findDetailListByStampCardId(Long stampCardId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();

		sql.append("SELECT * FROM STAMP WHERE STAMP_CARD_ID = ? AND IS_DELETED = 0 ");
		param.setLong(stampCardId);
		return queryForList(sql.toString(), StampDetailDTO.class, param);
	}

	@Override
	public List<Stamp> findByStampCardId(final Long stampCardId) {
		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM  STAMP WHERE STAMP_CARD_ID = ? AND IS_DELETED = 0 ");
		final StatementParameter param = new StatementParameter();
		param.setLong(stampCardId);
		return super.queryForList(sb.toString(), Stamp.class, param);
	}

	@Override
	public List<Stamp> findByStampIds(List<Long> ids) {
		final StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM  STAMP WHERE ID = :ids AND IS_DELETED = 0 ");
		
		final Map<String,Object> param = new HashMap<String, Object>();
		param.put("ids",ids );
		
		return super.queryForListWithNamedParam(sb.toString(), Stamp.class, param);
	}

	@Override
	public int findCampaignStampTotalQty(final Long campaignId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT COUNT(1) FROM STAMP S ")
			.append("LEFT JOIN STAMP_CARD SC ON SC.ID = S.STAMP_CARD_ID AND SC.IS_DELETED = 0 ")
			.append("WHERE S.IS_DELETED = 0 AND SC.CAMPAIGN_ID  = ? ");
		
		param.setLong(campaignId);
		return queryForInt(sql.toString(),param);
	}

}
