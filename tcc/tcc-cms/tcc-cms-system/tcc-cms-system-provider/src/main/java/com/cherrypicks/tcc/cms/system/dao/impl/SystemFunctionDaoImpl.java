package com.cherrypicks.tcc.cms.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cherrypicks.tcc.cms.dao.impl.AbstractBaseDao;
import com.cherrypicks.tcc.cms.dao.jdbc.StatementParameter;
import com.cherrypicks.tcc.cms.system.dao.SystemFunctionDao;
import com.cherrypicks.tcc.cms.system.model.SystemFunction;

@Repository
public class SystemFunctionDaoImpl extends AbstractBaseDao<SystemFunction> implements SystemFunctionDao {

	@Override
	public List<SystemFunction> findByFilter(Map<String, Object> criteriaMap, String sortField, String sortType,
			int... args) {
		return null;
	}
	
//	@Override
//	public List<SystemFunction> findAll() {
//		final StringBuilder sql = new StringBuilder();
//		
//		sql.append("SELECT * FROM SYSTEM_FUNCTION WHERE IS_DELETED = 0 ");
//		
//		return queryForList(sql.toString(), SystemFunction.class);
//	}

	@Override
	public List<SystemFunction> findUserButtonPermission(Long userId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT SF.ID,SF.PAGE_INFO ")
			.append("FROM SYSTEM_FUNCTION SF  ")
			.append("LEFT JOIN SYSTEM_ROLE_FUNCTION_MAP SRF ON SRF.FUNC_ID = SF.ID ")
			.append("LEFT JOIN SYSTEM_USER_ROLE_MAP SUR ON SUR.ROLE_ID = SRF.ROLE_ID ")
			.append("LEFT JOIN SYSTEM_USER_MERCHANT_MAP SM ON  SM.USER_ID = SUR.USER_ID AND SM.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_FUNCTION_FILTER_MAP MF ON MF.MERCHANT_ID = SM.MERCHANT_ID AND MF.FUNC_ID = SF.ID AND MF.IS_DELETED = 0 ")
			.append("WHERE SUR.USER_ID = ? AND MF.ID IS NULL ")
			.append("AND SUR.IS_DELETED = 0 AND SRF.IS_DELETED = 0 AND SF.IS_DELETED = 0 ")
			.append("AND FUNC_TYPE = ? ");
		param.setLong(userId);
		param.setInt(SystemFunction.FuncType.BUTTON.getCode());
		
		return queryForList(sql.toString(), SystemFunction.class,param);
	}

	@Override
	public List<SystemFunction> findByRoleIdAndFuncType(final Long roleId,final Integer funcType) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT SF.* ")
			.append("FROM SYSTEM_FUNCTION SF  ")
			.append("LEFT JOIN SYSTEM_ROLE_FUNCTION_MAP SRF ON SRF.FUNC_ID = SF.ID ")
			.append("WHERE SRF.ROLE_ID = ? AND SF.FUNC_TYPE = ? ")
			.append("AND SRF.IS_DELETED = 0 AND SF.IS_DELETED = 0 ORDER BY SF.DISPLAY_ORDER ");
		param.setLong(roleId);
		param.setInt(funcType);
		
		return queryForList(sql.toString(), SystemFunction.class,param);
	}

	@Override
	public List<SystemFunction> findSystemRoleActions(Long roleId) {
		
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT SF.* ")
			.append("FROM SYSTEM_FUNCTION SF  ")
			.append("LEFT JOIN SYSTEM_ROLE_FUNCTION_MAP SRF ON SRF.FUNC_ID = SF.ID ")
			.append("WHERE SRF.ROLE_ID = ? AND SF.FUNC_TYPE != ? ")
			.append("AND SRF.IS_DELETED = 0 AND SF.IS_DELETED = 0 ORDER BY SF.DISPLAY_ORDER ");
		param.setLong(roleId);
		param.setInt(SystemFunction.FuncType.MENU.getCode());
		
		return queryForList(sql.toString(), SystemFunction.class,param);
	}

	@Override
	public List<SystemFunction> findMallUserMenus(final Long userId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT SF.* FROM SYSTEM_FUNCTION SF ")
			.append("LEFT JOIN SYSTEM_ROLE_FUNCTION_MAP SRF ON SRF.FUNC_ID = SF.ID AND SRF.IS_DELETED = 0 ")
			.append("LEFT JOIN SYSTEM_USER_ROLE_MAP SU ON SU.ROLE_ID = SRF.ROLE_ID AND SU.IS_DELETED = 0 ")
			.append("LEFT JOIN SYSTEM_USER_MERCHANT_MAP SM ON SM.USER_ID = SU.USER_ID AND SU.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_FUNCTION_FILTER_MAP MF ON MF.FUNC_ID = SF.ID AND MF.MERCHANT_ID = SM.MERCHANT_ID AND MF.IS_DELETED = 0 ")
			.append("WHERE SU.USER_ID = ?  AND SF.FUNC_TYPE = ? AND MF.ID IS NULL AND SF.IS_DELETED = 0 ")
			.append("ORDER BY SF.DISPLAY_ORDER ");
		
		param.setLong(userId);
		param.setInt(SystemFunction.FuncType.MENU.getCode());
		
		return queryForList(sql.toString(), SystemFunction.class, param);
	}

	@Override
	public List<SystemFunction> findMallUserActions(final Long userId) {
		final StringBuilder sql = new StringBuilder();
		final StatementParameter param = new StatementParameter();
		
		sql.append("SELECT SF.* FROM SYSTEM_FUNCTION SF ")
			.append("LEFT JOIN SYSTEM_ROLE_FUNCTION_MAP SRF ON SRF.FUNC_ID = SF.ID AND SRF.IS_DELETED = 0 ")
			.append("LEFT JOIN SYSTEM_USER_ROLE_MAP SU ON SU.ROLE_ID = SRF.ROLE_ID AND SU.IS_DELETED = 0 ")
			.append("LEFT JOIN SYSTEM_USER_MERCHANT_MAP SM ON SM.USER_ID = SU.USER_ID AND SU.IS_DELETED = 0 ")
			.append("LEFT JOIN MERCHANT_FUNCTION_FILTER_MAP MF ON MF.FUNC_ID = SF.ID AND MF.MERCHANT_ID = SM.MERCHANT_ID AND MF.IS_DELETED = 0 ")
			.append("WHERE SU.USER_ID = ?  AND SF.FUNC_TYPE != ? AND MF.ID IS NULL AND SF.IS_DELETED = 0 ")
			.append("ORDER BY SF.DISPLAY_ORDER ");
		
		param.setLong(userId);
		param.setInt(SystemFunction.FuncType.MENU.getCode());
		
		return queryForList(sql.toString(), SystemFunction.class, param);
	}

}
