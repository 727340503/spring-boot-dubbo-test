package com.cherrypicks.tcc.cms.merchant.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.merchant.dao.MerchantAreaLangMapDao;
import com.cherrypicks.tcc.cms.merchant.model.MerchantAreaLangMap;

@Repository
public class MerchantAreaLangMapDaoImpl extends AbstractBaseDao<MerchantAreaLangMap> implements MerchantAreaLangMapDao {

	@Override
	public List<MerchantAreaLangMap> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		return null;
	}

	@Override
	public List<MerchantAreaLangMap> findByMerchantAreaId(Long MerchantAreaId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT * FROM MERCHANT_AREA_LANG_MAP WHERE MERCHANT_AREA_ID = ? AND IS_DELETED = 0 ");
		param.setLong(MerchantAreaId);
		
		return queryForList(sql.toString(), MerchantAreaLangMap.class, param);
	}

	@Override
	public boolean delByMerchantAreaIds(List<Object> merchantAreaIds) {
		final StringBuilder sql = new StringBuilder("DELETE FROM MERCHANT_AREA_LANG_MAP WHERE MERCHANT_AREA_ID in (:merchantAreaIds)");
        final Map<String, List<Object>> paramMap = Collections.singletonMap("merchantAreaIds", merchantAreaIds);
        return super.updateForRecordWithNamedParam(sql.toString(), paramMap) > 0;
	}



}
