package com.zhide.app.logic;

import android.util.Log;

import com.zhide.app.common.CommonUrl;
import com.zhide.app.model.ResponseModel;
import com.zhide.app.okhttp.DataManager;
import com.zhide.okhttputils.callback.GenericsCallback;
import com.zhide.okhttputils.request.JsonGenericsSerializator;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Create by Admin on 2018/9/7
 */
public class MainManager {
    private static MainManager instance = null;
    private static DataManager dataInstance = null;

    private MainManager() {
    }

    public static MainManager getInstance() {
        if (instance == null) {
            instance = new MainManager();
        }
        if (dataInstance == null) {
            dataInstance = DataManager.getInstance();
        }
        return instance;
    }

    /**
     * 请求首页新闻消息列表
     */
    public void getMainPageNews() {

        JSONObject params = new JSONObject();
        try {
            params.put("ActionMethod ", "news");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.getMainPageNews, params)
                .execute(new GenericsCallback<ResponseModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                      /*  String errorMsg = JsonUtils.getErrorMsg(response);
                        EventBus.getDefault().post(new ErrorResponseEvent(errorMsg, CommonPageState.login_page));*/
                        Log.d("admin", "onError: message=" + message);
                    }

                    @Override
                    public void onResponse(ResponseModel response, int id) {

                        Log.d("admin", "onResponse: response=" + response.getCode() + "-" + response.getMsg());
                    }
                });

    }



}
