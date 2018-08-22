package com.zhide.app.utils;

/**
 * Created by hasee on 2018/6/7.
 */

public class EmptyUtil {

    public static boolean isEmpty(String content) {
        if (content == null) {
            return true;
        }
        if (content.isEmpty()) {
            return true;
        }
        return false;
    }
}
