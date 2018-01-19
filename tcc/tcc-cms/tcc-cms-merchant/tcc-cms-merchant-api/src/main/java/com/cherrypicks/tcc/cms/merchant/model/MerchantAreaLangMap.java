package com.cherrypicks.tcc.cms.merchant.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class MerchantAreaLangMap extends BaseModel {
	/** 版本号 */
	private static final long serialVersionUID = 4696593090687497538L;

	private Long merchantAreaId;
	private String areaInfo;
	private String descr;
	private String langCode;

	public Long getMerchantAreaId() {
		return merchantAreaId;
	}

	public void setMerchantAreaId(Long merchantAreaId) {
		this.merchantAreaId = merchantAreaId;
	}

	public String getAreaInfo() {
		return areaInfo;
	}

	public void setAreaInfo(String areaInfo) {
		this.areaInfo = areaInfo;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

}