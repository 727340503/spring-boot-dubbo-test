package com.cherrypicks.tcc.cms.merchant.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.merchant.dao.MerchantDao;
import com.cherrypicks.tcc.cms.merchant.dto.MerchantItemDTO;
import com.cherrypicks.tcc.cms.merchant.model.Merchant;

@Repository
public class MerchantDaoImpl extends AbstractBaseDao<Merchant> implements MerchantDao {

	@Override
	public List<MerchantItemDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args) {
		String alias = "m";
		
		final String name = (String) criteriaMap.get("name");
		final String merchantCode = (String) criteriaMap.get("merchantCode");
		final Integer status = (Integer) criteriaMap.get("status");
		final String langCode = (String) criteriaMap.get("langCode");
		final Long merchantId = (Long) criteriaMap.get("merchantId");

		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(*) FROM MERCHANT m ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ml ON ml.MERCHANT_ID = m.id ")
				.append("WHERE m.IS_DELETED = 0 AND ml.IS_DELETED = 0 ");
		
		sql.append("SELECT m.ID,m.STATUS,m.CREATED_TIME,ml.NAME FROM MERCHANT m ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ml ON ml.MERCHANT_ID = m.id ")
			.append("WHERE m.IS_DELETED = 0 AND ml.IS_DELETED = 0 ");
		
		if(StringUtils.isNotBlank(langCode)){
			sqlCount.append("AND ml.LANG_CODE = ? ");
			sql.append("AND ml.LANG_CODE = ? ");
			
			param.setString(langCode);
		}else{
			sqlCount.append("AND ml.IS_DEFAULT = ? ");
			sql.append("AND ml.IS_DEFAULT = ? ");
			
			param.setBool(true);
		}
		
		if(StringUtils.isNotBlank(name)){
			sqlCount.append("AND ml.NAME REGEXP ? ");
			sql.append("AND ml.NAME REGEXP ? ");
			
			param.setString(name);
		}
		
		if(StringUtils.isNotBlank(merchantCode)){
			sqlCount.append("AND m.MERCHANT_CODE = ? ");
			sql.append("AND m.MERCHANT_CODE = ? ");
			
			param.setString(merchantCode);
		}
		
		if(null != status){
			sqlCount.append("AND m.STATUS = ? ");
			sql.append("AND m.STATUS = ? ");
		
			param.setInt(status);
		}
		
		if(null != merchantId){
			sqlCount.append("AND m.id = ? ");
			sql.append("AND m.id = ? ");
		
			param.setLong(merchantId);
		}
		
		if(sortField.equalsIgnoreCase("name")){
			alias = "ml";
		}
		
		return getPagingList(MerchantItemDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}

	@Override
	public Merchant findByMerchantCode(final String merchantCode) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT ID,MERCHANT_CODE,SECURITY_KEY,LOGIN_METHOD,ISSUE_STAMP_METHOD,STATUS,IS_DELETED FROM MERCHANT WHERE MERCHANT_CODE = ? AND IS_DELETED = 0 ");
		param.setString(merchantCode);
		
		return super.query(sql.toString(), Merchant.class, param);
	}

}
