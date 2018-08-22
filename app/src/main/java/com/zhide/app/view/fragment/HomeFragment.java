package com.zhide.app.view.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhide.app.R;
import com.zhide.app.view.activity.ShowerMainActivity;
import com.zhide.app.view.base.BaseFragment;
import com.zhide.app.view.views.RippleBackground;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.llRecharge)
    LinearLayout llRecharge;
    @BindView(R.id.llShower)
    LinearLayout llShower;

    @BindView(R.id.llNews)
    LinearLayout llNews;

    @Override
    protected int setFrgContainView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {


        for (int i=0;i<3;i++)
        {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_news,null,false);
            llNews.addView(view);
        }

    }

    @Override
    protected void initView() {
        llRecharge.setOnClickListener(this);
        llShower.setOnClickListener(this);


    }

    @Override
    protected void reLoadData() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.llRecharge:break;
            case R.id.llShower:
                ShowerMainActivity.start(getActivity());
                break;
        }
    }
}
