package com.cherrypicks.tcc.cms.merchant.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.merchant.dao.MerchantAreaDao;
import com.cherrypicks.tcc.cms.merchant.dto.MerchantAreaDTO;
import com.cherrypicks.tcc.cms.merchant.model.MerchantArea;

@Repository
public class MerchantAreaDaoImpl extends AbstractBaseDao<MerchantArea> implements MerchantAreaDao {

	@Override
	public List<MerchantArea> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MerchantAreaDTO> findByMerchantId(Long merchantId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT ID,MERCHANT_ID,CREATED_TIME FROM MERCHANT_AREA WHERE MERCHANT_ID = ? AND IS_DELETED = 0 ");
		param.setLong(merchantId);
		
		return queryForList(sql.toString(), MerchantAreaDTO.class, param);
	}



}
