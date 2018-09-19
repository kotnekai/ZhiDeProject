package com.zhide.app.model;

import java.util.List;

/**
 * Create by Admin on 2018/9/18
 */
public class BreakdownModel {
    private int code;
    private String message;

    private List<BreakdownDeviceModel> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BreakdownDeviceModel> getData() {
        return data;
    }

    public void setData(List<BreakdownDeviceModel> data) {
        this.data = data;
    }
}
