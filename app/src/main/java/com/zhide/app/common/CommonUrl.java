package com.zhide.app.common;

public class CommonUrl {
    public static final String BASE_URL = "http://zdtest.zlan-ai.com"; // 测试
    //public static final String BASE_URL = "http://school.zhidn.com"; // 正式
    public static final String sendSmsCode = BASE_URL + "/Student/UserGetSmsCode";
    public static final String registerUser = BASE_URL + "/Student/UserRegister";
    public static final String modifyPassword = BASE_URL + "/Student/UserPayInfo_Submit";



    public static final String GET_APK_VERSION_INFO = BASE_URL + "/apk/che"  + "ck";
    public static final String GET_WECHAT_PARAMS = BASE_URL + "/apk/wx";
    public static final String GET_ALIPAY_PARAMS = BASE_URL + "/apk/ali";

}
