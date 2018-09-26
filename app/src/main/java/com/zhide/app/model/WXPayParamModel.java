package com.zhide.app.model;

/**
 * Create by Admin on 2018/8/16
 */
public class WXPayParamModel {

    private int code;
    private String message;

    private WxpayParamsData data;

    public WxpayParamsData getData() {

        return data;
    }

    public void setData(WxpayParamsData data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class WxpayParamsData {
        private String appid;
        private String partnerid;//微信支付分配的商户号,partnerId
        private String prepayid;//微信返回的支付交易会话ID
        private String packageValue;//暂填写固定值Sign=WXPay
        private String noncestr;//随机字符串，不长于32位。推荐随机数生成算法d
        private String timestamp;//时间戳
        private String sign;//签名
        private String result_code;//结果码
        private String return_code;//返回码
        private String return_msg;//
        private String trade_type;//交易类型 app
        private String USB_OrderNo;//交易单号 app

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
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

        public String getResult_code() {
            return result_code;
        }

        public void setResult_code(String result_code) {
            this.result_code = result_code;
        }

        public String getReturn_code() {
            return return_code;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }

        public String getUSB_OrderNo() {
            return USB_OrderNo;
        }

        public void setUSB_OrderNo(String USB_OrderNo) {
            this.USB_OrderNo = USB_OrderNo;
        }

        @Override
        public String toString() {
            return "WxpayParamsData{" +
                    "appid='" + appid + '\'' +
                    ", partnerid='" + partnerid + '\'' +
                    ", prepayid='" + prepayid + '\'' +
                    ", packageValue='" + packageValue + '\'' +
                    ", noncestr='" + noncestr + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    ", sign='" + sign + '\'' +
                    ", result_code='" + result_code + '\'' +
                    ", return_code='" + return_code + '\'' +
                    ", return_msg='" + return_msg + '\'' +
                    ", trade_type='" + trade_type + '\'' +
                    ", USB_OrderNo='" + USB_OrderNo + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WXPayParamModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
