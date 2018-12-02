package com.zhide.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;

import com.zhide.app.common.ApplicationHolder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by hasee on 2017/12/25.
 */

public class PreferencesUtils {

    public static String PREFERENCE_NAME = "TrineaAndroidCommon";
    private static Context context = ApplicationHolder.getInstance().getAppContext();

    public static boolean putString(String key, String value) {

        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);
        Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getString(String key) {

        return getString(key, null);
    }

    public static String getString(String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);
        return settings.getString(key, defaultValue);
    }

    public static boolean putInt(String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);
        Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static int getInt(String key) {
        return getInt(key, -1);
    }

    public static int getInt(String key, int defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);
        return settings.getInt(key, defaultValue);
    }

    public static boolean putLong(String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);
        Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static long getLong(String key) {
        return getLong(key, -1L);
    }

    public static long getLong(String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);
        return settings.getLong(key, defaultValue);
    }


    public static boolean putFloat(String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);
        Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }


    public static float getFloat(String key) {
        return getFloat(key, -1f);
    }

    public static float getFloat(String key, float defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);
        return settings.getFloat(key, defaultValue);
    }





    public static boolean putBoolean(String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);
        Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);
        return settings.getBoolean(key, defaultValue);
    }

    public static void clearshare() {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, 0);
        Editor editor = settings.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * 保存对象
     *
     * @param key 键
     * @param obj 要保存的对象（Serializable的子类）
     * @param <T> 泛型定义
     */
    public static <T extends Serializable> void putObject(String key, T obj) {
        try {
            put(key, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象
     *
     * @param key 键
     * @param <T> 指定泛型
     * @return 泛型对象
     */
    public static <T extends Serializable> T getObject(String key) {
        try {
            return (T) get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存储对象
     */
    private static void put(String key, Object obj)
            throws IOException {
        if (obj == null) {//判断对象是否为空
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        // 将对象放到OutputStream中
        // 将对象转换成byte数组，并将其进行base64编码
        String objectStr = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        baos.close();
        oos.close();
        putString(key, objectStr);
    }

    /**
     * 获取对象
     */
    private static Object get(String key)
            throws IOException, ClassNotFoundException {
        String wordBase64 = getString(key);
        // 将base64格式字符串还原成byte数组
        if (TextUtils.isEmpty(wordBase64)) { //不可少，否则在下面会报java.io.StreamCorruptedException
            return null;
        }
        byte[] objBytes = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        // 将byte数组转换成product对象
        Object obj = ois.readObject();
        bais.close();
        ois.close();
        return obj;
    }
}
