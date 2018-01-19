package com.cherrypicks.tcc.cms.system.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.system.dto.AuthorizeSystemUserDetailDTO;
import com.cherrypicks.tcc.cms.system.dto.SystemUserDetailDTO;
import com.cherrypicks.tcc.cms.system.model.SystemUser;

public interface SystemUserDao extends IBaseDao<SystemUser> {

	SystemUser findByUserName(final String userName);

	void updateUserSession(final String session,final Long userId);

	SystemUser findByEmail(final String email);

	boolean delByIds(List<Long> list);

	SystemUserDetailDTO findById(Long systemUserId);

	long findSystemUserCountByRoleIds(List<Object> roleIds);

	AuthorizeSystemUserDetailDTO findAuthorizeUser(Long userId);

	Integer checkDBConnect(final String tableName);

}
