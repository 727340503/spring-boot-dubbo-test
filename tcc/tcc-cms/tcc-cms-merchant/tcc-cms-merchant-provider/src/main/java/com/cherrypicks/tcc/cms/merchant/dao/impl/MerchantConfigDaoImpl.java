package com.cherrypicks.tcc.cms.merchant.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.merchant.dao.MerchantConfigDao;
import com.cherrypicks.tcc.cms.merchant.dto.MerchantConfigItemDTO;
import com.cherrypicks.tcc.cms.merchant.model.MerchantConfig;

@Repository
public class MerchantConfigDaoImpl extends AbstractBaseDao<MerchantConfig> implements MerchantConfigDao{

	@Override
	public List<MerchantConfigItemDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args) {
		String alias = "TEMP";
		
		final String langCode =  (String) criteriaMap.get("langCode");
		final Long merchantId =  (Long) criteriaMap.get("merchantId");
		
		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(*) FROM ( ")
				.append("SELECT MC.ID ")
				.append("FROM MERCHANT_CONFIG MC  ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = MC.MERCHANT_ID AND ML.IS_DELETED = 0 ")
				.append("WHERE MC.IS_DELETED = 0 ");

		
		sql.append("SELECT * FROM ( ")
			.append("SELECT ")
			.append("MC.ID,ML.NAME AS MERCHANT_NAME,MC.MERCHANT_ID,MC.CREATED_TIME,MC.UPDATED_TIME ")
			.append("FROM MERCHANT_CONFIG MC  ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = MC.MERCHANT_ID AND ML.IS_DELETED = 0 ")
			.append("WHERE MC.IS_DELETED = 0 ");
		
		if(StringUtils.isNotBlank(langCode)){
			sqlCount.append("AND ML.LANG_CODE = ? ");
			sql.append("AND ML.LANG_CODE = ? ");
		
			param.setString(langCode);
		}else{
			sqlCount.append("AND ML.IS_DEFAULT = ? ");
			sql.append("AND ML.IS_DEFAULT = ? ");
		
			param.setBool(true);
		}
		
		if(null != merchantId){
			sqlCount.append("AND MC.MERCHANT_ID = ? ");
			sql.append("AND MC.MERCHANT_ID = ? ");
		
			param.setLong(merchantId);
		}
		
		sql.append(" ) AS TEMP ");
		sqlCount.append(" ) AS TEMP ");
		
		return getPagingList(MerchantConfigItemDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}

	@Override
	public MerchantConfig findByMerchantId(final Long merchantId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT * FROM MERCHANT_CONFIG WHERE IS_DELETED = 0 AND MERCHANT_ID = ? ");
		param.setLong(merchantId);
		
		return query(sql.toString(), MerchantConfig.class, param);
	}


}
