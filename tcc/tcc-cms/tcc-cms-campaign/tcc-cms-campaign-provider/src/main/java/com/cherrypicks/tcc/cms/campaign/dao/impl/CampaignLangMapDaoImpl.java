package com.cherrypicks.tcc.cms.campaign.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.campaign.dao.CampaignLangMapDao;
import com.cherrypicks.tcc.cms.campaign.dto.CampaignLangMapDetailDTO;
import com.cherrypicks.tcc.cms.campaign.model.CampaignLangMap;
import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;

@Repository
public class CampaignLangMapDaoImpl extends AbstractBaseDao<CampaignLangMap> implements CampaignLangMapDao {

	@Override
	public List<CampaignLangMap> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		return null;
	}

	@Override
	public List<CampaignLangMap> findByName(String name) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT ID,CAMPAIGN_ID,NAME,COVER_IMG,BG_IMG,DESCR,TERMS,LANG_CODE,CREATED_BY,CREATED_TIME,UPDATED_BY,UPDATED_TIME,IS_DELETED FROM CAMPAIGN_LANG_MAP WHERE NAME = ? AND IS_DELETED = 0 ");
		param.setString(name);
		
		return queryForList(sql.toString(), CampaignLangMap.class, param);
	}

	@Override
	public CampaignLangMap findbyCampaignId(Long campaignId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT * FROM CAMPAIGN_LANG_MAP WHERE CAMPAIGN_ID = ? AND IS_DELETED = 0 ");
		param.setLong(campaignId);
		
		return query(sql.toString(), CampaignLangMap.class, param);
	}

	@Override
	public List<CampaignLangMapDetailDTO> findDetailByCampaignId(Long campaignId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT ID,NAME,COVER_IMG,BG_IMG,BG_COLOR,DESCR,TERMS,LANG_CODE,PRM_BANNER_IMG,CREATED_BY,CREATED_TIME,UPDATED_BY,UPDATED_TIME,IS_DELETED FROM CAMPAIGN_LANG_MAP WHERE CAMPAIGN_ID = ? AND IS_DELETED = 0 ");
		param.setLong(campaignId);

		return queryForList(sql.toString(), CampaignLangMapDetailDTO.class, param);
	}

	@Override
	public CampaignLangMap findCampaignDefaultLangMap(final Long campaignId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT CL.* FROM CAMPAIGN_LANG_MAP CL ")
			.append("LEFT JOIN CAMPAIGN C ON C.ID = CL.CAMPAIGN_ID AND C.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = C.MERCHANT_ID AND ML.LANG_CODE = CL.LANG_CODE AND ML.IS_DELETED = 0 ")
			.append("WHERE CL.IS_DELETED = 0 AND ML.IS_DEFAULT = ? AND CL.CAMPAIGN_ID = ? ");
		
		param.setBool(true);
		param.setLong(campaignId);
		
		return query(sql.toString(), CampaignLangMap.class, param);
	}

}
