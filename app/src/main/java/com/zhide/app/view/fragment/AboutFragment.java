package com.zhide.app.view.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.logic.UserManager;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.view.activity.OperateGuideActivity;
import com.zhide.app.view.activity.RepairActivity;
import com.zhide.app.view.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutFragment extends BaseFragment {

    @BindView(R.id.llFailureReporting)
    LinearLayout llFailureReporting;

    @BindView(R.id.llOperationGuide)
    LinearLayout llOperationGuide;
    @BindView(R.id.llVersion)
    LinearLayout llVersion;
    @BindView(R.id.rlServicePhone)
    RelativeLayout rlServicePhone;

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void reLoadData() {

    }

    @Override
    protected int setFrgContainView() {
        return R.layout.fragment_about;

    }

    @OnClick({R.id.llFailureReporting, R.id.llOperationGuide, R.id.llVersion, R.id.rlServicePhone})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.llFailureReporting:
                startActivity(RepairActivity.makeIntent(getActivity()));
                break;
            case R.id.llOperationGuide:
                startActivity(OperateGuideActivity.makeIntent(getActivity()));
                break;
            case R.id.llVersion:
                String userId = PreferencesUtils.getString(CommonParams.LOGIN_USER_ID);
                UserManager.getInstance().getUserInfoById(userId);
                break;
            case R.id.rlServicePhone:
                break;
        }
    }
}
