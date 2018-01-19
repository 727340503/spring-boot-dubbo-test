package com.cherrypicks.tcc.cms.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.exception.RecordIsReferencedException;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.system.dao.SystemRoleDao;
import com.cherrypicks.tcc.cms.system.dto.SystemRoleDetailDTO;
import com.cherrypicks.tcc.cms.system.exception.SystemRoleIsExistException;
import com.cherrypicks.tcc.cms.system.exception.SystemRoleNotExistException;
import com.cherrypicks.tcc.cms.system.model.SystemRole;
import com.cherrypicks.tcc.cms.system.service.SystemFuncService;
import com.cherrypicks.tcc.cms.system.service.SystemRoleService;
import com.cherrypicks.tcc.cms.system.service.SystemUserService;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.I18nUtil;

@Service(version="1.0",timeout=5000,retries=3)
public class SystemRoleServiceImpl extends AbstractBaseService<SystemRole> implements SystemRoleService {

	@Autowired
	private SystemRoleDao systemRoleDao;
	
	@Autowired
	private SystemFuncService systemFuncService;
	
	@Autowired
	private SystemUserService systemUserService;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<SystemRole> systemRoleDao) {
		super.setBaseDao(systemRoleDao);
	}

	@Override
	@Transactional
	public SystemRole createSystemRole(Long userId, Integer roleType, String roleName, String roleDesc,String funcIdsStr, String lang) {
		SystemRole checkRole = systemRoleDao.findByName(roleName);
		if(null != checkRole){
			throw new SystemRoleIsExistException(I18nUtil.getMessage(CmsCodeStatus.SYSTEM_ROLE_IS_EXIST, null, lang));
		}
		
		SystemRole role = new SystemRole();
		role.setIsDeleted(false);
		role.setCreatedBy(String.valueOf(userId));
		role.setCreatedTime(DateUtil.getCurrentDate());
		role.setRoleName(roleName);
		if(StringUtils.isNotBlank(roleDesc)){
			role.setRoleDesc(roleDesc);
		}
		role.setRoleType(SystemRole.Roletype.PLATFORM.getCode() == roleType.intValue() ? roleType : SystemRole.Roletype.MALL.getCode());
		
		role = systemRoleDao.add(role);
		systemFuncService.addRoleFuncMap(role,funcIdsStr);
		
		return role;
	}

	@Override
	@Transactional
	public SystemRole updateSystemRole(Long userId, Long roleId, Integer roleType, String roleName, String roleDesc,
			String funcIdsStr, String lang) {
		SystemRole role = systemRoleDao.get(roleId);
		if(null == role){
			throw new SystemRoleNotExistException(I18nUtil.getMessage(CmsCodeStatus.SYSTEM_ROLE_NOT_EXIST, null, lang));
		}
		
		if(StringUtils.isNotBlank(roleName) && !roleName.equalsIgnoreCase(role.getRoleName())){
			SystemRole checkRole = systemRoleDao.findByName(roleName);
			if(null != checkRole){
				throw new SystemRoleIsExistException(I18nUtil.getMessage(CmsCodeStatus.SYSTEM_ROLE_IS_EXIST, null, lang));
			}
			role.setRoleName(roleName);
		}
		
		if(StringUtils.isNotBlank(roleDesc)){
			role.setRoleDesc(roleDesc);
		}
		if(null != roleType){
			role.setRoleType(SystemRole.Roletype.PLATFORM.getCode() == roleType.intValue() ? roleType : SystemRole.Roletype.MALL.getCode());
		}
		role.setUpdatedBy(String.valueOf(userId));
		
		role = systemRoleDao.updateForVersion(role);
		
		systemFuncService.updateRoleFuncMap(role,funcIdsStr);
		
		return role;
	}

	@Override
	@Transactional
	public void deleteSystemRole(String roleIds, String lang) {
		
		String[] idStrArr = roleIds.split(",");
		List<Object> roleIdList = new ArrayList<Object>();
		for(String idStr : idStrArr){
			roleIdList.add(Long.parseLong(idStr));
		}
		
		long count = systemUserService.findSystemUserCountByRoleIds(roleIdList);
		if(count > 0){
			throw new RecordIsReferencedException(I18nUtil.getMessage(CmsCodeStatus.RECORD_IS_REFERENCED, null, lang));
		}
		
		systemFuncService.deleteRoleFuncMapByRoleIds(roleIdList);
		
		systemRoleDao.delByIds(roleIdList);
	}

	@Override
	public SystemRoleDetailDTO findDetailById(Long roleId, String lang) {
		
		SystemRole role = systemRoleDao.get(roleId);
		if(null == role){
			throw new SystemRoleNotExistException(I18nUtil.getMessage(CmsCodeStatus.SYSTEM_ROLE_NOT_EXIST, null, lang));
		}
		
		SystemRoleDetailDTO roleDetail = new SystemRoleDetailDTO();
		BeanUtils.copyProperties(role, roleDetail);
		
		roleDetail.setSystemFuncs(systemFuncService.findFuncIdsByRoleId(roleId));
		
		return roleDetail;
	}

	@Override
	public SystemRole findByUserId(Long userId) {
		return systemRoleDao.findByUserId(userId);
	}

	@Override
	public List<SystemRole> findRoleListByType(final Integer roleType) {
		return systemRoleDao.findByRoleType(roleType);
	}

}
