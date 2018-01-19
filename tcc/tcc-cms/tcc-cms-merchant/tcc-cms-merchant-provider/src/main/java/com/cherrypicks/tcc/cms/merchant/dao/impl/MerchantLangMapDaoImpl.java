package com.cherrypicks.tcc.cms.merchant.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.merchant.dao.MerchantLangMapDao;
import com.cherrypicks.tcc.cms.merchant.model.MerchantLangMap;

@Repository
public class MerchantLangMapDaoImpl extends AbstractBaseDao<MerchantLangMap> implements MerchantLangMapDao {

	@Override
	public List<MerchantLangMap> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		return null;
	}

	@Override
	public List<MerchantLangMap> findByName(final String name) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT * FROM MERCHANT_LANG_MAP WHERE NAME= ? AND IS_DELETED = 0 ");
		param.setString(name);
		
		return queryForList(sql.toString(), MerchantLangMap.class, param);
	}

	@Override
	public List<MerchantLangMap> findByMerchantId(Long merchantId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT * FROM MERCHANT_LANG_MAP WHERE MERCHANT_ID = ? AND IS_DELETED = 0 ");
		param.setLong(merchantId);
		
		return queryForList(sql.toString(), MerchantLangMap.class, param);
	}

	@Override
	public MerchantLangMap findDefaultMerchantLangMapByMerchantId(Long merchantId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT * FROM MERCHANT_LANG_MAP WHERE MERCHANT_ID = ? AND IS_DEFAULT = ? AND IS_DELETED = 0 ");
		param.setLong(merchantId);
		param.setBool(true);
		
		return query(sql.toString(), MerchantLangMap.class, param);
	}

	@Override
	public MerchantLangMap findByMerchantIdAndLangCode(Long merchantId, String langCode) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT * FROM MERCHANT_LANG_MAP WHERE MERCHANT_ID = ? AND LANG_CODE = ? AND IS_DELETED = 0 ");
		param.setLong(merchantId);
		param.setString(langCode);
		
		return query(sql.toString(), MerchantLangMap.class, param);
	}

	@Override
	public List<String> findMerchantLangCodes(Long merchantId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT LANG_CODE FROM MERCHANT_LANG_MAP WHERE MERCHANT_ID = ? AND IS_DELETED = 0 ");
		param.setLong(merchantId);
		
		return queryForStrings(sql.toString(),  param);
	}

	@Override
	public String findMerchantDefaultLangCode(Long merchantId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT LANG_CODE FROM MERCHANT_LANG_MAP WHERE MERCHANT_ID = ? AND IS_DELETED = 0 AND IS_DEFAULT = ? ");
		param.setLong(merchantId);
		param.setBool(true);
		
		return queryForString(sql.toString(),  param);
	}


}
