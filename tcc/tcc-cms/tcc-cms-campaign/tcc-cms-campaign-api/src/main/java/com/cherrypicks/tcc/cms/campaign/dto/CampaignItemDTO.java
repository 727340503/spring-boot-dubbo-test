package com.cherrypicks.tcc.cms.campaign.dto;

import java.util.Date;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class CampaignItemDTO extends BaseObject {

	private static final long serialVersionUID = 3273544677238936729L;

	private Long id;
	private Long merchantId;
	private Integer status;
	private String name;
	private Date startTime;
	private Date endTime;
	private String prmBannerImg;
	private String prmBannerUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getPrmBannerImg() {
		return prmBannerImg;
	}

	public void setPrmBannerImg(String prmBannerImg) {
		this.prmBannerImg = prmBannerImg;
	}

	public String getPrmBannerUrl() {
		return prmBannerUrl;
	}

	public void setPrmBannerUrl(String prmBannerUrl) {
		this.prmBannerUrl = prmBannerUrl;
	}

}
