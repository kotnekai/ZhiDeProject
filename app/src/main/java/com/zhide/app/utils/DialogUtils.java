package com.zhide.app.utils;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.delegate.IConfirmClickListener;
import com.zhide.app.delegate.SpinerOnItemClickListener;
import com.zhide.app.model.SpinnerSelectModel;
import com.zhide.app.model.SystemInfoModel;
import com.zhide.app.view.adapter.SpinerAdapter;
import com.zhide.app.view.base.WebViewActivity;

import java.util.List;


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
     * 确认弹窗，，只有一个确认按钮的
     *
     * @param context
     * @param title
     * @param
     */
    public static void showTipsDialog(Context context, String hintContent, String title, boolean inputBtn, final IConfirmClickListener listener) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.tips_dialog_view, null);

        TextView tvOkBtn = (TextView) view.findViewById(R.id.tvOkBtn);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        final EditText edtContent = (EditText) view.findViewById(R.id.edtContent);
        if (hintContent != null) {
            edtContent.setHint(hintContent);
        }
        if (title != null) {
            tvTitle.setText(title);
        }
        if (inputBtn) {
            edtContent.setEnabled(true);
            edtContent.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else {
            edtContent.setEnabled(false);
        }
        tvOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                listener.confirmClick(edtContent.getText().toString());
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

    public static void showPermissionDialog(Context context, final View.OnClickListener listener) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.show_permission_dialog, null);
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

        TextView tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tvConfirm);

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

    public static void showBottomListDialog(final Activity context, List<SpinnerSelectModel> list, final SpinerOnItemClickListener listener) {
        if (list == null || list.size() == 0) {
            return;
        }
        View contentView = LayoutInflater.from(context).inflate(R.layout.common_popup_list_dialog, null);
        ListView lv_pop = (ListView) contentView.findViewById(R.id.lv_pop);
        TextView tvCancel = (TextView) contentView.findViewById(R.id.tvCancel);
        int heightPop;
        if (list.size() > 1) {
            heightPop = 140 + 55 * (list.size() - 1);
        } else {
            heightPop = 150;
        }
        final PopupWindow popWnd = new PopupWindow(contentView, UIUtils.dipToPx(context, 330),
                UIUtils.dipToPx(context, heightPop));
        popWnd.setContentView(contentView);
        popWnd.setTouchable(true);
        //设置背景,这个没什么效果，不添加会报错
        popWnd.setBackgroundDrawable(new BitmapDrawable());
        //设置背景色
        setBackgroundAlpha(context, 0.5f);
        popWnd.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(context, 1f);
            }
        });
        // 设置此参数获得焦点，否则无法点击
        popWnd.setFocusable(true);
        popWnd.setAnimationStyle(R.style.popwindow_anim_style);
        popWnd.showAtLocation(contentView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        SpinerAdapter adapter = new SpinerAdapter(context, list);
        lv_pop.setAdapter(adapter);
        lv_pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onItemClick(position, (int) id);
                if (popWnd.isShowing()) {
                    //设置背景色
                    popWnd.dismiss();
                }
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popWnd.isShowing()) {
                    //设置背景色
                    popWnd.dismiss();
                }
            }
        });
    }
    // dialog.show();
    //设置屏幕背景透明效果

    public static void setBackgroundAlpha(Activity context, float alpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = alpha;
        context.getWindow().setAttributes(lp);
    }

    public static final int aliPayType = 1;
    public static final int wxPayType = 2;

    public static void showBottomSelectTypePop(final Activity context, final SpinerOnItemClickListener listener) {

        View contentView = LayoutInflater.from(context).inflate(R.layout.bottom_pop_layout, null);

        ImageView ivClosePop = contentView.findViewById(R.id.ivClosePop);
        RelativeLayout rlSelectAliPay = contentView.findViewById(R.id.rlSelectAliPay);
        RelativeLayout rlSelectWxPay = contentView.findViewById(R.id.rlSelectWxPay);
        final ImageView ivSelectAliPay = contentView.findViewById(R.id.ivSelectAliPay);
        final ImageView ivSelectWxPay = contentView.findViewById(R.id.ivSelectWxPay);

        final PopupWindow popWnd = new PopupWindow(contentView, UIUtils.getScreenWidth(context),
                UIUtils.getScreenHeight(context) / 3);
        popWnd.setContentView(contentView);
        popWnd.setTouchable(true);
        //设置背景,这个没什么效果，不添加会报错
        popWnd.setBackgroundDrawable(new BitmapDrawable());
        //设置背景色
        setBackgroundAlpha(context, 0.5f);
        popWnd.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(context, 1f);
            }
        });
        ivClosePop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popWnd.isShowing()) {
                    popWnd.dismiss();
                }
            }
        });
        rlSelectAliPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResourceUtils.setImageResource(ivSelectAliPay, R.mipmap.select_blue);
                ResourceUtils.setImageResource(ivSelectWxPay, R.mipmap.select_gray);
                listener.onItemClick(0, aliPayType);
                if (popWnd.isShowing()) {
                    popWnd.dismiss();
                }
            }
        });
        rlSelectWxPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResourceUtils.setImageResource(ivSelectAliPay, R.mipmap.select_gray);
                ResourceUtils.setImageResource(ivSelectWxPay, R.mipmap.select_blue);
                listener.onItemClick(1, wxPayType);
                if (popWnd.isShowing()) {
                    popWnd.dismiss();
                }
            }
        });
        // 设置此参数获得焦点，否则无法点击
        popWnd.setFocusable(true);
        popWnd.setAnimationStyle(R.style.popwindow_anim_style);
        popWnd.showAtLocation(contentView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);


    }


    /**
     * 蓝牙连接提示
     *
     * @param activity
     */
    public static void showEnableBlueToothDialog(Activity activity) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setMessage(R.string.dialog_bluetooth_connect)
                .setNegativeButton(activity.getString(R.string.cancel), null)
                .setPositiveButton(activity.getString(R.string.open), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BluetoothAdapter.getDefaultAdapter().enable();
                    }
                }).create();
        dialog.show();
    }

    /**
     * 网络未连接
     *
     * @param activity
     */
    public static void showNetWorkNotConnectDialog(Activity activity) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setMessage(R.string.dialog_network_stop_connect)
                .setPositiveButton(activity.getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    public static void showApkUpdateDialog(final Context context, SystemInfoModel.SystemData apkInfoModel, final View.OnClickListener listener) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.apk_update_layout, null);

        TextView tvApkVersion = (TextView) view.findViewById(R.id.tvApkVersion);

        TextView tvUpdateTime = (TextView) view.findViewById(R.id.tvUpdateTime);
        TextView tvUpdateContent = (TextView) view.findViewById(R.id.tvUpdateContent);

        TextView tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tvConfirm);


        tvApkVersion.setText("版本：" + apkInfoModel.getNI_Title());

        tvUpdateTime.setText("更新时间：" + apkInfoModel.getNI_UpdateTime());
        tvUpdateContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvUpdateContent.setText(apkInfoModel.getNI_Summary());

        dialog.setView(view);

        if (apkInfoModel.getNI_Index() == 1) {
            dialog.setCanceledOnTouchOutside(false);
            tvCancel.setVisibility(View.GONE);
            dialog.setCancelable(false);
        } else {
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            tvCancel.setVisibility(View.VISIBLE);
        }

        dialog.show();
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

    public static void showAnnouncementDialog(final Context context, final String contentUrl, boolean isForce, String title, final String content, final View.OnClickListener listener) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.announce_layout, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        final TextView tvContentUrl = (TextView) view.findViewById(R.id.tvContentUrl);
        TextView tvUpdateContent = (TextView) view.findViewById(R.id.tvUpdateContent);

        TextView tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        TextView tvConfirm = (TextView) view.findViewById(R.id.tvConfirm);
        tvTitle.setText(title);
        tvUpdateContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        if (EmptyUtil.isEmpty(content)) {
            tvUpdateContent.setText("暂无公告详情");

        } else {
            tvUpdateContent.setText(content);

        }

        if (EmptyUtil.isEmpty(contentUrl)) {
            tvContentUrl.setVisibility(View.GONE);
        } else {
            tvContentUrl.setVisibility(View.VISIBLE);
            tvContentUrl.setText(contentUrl);
        }

        dialog.setView(view);

        if (isForce) {
            dialog.setCanceledOnTouchOutside(false);
            tvCancel.setVisibility(View.GONE);
            dialog.setCancelable(false);
        } else {
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            tvCancel.setVisibility(View.VISIBLE);
        }

        dialog.show();
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
        tvContentUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(WebViewActivity.makeIntent(context, "", contentUrl));
            }
        });

    }


    /**
     * 设备不匹配
     *
     * @param activity
     */
    public static void showDeviceNotMatchDialog(final Activity activity) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setMessage(R.string.dialog_device_no_match)
                .setPositiveButton(activity.getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        activity.finish();
                    }
                }).create();
        dialog.show();
    }

}
