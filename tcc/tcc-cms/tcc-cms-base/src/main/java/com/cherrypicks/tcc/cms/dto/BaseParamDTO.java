package com.cherrypicks.tcc.cms.dto;

public class BaseParamDTO extends BaseObject {

    private static final long serialVersionUID = 3624459326239763200L;

    private Long userId;
    private String session;
    private String accessToken;
    private String lang;
    private Integer deviceType;
    private String deviceId;
    private String deviceToken;
    private String deviceIp;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(final String lang) {
        this.lang = lang;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(final Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(final String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(final String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(final String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSession() {
        return session;
    }

    public void setSession(final String session) {
        this.session = session;
    }

}
