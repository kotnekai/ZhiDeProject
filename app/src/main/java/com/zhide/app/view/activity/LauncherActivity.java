package com.zhide.app.view.activity;

import android.os.Bundle;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.view.base.BaseActivity;

public class LauncherActivity extends BaseActivity {

    @Override
    protected int getCenterView() {
        return R.layout.activity_launcher;
    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return null;
    }

    @Override
    protected void initHeader() {
        setHeaderVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        long userId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID, 0);
        if (userId == 0) {
            startActivity(LoginActivity.makeIntent(this));
        } else {
            startActivity(MainActivity.makeIntent(this));
        }

  /*      String token = PreferencesUtils.getString(CommonParams.USER_TOKEN);
        if (token == null) {
            startActivity(LoginActivity.makeIntent(this));
        } else {
            startActivity(MainActivity.makeIntent(this));
        }*/
        overridePendingTransition(R.anim.com_activity_fade_in, R.anim.com_activity_fade_out);
        finish();

    }

}
