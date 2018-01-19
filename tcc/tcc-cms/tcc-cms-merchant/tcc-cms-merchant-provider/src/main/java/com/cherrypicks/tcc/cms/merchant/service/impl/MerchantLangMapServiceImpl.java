package com.cherrypicks.tcc.cms.merchant.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.exception.JsonParseException;
import com.cherrypicks.tcc.cms.merchant.dao.MerchantLangMapDao;
import com.cherrypicks.tcc.cms.merchant.exception.MerchantNotExistException;
import com.cherrypicks.tcc.cms.merchant.model.Merchant;
import com.cherrypicks.tcc.cms.merchant.model.MerchantLangMap;
import com.cherrypicks.tcc.cms.merchant.service.MerchantLangMapService;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.JsonUtil;



@Service(version="1.0",timeout=5000,retries=3)
public class MerchantLangMapServiceImpl extends AbstractBaseService<MerchantLangMap> implements MerchantLangMapService {
	
	@Autowired
	private MerchantLangMapDao merchantLangMapDao;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<MerchantLangMap> merchantLangMapDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(merchantLangMapDao);
	}

	@Override
	@Transactional
	public List<MerchantLangMap> createMerchantLangMap(final Merchant merchant, final String langData, final String lang) {
		List<MerchantLangMap> merchantLangMapList = JsonUtil.toListObject(langData, MerchantLangMap.class);
		if(null == merchantLangMapList){
			throw new JsonParseException(I18nUtil.getMessage(CmsCodeStatus.JSON_PARSE_EXCEPTION, null, lang));
		}
		MerchantLangMap defaultMerchantLangMap = null;
		
		for(MerchantLangMap merchantLangMap : merchantLangMapList){
			if(merchantLangMap.getIsDefault()){
				defaultMerchantLangMap = merchantLangMap;
				break;
			}
		}
		
		if(null == defaultMerchantLangMap){
			throw new IllegalArgumentException("Default lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		for(MerchantLangMap addMerchantLangMap : merchantLangMapList){
			if(StringUtils.isBlank(addMerchantLangMap.getLangCode())){
				throw new IllegalArgumentException("Lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			if(StringUtils.isBlank(addMerchantLangMap.getName()) && addMerchantLangMap.getIsDefault()){
				throw new IllegalArgumentException("Name "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			if(StringUtils.isBlank(addMerchantLangMap.getName())){
				addMerchantLangMap.setDescr(defaultMerchantLangMap.getDescr());
				addMerchantLangMap.setImg(defaultMerchantLangMap.getImg());
				addMerchantLangMap.setLogo(defaultMerchantLangMap.getLogo());
				addMerchantLangMap.setName(defaultMerchantLangMap.getName());
				addMerchantLangMap.setIsDefault(false);
			}
			addMerchantLangMap.setCreatedTime(merchant.getCreatedTime());
			addMerchantLangMap.setCreatedBy(merchant.getCreatedBy());
			addMerchantLangMap.setMerchantId(merchant.getId());
			addMerchantLangMap.setIsDeleted(false);
			merchantLangMapDao.add(addMerchantLangMap);
		}
		
		return merchantLangMapList;
	}

	@Override
	@Transactional
	public List<MerchantLangMap> updateMerchantLangMap(final Merchant merchant, final String defaultLangCode,final String langData, final String lang) {
		List<MerchantLangMap> merchantLangMapList = JsonUtil.toListObject(langData, MerchantLangMap.class);
		if(null == merchantLangMapList){
			throw new JsonParseException(I18nUtil.getMessage(CmsCodeStatus.JSON_PARSE_EXCEPTION, null, lang));
		}
		
		MerchantLangMap defaultMerchantLangMap = merchantLangMapDao.findDefaultMerchantLangMapByMerchantId(merchant.getId());
		if(!defaultLangCode.equals(defaultMerchantLangMap.getLangCode())){
			defaultMerchantLangMap.setIsDefault(false);
			defaultMerchantLangMap.setUpdatedBy(merchant.getUpdatedBy());
			merchantLangMapDao.updateForVersion(defaultMerchantLangMap);
		}
		
		boolean isUpdateDefaultLangCode = true;
		for(MerchantLangMap merchantLangMap : merchantLangMapList){
			MerchantLangMap updateMerchantLangMap = merchantLangMapDao.get(merchantLangMap.getId());
			if(null == updateMerchantLangMap){
				throw new MerchantNotExistException(I18nUtil.getMessage(CmsCodeStatus.MERCHANT_NOT_EXIST,null, lang));
			}
			
			updateMerchantLangMap.setUpdatedBy(merchant.getUpdatedBy());
			updateMerchantLangMap.setIsDefault(false);
			if(updateMerchantLangMap.getLangCode().equals(defaultMerchantLangMap.getLangCode())){
				updateMerchantLangMap.setIsDefault(true);
				isUpdateDefaultLangCode = false;
			}
			
			if(StringUtils.isNotBlank(merchantLangMap.getName()) && !updateMerchantLangMap.getName().equalsIgnoreCase(merchantLangMap.getName())){
				updateMerchantLangMap.setName(merchantLangMap.getName());
			}
			
			if(null != merchantLangMap.getDescr()){
				updateMerchantLangMap.setDescr(merchantLangMap.getDescr());
			}
			if(null != merchantLangMap.getImg()){
				updateMerchantLangMap.setImg(merchantLangMap.getImg());
			}
			if(null != merchantLangMap.getLogo()){
				updateMerchantLangMap.setLogo(merchantLangMap.getLogo());
			}

			merchantLangMapDao.updateForVersion(updateMerchantLangMap);
		}
		
		if(isUpdateDefaultLangCode){
			MerchantLangMap updToDefaultMerchantLangMap = merchantLangMapDao.findByMerchantIdAndLangCode(merchant.getId(),defaultLangCode);
			if(null == updToDefaultMerchantLangMap){
				throw new MerchantNotExistException(I18nUtil.getMessage(CmsCodeStatus.MERCHANT_NOT_EXIST, null, lang));
			}
			updToDefaultMerchantLangMap.setIsDefault(true);
			updToDefaultMerchantLangMap.setUpdatedBy(merchant.getUpdatedBy());
			merchantLangMapDao.updateForVersion(updToDefaultMerchantLangMap);
		}
		
		return merchantLangMapList;
	}

	@Override
	public List<MerchantLangMap> findByMerchantId(final Long merchantId) {
		List<MerchantLangMap> merchantLangMapList = merchantLangMapDao.findByMerchantId(merchantId);
		
		return merchantLangMapList;
	}

	@Override
	public MerchantLangMap findMerchantDefaultNameById(final Long merchantId) {
		return merchantLangMapDao.findDefaultMerchantLangMapByMerchantId(merchantId);
	}

	@Override
	public List<String> findMerchantLangCodes(final Long merchantId) {
		// TODO Auto-generated method stub
		return merchantLangMapDao.findMerchantLangCodes(merchantId);
	}

	@Override
	public String findMerchantDefaultLangCode(final Long merchantId) {
		return merchantLangMapDao.findMerchantDefaultLangCode(merchantId);
	}

//	@Override
//	public List<MerchantLangMap> findDetailByMerchantId(final Long merchantId) {
//		List<MerchantLangMap> merchantLangMapList = merchantLangMapDao.findByMerchantId(merchantId);
////		for(MerchantLangMap merchantLangMap : merchantLangMapList){
////			merchantLangMap.setImg(Global.getCallFileUrl(request, merchantLangMap.getImg()));
////			merchantLangMap.setLogo(Global.getCallFileUrl(request, merchantLangMap.getLogo()));
////		}
//		
//		return merchantLangMapList;
//	}
}
