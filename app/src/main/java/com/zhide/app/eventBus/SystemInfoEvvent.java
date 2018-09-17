package com.zhide.app.eventBus;

import com.zhide.app.model.SystemInfoModel;

/**
 * Create by Admin on 2018/9/14
 */
public class SystemInfoEvvent {
    private SystemInfoModel infoModel;

    public SystemInfoEvvent(SystemInfoModel infoModel) {
        this.infoModel = infoModel;
    }

    public SystemInfoModel getInfoModel() {
        return infoModel;
    }
}
