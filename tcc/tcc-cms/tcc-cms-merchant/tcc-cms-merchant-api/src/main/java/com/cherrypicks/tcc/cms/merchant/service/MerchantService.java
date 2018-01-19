package com.cherrypicks.tcc.cms.merchant.service;

import com.cherrypicks.tcc.cms.merchant.dto.MerchantDetailDTO;
import com.cherrypicks.tcc.cms.merchant.model.Merchant;
import com.cherrypicks.tcc.cms.service.IBaseService;

public interface MerchantService extends IBaseService<Merchant>{

//	MerchantDTO createMerchant(final Long userId,final String securityKey,final String timeZone,final String loginMethod,final Integer issueStampMethod,
//			final Integer status,final String name,final String img,final String logo,final String descr,final String langCode, final String lang);
	Merchant createMerchant(final Long userId,final String securityKey,final String timeZone,final String loginMethod,final Integer issueStampMethod,
			final Integer status,final Boolean isCouponManagement,final Long currencyUnitId,final String dateFormat,final String hoursFormat,
			final String phoneDistrictCode,final Integer reservationType, final Integer mapType, final String langData, final String lang);

//	MerchantDTO updateMerchant(final Long userId, final Long merchantId,final String securityKey,final String timeZone,final String loginMethod,final Integer issueStampMethod,
//			final Integer status,final String name,final String img,final String logo,final String descr,final Boolean isDefault, final String lang);
	Merchant updateMerchant(final Long userId, final Long merchantId,final String securityKey,final String loginMethod,final Integer issueStampMethod,
			final Integer status,final Boolean isCouponManagement,final String defaultLangCode,final String phoneDistrictCode,final String dateFormat,final String hoursFormat,
			final Integer mapType, final String langData, final String lang);

	MerchantDetailDTO findDetailById(final Long userId,final Long merchantId, final String lang);

	String generateMerchantSecurityKey();

	String findMerchantDefaultNameById(Long merchantId);

	Long findMerchantBySystemUserId(Long userId);

	String getMerchantSecurityKey(final Long userId, final String password, final Long merchantId, final String lang);

	void updateMerchantSecurityKey(final Long userId, final Long merchantId, final String securityKey, final String lang);

}
