package com.cherrypicks.tcc.cms.campaign.model;

import java.util.Date;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class Campaign extends BaseModel {
	/** 版本号 */
	private static final long serialVersionUID = 7870077668542239176L;

	public enum CampaignStatus {
		PENDING(1), ACTIVE(2), EXPIRED(3), IN_ACTIVE(4);

		private final int value;

		CampaignStatus(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

	public enum GrantType {
		ALL_USER(0),NEW_USER(1),NOT_PRESENTED(2);

		private final int value;

		GrantType(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

	/**  */
	private Long merchantId;

	/**  */

	private Date startTime;

	/**  */

	private Date endTime;

	private Date collStartTime;
	private Date collEndTime;
	private Date redeemStartTime;
	private Date redeemEndTime;

	/**
	 * 0 - inactive (invisible) 1-active 2-collect stamps 3-redemption 4-collect
	 * stamps ONLY 5-redemption ONLY
	 */
	private Integer status;

	private Integer stampRatio;

	private String prmBannerUrl;

	private Integer inappOpen;

	private Integer grantStampQty;
	private Integer grantType;

	/**
	 * 获取
	 *
	 * @return
	 */
	public Long getMerchantId() {
		return this.merchantId;
	}

	/**
	 * 设置
	 *
	 * @param merchantId
	 *
	 */
	public void setMerchantId(final Long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public Date getStartTime() {
		return this.startTime;
	}

	/**
	 * 设置
	 *
	 * @param startTime
	 *
	 */
	public void setStartTime(final Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public Date getEndTime() {
		return this.endTime;
	}

	/**
	 * 设置
	 *
	 * @param endTime
	 *
	 */
	public void setEndTime(final Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 获取0 - inactive (invisible) 1-active 2-collect stamps 3-redemption
	 * 4-collect stamps ONLY 5-redemption ONLY
	 *
	 * @return 0 - inactive (invisible) 1-active 2-collect stamps 3-redemption
	 *         4-collect stamps ONLY 5-redemption ONLY
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 设置0 - inactive (invisible) 1-active 2-collect stamps 3-redemption
	 * 4-collect stamps ONLY 5-redemption ONLY
	 *
	 * @param status
	 *            0 - inactive (invisible) 1-active 2-collect stamps
	 *            3-redemption 4-collect stamps ONLY 5-redemption ONLY
	 */
	public void setStatus(final Integer status) {
		this.status = status;
	}

	public Date getCollStartTime() {
		return collStartTime;
	}

	public void setCollStartTime(final Date collStartTime) {
		this.collStartTime = collStartTime;
	}

	public Date getCollEndTime() {
		return collEndTime;
	}

	public void setCollEndTime(final Date collEndTime) {
		this.collEndTime = collEndTime;
	}

	public Date getRedeemStartTime() {
		return redeemStartTime;
	}

	public void setRedeemStartTime(final Date redeemStartTime) {
		this.redeemStartTime = redeemStartTime;
	}

	public Date getRedeemEndTime() {
		return redeemEndTime;
	}

	public void setRedeemEndTime(final Date redeemEndTime) {
		this.redeemEndTime = redeemEndTime;
	}

	public Integer getStampRatio() {
		return stampRatio;
	}

	public void setStampRatio(final Integer stampRatio) {
		this.stampRatio = stampRatio;
	}

	public String getPrmBannerUrl() {
		return prmBannerUrl;
	}

	public void setPrmBannerUrl(final String prmBannerUrl) {
		this.prmBannerUrl = prmBannerUrl;
	}

	public Integer getInappOpen() {
		return inappOpen;
	}

	public void setInappOpen(final Integer inappOpen) {
		this.inappOpen = inappOpen;
	}

	public Integer getGrantStampQty() {
		return grantStampQty;
	}

	public void setGrantStampQty(final Integer grantStampQty) {
		this.grantStampQty = grantStampQty;
	}

	public Integer getGrantType() {
		return grantType;
	}

	public void setGrantType(final Integer grantType) {
		this.grantType = grantType;
	}

}