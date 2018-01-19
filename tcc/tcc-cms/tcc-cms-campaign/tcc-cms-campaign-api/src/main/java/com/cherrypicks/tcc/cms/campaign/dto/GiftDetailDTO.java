package com.cherrypicks.tcc.cms.campaign.dto;

import java.util.Date;
import java.util.List;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class GiftDetailDTO extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2200304026174582717L;

	private Long id;
	private Long merchantId;
	private Date createdTime;
	private List<GiftLangMapDTO> giftLangMaps;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public List<GiftLangMapDTO> getGiftLangMaps() {
		return giftLangMaps;
	}

	public void setGiftLangMaps(List<GiftLangMapDTO> giftLangMaps) {
		this.giftLangMaps = giftLangMaps;
	}

}
