package com.zhide.app.eventBus;

/**
 * Create by Admin on 2018/9/26
 */
public class PayResultEvent {
    private int code;
    private String msg;

    public PayResultEvent(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public PayResultEvent(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
