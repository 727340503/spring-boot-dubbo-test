package com.cherrypicks.tcc.util.mail;

import java.util.Properties;

public class MailInfo {
    private String mailServerHost; // 服务器ip
    private Integer mailServerPort; // 端口
    private String fromNick;
    private String fromAddress; // 发送者的邮件地址
    private String[] toAddress; // 邮件接收者地址
    private String username; // 登录邮件发送服务器的用户名
    private String password; // 登录邮件发送服务器的密码
    private boolean validate = true; // 是否需要身份验证
    private String subject; // 邮件主题
    private String content; // 邮件内容
    private String[] attachFileNames; // 附件名称
    private boolean starttlsEnable;
    private boolean starttlsRequired;

    public Properties getProperties() {
        final Properties p = new Properties();
        p.put("mail.smtp.host", this.mailServerHost);
        p.put("mail.smtp.port", this.mailServerPort);
        p.put("mail.smtp.auth", validate ? "true" : "false");
        p.put("mail.smtp.starttls.enable", starttlsEnable ? "true" : "false");
        p.put("mail.smtp.starttls.required", starttlsRequired ? "true" : "false");
        p.put("mail.smtp.connectiontimeout", 20000);
        p.put("mail.smtp.timeout", 60000);
        return p;
    }

    public String getMailServerHost() {
        return mailServerHost;
    }

    public void setMailServerHost(final String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }

    public Integer getMailServerPort() {
        return mailServerPort;
    }

    public void setMailServerPort(final Integer mailServerPort) {
        this.mailServerPort = mailServerPort;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(final String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String[] getToAddress() {
        return toAddress;
    }

    public void setToAddress(final String[] toAddress) {
        this.toAddress = toAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(final boolean validate) {
        this.validate = validate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String[] getAttachFileNames() {
        return attachFileNames;
    }

    public void setAttachFileNames(final String[] attachFileNames) {
        this.attachFileNames = attachFileNames;
    }

    public boolean isStarttlsEnable() {
        return starttlsEnable;
    }

    public void setStarttlsEnable(final boolean starttlsEnable) {
        this.starttlsEnable = starttlsEnable;
    }

    public boolean isStarttlsRequired() {
        return starttlsRequired;
    }

    public void setStarttlsRequired(final boolean starttlsRequired) {
        this.starttlsRequired = starttlsRequired;
    }

	public String getFromNick() {
		return fromNick;
	}

	public void setFromNick(String fromNick) {
		this.fromNick = fromNick;
	}
    
}
