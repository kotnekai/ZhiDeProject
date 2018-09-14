package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.common.ComApplication;
import com.zhide.app.common.CommonParams;
import com.zhide.app.eventBus.LoginEvent;
import com.zhide.app.logic.LogicManager;
import com.zhide.app.model.RegisterLoginModel;
import com.zhide.app.utils.DesUtil;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.utils.UIUtils;
import com.zhide.app.view.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.edtPhoneNumber)
    EditText edtPhoneNumber;
    @BindView(R.id.ivRightIcon)
    ImageView ivRightIcon;
    @BindView(R.id.edtPsw)
    EditText edtPsw;
    @BindView(R.id.rlLogin)
    RelativeLayout rlLogin;
    @BindView(R.id.tvForgetPsw)
    TextView tvForgetPsw;
    @BindView(R.id.ckRememberPsw)
    CheckBox ckRememberPsw;

    @Override
    protected int getCenterView() {
        return R.layout.activity_login;
    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return null;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(UIUtils.getValueString(R.string.login_title));
        setRightTextVisibility(View.VISIBLE);
        setLeftIconVisibility(View.GONE);
        setHeader_RightText(UIUtils.getValueString(R.string.register_title));
        setHeader_RightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegisterActivity.makeIntent(LoginActivity.this));

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ComApplication.getApp().addActivity(this);
        initView();
    }

    private void initView() {
        tvForgetPsw.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        boolean isRemember = PreferencesUtils.getBoolean(CommonParams.PRF_PSW_CHECK_STATE);
        if (isRemember) {
            String phoneNumber = PreferencesUtils.getString(CommonParams.PRF_NAME);
            String password = PreferencesUtils.getString(CommonParams.PRF_PSW);
            edtPhoneNumber.setText(phoneNumber);
            edtPsw.setText(password);
            ckRememberPsw.setChecked(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        RegisterLoginModel dataModel = event.getDataModel();
        if (dataModel == null) {
            return;
        }
        RegisterLoginModel.UserModel data = dataModel.getData();
        if(data!=null){
            PreferencesUtils.putLong(CommonParams.LOGIN_USER_ID,data.getUSI_Id());
        }
        ToastUtil.showShort(dataModel.getMessage());
        if (dataModel.getCode() == 1) {
            startActivity(MainActivity.makeIntent(this));
            finish();
        }
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @OnClick({R.id.ivRightIcon, R.id.ckRememberPsw, R.id.tvForgetPsw, R.id.rlLogin})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivRightIcon:
                UIUtils.showOrHide(edtPsw);
                break;
            case R.id.tvForgetPsw:
                startActivity(ResetPswActivity.makeIntent(this));
                break;
            case R.id.rlLogin:

                String userName = edtPhoneNumber.getText().toString().trim();
                String password = edtPsw.getText().toString().trim();
                if (ckRememberPsw.isChecked()) {
                    PreferencesUtils.putBoolean(CommonParams.PRF_PSW_CHECK_STATE, true);
                    PreferencesUtils.putString(CommonParams.PRF_NAME, userName);
                    PreferencesUtils.putString(CommonParams.PRF_PSW, password);
                } else {
                    PreferencesUtils.putString(CommonParams.PRF_NAME, null);
                    PreferencesUtils.putString(CommonParams.PRF_PSW, null);
                    PreferencesUtils.putBoolean(CommonParams.PRF_PSW_CHECK_STATE, false);
                }
                LogicManager.getInstance().login(userName, password);
               // startActivity(MainActivity.makeIntent(this));
                break;

        }
    }

}
