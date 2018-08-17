package com.zhide.app.apkUpdate;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;


/**
 * Created by hasee on 2017/12/14.
 */

public class DownLoadApkFile {

    private   DownloadService.DownloadBinder downloadBinder;
    private  String downLoadUrl = null;
    private  ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder=(DownloadService.DownloadBinder) service;
            downLoadApk(downLoadUrl);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public  void updateAPk(Context context,String url) {
        downLoadUrl = url;
        Intent intent = new Intent(context,DownloadService.class);
        //context.startService(intent);
        context.bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    public void  downLoadApk(String downLoadUrl){
        if(downLoadUrl == null){
            return;
        }
        downloadBinder.startDownload(downLoadUrl);
    }

    public   void unbindService(Context context){
        if(connection!=null){
            context.unbindService(connection);
        }
    }
}
