package com.zhide.app.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.zhide.app.model.NotificationModel;


/**
 * Created by hasee on 2018/6/7.
 */

public class PageIntentUtils {
    public static Intent getPageIntent(Context context, NotificationModel notificationModel) {
        if (notificationModel == null || notificationModel.getViewType() == 0) {
            return null;
        }

        Intent intent = null;
        switch (notificationModel.getViewType()) {
            case 1: //跳转至..

                break;
            case 2: //跳转至..

                break;

        }
        return intent;
    }

    public static PendingIntent getPendIntent(Context context,Intent intent) {
        if (intent == null) {
            return null;
        }
        return PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

}
