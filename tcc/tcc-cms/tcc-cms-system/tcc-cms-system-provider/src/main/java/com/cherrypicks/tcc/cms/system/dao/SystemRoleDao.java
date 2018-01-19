package com.cherrypicks.tcc.cms.system.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.system.model.SystemRole;

public interface SystemRoleDao extends IBaseDao<SystemRole> {

	SystemRole findByUserId(final Long userId);

	SystemRole findByName(final String roleName);

	List<SystemRole> findByRoleType(final Integer roleType);

}
