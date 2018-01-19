package com.cherrypicks.tcc.cms.campaign.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class CampaignGiftExchangeType extends BaseModel {

	/**
	 *
	 */
	private static final long serialVersionUID = -669600113739347287L;

	private Long campaignGiftMapId;

	/**  */
	private Long stampQty;

	/** 1-Stamps 2-Stamps + Cash */
	private Integer type;

	private Integer status;

	public enum ExchangeStatus {
		IN_ACTIVE(0), ACTIVE(1);

		private final int value;

		ExchangeStatus(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

	public enum ExchangeType {
		STAMPS(1), STAMPS_CASH(2);

		private final int value;

		ExchangeType(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

	/**  */
	private Double cashQty;

	private String externalRuleCode;

	/**
	 * 获取
	 *
	 * @return
	 */
	public Long getStampQty() {
		return this.stampQty;
	}

	/**
	 * 设置
	 *
	 * @param stampQty
	 *
	 */
	public void setStampQty(final Long stampQty) {
		this.stampQty = stampQty;
	}

	/**
	 * 获取1-Stamps 2-Stamps + Cash
	 *
	 * @return 1-Stamps 2-Stamps + Cash
	 */
	public Integer getType() {
		return this.type;
	}

	/**
	 * 设置1-Stamps 2-Stamps + Cash
	 *
	 * @param type
	 *            1-Stamps 2-Stamps + Cash
	 */
	public void setType(final Integer type) {
		this.type = type;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public Double getCashQty() {
		return cashQty;
	}

	/**
	 * 设置
	 *
	 * @param cashQty
	 *
	 */
	public void setCashQty(final Double cashQty) {
		this.cashQty = cashQty;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public Long getCampaignGiftMapId() {
		return campaignGiftMapId;
	}

	/**
	 * 设置
	 *
	 * @param campaignGiftMapId
	 *
	 */
	public void setCampaignGiftMapId(final Long campaignGiftMapId) {
		this.campaignGiftMapId = campaignGiftMapId;
	}

	public enum Type {
		STAMPS("Stamps", 1), STAMPS_CASH("Stamps And Cash", 2);

		private final String vaule;
		private final int code;

		private Type(final String value, final int code) {
			this.vaule = value;
			this.code = code;
		}

		public String getValue() {
			return this.vaule;
		}

		public int getCode() {
			return this.code;
		}
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

    public String getExternalRuleCode() {
        return externalRuleCode;
    }

    public void setExternalRuleCode(final String externalRuleCode) {
        this.externalRuleCode = externalRuleCode;
    }

}