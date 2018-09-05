package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.view.base.BaseActivity;

import butterknife.BindView;

public class CardChargeBillActivity extends BaseActivity {

    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getCenterView() {
        return R.layout.activity_card_charge_bill;
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, CardChargeBillActivity.class);
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.card_bill_title));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
