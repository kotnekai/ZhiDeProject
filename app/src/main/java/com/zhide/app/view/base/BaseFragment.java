package com.zhide.app.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.eventBus.DefaultEvent;
import com.zhide.app.utils.NetworkUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by gugu on 2018/6/29.
 */

public abstract class BaseFragment extends Fragment {
    protected View containView;

    private LinearLayout llCenterView;
    @BindView(R.id.llEmptyPage)
    LinearLayout llEmptyPage;
    @BindView(R.id.tvConnectTip)
    TextView tvConnectTip;
    private Unbinder bindObj;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        containView = inflater.inflate(R.layout.frag_base_layout, null);

        llCenterView = containView.findViewById(R.id.llCenterView);

        if (setFrgContainView() != 0) {
            llCenterView.removeAllViews();
            View mAddContentView = LayoutInflater.from(getActivity()).inflate(setFrgContainView(), null);
            llCenterView.addView(mAddContentView, new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        }
        bindObj = ButterKnife.bind(this, containView);
        initView();
        initData();
        setNetErrorView();

        llEmptyPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reLoadData();
            }
        });
        return containView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bindObj != null) {
            bindObj.unbind();
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 全局处理一些判断的时候可以用
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDefaultEventBus(DefaultEvent event) {

    }

    /**
     * 是否显示网络错误页面
     *
     * @param
     */
    public void setNetErrorView() {
        boolean isNetConnect = NetworkUtils.isNetworkConnected(getActivity());
        if (isNetConnect) {
            llCenterView.setVisibility(View.VISIBLE);
            llEmptyPage.setVisibility(View.GONE);
        } else {
            llEmptyPage.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 是否显示空白页面
     *
     * @param
     */
    public void setEmptyView(boolean isEmpty, String errorTip) {
        if (isEmpty) {
            llCenterView.setVisibility(View.VISIBLE);
            llEmptyPage.setVisibility(View.VISIBLE);
            if (errorTip == null) {
                return;
            }
            tvConnectTip.setText(errorTip);
        } else {
            llEmptyPage.setVisibility(View.GONE);
            llCenterView.setVisibility(View.VISIBLE);
        }

    }


    /**
     * 一些View的相关操作
     */
    protected abstract void initView();

    protected abstract void initData();

    protected abstract void reLoadData();


    /**
     * 此方法用于设置内容布局页面
     *
     * @return 布局文件资源ID
     */
    protected abstract int setFrgContainView();

}

