package com.zhide.app.logic;

import android.util.Log;

import com.zhide.app.common.CommonUrl;
import com.zhide.app.eventBus.CardBillEvent;
import com.zhide.app.model.CardBillModel;
import com.zhide.app.model.ResponseModel;
import com.zhide.app.okhttp.DataManager;
import com.zhide.okhttputils.callback.GenericsCallback;
import com.zhide.okhttputils.request.JsonGenericsSerializator;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Create by Admin on 2018/9/7
 */
public class BillManager {
    private static BillManager instance = null;
    private static DataManager dataInstance = null;

    private BillManager() {
    }

    public static BillManager getInstance() {
        if (instance == null) {
            instance = new BillManager();
        }
        if (dataInstance == null) {
            dataInstance = DataManager.getInstance();
        }
        return instance;
    }

    /**
     * 获取卡账单信息
     * @param userId
     */
    public void getCardBillData(String userId){
        JSONObject params = new JSONObject();
        try {
            params.put("USI_Id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.getCardBillData, params)
                .execute(new GenericsCallback<CardBillModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                      /*  String errorMsg = JsonUtils.getErrorMsg(response);
                        EventBus.getDefault().post(new ErrorResponseEvent(errorMsg, CommonPageState.login_page));*/
                        Log.d("admin", "onError: message=" + message);
                    }

                    @Override
                    public void onResponse(CardBillModel response, int id) {
                        EventBus.getDefault().post(new CardBillEvent(response));
                       // Log.d("admin", "onResponse: response=" + response.getCode() + "-" + response.getMsg());
                    }
                });
    }

}
