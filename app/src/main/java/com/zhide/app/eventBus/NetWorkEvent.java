package com.zhide.app.eventBus;

import android.net.NetworkInfo;


/**
 * Create by Admin on 2018/9/19
 */
public class NetWorkEvent {
    private boolean isConnected;

    public NetWorkEvent(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean getNetWorkStatus() {
        return isConnected;
    }
}
