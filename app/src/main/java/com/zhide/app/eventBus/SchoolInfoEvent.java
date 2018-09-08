package com.zhide.app.eventBus;

import com.zhide.app.model.SchoolInfoModel;

/**
 * Create by Admin on 2018/9/8
 */
public class SchoolInfoEvent {
    private SchoolInfoModel schoolInfoModel;

    public SchoolInfoEvent(SchoolInfoModel schoolInfoModel) {
        this.schoolInfoModel = schoolInfoModel;
    }

    public SchoolInfoModel getSchoolInfoModel() {
        return schoolInfoModel;
    }
}
