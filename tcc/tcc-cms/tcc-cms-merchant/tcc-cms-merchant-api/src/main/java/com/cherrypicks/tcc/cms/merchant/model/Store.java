package com.cherrypicks.tcc.cms.merchant.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class Store extends BaseModel {
	/** 版本号 */
	private static final long serialVersionUID = -8992933332253844301L;

	public enum StoreStatus {
		IN_ACTIVE(0), ACTIVE(1);

		private final int value;

		StoreStatus(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

	private Long merchantAreaId;

	/**  */
	private String externalStoreId;

	/**  */
	private Long merchantId;

	/**  */
	private String phone;

	private Integer status;

	private String lat;
	private String lng;

	public Long getMerchantAreaId() {
		return merchantAreaId;
	}

	public void setMerchantAreaId(final Long merchantAreaId) {
		this.merchantAreaId = merchantAreaId;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public String getExternalStoreId() {
		return this.externalStoreId;
	}

	/**
	 * 设置
	 *
	 * @param externalStoreId
	 *
	 */
	public void setExternalStoreId(final String externalStoreId) {
		this.externalStoreId = externalStoreId;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public Long getMerchantId() {
		return this.merchantId;
	}

	/**
	 * 设置
	 *
	 * @param merchantId
	 *
	 */
	public void setMerchantId(final Long merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 获取
	 *
	 * @return
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * 设置
	 *
	 * @param phone
	 *
	 */
	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public enum Status {
		ACTIVE(1), IN_ACTIVE(0);

		private final int value;

		Status(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}
}