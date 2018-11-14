package com.zhide.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.zhide.app.common.ApplicationHolder;
import com.zhide.app.common.ComApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by hasee on 2018/2/6.
 */

public class FileUtils {
    private static Context mContext = ApplicationHolder.getInstance().getAppContext();

    protected static String DISK_CACHE_PATH = "com.zhide.app";

    /**
     * 用来合并文件夹用的格式化字符串
     * 格式:a/b
     */
    protected static final String FOLDER_MERGE_FORMATER = "%s/%s";

    /**
     * 格式:a/b/
     */
    protected static final String FOLDER_MERGE_FORMATERLAST = "%s/%s/";

    // 生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    private static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }

    public static String getFilePath(String extraPath) {

        if (!Environment.isExternalStorageRemovable()) {//如果外部储存可用
            File externalFilesDir = ApplicationHolder.getInstance().getAppContext().getExternalFilesDir(extraPath);
            if (externalFilesDir != null) {
                return externalFilesDir.getPath();//获得外部存储路径,默认路径为 /storage/emulated/0/Android/data/com.waka.workspace.logtofile/files/Logs/log_2016-03-14_16-15-09.log
            }
            return null;
        } else {
            return mContext.getFilesDir().getPath();//直接存在/data/data里，非root手机是看不到的
        }
    }

    public static void dealFile(String fileName, String extraPath) {
        String filePath = getFilePath(extraPath);
        if (filePath == null) {
            return;
        }
        //创建一个文件
        File downLoadFile = new File(filePath);

        if (!downLoadFile.exists()) {
            downLoadFile.mkdir();
        } else {
            File files[] = downLoadFile.listFiles(); // 声明目录下所有的文件
            for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                if (files[i].getName().equals(fileName)) {
                    break;
                } else {
                    files[i].delete();
                }
            }
        }
    }

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file == null) {
            return;
        }
        File[] files = file.listFiles();
        String today = DateUtils.getFormatDayTime(System.currentTimeMillis());
        String tomorrow = DateUtils.getFormatDayTime(DateUtils.getCurrentBeforeDay(System.currentTimeMillis()));
        for (int i = 0; i < files.length; i++) {
            if (!(files[i].getName().equals(today + ".html")) && !(files[i].getName().equals(tomorrow + ".html"))) {
                files[i].delete();
            }
        }
    }


    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap, String filePath, String fileName) {
        String savePath;
        File filePic;
        try {
            filePic = new File(filePath, fileName);
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return filePic.getAbsolutePath();
    }

    /**
     * app文件夹路径
     *
     * @return
     */
    public static String getAppFolderPath() {
        String cachePath;
        // /mnt/sdcard判断有无SD卡
        if (isSDCardExist()) {
            cachePath = String.format(FOLDER_MERGE_FORMATER, getSDCardDir(), DISK_CACHE_PATH);
        } else {
            // 没有就创建到手机内存
            cachePath = String.format(FOLDER_MERGE_FORMATER, ComApplication.getApp().getCacheDir(), DISK_CACHE_PATH);
        }
        ensureDir(cachePath);
        return cachePath;
    }

    /**
     * 获得SD卡目录
     *
     * @return
     */
    public static String getSDCardDir() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 是否sdk卡存在
     *
     * @return
     */
    public static boolean isSDCardExist() {
        return Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment.getExternalStorageState());
    }


    public static boolean ensureDir(String path) {
        if (null == path) {
            return false;
        }

        boolean ret = false;

        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            try {
                ret = file.mkdirs();
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }

        return ret;
    }

    public static String getFilePath() {

        String cachePath;
        // /mnt/sdcard判断有无SD卡
        if (isSDCardExist()) {
            cachePath = String.format(FOLDER_MERGE_FORMATER, getSDCardDir(), DISK_CACHE_PATH);
        } else {
            // 没有就创建到手机内存
            cachePath = String.format(FOLDER_MERGE_FORMATER, ComApplication.getApp().getCacheDir(), DISK_CACHE_PATH);
        }
        ensureDir(cachePath);
        return cachePath;

    }


    public static String getZhiDePath() {
        String path = getFilePath() + "/zhide";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
        //        return getFilePath();
    }

    public static String getZhiDeFileName() {
        return "ZHIDE" + ApplicationHolder.getInstance().getUserMobile();
    }

    /**
     * 获取日志路径
     *
     * @return
     */
    public static String getLogPath() {
        return getAppFolderPath() + "/log";
    }

    public static String getZhiDeLogPath() {
        return getLogPath() + "/user" + ApplicationHolder.getInstance().getUserMobile() + "/zhideLog";//getAppFolderPath() + "/heartLog" + "/user" + LifesenseApplication.getSleepUserid();
    }

    public static void writeLog(String content) {
        String sharefilenameKey = getZhiDeFileName();
        String suffixStr = "-log.txt";
        write7DaysLog(content, sharefilenameKey, getZhiDeLogPath(), suffixStr);
    }


    public static void write7DaysLog(String content, String sharefilenameKey, String logpath,
                                     String suffixStr) {
        String savefilename = PreferencesUtils.getString(sharefilenameKey, "");
        //是否写入系统日志
        boolean isWriteSystemLog = false;
        //当前时间搓
        long curreTime = System.currentTimeMillis();
        // 7天前的00点格式
        long currentBefore7Date = DateUtils.getStringToLong(
                DateUtils.getDayOf12clock(DateUtils.getDateString(curreTime), -7, 00));
        // 当前时间的00点格式
        String currentDate00 = DateUtils.getDayOf12clock(DateUtils.getDateString(curreTime), 0, 00);
        File file = null;
        if (!savefilename.equals("")) {
            String saveFileNameDate = savefilename.split(suffixStr)[0];
            // 文件名字的00点格式

            String savefileCurrentDate00 = DateUtils.getDayOf12clock(saveFileNameDate, 0, 00);
            // 存在文件名称
            file = new File(logpath + "/" + savefilename);
            if (!savefileCurrentDate00.equals(currentDate00)) { // 不是在同一天
                isWriteSystemLog = true;
                // 获取文件夹下面的所有文件
                String[] files = getFileNameList(logpath);
                if (files != null) {
                    for (String name : files) {
                        if (name.contains(suffixStr)) {
                            //取得文件名称
                            long fileDate = DateUtils.getStringToLong(name.split(suffixStr)[0]);
                            if (fileDate < currentBefore7Date) {
                                // 删除7天前的睡眠日志文件
                                file = new File(logpath + "/" + name);
                                file.delete();
                                file = createLogFile(sharefilenameKey, logpath, suffixStr);
                                // 写入文件
                                writeFile(file, isWriteSystemLog, content);
                                return;
                            }
                        }
                    }

                }
                //遍历完成之后 没有7天以外的日志 重新创建新的文件
                file = createLogFile(sharefilenameKey, logpath, suffixStr);
                // 写入文件
                writeFile(file, isWriteSystemLog, content);
            } else { // 是同一天的情况下
                writeFile(file, isWriteSystemLog, content);
                return;
            }
        } else {
            // 文件不存在的情况下
            isWriteSystemLog = true;
            file = createLogFile(sharefilenameKey, logpath, suffixStr);
            writeFile(file, isWriteSystemLog, content);
        }

    }


    public static String[] getFileNameList(String path) {
        File file = new File(path);
        String[] fileNames = file.list();
        return fileNames;
    }

    /**
     * 创建文件
     *
     * @return
     */
    public static File createLogFile(String sharefilenameKey, String logpath, String suffixStr) {
        // 创建一个当前日期的文件
        String date = DateUtils.getDateString(System.currentTimeMillis());
        File file = new File(logpath + "/" + date + suffixStr);
        String savefilename = file.getName();
        PreferencesUtils.putString(sharefilenameKey, savefilename);
        //+ " , 蓝牙版本号:"+123+" , 固件版本号:"+ 123

        return file;
    }


    public static void writeFile(File file, boolean isWriteSystemLog, String content) {
        // 写入数据
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileWriter filerWriter = new FileWriter(file, true);//后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            //判断是否写入系统日志信息
            if (isWriteSystemLog) {

                StringBuilder sb = new StringBuilder();
                sb.append("userId:").append(ApplicationHolder.getInstance().getUserMobile() + "")
                        .append(" , 手机型号:").append(SystemUtil.getmobileVersion())
                        .append(" , 系统版本号:").append(SystemUtil.getSystemVersion())
                        .append(" , app版本号:").append(SystemUtil.getVersionCode());

                bufWriter.write(sb.toString());
                bufWriter.newLine();
                bufWriter.write("======================");
                bufWriter.newLine();
            }
            bufWriter.write(content);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
