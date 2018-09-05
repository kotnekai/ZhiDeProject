package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.zhide.app.R;
import com.zhide.app.common.ApplicationHolder;
import com.zhide.app.common.ThreadPoolManager;
import com.zhide.app.delegate.IConfirmClickListener;
import com.zhide.app.delegate.IGetAliPayResult;
import com.zhide.app.delegate.SpinerOnItemClickListener;
import com.zhide.app.eventBus.RechargeInfoEvent;
import com.zhide.app.eventBus.PayOrderEvent;
import com.zhide.app.logic.ChargeManager;
import com.zhide.app.model.AliPayParamModel;
import com.zhide.app.model.ReChargeModel;
import com.zhide.app.model.WXPayParamModel;
import com.zhide.app.utils.DialogUtils;
import com.zhide.app.utils.EmptyUtil;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.utils.UIUtils;
import com.zhide.app.view.adapter.FragmentAdapter;
import com.zhide.app.view.base.BaseActivity;
import com.zhide.app.view.fragment.AboutFragment;
import com.zhide.app.view.fragment.CardChargeFragment;
import com.zhide.app.view.fragment.HomeFragment;
import com.zhide.app.view.fragment.MineFragment;
import com.zhide.app.view.fragment.WalletChargeFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.tvWalletTab:
                viewPager.setCurrentItem(0);
                break;
            case R.id.viewPager:
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
