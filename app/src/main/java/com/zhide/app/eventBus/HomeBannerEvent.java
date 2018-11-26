package com.zhide.app.eventBus;

import com.zhide.app.model.SystemInfoModel;

/**
 * Create by Admin on 2018/9/14
 */
public class HomeBannerEvent {
    private SystemInfoModel infoModel;

    public HomeBannerEvent(SystemInfoModel infoModel) {
        this.infoModel = infoModel;
    }

    public SystemInfoModel getInfoModel() {
        return infoModel;
    }

}
