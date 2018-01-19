package com.cherrypicks.tcc.cms.campaign.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class Gift extends BaseModel {
    /** 版本号 */
    private static final long serialVersionUID = -2571166236167184011L;

    /**  */
    private Long merchantId;

    /**
     * 获取
     *
     * @return
     */
    public Long getMerchantId() {
        return this.merchantId;
    }

    /**
     * 设置
     *
     * @param campaignId
     *
     */
    public void setMerchantId(final Long merchantId) {
        this.merchantId = merchantId;
    }

}