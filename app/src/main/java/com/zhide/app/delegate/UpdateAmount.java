package com.zhide.app.delegate;

import com.zhide.app.eventBus.UserInfoEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Create by Admin on 2018/11/14
 */
public abstract class UpdateAmount {
    public abstract void onUserInfoUpdate(UserInfoEvent event);
    /**
     * 用户信息，更新账户可用余额用的
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserInfoEvent(UserInfoEvent event) {
        onUserInfoUpdate(event);
    }
}
