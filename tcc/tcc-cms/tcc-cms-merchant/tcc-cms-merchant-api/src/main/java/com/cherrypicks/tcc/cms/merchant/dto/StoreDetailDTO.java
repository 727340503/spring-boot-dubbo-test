package com.cherrypicks.tcc.cms.merchant.dto;

import java.util.Date;
import java.util.List;

import com.cherrypicks.tcc.cms.dto.BaseObject;

public class StoreDetailDTO extends BaseObject {

	private static final long serialVersionUID = -2482879148474361543L;
	private Long id;
	private String externalStoreId;
	private String phone;
	private Long merchantId;
	private Integer status;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String udpatedBy;
	private Long merchantAreaId;
	private String lat;
	private String lng;
	private List<StoreLangMapDTO> storeLangMaps;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExternalStoreId() {
		return externalStoreId;
	}

	public void setExternalStoreId(String externalStoreId) {
		this.externalStoreId = externalStoreId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUdpatedBy() {
		return udpatedBy;
	}

	public void setUdpatedBy(String udpatedBy) {
		this.udpatedBy = udpatedBy;
	}

	public Long getMerchantAreaId() {
		return merchantAreaId;
	}

	public void setMerchantAreaId(Long merchantAreaId) {
		this.merchantAreaId = merchantAreaId;
	}

	public List<StoreLangMapDTO> getStoreLangMaps() {
		return storeLangMaps;
	}

	public void setStoreLangMaps(List<StoreLangMapDTO> storeLangMaps) {
		this.storeLangMaps = storeLangMaps;
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

}
