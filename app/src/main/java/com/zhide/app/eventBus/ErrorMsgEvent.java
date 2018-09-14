package com.zhide.app.eventBus;

/**
 * Create by Admin on 2018/9/13
 */
public class ErrorMsgEvent {
    private String messagge;

    public ErrorMsgEvent(String messagge) {
        this.messagge = messagge;
    }

    public String getMessagge() {
        return messagge;
    }
}
