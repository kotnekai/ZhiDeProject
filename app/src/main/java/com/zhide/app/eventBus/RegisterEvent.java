package com.zhide.app.eventBus;

import com.zhide.app.model.RegisterLoginModel;

/**
 * Create by Admin on 2018/9/7
 */
public class RegisterEvent {
    private RegisterLoginModel registerModel;

    public RegisterEvent(RegisterLoginModel registerModel) {
        this.registerModel = registerModel;
    }

    public RegisterLoginModel getRegisterModel() {
        return registerModel;
    }
}
