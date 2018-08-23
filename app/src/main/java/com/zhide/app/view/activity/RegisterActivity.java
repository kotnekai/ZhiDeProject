package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.edtPhoneNumber)
    EditText edtPhoneNumber;
    @BindView(R.id.edtPsw)
    EditText edtPsw;
    @BindView(R.id.ivRightIcon)
    ImageView ivRightIcon;
    @BindView(R.id.edtVerifyCode)
    EditText edtVerifyCode;
    @BindView(R.id.tvGetVerifyCode)
    TextView tvGetVerifyCode;
    @BindView(R.id.tvRegister)
    TextView tvRegister;
    @BindView(R.id.rlRegister)
    RelativeLayout rlRegister;

    @Override
    protected int getCenterView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.register_title));
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    @OnClick({R.id.ivRightIcon, R.id.tvGetVerifyCode, R.id.rlRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivRightIcon:
                break;
            case R.id.tvGetVerifyCode:
                break;
            case R.id.rlRegister:
                break;
        }
    }
}
