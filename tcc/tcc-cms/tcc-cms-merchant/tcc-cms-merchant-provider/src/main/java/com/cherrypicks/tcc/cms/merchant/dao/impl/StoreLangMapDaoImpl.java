package com.cherrypicks.tcc.cms.merchant.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.merchant.dao.StoreLangMapDao;
import com.cherrypicks.tcc.cms.merchant.dto.StoreLangMapDTO;
import com.cherrypicks.tcc.cms.merchant.model.StoreLangMap;

@Repository
public class StoreLangMapDaoImpl extends AbstractBaseDao<StoreLangMap> implements StoreLangMapDao {

	@Override
	public List<StoreLangMap> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,int... args) {
		return null;
	}

	@Override
	public List<StoreLangMapDTO> findStoreLagnMap(Long storeId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT SL.ID,SL.IMAGE,SL.ADDRESS,SL.NAME,SL.BUSINESS_INFO,SL.LANG_CODE,ML.NAME AS MERCHANT_NAME,MA.AREA_INFO AS MERCHANT_AREA_INFO FROM STORE_LANG_MAP SL ")
			.append("LEFT JOIN STORE S ON S.ID = SL.STORE_ID ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.LANG_CODE = SL.LANG_CODE AND S.MERCHANT_ID = ML.MERCHANT_ID AND ML.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_AREA_LANG_MAP MA ON MA.LANG_CODE = SL.LANG_CODE AND S.MERCHANT_AREA_ID = MA.MERCHANT_AREA_ID AND MA.IS_DELETED = 0 ")
			.append("WHERE S.IS_DELETED = 0 AND SL.STORE_ID = ? ");
		
		param.setLong(storeId);
		
		return queryForList(sql.toString(), StoreLangMapDTO.class, param);
	}
	
	



}
