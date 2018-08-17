package com.zhide.app.model;

/**
 * Created by hasee on 2018/2/6.
 */

public class VersionModel {
    private int versionCode;
    private String versionName;
    private long lastUpdateTime;

    public VersionModel(int versionCode, String versionName, long lastUpdateTime) {
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.lastUpdateTime = lastUpdateTime;
    }

    public VersionModel() {
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
