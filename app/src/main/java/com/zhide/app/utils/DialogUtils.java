package com.zhide.app.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zhide.app.R;


public class DialogUtils {

    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param
     * @return
     */
    public static Dialog createDingdanDialog(Context context, String timeid,
                                             String productid, String deviceid, String accountid,
                                             String accounttype, String usercount, String ykmoney, String consumemone, String rate, String mac) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.activity_consumption_list, null);

        EditText timeid_edit = (EditText) v.findViewById(R.id.timeid_edit);
        EditText productid_edit = (EditText) v.findViewById(R.id.productid_edit);
        EditText deviceid_edit = (EditText) v.findViewById(R.id.deviceid_edit);
        EditText accountid_edit = (EditText) v.findViewById(R.id.accountid_edit);
        EditText accounttype_edit = (EditText) v.findViewById(R.id.accounttype_edit);
        EditText usercount_edit = (EditText) v.findViewById(R.id.usercount_edit);
        EditText ykmoney_edit = (EditText) v.findViewById(R.id.ykmoney_edit);
        EditText consumeMone_edit = (EditText) v.findViewById(R.id.consumeMone_edit);
        EditText rate_edit = (EditText) v.findViewById(R.id.rate_edit);
        EditText mac_edit = (EditText) v.findViewById(R.id.mac_edit);

        timeid_edit.setText(timeid);
        productid_edit.setText(productid);
        deviceid_edit.setText(deviceid);
        accountid_edit.setText(accountid);
        accounttype_edit.setText(accounttype);
        usercount_edit.setText(usercount);
        ykmoney_edit.setText(ykmoney);
        consumeMone_edit.setText(consumemone);
        rate_edit.setText(rate);
        mac_edit.setText(mac);
        // 创建自定义样式dialog
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        // 不可以用“返回键”取消
        loadingDialog.setCancelable(true);
        loadingDialog.setContentView(v, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        return loadingDialog;
    }
}
