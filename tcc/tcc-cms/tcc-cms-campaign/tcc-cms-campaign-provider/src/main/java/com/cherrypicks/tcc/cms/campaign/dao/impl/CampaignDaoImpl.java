package com.cherrypicks.tcc.cms.campaign.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.campaign.dao.CampaignDao;
import com.cherrypicks.tcc.cms.campaign.dto.CampaignItemDTO;
import com.cherrypicks.tcc.cms.campaign.dto.HomePageItemDTO;
import com.cherrypicks.tcc.cms.campaign.model.Campaign;
import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;
import com.cherrypicks.tcc.util.DateUtil;

@Repository
public class CampaignDaoImpl extends AbstractBaseDao<Campaign> implements CampaignDao {

	@Override
	public List<CampaignItemDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args) {
		String alias = "c";
		
		final String langCode =  (String) criteriaMap.get("langCode");
		final Integer status = (Integer) criteriaMap.get("status");
		final String name = (String) criteriaMap.get("name");
		final Long merchantId = (Long) criteriaMap.get("merchantId");
		
		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(*) FROM CAMPAIGN c ")
				.append("LEFT JOIN CAMPAIGN_LANG_MAP cl ON cl.CAMPAIGN_ID = c.ID ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ml ON ml.MERCHANT_ID = c.MERCHANT_ID AND cl.LANG_CODE = ml.LANG_CODE ")
				.append("WHERE c.IS_DELETED = 0 AND cl.IS_DELETED = 0 AND ml.IS_DELETED = 0 ");
		
//		sql.append("SELECT c.*,cl.NAME,cl.COVER_IMG,cl.BG_IMG,cl.PRM_BANNER_IMG,cl.DESCR,cl.TERMS,cl.LANG_CODE,ml.NAME AS MERCHANT_NAME FROM CAMPAIGN c ")
		sql.append("SELECT c.ID,c.STATUS,c.START_TIME,c.END_TIME,c.MERCHANT_ID,cl.NAME FROM CAMPAIGN c ")
			.append("LEFT JOIN CAMPAIGN_LANG_MAP cl ON cl.CAMPAIGN_ID = c.ID ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ml ON ml.MERCHANT_ID = c.MERCHANT_ID AND cl.LANG_CODE = ml.LANG_CODE ")
			.append("WHERE c.IS_DELETED = 0 AND cl.IS_DELETED = 0 AND ml.IS_DELETED = 0 ");
		
		if(StringUtils.isNotBlank(langCode)){
			sqlCount.append("AND cl.LANG_CODE = ? ");
			sql.append("AND cl.LANG_CODE = ? ");
		
			param.setString(langCode);
		}else{
			sqlCount.append("AND ml.IS_DEFAULT = ? ");
			sql.append("AND ml.IS_DEFAULT = ? ");
		
			param.setBool(true);
		}
		
		if(StringUtils.isNotBlank(name)){
			sqlCount.append("AND cl.NAME REGEXP ? ");
			sql.append("AND cl.NAME REGEXP ? ");
		
			param.setString(name);
		}
		
		if(null != status){
			if(status.intValue() == Campaign.CampaignStatus.PENDING.toValue()){
				sqlCount.append("AND c.START_TIME > ? AND c.STATUS = ? ");
				sql.append("AND c.START_TIME > ? AND c.STATUS = ? ");
				
				param.setDate(DateUtil.getCurrentDate());
				param.setInt(Campaign.CampaignStatus.ACTIVE.toValue());
			}else if(status.intValue() == Campaign.CampaignStatus.EXPIRED.toValue()){
				sqlCount.append("AND c.START_TIME < ? AND END_TIME < ? AND c.STATUS = ? ");
				sql.append("AND c.START_TIME < ? AND END_TIME < ? AND c.STATUS = ? ");
				
				param.setDate(DateUtil.getCurrentDate());
				param.setDate(DateUtil.getCurrentDate());
				param.setInt(Campaign.CampaignStatus.ACTIVE.toValue());
			}else if(status.intValue() == Campaign.CampaignStatus.ACTIVE.toValue()){
				sqlCount.append("AND c.STATUS = ? AND c.START_TIME < ? AND c.END_TIME > ? ");
				sql.append("AND c.STATUS = ? AND c.START_TIME < ? AND c.END_TIME > ? ");
			
				param.setInt(status);
				param.setDate(DateUtil.getCurrentDate());
				param.setDate(DateUtil.getCurrentDate());
			}else{
				sqlCount.append("AND c.STATUS = ? ");
				sql.append("AND c.STATUS = ? ");
			
				param.setInt(Campaign.CampaignStatus.IN_ACTIVE.toValue());
			}
		}
		
		if(null != merchantId){
			sqlCount.append("AND c.MERCHANT_ID = ? ");
			sql.append("AND c.MERCHANT_ID = ? ");
		
			param.setLong(merchantId);
		}
		
		if(sortField.equalsIgnoreCase("name")){
			alias = "cl";
		}
		
//		if(sortField.equalsIgnoreCase("merchantName")){
//			alias = "ml";
//			sortField = "name";
//		}
		
		return getPagingList(CampaignItemDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}

	@Override
	public List<HomePageItemDTO> findHomePageCampaignList(final Long merchantId, final Integer status) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT c.ID,cl.NAME, cl.COVER_IMG AS IMG,c.PRM_BANNER_URL AS WEB_URL,c.START_TIME,c.END_TIME,c.STATUS FROM CAMPAIGN_LANG_MAP cl ")
			.append("LEFT JOIN CAMPAIGN c ON cl.CAMPAIGN_ID = c.ID AND c.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ml ON ml.MERCHANT_ID = c.MERCHANT_ID AND cl.LANG_CODE = ml.LANG_CODE AND ml.IS_DELETED = 0  ")
			.append("WHERE c.IS_DELETED = 0 AND ml.IS_DEFAULT = ? AND c.MERCHANT_ID = ? ");
		
		param.setBool(true);
		param.setLong(merchantId);
		
		if(null != status){
			if(status.intValue() == Campaign.CampaignStatus.ACTIVE.toValue() || status.intValue() == Campaign.CampaignStatus.IN_ACTIVE.toValue()){
				sql.append("AND c.STATUS = ? ");
				param.setInt(status);
			}else if(status.intValue() == Campaign.CampaignStatus.PENDING.toValue()){
				sql.append("AND c.STATUS = ? AND c.START_TIME > ? ");
				
				param.setInt(Campaign.CampaignStatus.ACTIVE.toValue());
				param.setDate(DateUtil.getCurrentDate());
			}else{//expired
				sql.append("AND c.STATUS = ? AND c.END_TIME < ? ");
				
				param.setInt(Campaign.CampaignStatus.ACTIVE.toValue());
				param.setDate(DateUtil.getCurrentDate());
			}
		}else{
			sql.append("AND c.STATUS = ? ");
			
			param.setInt(Campaign.CampaignStatus.ACTIVE.toValue());
		}
		
		
		return queryForList(sql.toString(), HomePageItemDTO.class,param);
	}

	@Override
	public List<CampaignItemDTO> findByCustomerId(Long customerId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT C.ID,CL.NAME FROM USER U ")
			.append("LEFT JOIN USER_STAMP_CARD US ON US.USER_ID = U.ID AND US.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN C ON C.ID = US.CAMPAIGN_ID AND C.IS_DELETED = 0 ")
			.append("LEFT JOIN CAMPAIGN_LANG_MAP CL ON CL.CAMPAIGN_ID = C.ID AND CL.LANG_CODE = U.LANG_CODE AND CL.IS_DELETED = 0 ")
			.append("WHERE U.ID = ? AND U.IS_DELETED = 0 ");
		param.setLong(customerId);
		
		return queryForList(sql.toString(), CampaignItemDTO.class, param);
	}

}
