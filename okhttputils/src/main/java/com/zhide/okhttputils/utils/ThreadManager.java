package com.zhide.okhttputils.utils;

import android.os.Handler;


/**
 * Created by hasee on 2018/2/27.
 */

public class ThreadManager {

    public static ThreadManager instance = null;

    private ThreadManager() {

    }

    public static ThreadManager getInstance() {
        if (instance == null) {
            instance = new ThreadManager();
        }
        return instance;
    }

    // 获取子线程
    public Handler getBackgroundHandler() {
        return DbTaskManager.getInstance().getReadHandler();
    }
}
