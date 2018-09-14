package com.zhide.app.view.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.eventBus.NewsModelEvent;
import com.zhide.app.logic.MainManager;
import com.zhide.app.logic.UserManager;
import com.zhide.app.model.NewsModel;
import com.zhide.app.view.activity.NewsListActivity;
import com.zhide.app.view.activity.RechargeActivity;
import com.zhide.app.view.activity.ShowerMainActivity;
import com.zhide.app.view.base.BaseFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

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

        MainManager.getInstance().getMainPageNews(1);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewModelEvent(NewsModelEvent event) {
        if (event.getFromPage() != 1) {
            return;
        }
        NewsModel newsModel = event.getNewsModel();
        if (newsModel == null) {
            return;
        }
        List<NewsModel.NewsData> data = newsModel.getData();
        if (data == null) {
            return;
        }
        updateNews(data);
    }

    private void updateNews(List<NewsModel.NewsData> data) {
        for (int i = 0; data.size() < 3; i++) {
            View newsView = LayoutInflater.from(getContext()).inflate(R.layout.item_news, null, false);
            TextView tvNewsTitle =  newsView.findViewById(R.id.tvNewsTitle);
            TextView tvNewsDate =  newsView.findViewById(R.id.tvNewsDate);
            TextView tvNewsDesc =  newsView.findViewById(R.id.tvNewsDesc);
            tvNewsTitle.setText(data.get(i).getNI_Name());
            tvNewsDate.setText(data.get(i).getNI_UpdateTime());
            tvNewsDesc.setText(data.get(i).getNI_Summary());
            llNews.addView(newsView);
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
