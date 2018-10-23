package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.common.ApplicationHolder;
import com.zhide.app.utils.UIUtils;
import com.zhide.app.view.base.BaseActivity;

import butterknife.BindView;

public class AboutActivity extends BaseActivity {


    @BindView(R.id.tvVersion)
    TextView tvVersion;

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        return intent;
    }

    @Override
    protected int getCenterView() {
        return R.layout.activity_about;
    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return null;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(UIUtils.getValueString(R.string.about_title));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
       String version = ApplicationHolder.getInstance().getVersionModel().getVersionName();

        tvVersion.setText(String.format(getString(R.string.depart_name),version));
    }

}
