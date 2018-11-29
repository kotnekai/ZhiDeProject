package com.zhide.app.eventBus;

import com.zhide.app.model.RoomInfoModel;

/**
 * Create by Admin on 2018/11/15
 */
public class RoomInfoEvent2 {
    private RoomInfoModel infoModel;
    public RoomInfoEvent2(RoomInfoModel infoModel) {
        this.infoModel = infoModel;
    }

    public RoomInfoModel getInfoModel() {
        return infoModel;
    }

}
