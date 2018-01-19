package com.cherrypicks.tcc.cms.merchant.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.merchant.dao.MerchantFunctionFilterMapDao;
import com.cherrypicks.tcc.cms.merchant.model.MerchantFunctionFilterMap;

@Repository
public class MerchantFunctionFilterMapDaoImpl extends AbstractBaseDao<MerchantFunctionFilterMap> implements MerchantFunctionFilterMapDao {

	@Override
	public List<MerchantFunctionFilterMap> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args) {
		return null;
	}

	@Override
	public void delByMerchantIdAndSystemFuncs(Long merchantId, List<Long> systemFunctionIds) {
		final StringBuilder sql = new StringBuilder();
		final Map<String,Object> paramMap = new HashMap<String,Object>();
		
//		sql.append("DELETE FROM MERCHANT_FUNCTION_FILTER_MAP WHERE MERCHANT_ID = :merchantId AND FUNC_ID IN ( :systemFunctionIds )");
		sql.append("UPDATE MERCHANT_FUNCTION_FILTER_MAP SET IS_DELETED = 1 WHERE MERCHANT_ID = :merchantId AND FUNC_ID IN ( :systemFunctionIds )");
		
		paramMap.put("merchantId", merchantId);
		paramMap.put("systemFunctionIds", systemFunctionIds);
		
		super.updateForRecordWithNamedParam(sql.toString(), paramMap);
	}

	@Override
	public int findByMerchantIdAndFilterCode(Long merchantId, int couponManagementFilterCode) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT COUNT(1) FROM MERCHANT_FUNCTION_FILTER_MAP MF ")
			.append("LEFT JOIN SYSTEM_FUNCTION_FILTER SF ON SF.FUNC_ID = MF.FUNC_ID AND SF.IS_DELETED = 0 ")
			.append("WHERE MF.MERCHANT_ID = ? AND SF.FILTER_CODE = ? AND MF.IS_DELETED = 0");
		
		param.setLong(merchantId);
		param.setInt(couponManagementFilterCode);
		
		return queryForInt(sql.toString(), param);
	}


}
