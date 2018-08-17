package com.zhide.app.eventBus;

import com.zhide.app.model.ApkInfoModel;

/**
 * Created by hasee on 2018/2/9.
 */

public class ApkInfoEvent {
    private ApkInfoModel apkInfoModel;
    private boolean isSelfCheck = false;
    public ApkInfoEvent(ApkInfoModel apkInfoModel, boolean isSelfCheck) {
        this.apkInfoModel = apkInfoModel;
        this.isSelfCheck = isSelfCheck;
    }

    public boolean isSelfCheck() {
        return isSelfCheck;
    }

    public ApkInfoModel getApkInfoModel() {
        return apkInfoModel;
    }
}
