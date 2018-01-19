package com.cherrypicks.tcc.cms.campaign.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.campaign.dao.StampCardLangMapDao;
import com.cherrypicks.tcc.cms.campaign.model.CampaignLangMap;
import com.cherrypicks.tcc.cms.campaign.model.StampCard;
import com.cherrypicks.tcc.cms.campaign.model.StampCardLangMap;
import com.cherrypicks.tcc.cms.campaign.service.StampCardLangMapService;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.exception.JsonParseException;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.JsonUtil;

@Service(version="1.0",timeout=5000,retries=3)
public class StampCardLangMapServiceImpl extends AbstractBaseService<StampCardLangMap> implements StampCardLangMapService {
	
	@Autowired
	private StampCardLangMapDao stampCardLangMapDao;
	
	@Override
	@Autowired
	public void setBaseDao(IBaseDao<StampCardLangMap> stampCardLangMapDao) {
		super.setBaseDao(stampCardLangMapDao);
	}

	@Override
	public void createStampLangMap(List<String> needAddStampCardLangs, String defaultMerchantLangCode,
			StampCard stampCard,String langData, String lang) {
		final List<CampaignLangMap> campaignLangMaps = JsonUtil.toListObject(langData, CampaignLangMap.class);
		if(null == campaignLangMaps){
			throw new JsonParseException(I18nUtil.getMessage(CmsCodeStatus.JSON_PARSE_EXCEPTION, null, lang));
		}

		StampCardLangMap stampCardDefaultLangMap = null;
		
		for(CampaignLangMap campaignLangMap : campaignLangMaps){
			StampCardLangMap addStampCardLangMap = new StampCardLangMap();
			
			BeanUtils.copyProperties(campaignLangMap, addStampCardLangMap);
			
			addStampCardLangMap.setCreatedBy(stampCard.getCreatedBy());
			addStampCardLangMap.setCreatedTime(stampCard.getCreatedTime());
			addStampCardLangMap.setIsDeleted(false);
			addStampCardLangMap.setStampCardId(stampCard.getId());
			stampCardLangMapDao.add(addStampCardLangMap);
			
			if(addStampCardLangMap.getLangCode().equalsIgnoreCase(defaultMerchantLangCode)){
				stampCardDefaultLangMap = addStampCardLangMap;
			}
			
			needAddStampCardLangs.remove(addStampCardLangMap.getLangCode());
		}
		
		for(String langCode : needAddStampCardLangs){
			stampCardDefaultLangMap.setId(null);
			stampCardDefaultLangMap.setLangCode(langCode);
			stampCardLangMapDao.add(stampCardDefaultLangMap);
		}
		
	}

	@Override
	public void updateStampLangMap(StampCard stampCard, String langData, String lang) {
		final List<CampaignLangMap> campaignLangMaps = JsonUtil.toListObject(langData, CampaignLangMap.class);
		if(null == campaignLangMaps){
			throw new JsonParseException(I18nUtil.getMessage(CmsCodeStatus.JSON_PARSE_EXCEPTION, null, lang));
		}
		
		for(CampaignLangMap campaignLangMap : campaignLangMaps){
			StampCardLangMap updStampCardLangMap = stampCardLangMapDao.findByStampCardIdAndLangCode(stampCard.getId(),campaignLangMap.getLangCode());
			if(null != updStampCardLangMap){
				if(StringUtils.isNotBlank(campaignLangMap.getCoverImg())){
					updStampCardLangMap.setCoverImg(campaignLangMap.getCoverImg());
				}
				
				if(null != campaignLangMap.getName()){
					updStampCardLangMap.setName(campaignLangMap.getName());
				}
				if(null != campaignLangMap.getDescr()){
					updStampCardLangMap.setDescr(campaignLangMap.getDescr());
				}
				if(null != campaignLangMap.getTerms()){
					updStampCardLangMap.setTerms(campaignLangMap.getTerms());
				}
				
				updStampCardLangMap.setUpdatedBy(stampCard.getUpdatedBy());

				stampCardLangMapDao.updateForVersion(updStampCardLangMap);
			}
		}
	}
}
