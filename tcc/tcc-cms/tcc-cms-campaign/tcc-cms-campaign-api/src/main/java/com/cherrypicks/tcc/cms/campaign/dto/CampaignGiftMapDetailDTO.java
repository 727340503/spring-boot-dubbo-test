package com.cherrypicks.tcc.cms.campaign.dto;

import java.util.Date;
import java.util.List;

import com.cherrypicks.tcc.cms.campaign.model.CampaignGiftExchangeType;
import com.cherrypicks.tcc.cms.dto.BaseObject;

public class CampaignGiftMapDetailDTO extends BaseObject {

	private static final long serialVersionUID = 2182329642252007174L;

	private Long id;
	private Long campaignId;
	private Long giftId;
	private String externalGiftId;
	private Integer status;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;
	private Boolean isDeleted = false;
	private Integer isReservation;
	private List<CampaignGiftExchangeType> giftExchangeTypes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	public Long getGiftId() {
		return giftId;
	}

	public void setGiftId(Long giftId) {
		this.giftId = giftId;
	}

	public String getExternalGiftId() {
		return externalGiftId;
	}

	public void setExternalGiftId(String externalGiftId) {
		this.externalGiftId = externalGiftId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getIsReservation() {
		return isReservation;
	}

	public void setIsReservation(Integer isReservation) {
		this.isReservation = isReservation;
	}

	public List<CampaignGiftExchangeType> getGiftExchangeTypes() {
		return giftExchangeTypes;
	}

	public void setGiftExchangeTypes(List<CampaignGiftExchangeType> giftExchangeTypes) {
		this.giftExchangeTypes = giftExchangeTypes;
	}

}
