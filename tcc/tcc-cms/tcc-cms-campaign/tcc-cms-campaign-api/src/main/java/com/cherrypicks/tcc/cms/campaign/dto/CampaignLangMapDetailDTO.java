package com.cherrypicks.tcc.cms.campaign.dto;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class CampaignLangMapDetailDTO extends BaseObject {

	private static final long serialVersionUID = 3273544677238936729L;

	private Long id;
	private String name;
	private String coverImg;
	private String bgImg;
	private String bgColor;
	private String prmBannerImg;
	private String descr;
	private String terms;
	private String langCode;

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

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public String getBgImg() {
		return bgImg;
	}

	public void setBgImg(String bgImg) {
		this.bgImg = bgImg;
	}

	public String getPrmBannerImg() {
		return prmBannerImg;
	}

	public void setPrmBannerImg(String prmBannerImg) {
		this.prmBannerImg = prmBannerImg;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

}
