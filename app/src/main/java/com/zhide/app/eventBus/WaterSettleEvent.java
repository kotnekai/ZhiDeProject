package com.zhide.app.eventBus;

import com.zhide.app.model.WaterSettleModel;

/**
 * Create by Admin on 2018/9/19
 */
public class WaterSettleEvent {
    private WaterSettleModel settleModel;

    public WaterSettleEvent(WaterSettleModel settleModel) {
        this.settleModel = settleModel;
    }

    public WaterSettleModel getSettleModel() {
        return settleModel;
    }
}
