package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.apkUpdate.AppUpdateManager;
import com.zhide.app.common.ApplicationHolder;
import com.zhide.app.common.CommonParams;
import com.zhide.app.eventBus.SystemInfoEvent;
import com.zhide.app.logic.MainManager;
import com.zhide.app.model.SystemInfoModel;
import com.zhide.app.utils.DialogUtils;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.utils.UIUtils;
import com.zhide.app.view.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class AboutActivity extends BaseActivity {


    @BindView(R.id.tvVersion)
    TextView tvVersion;
    @BindView(R.id.tvCheckVersion)
    TextView tvCheckVersion;


    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        return intent;
    }

    @Override
    protected int getCenterView() {
        return R.layout.activity_about;
    }

    @Override
    protected SmartRefreshLayout getRefreshView() {
        return null;
    }

    @Override
    protected void initHeader() {
        setHeaderTitle(UIUtils.getValueString(R.string.about_title));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        String version = ApplicationHolder.getInstance().getVersionModel().getVersionName();

        tvVersion.setText(String.format(getString(R.string.depart_name), version));
        tvCheckVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainManager.getInstance().getSystemInfo(CommonParams.PAGE_VERSION_ACTIVITY_TYPE);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSystemInfoEvent(SystemInfoEvent event) {
        SystemInfoModel infoModel = event.getInfoModel();
        if (event.getPageType() != CommonParams.PAGE_VERSION_ACTIVITY_TYPE) {
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
     *
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
                    AppUpdateManager.getInstance().updateNewApk(AboutActivity.this, systemModel);
                }
            });
        } else {
            ToastUtil.showShort(UIUtils.getValueString(R.string.act_is_new_ver));
        }
    }
}
