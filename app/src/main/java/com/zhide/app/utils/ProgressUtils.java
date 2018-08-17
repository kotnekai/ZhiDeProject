package com.zhide.app.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by gugu on 2018/1/6.
 */

public class ProgressUtils {
    public static ProgressUtils instance;
    private ProgressDialog progressDialog;

    private ProgressUtils() {

    }

    public static ProgressUtils getIntance() {
        if (instance == null) {
            instance = new ProgressUtils();
        }
        return instance;
    }


    public void setProgressDialog(String tips, Context context) {
        progressDialog = ProgressDialog.show(context, "提示", tips, false);
       // progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    public void dismissProgress() {
        if (progressDialog == null) {
            return;
        }
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }
}
