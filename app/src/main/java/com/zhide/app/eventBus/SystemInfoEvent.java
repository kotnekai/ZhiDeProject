package com.zhide.app.eventBus;

import com.zhide.app.model.SystemInfoModel;

/**
 * Create by Admin on 2018/9/14
 */
public class SystemInfoEvent {
    private SystemInfoModel infoModel;
   private int pageType;
    public SystemInfoEvent(SystemInfoModel infoModel,int pageType) {
        this.infoModel = infoModel;
        this.pageType =pageType;
    }

    public SystemInfoModel getInfoModel() {
        return infoModel;
    }

    public int getPageType() {
        return pageType;
    }
}
