package com.zhide.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import com.zhide.app.common.ApplicationHolder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by hasee on 2018/2/6.
 */

public class FileUtils {
    private static Context mContext = ApplicationHolder.getInstance().getAppContext();

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

    public static void dealFile(String fileName,String extraPath) {
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
    public static String saveBitmap(Context context, Bitmap mBitmap, String filePath,String fileName) {
        String savePath;
        File filePic;
        try {
            filePic = new File(filePath,fileName);
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
}
