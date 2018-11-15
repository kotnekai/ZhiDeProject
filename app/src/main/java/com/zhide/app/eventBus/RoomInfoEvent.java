package com.zhide.app.eventBus;

import com.zhide.app.model.RoomInfoModel;

/**
 * Create by Admin on 2018/11/15
 */
public class RoomInfoEvent {
    private RoomInfoModel infoModel;
    private String requestType;
    public RoomInfoEvent(RoomInfoModel infoModel,String requestType) {
        this.infoModel = infoModel;
        this.requestType = requestType;
    }

    public RoomInfoModel getInfoModel() {
        return infoModel;
    }

    public String getRequestType() {
        return requestType;
    }
}
