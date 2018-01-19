package com.cherrypicks.tcc.cms.campaign.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class CampaignGiftMap extends BaseModel {

	/**
	 *
	 */
	private static final long serialVersionUID = -669600113739347287L;

	public enum CampaignGiftMapStatus {
		COMING_SOON(1), AVAILABLE(2), LOW_STOCK(3), OUT_OF_STOCK(4), EXPIRED(5), IN_ACTIVE(6);

		private final int value;

		CampaignGiftMapStatus(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

	private Long campaignId;

	private Long giftId;

	/**  */
	private String externalGiftId;

	private Integer status;

	private Integer isReservation;

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(final Long campaignId) {
		this.campaignId = campaignId;
	}

	public Long getGiftId() {
		return giftId;
	}

	public void setGiftId(final Long giftId) {
		this.giftId = giftId;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置
	 *
	 * @param status
	 *
	 */
	public void setStatus(final Integer status) {
		this.status = status;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public String getExternalGiftId() {
		return this.externalGiftId;
	}

	/**
	 * 设置
	 *
	 * @param externalGiftId
	 *
	 */
	public void setExternalGiftId(final String externalGiftId) {
		this.externalGiftId = externalGiftId;
	}

	public Integer getIsReservation() {
		return isReservation;
	}

	public void setIsReservation(final Integer isReservation) {
		this.isReservation = isReservation;
	}

}