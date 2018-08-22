package com.zhide.app.model;

/**
 * Create by Admin on 2018/8/22
 */
public class MyBillModel {
    private String typeContent;// 类型描述
    private String timeData;// 时间
    private float transMoney;// 交易金额
    private int  moneyType;//1是支出 2是收入

    public MyBillModel(String typeContent, String timeData, float transMoney, int moneyType) {
        this.typeContent = typeContent;
        this.timeData = timeData;
        this.transMoney = transMoney;
        this.moneyType = moneyType;
    }

    public String getTypeContent() {
        return typeContent;
    }

    public int getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(int moneyType) {
        this.moneyType = moneyType;
    }

    public void setTypeContent(String typeContent) {
        this.typeContent = typeContent;
    }

    public String getTimeData() {
        return timeData;
    }

    public void setTimeData(String timeData) {
        this.timeData = timeData;
    }

    public float getTransMoney() {
        return transMoney;
    }

    public void setTransMoney(float transMoney) {
        this.transMoney = transMoney;
    }
}
