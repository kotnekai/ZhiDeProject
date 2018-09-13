package com.zhide.app.eventBus;

import com.zhide.app.model.ResponseModel;

/**
 * Create by Admin on 2018/9/13
 */
public class OkResponseEvent {
    private ResponseModel responseModel;

    public OkResponseEvent(ResponseModel responseModel) {
        this.responseModel = responseModel;
    }

    public ResponseModel getResponseModel() {
        return responseModel;
    }
}
