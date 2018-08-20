package com.zhide.app.utils;

import android.app.Activity;
import android.content.Context;
import com.zhide.app.R;
import com.zhide.app.model.SpinnerSelectModel;

import org.jaaksi.pickerview.picker.BasePicker;
import org.jaaksi.pickerview.picker.OptionPicker;
import org.jaaksi.pickerview.picker.TimePicker;
import org.jaaksi.pickerview.topbar.DefaultTopBar;
import org.jaaksi.pickerview.widget.DefaultCenterDecoration;
import org.jaaksi.pickerview.widget.PickerView;

import java.util.List;

/**
 * Created by hasee on 2018/5/15.
 */

public class PickViewUtil {

    private static long start_Time = 1420041600000L;
    public static final int TYPE_YEAR_MONTH = TimePicker.TYPE_YEAR | TimePicker.TYPE_MONTH;
    public static final int TYPE_YEAR_DAY = TimePicker.TYPE_DATE;
    public static final int TYPE_HOUR_MINUTE = TimePicker.TYPE_TIME;

    /**
     * @param activity
     * @param type           TimePicker.TYPE_ALL
     * @param selectListener
     */
    public static void showTimePickDialog(final Activity activity, long selectTime, long endTime, int type, TimePicker.OnTimeSelectListener selectListener) {
        if (selectTime <= 0) {
            selectTime = System.currentTimeMillis();
        }
        if (endTime == 0) {
            endTime = System.currentTimeMillis();
        }
        TimePicker timePicker = new TimePicker.Builder(activity, type,
                selectListener)
                .setRangDate(start_Time, endTime)
                .setSelectedDate(selectTime)
                .setInterceptor(new BasePicker.Interceptor() {
                    @Override
                    public void intercept(PickerView pickerView) {
                        pickerView.setColor(activity.getResources().getColor(R.color.main_blue_color), pickerView.getOutColor());
                    }
                })
                .create();
        DefaultTopBar topBar = (DefaultTopBar) timePicker.getTopBar();
        topBar.getTopBarView().setBackgroundColor(activity.getResources().getColor(R.color.main_blue_color));
        timePicker.show();
    }

    public static void initTimePickOptions(Context context) {
        // 利用修改静态默认属性值，快速定制一套满足自己app样式需求的Picker.
        DefaultCenterDecoration.sDefaultLineColor = context.getResources().getColor(R.color.main_blue_color);// 设置选中数据的上下两条线的颜色

    }

    public static void showSelectPickDialog(final Activity context, List<SpinnerSelectModel> selectModel, int hierarchy, OptionPicker.OnOptionSelectListener optionSelectListener) {
        OptionPicker optionPicker = new OptionPicker.Builder(context, hierarchy, optionSelectListener)
                .setInterceptor(new BasePicker.Interceptor() {
                    @Override
                    public void intercept(PickerView pickerView) {
                        pickerView.setColor(context.getResources().getColor(R.color.main_blue_color), pickerView.getOutColor());
                        pickerView.setTextSize(12, 15);
                    }
                })
                .create();
        DefaultTopBar topBar = (DefaultTopBar) optionPicker.getTopBar();
        optionPicker.setDataWithValues(selectModel);
        optionPicker.setPadding(10, 0, 10, 0);
        topBar.getTopBarView().setBackgroundColor(context.getResources().getColor(R.color.main_blue_color));
        optionPicker.show();
    }
}
