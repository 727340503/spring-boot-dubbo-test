package com.cherrypicks.tcc.cms.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.system.dao.SystemFunctionDao;
import com.cherrypicks.tcc.cms.system.dao.SystemRoleFuncDao;
import com.cherrypicks.tcc.cms.system.model.SystemFunction;
import com.cherrypicks.tcc.cms.system.model.SystemRole;
import com.cherrypicks.tcc.cms.system.model.SystemRoleFunctionMap;
import com.cherrypicks.tcc.cms.system.service.SystemFuncService;

@Service(version="1.0",timeout=5000,retries=3)
public class SystemFuncServiceImpl extends AbstractBaseService<SystemFunction> implements SystemFuncService {
	
	@Autowired
	private SystemFunctionDao systemFunctionDao;
	
	@Autowired
	private SystemRoleFuncDao SystemRoleFuncDao;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<SystemFunction> systemFunctionDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(systemFunctionDao);
	}

	@Override
	public List<SystemFunction> findUserButtonPermission(final Long userId) {
		return systemFunctionDao.findUserButtonPermission(userId);
	}

	@Override
	public void addRoleFuncMap(SystemRole role, String funcIdsStr) {
		String[] funcIdArr = funcIdsStr.split(",");
//		List<SystemRoleFunctionMap> systemRoleFuncs = new ArrayList<SystemRoleFunctionMap>();
		for(String funcIdStr : funcIdArr){
			SystemRoleFunctionMap roleFunc = new SystemRoleFunctionMap();
			roleFunc.setCreatedBy(role.getCreatedBy());
			roleFunc.setCreatedTime(role.getCreatedTime());
			roleFunc.setIsDeleted(false);
			roleFunc.setRoleId(role.getId());
			roleFunc.setFuncId(Long.parseLong(funcIdStr));
			SystemRoleFuncDao.add(roleFunc);
		}
		
	}

	@Override
	public void updateRoleFuncMap(SystemRole role,String funcIdsStr) {
		
		SystemRoleFuncDao.delByRoleId(role.getId());
		
		addRoleFuncMap(role, funcIdsStr);
		
	}

	@Override
	public void deleteRoleFuncMapByRoleIds(List<Object> roleIds) {
		SystemRoleFuncDao.delByRoleIds(roleIds);
	}

	@Override
	public List<Long> findFuncIdsByRoleId(Long roleId) {
		return SystemRoleFuncDao.findFuncIdsByRoleId(roleId);
	}

	
}
