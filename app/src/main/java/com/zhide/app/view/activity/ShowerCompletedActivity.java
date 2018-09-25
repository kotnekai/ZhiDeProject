package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.eventBus.WaterPreBillEvent;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.view.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import butterknife.BindView;

/**
 * Create by Admin on 2018/08/15
 *
 * @author Admin
 */
public class ShowerCompletedActivity extends BaseActivity implements  View.OnClickListener {

    Context mContext;

    @Override
    protected int getCenterView() {
        return R.layout.activity_shower_completed;
    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return null;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.consume_title));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = ShowerCompletedActivity.this;
        initData();
    }

    /**
     * 加载数据
     */
    private void initData() {
        Intent intent = getIntent();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WaterPreBillEvent event) {
        //服务端返回学生用水预扣费接口，可以执行下发费率
        if (event.getWaterPreBillModel() != null) {
        } else {

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
