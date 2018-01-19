package com.cherrypicks.tcc.cms.merchant.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.exception.JsonParseException;
import com.cherrypicks.tcc.cms.merchant.dao.StoreDao;
import com.cherrypicks.tcc.cms.merchant.dao.StoreLangMapDao;
import com.cherrypicks.tcc.cms.merchant.dto.StoreDetailDTO;
import com.cherrypicks.tcc.cms.merchant.dto.StoreLangMapDTO;
import com.cherrypicks.tcc.cms.merchant.exception.MerchantAreaNotExistException;
import com.cherrypicks.tcc.cms.merchant.exception.StoreNotExistException;
import com.cherrypicks.tcc.cms.merchant.model.MerchantArea;
import com.cherrypicks.tcc.cms.merchant.model.MerchantConfig;
import com.cherrypicks.tcc.cms.merchant.model.MerchantLangMap;
import com.cherrypicks.tcc.cms.merchant.model.Store;
import com.cherrypicks.tcc.cms.merchant.model.StoreLangMap;
import com.cherrypicks.tcc.cms.merchant.service.MerchantAreaService;
import com.cherrypicks.tcc.cms.merchant.service.MerchantConfigService;
import com.cherrypicks.tcc.cms.merchant.service.MerchantLangMapService;
import com.cherrypicks.tcc.cms.merchant.service.StoreService;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.system.service.IAuthorizeService;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.ImageUtil;
import com.cherrypicks.tcc.util.JsonUtil;

@Service(version="1.0",timeout=5000,retries=3)
public class StoreServiceImpl extends AbstractBaseService<Store> implements StoreService {

	@Autowired
	private StoreDao storeDao;
	
	@Autowired
	private StoreLangMapDao storeLangMapDao;
	
	@Autowired
	private MerchantLangMapService merchantLangMapService;
	
	@Autowired
	private MerchantAreaService merchantAreaService;
	
	@Reference(version="1.0")
	private IAuthorizeService authorizeService;
	
	@Autowired
	private MerchantConfigService merchantConfigService;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<Store> storeDao) {
		super.setBaseDao(storeDao);
	}

	@Override
	public StoreDetailDTO findDetailById(final Long userId, final Long storeId, final String lang) {
		Store store = storeDao.get(storeId);
		if(null != store){
			StoreDetailDTO storeDetail = new StoreDetailDTO();
			BeanUtils.copyProperties(store, storeDetail);
			
			authorizeService.checkUserMerchantPermission(userId, store.getMerchantId(), lang);
			
			MerchantConfig merchantConfig = merchantConfigService.findByMerchantId(store.getMerchantId());
			
			List<StoreLangMapDTO> storeLangMaps = storeLangMapDao.findStoreLagnMap(storeDetail.getId());
			for(StoreLangMapDTO storeLangMap : storeLangMaps){
				storeLangMap.setFullImage(ImageUtil.getImageFullPath(merchantConfig.getImgDomain(), storeLangMap.getImage()));
			}
			storeDetail.setStoreLangMaps(storeLangMaps);
			
			return storeDetail;
		}
		
		return null;
	}

	@Override
	@Transactional
	public Store createStore(Long userId, String externalStoreId, Long merchantId,Long merchantAreaId, String phone, Integer status, final String lat, final String lng, String langData,String lang) {
		
		authorizeService.checkUserMerchantPermission(userId, merchantId, lang);
		
		List<StoreLangMap> storeLangMaps = JsonUtil.toListObject(langData, StoreLangMap.class);
		if(null == storeLangMaps || storeLangMaps.size() <= 0 ){
			throw new JsonParseException(I18nUtil.getMessage(CmsCodeStatus.JSON_PARSE_EXCEPTION, null, lang));
		}
		
		MerchantArea merchantArea = merchantAreaService.findById(merchantAreaId);
		if(null == merchantArea || merchantArea.getMerchantId().intValue() != merchantId.intValue()){
			throw new MerchantAreaNotExistException(I18nUtil.getMessage(CmsCodeStatus.MERCHANT_AREA_NOT_EXIST, null, lang));
		}
		
		Store store = new Store();
		store.setCreatedBy(String.valueOf(userId));
		store.setCreatedTime(DateUtil.getCurrentDate());
		store.setExternalStoreId(externalStoreId);
		store.setMerchantId(merchantId);
		store.setMerchantAreaId(merchantAreaId);
		store.setPhone(phone);
		store.setStatus(Store.Status.ACTIVE.toValue());
		store.setLat(lat);
		store.setLng(lng);
		
		if(null != status && status.intValue() == Store.Status.IN_ACTIVE.toValue()){
			store.setStatus(status);
		}
		store = storeDao.add(store);
		
		List<MerchantLangMap> merchantLangMaps = merchantLangMapService.findByMerchantId(merchantId);
		List<String> langcodes = new ArrayList<String>();
		String defaultLangCode = StringUtils.EMPTY;
		for(MerchantLangMap merchantLangMap : merchantLangMaps){
			langcodes.add(merchantLangMap.getLangCode());
			if(merchantLangMap.getIsDefault()){
				defaultLangCode = merchantLangMap.getLangCode();
			}
		}
		
		createStoreLangMap(store,defaultLangCode,langcodes,storeLangMaps,lang);
		return store;
	}

	private void createStoreLangMap(Store store,String defaultLangCode, List<String> langcodes, List<StoreLangMap> storeLangMaps, String lang) {
		 StoreLangMap storeDefaultLangMap = null;
		 
		 for(StoreLangMap storeLangMap : storeLangMaps){
			 if(StringUtils.isBlank(storeLangMap.getLangCode())){
				 throw new IllegalArgumentException("Lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			 }
			 if(StringUtils.isBlank(storeLangMap.getName())){
				 continue;
			 }
			 
			 storeLangMap.setStoreId(store.getId());
			 storeLangMap.setCreatedBy(store.getCreatedBy());
			 storeLangMap.setCreatedTime(store.getCreatedTime());
			 
			 storeLangMapDao.add(storeLangMap);
			 
			 if(storeLangMap.getLangCode().equals(defaultLangCode)){
				 storeDefaultLangMap = storeLangMap;
			 }
			 
			 langcodes.remove(storeLangMap.getLangCode());
		 }
		 
		 if(null == storeDefaultLangMap){
			 throw new IllegalArgumentException(I18nUtil.getMessage("Default lang code "+CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		 }
		 
		 for(String langCode : langcodes){
			 storeDefaultLangMap.setId(null);
			 storeDefaultLangMap.setLangCode(langCode);
			 storeLangMapDao.add(storeDefaultLangMap);
		 }
		 
	}

	@Override
	@Transactional
	public void updateStore(Long userId, Long storeId, Long merchantAreaId, String externalStoreId, String phone, Integer status, final String lat, final String lng, String langData,String lang) {
		Store store = storeDao.get(storeId);
		if(null == store){
			throw new StoreNotExistException(I18nUtil.getMessage(CmsCodeStatus.STORE_NOT_EXIST, null, lang));
		}
		
		authorizeService.checkUserMerchantPermission(userId, store.getMerchantId(), lang);
		
		if(null != merchantAreaId && merchantAreaId.intValue() != store.getMerchantAreaId().intValue()){
			MerchantArea merchantArea = merchantAreaService.findById(merchantAreaId);
			if(null == merchantArea || merchantArea.getMerchantId().intValue() != store.getMerchantId().intValue()){
				throw new MerchantAreaNotExistException(I18nUtil.getMessage(CmsCodeStatus.MERCHANT_AREA_NOT_EXIST, null, lang));
			}
			store.setMerchantAreaId(merchantAreaId);
		}

		store.setLat(lat);
		store.setLng(lng);
		
		if(StringUtils.isNotBlank(externalStoreId)){
			store.setExternalStoreId(externalStoreId);
		}
		if(StringUtils.isNotBlank(phone)){
			store.setPhone(phone);
		}
		if(null != status){
			if(status.intValue() == Store.Status.ACTIVE.toValue() || status.intValue() == Store.Status.IN_ACTIVE.toValue()){
				store.setStatus(status);
			}
		}
		store.setUpdatedBy(String.valueOf(userId));
		
		storeDao.updateForVersion(store);
		if(StringUtils.isNotBlank(langData)){
			List<StoreLangMap> storeLangMaps = JsonUtil.toListObject(langData, StoreLangMap.class);
			if(null == storeLangMaps){
				throw new JsonParseException(I18nUtil.getMessage(CmsCodeStatus.JSON_PARSE_EXCEPTION, null, lang));
			}
			updateStoreLangMap(store,storeLangMaps,lang);
		}
	}

	private void updateStoreLangMap(Store store, List<StoreLangMap> storeLangMaps, String lang) {
		for(StoreLangMap storeLangMap : storeLangMaps){
			if(null == storeLangMap.getId()){
				throw new IllegalArgumentException(I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			StoreLangMap updStoreLangMap = storeLangMapDao.get(storeLangMap.getId());
			if(null == updStoreLangMap || updStoreLangMap.getStoreId().intValue() != store.getId().intValue()){
				throw new StoreNotExistException(I18nUtil.getMessage(CmsCodeStatus.STORE_NOT_EXIST, null, lang));
			}
			
			if(StringUtils.isNotBlank(storeLangMap.getAddress())){
				updStoreLangMap.setAddress(storeLangMap.getAddress());
			}
			if(StringUtils.isNotBlank(storeLangMap.getBusinessInfo())){
				updStoreLangMap.setBusinessInfo(storeLangMap.getBusinessInfo());
			}
			if(StringUtils.isNotBlank(storeLangMap.getName())){
				updStoreLangMap.setName(storeLangMap.getName());
			}
			if(null != storeLangMap.getImage()){
				updStoreLangMap.setImage(storeLangMap.getImage());
			}
			updStoreLangMap.setUpdatedBy(store.getUpdatedBy());
			
			storeLangMapDao.updateForVersion(updStoreLangMap);
		}
	}

	@Override
	public long findCountByMerchantAreaIds(List<Object> merchantAreaIds) {
		return storeDao.findCountByMerchantAreaIds(merchantAreaIds);
	}
	
}
