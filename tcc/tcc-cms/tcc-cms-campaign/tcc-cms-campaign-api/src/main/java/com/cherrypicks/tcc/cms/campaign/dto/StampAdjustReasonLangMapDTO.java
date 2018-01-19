package com.cherrypicks.tcc.cms.campaign.dto;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class StampAdjustReasonLangMapDTO extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4228209221403757041L;

	private Long id;
	private String title;
	private String content;
	private String langCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
