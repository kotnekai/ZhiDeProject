package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.utils.UIUtils;
import com.zhide.app.view.adapter.FragmentAdapter;
import com.zhide.app.view.base.BaseActivity;
import com.zhide.app.view.fragment.CardChargeFragment;
import com.zhide.app.view.fragment.WalletChargeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RechargeActivity extends BaseActivity {


    @BindView(R.id.tvWalletTab)
    TextView tvWalletTab;
    @BindView(R.id.tvCardTab)
    TextView tvCardTab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<Fragment> fragmentList;
    private FragmentAdapter adapter;

    @Override
    protected int getCenterView() {
        return R.layout.activity_recharge;
    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return null;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(UIUtils.getValueString(R.string.mine_charge));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new WalletChargeFragment());
        fragmentList.add(new CardChargeFragment());
        adapter = new FragmentAdapter(fragmentList, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }


    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, RechargeActivity.class);
        return intent;
    }

    @OnClick({R.id.tvWalletTab, R.id.tvCardTab})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.tvWalletTab:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tvCardTab:
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
