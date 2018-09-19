package com.zhide.app.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.zhide.app.eventBus.UserInfoSchoolInfoEvent;
import com.zhide.app.logic.MainManager;
import com.zhide.app.logic.UserManager;
import com.zhide.app.model.NewsModel;
import com.zhide.app.model.UserData;
import com.zhide.app.model.UserSchoolDataModel;
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
    long userId;
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
     * 用户,学校信息，更新账户可用余额用的
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserInfoSchoolInfoEvent event) {
        UserSchoolDataModel userData = event.getUserSchoolDataModel();
        if (userData == null) {
            return;
        }

        //判断余额是否大于预扣费，大于可以洗澡，小于要跳到充值界面
        if (userData.getUSI_MainBalance()>userData.getSI_Deducting())
        {
            ShowerMainActivity.start(getActivity());
        }
        else
        {
            //弹出框，余额不足请充值
        }
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
         userId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID);
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
                UserManager.getInstance().getUserSchoolInfoById(userId,1);
                break;
            case R.id.tvNewsMore:
                NewsListActivity.start(getActivity());
                break;
        }
    }

    private void showChargeDialog(Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("请输入");     //设置对话框标题
        builder.setIcon(android.R.drawable.btn_star);      //设置对话框标题前的图
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setCancelable(true);   //设置按钮是否可以按返回键取消,false则不可以取消
        AlertDialog dialog = builder.create();  //创建对话框
        dialog.setCanceledOnTouchOutside(true);      //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
        dialog.show();
    }
}
