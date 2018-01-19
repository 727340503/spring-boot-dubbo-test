package com.cherrypicks.tcc.cms.system.service;

import java.util.List;

import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.system.model.SystemFunctionFilter;

public interface SystemFunctionFilterService extends IBaseService<SystemFunctionFilter>{

	List<Long> findByFilterCode(final int systemFunctionFilterCode);

}
