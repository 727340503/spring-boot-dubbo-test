package com.cherrypicks.tcc.cms.campaign.service;

import java.util.Date;
import java.util.List;

import com.cherrypicks.tcc.cms.campaign.dto.CampaignDetailDTO;
import com.cherrypicks.tcc.cms.campaign.dto.CampaignItemDTO;
import com.cherrypicks.tcc.cms.campaign.dto.CampaignReportDTO;
import com.cherrypicks.tcc.cms.campaign.dto.HomePageItemDTO;
import com.cherrypicks.tcc.cms.campaign.model.Campaign;
import com.cherrypicks.tcc.cms.service.IBaseService;

public interface CampaignService extends IBaseService<Campaign>{

//	CampaignDTO createCampiagn(final Long userId,final Long merchantId,final Integer stampRatio,final Integer redeemRule,final String prmBannerUrl,
//			final Date startTime,final Date endTime,final Date collStartTime, final Date collEndTime,final Date redeemStartTime,final Date redeemEndTime,
//			final Integer status, final String name, final String bannerImg,final String bgImg,final String prmBannerImg,final String descr,final String terms,final String langCode,final String lang);
	CampaignDetailDTO createCampiagn(final Long userId,final Long merchantId,final Integer stampRatio,final String prmBannerUrl,
			final Date startTime,final Date endTime,final Date collStartTime, final Date collEndTime,final Date redeemStartTime,final Date redeemEndTime,
			final Integer inappOpen, final Integer grantStampQty,final Integer grantType,final String langData,final String lang);

	Campaign updateCampaign(final Long userId,final Long campaignId, final Integer stampRatio,final String prmBannerUrl,
			final Date startTime,final Date endTime,final Date collStartTime, final Date collEndTime,final Date redeemStartTime,final Date redeemEndTime,
			final Integer status, final Integer inappOpen, final Integer grantStampQty,final Integer grantType,final String langData, final String lang);

	CampaignDetailDTO findDetailById(final Long userId,final Long campaignId, final String lang);

	List<HomePageItemDTO> findHomePageCampaignList(final Long userId, final Long merchantId, final Integer status, final String lang);

	List<CampaignItemDTO> findByCustomerId(final Long userId, final Long customerId, final String lang);

	CampaignReportDTO getCampaignReport(final Long campaignId);

}
