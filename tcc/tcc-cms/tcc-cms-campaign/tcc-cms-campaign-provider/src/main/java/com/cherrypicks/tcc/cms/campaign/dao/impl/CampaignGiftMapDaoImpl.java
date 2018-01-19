package com.cherrypicks.tcc.cms.campaign.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.campaign.dao.CampaignGiftMapDao;
import com.cherrypicks.tcc.cms.campaign.dto.CampaignGiftItemDTO;
import com.cherrypicks.tcc.cms.campaign.dto.CampaignGiftMapDetailDTO;
import com.cherrypicks.tcc.cms.campaign.model.CampaignGiftMap;
import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;

@Repository
public class CampaignGiftMapDaoImpl extends AbstractBaseDao<CampaignGiftMap> implements CampaignGiftMapDao {

	@Override
	public List<CampaignGiftItemDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,int... args) {
		String alias = "GL";
		
		final String langCode =  (String) criteriaMap.get("langCode");
		final Long campaignId = (Long) criteriaMap.get("campaignId");
		
		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(*) FROM GIFT_LANG_MAP GL ")
				.append("LEFT JOIN CAMPAIGN_GIFT_MAP CA ON CA.GIFT_ID = GL.GIFT_ID AND CA.IS_DELETED = 0 ")
				.append("LEFT JOIN CAMPAIGN C ON C.ID = CA.CAMPAIGN_ID AND C.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = C.MERCHANT_ID AND ML.IS_DELETED = 0 ")
				.append("WHERE CA.CAMPAIGN_ID = ? ");
		
		sql.append("SELECT CA.ID,CA.GIFT_ID,CA.CREATED_TIME,GL.NAME,CA.STATUS,C.START_TIME,C.END_TIME,CA.IS_RESERVATION FROM GIFT_LANG_MAP GL ")
			.append("LEFT JOIN CAMPAIGN_GIFT_MAP CA ON CA.GIFT_ID = GL.GIFT_ID AND CA.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN C ON C.ID = CA.CAMPAIGN_ID AND C.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = C.MERCHANT_ID AND ML.IS_DELETED = 0 AND GL.LANG_CODE = ML.LANG_CODE  ")
			.append("WHERE CA.CAMPAIGN_ID = ? AND GL.IS_DELETED = 0 ");
			
		param.setLong(campaignId);
		
		if(StringUtils.isNotBlank(langCode)){
			sqlCount.append("AND GL.LANG_CODE = ? ");
			sql.append("AND GL.LANG_CODE = ? ");
		
			param.setString(langCode);
		}else{
			sqlCount.append("AND ML.IS_DEFAULT = ? ");
			sql.append("AND ML.IS_DEFAULT = ? ");
		
			param.setBool(true);
		}
		
		return getPagingList(CampaignGiftItemDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}

	@Override
	public long getCountByGiftIds(List<Object> giftIds) {
		String sql = "SELECT COUNT(ID) FROM CAMPAIGN_GIFT_MAP WHERE GIFT_ID IN (:giftIds) AND IS_DELETED = 0";
		Map<String, List<Object>> paramMap = Collections.singletonMap("giftIds", giftIds);
		return super.queryForLongWithNamedParam(sql, paramMap);
	}

	@Override
	public CampaignGiftMapDetailDTO findByCampaignIdAndGiftId(Long campaignId, Long giftId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();

		sql.append("SELECT * FROM CAMPAIGN_GIFT_MAP WHERE CAMPAIGN_ID = ? AND GIFT_ID = ? AND IS_DELETED = 0 ");
		param.setLong(campaignId);
		param.setLong(giftId);

		return query(sql.toString(), CampaignGiftMapDetailDTO.class, param);
	}

	@Override
	public boolean updateStatusByCampId(Long campId, Integer status) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("UPDATE CAMPAIGN_GIFT_MAP SET STATUS = ? WHERE CAMPAIGN_ID = ? AND IS_DELETED = 0 ");
		param.setInt(status);
		param.setLong(campId);
		
		return updateForRecord(sql.toString(), param) > 0 ;
	}

	@Override
	public List<String> findGiftRelatedCampaignNames(final Long giftId, final String langCode) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT CL.NAME FROM CAMPAIGN_GIFT_MAP CG ")
			.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = CG.CAMPAIGN_ID AND CL.IS_DELETED = 0 ")
			.append("WHERE CG.IS_DELETED = 0 AND CG.GIFT_ID = ? AND CL.LANG_CODE = ? ");
		
		param.setLong(giftId);
		param.setString(langCode);
		
		return queryForStrings(sql.toString(), param);
	}

}
