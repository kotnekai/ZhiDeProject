package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MyBillActivity extends BaseActivity {

    @BindView(R.id.tvAllTab)
    TextView tvAllTab;
    @BindView(R.id.tvPayTab)
    TextView tvPayTab;
    @BindView(R.id.tvChargeTab)
    TextView tvChargeTab;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    private int selectType = 1;

    @Override
    protected int getCenterView() {
        return R.layout.activity_my_bill;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.my_bill_title));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, MyBillActivity.class);
    }

    @OnClick({R.id.tvAllTab, R.id.tvPayTab, R.id.tvChargeTab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAllTab:
                break;
            case R.id.tvPayTab:
                break;
            case R.id.tvChargeTab:
                break;
        }
    }
}
