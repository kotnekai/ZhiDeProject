package com.zhide.app.view.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.eventBus.NewsModelEvent;
import com.zhide.app.eventBus.UserInfoEvent;
import com.zhide.app.logic.MainManager;
import com.zhide.app.logic.UserManager;
import com.zhide.app.model.NewsModel;
import com.zhide.app.model.UserData;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.utils.UIUtils;
import com.zhide.app.view.activity.NewsListActivity;
import com.zhide.app.view.activity.RechargeActivity;
import com.zhide.app.view.activity.ShowerMainActivity;
import com.zhide.app.view.base.BaseFragment;
import com.zhide.app.view.base.WebViewActivity;

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

    @BindView(R.id.tvCanUserMoney)
    TextView tvCanUserMoney;

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

    /**
     * 用户信息，更新账户可用余额用的
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserInfoEvent(UserInfoEvent event) {
        if (event.getUpdatePage() != 1) {
            return;
        }
        UserData userData = event.getUserData();
        if (userData == null) {
            return;
        }
        updateInfoUI(userData);
    }

    @Override
    public void onResume() {
        super.onResume();
        long userId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID);
        UserManager.getInstance().getUserInfoById(userId,1);
    }

    private void updateInfoUI(UserData userData) {
        Float usi_totalBalance = userData.getUSI_TotalBalance();
        tvCanUserMoney.setText(UIUtils.getFloatData(usi_totalBalance));
    }

    private void updateNews(List<NewsModel.NewsData> data) {
        int size;
        if (data.size() <= 5) {
            size = data.size();
        } else {
            size = 5;
        }
        for (int i = 0; i < size; i++) {
            View newsView = LayoutInflater.from(getContext()).inflate(R.layout.item_news, null, false);
            LinearLayout llItemLayout = newsView.findViewById(R.id.llItemLayout);
            TextView tvNewsTitle = newsView.findViewById(R.id.tvNewsTitle);
            TextView tvNewsDate = newsView.findViewById(R.id.tvNewsDate);
            TextView tvNewsDesc = newsView.findViewById(R.id.tvNewsDesc);
            tvNewsTitle.setText(data.get(i).getNI_Title());
            tvNewsDate.setText(data.get(i).getNI_UpdateTime());
            tvNewsDesc.setText(data.get(i).getNI_Summary());
            final String ni_url = data.get(i).getNI_Url();
            if (ni_url != null) {
                llItemLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().startActivity(WebViewActivity.makeIntent(getActivity(), "", ni_url));
                    }
                });
            }
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
