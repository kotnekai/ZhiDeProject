package com.zhide.app.delegate;

import android.widget.TextView;

/**
 * Created by hasee on 2018/7/2.
 */

public interface ISelectClickListener {
    void selectIt(TextView view, String selectName, long selectId);
}
