package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.apkUpdate.AppUpdateManager;
import com.zhide.app.common.CommonParams;
import com.zhide.app.eventBus.SystemInfoEvent;
import com.zhide.app.logic.MainManager;
import com.zhide.app.model.SystemInfoModel;
import com.zhide.app.utils.DialogUtils;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.utils.UIUtils;
import com.zhide.app.view.adapter.FragmentAdapter;
import com.zhide.app.view.base.BaseActivity;
import com.zhide.app.view.fragment.AboutFragment;
import com.zhide.app.view.fragment.HomeFragment;
import com.zhide.app.view.fragment.MineFragment;
import com.zhide.app.view.views.NoScrollViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create by Admin on 2018/08/14
 *
 * @author Admin
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    @BindView(R.id.ivHomeTab)
    ImageView ivHomeTab;
    @BindView(R.id.ivAboutTab)
    ImageView ivAboutTab;
    @BindView(R.id.ivMineTab)
    ImageView ivMineTab;
    @BindView(R.id.tvFourthTab)
    TextView tvFourthTab;
    private List<Fragment> fragmentList;
    private FragmentAdapter adapter;

    @Override
    protected int getCenterView() {
        return R.layout.activity_main;
    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return null;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(getString(R.string.app_name));
        setLeftIconVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new AboutFragment());
        fragmentList.add(new MineFragment());
        tvFourthTab.setVisibility(View.GONE);//隐藏一个
        adapter = new FragmentAdapter(fragmentList, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        ivHomeTab.setSelected(true);
        MainManager.getInstance().getSystemInfo(CommonParams.PAGE_HOME_ACTIVITY_TYPE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSystemInfoEvent(SystemInfoEvent event) {
        SystemInfoModel infoModel = event.getInfoModel();
        if (event.getPageType() != CommonParams.PAGE_HOME_ACTIVITY_TYPE) {
            return;
        }
        if (infoModel == null) {
            ToastUtil.showShort(getString(R.string.get_net_data_error));
            return;
        }
        if (infoModel.getCode() != 1) {
            return;
        }
        PreferencesUtils.putObject(CommonParams.SYSTEM_INFO, infoModel);
        checkApkUpdate(infoModel);
    }

    /**
     * 检查apk更新
     * @param infoModel
     */
    private void checkApkUpdate(SystemInfoModel infoModel) {
        final SystemInfoModel.SystemData systemModel = MainManager.getInstance().getSystemModel(CommonParams.SYSTEM_APK_ID, infoModel);
        if (systemModel == null) {
            return;
        }
        boolean needUpdateApk = AppUpdateManager.getInstance().needUpdateApk(systemModel);
        if (needUpdateApk) {
            DialogUtils.showApkUpdateDialog(this, systemModel, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUpdateManager.getInstance().updateNewApk(MainActivity.this, systemModel);
                }
            });
        } else {
            ToastUtil.showShort(UIUtils.getValueString(R.string.act_is_new_ver));
        }
    }


    @OnClick({R.id.ivHomeTab, R.id.ivAboutTab, R.id.ivMineTab, R.id.tvFourthTab})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivHomeTab:
                viewPager.setCurrentItem(0);
                ivHomeTab.setSelected(true);
                ivAboutTab.setSelected(false);
                ivMineTab.setSelected(false);
                setHeaderTitle(getString(R.string.app_name));

                break;
            case R.id.ivAboutTab:
                viewPager.setCurrentItem(1);
                ivHomeTab.setSelected(false);
                ivAboutTab.setSelected(true);
                ivMineTab.setSelected(false);
                setHeaderTitle(getString(R.string.about_tab));

                break;
            case R.id.ivMineTab:
                viewPager.setCurrentItem(2);
                ivHomeTab.setSelected(false);
                ivAboutTab.setSelected(false);
                ivMineTab.setSelected(true);
                setHeaderTitle(getString(R.string.mine_tab_title));

                break;
            case R.id.tvFourthTab:

                break;
        }

    }
}
