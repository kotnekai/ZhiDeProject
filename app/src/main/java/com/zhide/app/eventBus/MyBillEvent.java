package com.zhide.app.eventBus;

import com.zhide.app.model.MyBillModel;

/**
 * Create by Admin on 2018/9/13
 */
public class MyBillEvent {
    private MyBillModel myBillModel;

    public MyBillEvent(MyBillModel myBillModel) {
        this.myBillModel = myBillModel;
    }

    public MyBillModel getMyBillModel() {
        return myBillModel;
    }
}
