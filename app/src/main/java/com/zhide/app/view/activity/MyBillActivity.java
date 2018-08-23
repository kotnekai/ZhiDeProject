package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.model.MyBillModel;
import com.zhide.app.view.adapter.MyBillAdapter;
import com.zhide.app.view.base.BaseActivity;

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
    private int selectType = 1;//1,全部，2，支出，3，充值
    private LinearLayoutManager mLayoutManager;
    private MyBillAdapter adapter;

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
        initData();
    }

    private List<MyBillModel> myBillList;

    private void initData() {
        myBillList = new ArrayList<>();
        for(int i=0;i<10;i++){
            if(i%2==0){
                myBillList.add(new MyBillModel("热水表"+i,"2018/08/"+i,(float)(23.10+i),1));
            }else {
                myBillList.add(new MyBillModel("热水表"+i,"2018/08/"+i,(float)(23.10+i),2));
            }
        }
        adapter = new MyBillAdapter(this, myBillList);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mLayoutManager.setStackFromEnd(true);
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setAdapter(adapter);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, MyBillActivity.class);
    }

    @OnClick({R.id.tvAllTab, R.id.tvPayTab, R.id.tvChargeTab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAllTab:
                selectType = 1;
                break;
            case R.id.tvPayTab:
                selectType = 2;
                break;
            case R.id.tvChargeTab:
                selectType = 3;
                break;
        }
    }
}
