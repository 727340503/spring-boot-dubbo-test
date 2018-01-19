package com.cherrypicks.tcc.cms.merchant.dto;

import java.util.Date;
import java.util.List;

import com.cherrypicks.tcc.cms.dto.BaseObject;
import com.cherrypicks.tcc.cms.merchant.model.MerchantAreaLangMap;


public class MerchantAreaDTO extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2200304026174582717L;

	private Long id;
	private Long merchantId;
	private Date createdTime;
	private List<MerchantAreaLangMap> merchantAreaLangMaps;

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

	public List<MerchantAreaLangMap> getMerchantAreaLangMaps() {
		return merchantAreaLangMaps;
	}

	public void setMerchantAreaLangMaps(List<MerchantAreaLangMap> merchantAreaLangMaps) {
		this.merchantAreaLangMaps = merchantAreaLangMaps;
	}

}
