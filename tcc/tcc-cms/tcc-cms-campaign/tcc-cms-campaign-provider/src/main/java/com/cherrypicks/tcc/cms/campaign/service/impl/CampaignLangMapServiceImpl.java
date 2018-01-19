package com.cherrypicks.tcc.cms.campaign.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.campaign.dao.CampaignLangMapDao;
import com.cherrypicks.tcc.cms.campaign.dto.CampaignLangMapDetailDTO;
import com.cherrypicks.tcc.cms.campaign.exception.CampaignNotExistException;
import com.cherrypicks.tcc.cms.campaign.model.Campaign;
import com.cherrypicks.tcc.cms.campaign.model.CampaignLangMap;
import com.cherrypicks.tcc.cms.campaign.service.CampaignLangMapService;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.exception.ForbiddenException;
import com.cherrypicks.tcc.cms.exception.JsonParseException;
import com.cherrypicks.tcc.cms.merchant.model.MerchantConfig;
import com.cherrypicks.tcc.cms.merchant.service.MerchantConfigService;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.ImageUtil;
import com.cherrypicks.tcc.util.JsonUtil;



@Service(version="1.0",timeout=5000,retries=3)
public class CampaignLangMapServiceImpl extends AbstractBaseService<CampaignLangMap> implements CampaignLangMapService {
	
	@Autowired
	private CampaignLangMapDao campaignLangMapDao;
	
	@Reference(version="1.0")
	private MerchantConfigService merchantConfigService;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<CampaignLangMap> campaignLangMapDao) {
		super.setBaseDao(campaignLangMapDao);
	}

	@Override
	@Transactional
	public void createCampLangMap(final List<String> merchantLangCodes,final String defaultCampaignLangCode, final Campaign campaign, final String langData,final String lang) {
		final List<CampaignLangMap> requestCampaignLangMap = JsonUtil.toListObject(langData, CampaignLangMap.class);
		if(null == requestCampaignLangMap){
			throw new JsonParseException(I18nUtil.getMessage(CmsCodeStatus.JSON_PARSE_EXCEPTION, null, lang));
		}
		CampaignLangMap defaultCampaignLangMap = null;
		
		for(CampaignLangMap addCLM : requestCampaignLangMap){
			if(StringUtils.isBlank(addCLM.getName())){
				throw new IllegalArgumentException("Name "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			if(StringUtils.isBlank(addCLM.getLangCode())){
				throw new IllegalArgumentException("Lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			if(!merchantLangCodes.contains(addCLM.getLangCode())){
				throw new IllegalArgumentException("Lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			addCLM.setCreatedBy(campaign.getCreatedBy());
			addCLM.setCreatedTime(campaign.getCreatedTime());
			addCLM.setIsDeleted(false);
			addCLM.setCampaignId(campaign.getId());
			campaignLangMapDao.add(addCLM);

			if(addCLM.getLangCode().equalsIgnoreCase(defaultCampaignLangCode)){
				defaultCampaignLangMap = addCLM;
			}
			merchantLangCodes.remove(addCLM.getLangCode());
		}
		
		if(null == defaultCampaignLangMap){
			throw new IllegalArgumentException("Default lang code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang)); 
		}
		
		for(String langCode : merchantLangCodes){
			defaultCampaignLangMap.setId(null);
			defaultCampaignLangMap.setLangCode(langCode);
			campaignLangMapDao.add(defaultCampaignLangMap);
		}
		
	}

	@Override
	public void updateCampLangMap(final Campaign campaign, final String langData, final String lang) {
		final List<CampaignLangMap> reqCampaignLangMapList = JsonUtil.toListObject(langData, CampaignLangMap.class);
		if(null == reqCampaignLangMapList){
			throw new JsonParseException(I18nUtil.getMessage(CmsCodeStatus.JSON_PARSE_EXCEPTION, null, lang));
		}
		
		for(CampaignLangMap reqCampaignLangMap : reqCampaignLangMapList){
			CampaignLangMap updCampaignLangMap = campaignLangMapDao.get(reqCampaignLangMap.getId());
			if(null == updCampaignLangMap){
				throw new CampaignNotExistException(I18nUtil.getMessage(CmsCodeStatus.CAMPAIGN_NOT_EXIST, null, lang));
			}
			
			if(StringUtils.isBlank(reqCampaignLangMap.getName())){
				throw new IllegalArgumentException("Campaign name "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			if(updCampaignLangMap.getCampaignId().intValue() != campaign.getId().intValue()){
				throw new ForbiddenException();
			}
			
			if(StringUtils.isNotBlank(reqCampaignLangMap.getName())){
				updCampaignLangMap.setName(reqCampaignLangMap.getName());
			}
			
			if(null != reqCampaignLangMap.getBgImg()){
				updCampaignLangMap.setBgImg(reqCampaignLangMap.getBgImg());
			}
			if(null != reqCampaignLangMap.getCoverImg()){
				updCampaignLangMap.setCoverImg(reqCampaignLangMap.getCoverImg());
			}
			if(null != reqCampaignLangMap.getPrmBannerImg()){
				updCampaignLangMap.setPrmBannerImg(reqCampaignLangMap.getPrmBannerImg());
			}
			if(null != reqCampaignLangMap.getBgColor()){
				updCampaignLangMap.setBgColor(reqCampaignLangMap.getBgColor());
			}
			
			if(null != reqCampaignLangMap.getDescr()){
				updCampaignLangMap.setDescr(reqCampaignLangMap.getDescr());
			}
			if(null != reqCampaignLangMap.getTerms()){
				updCampaignLangMap.setTerms(reqCampaignLangMap.getTerms());
			}
				
			
			updCampaignLangMap.setUpdatedBy(campaign.getUpdatedBy());
			
			campaignLangMapDao.updateForVersion(updCampaignLangMap);
		}
	}

	@Override
	public List<CampaignLangMapDetailDTO> findCampLangMapDetailByCampaignId(final Long merchantId,final Long campaignId) {
		List<CampaignLangMapDetailDTO> details =  campaignLangMapDao.findDetailByCampaignId(campaignId);
		
		MerchantConfig merchantConfig = merchantConfigService.findByMerchantId(merchantId);
		
		for(CampaignLangMapDetailDTO detail : details){
			detail.setBgImg(ImageUtil.getImageFullPath(merchantConfig.getImgDomain(), detail.getBgImg()));
			detail.setCoverImg(ImageUtil.getImageFullPath(merchantConfig.getImgDomain(), detail.getCoverImg()));
			detail.setPrmBannerImg(ImageUtil.getImageFullPath(merchantConfig.getImgDomain(), detail.getPrmBannerImg()));
		}
		return details;
	}

	@Override
	public CampaignLangMap findCampaignDefaultLangMap(final Long campaignId) {
		return campaignLangMapDao.findCampaignDefaultLangMap(campaignId);
	}
	
}
