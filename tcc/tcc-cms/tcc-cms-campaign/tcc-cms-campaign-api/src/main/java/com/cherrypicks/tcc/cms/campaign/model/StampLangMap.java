package com.cherrypicks.tcc.cms.campaign.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class StampLangMap extends BaseModel {
	/** 版本号 */
	private static final long serialVersionUID = -3826064478159386446L;

	/**  */
	private Long stampId;

	/**  */
	private String stampImg;

	/**  */
	private String langCode;

	/**
	 * 获取
	 *
	 * @return
	 */
	public Long getStampId() {
		return this.stampId;
	}

	/**
	 * 设置
	 *
	 * @param stampId
	 *
	 */
	public void setStampId(final Long stampId) {
		this.stampId = stampId;
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

	public String getStampImg() {
		return stampImg;
	}

	public void setStampImg(String stampImg) {
		this.stampImg = stampImg;
	}

}