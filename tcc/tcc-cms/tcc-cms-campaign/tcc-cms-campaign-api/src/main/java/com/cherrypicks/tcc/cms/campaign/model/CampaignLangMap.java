package com.cherrypicks.tcc.cms.campaign.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class CampaignLangMap extends BaseModel {
	/** 版本号 */
	private static final long serialVersionUID = -1698843720290049344L;

	/**  */
	private Long campaignId;

	/**  */
	private String name;

	/** */
	private String coverImg;

	/** */
	private String bgImg;

	/** */
	private String bgColor;

	/** */
	private String prmBannerImg;

	/**  */
	private String descr;

	/** */
	private String terms;

	/**  */
	private String langCode;

	/**
	 * 获取
	 *
	 * @return
	 */
	public Long getCampaignId() {
		return this.campaignId;
	}

	/**
	 * 设置
	 *
	 * @param campaignId
	 *
	 */
	public void setCampaignId(final Long campaignId) {
		this.campaignId = campaignId;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 设置
	 *
	 * @param name
	 *
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public String getDescr() {
		return this.descr;
	}

	/**
	 * 设置
	 *
	 * @param descr
	 *
	 */
	public void setDescr(final String descr) {
		this.descr = descr;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public String getLangCode() {
		return this.langCode;
	}

	/**
	 * 设置
	 *
	 * @param langCode
	 *
	 */
	public void setLangCode(final String langCode) {
		this.langCode = langCode;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(final String coverImg) {
		this.coverImg = coverImg;
	}

	public String getBgImg() {
		return bgImg;
	}

	public void setBgImg(final String bgImg) {
		this.bgImg = bgImg;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(final String terms) {
		this.terms = terms;
	}

	public String getPrmBannerImg() {
		return prmBannerImg;
	}

	public void setPrmBannerImg(final String prmBannerImg) {
		this.prmBannerImg = prmBannerImg;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(final String bgColor) {
		this.bgColor = bgColor;
	}

}