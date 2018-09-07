package com.zhide.app.view.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.logic.MainManager;
import com.zhide.app.logic.UserManager;
import com.zhide.app.view.activity.NewsListActivity;
import com.zhide.app.view.activity.RechargeActivity;
import com.zhide.app.view.activity.ShowerMainActivity;
import com.zhide.app.view.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Admin create by 2018-08-15
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.llRecharge)
    LinearLayout llRecharge;
    @BindView(R.id.llShower)
    LinearLayout llShower;

    @BindView(R.id.llNews)
    LinearLayout llNews;

    @BindView(R.id.tvNewsMore)
    TextView tvNewsMore;

    @Override
    protected int setFrgContainView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {

        MainManager.getInstance().getMainPageNews();
        for (int i = 0; i < 3; i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_news, null, false);
            llNews.addView(view);
        }

    }

    @Override
    protected void initView() {
    }

    @Override
    protected void reLoadData() {

    }


    @Override
    @OnClick({R.id.llRecharge, R.id.llShower, R.id.tvNewsMore})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llRecharge:
                startActivity(RechargeActivity.makeIntent(getActivity()));
                break;
            case R.id.llShower:
                ShowerMainActivity.start(getActivity());
                break;
            case R.id.tvNewsMore:
                NewsListActivity.start(getActivity());
                break;
        }
    }
}
