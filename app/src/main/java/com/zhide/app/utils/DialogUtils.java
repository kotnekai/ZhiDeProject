package com.zhide.app.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    /**
     *确认弹窗，，只有一个确认按钮的
     * @param context
     * @param title
     * @param content
     */
    public static void showTipsDialog(Context context, String title, String content) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.tips_dialog_view, null);

        TextView tvOkBtn = (TextView) view.findViewById(R.id.tvOkBtn);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        TextView tvContent = (TextView) view.findViewById(R.id.tvContent);
        if (title != null) {
            tvTitle.setText(title);
        }
        if (content != null) {
            tvContent.setText(content);
        }
        tvOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        dialog.setView(view);
        dialog.show();
        Window dialogWindow = dialog.getWindow();
        if (dialogWindow == null) {
            return;
        }
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = UIUtils.dipToPx(context, 300);
        dialogWindow.setAttributes(lp);

        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL);
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    public static void showConfirmDialog(Context context, String content, final View.OnClickListener listener) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.show_confirm_dialog, null);
        dialog.setView(view);
        dialog.show();
        Window dialogWindow = dialog.getWindow();
        if (dialogWindow == null) {
            return;
        }
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL);
        lp.width = UIUtils.dipToPx(context, 300);
        //lp.height = UIUtils.dipToPx(context,200);
        dialogWindow.setAttributes(lp);
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tvContent = (TextView) view.findViewById(R.id.tvContent);

        TextView tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tvConfirm);
        tvContent.setText(content);


        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

    }

}
