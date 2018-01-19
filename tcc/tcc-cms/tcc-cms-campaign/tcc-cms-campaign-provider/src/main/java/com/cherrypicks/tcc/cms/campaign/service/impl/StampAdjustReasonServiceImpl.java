package com.cherrypicks.tcc.cms.campaign.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.campaign.dao.StampAdjustReasonDao;
import com.cherrypicks.tcc.cms.campaign.dao.StampAdjustReasonLangMapDao;
import com.cherrypicks.tcc.cms.campaign.dto.StampAdjustReasonDetailDTO;
import com.cherrypicks.tcc.cms.campaign.dto.StampAdjustReasonLangMapDTO;
import com.cherrypicks.tcc.cms.campaign.exception.StampAdjustReasonNotExistException;
import com.cherrypicks.tcc.cms.campaign.model.StampAdjustReason;
import com.cherrypicks.tcc.cms.campaign.model.StampAdjustReasonLangMap;
import com.cherrypicks.tcc.cms.campaign.service.StampAdjustReasonService;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.exception.ForbiddenException;
import com.cherrypicks.tcc.cms.exception.JsonParseException;
import com.cherrypicks.tcc.cms.merchant.model.MerchantLangMap;
import com.cherrypicks.tcc.cms.merchant.service.MerchantLangMapService;
import com.cherrypicks.tcc.cms.merchant.service.MerchantService;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.system.service.IAuthorizeService;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.Json;
import com.cherrypicks.tcc.util.JsonUtil;


@Service(version="1.0",timeout=5000,retries=3)
public class StampAdjustReasonServiceImpl extends AbstractBaseService<StampAdjustReason> implements StampAdjustReasonService {

	@Autowired
	private StampAdjustReasonDao stampAdjustReasonDao;
	
	@Autowired
	private StampAdjustReasonLangMapDao stampAdjustReasonLangMapDao;
	
	@Reference(version="1.0")
	private MerchantLangMapService merchantLangMapService;
	
	@Reference(version="1.0")
	private MerchantService merchantService;
	
	@Reference(version="1.0")
	private IAuthorizeService authorizeService;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<StampAdjustReason> stampAdjustReasonDao) {
		super.setBaseDao(stampAdjustReasonDao);
	}

	@Override
	@Transactional
	public StampAdjustReason createStampAdjustReason(final Long userId, final Long merchantId, final String langData, final String lang) {
		
		List<StampAdjustReasonLangMap> stampAdjustReasonLangMaps = JsonUtil.toListObject(langData, StampAdjustReasonLangMap.class);
		if(null == stampAdjustReasonLangMaps){
			throw new JsonParseException();
		}
		
		StampAdjustReason addReason = new StampAdjustReason();
		
		addReason.setMerchantId(merchantId);
		addReason.setCreatedBy(String.valueOf(userId));
		addReason.setCreatedTime(DateUtil.getCurrentDate());
		addReason.setIsDeleted(false);
		
		addReason = stampAdjustReasonDao.add(addReason);
		
		List<MerchantLangMap> merchantLangMaps = merchantLangMapService.findByMerchantId(merchantId);
		List<String> langcodes = new ArrayList<String>();
		String defaultLangCode = StringUtils.EMPTY;
		for(MerchantLangMap merchantLangMap : merchantLangMaps){
			langcodes.add(merchantLangMap.getLangCode());
			if(merchantLangMap.getIsDefault()){
				defaultLangCode = merchantLangMap.getLangCode();
			}
		}
		addStampAdjustReasonLangMap(userId, addReason, stampAdjustReasonLangMaps, langcodes, defaultLangCode,lang);
		
		return addReason;
	}

	private void addStampAdjustReasonLangMap(final Long userId, final StampAdjustReason stampAdjustReason, final List<StampAdjustReasonLangMap> stampAdjustReasonLangMaps,
							final List<String> merchantLangCodes, final String merchantDefaultLangCode,final String lang) {
		StampAdjustReasonLangMap stampAdjustDefaultLangMap = null;
		 
		 for(StampAdjustReasonLangMap reasonLangmap : stampAdjustReasonLangMaps){
			 
			 if(StringUtils.isBlank(reasonLangmap.getLangCode())){
				 throw new IllegalArgumentException("Lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			 }
			 if(StringUtils.isBlank(reasonLangmap.getContent())){
				 throw new IllegalArgumentException("Content "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			 }
			 
			 if(!merchantLangCodes.contains(reasonLangmap.getLangCode())){
				 throw new IllegalArgumentException("Lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			 }
			 
			 reasonLangmap.setStampAdjustReasonId(stampAdjustReason.getId());
			 reasonLangmap.setCreatedBy(String.valueOf(userId));
			 reasonLangmap.setCreatedTime(DateUtil.getCurrentDate());
			 reasonLangmap.setIsDeleted(false);
			 
			 reasonLangmap = stampAdjustReasonLangMapDao.add(reasonLangmap);
			 
			 if(reasonLangmap.getLangCode().equals(merchantDefaultLangCode)){
				 stampAdjustDefaultLangMap = reasonLangmap;
			 }
			 
			 merchantLangCodes.remove(reasonLangmap.getLangCode());
		 }
		 
		 if(null == stampAdjustDefaultLangMap){
			 throw new IllegalArgumentException(I18nUtil.getMessage("Default lang code "+CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		 }
		 
		 for(String langCode : merchantLangCodes){
			 stampAdjustDefaultLangMap.setId(null);
			 stampAdjustDefaultLangMap.setLangCode(langCode);
			 stampAdjustReasonLangMapDao.add(stampAdjustDefaultLangMap);
		 }
		
	}

	@Override
	@Transactional
	public void updateStampAdjustReason(final Long userId, final Long id, final String langData, final String lang) {
		List<StampAdjustReasonLangMap> updateReasons = Json.toListObject(langData, StampAdjustReasonLangMap.class);
		if(null == updateReasons){
			throw new JsonParseException();
		}
		
		StampAdjustReason stampAdjustReason = stampAdjustReasonDao.get(id);
		if(null == stampAdjustReason){
			throw new StampAdjustReasonNotExistException();
		}
		
		authorizeService.checkUserMerchantPermission(userId, stampAdjustReason.getMerchantId(), lang);
		
		stampAdjustReason.setUpdatedBy(String.valueOf(userId));
		
		stampAdjustReasonDao.updateForVersion(stampAdjustReason);
		
		updateStampAdjustReasonLangMaps(userId,id,updateReasons,lang);
	}

	private void updateStampAdjustReasonLangMaps(final Long userId,final Long stampAdjustReasonId, final List<StampAdjustReasonLangMap> updateReasons, final String lang) {
		
		for(StampAdjustReasonLangMap reason : updateReasons){
			if(null == reason.getId()){
				throw new IllegalArgumentException("Stamp adjust reason lang map id "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			if(StringUtils.isEmpty(reason.getContent())){
				throw new IllegalArgumentException("Content "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}

			StampAdjustReasonLangMap updateReason = stampAdjustReasonLangMapDao.get(reason.getId());

			if(null == updateReason || updateReason.getStampAdjustReasonId().intValue() != stampAdjustReasonId.intValue()){
				throw new StampAdjustReasonNotExistException();
			}
			
			updateReason.setContent(reason.getContent());
			updateReason.setTitle(reason.getTitle());
			updateReason.setUpdatedBy(String.valueOf(userId));
			
			stampAdjustReasonLangMapDao.updateForVersion(updateReason);
		}
	}

	@Override
	@Transactional
	public void deleteStampAdjustReason(final Long userId, final String ids, final String lang) {
		String[] idArr = ids.split(",");
		List<Object> idList = new ArrayList<Object>();
		for(String idStr : idArr){
			idList.add(Long.parseLong(idStr));
		}

		List<StampAdjustReason> delStampAdjustReasons = stampAdjustReasonDao.findByIds(idList);
		if(null == delStampAdjustReasons || delStampAdjustReasons.size() <= 0){
			throw new StampAdjustReasonNotExistException();
		}
		
		final Long loginUserMerchantId = merchantService.findMerchantBySystemUserId(userId);
		if(null != loginUserMerchantId){
			for(StampAdjustReason reason : delStampAdjustReasons){
				if(reason.getMerchantId().intValue() != loginUserMerchantId.intValue()){
					throw new ForbiddenException();
				}
			}
		}
		
		stampAdjustReasonLangMapDao.delByStampAdjustReasonIds(idList);
		
		stampAdjustReasonDao.delByIds(idList);
		
	}

	@Override
	public StampAdjustReasonDetailDTO getStampAdjustReasonDetail(final Long userId, final Long id, final String lang) {
		
		StampAdjustReason reason = stampAdjustReasonDao.get(id);
		if(null != reason){
			authorizeService.checkUserMerchantPermission(userId, reason.getMerchantId(), lang);
			
			StampAdjustReasonDetailDTO reasonDetail =  new StampAdjustReasonDetailDTO();
			reasonDetail.setId(reason.getId());
			reasonDetail.setMerchantId(reason.getMerchantId());
			
			final List<StampAdjustReasonLangMapDTO> reasonLangMaps = stampAdjustReasonLangMapDao.findByStampAdjustReasonId(reason.getId());
			
			reasonDetail.setStampAdjustReasonLangMaps(reasonLangMaps);
			
			return reasonDetail;
		}
		
		return null;
	}
	
	
}
