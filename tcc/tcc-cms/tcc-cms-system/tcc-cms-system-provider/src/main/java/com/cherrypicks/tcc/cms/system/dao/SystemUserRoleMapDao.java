package com.cherrypicks.tcc.cms.system.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.system.model.SystemUserRoleMap;

public interface SystemUserRoleMapDao extends IBaseDao<SystemUserRoleMap> {

	SystemUserRoleMap findByUserId(final Long UserId);

	boolean delByUserIds(List<Long> userIds);

}
