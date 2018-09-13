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
import com.zhide.app.eventBus.NewsModelEvent;
import com.zhide.app.logic.MainManager;
import com.zhide.app.model.NewsModel;
import com.zhide.app.view.adapter.NewsRecyclerAdapter;
import com.zhide.app.view.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    List<NewsModel.NewsData> newsList = new ArrayList<>();

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
    protected SmartRefreshLayout getRefreshView() {
        return refreshLayout;
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
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewModelEvent(NewsModelEvent event) {
        if (event.getFromPage() != 2) {
            return;
        }
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        NewsModel newsModel = event.getNewsModel();
        if (newsModel == null) {
            return;
        }
        List<NewsModel.NewsData> data = newsModel.getData();
        if (data == null) {
            return;
        }
        newsList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    private void initData() {
        MainManager.getInstance().getMainPageNews(2);
    }

}
