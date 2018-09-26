package com.zhide.app.logic;

import android.util.Log;

import com.zhide.app.common.CommonUrl;
import com.zhide.app.eventBus.ErrorMsgEvent;
import com.zhide.app.eventBus.PayOrderEvent;
import com.zhide.app.eventBus.WaterPreBillEvent;
import com.zhide.app.eventBus.WaterSettleEvent;
import com.zhide.app.model.WXPayParamModel;
import com.zhide.app.model.WaterPreBillModel;
import com.zhide.app.model.WaterSettleModel;
import com.zhide.app.okhttp.DataManager;
import com.zhide.okhttputils.callback.GenericsCallback;
import com.zhide.okhttputils.request.JsonGenericsSerializator;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Create by Admin on 2018/8/27
 */
public class ChargeManager {
    private static ChargeManager instance = null;
    private static DataManager dataManager = null;

    private ChargeManager() {

    }

    public static ChargeManager getInstance() {
        if (instance == null) {
            instance = new ChargeManager();
        }
        if (dataManager == null) {
            dataManager = DataManager.getInstance();
        }
        return instance;
    }


    public void getWeChatPayParams(float amount, long userId) {
        Log.d("admin", "getWeChatPayParams: amount=" + amount);
        JSONObject params = new JSONObject();
        try {
            params.put("USR_Money", amount);
            params.put("USI_Id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dataManager.sendPostRequestData(CommonUrl.GET_WECHAT_PARAMS, params)
                .execute(new GenericsCallback<WXPayParamModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(WXPayParamModel response, int id) {
                        EventBus.getDefault().post(new PayOrderEvent(response, true));
                    }
                });
    }

    public void getAliPayParams(float amount) {
        JSONObject params = new JSONObject();
        try {
            params.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataManager.sendPostRequestData(CommonUrl.GET_ALIPAY_PARAMS, params)
                .execute(new GenericsCallback<WXPayParamModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(WXPayParamModel response, int id) {
                        EventBus.getDefault().post(new PayOrderEvent(response, false));
                    }
                });
    }

    /**
     * 学生用水预扣费接口
     */
    public void useWaterPreBill(long userId) {
        JSONObject params = new JSONObject();
        try {
            params.put("USI_Id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataManager.sendPostRequestData(CommonUrl.useWaterPreBill, params)
                .execute(new GenericsCallback<WaterPreBillModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(WaterPreBillModel response, int id) {
                        EventBus.getDefault().post(new WaterPreBillEvent(response));
                    }
                });
    }

    /**
     * 学生用水结算接口
     *
     * @param realBill 产生的实际费用
     * @param USB_Id   后台返回的订单id
     */
    public void useWaterSettlement(float realBill, int USB_Id) {

        JSONObject params = new JSONObject();
        try {
            params.put("USBP_Money", realBill);
            params.put("USB_Id", USB_Id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataManager.sendPostRequestData(CommonUrl.useWaterSettlement, params)
                .execute(new GenericsCallback<WaterSettleModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(WaterSettleModel response, int id) {
                        EventBus.getDefault().post(new WaterSettleEvent(response));
                    }
                });
    }

}
