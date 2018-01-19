package com.cherrypicks.tcc.cms.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.system.dao.SystemRoleDao;
import com.cherrypicks.tcc.cms.system.model.SystemRole;

@Repository
public class SystemRoleDaoImpl extends AbstractBaseDao<SystemRole> implements SystemRoleDao {

	@Override
	public List<SystemRole> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		return null;
	}

	@Override
	public SystemRole findByUserId(final Long userId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT sr.ID,sr.ROLE_TYPE,sr.ROLE_NAME,sr.ROLE_DESC,sr.IS_DELETED ")
			.append("FROM SYSTEM_ROLE sr LEFT JOIN SYSTEM_USER_ROLE_MAP su ON su.ROLE_ID = sr.ID ")
			.append("WHERE su.USER_ID = ? AND sr.IS_DELETED = 0 AND su.IS_DELETED = 0 ");
		param.setLong(userId);
		
		return super.query(sql.toString(), SystemRole.class, param);
	}

	@Override
	public SystemRole findByName(String roleName) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT * FROM SYSTEM_ROLE WHERE ROLE_NAME = ? AND IS_DELETED = 0 ");
		param.setString(roleName);
		
		return super.query(sql.toString(), SystemRole.class, param);
	}

	@Override
	public List<SystemRole> findByRoleType(final Integer roleType) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT * FROM SYSTEM_ROLE WHERE ROLE_TYPE = ? AND IS_DELETED = 0 ");
		param.setInt(roleType);
		
		return super.queryForList(sql.toString(), SystemRole.class, param);
	}


}
