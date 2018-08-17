package com.zhide.app.model;

public class DefaultEventModel {
    private int currentPageType;
    private String contentStr;

    public DefaultEventModel(int currentPageType, String contentStr) {
        this.currentPageType = currentPageType;
        this.contentStr = contentStr;
    }

    public int getCurrentPageType() {
        return currentPageType;
    }

    public void setCurrentPageType(int currentPageType) {
        this.currentPageType = currentPageType;
    }

    public String getContentStr() {
        return contentStr;
    }

    public void setContentStr(String contentStr) {
        this.contentStr = contentStr;
    }
}
