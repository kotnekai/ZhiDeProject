package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.utils.EmptyUtil;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class WithdrawActivity extends BaseActivity {

    @BindView(R.id.edtPayeeName)
    EditText edtPayeeName;
    @BindView(R.id.edtAcceptCount)
    EditText edtAcceptCount;
    @BindView(R.id.edtPhoneNumber)
    EditText edtPhoneNumber;
    @BindView(R.id.tvSurplusMoney)
    TextView tvSurplusMoney;
    @BindView(R.id.tvCancel)
    TextView tvCancel;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
    private float totalMoney;

    @Override
    protected int getCenterView() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.with_draw_title));
    }

    public static Intent makeIntent(Context context, float totalMoney) {
        Intent intent = new Intent(context, WithdrawActivity.class);
        intent.putExtra("totalMoney", totalMoney);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            totalMoney = intent.getFloatExtra("totalMoney", 0);
        }
        tvSurplusMoney.setText(String.valueOf(totalMoney));
    }

    @OnClick({R.id.tvCancel, R.id.tvSubmit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                finish();
                break;
            case R.id.tvSubmit:
                submitOrder();
                break;
        }
    }

    private void submitOrder() {
        String payeeName = edtPayeeName.getText().toString();
        String acceptAccount = edtAcceptCount.getText().toString();
        String phoneNumber = edtPhoneNumber.getText().toString();
        String surplusMoney = tvSurplusMoney.getText().toString();
        if (EmptyUtil.isEmpty(payeeName)||EmptyUtil.isEmpty(acceptAccount)||EmptyUtil.isEmpty(phoneNumber)) {
            ToastUtil.showShort(getString(R.string.with_draw_empty_tip));
            return;
        }
    }

}
