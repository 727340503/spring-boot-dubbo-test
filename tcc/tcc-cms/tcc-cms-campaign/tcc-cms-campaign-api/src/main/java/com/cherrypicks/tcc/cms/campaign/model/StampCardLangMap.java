package com.cherrypicks.tcc.cms.campaign.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class StampCardLangMap extends BaseModel {
	/** 版本号 */
	private static final long serialVersionUID = -3826064478159386446L;

	/**  */
	private Long stampCardId;

	/**  */
	private String name;

	/**  */
	private String coverImg;

	/**  */
	private String descr;

	/**  */
	private String terms;

	/**  */
	private String langCode;

	public Long getStampCardId() {
		return stampCardId;
	}

	public void setStampCardId(Long stampCardId) {
		this.stampCardId = stampCardId;
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
	public String getTerms() {
		return this.terms;
	}

	/**
	 * 设置
	 *
	 * @param terms
	 *
	 */
	public void setTerms(final String terms) {
		this.terms = terms;
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

	/**
	 * 获取
	 *
	 * @return
	 */
	public String getCoverImg() {
		return coverImg;
	}

	/**
	 * 设置
	 *
	 * @param coverImage
	 *
	 */
	public void setCoverImg(final String coverImg) {
		this.coverImg = coverImg;
	}

}