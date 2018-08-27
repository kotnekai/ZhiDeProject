package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.utils.EmptyUtil;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.utils.UIUtils;
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
    private final int millisInFuture = 60000;
    private final int countDownInterval = 1000;
    private CountTimer countTimer;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countTimer = new CountTimer(millisInFuture, countDownInterval);
    }

    @OnClick({R.id.ivRightIcon, R.id.tvGetVerifyCode, R.id.rlRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivRightIcon:
                UIUtils.showOrHide(edtPsw);
                break;
            case R.id.tvGetVerifyCode:
                String phoneNumber = edtPhoneNumber.getText().toString();
                if(EmptyUtil.isEmpty(phoneNumber)){
                    ToastUtil.showShort(getString(R.string.please_input_phone));
                    return;
                }
                if (countTimer != null) {
                    countTimer.cancel();
                    countTimer.start();///开启倒计时
                }
                break;
            case R.id.rlRegister:
                break;
        }
    }

    /**
     * 点击按钮后倒计时
     */
    class CountTimer extends CountDownTimer {

        public CountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        /**
         * 倒计时过程中调用
         *
         * @param millisUntilFinished
         */
        @Override
        public void onTick(long millisUntilFinished) {
            tvGetVerifyCode.setText(String.format(getString(R.string.register_count_time), (millisUntilFinished / 1000) + ""));
            tvGetVerifyCode.setEnabled(false);
        }

        /**
         * 倒计时完成后调用
         */
        @Override
        public void onFinish() {
            tvGetVerifyCode.setEnabled(true);
            tvGetVerifyCode.setText(getString(R.string.register_get_code));
        }
    }
}
