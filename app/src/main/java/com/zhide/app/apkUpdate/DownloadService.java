package com.zhide.app.apkUpdate;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import com.zhide.app.common.ApplicationHolder;
import com.zhide.app.common.CommonParams;
import com.zhide.app.utils.DateUtils;
import com.zhide.app.utils.FileUtils;
import com.zhide.app.utils.NotificationUtil;
import com.zhide.app.utils.PageIntentUtils;

import java.io.File;

/**
 * Created by gugu on 2017/12/10.
 */

public class DownloadService extends Service {

    private DownloadTask downloadTask;
    private String downloadUrl;
    private Context context = ApplicationHolder.getInstance().getAppContext();
    private DownloadBinder mBinder = new DownloadBinder();
    private String fileName;

    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }


    private DownloadApkListener listener = new DownloadApkListener() {

        @Override
        public void onProgress(int progress) {
            //NotificationManager的notify()可以让通知显示出来。
            //notify(),接收两个参数，第一个参数是id:每个通知所指定的id都是不同的。第二个参数是Notification对象。

            NotificationUtil.getNotificationManager(context).notify(1, NotificationUtil.getLoadNotification(context, "下载中。。", progress, null));
        }

        @Override
        public void onSuccess() {
            downloadTask = null;

            //下载成功时将前台服务通知关闭，并创建一个下载成功的通知
            stopForeground(true);
            Toast.makeText(DownloadService.this, "下载成功", Toast.LENGTH_SHORT).show();

            Intent notiItent = AppUpdateManager.getInstance().getNotiItent(context, FileUtils.getFilePath(CommonParams.APK_PATH) + "/" + fileName);
            NotificationUtil.getNotificationManager(context).notify(1, NotificationUtil.getLoadNotification(context, "下载成功", 100, PageIntentUtils.getPendIntent(context,notiItent)));
            AppUpdateManager.getInstance().installApk(ApplicationHolder.getInstance().getAppContext(), FileUtils.getFilePath(CommonParams.APK_PATH) + "/" + fileName);

        }

        @Override
        public void onFailed() {
            downloadTask = null;

            //下载失败时，将前台服务通知关闭，并创建一个下载失败的通知
            stopForeground(true);

            NotificationUtil.getNotificationManager(context).notify(1,NotificationUtil.getLoadNotification(context,"下载失败",-1,null));
            Toast.makeText(DownloadService.this, "下载失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onPaused() {
            downloadTask = null;
            //  Toast.makeText(DownloadService.this, "Download Paused", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            //取消下载，将前台服务通知关闭，并创建一个下载失败的通知
            // stopForeground(true);
            //Toast.makeText(DownloadService.this, "Download Canceled", Toast.LENGTH_SHORT).show();

        }
    };


    class DownloadBinder extends Binder {
        /**
         * 开始下载
         *
         * @param
         */

        public void startDownload(String url) {

            if (downloadTask == null) {
                downloadUrl = url;
                String formatDayTime = DateUtils.getFormatDayTime(System.currentTimeMillis(), DateUtils.DATE_FORMAT_YEAR2);
                int versionCode = ApplicationHolder.getInstance().getVersionModel().getVersionCode();

                fileName = formatDayTime + "_" + versionCode + ".apk";
                FileUtils.dealFile(fileName,CommonParams.APK_PATH);
                downloadTask = new DownloadTask(listener);
                //启动下载任务
                downloadTask.execute(downloadUrl, FileUtils.getFilePath(CommonParams.APK_PATH), fileName);
                //startForeground(1, NotificationUtils.getNotification(context, "开始下载...", 0));
                startForeground(1, NotificationUtil.getLoadNotification(context, "开始下载...", 0, null));

                Toast.makeText(DownloadService.this, "后台下载中...", Toast.LENGTH_SHORT).show();
            }
        }


        /**
         * 暂停下载
         */

        public void pauseDownload() {
            if (downloadTask != null) {
                downloadTask.pauseDownload();
            }
        }


        /**
         * 取消下载
         */

        public void cancelDownload() {
            if (downloadTask != null) {
                downloadTask.cancelDownload();
            } else {
                if (downloadUrl != null) {
                    //取消下载时需要将文件删除，并将通知关闭
                    String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                    File file = new File(directory + fileName);
                    if (file.exists()) {
                        file.delete();
                    }

                    NotificationUtil.getNotificationManager(context).cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this, "取消下载", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}
