package com.cherrypicks.tcc.cms.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cherrypicks.tcc.cms.api.util.AssertUtil;
import com.cherrypicks.tcc.cms.api.vo.ResultVO;
import com.cherrypicks.tcc.cms.api.vo.SuccessVO;
import com.cherrypicks.tcc.cms.service.IBaseService;
import com.cherrypicks.tcc.cms.system.dto.SystemRoleDetailDTO;
import com.cherrypicks.tcc.cms.system.model.SystemRole;
import com.cherrypicks.tcc.cms.system.service.SystemRoleService;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;

@RestController
public class SystemRoleController extends BaseController<SystemRole>{
	
	@Reference(version="1.0")
	private SystemRoleService systemRoleService;
	
	@Override
	@Reference(version="1.0",interfaceClass=SystemRoleService.class)
	public void setBaseService(IBaseService<SystemRole> systemRoleService) {
		super.setBaseService(systemRoleService);
	}
	
	@RequestMapping(value="/getSystemRoleAllList",method=RequestMethod.POST)
	public ResultVO findAllRoles(final Long userId){
		
		SystemRole userRole = systemRoleService.findByUserId(userId);
		List<SystemRole> roles = null;
		if(userRole.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			roles = systemRoleService.findRoleListByType(SystemRole.Roletype.MALL.getCode());
		}else{
			roles = systemRoleService.findAll();
		}
		
		ResultVO result = new ResultVO();
		result.setResult(roles);
		
		return result;
	}
	
	
	@RequestMapping(value="/createSystemRole",method=RequestMethod.POST)
	public ResultVO addRole(final Long userId,final Integer roleType,final String roleName, final String roleDesc,final String funcIds, final String lang){
		
		AssertUtil.notNull(roleType, "Role type "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(roleName, "Role name "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(roleName, "Func ids "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		SystemRole role = systemRoleService.createSystemRole(userId,roleType,roleName,roleDesc,funcIds,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(role);
		return result;
	}
	
	@RequestMapping(value="/updateSystemRole",method=RequestMethod.POST)
	public ResultVO updateSystemRole(final Long userId,final Long roleId, final Integer roleType,final String roleName, final String roleDesc,final String funcIds, final String lang){
		
		AssertUtil.notNull(roleId, "Role id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(roleName, "Func ids "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
	
		SystemRole role = systemRoleService.updateSystemRole(userId,roleId,roleType,roleName,roleDesc,funcIds,lang);

		ResultVO result = new ResultVO();
		result.setResult(role);
		return result;
	}
	
	@RequestMapping(value="/deleteSystemRole",method=RequestMethod.POST)
	public SuccessVO deleteSystemRole(final Long userId,final String roleIds,final String lang){
		
		AssertUtil.notNull(roleIds, "Role id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		systemRoleService.deleteSystemRole(roleIds,lang);
		
		return new SuccessVO();
	}
	
	@RequestMapping(value="/getSystemRoleDetail",method=RequestMethod.POST)
	public ResultVO getSystemRoleDetail(final Long roleId,final String lang){
		
		AssertUtil.notNull(roleId, "Role id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		SystemRoleDetailDTO systemRoleDetail = systemRoleService.findDetailById(roleId,lang);
		
		ResultVO result = new ResultVO();
		result.setResult(systemRoleDetail);
		return result;
	}
	
}
