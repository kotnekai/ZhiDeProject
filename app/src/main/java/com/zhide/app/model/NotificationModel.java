package com.zhide.app.model;

/**
 * Created by hasee on 2018/6/7.
 */

public class NotificationModel {
    private String title;// 消息标题
    private long msgId;// 消息id
    private int  viewType;// 跳转页面


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
