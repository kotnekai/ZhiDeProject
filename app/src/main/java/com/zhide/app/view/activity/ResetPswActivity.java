package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ResetPswActivity extends BaseActivity {

    @BindView(R.id.edtPhoneNumber)
    EditText edtPhoneNumber;
    @BindView(R.id.edtNewPsw)
    EditText edtNewPsw;
    @BindView(R.id.ivRightIcon)
    ImageView ivRightIcon;
    @BindView(R.id.edtVerifyCode)
    EditText edtVerifyCode;
    @BindView(R.id.tvGetVerifyCode)
    TextView tvGetVerifyCode;
    @BindView(R.id.tvLogin)
    TextView tvLogin;

    @Override
    protected int getCenterView() {
        return R.layout.activity_reset_psw;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.reset_password_title));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, ResetPswActivity.class);
        return intent;
    }

    @OnClick({R.id.ivRightIcon, R.id.tvGetVerifyCode, R.id.tvLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivRightIcon:
                break;
            case R.id.tvGetVerifyCode:
                break;
            case R.id.tvLogin:
                break;
        }
    }
}
