package com.zhide.app.model;

/**
 * Create by Admin on 2018/8/16
 */
public class WXPayParamModel {
    private String appId;
    private String partnerId;//微信支付分配的商户号
    private String prepayId;//微信返回的支付交易会话ID
    private String packageValue;//暂填写固定值Sign=WXPay
    private String noncestr;//随机字符串，不长于32位。推荐随机数生成算法d
    private String timestamp;//时间戳
    private String sign;//签名

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
