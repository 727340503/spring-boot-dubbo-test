package com.cherrypicks.tcc.cms.enums;

public enum PushNotifTemplate {

	EN_US("en_US", "push_message_en_us"), ZH_CN("zh_CN", "push_message_zh_cn"), ZH_HK("zh_HK","push_message_zh_hk");

	String code;
	String name;

	private PushNotifTemplate(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static PushNotifTemplate getVmByLangCode(String code) {
		PushNotifTemplate obj = null;
		final PushNotifTemplate[] objs = PushNotifTemplate.values();
		if (objs != null && objs.length > 0) {
			for (final PushNotifTemplate val : objs) {
				if (val.code.equalsIgnoreCase(code)) {
					obj = val;
					break;
				}
			}
		}

		return obj;
	}
}
