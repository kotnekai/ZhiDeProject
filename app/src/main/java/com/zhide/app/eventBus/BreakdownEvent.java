package com.zhide.app.eventBus;

import com.zhide.app.model.BreakdownModel;

/**
 * Create by Admin on 2018/9/18
 */
public class BreakdownEvent {
    private BreakdownModel breakdownModel;

    public BreakdownEvent(BreakdownModel breakdownModel) {
        this.breakdownModel = breakdownModel;
    }

    public BreakdownModel getBreakdownModel() {
        return breakdownModel;
    }
}
