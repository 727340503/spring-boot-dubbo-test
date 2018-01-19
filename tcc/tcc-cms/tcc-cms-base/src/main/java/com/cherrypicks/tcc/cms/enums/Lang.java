package com.cherrypicks.tcc.cms.enums;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Lang {
    EN_US("en_US"), // en_US
    ZH_CN("zh_CN"), // zh_CN
    ZH_HK("zh_HK"); // zh_HK

    public Map<String, Locale> localeMap = new ConcurrentHashMap<String, Locale>();

    private String value;

    Lang(final String value) {
        this.value = value;
    }

    public String toValue() {
        return this.value;
    }

    public Locale toLocale() {
        Locale locale = localeMap.get(this.value);
        if (locale == null) {
            final String[] langCodes = this.value.split("_");
            locale = new Locale(langCodes[0], langCodes[1]);
            localeMap.put(this.value, locale);
        }

        return locale;
    }

    public static Lang toLang(final String value) {
        Lang obj = null;
        final Lang[] objs = Lang.values();
        if (objs != null && objs.length > 0) {
            for (final Lang val : objs) {
                if (val.value.equals(value)) {
                    obj = val;
                    break;
                }
            }
        }

        return obj;
    }

    public static Lang getDefaultLang() {
        return EN_US;
    }
}
