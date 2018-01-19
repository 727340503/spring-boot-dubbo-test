package com.cherrypicks.tcc.util;

import java.util.UUID;

public final class Constants {

    public static final String LANG = "lang";
    public static final String LANG_CODE = "langCode";
    public static final String CMS = "CMS";
    public static final String UTF8 = "UTF-8";
    public static final String USERID = "userId";
    public static final String SESSIONID = "session";
    public static final String SUCCESSMSG = "Success";
    public static final String ACCESSTOKEN = "accessToken";
    public static final String CONTENT_TYPE_HTML = "text/html";
    public static final String MESSAGE_JSON = "messageJson";
    public static final String CONTENT_TYPE_JSON = "application/json; charset=UTF-8";
    public static final String ERROR_PATH = "/error";
    public static final String PAGE_NOT_FOUND_PATH = "/pageNotFound";
    public static final String POS = "POS";
    public static final String KEEPER_POS = "KEEPER:";
    public static final String COLLECT_STAMP_CARD_PREFIX = "1";
    public static final String COUPON_BARCODE_PREFIX = "2";
    public static final String REDEEM_BARCODE_PREFIX = "3";
    public static final String RESERVATION_BARCODE_PREFIX = "4";
    public static final String RESVATION_TRANS_NO_PREFIX = "7";
    public static final String REDEEM_TRANS_NO_PREFIX = "8";
    public static final String TRANS_STAMP_TRANS_NO_PREFIX = "9";
    public static final String MERCHANT_ID = "merchantId";
    public static final String ERROR_CODE = "errorCode";
    public static final String MESSAGE = "message";
    public static final String DATA = "data";
    public static final String CREATE_BY_API = "SYSTEM";
    public static final String UPDATE_BY_API = "SYSTEM";
    public static final String CMS_DOWNLOAD_PATH = "download.path";
    public static final String CMS_APPLICATION_PROPERTIES = "application.properties";
    public static final String USER_ICON_IMAGE_FLODER = "shoppo";
    public static final String URL_ANALYSIS = "urlAnalysis";
    public static final String ALGORITHM_AES = "AES";
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final Integer DEFAULT_MAX_SEND_COUNT = 5;

    private Constants() {
    };

/*    public static class RoleKey {
        public static final String ROLE_AUTHENTICATED = "ROLE_AUTHENTICATED";
        public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
        public static final String ROLE_ADMIN = "ROLE_ADMIN";
        public static final String ROLE_USER = "ROLE_USER";
    }*/


    public static final String TEMP_NICK_NAME = "訪問用戶";

    public static final String MERCHANT = "merchant";

    //user info
    public static final Integer USER_EXPIRY_MINS = 3600;

    //update user iocn model
    public static final String ICON = "image";

    //update user iocn model
    public static final String PHOTO = "photo";

    //default lang
    public static final String DEFAULT_LANG="zh_HK";

	public static final String PUSH_DATA = "pushData";
	public static final String PUSH_TYPE = "type";
	public static final String PUSH_PAGE_REDIRECT_TYPE = "pageRedirectType";

	//Report
	public static final String REDEMPTION_REPORT = "Report_Redemption - %s_%s-%s";
	public static final String COLLECT_STAMPS_REPORT = " Report_Collectstamps - %s_%s-%s";
	public static final String RESERVATION_REPORT = "Report_Reservation - %s_%s-%s";
	public static final String TRANSFER_STAMPS_REPORT = "Report_Transferstamps - %s_%s-%s";
	public static final String COLLECT_COUPON_REPORT = "Report_Collectcoupon - %s_%s-%s";
	public static final String USER_REPORT = "User report - %s_%s-%s";
	public static final String REDEEM_COUPON_REPORT = "Report_Redeemcoupon - %s_%s-%s";
	public static final String CAMPAIGN_REPORT = "Campaign report - %s_%s";
	public static final String EXCHANGE_METHOD = "Method ";
	public static final String TEMPLATE_VM = ".vm";

	public static final String TRANS_STAMP = "/tcc_api/posTransStamp.do";
	public static final String REDEEM_GIFT = "/tcc_api/posRedeemGift.do";
	public static final String RESERVATION = "/tcc_api/posReservationChangeStatus.do";
	/*public static final String GIFT_OUT_STOCK =  "/tcc_api/posGiftOutOfStock.do";
	public static final String GIFT_LOG_STOCK = "/tcc_api/posGiftLowStock.do";*/

	public static final String TCC_TRANS_STAMP_LOG = "posTransStamp-log";
	public static final String TCC_REDEEM_GIFT_LOG = "posRedeemGift-log";
	public static final String TCC_RESERVATION_LOG = "posReservationChangeStatus-log";

    public static final String KEEPER_TRANS_STAMP_LOG = "keeper-posTransStamp-log";
    public static final String KEEPER_REDEEM_GIFT_LOG = "keeper-posRedeemGift-log";
    public static final String KEEPER_RESERVATION_LOG = "keeper-posReservationChangeStatus-log";
	/*public static final String GIFT_OUT_STOCK_LOG = "posGiftOutOfStock-log";
	public static final String GIFT_LOG_STOCK_LOG = "posGiftLowStock-log";*/

    public static String getUUID(){
        final String pkId = UUID.randomUUID().toString().replace("-", "");
        return pkId;
    }
}
