package com.zhide.app.eventBus;

import com.zhide.app.model.UserInfoModel;

/**
 * Create by Admin on 2018/9/8
 */
public class UserInfoEvent {
    private UserInfoModel userInfoModel;

    public UserInfoEvent(UserInfoModel userInfoModel) {
        this.userInfoModel = userInfoModel;
    }

    public UserInfoModel getUserInfoModel() {
        return userInfoModel;
    }
}
