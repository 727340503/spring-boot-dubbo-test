package com.cherrypicks.tcc.cms.campaign.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.campaign.dao.CampaignGiftExchangeTypeDao;
import com.cherrypicks.tcc.cms.campaign.dao.CampaignGiftMapDao;
import com.cherrypicks.tcc.cms.campaign.dto.CampaignGiftMapDetailDTO;
import com.cherrypicks.tcc.cms.campaign.exception.CampaignGiftMapEXTypeNotExistException;
import com.cherrypicks.tcc.cms.campaign.exception.CampaignGiftMapIsExistException;
import com.cherrypicks.tcc.cms.campaign.exception.CampaignGiftMapNotExistException;
import com.cherrypicks.tcc.cms.campaign.exception.CampaignIsOverOrExpiredException;
import com.cherrypicks.tcc.cms.campaign.exception.CampaignNotExistException;
import com.cherrypicks.tcc.cms.campaign.exception.GiftNotExistException;
import com.cherrypicks.tcc.cms.campaign.model.Campaign;
import com.cherrypicks.tcc.cms.campaign.model.CampaignGiftExchangeType;
import com.cherrypicks.tcc.cms.campaign.model.CampaignGiftMap;
import com.cherrypicks.tcc.cms.campaign.model.Gift;
import com.cherrypicks.tcc.cms.campaign.service.CampaignGiftMapService;
import com.cherrypicks.tcc.cms.campaign.service.CampaignService;
import com.cherrypicks.tcc.cms.campaign.service.GiftService;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.exception.JsonParseException;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.system.service.IAuthorizeService;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.JsonUtil;

@Service(version="1.0",timeout=5000,retries=3)
public class CampaignGiftMapServiceImpl extends AbstractBaseService<CampaignGiftMap> implements CampaignGiftMapService {

	@Autowired
	private CampaignGiftMapDao campaignGiftMapDao;

	@Autowired
	private CampaignGiftExchangeTypeDao campaignGiftExchangeTypeDao;

	@Autowired
	private GiftService giftService;

	@Reference(version="1.0")
	private IAuthorizeService authorizeService;

	@Autowired
	private CampaignService campaignService;

	@Override
	@Autowired
	public void setBaseDao(final IBaseDao<CampaignGiftMap> campaignGiftMapDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(campaignGiftMapDao);
	}

	@Override
	public long getCountByGiftIds(final List<Object> idList) {
		return campaignGiftMapDao.getCountByGiftIds(idList);
	}

	@Override
	@Transactional
	public void addCampaignGifts(final Long userId, final Long campaignId, final Long giftId,
			final String externalGiftId,final Integer isReservation,final String giftExchangeTypeData, final String lang) {
		final List<CampaignGiftExchangeType> campGiftEXTypes = JsonUtil.toListObject(giftExchangeTypeData,CampaignGiftExchangeType.class);
		if (null == campGiftEXTypes) {
			throw new JsonParseException(I18nUtil.getMessage(CmsCodeStatus.JSON_PARSE_EXCEPTION, null, lang));
		}

		final CampaignGiftMapDetailDTO checkCampGiftMap = campaignGiftMapDao.findByCampaignIdAndGiftId(campaignId,giftId);
		if(null != checkCampGiftMap){
			throw new CampaignGiftMapIsExistException(I18nUtil.getMessage(CmsCodeStatus.CAMPAIGN_GIFT_MAP_IS_EXIST, null, lang));
		}

		final Gift checkGift = giftService.findById(giftId);
		if(null == checkGift){
			throw new GiftNotExistException(I18nUtil.getMessage(CmsCodeStatus.GIFT_NOT_EXIST, null, lang));
		}

		final Campaign campaign = campaignService.findById(campaignId);
		if(null == campaign){
			throw new CampaignNotExistException();
		}

		authorizeService.checkUserMerchantPermission(userId, campaign.getMerchantId(), lang);

		CampaignGiftMap addCampaignGiftMap = new CampaignGiftMap();
		addCampaignGiftMap.setCreatedBy(String.valueOf(userId));
		addCampaignGiftMap.setCreatedTime(DateUtil.getCurrentDate());
		addCampaignGiftMap.setGiftId(giftId);
		addCampaignGiftMap.setCampaignId(campaignId);
		addCampaignGiftMap.setExternalGiftId(externalGiftId);
		addCampaignGiftMap.setIsReservation(isReservation);

		addCampaignGiftMap.setStatus(CampaignGiftMap.CampaignGiftMapStatus.AVAILABLE.toValue());

		addCampaignGiftMap = campaignGiftMapDao.add(addCampaignGiftMap);

		createGiftExchangeType(addCampaignGiftMap, campGiftEXTypes, lang);

	}

	private void createGiftExchangeType(final CampaignGiftMap campaignGiftMap, final List<CampaignGiftExchangeType> campGiftEXTypes,
			final String lang) {

		for (final CampaignGiftExchangeType campGiftEXtype : campGiftEXTypes) {
			if (null == campGiftEXtype.getType()) {
				throw new IllegalArgumentException("Type " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}

			if (null == campGiftEXtype.getStampQty()) {
				throw new IllegalArgumentException("Stamp QTY " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}
			
			if(StringUtils.isBlank(campGiftEXtype.getExternalRuleCode())){
				throw new IllegalArgumentException("External rule code " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
			}

			final CampaignGiftExchangeType newCampGiftEXtype = new CampaignGiftExchangeType();
			BeanUtils.copyProperties(campGiftEXtype, newCampGiftEXtype);

			if (campGiftEXtype.getType().intValue() == CampaignGiftExchangeType.Type.STAMPS_CASH.getCode()) {
				if (null == campGiftEXtype.getCashQty()) {
					throw new IllegalArgumentException("Cash QTY " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
				}
			} else {
				newCampGiftEXtype.setType(CampaignGiftExchangeType.Type.STAMPS.getCode());
			}

			newCampGiftEXtype.setStatus(CampaignGiftExchangeType.ExchangeStatus.ACTIVE.toValue());
			if(null != campGiftEXtype.getStatus()){
				if(campGiftEXtype.getStatus().intValue() == CampaignGiftExchangeType.ExchangeStatus.IN_ACTIVE.toValue()){
					newCampGiftEXtype.setStatus(campGiftEXtype.getStatus());
				}
			}

			newCampGiftEXtype.setCampaignGiftMapId(campaignGiftMap.getId());
			newCampGiftEXtype.setCreatedBy(campaignGiftMap.getCreatedBy());
			newCampGiftEXtype.setCreatedTime(campaignGiftMap.getCreatedTime());

			campaignGiftExchangeTypeDao.add(newCampGiftEXtype);
		}

	}

	@Override
	@Transactional
	public void updateCampaignGifts(final Long userId, final Long campaignGiftMapId, final String externalGiftId,
			final Integer status, final Integer isReservation, final String giftExchangeTypeData, final String lang) {

		final List<CampaignGiftExchangeType> campGiftEXTypes = JsonUtil.toListObject(giftExchangeTypeData,CampaignGiftExchangeType.class);
		if (null == campGiftEXTypes) {
			throw new JsonParseException(I18nUtil.getMessage(CmsCodeStatus.JSON_PARSE_EXCEPTION, null, lang));
		}

		final CampaignGiftMap updCampaignGiftMap = campaignGiftMapDao.get(campaignGiftMapId);
		if (null == updCampaignGiftMap) {
			throw new CampaignGiftMapNotExistException(I18nUtil.getMessage(CmsCodeStatus.CAMPAIGN_GIFT_MAP_NOT_EXIST, null, lang));
		}

		final Campaign campaign = campaignService.findById(updCampaignGiftMap.getCampaignId());

		authorizeService.checkUserMerchantPermission(userId, campaign.getMerchantId(), lang);

		if (StringUtils.isNotBlank(externalGiftId)) {
			updCampaignGiftMap.setExternalGiftId(externalGiftId);
		}

		if (null != status && status.intValue() != updCampaignGiftMap.getStatus().intValue()) {
			if (status == CampaignGiftMap.CampaignGiftMapStatus.AVAILABLE.toValue()
					|| status == CampaignGiftMap.CampaignGiftMapStatus.LOW_STOCK.toValue()
					|| status == CampaignGiftMap.CampaignGiftMapStatus.OUT_OF_STOCK.toValue()
					|| status == CampaignGiftMap.CampaignGiftMapStatus.IN_ACTIVE.toValue()) {
				//check acmpaign status
				if(updCampaignGiftMap.getStatus().intValue() == CampaignGiftMap.CampaignGiftMapStatus.IN_ACTIVE.toValue() &&
						status != CampaignGiftMap.CampaignGiftMapStatus.IN_ACTIVE.toValue()){
					if(campaign.getStatus().intValue() == Campaign.CampaignStatus.IN_ACTIVE.toValue()){
						throw new CampaignIsOverOrExpiredException();
					}
					if(campaign.getEndTime().getTime() <= DateUtil.getCurrentDate().getTime()){
						throw new CampaignIsOverOrExpiredException();
					}
				}
				updCampaignGiftMap.setStatus(status);
			}
		}
		if (null != isReservation) {
			updCampaignGiftMap.setIsReservation(isReservation);
		}
		updCampaignGiftMap.setUpdatedBy(String.valueOf(userId));

		campaignGiftMapDao.updateForVersion(updCampaignGiftMap);

		updateCampaignGiftExchangType(userId, updCampaignGiftMap, campGiftEXTypes, lang);
	}

	private void updateCampaignGiftExchangType(final Long userId, final CampaignGiftMap campGiftMap,
			final List<CampaignGiftExchangeType> campGiftEXTypes, final String lang) {
		final List<CampaignGiftExchangeType> addCampGiftEXTypes = new ArrayList<CampaignGiftExchangeType>();
		for (final CampaignGiftExchangeType campGiftEXtype : campGiftEXTypes) {
			if (null != campGiftEXtype.getId()) {

				final CampaignGiftExchangeType updGiftEXtype = campaignGiftExchangeTypeDao.get(campGiftEXtype.getId());
				if (null == updGiftEXtype) {
					throw new CampaignGiftMapEXTypeNotExistException(
							I18nUtil.getMessage(CmsCodeStatus.CAMPAIGN_GIFT_MAP_EX_TYPE_NOT_EXIST, null, lang));
				}
				if(null == campGiftEXtype.getStampQty() || campGiftEXtype.getStampQty() <= 0 ){
					throw new IllegalArgumentException("Stamp QTY "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
				}
				if(StringUtils.isBlank(campGiftEXtype.getExternalRuleCode())){
					throw new IllegalArgumentException("External rule code "+I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
				}
				
				updGiftEXtype.setStampQty(campGiftEXtype.getStampQty());
				updGiftEXtype.setExternalRuleCode(campGiftEXtype.getExternalRuleCode());

				if (null != campGiftEXtype.getCashQty() && campGiftEXtype.getCashQty() > 0) {
					updGiftEXtype.setCashQty(campGiftEXtype.getCashQty());
				}else{
					updGiftEXtype.setCashQty(0d);
				}
				if (updGiftEXtype.getType().intValue() != campGiftEXtype.getType().intValue()) {

					updGiftEXtype.setType(CampaignGiftExchangeType.Type.STAMPS.getCode());
					if (campGiftEXtype.getType().intValue() == CampaignGiftExchangeType.Type.STAMPS_CASH.getCode()) {
						if (null == campGiftEXtype.getCashQty()) {
							throw new IllegalArgumentException("Cash QTY " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
						}
						updGiftEXtype.setType(CampaignGiftExchangeType.Type.STAMPS_CASH.getCode());
					} else {
						updGiftEXtype.setCashQty(0d);
					}
				}

				if(null != campGiftEXtype.getStatus()){
					if(campGiftEXtype.getStatus().intValue() == CampaignGiftExchangeType.ExchangeStatus.IN_ACTIVE.toValue() || campGiftEXtype.getStatus().intValue() == CampaignGiftExchangeType.ExchangeStatus.ACTIVE.toValue()){
						updGiftEXtype.setStatus(campGiftEXtype.getStatus());
					}
				}

				updGiftEXtype.setUpdatedBy(String.valueOf(userId));

				campaignGiftExchangeTypeDao.updateForVersion(updGiftEXtype);
			} else {
				addCampGiftEXTypes.add(campGiftEXtype);
			}
		}
		if (campGiftEXTypes.size() > 0) {
			campGiftMap.setCreatedBy(String.valueOf(userId));
			campGiftMap.setCreatedTime(DateUtil.getCurrentDate());
			// add campaign gift map exchange type
			createGiftExchangeType(campGiftMap, addCampGiftEXTypes, lang);
		}
	}

	@Override
	@Transactional
	public void delCampaignGiftMapByIds(final String campaignGiftMapIds, final String lang) {
		final String[] idArr = campaignGiftMapIds.split(",");
		final List<Object> campaignGiftMapIdList = new ArrayList<Object>();
		for (final String idStr : idArr) {
			campaignGiftMapIdList.add(Long.parseLong(idStr));
		}

		campaignGiftExchangeTypeDao.delByCampaignGiftMapIds(campaignGiftMapIdList);
		campaignGiftMapDao.delByIds(campaignGiftMapIdList);
	}

	@Override
	@Transactional
	public void delCampaignGiftExchangeTypeByIds(final String giftEXTypeIds, final String lang) {
		final String[] idArr = giftEXTypeIds.split(",");
		final List<Object> giftEXTypeIdList = new ArrayList<Object>();
		for (final String idStr : idArr) {
			giftEXTypeIdList.add(Long.parseLong(idStr));
		}

		campaignGiftExchangeTypeDao.delByIds(giftEXTypeIdList);
	}

	@Override
	public CampaignGiftMapDetailDTO findByCampaignIdAndGiftID(final Long userId,final Long campaignId, final Long giftId, final String lang) {
		final CampaignGiftMapDetailDTO campGiftMapDetail = campaignGiftMapDao.findByCampaignIdAndGiftId(campaignId,giftId);

		if (null != campGiftMapDetail) {

			final Campaign campaign = campaignService.findById(campGiftMapDetail.getCampaignId());
			authorizeService.checkUserMerchantPermission(userId, campaign.getMerchantId(), lang);

			campGiftMapDetail.setGiftExchangeTypes(campaignGiftExchangeTypeDao.findByCampGiftMapId(campGiftMapDetail.getId()));
			return campGiftMapDetail;
		}
		return null;
	}

	@Override
	public boolean updateCampGiftMapStatus(final Campaign campaign, final Integer status) {
		return campaignGiftMapDao.updateStatusByCampId(campaign.getId(),status);
	}

	@Override
	public List<String> findGiftRelatedCampaignNames(final Long giftId, final String langCode) {
		return campaignGiftMapDao.findGiftRelatedCampaignNames(giftId,langCode);
	}
}
