package com.cherrypicks.tcc.cms.system.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.system.model.SystemRoleFunctionMap;

public interface SystemRoleFuncDao extends IBaseDao<SystemRoleFunctionMap> {

	boolean delByRoleId(Long roleId);

	int delByRoleIds(List<Object> roleIds);

	List<Long> findFuncIdsByRoleId(Long roleId);


}
