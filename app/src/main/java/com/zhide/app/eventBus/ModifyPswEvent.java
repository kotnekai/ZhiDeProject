package com.zhide.app.eventBus;

import com.zhide.app.model.ResponseModel;

/**
 * Create by Admin on 2018/9/7
 */
public class ModifyPswEvent {
    private ResponseModel responseModel;

    public ModifyPswEvent(ResponseModel responseModel) {
        this.responseModel = responseModel;
    }

    public ResponseModel getResponseModel() {
        return responseModel;
    }
}
