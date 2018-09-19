package com.zhide.app.model;

/**
 * Create by Admin on 2018/9/19
 */
public class WithdrawModel {
    private long USI_Id;
    private float USW_Money;
    private String USW_Account;
    private String USW_AccountName;
    private String USW_ContactMobile;

    public long getUSI_Id() {
        return USI_Id;
    }

    public void setUSI_Id(long USI_Id) {
        this.USI_Id = USI_Id;
    }

    public float getUSW_Money() {
        return USW_Money;
    }

    public void setUSW_Money(float USW_Money) {
        this.USW_Money = USW_Money;
    }

    public String getUSW_Account() {
        return USW_Account;
    }

    public void setUSW_Account(String USW_Account) {
        this.USW_Account = USW_Account;
    }

    public String getUSW_AccountName() {
        return USW_AccountName;
    }

    public void setUSW_AccountName(String USW_AccountName) {
        this.USW_AccountName = USW_AccountName;
    }

    public String getUSW_ContactMobile() {
        return USW_ContactMobile;
    }

    public void setUSW_ContactMobile(String USW_ContactMobile) {
        this.USW_ContactMobile = USW_ContactMobile;
    }
}
