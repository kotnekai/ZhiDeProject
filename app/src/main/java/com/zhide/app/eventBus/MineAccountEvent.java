package com.zhide.app.eventBus;

import com.zhide.app.model.AccountInfoModel;

/**
 * Create by Admin on 2018/8/20
 */
public class MineAccountEvent {
    private AccountInfoModel infoModel;

    public MineAccountEvent(AccountInfoModel infoModel) {
        this.infoModel = infoModel;
    }

    public AccountInfoModel getInfoModel() {
        return infoModel;
    }
}
