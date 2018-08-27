package com.zhide.app.utils;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;


import com.zhide.app.common.ApplicationHolder;

import java.text.DecimalFormat;


/**
 * Created by gugu on 2018/1/6.
 */

public class UIUtils {

    /**
     * dp转px
     *
     * @param context
     * @param dip
     * @return
     */
    public static int dipToPx(Context context, int dip) {
        return (int) (dip * getScreenDensity(context) + 0.5f);
    }

    /**
     * float保留2位有效数字
     *
     * @param data
     * @return
     */
    public static String getDoublePoint(float data) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(data);//format 返回的是字符串
    }

    /**
     * KB转MB
     *
     * @param sizeKb
     * @return
     */
    public static String kbToMb(long sizeKb) {
        float sizeMb = sizeKb / (1024);
        return getDoublePoint(sizeMb);

    }

    //1、通过Resources获取
    public static int getScreenHeight(Context context) {

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;


    }

    //1、通过Resources获取
    public static int getScreenWidth(Context context) {

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;

    }

    /**
     * 获取屏幕像素
     *
     * @param context
     * @return
     */
    public static float getScreenDensity(Context context) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
                    .getMetrics(dm);
            return dm.density;
        } catch (Exception e) {
            return DisplayMetrics.DENSITY_DEFAULT;
        }
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param sp      （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float sp) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }

    public static String getChangeContent(String content, String tips) {
        if (content == null || content.isEmpty()) {
            return tips;
        }
        return content;
    }

    /**
     * 代码里不直接写中文，通过这个方法返回string里面的资源文字
     *
     * @param resId
     * @return
     */
    public static String getValueString(int resId) {
        return ApplicationHolder.getInstance().getAppContext().getResources().getString(resId);
    }

    public static String getNumberUnitFormart(int number) {
        if (number <= 9999) {
            return number + "";
        }
        float nums = (float) number / 10000;
        String doublePoint = getDoublePoint(nums);
        return doublePoint + "万";
    }

    public static void initViewState(View view, int state) {
        if (view == null) {
            return;
        }
        if (state == 1) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);

        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public static void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        context.getWindow().setAttributes(lp);
    }

    public static int[] getViewHeightAndWidth(View asView) {
        int[] viewParams = new int[2];
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        asView.measure(w, h);
        int height = asView.getMeasuredHeight();
        int width = asView.getMeasuredWidth();
        viewParams[0] = width;
        viewParams[1] = height;
        return viewParams;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    /**
     * 密码显示或隐藏 （切换）
     */
    public static void showOrHide(EditText etPassword) {
        //记住光标开始的位置
        int pos = etPassword.getSelectionStart();
        if (etPassword.getInputType() != (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {//隐藏密码
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {//显示密码
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        etPassword.setSelection(pos);
    }
}
