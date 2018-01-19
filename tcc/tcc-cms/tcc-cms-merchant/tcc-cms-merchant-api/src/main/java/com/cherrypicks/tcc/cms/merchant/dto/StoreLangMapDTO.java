package com.cherrypicks.tcc.cms.merchant.dto;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class StoreLangMapDTO extends BaseObject {

	private static final long serialVersionUID = -2482879148474361543L;
	private Long id;
	private String name;
	private String image;
	private String fullImage;
	private String address;
	private String langCode;
	private String merchantName;
	private String merchantAreaInfo;
	private String businessInfo;

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getFullImage() {
		return fullImage;
	}

	public void setFullImage(String fullImage) {
		this.fullImage = fullImage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantAreaInfo() {
		return merchantAreaInfo;
	}

	public void setMerchantAreaInfo(String merchantAreaInfo) {
		this.merchantAreaInfo = merchantAreaInfo;
	}

	public String getBusinessInfo() {
		return businessInfo;
	}

	public void setBusinessInfo(String businessInfo) {
		this.businessInfo = businessInfo;
	}

}
