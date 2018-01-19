package com.cherrypicks.tcc.cms.system.dao;

import java.util.List;

import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.system.model.SystemFunctionFilter;

public interface SystemFunctionFilterDao extends IBaseDao<SystemFunctionFilter> {

	List<Long> findByFilterCode(final int filterCode);


}
