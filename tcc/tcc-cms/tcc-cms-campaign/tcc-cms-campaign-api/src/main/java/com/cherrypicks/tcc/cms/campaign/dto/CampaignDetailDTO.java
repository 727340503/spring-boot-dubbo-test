package com.cherrypicks.tcc.cms.campaign.dto;

import java.util.Date;
import java.util.List;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class CampaignDetailDTO extends BaseObject {

	private static final long serialVersionUID = 3273544677238936729L;

	private Long id;
	private Long merchantId;
	private String merchantName;
	private Long stampCardId;
	private Date startTime;
	private Date endTime;
	private Date collStartTime;
	private Date collEndTime;
	private Date redeemStartTime;
	private Date redeemEndTime;
//	private Date reservationExpiredTime;
	private Integer status;
	private Integer stampRatio;
	private String prmBannerUrl;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;
	private Boolean isDeleted = false;
	private Integer inappOpen;
	private Integer grantStampQty;
	private Integer grantType;
	private List<CampaignLangMapDetailDTO> campaignLangMaps;

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

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public Long getStampCardId() {
		return stampCardId;
	}

	public void setStampCardId(Long stampCardId) {
		this.stampCardId = stampCardId;
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

	public Date getCollStartTime() {
		return collStartTime;
	}

	public void setCollStartTime(Date collStartTime) {
		this.collStartTime = collStartTime;
	}

	public Date getCollEndTime() {
		return collEndTime;
	}

	public void setCollEndTime(Date collEndTime) {
		this.collEndTime = collEndTime;
	}

	public Date getRedeemStartTime() {
		return redeemStartTime;
	}

	public void setRedeemStartTime(Date redeemStartTime) {
		this.redeemStartTime = redeemStartTime;
	}

	public Date getRedeemEndTime() {
		return redeemEndTime;
	}

	public void setRedeemEndTime(Date redeemEndTime) {
		this.redeemEndTime = redeemEndTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStampRatio() {
		return stampRatio;
	}

	public void setStampRatio(Integer stampRatio) {
		this.stampRatio = stampRatio;
	}

	public String getPrmBannerUrl() {
		return prmBannerUrl;
	}

	public void setPrmBannerUrl(String prmBannerUrl) {
		this.prmBannerUrl = prmBannerUrl;
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

	public Integer getInappOpen() {
		return inappOpen;
	}

	public void setInappOpen(Integer inappOpen) {
		this.inappOpen = inappOpen;
	}

	public List<CampaignLangMapDetailDTO> getCampaignLangMaps() {
		return campaignLangMaps;
	}

	public void setCampaignLangMaps(List<CampaignLangMapDetailDTO> campaignLangMaps) {
		this.campaignLangMaps = campaignLangMaps;
	}

	public Integer getGrantStampQty() {
		return grantStampQty;
	}

	public void setGrantStampQty(Integer grantStampQty) {
		this.grantStampQty = grantStampQty;
	}

	public Integer getGrantType() {
		return grantType;
	}

	public void setGrantType(Integer grantType) {
		this.grantType = grantType;
	}

}
