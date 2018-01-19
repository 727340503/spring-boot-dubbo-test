package com.cherrypicks.tcc.cms.campaign.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.campaign.dao.StampDao;
import com.cherrypicks.tcc.cms.campaign.dao.StampLangMapDao;
import com.cherrypicks.tcc.cms.campaign.dto.StampDetailDTO;
import com.cherrypicks.tcc.cms.campaign.dto.StampLangMapDetailDTO;
import com.cherrypicks.tcc.cms.campaign.exception.CampaignIsStartsException;
import com.cherrypicks.tcc.cms.campaign.exception.CampaignNotExistException;
import com.cherrypicks.tcc.cms.campaign.exception.StampNotExistException;
import com.cherrypicks.tcc.cms.campaign.model.Campaign;
import com.cherrypicks.tcc.cms.campaign.model.Stamp;
import com.cherrypicks.tcc.cms.campaign.model.StampCard;
import com.cherrypicks.tcc.cms.campaign.model.StampLangMap;
import com.cherrypicks.tcc.cms.campaign.service.CampaignService;
import com.cherrypicks.tcc.cms.campaign.service.StampCardService;
import com.cherrypicks.tcc.cms.campaign.service.StampService;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.exception.ForbiddenException;
import com.cherrypicks.tcc.cms.exception.JsonParseException;
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
public class StampServiceImpl extends AbstractBaseService<Stamp> implements StampService {

	@Autowired
	private StampDao stampDao;
	
	@Autowired
	private StampLangMapDao stampLangMapDao;
	
	@Autowired
	private CampaignService campaignService;
	
	@Autowired
	private StampCardService stampCardService;
	
	@Reference(version="1.0")
	private MerchantLangMapService merchantLangMapService;
	
//	@Autowired
//	private UserStampDao userStampDao;
	
	@Reference(version="1.0")
	private IAuthorizeService authorizeService;
	
	@Reference(version="1.0")
	private MerchantConfigService merchantConfigService;
	
	@Value("${default.campaign.stamp.image}")
	private String DefaultCampaignStampImage;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<Stamp> stampDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(stampDao);
	}

	private void createStamp(Long userId,Long campaignId, String langData, String lang) {
		Campaign campaign = campaignService.findById(campaignId);
		
		if(null == campaign){
			throw new CampaignNotExistException(I18nUtil.getMessage(CmsCodeStatus.CAMPAIGN_NOT_EXIST, null, lang));
		}
		
		List<MerchantLangMap> merchantLangMaps = merchantLangMapService.findByMerchantId(campaign.getMerchantId());
		List<String> langCodes = new ArrayList<String>();
		for(MerchantLangMap merchantLangMap : merchantLangMaps){
			langCodes.add(merchantLangMap.getLangCode());
		}
		
		
		StampCard stampCard = stampCardService.findbyCampaignId(campaignId);
		Stamp stamp = new Stamp();
		stamp.setStampCardId(stampCard.getId());
		stamp.setCreatedBy(String.valueOf(userId));
		stamp.setCreatedTime(DateUtil.getCurrentDate());
		
		stamp = stampDao.add(stamp);
		
		StampLangMap addStampLangMap = JsonUtil.toObject(langData,  StampLangMap.class);
		createStampLangMaps(langCodes,stamp,addStampLangMap,lang);
		
	}

	private void createStampLangMaps(List<String> langCodes, Stamp stamp, StampLangMap addStampLangMap, String lang) {
		for(String langCode : langCodes){
			if(StringUtils.isBlank(addStampLangMap.getStampImg())){
				throw new IllegalArgumentException(I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			addStampLangMap.setStampId(stamp.getId());
			addStampLangMap.setCreatedBy(stamp.getCreatedBy());
			addStampLangMap.setCreatedTime(stamp.getCreatedTime());
			addStampLangMap.setIsDeleted(false);
			
			addStampLangMap.setId(null);
			addStampLangMap.setLangCode(langCode);
			stampLangMapDao.add(addStampLangMap);
		}
	}

	@Override
	@Transactional
	public void updateStamp(Long userId, final Long campaignId,String langData, String lang) {
		
		Campaign campaign = campaignService.findById(campaignId);
		if(null == campaign){
			throw new CampaignNotExistException();
		}
		
		authorizeService.checkUserMerchantPermission(userId, campaign.getMerchantId(), lang);
		
		if(StringUtils.isNotBlank(langData)){
			List<StampLangMap> stampLangMaps = JsonUtil.toListObject(langData, StampLangMap.class);
			if(null == stampLangMaps || stampLangMaps.size() < 1){
				throw new JsonParseException(I18nUtil.getMessage(CmsCodeStatus.JSON_PARSE_EXCEPTION, null, lang));
			}
			
			for(StampLangMap updStampLangMap : stampLangMaps){
				if(null == updStampLangMap.getId()){
					this.createStamp(userId, campaignId, JsonUtil.toJson(updStampLangMap), lang);
					continue;
				}
				
				StampLangMap stampLangMap = stampLangMapDao.get(updStampLangMap.getId());
				if(null == stampLangMap){
					throw new StampNotExistException(I18nUtil.getMessage(CmsCodeStatus.STAMP_NOT_EXIST, null, lang));
				}
				
				if(StringUtils.isBlank(updStampLangMap.getStampImg())){
					throw new IllegalArgumentException("Stamp img "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
				}
				
				if(!stampLangMap.getStampImg().equals(updStampLangMap.getStampImg())){
					stampLangMapDao.updateImgByStampId(userId,updStampLangMap.getStampImg(),stampLangMap.getStampId());
				}
			}
		}
		
	}

	@Override
	@Transactional
	public void delByIds(final Long userId,final String stampIds,final Long campaignId, final String lang) {
		String[] idArr = stampIds.split(",");
		List<Long> stampIdList = new ArrayList<Long>(); 
		for(String idStr : idArr){
			stampIdList.add(Long.parseLong(idStr));
		}
		
		Campaign campaign = campaignService.findById(campaignId);
		if(null == campaign){
			throw new CampaignNotExistException();
		}
		
		authorizeService.checkUserMerchantPermission(userId, campaign.getMerchantId(), lang);
		
		if(DateUtil.getCurrentDate().getTime() >= campaign.getCollStartTime().getTime()){
			throw new CampaignIsStartsException();
		}
		
//		long userCount = userStampDao.findUserCountByStampIdList(stampIdList);
//		if(userCount > 0){
//			throw new RecordIsReferencedException(I18nUtil.getMessage(CmsCodeStatus.RECORD_IS_REFERENCED, null, lang));
//		}
		
		StampCard stampCard = stampCardService.findbyCampaignId(campaignId);
		
		List<Stamp> stamps = stampDao.findByStampIds(stampIdList);
		for(Stamp stamp : stamps){
			if(stamp.getStampCardId().intValue() != stampCard.getId().intValue()){
				throw new ForbiddenException();
			}
		}
		
		final int campaignStampQty = stampDao.findCampaignStampTotalQty(campaignId);
		
		if(stampIdList.size() >= campaignStampQty){
			throw new IllegalArgumentException("Ids "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		}
		
		stampLangMapDao.delByStampIds(stampIdList);
		
		stampDao.delByStampIds(stampIdList);
	}

	@Override
	public List<StampDetailDTO> findByCampaignId(final Long userId,final Long campaignId, final String lang) {
		StampCard stampCard = stampCardService.findbyCampaignId(campaignId);
		if(null == stampCard){
			throw new StampNotExistException(I18nUtil.getMessage(CmsCodeStatus.STAMP_NOT_EXIST, null, lang));
		}
		
		Campaign campaign = campaignService.findById(campaignId);
		if(null == campaign){
			throw new CampaignNotExistException();
		}
		
		authorizeService.checkUserMerchantPermission(userId, campaign.getMerchantId(), lang);
		
		MerchantConfig merchantConfig = merchantConfigService.findByMerchantId(campaign.getMerchantId());
		
		List<StampDetailDTO> stamps = stampDao.findDetailListByStampCardId(stampCard.getId()); 
		for(StampDetailDTO stamp : stamps){
			List<StampLangMapDetailDTO> stampLangMaps = stampLangMapDao.findByStampId(stamp.getId());
			for(StampLangMapDetailDTO stampLangMap : stampLangMaps){
				stampLangMap.setStampFullImg(ImageUtil.getImageFullPath(merchantConfig.getImgDomain(), stampLangMap.getStampImg()));
			}
			stamp.setStampLangMaps(stampLangMaps);
		}
		
		return stamps;
	}

	@Override
	@Transactional
	public void addCampaignDefaultStamp(final Long userId,final Campaign campaign,final String lang) {
		StampLangMap stampLangMap = new StampLangMap();
		stampLangMap.setCreatedBy(String.valueOf(userId));
		stampLangMap.setCreatedTime(campaign.getCreatedTime());
		stampLangMap.setStampImg(DefaultCampaignStampImage);
		
		this.createStamp(userId, campaign.getId(), JsonUtil.toJson(stampLangMap), lang);
	}
}
