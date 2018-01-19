package com.cherrypicks.tcc.cms.system.service;

import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.system.model.SystemUserMerchantMap;

public interface SystemUserMerchantMapService extends IBaseService<SystemUserMerchantMap>{

	SystemUserMerchantMap findByUserId(final Long userId);

}
