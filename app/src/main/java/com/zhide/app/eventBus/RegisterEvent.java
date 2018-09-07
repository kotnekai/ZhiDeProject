package com.zhide.app.eventBus;

import com.zhide.app.model.RegisterModel;

/**
 * Create by Admin on 2018/9/7
 */
public class RegisterEvent {
    private RegisterModel registerModel;

    public RegisterEvent(RegisterModel registerModel) {
        this.registerModel = registerModel;
    }

    public RegisterModel getRegisterModel() {
        return registerModel;
    }
}
