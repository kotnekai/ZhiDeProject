package com.zhide.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhide.app.R;
import com.zhide.app.common.ComApplication;
import com.zhide.app.common.CommonParams;
import com.zhide.app.eventBus.LoginEvent;
import com.zhide.app.logic.LogicManager;
import com.zhide.app.model.RegisterLoginModel;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.utils.ProgressUtils;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.utils.UIUtils;
import com.zhide.app.view.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {


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

    }

}
