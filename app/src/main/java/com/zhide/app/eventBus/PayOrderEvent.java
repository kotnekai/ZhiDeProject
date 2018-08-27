package com.zhide.app.eventBus;

import com.zhide.app.model.AliPayParamModel;
import com.zhide.app.model.WXPayParamModel;

/**
 * Create by Admin on 2018/8/27
 */
public class PayOrderEvent {
    private WXPayParamModel wxPayParamModel;
    private AliPayParamModel aliPayParamModel;
    private boolean isWeChatPay = true;

    public PayOrderEvent(WXPayParamModel paramModel, boolean isWeChatPay) {
        wxPayParamModel = paramModel;
        this.isWeChatPay = isWeChatPay;
    }

    public PayOrderEvent(AliPayParamModel aliPayParamModel, boolean isWeChatPay) {
        this.aliPayParamModel = aliPayParamModel;
        this.isWeChatPay = isWeChatPay;
    }

    public boolean isWeChatPay() {
        return isWeChatPay;
    }

    public WXPayParamModel getWxPayParamModel() {
        return wxPayParamModel;
    }

    public AliPayParamModel getAliPayParamModel() {
        return aliPayParamModel;
    }
}
