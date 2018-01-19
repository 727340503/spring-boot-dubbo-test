package com.cherrypicks.tcc.cms.system.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.dao.jedis.RedisKey;
import com.cherrypicks.tcc.cms.exception.ForbiddenException;
import com.cherrypicks.tcc.cms.merchant.service.MerchantLangMapService;
import com.cherrypicks.tcc.cms.merchant.service.MerchantService;
import com.cherrypicks.tcc.cms.system.dao.SystemFunctionDao;
import com.cherrypicks.tcc.cms.system.dao.SystemRoleDao;
import com.cherrypicks.tcc.cms.system.dao.SystemUserDao;
import com.cherrypicks.tcc.cms.system.dao.SystemUserMerchantMapDao;
import com.cherrypicks.tcc.cms.system.dao.cache.SystemUserCacheDao;
import com.cherrypicks.tcc.cms.system.dto.AuthorizeSystemUserDetailDTO;
import com.cherrypicks.tcc.cms.system.exception.InvalidUserNameOrPasswordException;
import com.cherrypicks.tcc.cms.system.exception.SystemUserInvalidStatusException;
import com.cherrypicks.tcc.cms.system.model.SystemFunction;
import com.cherrypicks.tcc.cms.system.model.SystemRole;
import com.cherrypicks.tcc.cms.system.model.SystemUser;
import com.cherrypicks.tcc.cms.system.service.IAuthorizeService;
import com.cherrypicks.tcc.cms.system.service.SystemRoleService;
import com.cherrypicks.tcc.cms.system.vo.AuthenticatedUserDetails;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.EncryptionUtil;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.UUIDGenerator;

@Service(version="1.0",timeout=5000,retries=3)
public class AuthorizeServiceImpl implements IAuthorizeService {
	
	protected Logger log = Logger.getLogger(IAuthorizeService.class.getName());
	
	
	@Autowired
	private SystemUserDao systemUserDao;
	
	@Autowired
	private SystemRoleDao systemRoleDao;
	
	@Autowired
	private SystemFunctionDao systemFunctionDao;
	
	@Autowired
	private SystemUserCacheDao systemUserCacheDao;
	
	@Autowired
	private SystemUserMerchantMapDao systemUserMerchantMapDao;
	
	@Reference(version="1.0")
	private MerchantLangMapService merchantLangMapService;
	
	@Reference(version="1.0")
	private MerchantService merchantService;
	
	@Autowired
	private SystemRoleService systemRoleService;
	
	@Value("${user.expiry.mins:30}")
	private Integer userExpiryMins;

	@Override
	public AuthorizeSystemUserDetailDTO findAuthSystemUserDetail(Long userId) {
		AuthorizeSystemUserDetailDTO authUserDetail = systemUserDao.findAuthorizeUser(userId);
		return authUserDetail;
	}
	
	/**
	 * user login
	 */
	@Override
	@Transactional
	public AuthenticatedUserDetails login(String userName, String password, String lang) throws Exception {

		SystemUser loginUser = systemUserDao.findByUserName(userName);
		if (null == loginUser) {
			log.debug("用户不存在");
			throw new InvalidUserNameOrPasswordException(
					I18nUtil.getMessage(CmsCodeStatus.INVALID_USER_NAME, null, lang));
		}

		String newPassword = EncryptionUtil.getMD5(password);
		if (!newPassword.equalsIgnoreCase(loginUser.getPassword())) {
			log.debug("密码错误");
			throw new InvalidUserNameOrPasswordException(I18nUtil.getMessage(CmsCodeStatus.INVALID_PASSWORD, null, lang));
		}

		if (SystemUser.Status.INACTIVE.getCode() == loginUser.getStatus().intValue()) {
			log.debug("用户状态异常");
			throw new SystemUserInvalidStatusException(I18nUtil.getMessage(CmsCodeStatus.INVALID_USER_STATUS, null, lang));
		}

		if (loginUser.getIsDeleted()) {
			log.debug("用户已删除");
			throw new SystemUserInvalidStatusException(I18nUtil.getMessage(CmsCodeStatus.INVALID_USER_STATUS, null, lang));
		}

		String session = EncryptionUtil.getSHA256(UUIDGenerator.randomUUID());
		systemUserDao.updateUserSession(session, loginUser.getId());

		// login success add session to cache
		addSessionCache(loginUser, session);

		final AuthenticatedUserDetails authUser = new AuthenticatedUserDetails();
		SystemRole userRole = systemRoleDao.findByUserId(loginUser.getId());
		authUser.setSystemUserId(loginUser.getId());
		authUser.setSession(session);
		authUser.setUserName(userName);
		authUser.setSystemUserRole(userRole);

		if(userRole.getRoleType().intValue() == SystemRole.Roletype.PLATFORM.getCode()){
			authUser.setSystemFuncs(systemFunctionDao.findByRoleIdAndFuncType(userRole.getId(),SystemFunction.FuncType.MENU.getCode()));
			authUser.setSystemActions(systemFunctionDao.findSystemRoleActions(userRole.getId()));
			
		}else{
			authUser.setSystemFuncs(systemFunctionDao.findMallUserMenus(loginUser.getId()));
			authUser.setSystemActions(systemFunctionDao.findMallUserActions(loginUser.getId()));

			final Long merchantId = systemUserMerchantMapDao.findByUserId(loginUser.getId()).getMerchantId();
			authUser.setMerchantId(merchantId);
			authUser.setMerchantLangCodes(merchantLangMapService.findMerchantLangCodes(merchantId));
		}
		
		return authUser;
	}
	
	/**
	 * add session cache
	 * 
	 * @param loginUser
	 * @param session
	 */
	private void addSessionCache(SystemUser loginUser, String session) {
		// add session cache
		loginUser.setSession(session);
		loginUser.setSessionExpireTime(DateUtil.addMins(DateUtil.getCurrentDate(), userExpiryMins));
		systemUserCacheDao.add(loginUser);
		// add current session to cache
		systemUserCacheDao.addString(RedisKey.getUserSessionKey(loginUser.getId(), session),
				String.valueOf(loginUser.getId()));
		if (userExpiryMins > 0) {
			systemUserCacheDao.expire(session, userExpiryMins * 60);
			systemUserCacheDao.expire(RedisKey.getUserSessionKey(loginUser.getId(), session), userExpiryMins * 60);
		}
	}
	
	
	@Override
	@Transactional
	public void logout(Long userId, String session, String lang) {
		SystemUser systemUser = systemUserDao.get(userId);
		if(null != systemUser){
			Date now = DateUtil.getCurrentDate();
			systemUser.setSession(null);	
			systemUser.setSessionExpireTime(now);
			systemUser.setUpdatedTime(now);
			systemUserDao.update(systemUser);
		}
		
		systemUserCacheDao.del(userId);
		systemUserCacheDao.del(RedisKey.getUserSessionKey(userId, session));
	}
	
	/**
	 * check user login status
	 */
	@Override
	public boolean checkUserSession(final long userId, final String session, final String lang) throws Exception {
		final String systemUserIdCache = systemUserCacheDao.getString(RedisKey.getUserSessionKey(userId, session));
		if (StringUtils.isNotBlank(systemUserIdCache)) {
			final SystemUser sysUserCache = systemUserCacheDao.get(Long.parseLong(systemUserIdCache));
			if (sysUserCache != null) {
				return validateUserSession(sysUserCache, session);
			} else {
				return checkLoginForDB(userId, session, lang);
			}
		} else {
			return checkLoginForDB(userId, session, lang);
		}
	}

	private boolean checkLoginForDB(final long userId, final String session, final String lang) {
		SystemUser systemUserDB = systemUserDao.get(userId);
		if (null != systemUserDB && validateUserSession(systemUserDB, session)) {
			return true;
		}
		return false;
	}

	private boolean validateUserSession(final SystemUser systemUser, final String session) {
		if (null != systemUser) {
			final Date sessionExprieTime = systemUser.getSessionExpireTime();
			if (null != sessionExprieTime && session.equalsIgnoreCase(systemUser.getSession())
					&& (sessionExprieTime.getTime() - DateUtil.getCurrentDate().getTime()) > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * renewal Sessoin time
	 */
	@Override
	@Transactional
	public void renewalSessoin(final Long systemUserId, final String session) {
		final String sysUserIdCache = systemUserCacheDao.getString(RedisKey.getUserSessionKey(systemUserId, session));

		if (StringUtils.isNotBlank(sysUserIdCache)) {
			final SystemUser sysUserCache = systemUserCacheDao.get(Long.parseLong(sysUserIdCache));
			if (null != sysUserCache) {
				renewalSessionCache(sysUserCache);
			}
		}

		final SystemUser sysUserDB = systemUserDao.get(systemUserId);
		if (null != sysUserDB) {
			final Date date = DateUtil.getCurrentDate();
			sysUserDB.setUpdatedTime(date);
			sysUserDB.setSessionExpireTime(DateUtil.addMins(date, userExpiryMins));
			systemUserDao.update(sysUserDB);
		}
	}
	
	/**
	 * update login user cache expire time
	 * @param sysUserCache
	 */
	private void renewalSessionCache(final SystemUser sysUserCache) {
		if (userExpiryMins > 0) {
			systemUserCacheDao.expire(sysUserCache, userExpiryMins * 60);
			systemUserCacheDao.expire(RedisKey.getUserSessionKey(sysUserCache.getId(), sysUserCache.getSession()),userExpiryMins * 60);
		}
	}

	@Override
	public boolean checkUserMerchantPermission(Long userId, Long merchantId,String lang) {
		SystemRole userRole = systemRoleService.findByUserId(userId);
		if(userRole.getRoleType().intValue() == SystemRole.Roletype.MALL.getCode()){
			Long userMerchantId = merchantService.findMerchantBySystemUserId(userId);
			if(null == userMerchantId || null == merchantId || userMerchantId.intValue() != merchantId.intValue()){
				throw new ForbiddenException(I18nUtil.getMessage(CmsCodeStatus.FORBIDDEN, null, lang));
			}
		}
		
		return true;
	}
}
