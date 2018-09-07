package com.zhide.app.eventBus;

import com.zhide.app.model.RegisterLoginModel;

public class LoginEvent {
   private RegisterLoginModel dataModel;

    public LoginEvent(RegisterLoginModel dataModel) {
        this.dataModel = dataModel;
    }

    public RegisterLoginModel getDataModel() {
        return dataModel;
    }
}
