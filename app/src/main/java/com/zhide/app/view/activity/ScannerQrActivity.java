package com.zhide.app.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.klcxkj.jxing.OnScannerCompletionListener;
import com.klcxkj.jxing.ScannerView;
import com.klcxkj.jxing.common.Scanner;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.utils.EmptyUtil;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.view.base.BaseActivity;
import com.zhide.app.view.widget.NiftyDialogBuilder;

import butterknife.BindView;

public class ScannerQrActivity extends BaseActivity implements OnScannerCompletionListener {


    @BindView(R.id.scannerView)
    ScannerView scannerView;
    @BindView(R.id.ivFlashImg)
    ImageView ivFlashImg;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.tvShowTip)
    TextView tvShowTip;
    private boolean ifOpenLight = false; // 判断是否开启闪光灯

    protected NiftyDialogBuilder dialogBuilder;

    @Override
    protected int getCenterView() {
        return R.layout.activity_scanner_qr;
    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return null;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.scanner_qr_title));
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, ScannerQrActivity.class);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogBuilder = NiftyDialogBuilder.getInstance(this);
        initOptions();
        openLight();
        initClick();
        setAdmin();

    }

    @Override
    protected void onResume() {
        scannerView.onResume();
        resetStatusView();
        super.onResume();
    }

    private void restartPreviewAfterDelay(long delayMS) {
        scannerView.restartPreviewAfterDelay(delayMS);
        resetStatusView();
    }

    private Result mLastResult;

    private void resetStatusView() {
        mLastResult = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mLastResult != null) {
                    restartPreviewAfterDelay(0L);
                    return true;
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        scannerView.onPause();
        super.onPause();
    }

    private void initClick() {

        ivFlashImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ifOpenLight = !ifOpenLight;
                openLight();
            }
        });
    }

    // 是否开启闪光灯
    private void openLight() {
        if (ifOpenLight) {
            // 打开
            ivFlashImg.setSelected(true);
            scannerView.toggleLight(true); // 开闪光灯
        } else {
            // 关闭
            ivFlashImg.setSelected(false);
            scannerView.toggleLight(false);
        }
    }

    private void setAdmin() {
        if (ContextCompat.checkSelfPermission(ScannerQrActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            ActivityCompat.requestPermissions(ScannerQrActivity.this, new String[]{Manifest.permission.CAMERA}, 60);
        }
    }

    private void initOptions() {
        scannerView.setOnScannerCompletionListener(this);
        scannerView.setLaserFrameSize(240, 240);
        int scanMode = 0;

        if (scanMode == 1) {
            //二维码
            scannerView.setScanMode(Scanner.ScanMode.QR_CODE_MODE);
        } else if (scanMode == 2) {
            //一维码
            scannerView.setScanMode(Scanner.ScanMode.PRODUCT_MODE);
        }
        //显示扫描成功后的缩略图
        scannerView.isShowResThumbnail(true);
        //全屏识别
        scannerView.isScanFullScreen(false);
        //隐藏扫描框
        scannerView.isHideLaserFrame(false);
        scannerView.setLaserFrameTopMargin(120);//扫描框与屏幕上方距离
        scannerView.setLaserFrameCornerLength(25);//设置4角长度
        scannerView.setLaserLineResId(R.drawable.scan_line);//线图

    }

    private void showErrorQR(String string) {
        ToastUtil.showShort(string);
        restartPreviewAfterDelay(1000);
    }
    public static final int RESULT_CODE = 200;
    public static final String QR_DATA = "data";
    @Override
    public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap bitmap) {
        if (rawResult == null) {
            showErrorQR("无效的二维码");
            return;
        }
        String resultData = rawResult.getText();
        if (EmptyUtil.isEmpty(resultData)) {
            showErrorQR(getString(R.string.error_qr));
            return;
        }else {
            Intent intent = new Intent();
            intent.putExtra(QR_DATA,resultData);
            setResult(RESULT_CODE,intent);
            finish();
        }
    }
}
