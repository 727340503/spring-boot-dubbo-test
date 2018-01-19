package com.cherrypicks.tcc.cms.merchant.dto;

import java.util.Date;
import java.util.List;

import com.cherrypicks.tcc.cms.dto.BaseObject;
import com.cherrypicks.tcc.cms.merchant.model.MerchantLangMap;

public class MerchantDetailDTO extends BaseObject {

	private static final long serialVersionUID = -6235549112480760216L;

	private Long id;
	private String merchantCode;
	private String securityKey;
	private String loginMethod;
	private Integer issueStampMethod;
	private Integer status;
	private String timeZone;
	private Date createdTime;
	private String createdBy;
	private Date updatedTime;
	private String updatedBy;
	private Boolean isDeleted = false;
	private Boolean isCouponManagement = false;
	private Long currencyUnitId;
	private String currencyCode;
	private String currencyFontCode;
	private String dateFormat;
	private String hoursFormat;
	private String phoneDistrictCode;
	private Integer reservationType;
	private Integer mapType;
	private List<MerchantLangMap> merchantLangMapList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getSecurityKey() {
		return securityKey;
	}

	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}

	public String getLoginMethod() {
		return loginMethod;
	}

	public void setLoginMethod(String loginMethod) {
		this.loginMethod = loginMethod;
	}

	public Integer getIssueStampMethod() {
		return issueStampMethod;
	}

	public void setIssueStampMethod(Integer issueStampMethod) {
		this.issueStampMethod = issueStampMethod;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
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

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<MerchantLangMap> getMerchantLangMapList() {
		return merchantLangMapList;
	}

	public void setMerchantLangMapList(List<MerchantLangMap> merchantLangMapList) {
		this.merchantLangMapList = merchantLangMapList;
	}

	public Boolean getIsCouponManagement() {
		return isCouponManagement;
	}

	public void setIsCouponManagement(Boolean isCouponManagement) {
		this.isCouponManagement = isCouponManagement;
	}

	public Long getCurrencyUnitId() {
		return currencyUnitId;
	}

	public void setCurrencyUnitId(Long currencyUnitId) {
		this.currencyUnitId = currencyUnitId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyFontCode() {
		return currencyFontCode;
	}

	public void setCurrencyFontCode(String currencyFontCode) {
		this.currencyFontCode = currencyFontCode;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getHoursFormat() {
		return hoursFormat;
	}

	public void setHoursFormat(String hoursFormat) {
		this.hoursFormat = hoursFormat;
	}

	public String getPhoneDistrictCode() {
		return phoneDistrictCode;
	}

	public void setPhoneDistrictCode(String phoneDistrictCode) {
		this.phoneDistrictCode = phoneDistrictCode;
	}

	public Integer getReservationType() {
		return reservationType;
	}

	public void setReservationType(Integer reservationType) {
		this.reservationType = reservationType;
	}

	public Integer getMapType() {
		return mapType;
	}

	public void setMapType(Integer mapType) {
		this.mapType = mapType;
	}

}
