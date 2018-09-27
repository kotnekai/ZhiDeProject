package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.eventBus.OkResponseEvent;
import com.zhide.app.logic.UserManager;
import com.zhide.app.model.ResponseModel;
import com.zhide.app.model.WithdrawModel;
import com.zhide.app.utils.EmptyUtil;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.view.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class WithdrawActivity extends BaseActivity {

    @BindView(R.id.edtPayeeName)
    EditText edtPayeeName;
    @BindView(R.id.edtAcceptCount)
    EditText edtAcceptCount;
    @BindView(R.id.edtPhoneNumber)
    EditText edtPhoneNumber;
    @BindView(R.id.edtSurplusMoney)
    EditText edtSurplusMoney;
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
    protected SmartRefreshLayout getRefreshView() {
        return null;
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
        long userId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID);
        String payeeName = edtPayeeName.getText().toString();
        String acceptAccount = edtAcceptCount.getText().toString();
        String phoneNumber = edtPhoneNumber.getText().toString();
        String surplusMoney = edtSurplusMoney.getText().toString();
        if (EmptyUtil.isEmpty(payeeName) || EmptyUtil.isEmpty(acceptAccount) || EmptyUtil.isEmpty(phoneNumber)) {
            ToastUtil.showShort(getString(R.string.with_draw_empty_tip));
            return;
        }
        float withdrawMoney = Float.parseFloat(surplusMoney);
        if (withdrawMoney == 0) {
            ToastUtil.showShort(getString(R.string.can_withdraw_money));
            return;
        }
        WithdrawModel params = new WithdrawModel();
        params.setUSW_Money(withdrawMoney);
        params.setUSI_Id(userId);
        params.setUSW_AccountName(payeeName);
        params.setUSW_Account(acceptAccount);
        params.setUSW_ContactMobile(phoneNumber);
        UserManager.getInstance().doWithdraw(params);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOkResponseEvent(OkResponseEvent event) {
        ResponseModel responseModel = event.getResponseModel();
        if (responseModel == null) {
            return;
        }
        int code = responseModel.getCode();
        ToastUtil.showShort(responseModel.getMsg());
        if (code == 1) {
            finish();
        }
    }
}
