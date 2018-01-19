package com.cherrypicks.tcc.cms.merchant.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.exception.ForbiddenException;
import com.cherrypicks.tcc.cms.exception.JsonParseException;
import com.cherrypicks.tcc.cms.exception.RecordIsReferencedException;
import com.cherrypicks.tcc.cms.merchant.dao.MerchantAreaDao;
import com.cherrypicks.tcc.cms.merchant.dao.MerchantAreaLangMapDao;
import com.cherrypicks.tcc.cms.merchant.dto.MerchantAreaDTO;
import com.cherrypicks.tcc.cms.merchant.exception.MerchantAreaNotExistException;
import com.cherrypicks.tcc.cms.merchant.model.MerchantArea;
import com.cherrypicks.tcc.cms.merchant.model.MerchantAreaLangMap;
import com.cherrypicks.tcc.cms.merchant.model.MerchantLangMap;
import com.cherrypicks.tcc.cms.merchant.service.MerchantAreaService;
import com.cherrypicks.tcc.cms.merchant.service.MerchantLangMapService;
import com.cherrypicks.tcc.cms.merchant.service.MerchantService;
import com.cherrypicks.tcc.cms.merchant.service.StoreService;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.system.model.SystemRole;
import com.cherrypicks.tcc.cms.system.service.IAuthorizeService;
import com.cherrypicks.tcc.cms.system.service.SystemRoleService;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.JsonUtil;



@Service(version="1.0",timeout=5000,retries=3)
public class MerchantAreaServiceImpl extends AbstractBaseService<MerchantArea> implements MerchantAreaService {
	
	@Autowired
	private MerchantAreaDao merchantAreaDao;
	
	@Autowired
	private MerchantAreaLangMapDao merchantAreaLangMapDao;
	
	@Autowired
	private MerchantLangMapService merchantLangMapService;
	
	@Reference(version="1.0")
	private IAuthorizeService authorizeService;
	
	@Reference(version="1.0")
	private SystemRoleService systemRoleService;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private StoreService storeService;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<MerchantArea> merchantAreaDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(merchantAreaDao);
	}

	@Override
	public List<MerchantAreaDTO> findByMerchantId(Long userId, Long merchantId, String lang) {
		
		authorizeService.checkUserMerchantPermission(userId, merchantId, lang);
		
		List<MerchantAreaDTO> merchantAreaList = merchantAreaDao.findByMerchantId(merchantId);

		if(null != merchantAreaList && merchantAreaList.size() > 0 ){
			for(MerchantAreaDTO merchantArea : merchantAreaList){
				merchantArea.setMerchantAreaLangMaps(merchantAreaLangMapDao.findByMerchantAreaId(merchantArea.getId()));
			}
			
			return merchantAreaList;
		}
		
		return null;
	}

	@Override
	@Transactional
	public MerchantArea createMerchantArea(Long userId, Long merchantId, String langData, String lang) {
		
		authorizeService.checkUserMerchantPermission(userId, merchantId, lang);
		
		List<MerchantAreaLangMap> merchantAreaLangMaps = JsonUtil.toListObject(langData, MerchantAreaLangMap.class);
		if(null == merchantAreaLangMaps){
			throw new JsonParseException(I18nUtil.getMessage(CmsCodeStatus.JSON_PARSE_EXCEPTION, null, lang));
		}
		
		MerchantArea merchantArea = new MerchantArea();
		merchantArea.setCreatedBy(String.valueOf(userId));
		merchantArea.setCreatedTime(DateUtil.getCurrentDate());
		merchantArea.setMerchantId(merchantId);
		
		merchantArea = merchantAreaDao.add(merchantArea);
		
		createMerchantAreaLangMap(merchantArea,merchantAreaLangMaps,lang);
		
		return merchantArea;
	}

	private void createMerchantAreaLangMap(MerchantArea merchantArea, List<MerchantAreaLangMap> merchantAreaLangMaps,String lang) {
		
		List<MerchantLangMap> merchantLangMaps = merchantLangMapService.findByMerchantId(merchantArea.getMerchantId());
		
		List<String> langCodes = new ArrayList<String>();
		String merchantDefaultLangCode = StringUtils.EMPTY;
		for(MerchantLangMap merchantLangMap : merchantLangMaps){
			langCodes.add(merchantLangMap.getLangCode());
			if(merchantLangMap.getIsDefault()){
				merchantDefaultLangCode = merchantLangMap.getLangCode();
			}
		}
		
		MerchantAreaLangMap merchantAreaDefaultLangMap = null;
		
		for(MerchantAreaLangMap merchantAreaLangMap : merchantAreaLangMaps){
			if(StringUtils.isBlank(merchantAreaLangMap.getLangCode())){
				throw new IllegalArgumentException("Lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			if(StringUtils.isBlank(merchantAreaLangMap.getAreaInfo())){
				throw new IllegalArgumentException("Area info "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			merchantAreaLangMap.setCreatedBy(merchantArea.getCreatedBy());
			merchantAreaLangMap.setCreatedTime(merchantArea.getCreatedTime());
			merchantAreaLangMap.setMerchantAreaId(merchantArea.getId());
			
			merchantAreaLangMapDao.add(merchantAreaLangMap);
			
			if(merchantDefaultLangCode.equalsIgnoreCase(merchantAreaLangMap.getLangCode())){
				merchantAreaDefaultLangMap = merchantAreaLangMap;
			}
			
			langCodes.remove(merchantAreaLangMap.getLangCode());
		}
		
		if(null == merchantAreaDefaultLangMap){
			throw new IllegalArgumentException("Default lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		for(String langCode : langCodes){
			merchantAreaDefaultLangMap.setId(null);
			merchantAreaDefaultLangMap.setLangCode(langCode);
			
			merchantAreaLangMapDao.add(merchantAreaDefaultLangMap);
		}
	}

	@Override
	@Transactional
	public void updateMerchantArea(Long userId, Long merchantAreaId, String langData, String lang) {
		MerchantArea merchantArea = merchantAreaDao.get(merchantAreaId);
		if(null == merchantArea){
			throw new MerchantAreaNotExistException(I18nUtil.getMessage(CmsCodeStatus.MERCHANT_AREA_NOT_EXIST, null, lang));
		}
		
		List<MerchantAreaLangMap> merchantAreaLangMaps = JsonUtil.toListObject(langData, MerchantAreaLangMap.class);
		if(null == merchantAreaLangMaps){
			throw new JsonParseException(I18nUtil.getMessage(CmsCodeStatus.JSON_PARSE_EXCEPTION, null, lang));
		}
		
		authorizeService.checkUserMerchantPermission(userId, merchantArea.getMerchantId(), lang);
		
		for(MerchantAreaLangMap merchantAreaLangMap : merchantAreaLangMaps){
			if(null == merchantAreaLangMap.getId()){
				throw new IllegalArgumentException("Merchant area lang map id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			MerchantAreaLangMap updMerchantAreaLangMap = merchantAreaLangMapDao.get(merchantAreaLangMap.getId());
			if(null == updMerchantAreaLangMap){
				throw new MerchantAreaNotExistException(I18nUtil.getMessage(CmsCodeStatus.MERCHANT_AREA_NOT_EXIST, null, lang));
			}
			
			if(updMerchantAreaLangMap.getMerchantAreaId().intValue() != merchantAreaId.intValue()){
				throw new ForbiddenException(I18nUtil.getMessage(CmsCodeStatus.FORBIDDEN, null, lang));
			}
			
			if(StringUtils.isNotBlank(merchantAreaLangMap.getAreaInfo())){
				updMerchantAreaLangMap.setAreaInfo(merchantAreaLangMap.getAreaInfo());
			}
			if(StringUtils.isNotBlank(merchantAreaLangMap.getDescr())){
				updMerchantAreaLangMap.setDescr(merchantAreaLangMap.getDescr());
			}
			
			updMerchantAreaLangMap.setUpdatedBy(String.valueOf(userId));
			
			merchantAreaLangMapDao.updateForVersion(updMerchantAreaLangMap);
		}
		
	}

	@Override
	@Transactional
	public void delByIds(Long userId, String ids, String lang) {
		String[] idArr = ids.split(",");
		List<Object> idList = new ArrayList<Object>();
		for(String idStr : idArr){
			idList.add(Long.parseLong(idStr));
		}
		
		long count = storeService.findCountByMerchantAreaIds(idList);
		if(count > 0){
			throw new RecordIsReferencedException(I18nUtil.getMessage(CmsCodeStatus.RECORD_IS_REFERENCED, null, lang));
		}
		
		SystemRole userRole = systemRoleService.findByUserId(userId);
		boolean isPlatform = false;
		if(userRole.getRoleType().intValue() == SystemRole.Roletype.PLATFORM.getCode()){
			isPlatform = true;
		}
		
		Long userMerchantId = 0L;
		if(!isPlatform){
			userMerchantId = merchantService.findMerchantBySystemUserId(userId);
		}
		
		List<MerchantArea> merchantAreas = merchantAreaDao.findByIds(idList);
		for(MerchantArea merchantArea : merchantAreas){
			if(!isPlatform && merchantArea.getMerchantId().intValue() != userMerchantId.intValue()){
				throw new ForbiddenException(I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
		}
		
		merchantAreaLangMapDao.delByMerchantAreaIds(idList);
	
		merchantAreaDao.delByIds(idList);
		
	}
	
	
}
