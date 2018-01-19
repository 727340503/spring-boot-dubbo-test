package com.cherrypicks.tcc.cms.campaign.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class StampAdjustReasonLangMap extends BaseModel {
	/** 版本号 */
	private static final long serialVersionUID = 4696593090687497538L;

	private Long stampAdjustReasonId;
	private String title;
	private String content;
	private String langCode;

	public Long getStampAdjustReasonId() {
		return stampAdjustReasonId;
	}

	public void setStampAdjustReasonId(Long stampAdjustReasonId) {
		this.stampAdjustReasonId = stampAdjustReasonId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

}