package com.cherrypicks.tcc.cms.campaign.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class GiftLangMap extends BaseModel {
    /** 版本号 */
    private static final long serialVersionUID = 8184993635037734478L;

    /**  */
    private Long giftId;

    /**  */
    private String name;

    /**  */
    private String image;

    /**  */
    private String descr;

    /**  */
    private String langCode;

    /**
     * 获取
     *
     * @return
     */
    public Long getGiftId() {
        return this.giftId;
    }

    /**
     * 设置
     *
     * @param giftId
     *
     */
    public void setGiftId(final Long giftId) {
        this.giftId = giftId;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置
     *
     * @param name
     *
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getImage() {
        return this.image;
    }

    /**
     * 设置
     *
     * @param image
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
    public String getDescr() {
        return this.descr;
    }

    /**
     * 设置
     *
     * @param descr
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
}