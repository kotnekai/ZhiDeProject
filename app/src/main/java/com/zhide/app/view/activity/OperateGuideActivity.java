package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.eventBus.GuideModelEvent;
import com.zhide.app.logic.MainManager;
import com.zhide.app.model.GuideModel;
import com.zhide.app.view.base.BaseActivity;
import com.zhide.app.view.base.WebViewActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class OperateGuideActivity extends BaseActivity {

    @BindView(R.id.llBatheGuide)
    LinearLayout llBatheGuide;

    @BindView(R.id.tvGuideItem)
    TextView tvGuideItem;
    private GuideModel.GuideData data;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainManager.getInstance().getGuideList();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGuideModelEvent(GuideModelEvent event) {
        GuideModel guideModel = event.getGuideModel();
        if (guideModel == null) {
            return;
        }
        data = guideModel.getData();
        updateUI();
    }

    private void updateUI() {
        if (data == null) {
            return;
        }
        llBatheGuide.setVisibility(View.VISIBLE);
        tvGuideItem.setText(data.getNI_Name());
    }

    @OnClick({R.id.llBatheGuide})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llBatheGuide:
                if (data == null || data.getNI_Url() == null) {
                return;
            }
            startActivity(WebViewActivity.makeIntent(this, data.getNI_Name(), data.getNI_Url()));
            break;
        }
    }
}
