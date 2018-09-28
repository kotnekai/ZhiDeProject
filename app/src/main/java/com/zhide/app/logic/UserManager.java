package com.zhide.app.logic;

import android.util.Log;

import com.zhide.app.common.CommonUrl;
import com.zhide.app.eventBus.ErrorMsgEvent;
import com.zhide.app.eventBus.LoginEvent;
import com.zhide.app.eventBus.ModifyPswEvent;
import com.zhide.app.eventBus.OkResponseEvent;
import com.zhide.app.eventBus.RegisterEvent;
import com.zhide.app.eventBus.SchoolInfoEvent;
import com.zhide.app.eventBus.UserInfoEvent;
import com.zhide.app.eventBus.UserInfoSchoolInfoEvent;
import com.zhide.app.model.RegisterLoginModel;
import com.zhide.app.model.ResponseModel;
import com.zhide.app.model.SchoolInfoModel;
import com.zhide.app.model.UserData;
import com.zhide.app.model.UserInfoModel;
import com.zhide.app.model.UserSchoolDataModel;
import com.zhide.app.model.WithdrawModel;
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

    /**
     * 登录页面
     *
     * @param userName
     * @param password
     */
    public void login(String userName, String password) {
        JSONObject params = new JSONObject();
        try {
            params.put("USI_Mobile", userName);
            params.put("USI_Pwd", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.login, params)
                .execute(new GenericsCallback<RegisterLoginModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(RegisterLoginModel response, int id) {
                        EventBus.getDefault().post(new LoginEvent(response));
                        Log.d("admin", "onResponse: response=" + response.getCode() + "-" + response.getMessage());
                    }
                });

    }

    /**
     * 发送验证码
     *
     * @param mobile
     */
    public void sendSmsCode(String mobile) {

        JSONObject params = new JSONObject();
        try {
            params.put("USI_Mobile", mobile);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.sendSmsCode, params)
                .execute(new GenericsCallback<ResponseModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
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
                .execute(new GenericsCallback<RegisterLoginModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(RegisterLoginModel response, int id) {
                        Log.d("admin", "onResponse: response=" + response.getCode() + "-" + response.getMessage());
                        EventBus.getDefault().post(new RegisterEvent(response));
                    }
                });
    }

    /**
     * 修改密码
     */
    public void modifyPassword(String userId, String password) {
        JSONObject params = new JSONObject();
        try {
            params.put("USI_Id", userId);
            params.put("USI_Pwd", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.modifyPassword, params)
                .execute(new GenericsCallback<ResponseModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(ResponseModel response, int id) {
                        Log.d("admin", "onResponse: response=" + response.getCode() + "-" + response.getMsg());
                        EventBus.getDefault().post(new ModifyPswEvent(response));
                    }
                });
    }


    /**
     * 请求用户学校信息
     *
     * @param userId
     */
    public void getUserSchoolInfo(long userId) {
        JSONObject params = new JSONObject();
        try {
            params.put("USI_Id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getSchoolData(params);
    }

    /**
     * 请求用户学校信息
     *
     * @param guideStr
     */
    public void getUserSchoolInfo(String guideStr) {
        JSONObject params = new JSONObject();
        try {
            params.put("SI_Code", guideStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getSchoolData(params);
    }

    private void getSchoolData(JSONObject params) {
        dataInstance.sendPostRequestData(CommonUrl.getUserSchoolInfo, params)
                .execute(new GenericsCallback<SchoolInfoModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(SchoolInfoModel response, int id) {
                        Log.d("admin", "onResponse: response=" + response.getCode() + "-" + response.getMsg());
                        EventBus.getDefault().post(new SchoolInfoEvent(response));
                    }
                });
    }

    /**
     * 请求学生个人信息
     *
     * @param userId
     */
    public void getUserInfoById(long userId, final int fromPage) {
        JSONObject params = new JSONObject();
        //Long.parseLong(userId)
        try {
            params.put("USI_Id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.getUserInfoById, params)
                .execute(new GenericsCallback<UserInfoModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(UserInfoModel response, int id) {
                        UserData userData1 = response.getData();
                        if (userData1 == null) {
                            return;
                        }
                        EventBus.getDefault().post(new UserInfoEvent(userData1, fromPage));
                    }
                });
    }

    /**
     * 请求学生和学校个人信息
     *
     * @param userId
     */
    public void getUserSchoolInfoById(long userId, final int fromPage) {
        JSONObject params = new JSONObject();
        //Long.parseLong(userId)
        try {
            params.put("USI_Id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.getUserInfoSchoolInfo, params)
                .execute(new GenericsCallback<UserSchoolDataModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(UserSchoolDataModel response, int id) {
                        EventBus.getDefault().post(new UserInfoSchoolInfoEvent(response, fromPage));
                    }
                });
    }


    public void doWithdraw(WithdrawModel withdrawModel) {
        JSONObject params = new JSONObject();
        try {
            params.put("USI_Id", withdrawModel.getUSI_Id());
            params.put("USW_Money", withdrawModel.getUSW_Money());
            params.put("USW_Account", withdrawModel.getUSW_Account());
            params.put("USW_AccountName", withdrawModel.getUSW_AccountName());
            params.put("USW_ContactMobile", withdrawModel.getUSW_ContactMobile());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.doWithdraw, params)
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

    /**
     * 重置登录密码
     * @param userId
     * @param newPsw
     * @param oldPsw
     */
    public void resetLoginPsw(long userId,String newPsw,String oldPsw) {
        JSONObject params = new JSONObject();
        try {
            params.put("USI_Id", userId);
            params.put("USI_Pwd", newPsw);
            params.put("USI_OldPwd", oldPsw);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.resetLoginPsw, params)
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
