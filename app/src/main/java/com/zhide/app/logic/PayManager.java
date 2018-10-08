package com.zhide.app.logic;

import android.app.Activity;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.zhide.app.common.ApplicationHolder;
import com.zhide.app.delegate.IGetAliPayResult;
import com.zhide.app.model.WXPayParamModel;

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

    public void getAliPayParams(float selectAmount,long userId) {
        ChargeManager.getInstance().getAliPayParams(selectAmount,userId);
    }

    public void getWxPayParams(float selectAmount, long userId) {
        ChargeManager.getInstance().getWeChatPayParams(selectAmount, userId);

    }

    /**
     * 调起支付宝支付
     *
     * @param orderInfo
     */
    public void sendAliPayRequest(final Activity context, final String orderInfo, final IGetAliPayResult aliPayResult) {
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
    public void sendWxPayRequest(WXPayParamModel.WxpayParamsData paramModel) {
        Log.d("admin", "sendWxPayRequest1: paramModel=" + paramModel);

        PayReq request = new PayReq();
        request.appId = paramModel.getAppid();
        request.nonceStr = paramModel.getNoncestr();
        request.packageValue = paramModel.getPackageValue();
        request.partnerId = paramModel.getPartnerid();
        request.prepayId = paramModel.getPrepayid();
        request.timeStamp = paramModel.getTimestamp();
        request.sign = paramModel.getSign();

        IWXAPI msgApi = ApplicationHolder.getInstance().getMsgApi();

        msgApi.sendReq(request);
    }



}
