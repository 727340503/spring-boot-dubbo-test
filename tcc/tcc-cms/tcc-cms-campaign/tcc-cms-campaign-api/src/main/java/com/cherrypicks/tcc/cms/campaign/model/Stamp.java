package com.cherrypicks.tcc.cms.campaign.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class Stamp extends BaseModel {
	/** 版本号 */
	private static final long serialVersionUID = 4167662183305331221L;

	/**  */
	private Long stampCardId;

	public Long getStampCardId() {
		return stampCardId;
	}

	public void setStampCardId(final Long stampCardId) {
		this.stampCardId = stampCardId;
	}

}