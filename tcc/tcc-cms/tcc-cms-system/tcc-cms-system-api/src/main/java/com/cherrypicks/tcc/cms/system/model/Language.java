package com.cherrypicks.tcc.cms.system.model;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.cherrypicks.tcc.cms.dto.BaseModel;

/**
 * @version 1.0.0 2017-03-07
 */
public class Language extends BaseModel {
    /** 版本号 */
    private static final long serialVersionUID = 1761547124723519826L;
    
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

    /**  */
    private String langCode;

    /**  */
    private String descr;

    /**  */
    private String image;

    /**  */
    private Boolean isDefault;

    /**  */
    private Integer sortOrder;

    /**
     * 获取
     *
     * @return
     */
    public String getLangCode() {
        return this.langCode;
    }

    /**
     * 设置
     *
     * @param langCode
     *
     */
    public void setLangCode(final String langCode) {
        this.langCode = langCode;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getDescr() {
        return this.descr;
    }

    /**
     * 设置
     *
     * @param langDesc
     *
     */
    public void setDescr(final String descr) {
        this.descr = descr;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getiImage() {
        return this.image;
    }

    /**
     * 设置
     *
     * @param langImage
     *
     */
    public void setImage(final String image) {
        this.image = image;
    }

    /**
     * 获取
     *
     * @return
     */
    public Boolean getIsDefault() {
        return this.isDefault;
    }

    /**
     * 设置
     *
     * @param isDefault
     *
     */
    public void setIsDefault(final Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getSortOrder() {
        return this.sortOrder;
    }

    /**
     * 设置
     *
     * @param sortOrder
     *
     */
    public void setSortOrder(final Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}