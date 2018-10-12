package com.zhide.app.view.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.zhide.app.utils.EmptyUtil;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.utils.UIUtils;
import com.zhide.app.view.activity.NewsListActivity;
import com.zhide.app.view.activity.OperateGuideActivity;
import com.zhide.app.view.activity.RechargeActivity;
import com.zhide.app.view.activity.ShowerMainActivity;
import com.zhide.app.view.base.BaseFragment;
import com.zhide.app.view.base.WebViewActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    @BindView(R.id.llMainControl)
    LinearLayout llMainControl;
    @BindView(R.id.rlMoney)
    RelativeLayout rlMoney;
    @BindView(R.id.ivGuide)
    ImageView ivGuide;
    Unbinder unbinder;
    private UserData userData;

    @Override
    protected int setFrgContainView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        Log.d("admin", "initData: home");
        MainManager.getInstance().getMainPageNews(1);
        userData = PreferencesUtils.getObject(CommonParams.USER_INFO);
        if (userData != null) {
            updateInfoUI();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewModelEvent(NewsModelEvent event) {
        if (event.getFromPage() != 1) {
            return;
        }

        NewsModel newsModel = event.getNewsModel();
        Log.d("admin", "onNewModelEvent: newsModel=" + newsModel);

        if (newsModel == null) {
            return;
        }
        List<NewsModel.NewsData> data = newsModel.getData();
        if (data == null) {
            return;
        }
        Log.d("admin", "onNewModelEvent: updateNews");
        updateNews(data);
    }


    /**
     * 用户,学校信息，更新账户可用余额用的
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserInfoSchoolInfoEvent event) {
        UserSchoolDataModel userInfoData = event.getUserSchoolDataModel();
        if (userInfoData == null) {
            return;
        }
        UserSchoolDataModel.SchoolInfoData userData = userInfoData.getData();

        if (TextUtils.isEmpty(userData.getSI_Name())) {
            //先去绑定学校
            showBindSchoolDialog(getContext());
        }
        //保存学校水表预扣金额
        if (userData.getSI_Deducting() != null) {
            PreferencesUtils.putFloat(CommonParams.SI_DEDUCTING, userData.getSI_Deducting());
        }
        //保存学校用水费率
        if (userData.getSI_WaterRate() != null) {
            PreferencesUtils.putFloat(CommonParams.SCHOOL_WATERRATE, userData.getSI_WaterRate());
        }
        //服务端balance返回单位是元，Deducting返回是毫分，水表的值，所以先乘以1000再判断
        float balance = userData.getUSI_MainBalance() * 1000;
        //保存预存金额，等下写入到水表中
        PreferencesUtils.putFloat(CommonParams.USI_MAINBALANCE, balance);

        //判断余额是否大于预扣费，大于可以洗澡，小于要跳到充值界面
        if (balance >= userData.getSI_Deducting()) {
            ShowerMainActivity.start(getActivity());
        } else {
            //弹出框，余额不足请充值
            showChargeDialog(getContext());
        }
    }


    /**
     * 用户信息，更新账户可用余额用的
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserInfoEvent(UserInfoEvent event) {
        if (!(event.getUpdatePage() == CommonParams.PAGE_HOME_FRAG_TYPE || event.getUpdatePage() == CommonParams.PAGE_WALLET_FRAG_TYPE)) {
            return;
        }

        UserData userInfo = event.getUserData();
        if (userInfo == null) {
            return;
        }
        userData = userInfo;
        updateInfoUI();
        PreferencesUtils.putObject(CommonParams.USER_INFO, userInfo);

    }

    @Override
    public void onResume() {
        super.onResume();
        userId = PreferencesUtils.getLong(CommonParams.LOGIN_USER_ID);
        UserManager.getInstance().getUserInfoById(userId, CommonParams.PAGE_HOME_FRAG_TYPE);
    }

    private void updateInfoUI() {
        Float usi_totalBalance = userData.getUSI_TotalBalance();
        tvCanUserMoney.setText(UIUtils.getFloatData(usi_totalBalance));
    }

    private void updateNews(List<NewsModel.NewsData> data) {
        llNews.removeAllViews();
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

            String ni_updateTime = data.get(i).getNI_UpdateTime();
            String[] split = ni_updateTime.split(" ");
            tvNewsDate.setText(split[0]);
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
    @OnClick({R.id.llRecharge, R.id.llShower, R.id.tvNewsMore,R.id.ivGuide})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llRecharge:
                if (userData == null) {
                    return;
                }
                if (!EmptyUtil.isCompleteInfo(userData)) {
                    ToastUtil.showShort(getString(R.string.complete_info_tip));
                    return;
                }
                startActivity(RechargeActivity.makeIntent(getActivity()));
                break;
            case R.id.llShower:
                if (userData == null) {
                    return;
                }
                if (!EmptyUtil.isCompleteInfo(userData)) {
                    ToastUtil.showShort(getString(R.string.complete_info_tip));
                    return;
                }
                UserManager.getInstance().getUserSchoolInfoById(userId, 1);
                break;
            case R.id.tvNewsMore:
                NewsListActivity.start(getActivity());
                break;
            case R.id.ivGuide:
                startActivity(OperateGuideActivity.makeIntent(getActivity()));
                break;
        }
    }

    private void showChargeDialog(Context context) {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
        localBuilder.setMessage("当前余额不足，请及时充值");
        localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                startActivity(RechargeActivity.makeIntent(getActivity()));
            }
        });
        localBuilder.setCancelable(false).create();
        localBuilder.show();
    }

    private void showBindSchoolDialog(Context context) {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(context);
        localBuilder.setMessage("使用前，请先绑定所在学校");
        localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
            }
        });
        localBuilder.setCancelable(false).create();
        localBuilder.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
