package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.eventBus.MyBillEvent;
import com.zhide.app.logic.BillManager;
import com.zhide.app.model.MyBillModel;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.view.adapter.MyBillAdapter;
import com.zhide.app.view.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

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


    @BindView(R.id.ivTabIcon1)
    ImageView ivTabIcon1;
    @BindView(R.id.ivTabIcon2)
    ImageView ivTabIcon2;
    @BindView(R.id.ivTabIcon3)
    ImageView ivTabIcon3;

    private String selectType;
    private LinearLayoutManager mLayoutManager;
    private MyBillAdapter adapter;
    private List<MyBillModel.BillData> dataList = new ArrayList<>();
    private long userId;

    @Override
    protected int getCenterView() {
        return R.layout.activity_my_bill;
    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return smartRefresh;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.my_bill_title));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, MyBillActivity.class);
    }

    private void initData() {
        userId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID);
        adapter = new MyBillAdapter(this, dataList);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mLayoutManager.setStackFromEnd(true);
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setAdapter(adapter);
        selectType = getString(R.string.all_str);
        BillManager.getInstance().getMyBillData(userId, selectType);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMyBillEvent(MyBillEvent event) {
        MyBillModel myBillModel = event.getMyBillModel();
        if (myBillModel == null) {
            return;
        }
        List<MyBillModel.BillData> data = myBillModel.getData();
        if (data == null) {
            return;
        }
        dataList.clear();
        dataList.addAll(data);
        adapter.notifyDataSetChanged();
    }


    @OnClick({R.id.tvAllTab, R.id.tvPayTab, R.id.tvChargeTab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAllTab:
                selectType = getString(R.string.all_str);
                ivTabIcon1.setVisibility(View.VISIBLE);
                ivTabIcon2.setVisibility(View.INVISIBLE);
                ivTabIcon3.setVisibility(View.INVISIBLE);

                break;
            case R.id.tvPayTab:
                selectType = getString(R.string.pay_str);
                ivTabIcon1.setVisibility(View.INVISIBLE);
                ivTabIcon2.setVisibility(View.VISIBLE);
                ivTabIcon3.setVisibility(View.INVISIBLE);
                break;
            case R.id.tvChargeTab:
                selectType = getString(R.string.recharge_title);
                ivTabIcon1.setVisibility(View.INVISIBLE);
                ivTabIcon2.setVisibility(View.INVISIBLE);
                ivTabIcon3.setVisibility(View.VISIBLE);
                break;
        }
        BillManager.getInstance().getMyBillData(userId, selectType);

    }
}
