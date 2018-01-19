package com.cherrypicks.tcc.cms.campaign.dto;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class StampLangMapDetailDTO extends BaseObject {

	private static final long serialVersionUID = -1670868078282813379L;
	private Long id;
	private String stampImg;
	private String stampFullImg;
	private String langCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStampImg() {
		return stampImg;
	}

	public void setStampImg(String stampImg) {
		this.stampImg = stampImg;
	}

	public String getStampFullImg() {
		return stampFullImg;
	}

	public void setStampFullImg(String stampFullImg) {
		this.stampFullImg = stampFullImg;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

}
