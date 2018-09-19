package com.zhide.app.eventBus;

import com.zhide.app.model.SchoolInfoModel;
import com.zhide.app.model.UserSchoolDataModel;

/**
 * Create by Admin on 2018/9/8
 */
public class UserInfoSchoolInfoEvent {
    private UserSchoolDataModel userSchoolDataModel;
    private int  updatePage = 1;//1 首页，2 关于页 ，3 我的 页
    public UserInfoSchoolInfoEvent(UserSchoolDataModel userSchoolDataModel, int updatePage) {
        this.userSchoolDataModel = userSchoolDataModel;
        this.updatePage = updatePage;
    }
    public int getUpdatePage() {
        return updatePage;
    }
    public UserSchoolDataModel getUserSchoolDataModel() {
        return userSchoolDataModel;
    }
}
