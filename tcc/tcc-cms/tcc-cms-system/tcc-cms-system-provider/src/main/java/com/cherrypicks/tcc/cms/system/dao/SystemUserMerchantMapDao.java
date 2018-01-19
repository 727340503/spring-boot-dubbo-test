package com.cherrypicks.tcc.cms.system.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.system.model.SystemUserMerchantMap;

public interface SystemUserMerchantMapDao extends IBaseDao<SystemUserMerchantMap> {

	boolean delByUserIds(final List<Long> userIds);

	SystemUserMerchantMap findByUserId(Long systemUserId);

}
