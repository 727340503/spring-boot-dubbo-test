package com.cherrypicks.tcc.cms.system.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.system.dao.SystemUserMerchantMapDao;
import com.cherrypicks.tcc.cms.system.model.SystemUserMerchantMap;

@Repository
public class SystemUserMerchantMapDaoImpl extends AbstractBaseDao<SystemUserMerchantMap> implements SystemUserMerchantMapDao {

	@Override
	public List<SystemUserMerchantMap> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		return null;
	}

	@Override
	public boolean delByUserIds(List<Long> userIds) {
//		final StringBuilder sql = new StringBuilder("DELETE FROM SYSTEM_USER_MERCHANT_MAP WHERE USER_ID in (:userIds)");
		final StringBuilder sql = new StringBuilder("UPDATE SYSTEM_USER_MERCHANT_MAP SET IS_DELETED = 1 WHERE USER_ID in (:userIds)");
        final Map<String, List<Long>> paramMap = Collections.singletonMap("userIds", userIds);
        return super.updateForRecordWithNamedParam(sql.toString(), paramMap) > 0;
	}

	@Override
	public SystemUserMerchantMap findByUserId(Long systemUserId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT ID,USER_ID,MERCHANT_ID FROM SYSTEM_USER_MERCHANT_MAP WHERE IS_DELETED = 0 AND USER_ID = ? ");
		param.setLong(systemUserId);
		
		return query(sql.toString(), SystemUserMerchantMap.class,param);
	}

}
