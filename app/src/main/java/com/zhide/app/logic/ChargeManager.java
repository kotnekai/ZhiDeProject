package com.zhide.app.logic;

import android.util.Log;

import com.zhide.app.common.CommonUrl;
import com.zhide.app.eventBus.PayOrderEvent;
import com.zhide.app.model.WXPayParamModel;
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

    public void getWeChatPayParams(float amount) {
        JSONObject params = new JSONObject();
        try {
            params.put("amount", amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataManager.sendPostRequestData(CommonUrl.GET_WECHAT_PARAMS, params)
                .execute(new GenericsCallback<WXPayParamModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                      /*  String errorMsg = JsonUtils.getErrorMsg(response);
                        EventBus.getDefault().post(new ErrorResponseEvent(errorMsg, CommonPageState.login_page));*/
                        Log.d("xyc", "onError: message=" + message);
                    }

                    @Override
                    public void onResponse(WXPayParamModel response, int id) {
                        Log.d("xyc", "onResponse: response=" + response);
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
                      /*  String errorMsg = JsonUtils.getErrorMsg(response);
                        EventBus.getDefault().post(new ErrorResponseEvent(errorMsg, CommonPageState.login_page));*/
                        Log.d("xyc", "onError: message=" + message);
                    }

                    @Override
                    public void onResponse(WXPayParamModel response, int id) {
                        Log.d("xyc", "onResponse: response=" + response);
                        EventBus.getDefault().post(new PayOrderEvent(response, false));
                    }
                });
    }
}
