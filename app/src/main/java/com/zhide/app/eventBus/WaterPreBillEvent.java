package com.zhide.app.eventBus;

import com.zhide.app.model.WaterPreBillModel;

/**
 * Create by Admin on 2018/9/19
 */
public class WaterPreBillEvent {
    private WaterPreBillModel waterPreBillModel;

    public WaterPreBillEvent(WaterPreBillModel waterPreBillModel) {
        this.waterPreBillModel = waterPreBillModel;
    }

    public WaterPreBillModel getWaterPreBillModel() {
        return waterPreBillModel;
    }
}
