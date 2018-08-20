package com.zhide.app.model;

/**
 * Create by Admin on 2018/8/20
 */
public class ReChargeModel {
    private float totalBalance;//账户基本余额
    private float cashBalance;//现金余额
    private float giftBalance;//赠送余额
    private String chargeActTitle;//充值活动说明
    private String chargeTitle;//充值说明

    public float getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(float totalBalance) {
        this.totalBalance = totalBalance;
    }

    public float getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(float cashBalance) {
        this.cashBalance = cashBalance;
    }

    public float getGiftBalance() {
        return giftBalance;
    }

    public void setGiftBalance(float giftBalance) {
        this.giftBalance = giftBalance;
    }

    public String getChargeActTitle() {
        return chargeActTitle;
    }

    public void setChargeActTitle(String chargeActTitle) {
        this.chargeActTitle = chargeActTitle;
    }

    public String getChargeTitle() {
        return chargeTitle;
    }

    public void setChargeTitle(String chargeTitle) {
        this.chargeTitle = chargeTitle;
    }
}
