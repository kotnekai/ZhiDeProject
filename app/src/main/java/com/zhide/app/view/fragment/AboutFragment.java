package com.zhide.app.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhide.app.R;
import com.zhide.app.common.CommonParams;
import com.zhide.app.logic.UserManager;
import com.zhide.app.utils.PreferencesUtils;
import com.zhide.app.utils.ToastUtil;
import com.zhide.app.view.activity.AboutActivity;
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
                startActivity(AboutActivity.makeIntent(getActivity()));
                break;
            case R.id.rlServicePhone:
                callPhone();
                break;
        }
    }
    private void callPhone()
    {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            // 没有获得授权，申请授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.CALL_PHONE)) {
                // 返回值：
                //如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
                //如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
                //如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                // 弹窗需要解释为何需要该权限，再次请求授权
                ToastUtil.showLong("请授权！");
                // 帮跳转到该应用的设置界面，让用户手动授权
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
            else
            {
                // 不需要解释为何需要该权限，直接请求授权
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},0);
            }
        }
        else
        {
            // 已经获得授权，可以打电话
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:4007778408"));
            startActivity(intent);
        }

    }
}
