package com.zhide.app.view.activity;

import android.os.Bundle;
import android.view.View;

import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.view.base.BaseActivity;

public class LauncherActivity extends BaseActivity {

    @Override
    protected int getCenterView() {
        return 0;
    }

    @Override
    protected void initHeader() {
        setHeaderVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        initData();
    }

    private void initData() {
        startActivity(MainActivity.makeIntent(this));

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
