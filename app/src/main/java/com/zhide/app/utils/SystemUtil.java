package com.zhide.app.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Looper;
import android.provider.ContactsContract;
import android.provider.Settings;

import com.zhide.app.common.ComApplication;


/**
 * Created by lifesense on 16/5/3.
 */
public class SystemUtil {
    //手机型号
    public static String getmobileVersion() {
        return android.os.Build.MODEL;
    }

    //手机系统版本
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    //版本号
    public static int getVersionCode() {
        return getPackageInfo(ComApplication.getApp()).versionCode;
    }

    public static String getVersionName() {
        String versionname = getPackageInfo(ComApplication.getApp()).versionName;
        if (versionname.contains("(")) {
            versionname = versionname.split("\\(")[0];
        }
        return versionname;
    }

    public static boolean isCallPermission(Context mContext) {
        try {
            ContentResolver resolver = mContext.getContentResolver();
            Uri contactUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人Uri；              ContentResolver resolver = mContext.getContentResolver();
            Cursor cursor = resolver.query(contactUri, new String[]{"display_name", "sort_key", "contact_id", "data1"}, null, null, "sort_key");
            int count = cursor.getCount();
            cursor.close();
            return count > 0;
        } catch (Exception e) {
        }
        return false;
    }

    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";



    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();

        }

        return pi;
    }

    private static Uri SMS_INBOX = Uri.parse("content://sms/");



    public static boolean isProviderEnabledGps(Context context) {
        int mode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE,
                Settings.Secure.LOCATION_MODE_OFF);

        return mode == Settings.Secure.LOCATION_MODE_SENSORS_ONLY ||
                mode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY;
    }

    public static boolean isHasReceiveSmsPermission(Context context) {
        ContentResolver cr = context.getContentResolver();
        String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
        Cursor cur = cr.query(SMS_INBOX, projection, null, null, "date desc");
        if (null == cur) {
            return false;
        }
        if (cur.getCount() > 0) {
            cur.close();
            return true;
        }
        cur.close();
        return false;
    }

    /***
     * 判断是否主线程
     *
     * @return
     */
    public static boolean isMainThread() {
        Looper myLooper = Looper.myLooper();
        Looper mainLooper = Looper.getMainLooper();
        return myLooper == mainLooper;
    }

    public static SystemOS getSystemOS() {
        SystemOS systemOS = SystemOS.UNKNOWN;
        String name =android.os.Build.MANUFACTURER;
        if(name.equalsIgnoreCase("xiaomi")){
            systemOS= SystemOS.XIAOMI;
        }
        if(name.equalsIgnoreCase("meizu")){
            systemOS= SystemOS.MEIZU;
        }
        if(name.equalsIgnoreCase("oppo")){
            systemOS= SystemOS.OPPO;
        }
        if(name.equalsIgnoreCase("vivo")){
            systemOS= SystemOS.VIVO;
        }
        if(name.equalsIgnoreCase("huawei")){
            systemOS= SystemOS.HUAWEI;
        }

        return systemOS;
    }

    private static String getHandSetInfo() {
        // 版本显示:Flyme OS 4.5.4.1A

        String handSetInfo = "手机型号:" + android.os.Build.MODEL
                + "\n系统版本:" + android.os.Build.VERSION.RELEASE
                + "\n产品型号:" + android.os.Build.PRODUCT
                + "\n版本显示:" + android.os.Build.DISPLAY //可以判断魅族版本
                + "\n系统定制商:" + android.os.Build.BRAND
                + "\n设备参数:" + android.os.Build.DEVICE
                + "\n开发代号:" + android.os.Build.VERSION.CODENAME
                + "\nSDK版本号:" + android.os.Build.VERSION.SDK_INT
                + "\nCPU类型:" + android.os.Build.CPU_ABI
                + "\n硬件类型:" + android.os.Build.HARDWARE
                + "\n主机:" + android.os.Build.HOST
                + "\n生产ID:" + android.os.Build.ID
                + "\nROM制造商:" + android.os.Build.MANUFACTURER // 这行返回的是rom定制商的名称
                ;
        return handSetInfo;
    }

    public enum SystemOS {
        XIAOMI, MEIZU, HUAWEI, OPPO, VIVO, LETV, UNKNOWN
    }

}
