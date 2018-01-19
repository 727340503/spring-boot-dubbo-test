package com.cherrypicks.tcc.cms.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.system.dao.SystemFunctionFilterDao;
import com.cherrypicks.tcc.cms.system.model.SystemFunctionFilter;

@Repository
public class SystemFunctionFilterDaoImpl extends AbstractBaseDao<SystemFunctionFilter> implements SystemFunctionFilterDao {

	@Override
	public List<SystemFunctionFilter> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType, int... args) {
		return null;
	}

	@Override
	public List<Long> findByFilterCode(final int filterCode) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
	
		sql.append("SELECT FUNC_ID FROM SYSTEM_FUNCTION_FILTER WHERE FILTER_CODE = ? AND IS_DELETED = 0 ");
		param.setInt(filterCode);
		
		return queryForLongs(sql.toString(), param);
	}

	
}
