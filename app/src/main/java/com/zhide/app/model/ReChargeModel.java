package com.zhide.app.model;

/**
 * Create by Admin on 2018/8/20
 */
public class ReChargeModel {
    private Float totalBalance;//账户基本余额
    private Float cashBalance;//现金余额
    private Float giftBalance;//赠送余额
    private String chargeActTitle;//充值活动说明
    private String chargeTitle;//充值说明

    public Float getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Float totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Float getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(Float cashBalance) {
        this.cashBalance = cashBalance;
    }

    public Float getGiftBalance() {
        return giftBalance;
    }

    public void setGiftBalance(Float giftBalance) {
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
