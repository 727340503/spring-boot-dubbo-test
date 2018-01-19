package com.cherrypicks.tcc.cms.system.service;

import com.cherrypicks.tcc.cms.system.dto.AuthorizeSystemUserDetailDTO;
import com.cherrypicks.tcc.cms.system.vo.AuthenticatedUserDetails;

public interface IAuthorizeService{

	AuthorizeSystemUserDetailDTO findAuthSystemUserDetail(Long userId);

	AuthenticatedUserDetails login(String userName, String password, String lang)  throws Exception;

	void logout(Long userId, String session, String lang);

	boolean checkUserSession(long parseLong, String session, String lang) throws Exception;

	void renewalSessoin(Long systemUserId, String session);

	boolean checkUserMerchantPermission(Long userId, Long merchantId,String lang);

}
