package com.zhide.app.eventBus;

import com.zhide.app.model.UserData;

/**
 * Create by Admin on 2018/9/8
 */
public class UserInfoEvent {
    private UserData userData;
   private int  updatePage = 1;//1 首页，2 关于页 ，3 我的 页

    public UserInfoEvent(UserData userData, int updatePage) {
        this.userData = userData;
        this.updatePage = updatePage;
    }

    public int getUpdatePage() {
        return updatePage;
    }

    public UserData getUserData() {
        return userData;
    }
}
