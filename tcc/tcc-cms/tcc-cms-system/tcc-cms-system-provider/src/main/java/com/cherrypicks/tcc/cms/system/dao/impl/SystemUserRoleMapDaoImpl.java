package com.cherrypicks.tcc.cms.system.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.system.dao.SystemUserRoleMapDao;
import com.cherrypicks.tcc.cms.system.model.SystemUserRoleMap;

@Repository
public class SystemUserRoleMapDaoImpl extends AbstractBaseDao<SystemUserRoleMap> implements SystemUserRoleMapDao {

	@Override
	public List<SystemUserRoleMap> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,int... args) {
		return null;
	}

	@Override
	public SystemUserRoleMap findByUserId(final Long UserId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT ID,ROLE_ID,USER_ID,CREATED_BY,CREATED_TIME,UPDATED_BY,UPDATED_TIME,IS_DELETED FROM SYSTEM_USER_ROLE_MAP WHERE USER_ID = ? AND IS_DELETED = 0 ");
		param.setLong(UserId);
		
		return query(sql.toString(), SystemUserRoleMap.class, param);
	}

	@Override
	public boolean delByUserIds(List<Long> userIds) {
//		final StringBuilder sql = new StringBuilder("DELETE FROM SYSTEM_USER_ROLE_MAP WHERE USER_ID in (:userIds)");
		final StringBuilder sql = new StringBuilder("UPDATE SYSTEM_USER_ROLE_MAP SET IS_DELETED = 1 WHERE USER_ID in (:userIds)");
        final Map<String, List<Long>> paramMap = Collections.singletonMap("userIds", userIds);
        return super.updateForRecordWithNamedParam(sql.toString(), paramMap) > 0;
	}
	

}
