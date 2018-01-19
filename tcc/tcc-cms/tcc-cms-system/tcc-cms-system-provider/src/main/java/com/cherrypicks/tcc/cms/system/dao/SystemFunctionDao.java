package com.cherrypicks.tcc.cms.system.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.system.model.SystemFunction;

public interface SystemFunctionDao extends IBaseDao<SystemFunction> {

	List<SystemFunction> findByRoleIdAndFuncType(final Long roleId,final Integer funcType);

	List<SystemFunction> findUserButtonPermission(Long userId);

	List<SystemFunction> findSystemRoleActions(Long roleId);

	List<SystemFunction> findMallUserMenus(final Long userId);

	List<SystemFunction> findMallUserActions(final Long userId);

}
