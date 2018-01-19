package com.cherrypicks.tcc.cms.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.exception.ForbiddenException;
import com.cherrypicks.tcc.cms.merchant.service.MerchantService;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.system.dao.SystemRoleDao;
import com.cherrypicks.tcc.cms.system.dao.SystemUserDao;
import com.cherrypicks.tcc.cms.system.dao.SystemUserMerchantMapDao;
import com.cherrypicks.tcc.cms.system.dao.SystemUserRoleMapDao;
import com.cherrypicks.tcc.cms.system.dto.SystemUserDetailDTO;
import com.cherrypicks.tcc.cms.system.exception.SystemRoleNotExistException;
import com.cherrypicks.tcc.cms.system.exception.SystemUserIsExistException;
import com.cherrypicks.tcc.cms.system.model.SystemRole;
import com.cherrypicks.tcc.cms.system.model.SystemUser;
import com.cherrypicks.tcc.cms.system.model.SystemUserMerchantMap;
import com.cherrypicks.tcc.cms.system.model.SystemUserRoleMap;
import com.cherrypicks.tcc.cms.system.service.IAuthorizeService;
import com.cherrypicks.tcc.cms.system.service.SystemUserService;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.EncryptionUtil;
import com.cherrypicks.tcc.util.I18nUtil;

@Service(version="1.0",timeout=5000,retries=3)
public class SystemUserServiceImpl extends AbstractBaseService<SystemUser> implements SystemUserService {

	@Autowired
	private SystemUserDao systemUserDao;

	@Autowired
	private SystemRoleDao systemRoleDao;

	@Autowired
	private SystemUserRoleMapDao systemUserRoleMapDao;

	@Autowired
	private SystemUserMerchantMapDao systemUserMerchantMapDao;
	
	@Reference(version="1.0")
	private MerchantService merchantService;
	
	@Autowired
	private IAuthorizeService authorizeService;
	
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<SystemUser> systemUserDao) {
		super.setBaseDao(systemUserDao);
	}

	@Override
	@Transactional
	public SystemUser createSystemUser(final Long userId,final Long merchantId,final String userName,final String password,final Long roleId,final String mobile,final String email,final Integer status,final String lang) {
		
		SystemUser checkUser = systemUserDao.findByUserName(userName);
		if(null != checkUser){
			throw new SystemUserIsExistException(I18nUtil.getMessage(CmsCodeStatus.SYSTEM_USER_IS_EXIST, null, lang));
		}
		checkUser = systemUserDao.findByEmail(email);
		if(null != checkUser){
			throw new SystemUserIsExistException(I18nUtil.getMessage(CmsCodeStatus.SYSTEM_USER_EMAIL_IS_EXIT, null, lang));
		}
		
		SystemRole role = systemRoleDao.get(roleId);
		if(null == role){
			throw new SystemRoleNotExistException(I18nUtil.getMessage(CmsCodeStatus.SYSTEM_ROLE_NOT_EXIST, null, lang));
		}
		if(role.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			if(null == merchantId){
				throw new IllegalArgumentException("Merchant "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
		}
		
		checkCreateUserPermission(userId,merchantId,role,lang);

		Date now = DateUtil.getCurrentDate();
		SystemUser addSystemUser = new SystemUser();
		addSystemUser.setUserName(userName);
		addSystemUser.setPassword(EncryptionUtil.getMD5(password));
		addSystemUser.setEmail(email);
		if(StringUtils.isNotBlank(mobile)){ 
			addSystemUser.setMobile(mobile);
		}
		//set user status
		addSystemUser.setStatus(SystemUser.Status.ACTIVE.getCode());
		if(null != status && status.intValue() == SystemUser.Status.INACTIVE.getCode()){
			addSystemUser.setStatus(SystemUser.Status.INACTIVE.getCode());
		}
		
		addSystemUser.setCreatedBy(String.valueOf(userId));
		addSystemUser.setCreatedTime(now);
		addSystemUser.setIsDeleted(false);
		addSystemUser = systemUserDao.add(addSystemUser);
		
		
		//add user role
		SystemUserRoleMap userRoleMap = new SystemUserRoleMap();
		userRoleMap.setUserId(addSystemUser.getId());
		userRoleMap.setRoleId(roleId);
		userRoleMap.setCreatedBy(String.valueOf(userId));  
		userRoleMap.setCreatedTime(now);;
		systemUserRoleMapDao.add(userRoleMap);
		if(role.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			//add system user merchant
			SystemUserMerchantMap systemUserMerchantMap = new SystemUserMerchantMap();
			systemUserMerchantMap.setCreatedBy(String.valueOf(userId));
			systemUserMerchantMap.setCreatedTime(now);
			systemUserMerchantMap.setIsDeleted(false);
			systemUserMerchantMap.setUserId(addSystemUser.getId());
			systemUserMerchantMap.setMerchantId(merchantId);
			systemUserMerchantMap = systemUserMerchantMapDao.add(systemUserMerchantMap);
		}
		
		
		return addSystemUser;
	}

	
	private void checkCreateUserPermission(final Long userId, final Long merchantId, final SystemRole role, final String lang) {
		
		authorizeService.checkUserMerchantPermission(userId, merchantId, lang);
		
		SystemUserDetailDTO userDetail = this.findSystemUserDetail(userId, lang);
		
		if(role.getRoleType().intValue() == SystemRole.Roletype.PLATFORM.getCode()){
			if(userDetail.getRoleType().intValue() != SystemRole.Roletype.PLATFORM.getCode()){
				throw new ForbiddenException();
			}
		}
		
	}

	@Override
	@Transactional
	public SystemUser updateSystemUser(final Long userId, final Long updUserId, final String userName,final String password, final Long roleId, final String mobile, final String email, final Integer status, String lang) {
		
		checkUpdateSystemUserPermission(userId,updUserId,roleId,lang);
		
		if(StringUtils.isNotBlank(userName)){
			SystemUser checkUser = systemUserDao.findByUserName(userName);
			if(null != checkUser && checkUser.getId().intValue() != updUserId.intValue()){
				throw new SystemUserIsExistException(I18nUtil.getMessage(CmsCodeStatus.SYSTEM_USER_IS_EXIST, null, lang));
			}
		}
		
		if(StringUtils.isNotBlank(email)){
			SystemUser checkUser = systemUserDao.findByEmail(email);
			if(null != checkUser && checkUser.getId().intValue() != updUserId.intValue()){
				throw new SystemUserIsExistException(I18nUtil.getMessage(CmsCodeStatus.SYSTEM_USER_EMAIL_IS_EXIT, null, lang));
			}
		}
		
		SystemUser updSystemUser = getUpdateSystemUser(userId,updUserId,userName,password,mobile,email,status,lang);
		
		if(null != updSystemUser){
			systemUserDao.updateForVersion(updSystemUser);
			
			//update user role
			if(null != roleId){
				SystemUserRoleMap userRole = systemUserRoleMapDao.findByUserId(updUserId);
				if(null != userRole && userRole.getRoleId().intValue() != roleId.intValue()){
					userRole.setUpdatedBy(String.valueOf(userId));
					userRole.setUpdatedTime(DateUtil.getCurrentDate());
					userRole.setRoleId(roleId);
					systemUserRoleMapDao.update(userRole);
				}
			}
			return updSystemUser;
		}
		
		return null;
	}

	private void checkUpdateSystemUserPermission(final Long userId, final Long updUserId,final Long roleId, final String lang) {
		final SystemUserDetailDTO userDetail = this.findSystemUserDetail(userId, lang);
		final SystemUserDetailDTO updUserDetail = this.findSystemUserDetail(updUserId, lang);
		if(null != updUserDetail){
			if(updUserDetail.getRoleType().intValue() == SystemRole.Roletype.PLATFORM.getCode() && userDetail.getRoleType().intValue() != SystemRole.Roletype.PLATFORM.getCode()){
				throw new ForbiddenException();
			}
			
			if(updUserDetail.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode() && userDetail.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
				if(userDetail.getMerchantId().intValue() != updUserDetail.getMerchantId().intValue()){
					throw new ForbiddenException();
				}
			}
			
			if(null != roleId){
				final SystemRole role = systemRoleDao.get(roleId);
				
				if(null == role){
					throw new SystemRoleNotExistException();
				}
			
				if(role.getRoleType().intValue() != updUserDetail.getRoleType().intValue()){
					throw new ForbiddenException();
				}
			}
		}
	}

	private SystemUser getUpdateSystemUser(final Long userId, final Long updUserId, final String userName, final String password, final String mobile,final String email, final Integer status, final String lang) {
		SystemUser updSystemUser = systemUserDao.get(updUserId);
		
		if(null != updSystemUser){
			updSystemUser.setUpdatedBy(String.valueOf(userId));
			if(StringUtils.isNotBlank(userName)){
				updSystemUser.setUserName(userName);
			}
			if(StringUtils.isNotBlank(password)){
				updSystemUser.setPassword(EncryptionUtil.getMD5(password));
			}
			if(StringUtils.isNotBlank(email)){
				updSystemUser.setEmail(email);
			}
			if(StringUtils.isNotBlank(mobile)){
				updSystemUser.setMobile(mobile);
			}
			if(null != status){
				//update active status,check user name exist
				if(status.intValue() == SystemUser.Status.ACTIVE.getCode() && updSystemUser.getStatus().intValue() == SystemUser.Status.INACTIVE.getCode()){
					SystemUser checkUser = systemUserDao.findByUserName(userName);
					if(null != checkUser && checkUser.getId().intValue() != updUserId.intValue()){
						throw new SystemUserIsExistException(I18nUtil.getMessage(CmsCodeStatus.SYSTEM_USER_IS_EXIST, null, lang));
					}
					updSystemUser.setStatus(status);
				}else if(status.intValue() == SystemUser.Status.INACTIVE.getCode()){
					updSystemUser.setStatus(status);
				}
			}
		}
		
		return updSystemUser;
	}

	@Override
	@Transactional
	public void delByIds(final Long userId,final String ids,final String lang) {
		
		String[] idsStr = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for(String idStr : idsStr){
			idList.add(Long.parseLong(idStr));
		}

		SystemUserDetailDTO loginUserDetail = this.findSystemUserDetail(userId, lang);
		
		for(Long delUserId : idList){
			SystemUserDetailDTO delUserDetail = this.findSystemUserDetail(delUserId, lang);
			if(delUserDetail.getRoleType().intValue() == SystemRole.Roletype.PLATFORM.getCode()){
				if(loginUserDetail.getRoleType().intValue() != SystemRole.Roletype.PLATFORM.getCode() || loginUserDetail.getId().intValue() == delUserDetail.getId().intValue()){
					throw new ForbiddenException();
				}
			}else{
				if(loginUserDetail.getRoleType().intValue() != SystemRole.Roletype.PLATFORM.getCode() && loginUserDetail.getMerchantId().intValue() != delUserDetail.getMerchantId().intValue()){
					throw new ForbiddenException();
				}
			}
		}
		
		//del system user role map
		systemUserRoleMapDao.delByUserIds(idList);
		//del system user merchant map
		systemUserMerchantMapDao.delByUserIds(idList);
		//del system user
		systemUserDao.delByIds(idList);
	}

	@Override
	public SystemUserDetailDTO findSystemUserDetail(Long systemUserId,String lang) {
		SystemUserDetailDTO userDetail = systemUserDao.findById(systemUserId);
		
		SystemUserMerchantMap userMerchant = systemUserMerchantMapDao.findByUserId(systemUserId);
		
		if(null != userMerchant){
			userDetail.setMerchantId(userMerchant.getMerchantId());
			userDetail.setMerchantName(merchantService.findMerchantDefaultNameById(userMerchant.getMerchantId()));
		} 
		
		return userDetail;
	}

	@Override
	public Long findSystemUserCountByRoleIds(List<Object> roleIds) {
		return systemUserDao.findSystemUserCountByRoleIds(roleIds);
	}

}
