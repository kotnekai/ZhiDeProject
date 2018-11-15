package com.zhide.app.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonParams {
    public static boolean IS_WRITE_LOG = true;//是否写日志
    public static final String USER_TOKEN = "token";
    public static final String WECHAT_APPID = "wx637d024e071f822a";//微信支付appId
    public static final String WECHAT_PARTNERID = "1514789911";// 微信支付分配的商户号
    public static final String WECHAT_PAY_PACKAGEVALUE = "Sign=WXPay";// 微信支付分配的商户号

    public static int SI_Minchargeunit = 10;


    //apk的路径
    public static final String  APK_PATH = "/apk";

    public static final String PRF_PSW = "password";
    public static final String PRF_NAME = "username";
    public static final String PRF_PSW_CHECK_STATE = "checkState";
    public static final String LOGIN_USER_ID = "userId";
    public static final String SYSTEM_INFO = "system_info";


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

    //用户信息，暂存一下
    public static final String USER_INFO = "user_info";


    public static long currentUserId;

    public static final String CURRENT_ACCOUNT_ID = "current_account_id";


    public static int PAGE_HOME_FRAG_TYPE = 1;
    public static int PAGE_CARD_CHARGE_FRAG_TYPE = 2;
    public static int PAGE_MINE_FRAG_TYPE = 3;
    public static int PAGE_WALLET_FRAG_TYPE = 4;
    public static int PAGE_WITH_DRAW_TYPE = 5;
    public static int PAGE_CHARGE_ACTIVITY_TYPE = 6;
    public static int PAGE_HOME_ACTIVITY_TYPE = 7;
    public static int PAGE_VERSION_ACTIVITY_TYPE = 8;
    public static int PAGE_REGISTER_ACTIVITY_TYPE = 9;


    //结束洗澡代码，用于返回首页
    public static int FINISH_CODE = 300;

    public static int SYSTEM_APK_ID =1034;// apk更新id
    public static int SYSTEM_AGGREE_ID =1030;// 注册协议id
    public static int SYSTEM_PHOONE_ID =1029;// 电话id


    public static String DIRECTORY_ROOT = "/com.zhide.app/log";
    public static String FILE_LOG = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())).toString()+".txt";


    public static final String REQUEST_TYPE_SEAT = "幢座";
    public static final String REQUEST_TYPE_FLOOR = "楼层";
    public static final String REQUEST_TYPE_ROOM = "宿舍";
}
