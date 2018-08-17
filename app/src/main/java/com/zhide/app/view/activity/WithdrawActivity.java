package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zhide.app.R;
import com.zhide.app.view.base.BaseActivity;

public class WithdrawActivity extends BaseActivity {

    @Override
    protected int getCenterView() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.with_draw_title));
    }
    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, WithdrawActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
