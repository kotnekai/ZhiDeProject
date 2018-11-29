package com.zhide.app.logic;

import com.zhide.app.common.CommonParams;
import com.zhide.app.common.CommonUrl;
import com.zhide.app.eventBus.BreakdownEvent;
import com.zhide.app.eventBus.ErrorMsgEvent;
import com.zhide.app.eventBus.GuideModelEvent;
import com.zhide.app.eventBus.HomeBannerEvent;
import com.zhide.app.eventBus.NewsModelEvent;
import com.zhide.app.eventBus.OkResponseEvent;
import com.zhide.app.eventBus.RoomInfoEvent;
import com.zhide.app.eventBus.RoomInfoEvent2;
import com.zhide.app.eventBus.SaveInfoEvent;
import com.zhide.app.eventBus.SystemInfoEvent;
import com.zhide.app.model.BreakdownModel;
import com.zhide.app.model.GuideModel;
import com.zhide.app.model.NewsModel;
import com.zhide.app.model.ResponseModel;
import com.zhide.app.model.RoomInfoModel;
import com.zhide.app.model.SystemInfoModel;
import com.zhide.app.model.UserData;
import com.zhide.app.okhttp.DataManager;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.okhttputils.callback.GenericsCallback;
import com.zhide.okhttputils.request.JsonGenericsSerializator;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Create by Admin on 2018/9/7
 */
public class MainManager {
    private static MainManager instance = null;
    private static DataManager dataInstance = null;
    private final long userId;

    private MainManager() {
        userId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID, 0);

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
            params.put("USI_Id", userId);

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
                        EventBus.getDefault().post(new NewsModelEvent(response, fromPage));
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
            params.put("USI_SchoolNo", userData.getUSI_SchoolNo());
            params.put("USI_SchoolRoomNo", userData.getUSI_SchoolRoomNo());
            params.put("USI_Sex", userData.getUSI_Sex());
            params.put("USI_IDCard", userData.getUSI_IDCard());
            params.put("SI_Code", userData.getSI_Code());
            params.put("USI_Card_SN_PIN", userData.getUSI_Card_SN_PIN());
            params.put("SDI_Id",userData.getSDI_Id());
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

    /**
     * 操作指引接口
     */
    public void getGuideList() {
        JSONObject params = new JSONObject();
        try {
            params.put("ActionMethod", "guide");
            params.put("USI_Id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.getGuideList, params)
                .execute(new GenericsCallback<GuideModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(GuideModel response, int id) {
                        EventBus.getDefault().post(new GuideModelEvent(response));
                    }
                });
    }

    /**
     * 获取系统信息，注册协议，app更新信息，电话号码
     */
    public void getSystemInfo(final int pageType) {
        JSONObject params = new JSONObject();
        try {
            params.put("ActionMethod", "systeminfo");
            params.put("USI_Id", userId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.getSystemInfo, params)
                .execute(new GenericsCallback<SystemInfoModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(SystemInfoModel response, int id) {
                        EventBus.getDefault().post(new SystemInfoEvent(response, pageType));
                    }
                });
    }

    /**
     * 故障列表
     */
    public void getBreakdownType() {
        JSONObject params = new JSONObject();
        try {
            params.put("ActionMethod", "getbreakdowntype");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.getBreakdownType, params)
                .execute(new GenericsCallback<BreakdownModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(BreakdownModel response, int id) {
                        EventBus.getDefault().post(new BreakdownEvent(response));
                    }
                });
    }

    public void submitBreakInfo(String device, String reson, long userId, String content) {
        JSONObject params = new JSONObject();
        try {
            params.put("ZBI_DeviceType", device);
            params.put("ZBI_Type", reson);
            params.put("USI_Id", userId);
            params.put("ZBI_Content", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.submitBreakInfo, params)
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
     * apk 信息
     *
     * @param infoModel
     * @return
     */
    public SystemInfoModel.SystemData getSystemModel(int type, SystemInfoModel infoModel) {
        List<SystemInfoModel.SystemData> data = infoModel.getData();
        SystemInfoModel.SystemData systemData = null;
        if (data == null || data.size() == 0) {
            return null;
        }
        for (int i = 0; i < data.size(); i++) {
            if (type == data.get(i).getNI_Id()) {
                systemData = data.get(i);
                break;
            }
        }
        return systemData;
    }

    public void getSchoolRoom(long userId, long parentId, final String type) {
        JSONObject params = new JSONObject();
        try {
            params.put("USI_Id", userId);
            params.put("SDI_ParentId", parentId);
            params.put("SDI_Type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.getSchoolRoom, params)
                .execute(new GenericsCallback<RoomInfoModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(RoomInfoModel response, int id) {
                        EventBus.getDefault().post(new RoomInfoEvent(response, type));
                    }
                });
    }

    /**
     * 请求宿舍信息2
     * @param sdiId
     */
    public void getSchoolRoom2(long sdiId){
        JSONObject params = new JSONObject();
        try {
            params.put("SDI_Id", sdiId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.getSchoolRoom2, params)
                .execute(new GenericsCallback<RoomInfoModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(RoomInfoModel response, int id) {
                        EventBus.getDefault().post(new RoomInfoEvent2(response));
                    }
                });
    }


    /**
     * 获取广告banner轮播数据
     */
    public void getHomeBanner() {
        JSONObject params = new JSONObject();
        try {
            params.put("ActionMethod", "banner");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dataInstance.sendPostRequestData(CommonUrl.home_banner, params)
                .execute(new GenericsCallback<SystemInfoModel>(new JsonGenericsSerializator()) {
                    @Override
                    public void onError(Response response, Call call, Exception e, int id) {
                        String message = e.getMessage();
                        EventBus.getDefault().post(new ErrorMsgEvent(message));
                    }

                    @Override
                    public void onResponse(SystemInfoModel response, int id) {
                        EventBus.getDefault().post(new HomeBannerEvent(response));
                    }
                });
    }
}
