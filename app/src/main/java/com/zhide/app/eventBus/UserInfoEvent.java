package com.zhide.app.eventBus;

import com.zhide.app.model.UserData;

/**
 * Create by Admin on 2018/9/8
 */
public class UserInfoEvent {
    private UserData userData;

    public UserInfoEvent(UserData userData) {
        this.userData = userData;
    }

    public UserData getUserData() {
        return userData;
    }
}
