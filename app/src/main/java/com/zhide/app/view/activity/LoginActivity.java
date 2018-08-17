package com.zhide.app.view.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.eventBus.LoginEvent;
import com.zhide.app.logic.LogicManager;
import com.zhide.app.utils.UIUtils;
import com.zhide.app.view.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @BindView(R.id.edtPhoneNumber)
    EditText edtPhoneNumber;
    @BindView(R.id.ivLeftIcon)
    ImageView ivLeftIcon;
    @BindView(R.id.ivRightIcon)
    ImageView ivRightIcon;
    @BindView(R.id.edtPsw)
    EditText edtPsw;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.tvForgetPsw)
    TextView tvForgetPsw;
    @BindView(R.id.tvRememberPsw)
    TextView tvRememberPsw;

    @Override
    protected int getCenterView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(UIUtils.getValueString(R.string.login_title));
        setRightTextVisibility(View.VISIBLE);
        setHeader_RightText(UIUtils.getValueString(R.string.register_title));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {

    }

    @OnClick({R.id.ivRightIcon, R.id.tvRememberPsw, R.id.tvForgetPsw, R.id.tvLogin})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivRightIcon:

                break;
            case R.id.tvRememberPsw:
                break;
            case R.id.tvForgetPsw:
                startActivity(ResetPswActivity.makeIntent(this));
                break;
            case R.id.tvLogin:
                String userName = edtPhoneNumber.getText().toString().trim();
                String password = edtPsw.getText().toString().trim();
                LogicManager.getInstance().login(userName, password);
                startActivity(MainActivity.makeIntent(this));
                break;

        }
    }
}
