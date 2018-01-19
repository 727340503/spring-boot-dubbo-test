package com.cherrypicks.tcc.cms.system.service;

import java.util.List;

import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.system.dto.SystemUserDetailDTO;
import com.cherrypicks.tcc.cms.system.model.SystemUser;

public interface SystemUserService extends IBaseService<SystemUser> {

	SystemUser createSystemUser(final Long userId, final Long merchantId, final String userName, final String password,
			final Long roleId, final String mobile, final String email, final Integer status, final String lang);

	SystemUser updateSystemUser(final Long userId, final Long updUserId, final String userName, final String password,
			final Long roleId, final String mobile, final String email, final Integer status, String lang);
 
	void delByIds(final Long userId,final String ids,final String lang);

	SystemUserDetailDTO findSystemUserDetail(Long systemUserId,String lang);

	Long findSystemUserCountByRoleIds(List<Object> roleIdList);
}
