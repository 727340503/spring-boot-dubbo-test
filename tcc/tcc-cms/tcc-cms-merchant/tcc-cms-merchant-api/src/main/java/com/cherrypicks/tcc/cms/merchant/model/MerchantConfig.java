package com.cherrypicks.tcc.cms.merchant.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class MerchantConfig extends BaseModel {

	private static final long serialVersionUID = 7887134688255214392L;

	private Long merchantId;
	private String imgDomain;
	private String pushProjectId;
	private String emailHost;
	private Integer emailPort;
	private String emailUserName;
	private String emailPassword;
	private Boolean emailSmtpAuth;
	private Boolean emailSmtpStsEnable;
	private Boolean emailSmtpStsRequired;
	private String emailFromNick;
	private String emailFrom;
	private String supportEmailFrom;
	private String webDomain;
	private String apiDomain;
	private String reservationPushTemplate;
	private String registerVerifyEmailTemplate;
	private String resetPasswordEmailTemplate;
	private String iquiryEmailTemplate;
	private String shareTemplate;
	private String shareDefalutTitle;
	private String shareDefalutDescr;
	private String shareDefalutImage;
	private String smsBaseUrl;
	private String smsUserName;
	private String smsPassword;
	private String fbAppId;
	private String fbAppToken;
	private String fbDebugTokenApi;
	private Integer maxSendSmsCount;


	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(final Long merchantId) {
		this.merchantId = merchantId;
	}

	public String getImgDomain() {
		return imgDomain;
	}

	public void setImgDomain(final String imgDomain) {
		this.imgDomain = imgDomain;
	}

	public String getPushProjectId() {
		return pushProjectId;
	}

	public void setPushProjectId(final String pushProjectId) {
		this.pushProjectId = pushProjectId;
	}

	public String getEmailHost() {
		return emailHost;
	}

	public void setEmailHost(final String emailHost) {
		this.emailHost = emailHost;
	}

	public Integer getEmailPort() {
		return emailPort;
	}

	public void setEmailPort(final Integer emailPort) {
		this.emailPort = emailPort;
	}

	public String getEmailUserName() {
		return emailUserName;
	}

	public void setEmailUserName(final String emailUserName) {
		this.emailUserName = emailUserName;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(final String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public Boolean getEmailSmtpAuth() {
		return emailSmtpAuth;
	}

	public void setEmailSmtpAuth(final Boolean emailSmtpAuth) {
		this.emailSmtpAuth = emailSmtpAuth;
	}

	public Boolean getEmailSmtpStsEnable() {
		return emailSmtpStsEnable;
	}

	public void setEmailSmtpStsEnable(final Boolean emailSmtpStsEnable) {
		this.emailSmtpStsEnable = emailSmtpStsEnable;
	}

	public Boolean getEmailSmtpStsRequired() {
		return emailSmtpStsRequired;
	}

	public void setEmailSmtpStsRequired(final Boolean emailSmtpStsRequired) {
		this.emailSmtpStsRequired = emailSmtpStsRequired;
	}

	public String getSupportEmailFrom() {
		return supportEmailFrom;
	}

	public void setSupportEmailFrom(final String supportEmailFrom) {
		this.supportEmailFrom = supportEmailFrom;
	}

	public String getWebDomain() {
		return webDomain;
	}

	public void setWebDomain(final String webDomain) {
		this.webDomain = webDomain;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(final String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getReservationPushTemplate() {
		return reservationPushTemplate;
	}

	public void setReservationPushTemplate(final String reservationPushTemplate) {
		this.reservationPushTemplate = reservationPushTemplate;
	}

    public String getRegisterVerifyEmailTemplate() {
        return registerVerifyEmailTemplate;
    }

    public void setRegisterVerifyEmailTemplate(final String registerVerifyEmailTemplate) {
        this.registerVerifyEmailTemplate = registerVerifyEmailTemplate;
    }

    public String getResetPasswordEmailTemplate() {
        return resetPasswordEmailTemplate;
    }

    public void setResetPasswordEmailTemplate(final String resetPasswordEmailTemplate) {
        this.resetPasswordEmailTemplate = resetPasswordEmailTemplate;
    }

    public String getIquiryEmailTemplate() {
        return iquiryEmailTemplate;
    }

    public void setIquiryEmailTemplate(final String iquiryEmailTemplate) {
        this.iquiryEmailTemplate = iquiryEmailTemplate;
    }

    public String getShareTemplate() {
        return shareTemplate;
    }

    public void setShareTemplate(final String shareTemplate) {
        this.shareTemplate = shareTemplate;
    }

    public String getShareDefalutTitle() {
        return shareDefalutTitle;
    }

    public void setShareDefalutTitle(final String shareDefalutTitle) {
        this.shareDefalutTitle = shareDefalutTitle;
    }

    public String getShareDefalutDescr() {
        return shareDefalutDescr;
    }

    public void setShareDefalutDescr(final String shareDefalutDescr) {
        this.shareDefalutDescr = shareDefalutDescr;
    }

    public String getShareDefalutImage() {
        return shareDefalutImage;
    }

    public void setShareDefalutImage(final String shareDefalutImage) {
        this.shareDefalutImage = shareDefalutImage;
    }

    public String getApiDomain() {
        return apiDomain;
    }

    public void setApiDomain(final String apiDomain) {
        this.apiDomain = apiDomain;
    }

	public String getEmailFromNick() {
		return emailFromNick;
	}

	public void setEmailFromNick(final String emailFromNick) {
		this.emailFromNick = emailFromNick;
	}

    public String getSmsBaseUrl() {
        return smsBaseUrl;
    }

    public void setSmsBaseUrl(final String smsBaseUrl) {
        this.smsBaseUrl = smsBaseUrl;
    }

    public String getSmsUserName() {
        return smsUserName;
    }

    public void setSmsUserName(final String smsUserName) {
        this.smsUserName = smsUserName;
    }

    public String getSmsPassword() {
        return smsPassword;
    }

    public void setSmsPassword(final String smsPassword) {
        this.smsPassword = smsPassword;
    }

    public String getFbAppId() {
        return fbAppId;
    }

    public void setFbAppId(final String fbAppId) {
        this.fbAppId = fbAppId;
    }

    public String getFbAppToken() {
        return fbAppToken;
    }

    public void setFbAppToken(final String fbAppToken) {
        this.fbAppToken = fbAppToken;
    }

    public String getFbDebugTokenApi() {
        return fbDebugTokenApi;
    }

    public void setFbDebugTokenApi(final String fbDebugTokenApi) {
        this.fbDebugTokenApi = fbDebugTokenApi;
    }

    public Integer getMaxSendSmsCount() {
        return maxSendSmsCount;
    }

    public void setMaxSendSmsCount(final Integer maxSendSmsCount) {
        this.maxSendSmsCount = maxSendSmsCount;
    }

}