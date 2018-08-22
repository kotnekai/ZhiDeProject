package com.zhide.app.view.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.model.NewsModel;
import com.zhide.app.view.adapter.NewsRecyclerAdapter;
import com.zhide.app.view.adapter.ScanBluetoothDeviceAdapter;
import com.zhide.app.view.base.BaseActivity;
import com.zhide.app.view.views.RippleBackground;
import com.zhide.app.view.widget.NiftyDialogBuilder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Admin
 * @date 2018/8/20
 */

public class NewsListActivity extends BaseActivity {


    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    Context mContext;
    NewsRecyclerAdapter adapter;
    LinearLayoutManager mLayoutManager;

    List<NewsModel> newsList = new ArrayList<>();

    public static void start(Context context) {
        Intent intent = new Intent(context, NewsListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.news_title));

    }

    @Override
    protected int getCenterView() {
        return R.layout.activity_news_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = NewsListActivity.this;
        initView();
        initData();
    }

    private void initView() {


        adapter = new NewsRecyclerAdapter(mContext, newsList);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mLayoutManager.setStackFromEnd(true);
        //mLayoutManager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(0);


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                newsList.clear();
                initData();
                adapter.notifyDataSetChanged();
                refreshLayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                addMore();
                adapter.notifyDataSetChanged();
                refreshLayout.finishLoadMore();
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            NewsModel model = new NewsModel();
            model.setTitle("充值优惠");
            model.setDate("2018-07-22");
            model.setDesc("欢迎各位新生使用本平台服务，新用户于9月31日前首次充值均享有充100元送50元的优惠。");
            newsList.add(model);
        }
    }

    private void addMore() {
        for (int i = 0; i < 10; i++) {
            NewsModel model = new NewsModel();
            model.setTitle("充值优惠");
            model.setDate("2018-07-22");
            model.setDesc("欢迎各位新生使用本平台服务，新用户于9月31日前首次充值均享有充100元送50元的优惠。");
            newsList.add(model);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
