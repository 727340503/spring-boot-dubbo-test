package com.cherrypicks.tcc.cms.campaign.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.campaign.dao.GiftDao;
import com.cherrypicks.tcc.cms.campaign.dto.GiftItemDTO;
import com.cherrypicks.tcc.cms.campaign.model.Gift;
import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;

@Repository
public class GiftDaoImpl extends AbstractBaseDao<Gift> implements GiftDao {

	@Override
	public List<GiftItemDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,int... args) {
		String alias = "G";
		
		final String langCode =  (String) criteriaMap.get("langCode");
		final String name = (String) criteriaMap.get("name");
		final Long merchantId = (Long) criteriaMap.get("merchantId");
		final Long campaignId = (Long) criteriaMap.get("campaignId");
		final Boolean isRelatedCampaign = (Boolean) criteriaMap.get("isRelatedCampaign");
		
		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(*) FROM GIFT G ")
				.append("LEFT JOIN GIFT_LANG_MAP GL ON GL.GIFT_ID = G.ID ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ml ON ml.MERCHANT_ID = G.MERCHANT_ID AND GL.LANG_CODE = ml.LANG_CODE ");
		
//		sql.append("SELECT G.*,GL.NAME,GL.IMAGE,GL.DESCR,GL.LANG_CODE,ml.NAME AS MERCHANT_NAME,ml.MERCHANT_ID ");
		sql.append("SELECT G.ID,G.CREATED_TIME,GL.NAME,ml.NAME AS MERCHANT_NAME ");
		if(null != campaignId){
			sql.append(", ISNULL(CGM.ID) AS isNotRelatedCampaign,CGM.ID AS relatedCampaignId ");
		}
		sql.append("FROM GIFT G ")
			.append("LEFT JOIN GIFT_LANG_MAP GL ON GL.GIFT_ID = G.ID ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ml ON ml.MERCHANT_ID = G.MERCHANT_ID AND GL.LANG_CODE = ml.LANG_CODE ");
		
		if(null != campaignId){
			sqlCount.append("LEFT JOIN CAMPAIGN_GIFT_MAP CGM ON CGM.GIFT_ID = G.ID AND CGM.CAMPAIGN_ID = ? AND CGM.IS_DELETED = 0 ");
			sql.append("LEFT JOIN CAMPAIGN_GIFT_MAP CGM ON CGM.GIFT_ID = G.ID  AND CGM.CAMPAIGN_ID = ? AND CGM.IS_DELETED = 0 ");
			
			param.setLong(campaignId);
		}
		
		sqlCount.append("WHERE G.IS_DELETED = 0 AND GL.IS_DELETED = 0 AND ml.IS_DELETED = 0 ");
		sql.append("WHERE G.IS_DELETED = 0 AND GL.IS_DELETED = 0 AND ml.IS_DELETED = 0 ");
		
		if(StringUtils.isNotBlank(langCode)){
			sqlCount.append("AND GL.LANG_CODE = ? ");
			sql.append("AND GL.LANG_CODE = ? ");
		
			param.setString(langCode);
		}else{
			sqlCount.append("AND ml.IS_DEFAULT = ? ");
			sql.append("AND ml.IS_DEFAULT = ? ");
		
			param.setBool(true);
		}
		
		if(StringUtils.isNotBlank(name)){
			sqlCount.append("AND GL.NAME REGEXP ? ");
			sql.append("AND GL.NAME REGEXP ? ");
		
			param.setString(name);
		}
		
		if(null != merchantId){
			sqlCount.append("AND G.MERCHANT_ID = ? ");
			sql.append("AND G.MERCHANT_ID = ? ");
		
			param.setLong(merchantId);
		}
		
		if(null != campaignId && null != isRelatedCampaign){
			if(isRelatedCampaign){
				sqlCount.append("AND CGM.ID IS NOT NULL");
				sql.append("AND CGM.ID IS NOT NULL");
			}else{
				sqlCount.append("AND CGM.ID IS NULL");
				sql.append("AND CGM.ID IS NULL");
			}
		}
		
		if(sortField.equalsIgnoreCase("name")){
			alias = "GL";
		}
		
		if(sortField.equalsIgnoreCase("merchantName")){
			alias = "ml";
			sortField = "name";
		}
		
		return getPagingList(GiftItemDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}


}
