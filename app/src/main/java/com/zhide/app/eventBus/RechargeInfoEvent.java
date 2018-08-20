package com.zhide.app.eventBus;

import com.zhide.app.model.ReChargeModel;

/**
 * Create by Admin on 2018/8/20
 */
public class RechargeInfoEvent {
    private ReChargeModel chargeModel;

    public RechargeInfoEvent(ReChargeModel chargeModel) {
        this.chargeModel = chargeModel;
    }

    public ReChargeModel getChargeModel() {
        return chargeModel;
    }
}
