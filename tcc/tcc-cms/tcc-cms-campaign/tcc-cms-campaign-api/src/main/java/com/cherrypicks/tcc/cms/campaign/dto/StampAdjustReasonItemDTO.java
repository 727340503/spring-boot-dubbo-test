package com.cherrypicks.tcc.cms.campaign.dto;

import java.util.Date;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class StampAdjustReasonItemDTO extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4228209221403757041L;

	private Long id;
	private String content;
	private Date createdTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

}
