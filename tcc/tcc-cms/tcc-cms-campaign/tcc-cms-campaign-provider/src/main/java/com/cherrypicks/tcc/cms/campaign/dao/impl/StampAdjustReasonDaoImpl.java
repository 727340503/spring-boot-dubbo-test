package com.cherrypicks.tcc.cms.campaign.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.campaign.dao.StampAdjustReasonDao;
import com.cherrypicks.tcc.cms.campaign.dto.StampAdjustReasonItemDTO;
import com.cherrypicks.tcc.cms.campaign.model.StampAdjustReason;
import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;
import com.cherrypicks.tcc.util.TimeZoneConvert;

@Repository
public class StampAdjustReasonDaoImpl extends AbstractBaseDao<StampAdjustReason> implements StampAdjustReasonDao {

	@Override
	public List<StampAdjustReasonItemDTO> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args) {
		
		String alias = "TEMP";
		
		final String langCode =  (String) criteriaMap.get("langCode");
		final Long merchantId =  (Long) criteriaMap.get("merchantId");
		
		final StringBuilder sqlCount = new StringBuilder();
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sqlCount.append("SELECT COUNT(*) FROM ( ")
				.append("SELECT SA.ID ")
				.append("FROM STAMP_ADJUST_REASON SA ")
				.append("LEFT JOIN MERCHANT M ON M.ID = SA.MERCHANT_ID AND M.IS_DELETED = 0 ")
				.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = SA.MERCHANT_ID AND ML.IS_DELETED = 0 ")
				.append("LEFT JOIN STAMP_ADJUST_REASON_LANG_MAP SR ON SR.STAMP_ADJUST_REASON_ID = SA.ID AND SR.LANG_CODE = ML.LANG_CODE AND SR.IS_DELETED = 0 ")
				.append("WHERE SA.IS_DELETED = 0 ");
		
		sql.append("SELECT * FROM ( ")
			.append("SELECT ")
			.append("SA.ID,SR.CONTENT, ")
			.append("CONVERT_TZ(SA.CREATED_TIME,\"")
			.append(TimeZoneConvert.DEFAULT_TIMEZONE)
			.append("\", M.TIME_ZONE) AS CREATED_TIME ")
			.append("FROM STAMP_ADJUST_REASON SA ")
			.append("LEFT JOIN MERCHANT M ON M.ID = SA.MERCHANT_ID AND M.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_LANG_MAP ML ON ML.MERCHANT_ID = SA.MERCHANT_ID AND ML.IS_DELETED = 0 ")
			.append("LEFT JOIN STAMP_ADJUST_REASON_LANG_MAP SR ON SR.STAMP_ADJUST_REASON_ID = SA.ID AND SR.LANG_CODE = ML.LANG_CODE AND SR.IS_DELETED = 0 ")
			.append("WHERE SA.IS_DELETED = 0 ");
		
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
			sqlCount.append("AND SA.MERCHANT_ID = ? ");
			sql.append("AND SA.MERCHANT_ID = ? ");
		
			param.setLong(merchantId);
		}
		
		sql.append(" ) AS TEMP ");
		sqlCount.append(" ) AS TEMP ");
		
		return getPagingList(StampAdjustReasonItemDTO.class, sqlCount, sql, param, sortField, sortType, alias, args);
	}

}
