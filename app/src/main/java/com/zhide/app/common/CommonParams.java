package com.zhide.app.common;

public class CommonParams {
    public static boolean IS_WRITE_LOG = true;//是否写日志
    public static final String USER_TOKEN = "token";
    public static final String WECHAT_APPID = "wxac87d65c49952574";//微信支付appId
    public static final String WECHAT_PARTNERID = "1514789911";// 微信支付分配的商户号
    public static final String WECHAT_PAY_PACKAGEVALUE = "Sign=WXPay";// 微信支付分配的商户号

    //apk的路径
    public static final String  APK_PATH = "/apk";

    public static final String PRF_PSW = "password";
    public static final String PRF_NAME = "username";
    public static final String PRF_PSW_CHECK_STATE = "checkState";
    public static final String LOGIN_USER_ID = "userId";
    //学校对应水表费率
    public static final String SCHOOL_WATERRATE = "school_waterRate";
    //学生余额
    public static final String USI_MAINBALANCE = "USI_MainBalance";
    //预扣金额
    public static final String SI_DEDUCTING = "SI_Deducting";

    //消费金额
    public static final String PAY_MONEY = "pay_money";
    //完成时间
    public static final String COMPLETED_TIME = "completed_time";
    //返回金额
    public static final String RETURN_MONEY = "return_money";


    public static long currentUserId;

    public static int PAGE_HOME_FRAG_TYPE = 1;
    public static int PAGE_CARD_CHARGE_FRAG_TYPE = 2;
    public static int PAGE_MINE_FRAG_TYPE = 3;
    public static int PAGE_WALLET_FRAG_TYPE = 4;

}
