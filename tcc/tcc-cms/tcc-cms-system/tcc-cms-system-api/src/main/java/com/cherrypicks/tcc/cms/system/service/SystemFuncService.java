package com.cherrypicks.tcc.cms.system.service;

import java.util.List;

import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.system.model.SystemFunction;
import com.cherrypicks.tcc.cms.system.model.SystemRole;

public interface SystemFuncService extends IBaseService<SystemFunction>{

	List<SystemFunction> findUserButtonPermission(final Long userId);

	void addRoleFuncMap(SystemRole role, String funcIdsStr);

	void updateRoleFuncMap(SystemRole role,String funcIdsStr);

	void deleteRoleFuncMapByRoleIds(List<Object> roleIds);

	List<Long> findFuncIdsByRoleId(Long roleId);

}
