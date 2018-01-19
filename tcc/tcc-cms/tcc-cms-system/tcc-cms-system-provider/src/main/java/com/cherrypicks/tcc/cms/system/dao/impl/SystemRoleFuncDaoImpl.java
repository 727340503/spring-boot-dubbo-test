package com.cherrypicks.tcc.cms.system.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.system.dao.SystemRoleFuncDao;
import com.cherrypicks.tcc.cms.system.model.SystemRoleFunctionMap;

@Repository
public class SystemRoleFuncDaoImpl extends AbstractBaseDao<SystemRoleFunctionMap> implements SystemRoleFuncDao {

	@Override
	public List<SystemRoleFunctionMap> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delByRoleId(Long roleId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("DELETE FROM SYSTEM_ROLE_FUNCTION_MAP WHERE ROLE_ID = ? ");
		param.setLong(roleId);

		return updateForBoolean(sql.toString(),param);
	}

	@Override
	public int delByRoleIds(List<Object> roleIds) {
		String sql = "delete from SYSTEM_ROLE_FUNCTION_MAP where ROLE_ID in (:roleIds)";
		Map<String, List<Object>> paramMap = Collections.singletonMap("roleIds", roleIds);
		return super.updateForRecordWithNamedParam(sql.toString(), paramMap);
	}

	@Override
	public List<Long> findFuncIdsByRoleId(Long roleId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT FUNC_ID FROM SYSTEM_ROLE_FUNCTION_MAP WHERE ROLE_ID = ? AND IS_DELETED = 0 ");
		param.setLong(roleId);

		return queryForLongs(sql.toString(),param);
	}

}
