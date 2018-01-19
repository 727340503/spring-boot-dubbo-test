package com.cherrypicks.tcc.cms.merchant.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class StoreLangMap extends BaseModel {
	/** 版本号 */
	private static final long serialVersionUID = 151024810227043367L;

	/**  */
	private Long storeId;

	/**  */
	private String image;

	/**  */
	private String name;

	/**  */
	private String address;

	private String businessInfo;

	/**  */
	private String langCode;

	/**
	 * 获取
	 *
	 * @return
	 */
	public Long getStoreId() {
		return this.storeId;
	}

	/**
	 * 设置
	 *
	 * @param storeId
	 *
	 */
	public void setStoreId(final Long storeId) {
		this.storeId = storeId;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public String getImage() {
		return this.image;
	}

	/**
	 * 设置
	 *
	 * @param image
	 *
	 */
	public void setImage(final String image) {
		this.image = image;
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
	public String getAddress() {
		return this.address;
	}

	/**
	 * 设置
	 *
	 * @param address
	 *
	 */
	public void setAddress(final String address) {
		this.address = address;
	}

	public String getBusinessInfo() {
		return businessInfo;
	}

	public void setBusinessInfo(String businessInfo) {
		this.businessInfo = businessInfo;
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
}