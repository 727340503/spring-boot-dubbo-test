package com.cherrypicks.tcc.cms.merchant.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.merchant.dao.StoreDao;
import com.cherrypicks.tcc.cms.merchant.dto.StoreItemDTO;
import com.cherrypicks.tcc.cms.merchant.model.Store;

@Repository
public class StoreDaoImpl extends AbstractBaseDao<Store> implements StoreDao {

	@Override
	public List<StoreItemDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,int... args) {
		String alias = "s";
		
		final String name = (String) criteriaMap.get("name");
		final String merchantName = (String) criteriaMap.get("merchantName");
		final String langCode = (String) criteriaMap.get("langCode");
		final Long merchantId = (Long) criteriaMap.get("merchantId");
		final Integer status = (Integer) criteriaMap.get("status");

		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(*) FROM STORE s ")
				.append("LEFT JOIN STORE_LANG_MAP sl ON sl.STORE_ID = s.ID ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ml ON s.MERCHANT_ID = ml.MERCHANT_ID AND sl.LANG_CODE = ml.LANG_CODE ")
				.append("WHERE s.IS_DELETED = 0 AND ml.IS_DELETED = 0 AND sl.IS_DELETED = 0 ");
		
		sql.append("SELECT s.ID,sl.NAME,ml.NAME AS MERCHANT_NAME,s.STATUS,s.EXTERNAL_STORE_ID  FROM STORE s ")
			.append("LEFT JOIN STORE_LANG_MAP sl ON sl.STORE_ID = s.ID ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ml ON s.MERCHANT_ID = ml.MERCHANT_ID AND sl.LANG_CODE = ml.LANG_CODE ")
			.append("WHERE s.IS_DELETED = 0 AND ml.IS_DELETED = 0 AND sl.IS_DELETED = 0 ");
		
		if(StringUtils.isNotBlank(langCode)){
			sqlCount.append("AND ml.LANG_CODE = ? ");
			sql.append("AND ml.LANG_CODE = ? ");
			
			param.setString(langCode);
		}else{
			sqlCount.append("AND ml.IS_DEFAULT = ? ");
			sql.append("AND ml.IS_DEFAULT = ? ");
			
			param.setBool(true);
		}
		
		if(null != status){
			sqlCount.append("AND s.STATUS = ? ");
			sql.append("AND s.STATUS = ? ");
			
			param.setInt(status);
		}
		
		if(null != merchantId){
			sqlCount.append("AND s.MERCHANT_ID = ? ");
			sql.append("AND s.MERCHANT_ID = ? ");
			
			param.setLong(merchantId);
		}
		
		if(StringUtils.isNotBlank(name)){
			sqlCount.append("AND sl.NAME REGEXP ? ");
			sql.append("AND sl.NAME REGEXP ? ");
			
			param.setString(name);
		}
		
		if(StringUtils.isNotBlank(merchantName)){
			sqlCount.append("AND ml.NAME REGEXP ? ");
			sql.append("AND ml.NAME REGEXP ? ");
			
			param.setString(merchantName);
		}
		
		if(sortField.equalsIgnoreCase("name")){
			alias = "sl";
		}
		
		if(sortField.equalsIgnoreCase("merchantName")){
			alias = "ml";
			sortField = "name";
		}
		
		return getPagingList(StoreItemDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}

	@Override
	public long findCountByMerchantAreaIds(List<Object> merchantAreaIds) {
		final StringBuilder sql = new StringBuilder("SELECT COUNT(ID) FROM STORE WHERE MERCHANT_AREA_ID in (:merchantAreaIds)");
        final Map<String, List<Object>> paramMap = Collections.singletonMap("merchantAreaIds", merchantAreaIds);
        return super.queryForLongWithNamedParam(sql.toString(), paramMap);
	}


}
