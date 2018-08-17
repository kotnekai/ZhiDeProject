package com.zhide.okhttputils.log;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.zhide.okhttputils.log.model.NetLogModel;
import com.zhide.okhttputils.utils.ThreadManager;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hasee on 2018/1/17.
 */

public class NetLogManager {
    public static NetLogManager instance = null;
    private Context mContext;
    private  final String DATE_FORMAT_YEAR2 = "yyyy-MM-dd HH:mm:ss";
    private  final String DATE_FORMAT_DAY2 = "yyyy-MM-dd";
    private String LOG_EXTRA_PATH = "/net_log";
    private NetLogManager(Context context) {
        mContext = context;
    }

    public static NetLogManager getInstance(Context context) {
        if (instance == null) {
            instance = new NetLogManager(context);
        }
        return instance;
    }


    public void logNetResponse(final NetLogModel netLogModel, final boolean isResponseLog) {
        ThreadManager.getInstance().getBackgroundHandler().post(new Runnable() {
            @Override
            public void run() {

                String currentTime = getLongToString(getSystemLongTime(), DATE_FORMAT_DAY2);
                String formatSystemTime = getLongToString(getSystemLongTime(),DATE_FORMAT_YEAR2);

                String filePath = getFilePath(LOG_EXTRA_PATH);
                String fileName = currentTime + ".html";//log日志名，使用时间命名，保证不重复
                String log = "";
                if (isResponseLog) {
                    log = "log_time = " + formatSystemTime + "\n" + "netLogModel=" + netLogModel.toResponseString() + "\n\n";
                } else {
                    log = "log_time = " + formatSystemTime + "\n" + "netLogModel=" + netLogModel.toRequestString() + "\n\n";
                }
                writeTxtToFile(log, filePath, fileName);
            }
        });

    }

    public void logdResponse(final String request, final String response) {
        ThreadManager.getInstance().getBackgroundHandler().post(new Runnable() {
            @Override
            public void run() {

                String currentTime = getLongToString(getSystemLongTime(), DATE_FORMAT_DAY2);
                String formatSystemTime = getLongToString(getSystemLongTime(),DATE_FORMAT_YEAR2);

                String filePath = getFilePath(LOG_EXTRA_PATH);
                String fileName = currentTime + ".html";//log日志名，使用时间命名，保证不重复
                String log = "time=" + formatSystemTime + "\n" + "request_url=" + request + "\n" + "response = " + response + "\n\n";
                writeTxtToFile(log, filePath, fileName);
            }
        });

    }


    // 将字符串写入到文本文件中
    public void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
       makeFilePath(filePath, fileName);
        String strFilePath = filePath;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(filePath, fileName);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }

    public void LogNet(final String url, final int code, final String response) {

        ThreadManager.getInstance().getBackgroundHandler().post(new Runnable() {
            @Override
            public void run() {
                String currentTime = getLongToString(getSystemLongTime(), DATE_FORMAT_DAY2);
                String formatSystemTime = getLongToString(getSystemLongTime(),DATE_FORMAT_YEAR2);

                String filePath = getFilePath(LOG_EXTRA_PATH);
                String fileName = currentTime + ".html";//log日志名，使用时间命名，保证不重复
                String log = "time=" + formatSystemTime + "\n" + "request_url=" + url + "\n" + "response_code = " + code + "\n" + "response = " + response + "\n\n";

                writeTxtToFile(log, filePath, fileName);
            }
        });
    }
    /**
     * 获取系统时间
     *
     * @return
     */
    public long getSystemLongTime() {
        return System.currentTimeMillis();
    }
    /**
     * 时间戳转string时间格式
     *
     * @param time
     * @return
     */
    public String getLongToString(long time, String formatStr) {
        if (time == 0) {
            time = getSystemLongTime();
        }
        SimpleDateFormat format = new SimpleDateFormat(formatStr, Locale.CHINA);
        Date date = new Date(time);
        return format.format(date);
    }

    /**
     * getFilePath
     * @param extraPath
     * @return
     */
    public String getFilePath(String extraPath) {

        if (Environment.MEDIA_MOUNTED.equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {//如果外部储存可用
            File externalFilesDir =mContext.getExternalFilesDir(extraPath);
            if (externalFilesDir != null) {
                return externalFilesDir.getPath();//获得外部存储路径,默认路径为 /storage/emulated/0/Android/data/com.waka.workspace.logtofile/files/Logs/log_2016-03-14_16-15-09.log
            }
            return null;
        } else {
            return mContext.getFilesDir().getPath();//直接存在/data/data里，非root手机是看不到的
        }
    }
    // 生成文件
    public File makeFilePath(String filePath, String fileName) {
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
    private  void makeRootDirectory(String filePath) {
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
}
