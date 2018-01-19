package com.cherrypicks.tcc.cms.system.service;

import java.util.List;

import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.system.dto.SystemRoleDetailDTO;
import com.cherrypicks.tcc.cms.system.model.SystemRole;

public interface SystemRoleService extends IBaseService<SystemRole>{

	SystemRole createSystemRole(Long userId, Integer roleType, String roleName, String roleDesc, String funcIdsStr,
			String lang);

	SystemRole updateSystemRole(Long userId, Long roleId, Integer roleType, String roleName, String roleDesc,
			String funcIdsStr, String lang);

	void deleteSystemRole(String roleIds, String lang);

	SystemRoleDetailDTO findDetailById(Long roleId, String lang);

	SystemRole findByUserId(Long userId);

	List<SystemRole> findRoleListByType(final Integer roleType);

}
