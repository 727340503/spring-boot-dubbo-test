package com.cherrypicks.tcc.util;

/**
 * web service 系统错误状态码 -- cs
 */
public class CmsCodeStatus {

	public static final int FAILED = -1;
	// Input parameter error
	public static final int ILLEGAL_ARGUMENT = -2;
	public static int RECORD_VERSION_EXCEPTION = -3;
	//file upload error
	public static final int FILE_UPLOAD_EXCEPTION = -4;
	// record is referenced
	public static final int RECORD_IS_REFERENCED = -5;
	//json error
	public static int JSON_PARSE_EXCEPTION = -6;
	//update record status error
	public static final int UPDATE_RECORD_STATUS_EXCEPTION = -7;
	
	// Forbidden
	public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NO_PERMISSION = 491;
    
    // session error
    public static final int ILLEGAL_SESSION = 10401;
    
    // Login error
	public static final int INVALID_USER_NAME_OR_PASSWORD = 416;
	public static final int INVALID_USER_NAME = 417;
	public static final int INVALID_PASSWORD = 418;
	public static final int INVALID_USER_STATUS = 419;
	
	//system user error
	public static final int SYSTEM_USER_IS_EXIST = 420;
	public static final int SYSTEM_USER_EMAIL_IS_EXIT = 421;
	public static final int SYSTEM_ROLE_IS_EXIST = 424;
	public static final int SYSTEM_ROLE_NOT_EXIST = 425;
	
	//merchant error
	public static final int MERCHANT_NOT_EXIST = 523;
	public static final int MERCHANT_SECURITY_KEY_NOT_EXIST = 524;
	public static final int MERCHANT_AREA_NOT_EXIST = 525;
	public static final int MERCHANT_CONFIG_NOT_EXIST = 526;
	
	//campaign error
	public static final int CAMPAIGN_NOT_EXIST = 532;
	public static final int CAMPAIGN_GIFT_MAP_NOT_EXIST = 533;
	public static final int CAMPAIGN_GIFT_MAP_EX_TYPE_NOT_EXIST = 534;
	public static final int CAMPAIGN_GIFT_MAP_IS_EXIST = 535;
	public static final int CAMPAIGN_IS_OVER = 536;
	public static final int CAMPAIGN_IS_STARTS = 537;
	public static final int CAMPAIGN_STATUS_IS_NOT_ACTIVE_EXCEPTION = 538;

	//gift error
//	public static final int GIFT_NAME_IS_EXIST = 541;
	public static final int GIFT_NOT_EXIST = 542;
	
	//stamp error
	public static final int STAMP_NOT_EXIST = 552;

	//store
	public static final int STORE_NOT_EXIST = 562;
	
	//USER Reservation
	public static final int USER_RESERVATION_NOT_EXIST = 572;
	public static final int USER_RESERVATION_STATUS = 573;
	
	//push notif
//	public static final int PUSH_NOTIF_IS_EXIST = 581;
	public static final int PUSH_NOTIF_NOT_EXIST = 582;
	public static final int PUSH_NOTIF_STATUS_EXCEPTION = 583;
	
	//game
	public static final int GAME_NOT_EXIST = 592;
	
	//home page
	public static final int HOME_PAGE_DRAFT_NOT_EXIST = 612;

	//Promotion
	public static final int BANNER_NOT_EXIST = 622;
	public static final int COUPON_NOT_EXIST = 623;
	public static final int COUPON_QUOTA_QTY_LITTLE = 624;
	
	//User stamp history
	public static final int USER_STAMP_CARD_NOT_EXIST = 632;
	public static final int SUB_USER_STAMPS_EXCEPTION = 633;
	
	//Report
	public static final int REPORT_DATE_PERIOD_EXCEPTION = 731;

	//KEEPER
	public static final int KEEPER_USER_IS_EXIST_EXCEPTION = 831;
	public static final int KEEPER_USER_NOT_EXIST_EXCEPTION = 832;
	public static final int KEEPER_USER_STAFF_ID_IS_EXIST_EXCEPTION = 833;

	//STAMP ADJUST REASON
	public static final int STAMP_ADJUST_REASON_NOT_EXIST_EXCEPTION = 932;

	//push message
	public static final int REGISTER_DEVICE_TOKEN_ERROR = 10474;
	public static final int PUSH_UNBIND_USER_ERROR = 10475;
	public static final int PUSH_SEARCH_TASK_ERROR = 10476;
	public static final int PUSH_SEARCH_TASK_DETAIL_ERROR = 10477;
	public static final int PUSH_CANCEL_TASK_ERROR = 10478;
	public static final int PUSH_SEND_MSG_EXCEPTION = 10479;
    
}
