package com.zhide.app.apkUpdate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.zhide.app.common.ApplicationHolder;
import com.zhide.app.model.SystemInfoModel;
import com.zhide.app.model.VersionModel;
import com.zhide.app.okhttp.DataManager;
import com.zhide.app.okhttp.MyOkhttpUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hasee on 2018/2/5.
 */

public class AppUpdateManager {

    public static AppUpdateManager instance = null;
    private static DataManager dataInstance = null;

    private AppUpdateManager() {

    }

    public static AppUpdateManager getInstance() {
        if (instance == null) {
            instance = new AppUpdateManager();
        }
        if (dataInstance == null) {
            dataInstance = DataManager.getInstance();
        }
        return instance;
    }

    public long getContentLength(String url) {
        if (url == null) {
            return 0;
        }
        OkHttpClient okhttpClient = MyOkhttpUtils.getOkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = okhttpClient.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                response.body().close();
                return contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean needUpdateApk(SystemInfoModel.SystemData systemModel) {
        if (systemModel == null) {
            return false;
        }
        String apkVersion = systemModel.getNI_Title();
        VersionModel versionModel = ApplicationHolder.getInstance().getVersionModel();
        if (versionModel == null || apkVersion == null) {
            return false;
        }
        String versionName = versionModel.getVersionName();
        if (!apkVersion.equals(versionName)) {
            return true;
        }
        return false;
    }

    public void updateNewApk(Context context, SystemInfoModel.SystemData systemModel) {

        String apkPath = systemModel.getNI_Url();
        if (apkPath == null) {
            return;
        }
        DownLoadApkFile loadApkFile = new DownLoadApkFile();
        loadApkFile.updateAPk(context, apkPath);
    }
    private static final int INSTALL_PACKAGES_REQUESTCODE = 100;
    public void installApk(Context appContext, String filePath) {
        //Context appContext = ApplicationHolder.getAppContext();
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            // ".fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(appContext, "com.zhide.app.fileProvider", file);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            data = Uri.fromFile(file);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        appContext.startActivity(intent);
    }

   public Intent getNotiItent(Context appContext, String filePath){
       File file = new File(filePath);
       if (!file.exists()) {
           return null;
       }
       Intent intent = new Intent(Intent.ACTION_VIEW);
       Uri data;
       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       // 判断版本大于等于7.0
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
           // ".fileprovider"即是在清单文件中配置的authorities
           data = FileProvider.getUriForFile(appContext, "com.zhide.app.fileProvider", file);
           // 给目标应用一个临时授权
           intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
       } else {
           data = Uri.fromFile(file);
       }
       intent.setDataAndType(data, "application/vnd.android.package-archive");
       return intent;
   }
}
