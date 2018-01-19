package com.cherrypicks.tcc.util;

/**
 * web service 系统错误状态码
 */
public abstract class CodeStatus {

    public static final int SUCCUSS = 0;
    public static final int FAILED = -1;

    // Invalid argument
    public static final int ILLEGAL_ARGUMENT = 10400;

    // Invalid user session
    public static final int INVALID_USER_SESSION = 10401;

    // user not found
    public static final int USER_NOT_FOUND_ERROR = 10402;

    // call push
    public static final int REGISTER_DEVICE_TOKEN_ERROR = 10403;

    // security key not found
    public static final int SECURITY_KEY_NOT_FOUND_ERROR = 10404;

    //sign not match
    public static final int  SIGN_NOT_MATCH_ERROR = 10405;

    //parse json error
    public static final int  JSON_FORMAT_INVALID_ERROR = 10406;

    // merchant not found
    public static final int MERCHANT_NOT_FOUND_ERROR = 10407;

    // redeem Code not found
    public static final int REDEEM_CODE_NOT_FOUND_ERROR = 10408;

    // not Enough Stamp
    public static final int  NOT_ENOUGH_STAMP_ERROR = 10409;

    // collect Code not found
    public static final int COLLECT_CODE_NOT_FOUND_ERROR = 10410;

    // user coupon not found
    public static final int USER_COUPON_NOT_FOUND_ERROR = 10411;

    // user coupon redeemed
    public static final int USER_COUPON_REDEEMED_ERROR = 10412;

    // redeem Code expired
    public static final int REDEEM_CODE_EXPIRED_ERROR = 10414;

    // reservation Code  not Found
    public static final int RESERVATION_CODE_NOT_FOUND_ERROR = 10415;

    // reservation Code expired
    public static final int RESERVATION_CODE_EXPIRED_ERROR = 10416;

    // gift not found
    public static final int GIFT_NOT_FOUND_ERROR = 10417;

    // gift redemption
    public static final int GIFT_REDEMPTION_ERROR = 10418;

    // gift pick up
    public static final int RESERVATION_PICK_UP_ERROR = 10419;

    // user stamp Card not found
    public static final int USER_STAMP_CARD_NOT_FOUND_ERROR = 10420;

    // campaign stamp  not found
    public static final int  CAMPAIGN_STAMP_NOT_FOUND_ERROR = 10421;

    // repeat invited
    public static final int  REPEAT_INVITED_ERROR = 10422;

    // email invalied
    public static final int NOT_POSITIVE_FLOAT_ERROR = 10423;

    // email registered
    public static final int EMAIL_REGISTERED_ERROR = 10424;

    // face book registered
    public static final int FACE_BOOK_REGISTERED_ERROR = 10425;

    // email not active
    public static final int EMAIL_NOT_ACTIVE_ERROR = 10426;

    // incorrect password
    public static final int INCORRECT_PASSWORD_ERROR = 10427;

    // email not register
    public static final int EMAIL_NOT_REGISTER_ERROR = 10428;

    // user name incorrect
    public static final int USER_NAME_INCORRECT_ERROR = 10429;

    // send email error
    public static final int SEND_EMAIL_ERROR = 10430;

    // new old password is same
    public static final int NEWPWD_IS_SAME_AS_OLDPWD_ERROR = 10431;

    //Email Account has been veryfied
    public static final int EMAIL_VERIFIED_ERROR = 10432;

    //resend email out of times
    public static final int RESEND_EMAIL_OUTOF_TIMES_ERROR = 10433;

    // not email ergister user
    public static final int NOT_EMAIL_REGISTER_USER_ERROR = 10434;

    // CampaignGiftExchangeType Not Found
    public static final int CAMPAIGN_GIFT_EXCHANGE_TYPE_NOT_FOUND_ERROR = 10435;

    // FaceBook not found
    public static final int FACEBOOK_NOT_FOUND_ERROR = 10436;

    //password format invalid
    public static final int PASSWORD_FORMAT_INVALID = 10437;

    // facebook can not change password
    public static final int FACEBOOK_CANNOT_CHANGE_PASSWORD = 10438;

    // change user password out of times
    public static final int CHANGE_USER_PASSWORD_OUT_OF_TIMES_ERROR = 10439;

    // FaceBook Update RegisterEmail
    public static final int FACEBOOK_UPDATE_REGISTERE_MAIL_ERROR = 10440;

    //NewEmailIsSameAsOldEmail
    public static final int NEW_EMAIL_IS_SAME_AS_OLD_EMAIL_ERROR = 10441;

    //Email Exist
    public static final int EMAIL_EXIST_ERROR = 10442;

    //unknown facebook error
    public static final int UNKNOWN_FACEBOOK_ERROR = 10443;

    // facebooke login token invalid
    public static final int FACEBOOK_REGISTER_TOKEN_INVALID_ERROR = 10444;

    //facebooke register token invalid
    public static final int FACEBOOK_LOGIN_TOKEN_INVALID_ERROR = 10445;

    // push unbind user error
    public static final int PUSH_UNBIND_USER_ERROR = 10446;

    public static final int PUSH_CANCEL_TASK_ERROR = 10447;

    public static final int PUSH_SEARCH_TASK_DETAIL_ERROR = 10448;

    public static final int PUSH_SEARCH_TASK_ERROR = 10449;

    public static final int STAMPS_FORMAT_INVALID = 10450;

    public static final int ACTIVE_EMAIL_INVALID = 10451;

    public static final int ACTIVE_EMAIL_EXPIRED = 10452;

    public static final int ACTIVE_EMAIL_VERIFIED = 10453;

    public static final int RESERVATION_NOT_STOCK_ERROR = 10454;

    public static final int RESERVATION_CANCELLED_ERROR = 10455;

    public static final int PARSE_USER_ICON_ERROR = 10456;

    public static final int RESET_PASSWORD_EXPIRED = 10457;

    public static final int RESET_PASSOWORD_INVALID = 10458;

    public static final int NEW_PWD_AND_CONFIRM_PWD_NOT_SAME = 10459;

    public static final int CAMPAIGN_NOT_FOUND_ERROR = 10460;

    public static final int USER_HAS_BEAN_GRANT_STAMP_ERROR = 10461;

    public static final int GRANT_STAMP_NOT_TRANSFER_ERROR = 10462;

    public static final int EMAIL_FORMAT_INVALID = 10463;

    public static final int SNS_TYPE_NOT_SUPPORT_ERROR = 10464;

    public static final int LOGIN_TYPE_NOT_SUPPORT_ERROR = 10465;

    public static final int USER_NAME_EXIST_ERROR = 10466;

    public static final int EXCHANGE_TYPE_INVALID = 10467;

    public static final int HAVE_BEAND_INVITED_ERROR = 10468;

    // merchant config not found
    public static final int MERCHANT_CONFIG_NOT_FOUND_ERROR = 10469;

    public static final int RESET_REGISTRATION_EMAIL_OUTOF_TIMES_ERROR = 10470;

    public static final int SEND_FORGET_PASSWORD_EMAIL_OUTOF_TIMES_ERROR = 10471;

    public static final int CAMPAIGN_END_ERROR = 10472;

    public static final int CAMPAIGN_COLLECT_END_ERROR = 10473;

    public static final int CAMPAIGN_REDEEM_END_ERROR = 10474;

    public static final int CAMPAIGN_GIFT_OUT_OF_STOCK_ERROR = 10475;

    public static final int CAMPAIGN_GIFT_IN_ACTIVE_ERROR = 10476;

    public static final int PICK_UP_STORE_NOT_MATCH = 10477;

    public static final int EXTERNAL_RULE_CODE_NOT_MATCH = 10478;

    public static final int OPEN_RESERVATION_NOT_REDEEM_ERROR = 10479;

    // shoppo
    public static final int PHONE_REGISTERED_ERROR = 10480;

    public static final int SEND_SMS_ERROR = 10481;

    public static final int SMS_VERIFY_NOT_FOUND_ERROR = 10482;

    public static final int SMS_VERIFY_CODE_EXPIRED_ERROR = 10483;

    public static final int RESEND_SMS_OUTOF_TIMES_ERROR = 10484;

    public static final int PHONE_NOT_VERIFIED_ERROR = 10485;

    public static final int SMS_VERIFY_CODE_INCORRENT_ERROR = 10486;

    public static final int PHONE_NOT_REGISTER_ERROR = 10487;

    public static final int COUPON_IN_ACTIVE_ERROR = 10488;

    public static final int SEND_EMAIL_WITHIN_24_HOURS = 10489;

    public static final int SMS_LOG_NOT_FOUND_ERROR = 10490;

    public static final int SMS_MESSAGE_CANT_SEND_ERROR = 10492;

    public static final int COUPON_NOT_FOUND_ERROR = 10493;

    public static final int NO_REMAINDER_COUPON_ERROR = 10494;

    public static final int OUT_OF_ISSUE_DQUOTA_QTY_ERROR = 10495;

    public static final int COUPON_COLLECT_NOT_START_ERROR = 10496;

    public static final int COUPON_COLLECT_EXPIRED_ERROR = 10497;

    public static final int OUT_OF_COLLECT_DQUOTA_QTY_ERROR = 10498;

    public static final int COUPON_NO_BELONG_CAMPAIGN_ERROR = 10499;

    public static final int OUT_OF_REDEEM_QUOTA_QTY = 10500;

    public static final int TEMP_EMAIL_USER_NOT_FOUND = 10501;

    public static final int OUT_OF_MAX_REDEEM_QTY = 10502;

    public static final int COUPON_NOT_STATMPS_TYPE = 10503;

    public static final int COUPON_REDEEM_NOT_START = 10504;

    public static final int COUPON_REDEEM_EXPIRED_ERROR = 10505;

    public static final int SMS_SERVER_ERROR = 10506;


    // keeper

    public static final int USER_NAME_NOT_FOUND_ERROR = 20300;

    public static final int INVALID_ADJUSTSTAMP_TYPE = 20301;

    public static final int USER_STORE_NOT_FOUND_ERROR = 20302;

    public static final int KEEPER_CAMPAIGN_ERROR = 20305;

    public static final int KEEPER_COUPON_ERROR = 20306;

}
