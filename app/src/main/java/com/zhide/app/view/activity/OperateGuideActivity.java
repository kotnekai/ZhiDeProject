package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class OperateGuideActivity extends BaseActivity {

    @BindView(R.id.llBatheGuide)
    LinearLayout llBatheGuide;

    @Override
    protected int getCenterView() {
        return R.layout.activity_operate_guide;
    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return null;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.operate_guide_title));
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, OperateGuideActivity.class);
    }

    @OnClick({R.id.llBatheGuide})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llBatheGuide:
                break;
        }
    }
}
