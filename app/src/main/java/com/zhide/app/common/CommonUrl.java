package com.zhide.app.common;

public class CommonUrl {
    public static final String BASE_URL = "http://zdtest.zlan-ai.com"; // 测试
    //public static final String BASE_URL = "http://school.zhidn.com"; // 正式
    public static final String sendSmsCode = BASE_URL + "/Student/User_GetSmsCode";//
    public static final String login = BASE_URL + "/Student/User_Login";
    public static final String registerUser = BASE_URL + "/Student/User_Register";
    public static final String modifyPassword = BASE_URL + "/Student/UserPayInfo_Submit";
    public static final String savePersonInfo = BASE_URL + "/Student/UserInfo_Submit";

    public static final String getMainPageNews = BASE_URL + "/News/Get_NewsList";
    public static final String getCardBillData = BASE_URL + "/Student/Get_User_Student_Card_Purchase";
    public static final String getUserInfoById = BASE_URL + "/Student/Get_UserInfo";
    public static final String getUserSchoolInfo = BASE_URL + "/School/Get_SchoolInfo";
    public static final String getMyBillData = BASE_URL + "/Student/Get_UserBill";
    public static final String getGuideList = BASE_URL + "/News/Get_GuideList";
    public static final String getSystemInfo = BASE_URL + "/News/Get_SystemInfoList";
    public static final String getBreakdownType = BASE_URL + "/Student/GetBreakdownType";
    public static final String submitBreakInfo = BASE_URL + "/Student/Breakdown_Info_Submit";
    public static final String doWithdraw = BASE_URL + "/Student/User_Student_Withdraw_Submit";

    public static final String GET_APK_VERSION_INFO = BASE_URL + "/apk/che"  + "ck";
    public static final String GET_WECHAT_PARAMS = BASE_URL + "/apk/wx";
    public static final String GET_ALIPAY_PARAMS = BASE_URL + "/apk/ali";
    public static final String useWaterPreBill = BASE_URL + "/Student/User_Water_Deducting";
    public static final String useWaterSettlement = BASE_URL + "/Student/User_Water_Settlemen";

}
