package com.cherrypicks.tcc.cms.dao.jedis;

/**
 * 所有操作Redis的key必须从此获取
 */
public final class RedisKey {

    public static final String ENTITY_PREFIX = "Entity";

    public static final String STRING_PREFIX = "String";

    public static final String HASH_PREFIX = "Hash";

    public static final String SET_PREFIX = "Set";

    public static final String LIST_PREFIX = "List";

    public static final String DELIMITER = ":";

    private RedisKey() {

    }

    public static String getUserKey(final Long userId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("User").append(DELIMITER).append(userId)
                .append(DELIMITER).toString();
    }
    
    public static String getStringKey(final String str) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("connect").append(DELIMITER).append(str)
                .append(DELIMITER).toString();
    }
    
    public static String getUserSessionKey(final Long userId, final String accessToken) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("User").append(DELIMITER).append(userId)
                .append(DELIMITER).append("AccessToken").append(DELIMITER).append(accessToken).toString();
    }
    
    public static String getUserAllSessionKey(final Long userId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("User").append(DELIMITER).append(userId)
                .append(DELIMITER).append("AccessToken").append(DELIMITER).append("*").toString();
    }
    
    public static String getHomePageKey(final Long merchantId,final String langCode){
    	 return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("Merchant").append(DELIMITER).append(merchantId)
                 .append(DELIMITER).append("Lang").append(DELIMITER).append(langCode).toString();
    }

    public static String getMerchantConfigKey(final Long merchantId){
   	 return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("Merchant").append(DELIMITER).append(merchantId)
                .append(DELIMITER).toString();
    }
    
    public static String getKeeperUserKey(final Long merchantId,final Long userId) {
        return new StringBuilder(STRING_PREFIX).append(DELIMITER).append("Merchant").append(DELIMITER).append(merchantId)
                .append("KeeperUser").append(DELIMITER).append(userId)
                .append(DELIMITER).toString();
    }
    
}
