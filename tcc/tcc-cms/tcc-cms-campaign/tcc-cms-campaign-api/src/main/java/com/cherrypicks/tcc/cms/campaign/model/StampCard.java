package com.cherrypicks.tcc.cms.campaign.model;

import com.cherrypicks.tcc.cms.dto.BaseModel;

public class StampCard extends BaseModel {
    /** 版本号 */
    private static final long serialVersionUID = 4167662183305331221L;

    public enum StampCardStatus {
        PENDING(1),ACTIVE(2),EXPIRED(3),IN_ACTIVE(4);

        private final int value;

        StampCardStatus(final int value) {
            this.value = value;
        }

        public int toValue() {
            return this.value;
        }
    }

    /**  */
    private Long campaignId;

    /** 1-Merchant 2-Shopping */
    private Integer type;

    /** 1-Pending 2-Active 3-Expired 4-Inactive*/
    private Integer status;

    /**
     * 获取
     *
     * @return
     */
    public Long getCampaignId() {
        return this.campaignId;
    }

    /**
     * 设置
     *
     * @param campaignId
     *
     */
    public void setCampaignId(final Long campaignId) {
        this.campaignId = campaignId;
    }

    /**
     * 获取1-Merchant 2-Shopping
     *
     * @return 1-Merchant 2-Shopping
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置1-Merchant 2-Shopping
     *
     * @param type
     *          1-Merchant 2-Shopping
     */
    public void setType(final Integer type) {
        this.type = type;
    }

    /**
     * 获取1-Pending 2-Active 3-Expired 4-Inactive
     *
     * @return  1-Pending 2-Active 3-Expired 4-Inactive
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置1-Pending 2-Active 3-Expired 4-Inactive
     *
     * @param status
     *          1-Pending 2-Active 3-Expired 4-Inactive
     */
    public void setStatus(final Integer status) {
        this.status = status;
    }

    public enum Type{
    	MERCHANT("Merchant",1),SHOPPING("Shopping",2);
    	String value;
    	int code;
    	private Type(final String value,final int code){
    		this.value = value;
    		this.code = code;
    	}

    	public String getValue(){
    		return value;
    	}
    	 public int getCode(){
    		 return code;
    	 }
    }
}