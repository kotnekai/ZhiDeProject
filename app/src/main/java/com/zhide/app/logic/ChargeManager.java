package com.zhide.app.logic;

import android.util.Log;

import com.zhide.app.common.CommonUrl;
import com.zhide.app.eventBus.ErrorMsgEvent;
import com.zhide.app.eventBus.OkResponseEvent;
import com.zhide.app.eventBus.PayOrderEvent;
import com.zhide.app.eventBus.WaterPreBillEvent;
import com.zhide.app.eventBus.WaterSettleEvent;
import com.zhide.app.model.AliPayParamModel;
import com.zhide.app.model.ResponseModel;
import com.zhide.app.model.WXPayParamModel;
import com.zhide.app.model.WaterPreBillModel;
import com.zhide.app.model.WaterSettleModel;
import com.zhide.app.okhttp.DataManager;
import com.zhide.okhttputils.callback.GenericsCallback;
import com.zhide.okhttputils.request.JsonGenericsSerializator;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

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
        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(amount);//format 返回的是字符串
       double amounts = Double.parseDouble(p);


        JSONObject params = new JSONObject();
        try {
            params.put("USR_Money", amounts);
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

    public void getAliPayParams(float amount,long userId) {
        Log.d("admin", "getWeChatPayParams: amount=" + amount);
        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(amount);//format 返回的是字符串
        double amounts = Double.parseDouble(p);

        JSONObject params = new JSONObject();
        try {
            params.put("USR_Money", amounts);
            params.put("USI_Id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataManager.sendPostRequestData(CommonUrl.GET_ALIPAY_PARAMS, params)
                .execute(new GenericsCallback<AliPayParamModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(AliPayParamModel response, int id) {
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
    /**
     * 充钱到卡片
     */
    public void payToCard(long userId,float amount) {
        JSONObject params = new JSONObject();
        try {
            params.put("USI_Id", userId);
            params.put("TurnMoney", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataManager.sendPostRequestData(CommonUrl.payToCard, params)
                .execute(new GenericsCallback<ResponseModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(ResponseModel response, int id) {
                        EventBus.getDefault().post(new OkResponseEvent(response));
                    }
                });
    }
}
