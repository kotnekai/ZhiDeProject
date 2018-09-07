package com.zhide.app.logic;

import android.util.Log;

import com.zhide.app.common.CommonUrl;
import com.zhide.app.eventBus.ModifyPswEvent;
import com.zhide.app.eventBus.RegisterEvent;
import com.zhide.app.model.RegisterModel;
import com.zhide.app.model.ResponseModel;
import com.zhide.app.okhttp.DataManager;
import com.zhide.okhttputils.callback.GenericsCallback;
import com.zhide.okhttputils.request.JsonGenericsSerializator;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

public class UserManager {
    private static UserManager instance = null;
    private static DataManager dataInstance = null;

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        if (dataInstance == null) {
            dataInstance = DataManager.getInstance();
        }
        return instance;
    }

    public void login(String userName, String password) {
        JSONObject params = new JSONObject();
        try {
            params.put("userName", userName);
            params.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 发送验证码
     *
     * @param mobile
     */
    public void sendSmsCode(String mobile) {
        dataInstance.sendSinglePostRequestData(CommonUrl.sendSmsCode, mobile)
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

    /**
     * 注册接口
     */
    public void registerUser(String phoneNumber, String password, String verifyCode) {
        JSONObject params = new JSONObject();
        try {
            params.put("USI_Mobile", phoneNumber);
            params.put("USI_Pwd", password);
            params.put("USI_RegChannel", "安卓APP");
            params.put("USI_SMSCode", verifyCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.registerUser, params)
                .execute(new GenericsCallback<RegisterModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                      /*  String errorMsg = JsonUtils.getErrorMsg(response);
                        EventBus.getDefault().post(new ErrorResponseEvent(errorMsg, CommonPageState.login_page));*/
                        Log.d("admin", "onError: message=" + message);
                    }

                    @Override
                    public void onResponse(RegisterModel response, int id) {
                        Log.d("admin", "onResponse: response=" + response.getCode() + "-" + response.getMsg());
                        EventBus.getDefault().post(new RegisterEvent(response));
                    }
                });
    }

    /**
     * 修改密码
     */
    public void modifyPassword(String userId, String password, String payPassword) {
        JSONObject params = new JSONObject();
        try {
            params.put("USI_Id", userId);
            params.put("USI_Pwd", password);
            if (payPassword != null) {
                params.put("USI_PayPwd", payPassword);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.modifyPassword, params)
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
                        EventBus.getDefault().post(new ModifyPswEvent(response));
                    }
                });
    }

}
