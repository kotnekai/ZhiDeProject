package com.zhide.app.logic;

import android.app.Activity;
import android.content.Context;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.zhide.app.common.ApplicationHolder;
import com.zhide.app.delegate.IGetAliPayResult;
import com.zhide.app.model.AliPayParamModel;
import com.zhide.app.model.WXPayParamModel;
import com.zhide.app.utils.EmptyUtil;

import java.util.Map;

/**
 * Create by Admin on 2018/9/5
 */
public class PayManager {
    private static PayManager instance = null;

    private PayManager() {
    }

    public static PayManager getInstance() {
        if (instance == null) {
            instance = new PayManager();
        }
        return instance;
    }

    public void getAliPayParams(float selectAmount) {
        ChargeManager.getInstance().getAliPayParams(selectAmount);
    }

    public void getWxPayParams(float selectAmount) {
        ChargeManager.getInstance().getWeChatPayParams(selectAmount);

    }
    /**
     * 调起支付宝支付
     *
     * @param aliPayParamModel
     */
    public void sendAliPayRequest(final Activity context, final AliPayParamModel aliPayParamModel, final IGetAliPayResult aliPayResult ) {
        final String orderInfo = aliPayParamModel.getOrderInfo();
        if (EmptyUtil.isEmpty(orderInfo)) {
            return;
        }
        //异步处理
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                //新建任务
                PayTask alipay = new PayTask(context);
                String version = alipay.getVersion();
                //获取支付结果
                Map<String, String> result = alipay.payV2(orderInfo, true);
                aliPayResult.getResult(result);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 微信支付
     */
    public void sendWxPayRequest(WXPayParamModel paramModel) {

        PayReq request = new PayReq();
        request.appId = paramModel.getAppId();
        request.partnerId = paramModel.getPartnerId();
        request.prepayId = paramModel.getPrepayId();
        request.packageValue = paramModel.getPackageValue();
        request.nonceStr = paramModel.getNoncestr();
        request.timeStamp = paramModel.getTimestamp();
        request.sign = paramModel.getSign();
        ApplicationHolder.getInstance().getMsgApi().sendReq(request);
    }
}
