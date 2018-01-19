package com.cherrypicks.tcc.cms.campaign.dto;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class GiftLangMapDTO extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2200304026174582717L;

	private Long id;
	private String name;
	private String image;
	private String fullImage;
	private String descr;
	private String langCode;
	private String relatedCampaign;

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

	public String getFullImage() {
		return fullImage;
	}

	public void setFullImage(String fullImage) {
		this.fullImage = fullImage;
	}

	public String getRelatedCampaign() {
		return relatedCampaign;
	}

	public void setRelatedCampaign(String relatedCampaign) {
		this.relatedCampaign = relatedCampaign;
	}

}
