package com.zhide.app.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.zhide.app.common.ApplicationHolder;


/**
 * Created by hasee on 2017/12/28.
 */

public class ResourceUtils {
    private static final int DEFAULT_IMAGE_COLOR = 0xFFD9D9D9;
    private static ResourceUtils sResourceUtils;
    private static Context mApplicationContext;

    private ResourceUtils() {
        initContext();
    }

    public static ResourceUtils getInstance() {
        if (sResourceUtils == null) {
            synchronized (ResourceUtils.class) {
                if (sResourceUtils == null) {
                    sResourceUtils = new ResourceUtils();
                }
            }
        }

        return sResourceUtils;
    }

    private static void initContext() {
        if (mApplicationContext == null) {
            mApplicationContext = ApplicationHolder.getInstance().getAppContext();
        }
    }

    public String getString(int resId) {
        if (mApplicationContext == null || resId == 0) {
            return "";
        }
        try {
            return mApplicationContext.getString(resId);
        } catch (Resources.NotFoundException e) {
        }
        return "";
    }

    public String[] getStringArray(int resId) {
        if (mApplicationContext == null || resId == 0) {
            return new String[0];
        }
        try {
            return mApplicationContext.getResources().getStringArray(resId);
        } catch (Resources.NotFoundException e) {
        }
        return new String[0];
    }

    public int getColor(@ColorRes int resId) {
        if (mApplicationContext == null || resId == 0) {
            return 0;
        }
        try {
            return mApplicationContext.getResources().getColor(resId);
        } catch (Resources.NotFoundException e) {
        }
        return 0;
    }

    public int getDimensionPixelSize(int resId) {
        if (mApplicationContext == null || resId == 0) {
            return 0;
        }
        return mApplicationContext.getResources().getDimensionPixelSize(resId);
    }

    /**
     * 设置图片资源，加入OOM保护
     * @param imageView
     * @param resId
     */
    public static void setImageResource(ImageView imageView, int resId) {
        if (imageView == null || resId == 0) {
            return;
        }
        try {
            imageView.setImageResource(resId);
        } catch (OutOfMemoryError e) {
            Log.i("admin", "ResourceUtils setImageResource e = " + e);
            imageView.setImageDrawable(getDefaultImage());
        }
    }

    /**
     * 设置背景资源，加入OOM保护
     * @param view
     * @param resId
     */
    public static void setBackgroundResource(View view, int resId) {
        if (view == null || resId == 0) {
            return;
        }
        try {
            view.setBackgroundResource(resId);
        } catch (OutOfMemoryError e) {
            Log.i("ABEN", "ResourceUtils setBackgroundResource e = " + e);
            view.setBackgroundDrawable(getDefaultImage());
        }
    }


    /**
     * 获取通用背景色
     *
     * @return
     */
    public static Drawable getDefaultImage() {
        return new ColorDrawable(DEFAULT_IMAGE_COLOR);
    }
}
