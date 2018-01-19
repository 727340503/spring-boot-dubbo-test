package com.cherrypicks.tcc.cms.campaign.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.cherrypicks.tcc.cms.campaign.dao.CampaignDao;
import com.cherrypicks.tcc.cms.campaign.dto.CampaignDetailDTO;
import com.cherrypicks.tcc.cms.campaign.dto.CampaignItemDTO;
import com.cherrypicks.tcc.cms.campaign.dto.CampaignReportDTO;
import com.cherrypicks.tcc.cms.campaign.dto.HomePageItemDTO;
import com.cherrypicks.tcc.cms.campaign.exception.CampaignNotExistException;
import com.cherrypicks.tcc.cms.campaign.model.Campaign;
import com.cherrypicks.tcc.cms.campaign.model.CampaignGiftMap;
import com.cherrypicks.tcc.cms.campaign.model.StampCard;
import com.cherrypicks.tcc.cms.campaign.service.CampaignGiftMapService;
import com.cherrypicks.tcc.cms.campaign.service.CampaignLangMapService;
import com.cherrypicks.tcc.cms.campaign.service.CampaignService;
import com.cherrypicks.tcc.cms.campaign.service.StampCardService;
import com.cherrypicks.tcc.cms.campaign.service.StampService;
import com.cherrypicks.tcc.cms.dao.IBaseDao;
import com.cherrypicks.tcc.cms.merchant.exception.MerchantNotExistException;
import com.cherrypicks.tcc.cms.merchant.model.Merchant;
import com.cherrypicks.tcc.cms.merchant.model.MerchantConfig;
import com.cherrypicks.tcc.cms.merchant.model.MerchantLangMap;
import com.cherrypicks.tcc.cms.merchant.service.MerchantConfigService;
import com.cherrypicks.tcc.cms.merchant.service.MerchantLangMapService;
import com.cherrypicks.tcc.cms.merchant.service.MerchantService;
import com.cherrypicks.tcc.cms.service.impl.AbstractBaseService;
import com.cherrypicks.tcc.cms.system.service.IAuthorizeService;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.I18nUtil;
import com.cherrypicks.tcc.util.ImageUtil;
import com.cherrypicks.tcc.util.TimeZoneConvert;

@Service(version="1.0",timeout=5000,retries=3)
public class CampaignServiceImpl extends AbstractBaseService<Campaign> implements CampaignService {

	@Autowired
	private CampaignDao campaignDao;

	@Autowired
	private CampaignLangMapService campaignLangMapService;

	@Reference(version="1.0")
	private MerchantLangMapService merchantLangMapService;

	@Autowired
	private StampCardService stampCardService;

	@Reference(version="1.0")
	private MerchantService merchantService;

	@Reference(version="1.0")
	private IAuthorizeService authorizeService;

	@Autowired
	private CampaignGiftMapService campaignGiftMapService;

//	@Autowired
//	private HomePageService homePageService;

//	@Autowired
//	private UserService userService;

//	@Autowired
//	private UserStampHistoryService userStampHistoryService;

//	@Autowired
//	private UserStampCardDao userStampCardDao;

//	@Autowired
//	private UserReservationService userReservationService;

	@Autowired
	private StampService stampService;

	@Reference(version="1.0")
	private MerchantConfigService merchantConfigService;
	
//	@Autowired
//	private UserCouponService userCouponService;

	@Override
	@Autowired
	public void setBaseDao(final IBaseDao<Campaign> campaignDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(campaignDao);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CampaignItemDTO> findByFilter(final Map<String, Object> criteriaMap, final String sortField, final String sortType,final int... args) {
		final List<CampaignItemDTO> campItems = (List<CampaignItemDTO>) super.findByFilter(criteriaMap, sortField, sortType, args);

		if(null != campItems && campItems.size() > 0 ){
			for(final CampaignItemDTO campItem : campItems){
				if(campItem.getStatus().intValue() == Campaign.CampaignStatus.ACTIVE.toValue()){
					//update status
					final Long currentTime = DateUtil.getCurrentDate().getTime();
					if(campItem.getStartTime().getTime() > currentTime){
						campItem.setStatus(Campaign.CampaignStatus.PENDING.toValue());
					}else if(campItem.getEndTime().getTime() < currentTime){
						campItem.setStatus(Campaign.CampaignStatus.EXPIRED.toValue());
					}
				}
				//update time
				final Merchant merchant = merchantService.findById(campItem.getMerchantId());
				final String merchantTimeZone = merchant.getTimeZone();

				campItem.setStartTime(TimeZoneConvert.dateTimezoneToUI(campItem.getStartTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchantTimeZone));
				campItem.setEndTime(TimeZoneConvert.dateTimezoneToUI(campItem.getEndTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchantTimeZone));
			}
		}

		return campItems;
	}

	@Override
	@Transactional
	public CampaignDetailDTO createCampiagn(final Long userId, final Long merchantId, final Integer stampRatio,
			final String prmBannerUrl, final Date startTime, final Date endTime, final Date collStartTime,
			final Date collEndTime, final Date redeemStartTime, final Date redeemEndTime,
			final Integer inappOpen,final Integer grantStampQty,final Integer grantType, final String langData, final String lang) {
		final List<MerchantLangMap> merchantLangMapList = merchantLangMapService.findByMerchantId(merchantId);
		if (null == merchantLangMapList || merchantLangMapList.size() == 0) {
			throw new MerchantNotExistException(I18nUtil.getMessage(CmsCodeStatus.MERCHANT_NOT_EXIST, null, lang));
		}

		authorizeService.checkUserMerchantPermission(userId, merchantId, lang);

		final Merchant merchant = merchantService.findById(merchantId);
		final String timeZone = merchant.getTimeZone();

		// create campaign
		Campaign campaign = covertToNewCampaign(userId, merchantId, stampRatio, prmBannerUrl, startTime, endTime,
				collStartTime, collEndTime, redeemStartTime, redeemEndTime, inappOpen, timeZone,grantStampQty,grantType);
		campaign = campaignDao.add(campaign);

		final List<String> neddAddCampaignLangs = new ArrayList<String>();
		final List<String> needAddStampCardLangs = new ArrayList<String>();
		final List<String> needAddStampLangs = new ArrayList<String>();
		String defaultMerchantLangCode = StringUtils.EMPTY;
		for (final MerchantLangMap merchantLangMap : merchantLangMapList) {
			neddAddCampaignLangs.add(merchantLangMap.getLangCode());
			needAddStampCardLangs.add(merchantLangMap.getLangCode());
			needAddStampLangs.add(merchantLangMap.getLangCode());
			if (merchantLangMap.getIsDefault()) {
				defaultMerchantLangCode = merchantLangMap.getLangCode();
			}
		}

		// add campaign lang map
		campaignLangMapService.createCampLangMap(neddAddCampaignLangs, defaultMerchantLangCode, campaign, langData,
				lang);

		// add stamp card
		final StampCard stampCard = stampCardService.addStampCard(needAddStampCardLangs, defaultMerchantLangCode, campaign,
				langData, lang);

		//add default stamp
		stampService.addCampaignDefaultStamp(userId,campaign,lang);

		final CampaignDetailDTO campaignDetail = new CampaignDetailDTO();
		campaignDetail.setStampCardId(stampCard.getId());
		BeanUtils.copyProperties(campaign, campaignDetail);
		return campaignDetail;
	}

	private Campaign covertToNewCampaign(final Long userId, final Long merchantId, final Integer stampRatio,
			final String prmBannerUrl, final Date startTime, final Date endTime, final Date collStartTime,
			final Date collEndTime, final Date redeemStartTime, final Date redeemEndTime,
			final Integer inappOpen, final String timeZone,final Integer grantStampQty,final Integer grantType) {

		final Campaign campaign = new Campaign();
		campaign.setCreatedBy(String.valueOf(userId));
		campaign.setCreatedTime(DateUtil.getCurrentDate());
		campaign.setIsDeleted(false);
		campaign.setMerchantId(merchantId);
		campaign.setStampRatio(stampRatio);
		campaign.setStatus(Campaign.CampaignStatus.ACTIVE.toValue());
		campaign.setGrantType(0);

		campaign.setStartTime(TimeZoneConvert.dateTimezoneToDB(startTime, timeZone, TimeZoneConvert.DEFAULT_TIMEZONE));
		campaign.setEndTime(TimeZoneConvert.dateTimezoneToDB(endTime, timeZone, TimeZoneConvert.DEFAULT_TIMEZONE));
		campaign.setCollStartTime(TimeZoneConvert.dateTimezoneToDB(collStartTime, timeZone, TimeZoneConvert.DEFAULT_TIMEZONE));
		campaign.setCollEndTime(TimeZoneConvert.dateTimezoneToDB(collEndTime, timeZone, TimeZoneConvert.DEFAULT_TIMEZONE));
		campaign.setRedeemStartTime(TimeZoneConvert.dateTimezoneToDB(redeemStartTime, timeZone, TimeZoneConvert.DEFAULT_TIMEZONE));
		campaign.setRedeemEndTime(TimeZoneConvert.dateTimezoneToDB(redeemEndTime, timeZone, TimeZoneConvert.DEFAULT_TIMEZONE));

		if (StringUtils.isNotBlank(prmBannerUrl)) {
			campaign.setPrmBannerUrl(prmBannerUrl);
		}
		if(null != inappOpen){
			campaign.setInappOpen(inappOpen);
		}
		if(null != grantStampQty){
			campaign.setGrantStampQty(grantStampQty);
		}
		if(null != grantType){
			if(grantType.intValue() == Campaign.GrantType.NEW_USER.toValue()){
				campaign.setGrantType(grantType);
			}
		}
		return campaign;
	}

	@Override
	@Transactional
	public Campaign updateCampaign(final Long userId, final Long campaignId, final Integer stampRatio, final String prmBannerUrl,
			final Date startTime, final Date endTime, final Date collStartTime, final Date collEndTime,
			final Date redeemStartTime, final Date redeemEndTime, final Integer status,
			final Integer inappOpen,final Integer grantStampQty,final Integer grantType,final String langData, final String lang) {

		Campaign campaign = campaignDao.get(campaignId);
		if (null == campaign) {
			throw new CampaignNotExistException(I18nUtil.getMessage(CmsCodeStatus.CAMPAIGN_NOT_EXIST, null, lang));
		}

		authorizeService.checkUserMerchantPermission(userId, campaign.getMerchantId(), lang);

		final Merchant merchant = merchantService.findById(campaign.getMerchantId());
		final String merchantTimeZone = merchant.getTimeZone();

		campaign = covertToUpdateCampaign(campaign, userId, campaignId, stampRatio, prmBannerUrl, startTime, endTime,
				collStartTime, collEndTime, redeemStartTime, redeemEndTime, status, inappOpen, merchantTimeZone,grantStampQty,grantType);
		campaignDao.updateForVersion(campaign);

		if (StringUtils.isNotBlank(langData)) {
			campaignLangMapService.updateCampLangMap(campaign, langData, lang);
			stampCardService.updateStampCard(campaign, langData, lang);
		}

		//update merchat home page cache
//		homePageService.updateMerchantHomePageCacheByRefId(userId, campaign.getMerchantId(), campaign.getId(), HomePage.HomePageType.CAMPAIGN.toValue(), lang);

		return campaign;
	}

	private Campaign covertToUpdateCampaign(final Campaign campaign, final Long userId, final Long campaignId, final Integer stampRatio,
			final String prmBannerUrl, final Date startTime, final Date endTime, final Date collStartTime,
			final Date collEndTime, final Date redeemStartTime, final Date redeemEndTime,
			final Integer status, final Integer inappOpen, final String merchantTimeZone,final Integer grantStampQty,final Integer grantType) {
		campaign.setUpdatedBy(String.valueOf(userId));

		campaign.setStartTime(TimeZoneConvert.dateTimezoneToDB(startTime, merchantTimeZone, TimeZoneConvert.DEFAULT_TIMEZONE));
		campaign.setEndTime(TimeZoneConvert.dateTimezoneToDB(endTime, merchantTimeZone, TimeZoneConvert.DEFAULT_TIMEZONE));
		campaign.setCollStartTime(TimeZoneConvert.dateTimezoneToDB(collStartTime, merchantTimeZone, TimeZoneConvert.DEFAULT_TIMEZONE));
		campaign.setCollEndTime(TimeZoneConvert.dateTimezoneToDB(collEndTime, merchantTimeZone, TimeZoneConvert.DEFAULT_TIMEZONE));
		campaign.setRedeemStartTime(TimeZoneConvert.dateTimezoneToDB(redeemStartTime, merchantTimeZone, TimeZoneConvert.DEFAULT_TIMEZONE));
		campaign.setRedeemEndTime(TimeZoneConvert.dateTimezoneToDB(redeemEndTime, merchantTimeZone, TimeZoneConvert.DEFAULT_TIMEZONE));
		campaign.setPrmBannerUrl(prmBannerUrl);

		if (null != status) {
			if(status.intValue() == Campaign.CampaignStatus.ACTIVE.toValue() || status.intValue() == Campaign.CampaignStatus.IN_ACTIVE.toValue()){
				if(campaign.getStatus().intValue() != status.intValue()){
					if(status.intValue() == Campaign.CampaignStatus.ACTIVE.toValue()){
						campaignGiftMapService.updateCampGiftMapStatus(campaign,CampaignGiftMap.CampaignGiftMapStatus.AVAILABLE.toValue());
					}else{
						campaignGiftMapService.updateCampGiftMapStatus(campaign,CampaignGiftMap.CampaignGiftMapStatus.IN_ACTIVE.toValue());
//						final long count = homePageService.findCountByReftId(campaignId, HomePage.HomePageType.CAMPAIGN.toValue());
//						if(count > 0 ){
//							throw new UpdateRecordStatusException();
//						}
					}
				}
				campaign.setStatus(status);
			}else if(status.intValue() == Campaign.CampaignStatus.PENDING.toValue() || status.intValue() == Campaign.CampaignStatus.EXPIRED.toValue()){
				if(campaign.getStatus().intValue() == Campaign.CampaignStatus.IN_ACTIVE.toValue()){
					campaignGiftMapService.updateCampGiftMapStatus(campaign,CampaignGiftMap.CampaignGiftMapStatus.AVAILABLE.toValue());
				}
				campaign.setStatus(Campaign.CampaignStatus.ACTIVE.toValue());
			}
		}
		if (null != stampRatio) {
			campaign.setStampRatio(stampRatio);
		}
		if(null != inappOpen){
			campaign.setInappOpen(inappOpen);
		}
		if(null != grantType){
			campaign.setGrantType(grantType);
		}
		if(null != grantStampQty){
			campaign.setGrantStampQty(grantStampQty);
		}

		return campaign;
	}

	@Override
	public CampaignDetailDTO findDetailById(final Long userId,final Long campaignId, final String lang) {
		final Campaign campaign = campaignDao.get(campaignId);
		if (null == campaign) {
			throw new CampaignNotExistException(I18nUtil.getMessage(CmsCodeStatus.CAMPAIGN_NOT_EXIST, null, lang));
		}

		authorizeService.checkUserMerchantPermission(userId, campaign.getMerchantId(), lang);

		final CampaignDetailDTO result = new CampaignDetailDTO();
		BeanUtils.copyProperties(campaign, result);

		final Merchant merchant = merchantService.findById(campaign.getMerchantId());
		final String merchatTimeZone = merchant.getTimeZone();

		result.setStartTime(TimeZoneConvert.dateTimezoneToUI(campaign.getStartTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchatTimeZone));
		result.setEndTime(TimeZoneConvert.dateTimezoneToUI(campaign.getEndTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchatTimeZone));
		result.setCollStartTime(TimeZoneConvert.dateTimezoneToUI(campaign.getCollStartTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchatTimeZone));
		result.setCollEndTime(TimeZoneConvert.dateTimezoneToUI(campaign.getCollEndTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchatTimeZone));
		result.setRedeemStartTime(TimeZoneConvert.dateTimezoneToUI(campaign.getRedeemStartTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchatTimeZone));
		result.setRedeemEndTime(TimeZoneConvert.dateTimezoneToUI(campaign.getRedeemEndTime(), TimeZoneConvert.DEFAULT_TIMEZONE, merchatTimeZone));

		if(campaign.getStatus().intValue() == Campaign.CampaignStatus.ACTIVE.toValue()){
			//update status
			final Long currentTime = DateUtil.getCurrentDate().getTime();
			if(campaign.getStartTime().getTime() > currentTime){
				result.setStatus(Campaign.CampaignStatus.PENDING.toValue());
			}else if(campaign.getEndTime().getTime() < currentTime){
				result.setStatus(Campaign.CampaignStatus.EXPIRED.toValue());
			}
		}

		result.setCampaignLangMaps(campaignLangMapService.findCampLangMapDetailByCampaignId(campaign.getMerchantId(),campaignId));
		return result;
	}

	@Override
	public List<HomePageItemDTO> findHomePageCampaignList(final Long userId, final Long merchantId, final Integer status, final String lang) {

		authorizeService.checkUserMerchantPermission(userId, merchantId, lang);

		final List<HomePageItemDTO> homePageCampaigns = campaignDao.findHomePageCampaignList(merchantId,status);
		if(null != homePageCampaigns && homePageCampaigns.size() > 0 ){


			final MerchantConfig merchantConfig = merchantConfigService.findByMerchantId(merchantId);

			for(final HomePageItemDTO homePageCampaign : homePageCampaigns){
				homePageCampaign.setImg(ImageUtil.getImageFullPath(merchantConfig.getImgDomain(), homePageCampaign.getImg()));
				if(homePageCampaign.getStatus().intValue() == Campaign.CampaignStatus.ACTIVE.toValue()){
					//update status
					final Long currentTime = DateUtil.getCurrentDate().getTime();
					if(homePageCampaign.getStartTime().getTime() > currentTime){
						homePageCampaign.setStatus(Campaign.CampaignStatus.PENDING.toValue());
					}else if(homePageCampaign.getEndTime().getTime() < currentTime){
						homePageCampaign.setStatus(Campaign.CampaignStatus.EXPIRED.toValue());
					}
				}
			}
		}

		return homePageCampaigns;
	}

	@Override
	public List<CampaignItemDTO> findByCustomerId(final Long userId, final Long customerId, final String lang) {
//		final User user = userService.findById(customerId);
//		if(null == user){
//			return null;
//		}
//
//		authorizeService.checkUserMerchantPermission(userId, user.getMerchantId(), lang);

		return campaignDao.findByCustomerId(customerId);
	}

	@Override
	public CampaignReportDTO getCampaignReport(final Long campaignId) {

//		final Long totalStamps = userStampHistoryService.findCampaignTotalIssuanceStamps(campaignId);
//		final Long totalUsedStamps = userStampHistoryService.findCampaignTotalUsedStamps(campaignId);
//		final Long totalRedemptionCount = userStampHistoryService.findCampaignTotalRedemptionCount(campaignId);
//		final Long totalReservationCount = userReservationService.findCampaignReservationCount(campaignId);
//		final Long totalRevokeStamps = userStampHistoryService.findCampaignTotalRevokeStamps(campaignId);
//
//		final Long totalCampaignUserCount = userStampCardDao.findCampaignTotalUserCount(campaignId);
//		
//		final Long totalCollectCoupon = userCouponService.findTotalCountByCampaignId(campaignId,null);
//		final Long totalActiveCoupon = userCouponService.findTotalCountByCampaignId(campaignId,UserCoupon.UserCouponStatus.ACTIVE.toValue());
//		final Long totalInactiveCoupon = userCouponService.findTotalCountByCampaignId(campaignId, UserCoupon.UserCouponStatus.INACTIVE.toValue());
//		final Long totalRedeemCoupon = userCouponService.findTotalCountByCampaignId(campaignId,UserCoupon.UserCouponStatus.REDEEMED.toValue());
//		final Long totalExpiredCoupon = userCouponService.findTotalCountByCampaignId(campaignId,UserCoupon.UserCouponStatus.EXPIRED.toValue());

		final CampaignReportDTO campaignReport = new CampaignReportDTO();
//		campaignReport.setTotalStamps(totalStamps - totalRevokeStamps);
//		campaignReport.setTotalUsedStamps(totalUsedStamps);
//		campaignReport.setTotalRedemptionCount(totalRedemptionCount);
//		campaignReport.setTotalReservationCount(totalReservationCount);
//		campaignReport.setTotalCollectCoupon(totalCollectCoupon);
//		campaignReport.setTotalActiveCoupon(totalActiveCoupon);
//		campaignReport.setTotalInactiveCoupon(totalInactiveCoupon);
//		campaignReport.setTotalRedeemCoupon(totalRedeemCoupon);
//		campaignReport.setTotalExpiredCoupon(totalExpiredCoupon);
//		if(totalCampaignUserCount > 0){
//			final BigDecimal totalUsedStampsCount = new BigDecimal(totalUsedStamps);
//			final BigDecimal totalRedemptionCountBig = new BigDecimal(totalRedemptionCount);
//			final BigDecimal totalStampsCount = new BigDecimal(totalStamps - totalRevokeStamps);
//			campaignReport.setAverageUsedStamps(totalUsedStampsCount.divide(new BigDecimal(totalCampaignUserCount),2,BigDecimal.ROUND_HALF_UP).doubleValue());
//			campaignReport.setAverageRedemptionCount(totalRedemptionCountBig.divide(new BigDecimal(totalCampaignUserCount),2,BigDecimal.ROUND_HALF_UP).doubleValue());
//			campaignReport.setAverageCollectStamps(totalStampsCount.divide(new BigDecimal(totalCampaignUserCount),2,BigDecimal.ROUND_HALF_UP).doubleValue());
//		}

		return campaignReport;
	}

}
