package com.cherrypicks.tcc.cms.campaign.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.campaign.dao.GiftDao;
import com.cherrypicks.tcc.cms.campaign.dao.GiftLangMapDao;
import com.cherrypicks.tcc.cms.campaign.dto.GiftDetailDTO;
import com.cherrypicks.tcc.cms.campaign.dto.GiftLangMapDTO;
import com.cherrypicks.tcc.cms.campaign.exception.GiftNotExistException;
import com.cherrypicks.tcc.cms.campaign.model.Gift;
import com.cherrypicks.tcc.cms.campaign.model.GiftLangMap;
import com.cherrypicks.tcc.cms.campaign.service.CampaignGiftMapService;
import com.cherrypicks.tcc.cms.campaign.service.GiftService;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.exception.JsonParseException;
import com.cherrypicks.tcc.cms.exception.RecordIsReferencedException;
import com.cherrypicks.tcc.cms.merchant.exception.MerchantNotExistException;
import com.cherrypicks.tcc.cms.merchant.model.MerchantConfig;
import com.cherrypicks.tcc.cms.merchant.model.MerchantLangMap;
import com.cherrypicks.tcc.cms.merchant.service.MerchantConfigService;
import com.cherrypicks.tcc.cms.merchant.service.MerchantLangMapService;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.system.service.IAuthorizeService;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.ImageUtil;
import com.cherrypicks.tcc.util.JsonUtil;

@Service(version="1.0",timeout=5000,retries=3)
public class GiftServiceImpl extends AbstractBaseService<Gift> implements GiftService {

	@Autowired
	private GiftDao giftDao;
	
	@Autowired
	private GiftLangMapDao giftLangMapDao;
	
	@Reference(version="1.0")
	private MerchantLangMapService merchantLangMapService;
	
	@Autowired
	private CampaignGiftMapService campaignGiftMapService;
	
	@Reference(version="1.0")
	private IAuthorizeService authorizeService;
	
	@Reference(version="1.0")
	private MerchantConfigService merchantConfigService;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<Gift> giftDao) {
		super.setBaseDao(giftDao);
	}

	@Override
	@Transactional
	public Gift createGift(Long userId, Long merchantId, String langData, String lang) {
		
		authorizeService.checkUserMerchantPermission(userId, merchantId, lang);
		
		List<MerchantLangMap> merchantLangMaps = merchantLangMapService.findByMerchantId(merchantId);
		if(null == merchantLangMaps || merchantLangMaps.size() == 0 ){
			throw new MerchantNotExistException(I18nUtil.getMessage(CmsCodeStatus.MERCHANT_NOT_EXIST, null, lang));
		}
		
		Gift gift = new Gift();
		gift.setCreatedBy(String.valueOf(userId));
		gift.setCreatedTime(DateUtil.getCurrentDate());
		gift.setIsDeleted(false);
		gift.setMerchantId(merchantId);
		
		gift = giftDao.add(gift);
		
		List<String> langcodes = new ArrayList<String>();
		String defaultLangCode = StringUtils.EMPTY;
		for(MerchantLangMap merchantLangMap : merchantLangMaps){
			langcodes.add(merchantLangMap.getLangCode());
			if(merchantLangMap.getIsDefault()){
				defaultLangCode = merchantLangMap.getLangCode();
			}
		}
		
		createGitLangMaps(langcodes,defaultLangCode,gift,langData,lang);
		
		return gift;
	}

	private void createGitLangMaps(List<String> langCodes, String defaultLangCode, Gift gift, String langData, String lang) {
		List<GiftLangMap> giftLangMaps = JsonUtil.toListObject(langData, GiftLangMap.class);
		if(null == giftLangMaps){
			throw new JsonParseException(I18nUtil.getMessage(CmsCodeStatus.JSON_PARSE_EXCEPTION, null, lang));
		}
		GiftLangMap giftDefaultLangMap = null;

		for(GiftLangMap giftLangMap : giftLangMaps){
			if(StringUtils.isBlank(giftLangMap.getName())){
				continue;
			}
			if(StringUtils.isBlank(giftLangMap.getLangCode())){
				throw new IllegalArgumentException("Lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			if(!langCodes.contains(giftLangMap.getLangCode())){
				throw new IllegalArgumentException("Lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			giftLangMap.setCreatedBy(gift.getCreatedBy());
			giftLangMap.setCreatedTime(gift.getCreatedTime());
			giftLangMap.setIsDeleted(false);
			giftLangMap.setGiftId(gift.getId());
			
			giftLangMapDao.add(giftLangMap);
			
			if(giftLangMap.getLangCode().equals(defaultLangCode)){
				giftDefaultLangMap = giftLangMap;
			}
			
			langCodes.remove(giftLangMap.getLangCode());
		}
		
		if(null == giftDefaultLangMap){
			throw new IllegalArgumentException("Default lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		for(String langCode : langCodes){
			giftDefaultLangMap.setId(null);
			giftDefaultLangMap.setLangCode(langCode);
			giftLangMapDao.add(giftDefaultLangMap);
		}
	}

	@Override
	@Transactional
	public void updateGift(Long userId, Long giftId, String langData, String lang) {
		Gift gift = giftDao.get(giftId);
		if(null == gift){
			throw new GiftNotExistException(I18nUtil.getMessage(CmsCodeStatus.GIFT_NOT_EXIST, null, lang));
		}
		
		authorizeService.checkUserMerchantPermission(userId, gift.getMerchantId(), lang);
		
		List<GiftLangMap> giftLangMaps = JsonUtil.toListObject(langData, GiftLangMap.class);
		if(null == giftLangMaps){
			throw new JsonParseException(I18nUtil.getMessage(CmsCodeStatus.JSON_PARSE_EXCEPTION, null, lang));
		}
		
		for(GiftLangMap giftLangMap : giftLangMaps){
			if( null == giftLangMap.getId()){
				throw new IllegalArgumentException(I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			GiftLangMap updGiftLangMap = giftLangMapDao.get(giftLangMap.getId());
			
			if(null != giftLangMap.getName()){
				updGiftLangMap.setName(giftLangMap.getName());
			}
			if(null != giftLangMap.getImage()){
				updGiftLangMap.setImage(giftLangMap.getImage());
			}
			if(null != giftLangMap.getDescr()){
				updGiftLangMap.setDescr(giftLangMap.getDescr());
			}
			
			updGiftLangMap.setUpdatedBy(String.valueOf(userId));

			giftLangMapDao.updateForVersion(updGiftLangMap);
		}
	}

	@Override
	@Transactional
	public void deleteByIds(String ids,String lang) {
		String[] idsArr = ids.split(",");
		List<Object> idList = new ArrayList<Object>();
		for(String idStr : idsArr){
			idList.add(Long.parseLong(idStr));
		}
		
		long count = campaignGiftMapService.getCountByGiftIds(idList);
		if(count > 0 ){
			throw new RecordIsReferencedException(I18nUtil.getMessage(CmsCodeStatus.RECORD_IS_REFERENCED, null, lang));
		}
		
		giftLangMapDao.delByGiftIds(idList);
		
		giftDao.delByIds(idList);
	}

	@Override
	public GiftDetailDTO findGiftDetail(final Long userId, final Long giftId, final String lang) {
		Gift gift = giftDao.get(giftId);
		
		if(null != gift){
			GiftDetailDTO giftDetail = new GiftDetailDTO();
			BeanUtils.copyProperties(gift, giftDetail);
			
			authorizeService.checkUserMerchantPermission(userId, gift.getMerchantId(), lang);
			
			MerchantConfig merchantConfig = merchantConfigService.findByMerchantId(gift.getMerchantId());
			
			List<GiftLangMapDTO> giftLangMaps = giftLangMapDao.findByGiftId(giftDetail.getId());
			for(GiftLangMapDTO giftlangMap : giftLangMaps){
				giftlangMap.setFullImage(ImageUtil.getImageFullPath(merchantConfig.getImgDomain(), giftlangMap.getImage()));
				
				List<String> relatedCampaignNames = campaignGiftMapService.findGiftRelatedCampaignNames(giftId,giftlangMap.getLangCode());
				if(null != relatedCampaignNames && relatedCampaignNames.size() > 0 ){
					final StringBuilder relatedCampaignStr = new StringBuilder();
					for(String campaignname : relatedCampaignNames){
						relatedCampaignStr.append(campaignname);
						relatedCampaignStr.append(",");
					}
					relatedCampaignStr.delete(relatedCampaignStr.length()-1, relatedCampaignStr.length());
				
					giftlangMap.setRelatedCampaign(relatedCampaignStr.toString());
				}
				
			}
			giftDetail.setGiftLangMaps(giftLangMaps);
			
			return giftDetail;
		}
		
		return null;
	}
}
