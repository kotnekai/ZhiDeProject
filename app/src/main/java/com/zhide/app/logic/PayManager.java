package com.zhide.app.logic;

import android.app.Activity;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.zhide.app.common.CommonParams;
import com.zhide.app.delegate.IGetAliPayResult;
import com.zhide.app.model.AliPayParamModel;
import com.zhide.app.model.WXModel;
import com.zhide.app.model.WXPayParamModel;
import com.zhide.app.utils.EmptyUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
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

    public void getWxPayParams(float selectAmount, long userId) {
        ChargeManager.getInstance().getWeChatPayParams(selectAmount, userId);

    }

    /**
     * 调起支付宝支付
     *
     * @param aliPayParamModel
     */
    public void sendAliPayRequest(final Activity context, final AliPayParamModel aliPayParamModel, final IGetAliPayResult aliPayResult) {
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
    public void sendWxPayRequest(IWXAPI msgApi, WXPayParamModel.WxpayParamsData paramModel) {
        Log.d("admin", "sendWxPayRequest: paramModel=" + paramModel);

        PayReq request = new PayReq();
        request.appId = paramModel.getAppid();
        request.nonceStr = paramModel.getNonce_str();
        request.packageValue = paramModel.getPackageValue();
        request.partnerId = paramModel.getMch_id();
        request.prepayId = paramModel.getPrepay_id();
        request.timeStamp = paramModel.getTimestamp();
        List<WXModel> list = new LinkedList<>();
        list.add(new WXModel("appid", request.appId));
        list.add(new WXModel("noncestr", request.nonceStr));
        list.add(new WXModel("package", request.packageValue));
        list.add(new WXModel("partnerid", request.partnerId));
        list.add(new WXModel("prepayid", request.prepayId));
        list.add(new WXModel("timestamp", request.timeStamp));

        // request.sign = paramModel.getSign();
         request.sign = genAppSign(list);

        //  IWXAPI msgApi = ApplicationHolder.getInstance().getMsgApi();
        Log.d("admin", "sendWxPayRequest: msgApi=" + msgApi);
        msgApi.registerApp(CommonParams.WECHAT_APPID);
        msgApi.sendReq(request);
    }

    /**
     * 生成签名
     */
    private String genAppSign(List<WXModel> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).key);
            sb.append('=');
            sb.append(list.get(i).value);
            sb.append('&');
        }
        sb.append("key=");
        sb.append("c230ba9257044ea08356433e2972595f");
        String appSign = encode(sb.toString());
        return appSign;
    }

    public static String encode(String string) {
        byte[] hash = new byte[0];
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }


    /**
     * 生成签名
     */
  /*  private String genAppSign(List<WXModel> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).key);
            sb.append('=');
            sb.append(list.get(i).value);
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constant.WX_APP_KEY);
        String appSign = MD5Utils.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return appSign;
    }*/
}
