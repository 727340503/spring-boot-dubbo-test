package com.cherrypicks.tcc.cms.campaign.dto;

import java.util.List;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class StampAdjustReasonDetailDTO extends BaseObject {

	private static final long serialVersionUID = 7809045276369127236L;

	private Long id;
	private Long merchantId;
	private List<StampAdjustReasonLangMapDTO> stampAdjustReasonLangMaps;

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

	public List<StampAdjustReasonLangMapDTO> getStampAdjustReasonLangMaps() {
		return stampAdjustReasonLangMaps;
	}

	public void setStampAdjustReasonLangMaps(List<StampAdjustReasonLangMapDTO> stampAdjustReasonLangMaps) {
		this.stampAdjustReasonLangMaps = stampAdjustReasonLangMaps;
	}

}
