package com.zhide.app.utils;

import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.zhide.app.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by hasee on 2018/6/7.
 */

public class NotificationUtil {
    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
    /**
     * 显示一个普通的通知
     *
     * @param context 上下文
     */
    public static void showNotification(Context context, int imgId, String title, String content, PendingIntent pendingIntent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        if (imgId != 0) {
            /**设置通知左边的大图标**/
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        }
        /**设置通知右边的小图标**/
        builder.setSmallIcon(R.mipmap.ic_launcher);
        /**通知首次出现在通知栏，带上升动画效果的**/
        //builder.setTicker("通知来了");
        /**设置通知的标题**/
        if (title != null) {
            builder.setContentTitle(title);
        }
        /**设置通知的内容**/
        if (content == null) {
            builder.setContentText("点击通知，查看详情>>");
        } else {
            builder.setContentText(content);
        }
        /**通知产生的时间，会在通知信息里显示**/
        // .setWhen(System.currentTimeMillis())
        /**设置该通知优先级**/
        builder.setPriority(Notification.PRIORITY_HIGH);
        /**设置这个标志当用户单击面板就可以让通知将自动取消**/
        builder.setAutoCancel(true);
        /**设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)**/
        builder.setOngoing(false);
        /**向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：**/
        builder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);
        /**
         * PendingIntent.getActivity(context, 1, new Intent(context, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT)
         */
        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        }
        Notification notification = builder.build();
        NotificationManager notificationManager = getNotificationManager(context);
        /**发起通知**/
        notificationManager.notify(0, notification);
    }

    /**
     * 显示一个下载带进度条的通知
     *
     * @param context 上下文
     */
    public static void showNotificationProgress(Context context, String title, int progress, PendingIntent pendingIntent) {

        final NotificationManager notificationManager = getNotificationManager(context);
        Notification notification = getLoadNotification(context, title, progress, pendingIntent);
        //发送一个通知
        notificationManager.notify(2, notification);
    }

    /**
     * 获取NotificationManager的实例，对通知进行管理
     *
     * @return
     */

    public static NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static Notification getLoadNotification(Context context, String title, int progress, PendingIntent pendingIntent) {
        //进度条通知
        final NotificationCompat.Builder builderProgress = new NotificationCompat.Builder(context);
        builderProgress.setContentTitle(title);
        builderProgress.setSmallIcon(R.mipmap.ic_launcher);
        builderProgress.setTicker("下载通知");
        builderProgress.setProgress(100, progress, false);
        builderProgress.setAutoCancel(true);
        if(pendingIntent!=null){
            //当通知被点击的时候，跳转到pendingIntent中
            builderProgress.setContentIntent(pendingIntent);
        }
        return builderProgress.build();
    }

    /**
     * 悬挂式，支持6.0以上系统
     *
     * @param context
     */
/*    public static void showFullScreen(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.csdn.net/itachi85/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        builder.setAutoCancel(true);
        builder.setContentTitle("悬挂式通知");
        //设置点击跳转
        Intent hangIntent = new Intent();
        hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        hangIntent.setClass(context, MainActivity.class);
        //如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
        PendingIntent hangPendingIntent = PendingIntent.getActivity(context, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setFullScreenIntent(hangPendingIntent, true);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(3, builder.build());
    }*/

    /**
     * 折叠式
     *
     * @param context
     */
/*    public static void shwoNotify(Context context) {
        //先设定RemoteViews
        RemoteViews view_custom = new RemoteViews(context.getPackageName(), R.layout.view_custom);
        //设置对应IMAGEVIEW的ID的资源图片
        view_custom.setImageViewResource(R.id.custom_icon, R.mipmap.icon);
        view_custom.setTextViewText(R.id.tv_custom_title, "今日头条");
        view_custom.setTextColor(R.id.tv_custom_title, Color.BLACK);
        view_custom.setTextViewText(R.id.tv_custom_content, "金州勇士官方宣布球队已经解雇了主帅马克-杰克逊，随后宣布了最后的结果。");
        view_custom.setTextColor(R.id.tv_custom_content, Color.BLACK);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContent(view_custom)
                .setContentIntent(PendingIntent.getActivity(context, 4, new Intent(context, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT))
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setTicker("有新资讯")
                .setPriority(Notification.PRIORITY_HIGH)// 设置该通知优先级
                .setOngoing(false)//不是正在进行的   true为正在进行  效果和.flag一样
                .setSmallIcon(R.mipmap.icon);
        Notification notify = mBuilder.build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(4, notify);
    }*/

    /**
     * 通知栏权限是否开启
     * @param context
     * @return
     */
    public static boolean isNotificationEnabled(Context context) {
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        Class appOpsClass = null;
      /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
}
