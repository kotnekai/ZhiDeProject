package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.klcxkj.jxing.encode.QREncode;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.view.base.BaseActivity;

import butterknife.BindView;

public class QRCodeActivity extends BaseActivity {

    @BindView(R.id.ivQrCode)
    ImageView ivQrCode;
    @BindView(R.id.tvSchoolName)
    TextView tvSchoolName;

    public static Intent makeIntent(Context context, String gUid, String schoolName) {
        Intent intent = new Intent(context, QRCodeActivity.class);
        intent.putExtra("schoolName", schoolName);
        intent.putExtra("gUid", gUid);
        return intent;
    }

    @Override
    protected int getCenterView() {
        return R.layout.activity_qrcode;

    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return null;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.school_qr_code));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        String schoolName = intent.getStringExtra("schoolName");
        String gUid = intent.getStringExtra("gUid");
        tvSchoolName.setText(schoolName);
        if (TextUtils.isEmpty(gUid)) {
            return;
        }
        QREncode.Builder builder = new QREncode.Builder(this);
        Bitmap bitmap = builder.setContents(gUid).build().encodeAsBitmap();
         ivQrCode.setImageBitmap(bitmap);
    }
}
