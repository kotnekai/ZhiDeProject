package com.zhide.app.model;

/**
 * Created by hasee on 2018/2/9.
 */

public class ApkInfoModel {
    private String apkVersion;
    private String apkPath;
    private String apkDescription;
    private long lastUpdateTime;
    private long apkSize;
    private int isForceUpdate;//是否强制更新 0/不强制 1/强制更新

    public int getIsForceUpdate() {
        return isForceUpdate;
    }

    public void setIsForceUpdate(int isForceUpdate) {
        this.isForceUpdate = isForceUpdate;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public long getApkSize() {
        return apkSize;
    }

    public void setApkSize(long apkSize) {
        this.apkSize = apkSize;
    }

    public String getApkVersion() {
        return apkVersion;
    }

    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public String getApkDescription() {
        return apkDescription;
    }

    public void setApkDescription(String apkDescription) {
        this.apkDescription = apkDescription;
    }

    @Override
    public String toString() {
        return "ApkInfoModel{" +
                "apkVersion='" + apkVersion + '\'' +
                ", apkPath='" + apkPath + '\'' +
                ", apkDescription='" + apkDescription + '\'' +
                ", lastUpdateTime=" + lastUpdateTime +
                ", apkSize=" + apkSize +
                '}';
    }
}
