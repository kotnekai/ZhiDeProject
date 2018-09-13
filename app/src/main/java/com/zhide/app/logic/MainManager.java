package com.zhide.app.logic;

import android.util.Log;

import com.zhide.app.common.CommonUrl;
import com.zhide.app.eventBus.ErrorMsgEvent;
import com.zhide.app.eventBus.NewsModelEvent;
import com.zhide.app.eventBus.SaveInfoEvent;
import com.zhide.app.model.NewsModel;
import com.zhide.app.model.ResponseModel;
import com.zhide.app.model.UserData;
import com.zhide.app.model.UserInfoModel;
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
     * fromPage ==1 首页 ==2 更多页
     */
    public void getMainPageNews(final int fromPage) {

        JSONObject params = new JSONObject();
        try {
            params.put("ActionMethod", "news");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.getMainPageNews, params)
                .execute(new GenericsCallback<NewsModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(NewsModel response, int id) {
                        EventBus.getDefault().post(new NewsModelEvent(response,fromPage));
                    }
                });

    }

    /**
     * 保存个人信息
     *
     * @param userData
     */
    public void savePersonInfo(UserData userData) {
        JSONObject params = new JSONObject();
        try {
            params.put("USI_Id", userData.getUSI_Id());
            params.put("USI_TrueName", userData.getUSI_TrueName());
            params.put("USI_SchoolRoomNo", userData.getUSI_SchoolRoomNo());
            params.put("USI_Sex", userData.getUSI_Sex());
            params.put("USI_IDCard", userData.getUSI_IDCard());
            params.put("SI_Code", userData.getSI_ID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.savePersonInfo, params)
                .execute(new GenericsCallback<ResponseModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(ResponseModel response, int id) {
                        EventBus.getDefault().post(new SaveInfoEvent(response));
                    }
                });
    }

}
