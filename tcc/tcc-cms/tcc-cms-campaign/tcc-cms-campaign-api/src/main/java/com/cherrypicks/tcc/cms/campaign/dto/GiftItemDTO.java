package com.cherrypicks.tcc.cms.campaign.dto;

import java.util.Date;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class GiftItemDTO extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2200304026174582717L;

	private Long id;
	private String name;
	private String merchantName;
	private Date createdTime;
	private Boolean isNotRelatedCampaign;
	private Long relatedCampaignId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Boolean getIsNotRelatedCampaign() {
		return isNotRelatedCampaign;
	}

	public void setIsNotRelatedCampaign(Boolean isNotRelatedCampaign) {
		this.isNotRelatedCampaign = isNotRelatedCampaign;
	}

	public Long getRelatedCampaignId() {
		return relatedCampaignId;
	}

	public void setRelatedCampaignId(Long relatedCampaignId) {
		this.relatedCampaignId = relatedCampaignId;
	}

}
