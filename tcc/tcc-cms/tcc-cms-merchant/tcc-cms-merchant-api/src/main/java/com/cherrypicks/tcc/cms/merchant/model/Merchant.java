package com.cherrypicks.tcc.cms.merchant.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class Merchant extends BaseModel {

	/**
	 *
	 */
	private static final long serialVersionUID = -841693043453837781L;

	public enum MerchantStatus {
		IN_ACTIVE(0), ACTIVE(1);

		private final int value;

		MerchantStatus(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

	public enum ReservationType {
		PAY_WHEN_PICK_UP_AT_STORE(0), PAY_WHEN_MAKE_RESERVATION(1);

		private final int value;

		ReservationType(final int value) {
			this.value = value;
		}

		public int toValue() {
			return this.value;
		}
	}

	private String merchantCode;
	private String securityKey;
	private String loginMethod;
	private Integer issueStampMethod;
	private Integer status;
	private String timeZone;
	private String homePage;
	private String homePageDraft;
	private Boolean isCouponManagement;
	private Long currencyUnitId;
	private String dateFormat;
	private String hoursFormat;
	private String phoneDistrictCode;
	private Integer reservationType;
	private Integer mapType;

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(final String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getSecurityKey() {
		return securityKey;
	}

	public void setSecurityKey(final String securityKey) {
		this.securityKey = securityKey;
	}

	public String getLoginMethod() {
		return loginMethod;
	}

	public void setLoginMethod(final String loginMethod) {
		this.loginMethod = loginMethod;
	}

	public Integer getIssueStampMethod() {
		return issueStampMethod;
	}

	public void setIssueStampMethod(final Integer issueStampMethod) {
		this.issueStampMethod = issueStampMethod;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(final String timeZone) {
		this.timeZone = timeZone;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(final String homePage) {
		this.homePage = homePage;
	}

	public String getHomePageDraft() {
		return homePageDraft;
	}

	public void setHomePageDraft(String homePageDraft) {
		this.homePageDraft = homePageDraft;
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

	public enum MapType{
		BAIDU_MAP(1),GOOGLE_MAP(0);
		
		private final int code;
		
		private MapType(final int code){
			this.code = code;
		}
		
		public int getCode() {
			return code;
		}
	}
	
	public enum Status {
		INACTIVE(0), ACTIVE(1);

		private final int code;

		private Status(final int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		@Override
		public String toString() {
			return String.valueOf(this.code);
		}
	}

	public enum LoginMethodType {
		Email("Email", 1), FACEBOOK("FACEBOOK", 2), MOBILE("Mobile", 3);

		private final String value;
		private final int code;

		private LoginMethodType(final String value, final int code) {
			this.value = value;
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return String.valueOf(this.code);
		}
	}

	public enum IssueStampMethod {
		POS_INTEGRATION("POS integration", 1), MERCHANT_APP("Merchant app", 2);

		private final String value;
		private final int code;

		private IssueStampMethod(final String value, final int code) {
			this.value = value;
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return String.valueOf(this.code);
		}
	}
}
