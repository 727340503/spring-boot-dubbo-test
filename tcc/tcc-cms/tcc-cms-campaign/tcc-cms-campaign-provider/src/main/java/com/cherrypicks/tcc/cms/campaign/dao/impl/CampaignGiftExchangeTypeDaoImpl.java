package com.cherrypicks.tcc.cms.campaign.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.campaign.dao.CampaignGiftExchangeTypeDao;
import com.cherrypicks.tcc.cms.campaign.model.CampaignGiftExchangeType;
import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;

@Repository
public class CampaignGiftExchangeTypeDaoImpl extends AbstractBaseDao<CampaignGiftExchangeType> implements CampaignGiftExchangeTypeDao {

	@Override
	public List<CampaignGiftExchangeType> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args) {
		return null;
	}

	@Override
	public boolean delByCampaignGiftMapIds(List<Object> campaignGiftMapIds) {
		final StringBuilder sql = new StringBuilder("DELETE FROM CAMPAIGN_GIFT_EXCHANGE_TYPE WHERE CAMPAIGN_GIFT_MAP_ID in (:campaignGiftMapIds)");
        final Map<String, List<Object>> paramMap = Collections.singletonMap("campaignGiftMapIds", campaignGiftMapIds);
        return super.updateForRecordWithNamedParam(sql.toString(), paramMap) > 0;
	}

	@Override
	public List<CampaignGiftExchangeType> findByCampGiftMapId(Long campGiftMapId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT * FROM CAMPAIGN_GIFT_EXCHANGE_TYPE WHERE CAMPAIGN_GIFT_MAP_ID = ? AND IS_DELETED = 0 ");
		param.setLong(campGiftMapId);
		
		return queryForList(sql.toString(), CampaignGiftExchangeType.class, param);
	}

}
