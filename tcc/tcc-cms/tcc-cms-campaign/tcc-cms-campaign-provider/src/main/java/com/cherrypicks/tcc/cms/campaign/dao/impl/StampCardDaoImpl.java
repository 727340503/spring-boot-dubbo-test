package com.cherrypicks.tcc.cms.campaign.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.campaign.dao.StampCardDao;
import com.cherrypicks.tcc.cms.campaign.model.StampCard;
import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;

@Repository
public class StampCardDaoImpl extends AbstractBaseDao<StampCard> implements StampCardDao {

	@Override
	public List<StampCard> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StampCard findByCampaignId(Long campaignId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT * FROM STAMP_CARD WHERE CAMPAIGN_ID = ? AND IS_DELETED = 0 ");
		param.setLong(campaignId);
		
		return query(sql.toString(), StampCard.class, param);
	}

}
