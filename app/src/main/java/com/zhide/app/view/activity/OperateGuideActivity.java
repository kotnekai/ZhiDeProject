package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhide.app.R;
import com.zhide.app.eventBus.GuideModelEvent;
import com.zhide.app.logic.MainManager;
import com.zhide.app.model.GuideModel;
import com.zhide.app.view.adapter.GuideAdapter;
import com.zhide.app.view.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OperateGuideActivity extends BaseActivity {

    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private List<GuideModel.GuideData> dataList = new ArrayList<>();
    private GuideAdapter adapter;

    @Override
    protected int getCenterView() {
        return R.layout.activity_operate_guide;
    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return smartRefresh;
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
        initView();
        adapter = new GuideAdapter(this, dataList);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        MainManager.getInstance().getGuideList();
    }

    private void initView(){
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                MainManager.getInstance().getGuideList();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGuideModelEvent(GuideModelEvent event) {
        GuideModel guideModel = event.getGuideModel();
        smartRefresh.finishRefresh();
        if (guideModel == null) {
            return;
        }
        List<GuideModel.GuideData> data = guideModel.getData();
        dataList.clear();
        dataList.addAll(data);
        adapter.notifyDataSetChanged();
    }

}
